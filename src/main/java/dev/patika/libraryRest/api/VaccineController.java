package dev.patika.libraryRest.api;

import dev.patika.libraryRest.business.abstracts.IVaccineService;
import dev.patika.libraryRest.core.result.Result;
import dev.patika.libraryRest.core.result.ResultData;
import dev.patika.libraryRest.core.utilities.ResultHelper;
import dev.patika.libraryRest.dto.request.vaccine.VaccineSaveRequest;
import dev.patika.libraryRest.dto.request.vaccine.VaccineUpdateRequest;
import dev.patika.libraryRest.dto.response.CursorResponse;
import dev.patika.libraryRest.dto.response.vaccine.VaccineResponse;
import dev.patika.libraryRest.entities.Vaccine;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/vaccine")
public class VaccineController {
    private final IVaccineService vaccineService;

    public VaccineController(IVaccineService vaccineService) {
        this.vaccineService = vaccineService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<VaccineResponse> saveVaccine(@Valid @RequestBody VaccineSaveRequest vaccineSaveRequest) {
        return this.vaccineService.saveVaccine(vaccineSaveRequest);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> updateVaccine(@Valid @RequestBody VaccineUpdateRequest vaccineUpdateRequest) {
        return this.vaccineService.updateVaccine(vaccineUpdateRequest);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> findVaccineById(@PathVariable("id") Long id) {
        return this.vaccineService.findVaccineById(id);
    }

    @GetMapping("/animal/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<VaccineResponse>> findByAnimalId(@PathVariable("id") Long animalId) {
        return this.vaccineService.findByAnimalId(animalId);
    }

    @GetMapping()
    public ResultData<List<VaccineResponse>> findAllVaccines() {
        return this.vaccineService.findAllVaccines();
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result deleteVaccine(@PathVariable("id") Long id) {
        return this.vaccineService.deleteVaccine(id);
    }

    @GetMapping("/expiring")
    public ResultData<List<VaccineResponse>> findExpiringVaccines(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return this.vaccineService.findExpiringVaccines(startDate, endDate);
    }
}
