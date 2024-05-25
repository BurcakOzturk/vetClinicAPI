package dev.patika.libraryRest.dao;

import dev.patika.libraryRest.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AnimalRepo extends JpaRepository<Animal, Long> {
    List<Animal> findByName(String name);

    List<Animal> findByCustomerId(Long customerId);

    Animal findByNameAndSpeciesAndBreedAndGenderAndColourAndDateOfBirthAndCustomerId(
            String name,
            String species,
            String breed,
            String gender,
            String colour,
            LocalDate dateOfBirth,
            Long customerId
    );
}
