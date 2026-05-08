package cmpe172.salonbooking.repository;

import cmpe172.salonbooking.model.SalonService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceRepository extends JpaRepository<SalonService,Long> {
    Optional<SalonService> findById(Long serviceID);
}
