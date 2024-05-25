package dev.patika.libraryRest.business.abstracts;

import dev.patika.libraryRest.core.result.Result;
import dev.patika.libraryRest.core.result.ResultData;
import dev.patika.libraryRest.dto.request.vaccine.VaccineSaveRequest;
import dev.patika.libraryRest.dto.request.vaccine.VaccineUpdateRequest;
import dev.patika.libraryRest.dto.response.vaccine.VaccineResponse;
import dev.patika.libraryRest.entities.Vaccine;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface IVaccineService {

    ResultData<VaccineResponse> saveVaccine(VaccineSaveRequest vaccineSaveRequest);

    ResultData<VaccineResponse> updateVaccine(VaccineUpdateRequest vaccineUpdateRequest);

    ResultData<VaccineResponse> findVaccineById(Long id);

    ResultData<List<VaccineResponse>> findAllVaccines();

    Result deleteVaccine(Long id);

    ResultData<List<VaccineResponse>> findByAnimalId(Long animalId);

    ResultData<List<VaccineResponse>> findExpiringVaccines(LocalDate startDate, LocalDate endDate);
}

