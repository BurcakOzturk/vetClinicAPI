package dev.patika.libraryRest.business.abstracts;

import dev.patika.libraryRest.core.result.Result;
import dev.patika.libraryRest.core.result.ResultData;
import dev.patika.libraryRest.dto.request.doctor.DoctorSaveRequest;
import dev.patika.libraryRest.dto.request.doctor.DoctorUpdateRequest;
import dev.patika.libraryRest.dto.response.doctor.DoctorResponse;
import dev.patika.libraryRest.entities.Doctor;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IDoctorService {
    ResultData<DoctorResponse> saveDoctor(DoctorSaveRequest doctorSaveRequest);

    ResultData<DoctorResponse> updateDoctor(DoctorUpdateRequest doctorUpdateRequest);

    ResultData<DoctorResponse> findDoctorById(Long id);

    Result deleteDoctor(Long id);

    ResultData<List<DoctorResponse>> findAllDoctors();


}

