package cmpe172.salonbooking.dto;

public class NotificationRequestDTO {

    private String email;
    private String message;

    public NotificationRequestDTO() {}

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}