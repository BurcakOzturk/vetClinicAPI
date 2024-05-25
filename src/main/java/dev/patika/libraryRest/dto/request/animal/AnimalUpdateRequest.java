package dev.patika.libraryRest.dto.request.animal;

import dev.patika.libraryRest.entities.Vaccine;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalUpdateRequest {
    @Positive(message = "ID değeri pozitif sayı olmak zorunda")
    private Long id;

    @NotNull(message = "Kategori ismi boş veya null olamaz")
    private String name;

    @NotNull
    private String species;

    @NotNull
    private String breed;

    @NotNull
    private String gender;

    @NotNull
    private String colour;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    private Long customerId;

}
