package com.fortexa.api_gateway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.gateway.filter.ratelimiter.RateLimiter;
import org.springframework.cloud.gateway.filter.ratelimiter.RedisRateLimiter;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration tests for rate limiting functionality.
 * These tests require a running Redis instance accessible to the application context,
 * or a mocked RateLimiter bean for isolated testing.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test") // Assumes an application-test.properties or similar for test-specific configs if needed
// @AutoConfigureWebTestClient // Already included with SpringBootTest for WebTestClient
public class RateLimitingIntegrationTests {

    private static final Logger logger = LoggerFactory.getLogger(RateLimitingIntegrationTests.java);

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ApplicationContext context;

    @BeforeEach
    void setUp() {
        // Check if a RedisRateLimiter bean is present. If not, these tests will likely fail or be misleading.
        try {
            context.getBean(RedisRateLimiter.class);
            logger.info("RedisRateLimiter bean found. Proceeding with rate limiting tests.");
        } catch (Exception e) {
            logger.warn("RedisRateLimiter bean NOT found. Rate limiting tests might not behave as expected. " +
                        "Ensure Redis is configured and enabled for these tests.", e);
        }
    }
    
    /**
     * Tests the rate limiting on a protected endpoint (e.g., /api/v1/users/**).
     * 
     * NOTE: This test relies on the actual RedisRateLimiter and a running Redis instance.
     * The default RedisRateLimiter is configured for 10 requests/sec, burst capacity 20.
     * 
     * To make this test more robust in a CI environment without external Redis,
     * one might consider:
     * 1. Using an embedded Redis server (e.g., Testcontainers, or embedded-redis).
     * 2. Mocking the `RateLimiter` bean itself (e.g., using `@MockBean` if you want to test the gateway route configuration
     *    without hitting actual Redis). This would test if the filter is applied, but not Redis functionality.
     * 
     * This test attempts to verify the actual rate limiting behavior.
     */
    @Test
    @Disabled("Requires a running Redis instance and careful timing; enable for dedicated integration test runs.")
    void testRateLimitingOnUserEndpoint() throws InterruptedException {
        String path = "/api/v1/users/testuser"; // A path configured for rate limiting
        int totalRequests = 30; // More than burst capacity (20) + one replenish cycle (10)
        int initialBurst = 20; // Should pass (matches burst capacity)
        int subsequentRequests = 10; // Some should pass, some should fail

        AtomicInteger successfulRequests = new AtomicInteger(0);
        AtomicInteger failedRequests429 = new AtomicInteger(0);
        CountDownLatch latch = new CountDownLatch(totalRequests);
        ExecutorService executorService = Executors.newFixedThreadPool(totalRequests / 2); // Use multiple threads

        // Initial burst - these should ideally all pass if Redis is clean and responsive
        for (int i = 0; i < initialBurst; i++) {
            executorService.submit(() -> {
                webTestClient.get().uri(path)
                        .header("X-Forwarded-For", "10.0.0.1") // Consistent key for KeyResolver
                        .exchange()
                        .expectStatus().is stazione -> { // Check if status is not 429
                            if (stazione.is4xxClientError() && stazione.value() == HttpStatus.TOO_MANY_REQUESTS.value()) {
                                failedRequests429.incrementAndGet();
                            } else {
                                successfulRequests.incrementAndGet();
                                // We expect 401 Unauthorized if no token is provided, or 404/503 if downstream is unavailable.
                                // The key is that it's NOT a 429 for these initial requests.
                                assertTrue(stazione.value() != HttpStatus.TOO_MANY_REQUESTS.value(),
                                        "Request " + successfulRequests.get() + " should not be rate limited (429). Actual: " + stazione.value());
                            }
                            latch.countDown();
                        };
            });
        }
        
        // Wait a moment for the burst to be processed (ideally less than a second for replenishment)
        TimeUnit.MILLISECONDS.sleep(500); 

        // Subsequent requests - some of these should be rate limited
        for (int i = 0; i < subsequentRequests; i++) {
             executorService.submit(() -> {
                webTestClient.get().uri(path)
                        .header("X-Forwarded-For", "10.0.0.1") // Consistent key
                        .exchange()
                        .expectStatus().is stazione -> {
                             if (stazione.value() == HttpStatus.TOO_MANY_REQUESTS.value()) {
                                failedRequests429.incrementAndGet();
                            } else {
                                successfulRequests.incrementAndGet();
                            }
                            latch.countDown();
                        };
            });
             // Small delay between requests to allow rate limiter to potentially recover slightly if test is slow
             if (i % 5 == 0) TimeUnit.MILLISECONDS.sleep(100);
        }

        latch.await(30, TimeUnit.SECONDS); // Wait for all requests to complete
        executorService.shutdown();

        logger.info("Rate Limiting Test Results: Successful Requests: {}, Failed (429) Requests: {}",
                successfulRequests.get(), failedRequests429.get());

        // Assertions:
        // - At least `initialBurst` should be successful before any 429s, assuming a clean start.
        // - Some requests should have been rate-limited (429).
        // - The number of successful requests should be around burst capacity + replenish rate (e.g. 20 + a few, depending on timing).
        assertTrue(failedRequests429.get() > 0, "Expected some requests to be rate limited (429).");
        // This is a tricky assertion due to timing, concurrency, and actual Redis behavior.
        // A more reliable test might check that after exceeding burst, subsequent immediate requests fail.
        // For this structural test, we'll just ensure some 429s occurred.
    }
}
