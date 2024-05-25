package dev.patika.libraryRest.dto.converter;

import dev.patika.libraryRest.dao.DoctorRepo;
import dev.patika.libraryRest.dto.request.availableDate.AvailableDateSaveRequest;
import dev.patika.libraryRest.dto.request.availableDate.AvailableDateUpdateRequest;
import dev.patika.libraryRest.dto.response.availableDate.AvailableDateResponse;
import dev.patika.libraryRest.entities.AvailableDate;
import dev.patika.libraryRest.entities.Doctor;
import org.springframework.stereotype.Component;

@Component
public class AvailableDateConverter {

    private final DoctorRepo doctorRepo;

    /**
     * Constructs a new AvailableDateConverter with the specified DoctorRepository.
     *
     * @param doctorRepo the repository for accessing Doctor entities
     */
    public AvailableDateConverter(DoctorRepo doctorRepo) {
        this.doctorRepo = doctorRepo;
    }

    /**
     * Converts an AvailableDateSaveRequest object to an AvailableDate entity.
     *
     * @param availableDateSaveRequest the request object containing data for creating a new AvailableDate
     * @return the created AvailableDate entity
     */
    public AvailableDate convertToAvailableDate(AvailableDateSaveRequest availableDateSaveRequest) {
        if (availableDateSaveRequest == null) {
            return null;
        }
        AvailableDate availableDate = new AvailableDate();
        availableDate.setAvailableDate(availableDateSaveRequest.getAvailableDate());
        Doctor doctor = doctorRepo.findById(availableDateSaveRequest.getDoctorId()).get();
        availableDate.setDoctor(doctor);
        return availableDate;
    }

    /**
     * Converts an AvailableDateUpdateRequest object to an AvailableDate entity.
     *
     * @param availableDateUpdateRequest the request object containing data for updating an existing AvailableDate
     * @return the updated AvailableDate entity
     */
    public AvailableDate convertToUpdateAvailableDate(AvailableDateUpdateRequest availableDateUpdateRequest) {
        if (availableDateUpdateRequest == null) {
            return null;
        }
        AvailableDate availableDate = new AvailableDate();
        availableDate.setId(availableDateUpdateRequest.getId());
        availableDate.setAvailableDate(availableDateUpdateRequest.getAvailableDate());
        Doctor doctor = doctorRepo.findById(availableDateUpdateRequest.getDoctorId()).get();
        availableDate.setDoctor(doctor);
        return availableDate;
    }

    /**
     * Converts an AvailableDate entity to an AvailableDateResponse object.
     *
     * @param availableDate the AvailableDate entity to convert
     * @return the AvailableDateResponse object representing the entity
     */
    public AvailableDateResponse toAvailableDateResponse(AvailableDate availableDate) {
        if (availableDate == null) {
            return null;
        }
        AvailableDateResponse response = new AvailableDateResponse();
        response.setId(availableDate.getId());
        response.setAvailableDate(availableDate.getAvailableDate());
        response.setDoctorId(availableDate.getDoctor().getId());
        return response;
    }
}
