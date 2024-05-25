package dev.patika.libraryRest.dto.request.vaccine;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VaccineSaveRequest {

    @NotNull(message = "Aşı Adı Boş Olamaz")
    private String name;

    @NotNull
    private String code;

    @NotNull
    private LocalDate protectionStartDate;

    @NotNull
    private LocalDate protectionFinishDate;

    private Long animalId;


}
