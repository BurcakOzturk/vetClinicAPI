package dev.patika.libraryRest.dao;

import dev.patika.libraryRest.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Long> {
    Optional<Object> findByMail(String mail);
    Optional<Object> findByPhone(String mail);
}
