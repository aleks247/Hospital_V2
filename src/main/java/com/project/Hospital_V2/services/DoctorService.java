package com.project.Hospital_V2.services;

import com.project.Hospital_V2.entities.Appointment;
import com.project.Hospital_V2.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
        @Autowired
        private AppointmentRepository appointmentRepository;
         private final JdbcTemplate jdbcTemplate;

        public DoctorService(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }

        public void insertDoctors() {
            String sql = "INSERT IGNORE INTO doctor (id, username, first_name, last_name) " +
                    "SELECT user_id, username, first_name, last_name FROM users WHERE role = ?";
            Object[] params = {"DOCTOR"};
            int rowsAffected = jdbcTemplate.update(sql, params);
        }

        public Iterable<Appointment> getAllApointmentsFromSelectedDoctor(Integer doctorId){
            return appointmentRepository.getAllApointmentsFromSelectedDoctor(doctorId);
        }
}
