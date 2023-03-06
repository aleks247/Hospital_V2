package com.project.Hospital_V2.services;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class PatientService {
    private final JdbcTemplate jdbcTemplate;

    public PatientService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertPatients() {
        String sql = "INSERT IGNORE  INTO patient (id, age, first_name, last_name) " +
                "SELECT user_id, age, first_name, last_name FROM users WHERE role = ?";
        Object[] params = {"PATIENT"};
        int rowsAffected = jdbcTemplate.update(sql, params);
    }
}
