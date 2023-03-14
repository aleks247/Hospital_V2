package com.project.Hospital_V2.services;

import com.project.Hospital_V2.entities.Appointment;
import com.project.Hospital_V2.entities.Doctor;
import com.project.Hospital_V2.repositories.AppointmentRepository;
import com.project.Hospital_V2.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Service

public class DoctorService {
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    DoctorRepository doctorRepository;

    public String menu(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        Doctor doctor = doctorRepository.findByUsername(name);
        Iterable<Appointment> appointmentIterable = appointmentRepository.findAll();
        List<Appointment> appointments = new ArrayList<>();
        for (Appointment appointment : appointmentIterable) {
            if (appointment.getDoctor().equals(doctor)) {
                appointments.add(appointment);
            }
        }
        model.addAttribute("appointments", appointments);
        return "doctors/menu";
    }

    private final JdbcTemplate jdbcTemplate;

    public DoctorService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertDoctors() {
        String sql = "INSERT IGNORE INTO doctor (id, first_name, last_name) " +
                "SELECT user_id, first_name, last_name FROM users WHERE role = ?";
        Object[] params = {"DOCTOR"};
        int rowsAffected = jdbcTemplate.update(sql, params);
    }
}

