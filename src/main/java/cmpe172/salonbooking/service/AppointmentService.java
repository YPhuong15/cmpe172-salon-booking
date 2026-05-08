package cmpe172.salonbooking.service;

import cmpe172.salonbooking.dto.AppointmentRequestDTO;
import cmpe172.salonbooking.dto.AppointmentResponseDTO;
import cmpe172.salonbooking.dto.NotificationRequestDTO;
import cmpe172.salonbooking.mapper.AppointmentMapper;
import cmpe172.salonbooking.model.*;
import cmpe172.salonbooking.repository.AppointmentRepository;
import cmpe172.salonbooking.repository.ProviderRepository;
import cmpe172.salonbooking.repository.ServiceRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository repo;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ServiceRepository serviceRepo;

    @Autowired
    private ProviderRepository providerRepo;

   @Autowired
   private RestTemplate restTemplate;

   private static final Logger log = LoggerFactory.getLogger(AppointmentService.class);

    public Appointment bookAppointment(AppointmentRequestDTO appDto) {

        log.info("Booking request: client={}, provider={}, service={}, time={} {}",
                appDto.getClientName(),
                appDto.getProviderID(),
                appDto.getServiceID(),
                appDto.getDate(),
                appDto.getTime());

        int maxRetries = 3;

        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                Appointment appointment = bookingTransactional(appDto);
                log.info("Booking successful on attempt={}", attempt);
                return appointment;
            } catch (ObjectOptimisticLockingFailureException ex) {
                if (attempt == maxRetries) {
                    log.error("Booking failed on attempt={}", attempt);
                    throw new RuntimeException("Failed to book. Try again!");
                }
            }
        }
        throw new RuntimeException("Failed to book. Try again!");
    }
        @Transactional(isolation = Isolation.REPEATABLE_READ)
        public Appointment bookingTransactional (AppointmentRequestDTO appDto){

            Client client = clientService.findOrCreateClient(
                    appDto.getClientName(),
                    appDto.getEmail(),
                    appDto.getPhone()
            );

            Provider provider = providerRepo.findById(appDto.getProviderID())
                    .orElseThrow(() -> new RuntimeException("Provider does not exist"));

            SalonService salonService = serviceRepo.findById(appDto.getServiceID())
                    .orElseThrow(() -> new RuntimeException("Service not found"));

            LocalDateTime start =
                    LocalDateTime.parse(appDto.getDate() + "T" + appDto.getTime());

            LocalDateTime end = start.plusMinutes(salonService.getDuration());

            log.info("Checking availability for provider {} between {} and {}", provider.getName(), start, end);

            List<Appointment> conflicts = repo.findOverlappingAppointments(
                    provider.getId(),
                    start,
                    end
            );

            if (!conflicts.isEmpty()) {
                log.warn("Attempt to book unavailable time for provider {} at {}", provider.getId(), start);
                throw new RuntimeException("Conflict detected!");
            }

        Appointment appt = new Appointment();
        appt.setClient(client);
        appt.setProvider(provider);
        appt.setSalonService(salonService);
        appt.setStartTime(start);
        appt.setEndTime(end);
        appt.setStatus("CONFIRMED");

        Appointment savedAppt = repo.save(appt);
        log.info("Appointment booked with provider={} at {}", provider.getName(), start);

        sendNotification(appDto);
        return savedAppt;
    }

    private void sendNotification(AppointmentRequestDTO appDto) {
        NotificationRequestDTO notify =  new NotificationRequestDTO();
        notify.setEmail(appDto.getEmail());
        notify.setMessage("Your appointment has been booked!");

        try {
            restTemplate.postForObject("http://localhost:8080/notification/send",
                    notify,
                    String.class
            );
            log.info("Sending notification to {}", notify.getEmail());
        } catch (Exception ex) {
            log.error("Notification failed to send to {}, but appointment is booked", notify.getEmail(), ex);
        }
    }

    public void cancelAppointment(Long appId) {
        log.info("Canceling appointment with id={}", appId);

        Appointment appt = repo.findById(appId)
                .orElseThrow(() -> new RuntimeException("Appointment not found!"));
        appt.setStatus("CANCELLED");
        repo.save(appt);

        log.info("Appointment id={} cancelled!", appId);
    }

    // Show availability
    public List<Appointment> findByStatus(String status) {
        return repo.findByStatus("CONFIRMED");
    }

    // Show appointments under a client
    public List<Appointment> findByClientEmail(String email) {
        return repo.findByClientEmail(email);
    }
    // See appointments history
    public List<AppointmentResponseDTO> getAllAppointments() {
        return repo.findAll().stream().map(AppointmentMapper::toDTO).toList();
    }

    public List<String> generateSlots(Long providerId, String dateStr) {

        LocalDate date = LocalDate.parse(dateStr);

        LocalDateTime start = date.atTime(9, 0);
        LocalDateTime end = date.atTime(18, 0);

        List<Appointment> appointments =
                repo.findByProvider_IdAndStartTimeBetween(
                        providerId,
                        start,
                        end
                );

        Set<LocalTime> bookedTimes = appointments.stream()
                .map(a -> a.getStartTime().toLocalTime())
                .collect(Collectors.toSet());

        List<String> slots = new ArrayList<>();

        while (start.isBefore(end)) {

            LocalTime time = start.toLocalTime();

            if (!bookedTimes.contains(time)) {
                slots.add(time.toString()); // "09:00"
            }

            start = start.plusMinutes(30);
        }

        return slots;
    }

    public Appointment getAppointmentById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
    }
}
