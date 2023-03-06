package com.project.Hospital_V2.services;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {
         private final JdbcTemplate jdbcTemplate;

        public DoctorService(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }

        public void insertDoctors() {
            String sql = "INSERT INTO doctor (id, first_name, last_name) " +
                    "SELECT user_id, first_name, last_name FROM users WHERE role = ?";
            Object[] params = {"DOCTOR"};
            int rowsAffected = jdbcTemplate.update(sql, params);
        }
}
