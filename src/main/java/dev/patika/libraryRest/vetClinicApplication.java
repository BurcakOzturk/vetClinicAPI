package dev.patika.libraryRest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Veterinary Clinic Management System",
        version = "1.0", description = "."))

public class vetClinicApplication {

    public static void main(String[] args) {
        SpringApplication.run(vetClinicApplication.class, args);
    }

}
