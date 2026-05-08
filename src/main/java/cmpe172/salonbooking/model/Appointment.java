package cmpe172.salonbooking.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long apptID;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private SalonService salonService;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;

    @Version
    private Long version;

    public Appointment() {}

    public Appointment(Client client, Provider provider,
                       LocalDateTime startTime, LocalDateTime endTime,
                       String status, SalonService salonService) {
        this.client = client;
        this.provider = provider;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.salonService = salonService;
    }

    // getters & setters

    public Long getApptID() {
        return apptID;
    }

    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }

    public Provider getProvider() {
        return provider;
    }
    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public SalonService getSalonService() {
        return salonService;
    }
    public void setSalonService(SalonService salonService) {
        this.salonService = salonService;
    }

}