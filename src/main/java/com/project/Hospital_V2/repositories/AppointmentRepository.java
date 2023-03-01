package com.project.Hospital_V2.repositories;

import com.project.Hospital_V2.entities.Appointment;
import org.springframework.data.repository.CrudRepository;

public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {
}
