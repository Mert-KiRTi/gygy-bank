package intro.exception;

/**
 * Kütüphane sistemi içindeki tüm iş mantığı hataları için temel exception sınıfı.
 * RuntimeException'dan miras alarak checked exception zorunluluğunu ortadan kaldırır.
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }
}
