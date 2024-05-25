package dev.patika.libraryRest.api;


import dev.patika.libraryRest.business.abstracts.IAvailableDateService;
import dev.patika.libraryRest.core.result.Result;
import dev.patika.libraryRest.core.result.ResultData;
import dev.patika.libraryRest.core.utilities.ResultHelper;
import dev.patika.libraryRest.dto.request.availableDate.AvailableDateSaveRequest;
import dev.patika.libraryRest.dto.request.availableDate.AvailableDateUpdateRequest;
import dev.patika.libraryRest.dto.response.CursorResponse;
import dev.patika.libraryRest.dto.response.availableDate.AvailableDateResponse;
import dev.patika.libraryRest.entities.AvailableDate;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/availableDate")
public class AvailableDateController {
    private final IAvailableDateService availableDateService;

    public AvailableDateController(IAvailableDateService availableDateService) {
        this.availableDateService = availableDateService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AvailableDateResponse> saveAvailableDate(
            @Valid @RequestBody AvailableDateSaveRequest availableDateSaveRequest) {
        return this.availableDateService.saveAvailableDate(availableDateSaveRequest);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AvailableDateResponse> findAvailableDateById(@PathVariable("id") Long id) {
        return this.availableDateService.findAvailableDateById(id);
    }

    @GetMapping()
    public ResultData<List<AvailableDateResponse>> findAll() {
        return this.availableDateService.findAllAvailableDates();
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AvailableDateResponse> updateAvailableDate(
            @Valid @RequestBody AvailableDateUpdateRequest availableDateUpdateRequest) {
        return this.availableDateService.updateAvailableDate(availableDateUpdateRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result deleteAvailableDate(@PathVariable("id") Long id) {
        return this.availableDateService.deleteAvailableDate(id);
    }
}

