package dev.patika.libraryRest.dao;

import dev.patika.libraryRest.entities.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VaccineRepo extends JpaRepository<Vaccine, Long> {

    List<Vaccine> findByAnimalId(Long animalId);

    @Query("SELECT v FROM Vaccine v WHERE v.name = :name AND v.code = :code AND v.protectionStartDate = :startDate AND v.protectionFinishDate = :finishDate AND v.animal.id = :animalId")
    List<Vaccine> findByNameAndCodeAndProtectionDatesAndAnimalId(@Param("name") String name, @Param("code") String code, @Param("startDate") LocalDate startDate, @Param("finishDate") LocalDate finishDate, @Param("animalId") Long animalId);

    @Query("SELECT v FROM Vaccine v WHERE v.name = :name AND v.code = :code AND v.animal.id = :animalId")
    List<Vaccine> findByNameAndCodeAndAnimalId(@Param("name") String name, @Param("code") String code, @Param("animalId") Long animalId);

    List<Vaccine> findByProtectionFinishDateBetween(LocalDate startDate, LocalDate endDate);
}

