package dev.patika.libraryRest.business.abstracts;

import dev.patika.libraryRest.core.result.Result;
import dev.patika.libraryRest.core.result.ResultData;
import dev.patika.libraryRest.dto.request.appointment.AppointmentSaveRequest;
import dev.patika.libraryRest.dto.request.appointment.AppointmentUpdateRequest;
import dev.patika.libraryRest.dto.response.appointment.AppointmentResponse;
import dev.patika.libraryRest.entities.Appointment;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentService {
 ResultData<AppointmentResponse> saveAppointment(AppointmentSaveRequest appointmentSaveRequest);

    ResultData<AppointmentResponse> updateAppointment(AppointmentUpdateRequest appointmentUpdateRequest);

    ResultData<AppointmentResponse> findAppointmentById(Long id);

    ResultData<List<AppointmentResponse>> findAllAppointments();

    ResultData<List<AppointmentResponse>> getAppointmentsByDoctorAndDateRange(Long doctorId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    ResultData<List<AppointmentResponse>> getAppointmentsByDateAndAnimal(LocalDateTime startDateTime, LocalDateTime endDateTime, Long animalId);

    Result deleteAppointment(Long id);
}

