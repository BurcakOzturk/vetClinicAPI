package dev.patika.libraryRest.business.concretes;

import dev.patika.libraryRest.business.abstracts.IVaccineService;
import dev.patika.libraryRest.core.result.Result;
import dev.patika.libraryRest.core.result.ResultData;
import dev.patika.libraryRest.core.utilities.ResultInfo;
import dev.patika.libraryRest.dao.VaccineRepo;
import dev.patika.libraryRest.dto.converter.AnimalConverter;
import dev.patika.libraryRest.dto.converter.VaccineConverter;
import dev.patika.libraryRest.dto.request.vaccine.VaccineSaveRequest;
import dev.patika.libraryRest.dto.request.vaccine.VaccineUpdateRequest;
import dev.patika.libraryRest.dto.response.vaccine.VaccineResponse;
import dev.patika.libraryRest.entities.Vaccine;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VaccineManager implements IVaccineService {

    private final VaccineRepo vaccineRepo;
    private final AnimalManager animalManager;
    private final VaccineConverter vaccineConverter;

    public VaccineManager(VaccineRepo vaccineRepo, AnimalManager animalManager, VaccineConverter vaccineConverter, AnimalConverter animalConverter) {
        this.vaccineRepo = vaccineRepo;
        this.animalManager = animalManager;
        this.vaccineConverter = vaccineConverter;
    }

    @Override
    public ResultData<VaccineResponse> saveVaccine(VaccineSaveRequest vaccineSaveRequest) {
        this.animalManager.findAnimalById(vaccineSaveRequest.getAnimalId());

        validateExistingVaccines(vaccineSaveRequest);

        Vaccine saveVaccine = this.vaccineConverter.convertToVaccine(vaccineSaveRequest);
        this.vaccineRepo.save(saveVaccine);

        return ResultInfo.created(this.vaccineConverter.toVaccineResponse(saveVaccine));
    }

    private void validateExistingVaccines(VaccineSaveRequest vaccineSaveRequest) {
        List<Vaccine> existingVaccines = vaccineRepo.findByNameAndCodeAndAnimalId(
                vaccineSaveRequest.getName(),
                vaccineSaveRequest.getCode(),
                vaccineSaveRequest.getAnimalId()
        );

        if (!vaccineSaveRequest.getProtectionStartDate().isBefore(vaccineSaveRequest.getProtectionFinishDate())) {
            throw new IllegalArgumentException("The protection start date must be before the protection finish date.");
        }

        for (Vaccine existingVaccine : existingVaccines) {
            if (!existingVaccine.getProtectionFinishDate().isBefore(vaccineSaveRequest.getProtectionFinishDate())) {
                throw new IllegalArgumentException("A vaccine with the same name, code, and animal ID already exists" +
                        " with a protection finish date in the future or overlapping the new vaccine's dates.");
            }
        }
    }

    // Updates an existing vaccine.
    @Override
    public ResultData<VaccineResponse> updateVaccine(VaccineUpdateRequest vaccineUpdateRequest) {
        this.findVaccineById(vaccineUpdateRequest.getId());

        this.animalManager.findAnimalById(vaccineUpdateRequest.getAnimalId());

        Vaccine updateVaccine = this.vaccineConverter.convertToUpdateVaccine(vaccineUpdateRequest);

        this.vaccineRepo.save(updateVaccine);

        return ResultInfo.success(this.vaccineConverter.toVaccineResponse(updateVaccine));
    }

    @Override
    public ResultData<VaccineResponse> findVaccineById(Long id) {
        Vaccine vaccine = vaccineRepo.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Vaccine with ID " + id + " not found."));

        return ResultInfo.success(this.vaccineConverter.toVaccineResponse(vaccine));
    }

    @Override
    public ResultData<List<VaccineResponse>> findAllVaccines() {
        List<Vaccine> allVaccines = this.vaccineRepo.findAll();

        List<VaccineResponse> vaccineResponses = allVaccines.stream()
                .map(this.vaccineConverter::toVaccineResponse)
                .collect(Collectors.toList());

        return ResultInfo.success(vaccineResponses);
    }

    @Override
    public Result deleteVaccine(Long id) {
        Vaccine vaccine = this.findVaccine(id);

        this.vaccineRepo.delete(vaccine);

        return ResultInfo.ok();
    }

    public Vaccine findVaccine(Long id){
        return this.vaccineRepo.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Vaccine with ID " + id + " not found."));
    }

    @Override
    public ResultData<List<VaccineResponse>> findByAnimalId(Long animalId) {
        this.animalManager.findAnimalById(animalId);

        List<Vaccine> vaccines = this.vaccineRepo.findByAnimalId(animalId);

        if (vaccines.isEmpty()) {
            throw new EntityNotFoundException("No vaccines found for the animal with ID " + animalId);
        }

        List<VaccineResponse> vaccineResponses = vaccines.stream()
                .map(this.vaccineConverter::toVaccineResponse)
                .collect(Collectors.toList());

        return ResultInfo.success(vaccineResponses);
    }

    @Override
    public ResultData<List<VaccineResponse>> findExpiringVaccines(LocalDate startDate, LocalDate endDate) {
        List<Vaccine> expiringVaccines = vaccineRepo.findByProtectionFinishDateBetween(startDate, endDate);

        List<VaccineResponse> vaccineResponses = expiringVaccines.stream()
                .map(this.vaccineConverter::toVaccineResponse)
                .collect(Collectors.toList());

        return ResultInfo.success(vaccineResponses);
    }
}
