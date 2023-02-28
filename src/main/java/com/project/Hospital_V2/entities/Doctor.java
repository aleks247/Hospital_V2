package com.project.Hospital_V2.entities;

import com.project.Hospital_V2.enums.Specialties;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @NotNull
    @Size(min=3, max = 50)
    private String firstName;
    @NotNull
    @Size(min=3, max = 50)
    String lastName;
    @Enumerated(EnumType.STRING)
    @NotNull
    private Specialties speciality;

    public Doctor() {
    }

    public Doctor(Integer id, String firstName, String lastName, Specialties speciality) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.speciality = speciality;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Specialties getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Specialties speciality) {
        this.speciality = speciality;
    }
}

