package com.turkcell.library_cqrs.library_cqrs.core.mediator.pipeline;

import org.springframework.stereotype.Component;

/**
 * Pipeline behavior that monitors and logs performance metrics of request handling.
 * Tracks the execution time of each request and warns if it exceeds the defined threshold.
 *
 * This behavior intercepts all requests in the pipeline and logs performance warnings
 * for slow operations (threshold: 3000ms).
 *
 * @param <R> The type of the response/result
 */
@Component
public class PerformanceMonitoringBehavior<R> implements PipelineBehavior<R> {

    private static final long PERFORMANCE_THRESHOLD_MS = 3000L;

    @Override
    public R handle(Object request, RequestHandlerDelegate<R> next) {
        // Record the start time
        long startTimeMs = System.currentTimeMillis();

        try {
            // Execute the next handler in the pipeline
            return next.handle();
        } finally {
            // Calculate elapsed time
            long endTimeMs = System.currentTimeMillis();
            long elapsedTimeMs = endTimeMs - startTimeMs;

            // Log warning if execution time exceeds threshold
            if (elapsedTimeMs > PERFORMANCE_THRESHOLD_MS) {
                String requestClassName = request.getClass().getSimpleName();
                System.out.println(String.format(
                        "[PERFORMANCE WARN] İstek: %s çok yavaş çalıştı! Süre: %d ms",
                        requestClassName,
                        elapsedTimeMs
                ));
            }
        }
    }
}
