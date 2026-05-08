package cmpe172.salonbooking.controller;

import cmpe172.salonbooking.model.SalonService;
import cmpe172.salonbooking.repository.ServiceRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
public class ServiceController {

    private final ServiceRepository repo;

    public ServiceController(ServiceRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<SalonService> getAllServices() {
        return repo.findAll();
    }
}