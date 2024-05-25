package dev.patika.libraryRest.dto.request.vaccine;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineUpdateRequest {
    @Positive(message = "ID değeri pozitif sayı olmak zorunda")
    private Long id;

    @NotNull(message = "Kategori ismi boş veya null olamaz")
    private String name;

    @NotNull
    private String code;

    @NotNull
    private LocalDate protectionStartDate;

    @NotNull
    private LocalDate protectionFinishDate;

    private Long animalId;


}
