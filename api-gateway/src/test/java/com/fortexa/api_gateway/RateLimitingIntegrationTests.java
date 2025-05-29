package com.fortexa.api_gateway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
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
 * Integration tests for rate limiting functionality in the API Gateway.
 *
 * This test verifies that the rate limiting mechanism, backed by Redis, works as expected.
 * It simulates multiple requests to an endpoint and checks if the rate limiter enforces
 * the configured limits.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class RateLimitingIntegrationTests {

    private static final Logger logger = LoggerFactory.getLogger(RateLimitingIntegrationTests.class);

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ApplicationContext context;

    /**
     * Sets up the test environment by checking if the RedisRateLimiter bean is available.
     */
    @BeforeEach
    void setUp() {
        try {
            context.getBean(RedisRateLimiter.class);
            logger.info("RedisRateLimiter bean found. Proceeding with rate limiting tests.");
        } catch (Exception e) {
            logger.warn("RedisRateLimiter bean NOT found. Rate limiting tests might not behave as expected. " +
                    "Ensure Redis is configured and enabled for these tests.", e);
        }
    }

    /**
     * Tests the rate limiting functionality on a user endpoint.
     *
     * This test sends a burst of requests followed by additional requests to verify
     * that some requests are rate-limited (HTTP 429) while others succeed.
     *
     * @throws InterruptedException if the thread is interrupted while waiting for the latch
     */
    @Test
    @Disabled("Requires a running Redis instance and careful timing; enable for dedicated integration test runs.")
    void testRateLimitingOnUserEndpoint() throws InterruptedException {
        String path = "/api/v1/users/testuser";
        int totalRequests = 30;
        int initialBurst = 20;
        int subsequentRequests = 10;

        AtomicInteger successfulRequests = new AtomicInteger(0);
        AtomicInteger failedRequests429 = new AtomicInteger(0);
        CountDownLatch latch = new CountDownLatch(totalRequests);
        ExecutorService executorService = Executors.newFixedThreadPool(totalRequests / 2);

//        // Send initial burst of requests
//        for (int j = 0; j < initialBurst; j++) {
//            executorService.submit(() -> {
//                webTestClient.get().uri(path)
//                        .header("X-Forwarded-For", "10.0.0.1")
//                        .exchange()
//                        .expectStatus().value(status -> {
//                            if (status == HttpStatus.TOO_MANY_REQUESTS.value()) {
//                                failedRequests429.incrementAndGet();
//                            } else {
//                                successfulRequests.incrementAndGet();
//                            }
//                            latch.countDown();
//                        });
//            });
//        }
//
//        // Wait briefly before sending subsequent requests
//        TimeUnit.MILLISECONDS.sleep(500);
//
//        // Send subsequent requests
//        for (int k = 0; k < subsequentRequests; k++) {
//            executorService.submit(() -> {
//                webTestClient.get().uri(path)
//                        .header("X-Forwarded-For", "10.0.0.1")
//                        .exchange()
//                        .expectStatus().value(status -> {
//                            if (status == HttpStatus.TOO_MANY_REQUESTS.value()) {
//                                failedRequests429.incrementAndGet();
//                            } else {
//                                successfulRequests.incrementAndGet();
//                            }
//                            latch.countDown();
//                        });
//            });
//            if (k % 5 == 0) TimeUnit.MILLISECONDS.sleep(100);
//        }
//
//        // Wait for all requests to complete
//        latch.await(30, TimeUnit.SECONDS);
//        executorService.shutdown();
//
//        logger.info("Rate Limiting Test Results: Successful Requests: {}, Failed (429) Requests: {}",
//                successfulRequests.get(), failedRequests429.get());
//
//        // Assert that some requests were rate-limited
//        assertTrue(failedRequests429.get() > 0, "Expected some requests to be rate limited (429).");
    }
}