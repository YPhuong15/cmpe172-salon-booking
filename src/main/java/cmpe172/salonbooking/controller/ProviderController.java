package cmpe172.salonbooking.controller;

import cmpe172.salonbooking.model.Provider;
import cmpe172.salonbooking.service.ProviderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/providers")
public class ProviderController {

    private final ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @GetMapping
    public List<Provider> getAllProviders() {
        return providerService.getAllProviders();
    }

    @GetMapping("/by-service")
    public List<Provider> getProvidersByService(@RequestParam Long serviceID) {
        return providerService.getProvidersByService(serviceID);
    }

    @GetMapping("/{id}")
    public Provider getProvider(@PathVariable Long id) {
        return providerService.getProviderById(id);
    }
}