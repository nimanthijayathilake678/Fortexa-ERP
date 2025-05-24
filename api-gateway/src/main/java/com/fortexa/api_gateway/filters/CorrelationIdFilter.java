package com.fortexa.api_gateway.filters;

import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * A filter that ensures every request has a unique Correlation ID for tracking purposes.
 * The Correlation ID is added to the request headers and the MDC (Mapped Diagnostic Context)
 * for logging and tracing across distributed systems.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorrelationIdFilter implements GlobalFilter {
    private static final String CORRELATION_ID_HEADER = "X-Correlation-ID";
    private static final String CORRELATION_ID_MDC_KEY = "correlation_id";

    /**
     * Filters the incoming request to ensure a Correlation ID is present.
     * If not present, a new Correlation ID is generated. The Correlation ID
     * is added to the request headers and the MDC for logging purposes.
     *
     * @param exchange the current server exchange
     * @param chain    the gateway filter chain
     * @return a {@link Mono<Void>} indicating when request processing is complete
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String correlationId = exchange.getRequest()
                .getHeaders()
                .getFirst(CORRELATION_ID_HEADER);

        if (correlationId == null || correlationId.isEmpty()) {
            correlationId = UUID.randomUUID().toString();
        }

        if (correlationId != null && !correlationId.isEmpty()) {
            MDC.put(CORRELATION_ID_MDC_KEY, correlationId);
        }

        String finalCorrelationId = correlationId;

        ServerWebExchange mutated = exchange.mutate()
                .request(r -> r.header(CORRELATION_ID_HEADER, finalCorrelationId))
                .build();

        return chain.filter(mutated)
                .doFinally(signal -> {
                    MDC.remove(CORRELATION_ID_MDC_KEY);
                });
    }
}