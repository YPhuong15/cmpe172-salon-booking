package cmpe172.salonbooking.controller;

import cmpe172.salonbooking.dto.AppointmentRequestDTO;
import cmpe172.salonbooking.model.Appointment;
import cmpe172.salonbooking.service.SalonServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import cmpe172.salonbooking.service.AppointmentService;
import cmpe172.salonbooking.service.ProviderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AppointmentController {

    @Autowired
    private final AppointmentService appointmentService;
    private final ProviderService providerService;
    private final SalonServiceService salonService;

    public AppointmentController(AppointmentService appointmentService,
                                 ProviderService providerService,
                                 SalonServiceService salonService) {
        this.appointmentService = appointmentService;
        this.providerService = providerService;
        this.salonService = salonService;
    }


    @GetMapping("/appointments")
    public String page(Model model) {
        model.addAttribute("appointmentDTO", new AppointmentRequestDTO());
        model.addAttribute("services", salonService.getAllServices());
        model.addAttribute("providers", providerService.getAllProviders());

        return "appointments";
    }

    @GetMapping("/appointments/slots")
    @ResponseBody
    public List<String> getSlots(@RequestParam Long providerId, @RequestParam String date) {
        return appointmentService.generateSlots(providerId, date);
    }

    @PostMapping("/appointments/book")
    public String bookAppointment(@ModelAttribute("appointmentDto") AppointmentRequestDTO appDto) {
        Appointment appointment = appointmentService.bookAppointment(appDto);
        return "redirect:/appointments/confirmation/" + appointment.getApptID();
    }
    @GetMapping("/appointments/confirmation/{id}")
    public String confirmationPage(@PathVariable Long id, Model model) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        model.addAttribute("appointment", appointment);
        return "confirmation";
    }

    @GetMapping("/appointments/manage")
    public String manageAppointments(@RequestParam(required = false) String email,
                                     Model model) {

        model.addAttribute("email", email);

        if (email != null && !email.isBlank()) {
            List<Appointment> appointments =
                    appointmentService.findByClientEmail(email);

            model.addAttribute("appointments", appointments);
        }

        return "manage-appointments";
    }

    @PostMapping("/appointments/cancel/{id}")
    public String cancel(@PathVariable Long id, @RequestParam String email) {
        appointmentService.cancelAppointment(id);
        return "redirect:/appointments/manage?email=" + email;
    }
}
