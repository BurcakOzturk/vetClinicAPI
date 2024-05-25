package dev.patika.libraryRest.dto.request.customer;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CustomerSaveRequest {

    @NotNull
    private String name;

    @NotNull
    private String address;

    @NotNull
    private String phone;

    private String mail;
    private String city;
}
