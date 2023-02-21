package com.project.Hospital_V2.repositories;

import com.project.Hospital_V2.entities.Doctor;
import org.springframework.data.repository.CrudRepository;

public interface DoctorRepository extends CrudRepository<Doctor, Integer> {
}
