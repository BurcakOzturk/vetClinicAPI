package dev.patika.libraryRest.api;


import dev.patika.libraryRest.business.abstracts.IAppointmentService;
import dev.patika.libraryRest.core.result.Result;
import dev.patika.libraryRest.core.result.ResultData;
import dev.patika.libraryRest.core.utilities.ResultHelper;
import dev.patika.libraryRest.dto.request.appointment.AppointmentSaveRequest;
import dev.patika.libraryRest.dto.request.appointment.AppointmentUpdateRequest;
import dev.patika.libraryRest.dto.response.CursorResponse;
import dev.patika.libraryRest.dto.response.appointment.AppointmentResponse;
import dev.patika.libraryRest.entities.Appointment;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/v1/appointment")
public class AppointmentController {
    private final IAppointmentService appointmentService;

    public AppointmentController(IAppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AppointmentResponse> saveAppointment(
            @Valid @RequestBody AppointmentSaveRequest appointmentSaveRequest) {
        return this.appointmentService.saveAppointment(appointmentSaveRequest);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse> findAppointmentById(@PathVariable("id") Long id) {
        return this.appointmentService.findAppointmentById(id);
    }

    @GetMapping()
    public ResultData<List<AppointmentResponse>> findAllAppointments() {
        return this.appointmentService.findAllAppointments();
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse> update(@Valid @RequestBody AppointmentUpdateRequest appointmentUpdateRequest) {
        return this.appointmentService.updateAppointment(appointmentUpdateRequest);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result deleteAppointment(@PathVariable("id") Long id) {
        return this.appointmentService.deleteAppointment(id);
    }

    @GetMapping("/appointmentsByDateAndDoctor")
    public ResultData<List<AppointmentResponse>> getAppointmentsByDoctorAndDateRange(
            @RequestParam Long doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
    ) {
        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(LocalTime.MAX);

        return appointmentService.getAppointmentsByDoctorAndDateRange(doctorId, startDateTime, endDateTime);
    }

    @GetMapping("/appointmentsByDateAndAnimal")
    public ResultData<List<AppointmentResponse>> getAppointmentsByDateAndAnimal(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            @RequestParam Long animalId
    ) {
        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(LocalTime.MAX);

        return appointmentService.getAppointmentsByDateAndAnimal(startDateTime, endDateTime, animalId);
    }
}

