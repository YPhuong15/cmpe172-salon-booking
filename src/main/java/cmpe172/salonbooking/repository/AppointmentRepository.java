
package cmpe172.salonbooking.repository;

import cmpe172.salonbooking.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByProvider_Id(Long providerID);

    List<Appointment> findByProvider_IdAndStartTimeBetween(
            Long providerID,
            LocalDateTime start,
            LocalDateTime end
    );

    // 3️⃣ (Optional) Check if a slot is already booked
    boolean existsByProvider_IdAndStartTime(
            Long providerID,
            LocalDateTime startTime
    );
    @Query("""
    SELECT a FROM Appointment a
    WHERE a.provider.id = :providerId
    AND a.startTime < :endTime
    AND a.endTime > :startTime
    """)
        List<Appointment> findOverlappingAppointments(
                @Param("providerId") Long providerID,
                @Param("startTime") LocalDateTime startTime,
                @Param("endTime") LocalDateTime endTime
    );

    // Getting appointments by status
    List<Appointment> findByStatus(String status);

    // Getting appointment by client email
    List<Appointment> findByClientEmail(String clientEmail);
}