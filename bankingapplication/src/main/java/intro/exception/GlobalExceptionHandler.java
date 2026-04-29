package intro.exception;

import intro.dto.ErrorResponse;
import intro.dto.ValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * Merkezi hata yönetimi sınıfı.
 * Tüm controller'lardan fırlatılan exception'ları yakalar ve
 * standardize edilmiş hata yanıtları döndürür.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * BusinessException ve alt sınıflarını yakalayıp HTTP 400 ile döner.
     * 
     * @param exception fırlatılan BusinessException
     * @return ErrorResponse içeren ResponseEntity
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                "İş Mantığı Hatası",
                exception.getClass().getSimpleName(),
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * DTO validasyonundan geçemeyen hataları yakalayıp döner.
     * Hatalı alanları ve ilgili hata mesajlarını ayıklayarak ValidationErrorResponse içine doldurur.
     * 
     * @param exception fırlatılan MethodArgumentNotValidException
     * @return ValidationErrorResponse listesi içeren ResponseEntity
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationErrorResponse>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception) {
        
        List<ValidationErrorResponse> errorResponses = new ArrayList<>();
        
        // Binding result'tan tüm field error'ları al
        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            List<String> messages = new ArrayList<>();
            messages.add(fieldError.getDefaultMessage());
            
            ValidationErrorResponse validationError = new ValidationErrorResponse(
                    fieldError.getField(),
                    messages
            );
            errorResponses.add(validationError);
        });
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponses);
    }

    /**
     * Beklenmeyen genel exception'ları yakalayıp HTTP 500 ile döner.
     * 
     * @param exception fırlatılan Exception
     * @return ErrorResponse içeren ResponseEntity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Sunucu Hatası",
                "INTERNAL_SERVER_ERROR",
                "Beklenmeyen bir hata oluştu. Lütfen daha sonra tekrar deneyiniz."
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
