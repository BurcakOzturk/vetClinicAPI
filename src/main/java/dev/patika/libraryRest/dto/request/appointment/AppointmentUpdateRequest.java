package dev.patika.libraryRest.dto.request.appointment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentUpdateRequest {
    @NotNull(message = "Appointment id cannot be null.")
    @Positive(message = "Appointment id must be positive.")
    private Long id;
    @NotNull(message = "Appointment Date cannot be empty")
    private LocalDateTime appointmentDateTime;
    @NotNull(message = "Animal id cannot be empty.")
    @NotNull(message = "Animal id cannot be null.")
    @Positive(message = "Animal id must be positive.")
    private Long animalId;
    @NotNull(message = "Animal id cannot be empty.")
    @NotNull(message = "Animal id cannot be null.")
    @Positive(message = "Animal id must be positive.")
    private Long doctorId;
}
