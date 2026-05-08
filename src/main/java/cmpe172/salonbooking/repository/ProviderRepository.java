package cmpe172.salonbooking.repository;

import cmpe172.salonbooking.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProviderRepository extends JpaRepository<Provider,Long> {
    List<Provider> findBySpecialty(String specialty);

}
