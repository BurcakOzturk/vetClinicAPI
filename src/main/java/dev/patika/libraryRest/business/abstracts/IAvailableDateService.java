package dev.patika.libraryRest.business.abstracts;

import dev.patika.libraryRest.core.result.Result;
import dev.patika.libraryRest.core.result.ResultData;
import dev.patika.libraryRest.dto.request.availableDate.AvailableDateSaveRequest;
import dev.patika.libraryRest.dto.request.availableDate.AvailableDateUpdateRequest;
import dev.patika.libraryRest.dto.response.availableDate.AvailableDateResponse;
import dev.patika.libraryRest.entities.AvailableDate;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IAvailableDateService {
    ResultData<AvailableDateResponse> saveAvailableDate(AvailableDateSaveRequest availableDateSaveRequest);

    ResultData<AvailableDateResponse> updateAvailableDate(AvailableDateUpdateRequest availableDateUpdateRequest);

    ResultData<List<AvailableDateResponse>> findAllAvailableDates();

    ResultData<AvailableDateResponse> findAvailableDateById(Long id);

    Result deleteAvailableDate(Long id);

}

