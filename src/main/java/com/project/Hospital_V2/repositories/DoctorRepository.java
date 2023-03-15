package com.project.Hospital_V2.repositories;

import com.project.Hospital_V2.entities.Doctor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends CrudRepository<Doctor, Integer> {
    Doctor findByUsername(String username);
}
