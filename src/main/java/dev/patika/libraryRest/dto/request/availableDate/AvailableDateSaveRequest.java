package dev.patika.libraryRest.dto.request.availableDate;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AvailableDateSaveRequest {

    @NotNull
    private LocalDate availableDate;

    @NotNull
    private Long doctorId;

}
