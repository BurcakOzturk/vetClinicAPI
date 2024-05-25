package dev.patika.libraryRest.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "animals")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "species")
    private String species;

    @NotNull
    @Column(name = "breed")
    private String breed;

    @NotNull
    @Column(name = "gender")
    private String gender;

    @NotNull
    @Column(name = "colour")
    private String colour;

    @NotNull
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.REMOVE)
    private List<Vaccine> vaccineList;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.REMOVE)
    private List<Appointment> appointmentList;
}
