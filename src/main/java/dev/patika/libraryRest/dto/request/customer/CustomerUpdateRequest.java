package dev.patika.libraryRest.dto.request.customer;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUpdateRequest {
    @Positive(message = "ID değeri pozitif sayı olmak zorunda")
    private Long id;

    @NotNull(message = "Kategori ismi boş veya null olamaz")
    private String name;

    @NotNull
    private String address;

    @NotNull
    private String phone;

    private String mail;
    private String city;
}
