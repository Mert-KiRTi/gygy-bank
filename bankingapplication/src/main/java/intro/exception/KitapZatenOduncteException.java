package intro.exception;

/**
 * Ödünçte olan bir kitabı tekrar ödünç almaya çalışıldığında fırlatılan exception.
 */
public class KitapZatenOduncteException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public KitapZatenOduncteException(String message) {
        super(message);
    }

    public KitapZatenOduncteException(String message, Throwable cause) {
        super(message, cause);
    }
}
