package dev.patika.libraryRest.business.concretes;

import dev.patika.libraryRest.business.abstracts.IDoctorService;
import dev.patika.libraryRest.core.result.Result;
import dev.patika.libraryRest.core.result.ResultData;
import dev.patika.libraryRest.core.utilities.ResultInfo;
import dev.patika.libraryRest.dao.DoctorRepo;
import dev.patika.libraryRest.dto.converter.DoctorConverter;
import dev.patika.libraryRest.dto.request.doctor.DoctorSaveRequest;
import dev.patika.libraryRest.dto.request.doctor.DoctorUpdateRequest;
import dev.patika.libraryRest.dto.response.doctor.DoctorResponse;
import dev.patika.libraryRest.entities.Doctor;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorManager implements IDoctorService {

    private final DoctorConverter doctorConverter;
    private final DoctorRepo doctorRepo;

    public DoctorManager(DoctorConverter converter, DoctorRepo doctorRepo) {
        this.doctorConverter = converter;
        this.doctorRepo = doctorRepo;
    }

    // Saves a new doctor.
    @Override
    public ResultData<DoctorResponse> saveDoctor(DoctorSaveRequest doctorSaveRequest) {
        Doctor saveDoctor = this.doctorConverter.convertToDoctor(doctorSaveRequest);

        checkDoctorExistence(saveDoctor);

        this.doctorRepo.save(saveDoctor);

        return ResultInfo.created(this.doctorConverter.toDoctorResponse(saveDoctor));
    }

    @Override
    public ResultData<DoctorResponse> updateDoctor(DoctorUpdateRequest doctorUpdateRequest) {
        findDoctorById(doctorUpdateRequest.getId());

        Doctor updatedDoctor = this.doctorConverter.convertToupdateDoctor(doctorUpdateRequest);

        updatedDoctor.setId(doctorUpdateRequest.getId());

        this.doctorRepo.save(updatedDoctor);

        return ResultInfo.success(this.doctorConverter.toDoctorResponse(updatedDoctor));
    }

    @Override
    public ResultData<DoctorResponse> findDoctorById(Long id) {
        Doctor doctor = this.doctorRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doctor with ID " + id + " not found."));

        return ResultInfo.success(this.doctorConverter.toDoctorResponse(doctor));
    }

    @Override
    public Result deleteDoctor(Long id) {
        Doctor doctor = this.doctorRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doctor with ID " + id + " not found."));

        this.doctorRepo.delete(doctor);

        return ResultInfo.ok();
    }

    @Override
    public ResultData<List<DoctorResponse>> findAllDoctors() {
        List<Doctor> allDoctors = this.doctorRepo.findAll();

        List<DoctorResponse> doctorResponses = allDoctors.stream()
                .map(this.doctorConverter::toDoctorResponse)
                .collect(Collectors.toList());

        return ResultInfo.success(doctorResponses);
    }

    public Doctor findDoctorId(Long id) {
        return this.doctorRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doctor with ID " + id + " not found."));
    }

    // Checks if a doctor already exists based on their email and phone.
    private void checkDoctorExistence(Doctor doctor) {
        if (doctorRepo.findByMail(doctor.getMail()).isPresent()) {
            throw new IllegalArgumentException("Email address " + doctor.getMail() + " is already registered.");
        }
        if (doctorRepo.findByPhone(doctor.getPhone()).isPresent()) {
            throw new IllegalArgumentException("Phone " + doctor.getPhone() + " is already registered.");
        }
    }
}
