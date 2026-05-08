package cmpe172.salonbooking.dto;

public class AppointmentRequestDTO {
    private String date;
    private String time;

    private Long serviceID;
    private Long providerID;

    private String clientName;
    private String email;
    private String phone;

    public AppointmentRequestDTO() {

    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public Long getServiceID() {
        return serviceID;
    }
    public void setServiceID(Long serviceID) {
        this.serviceID = serviceID;
    }

    public Long getProviderID() {
        return providerID;
    }
    public void setProviderID(Long providerID) {
        this.providerID = providerID;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}