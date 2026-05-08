package cmpe172.salonbooking.service;

import cmpe172.salonbooking.model.Provider;
import cmpe172.salonbooking.model.SalonService;
import cmpe172.salonbooking.repository.ProviderRepository;
import cmpe172.salonbooking.repository.ServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderService {

    private final ProviderRepository repo;
    private final ServiceRepository salonServiceRepo;

    public ProviderService(ProviderRepository repo,  ServiceRepository salonServiceRepo) {
        this.repo = repo;
        this.salonServiceRepo = salonServiceRepo;
    }

    public List<Provider> getAllProviders() {
        return repo.findAll();
    }

    public List<Provider> getProvidersByService(Long serviceID) {
        SalonService salonService = salonServiceRepo.findById(serviceID)
                .orElseThrow(() -> new RuntimeException("Service not found"));
        return repo.findBySpecialty(salonService.getCategory());
    }

    public Provider getProviderById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Provider not found"));
    }
}