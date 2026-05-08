package cmpe172.salonbooking.model;

import jakarta.persistence.*;

import java.util.Optional;

@Entity
@Table(name = "services")
public class SalonService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String description;
    private int duration;
    private String category;

    public SalonService(Long id, String name, String description, int duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
    }

    public SalonService() {}
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

}
