package com.turkcell.spring_cqrs.core.security.exception;

/**
 * Kullanıcı giriş yapmış ancak istenen işlem için gerekli rollere/yetkilere sahip olmadığında fırlatılan exception.
 * HTTP 403 (Forbidden) yanıtı ile eşleşir.
 */
public class AuthorizationException extends RuntimeException {
    
    public AuthorizationException(String message) {
        super(message);
    }
    
    public AuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
