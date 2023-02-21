package com.project.Hospital_V2.repositories;

import com.project.Hospital_V2.entities.Patient;
import org.springframework.data.repository.CrudRepository;

public interface PatientRepository extends CrudRepository<Patient, Integer> {
}
