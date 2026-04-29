package intro.exception;

/**
 * İstenen kitap kütüphanede bulunamadığında fırlatılan exception.
 */
public class KitapBulunamadiException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public KitapBulunamadiException(String message) {
        super(message);
    }

    public KitapBulunamadiException(String message, Throwable cause) {
        super(message, cause);
    }
}
