package cmpe172.salonbooking.mapper;

import cmpe172.salonbooking.dto.AppointmentResponseDTO;
import cmpe172.salonbooking.model.Appointment;

public class AppointmentMapper {

    public static AppointmentResponseDTO toDTO(Appointment appt) {

        AppointmentResponseDTO dto = new AppointmentResponseDTO();

        dto.setApptID(appt.getApptID());
        dto.setStartTime(appt.getStartTime().toString());
        dto.setEndTime(appt.getEndTime().toString());
        dto.setStatus(appt.getStatus());

        // only if using relationships
        if (appt.getClient() != null) {
            dto.setClientName(appt.getClient().getName());
        }

        if (appt.getProvider() != null) {
            dto.setProviderName(appt.getProvider().getName());
        }

        if (appt.getSalonService() != null) {
            dto.setServiceName(appt.getSalonService().getName());
        }

        return dto;
    }
}