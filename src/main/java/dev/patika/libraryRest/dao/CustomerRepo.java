package dev.patika.libraryRest.dao;

import dev.patika.libraryRest.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
    boolean existsByMail(String mail);

    boolean existsByPhone(String phone);

    List<Customer> findByName(String name);
}
