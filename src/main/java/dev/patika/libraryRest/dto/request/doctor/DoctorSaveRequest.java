package dev.patika.libraryRest.dto.request.doctor;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DoctorSaveRequest {

    @NotNull
    private String name;

    private String phone;

    @NotNull(message = "Doktor mail adresi boş veya null olamaz")
    private String mail;

    @NotNull(message = "Doktor adresi boş veya null olamaz")
    private String address;

    private String city;
}
