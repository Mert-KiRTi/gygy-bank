package com.turkcell.library_cqrs.library_cqrs.core.mediator.pipeline;

import org.springframework.stereotype.Component;

/**
 * Pipeline behavior that logs incoming requests and outgoing responses.
 * Provides detailed logging of request/response data for debugging and auditing purposes.
 *
 * This behavior logs:
 * - Request details (class name and data) before execution
 * - Response details (class name and data) after execution
 *
 * @param <R> The type of the response/result
 */
@Component
public class LoggingBehavior<R> implements PipelineBehavior<R> {

    @Override
    public R handle(Object request, RequestHandlerDelegate<R> next) {
        // Log before request execution
        String requestClassName = request.getClass().getSimpleName();
        String requestData = request.toString();

        System.out.println(String.format(
                "[LOG - REQUEST] İşlem başladı: %s, Veri: %s",
                requestClassName,
                requestData
        ));

        // Execute the next handler in the pipeline
        R response = next.handle();

        // Log after request execution
        String responseClassName = response != null ? response.getClass().getSimpleName() : "null";
        String responseData = response != null ? response.toString() : "null";

        System.out.println(String.format(
                "[LOG - RESPONSE] İşlem bitti: %s, Dönen Cevap: %s",
                requestClassName,
                responseData
        ));

        return response;
    }
}
