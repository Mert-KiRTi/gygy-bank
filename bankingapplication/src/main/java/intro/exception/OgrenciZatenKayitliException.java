package intro.exception;

/**
 * Aynı öğrenci bilgileriyle tekrar kayıt yapılmaya çalışıldığında fırlatılan exception.
 */
public class OgrenciZatenKayitliException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public OgrenciZatenKayitliException(String message) {
        super(message);
    }

    public OgrenciZatenKayitliException(String message, Throwable cause) {
        super(message, cause);
    }
}
