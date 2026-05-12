package com.turkcell.spring_cqrs.core.security.exception;

/**
 * Kullanıcı giriş yapmadığında veya JWT token geçersiz olduğunda fırlatılan exception.
 * HTTP 401 (Unauthorized) yanıtı ile eşleşir.
 */
public class AuthenticatedException extends RuntimeException {
    
    public AuthenticatedException(String message) {
        super(message);
    }
    
    public AuthenticatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
