package dev.patika.libraryRest.dao;

import dev.patika.libraryRest.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Appointment a WHERE a.doctor.id = :doctorId AND a.appointmentDateTime = :appointmentDateTime")
    boolean existsByDoctorIdAndAppointmentDateTime(@Param("doctorId") Long doctorId, @Param("appointmentDateTime") LocalDateTime appointmentDateTime);

    boolean existsByDoctorIdAndAppointmentDateTimeBetween(Long doctorId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<Appointment> findByAnimalIdAndAppointmentDateTimeBetween(Long animalId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<Appointment> findByDoctorIdAndAppointmentDateTimeBetween(Long doctorId, LocalDateTime startDateTime, LocalDateTime endDateTime);}
