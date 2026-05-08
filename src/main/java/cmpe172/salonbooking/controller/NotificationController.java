package cmpe172.salonbooking.controller;

import cmpe172.salonbooking.dto.NotificationRequestDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @PostMapping("/send")
    public String sendNotification(@RequestBody NotificationRequestDTO dto) {

        System.out.println("📩 Notification SENT");
        System.out.println("To: " + dto.getEmail());
        System.out.println("Message: " + dto.getMessage());

        return "SENT";
    }
}