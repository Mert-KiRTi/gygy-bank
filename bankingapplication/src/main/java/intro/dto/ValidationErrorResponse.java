package intro.dto;

import java.util.List;

/**
 * DTO Validasyondan kaynaklanan hataları döndürmek için kullanılan response sınıfı.
 * Hatalı alan bilgisi ve ilgili hata mesajlarını içerir.
 */
public class ValidationErrorResponse {

    private String argument;
    private List<String> messages;

    // Default Constructor
    public ValidationErrorResponse() {
    }

    // Parametreli Constructor
    public ValidationErrorResponse(String argument, List<String> messages) {
        this.argument = argument;
        this.messages = messages;
    }

    // Getter metodları
    public String getArgument() {
        return argument;
    }

    public List<String> getMessages() {
        return messages;
    }

    // Setter metodları
    public void setArgument(String argument) {
        this.argument = argument;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    // toString metodu
    @Override
    public String toString() {
        return "ValidationErrorResponse{" +
                "argument='" + argument + '\'' +
                ", messages=" + messages +
                '}';
    }
}
