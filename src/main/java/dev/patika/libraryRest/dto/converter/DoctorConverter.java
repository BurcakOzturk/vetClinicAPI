package dev.patika.libraryRest.dto.converter;

import dev.patika.libraryRest.dto.request.doctor.DoctorSaveRequest;
import dev.patika.libraryRest.dto.request.doctor.DoctorUpdateRequest;
import dev.patika.libraryRest.dto.response.doctor.DoctorResponse;
import dev.patika.libraryRest.entities.Doctor;
import org.springframework.stereotype.Component;

@Component
public class DoctorConverter {

    // Converts a DoctorSaveRequest object to a Doctor entity.
    public Doctor convertToDoctor(DoctorSaveRequest doctorSaveRequest) {
        if (doctorSaveRequest == null) {
            return null;
        }
        Doctor doctor = new Doctor();
        doctor.setName(doctorSaveRequest.getName());
        doctor.setPhone(doctorSaveRequest.getPhone());
        doctor.setMail(doctorSaveRequest.getMail());
        doctor.setAddress(doctorSaveRequest.getAddress());
        doctor.setCity(doctorSaveRequest.getCity());
        return doctor;
    }

    // Converts a DoctorUpdateRequest object to an updated Doctor entity.
    public Doctor convertToupdateDoctor(DoctorUpdateRequest doctorUpdateRequest) {
        if (doctorUpdateRequest == null) {
            return null;
        }
        Doctor doctor = new Doctor();
        doctor.setId(doctorUpdateRequest.getId());
        doctor.setName(doctorUpdateRequest.getName());
        doctor.setPhone(doctorUpdateRequest.getPhone());
        doctor.setMail(doctorUpdateRequest.getMail());
        doctor.setAddress(doctorUpdateRequest.getAddress());
        doctor.setCity(doctorUpdateRequest.getCity());
        return doctor;
    }

    // Converts a Doctor entity to a DoctorResponse object.
    public DoctorResponse toDoctorResponse(Doctor doctor) {
        if (doctor == null) {
            return null;
        }
        DoctorResponse response = new DoctorResponse();
        response.setId(doctor.getId());
        response.setName(doctor.getName());
        response.setPhone(doctor.getPhone());
        response.setMail(doctor.getMail());
        response.setAddress(doctor.getAddress());
        response.setCity(doctor.getCity());
        return response;
    }
}
