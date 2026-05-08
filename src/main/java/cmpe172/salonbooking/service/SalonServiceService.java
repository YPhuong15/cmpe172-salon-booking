package cmpe172.salonbooking.service;

import cmpe172.salonbooking.model.SalonService;
import cmpe172.salonbooking.repository.ServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalonServiceService {

    private final ServiceRepository repo;

    public SalonServiceService(ServiceRepository repo) {
        this.repo = repo;
    }

    public List<SalonService> getAllServices() {
        return repo.findAll();
    }

    public SalonService getServiceById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));
    }
}
