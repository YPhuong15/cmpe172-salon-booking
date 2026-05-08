package cmpe172.salonbooking.dto;

import cmpe172.salonbooking.model.Appointment;

public class AppointmentResponseDTO {
    private Long apptID;

    private String clientName;
    private String providerName;
    private String serviceName;

    private String startTime;
    private String endTime;

    private String status;

    public AppointmentResponseDTO() {

    }

    public Long getApptID() {
        return apptID;
    }
    public void setApptID(Long apptID) {
        this.apptID = apptID;
    }

    public String getClientName() {
        return clientName;
    }
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getProviderName() {
        return providerName;
    }
    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
