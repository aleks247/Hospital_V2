package com.project.Hospital_V2.repositories;

import com.project.Hospital_V2.entities.Appointment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {
    List<Appointment> findByDoctorId(Integer DoctorId);
    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId")
    Iterable<Appointment> getAllApointmentsFromSelectedDoctor(@Param("doctorId") Integer doctorId);
}
