package dev.patika.libraryRest.dto.request.animal;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AnimalSaveRequest {

    @NotNull
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
