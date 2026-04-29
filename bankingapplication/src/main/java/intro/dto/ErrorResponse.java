package intro.dto;

/**
 * API'dan döndürülen standart hata yanıtı.
 * Tüm hataların tutarlı bir format içinde geri dönmesini sağlar.
 */
public class ErrorResponse {

    private String title;
    private String type;
    private String message;

    // Default Constructor
    public ErrorResponse() {
    }

    // Parametreli Constructor
    public ErrorResponse(String title, String type, String message) {
        this.title = title;
        this.type = type;
        this.message = message;
    }

    // Getter metodları
    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    // Setter metodları
    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // toString metodu
    @Override
    public String toString() {
        return "ErrorResponse{" +
                "title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
