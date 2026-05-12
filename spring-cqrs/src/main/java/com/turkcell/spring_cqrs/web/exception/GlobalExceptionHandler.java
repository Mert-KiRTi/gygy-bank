package com.turkcell.spring_cqrs.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.turkcell.spring_cqrs.core.security.exception.AuthenticatedException;
import com.turkcell.spring_cqrs.core.security.exception.AuthorizationException;
import com.turkcell.spring_cqrs.web.response.ErrorResponse;

/**
 * Global exception handler. Tüm controller'lardan fırlatılan exception'ları yakalar ve
 * uygun HTTP status kodları ile beraber JSON response döner.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * AuthenticatedException'ı yakalar ve HTTP 401 (Unauthorized) döner.
     * Kullanıcı token olmadan veya geçersiz token ile istek yaptığında tetiklenir.
     */
    @ExceptionHandler(AuthenticatedException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticatedException(AuthenticatedException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.UNAUTHORIZED.value(),
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
    
    /**
     * AuthorizationException'ı yakalar ve HTTP 403 (Forbidden) döner.
     * Kullanıcı token'ı olup ama gerekli rollere sahip olmadığında tetiklenir.
     */
    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ErrorResponse> handleAuthorizationException(AuthorizationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.FORBIDDEN.value(),
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }
    
    /**
     * Genel RuntimeException'ı yakalar (isteğe bağlı olarak diğer hataları da işleyebilir).
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Bir hata meydana geldi: " + ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
