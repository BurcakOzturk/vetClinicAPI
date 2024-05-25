package dev.patika.libraryRest.business.concretes;

import dev.patika.libraryRest.business.abstracts.IAppointmentService;
import dev.patika.libraryRest.core.result.Result;
import dev.patika.libraryRest.core.result.ResultData;
import dev.patika.libraryRest.core.utilities.ResultInfo;
import dev.patika.libraryRest.dao.AppointmentRepo;
import dev.patika.libraryRest.dto.converter.AppointmentConverter;
import dev.patika.libraryRest.dto.request.appointment.AppointmentSaveRequest;
import dev.patika.libraryRest.dto.request.appointment.AppointmentUpdateRequest;
import dev.patika.libraryRest.dto.response.appointment.AppointmentResponse;
import dev.patika.libraryRest.entities.Appointment;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentManager implements IAppointmentService {
    private final AppointmentConverter converterAppointment;
    private final AppointmentRepo appointmentRepository;
    private final AnimalManager animalManager;
    private final DoctorManager doctorManager;
    private final AvailableDateManager availableDateManager;

    public AppointmentManager(AppointmentConverter converter,
                              AppointmentRepo appointmentRepository,
                              AnimalManager animalManager,
                              DoctorManager doctorManager,
                              AvailableDateManager availableDateManager) {
        this.converterAppointment = converter;
        this.appointmentRepository = appointmentRepository;
        this.animalManager = animalManager;
        this.doctorManager = doctorManager;
        this.availableDateManager = availableDateManager;
    }

    @Override
    public ResultData<AppointmentResponse> saveAppointment(AppointmentSaveRequest appointmentSaveRequest) {
        this.animalManager.findAnimal(appointmentSaveRequest.getAnimalId());
        this.doctorManager.findDoctorById(appointmentSaveRequest.getDoctorId());
        LocalDate availableDate = appointmentSaveRequest.getAppointmentDateTime().toLocalDate();
        this.availableDateManager.availableDoctor(appointmentSaveRequest.getDoctorId(), availableDate);

        this.appointmentExists(appointmentSaveRequest.getDoctorId(), appointmentSaveRequest.getAppointmentDateTime());

        Appointment saveAppointment = this.converterAppointment.saveAppointment(appointmentSaveRequest);
        this.appointmentRepository.save(saveAppointment);
        return ResultInfo.created(this.converterAppointment.toAppointmentResponse(saveAppointment));
    }

    @Override
    public ResultData<AppointmentResponse> updateAppointment(AppointmentUpdateRequest appointmentUpdateRequest) {
        this.findAppointment(appointmentUpdateRequest.getId());
        this.animalManager.findAnimal(appointmentUpdateRequest.getAnimalId());
        this.doctorManager.findDoctorById(appointmentUpdateRequest.getDoctorId());
        LocalDate availableDate = appointmentUpdateRequest.getAppointmentDateTime().toLocalDate();
        this.availableDateManager.availableDoctor(appointmentUpdateRequest.getDoctorId(), availableDate);
        Appointment saveAppointment = this.converterAppointment.updateAppointment(appointmentUpdateRequest);
        this.appointmentRepository.save(saveAppointment);
        return ResultInfo.success(this.converterAppointment.toAppointmentResponse(saveAppointment));
    }

    void appointmentExists(Long doctorId, LocalDateTime appointmentDateTime) {
        LocalDateTime startDateTime = appointmentDateTime.withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endDateTime = startDateTime.plusHours(1); // Appointment duration assumed to be 1 hour

        boolean doctorAvailable = this.appointmentRepository
                .existsByDoctorIdAndAppointmentDateTimeBetween(doctorId, startDateTime, endDateTime);

        if (doctorAvailable) {
            throw new IllegalArgumentException("The doctor already has an appointment within the selected time range.");
        }
    }

    public Appointment findAppointment(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with ID: " + id));
    }

    @Override
    public ResultData<AppointmentResponse> findAppointmentById(Long id) {
        Appointment appointment = this.findAppointment(id);
        return ResultInfo.success(this.converterAppointment.toAppointmentResponse(appointment));
    }

    @Override
    public ResultData<List<AppointmentResponse>> findAllAppointments() {
        List<Appointment> allAppointments = this.appointmentRepository.findAll();
        List<AppointmentResponse> appointmentResponses = allAppointments.stream()
                .map(this.converterAppointment::toAppointmentResponse)
                .collect(Collectors.toList());
        return ResultInfo.success(appointmentResponses);
    }


    @Override
    public Result deleteAppointment(Long id) {
        Appointment appointment = this.findAppointment(id);
        this.appointmentRepository.delete(appointment);
        return ResultInfo.ok();
    }

    @Override
    public ResultData<List<AppointmentResponse>> getAppointmentsByDoctorAndDateRange(
            Long doctorId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Long doctor = this.doctorManager.findDoctorId(doctorId).getId();
        List<Appointment> appointments = appointmentRepository
                .findByDoctorIdAndAppointmentDateTimeBetween(doctor, startDateTime, endDateTime);

        List<AppointmentResponse> appointmentResponses = converterAppointment.toAppointmentResponseList(appointments);

        return ResultInfo.success(appointmentResponses);
    }

    @Override
    public ResultData<List<AppointmentResponse>> getAppointmentsByDateAndAnimal(
            LocalDateTime startDateTime, LocalDateTime endDateTime, Long animalId) {
        this.animalManager.findAnimalById(animalId);
        List<Appointment> appointments = appointmentRepository.findByAnimalIdAndAppointmentDateTimeBetween(animalId, startDateTime, endDateTime);

        List<AppointmentResponse> appointmentResponses = converterAppointment.toAppointmentResponseList(appointments);

        return ResultInfo.success(appointmentResponses);
    }
}
