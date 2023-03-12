package com.project.Hospital_V2.services;

import com.project.Hospital_V2.entities.Appointment;
import com.project.Hospital_V2.entities.Patient;
import com.project.Hospital_V2.repositories.AppointmentRepository;
import com.project.Hospital_V2.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service
public class PatientService {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private PatientRepository patientRepository;

    public PatientService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertPatients() {
        String sql = "INSERT IGNORE  INTO patient (id, username, age, first_name, last_name) " +
                "SELECT user_id, username, age, first_name, last_name FROM users WHERE role = ?";
        Object[] params = {"PATIENT"};
        int rowsAffected = jdbcTemplate.update(sql, params);
    }

    public ModelAndView appointmentCreation(Appointment appointment,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return  new ModelAndView("redirect:/patients/create");
        } else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String name = auth.getName();
            Patient patient = patientRepository.findByUsername(name);
            appointment.setPatient(patient);
            List<Appointment> doctorsApps = appointmentRepository.findByDoctorId(appointment.getDoctor().getId());
            System.out.println(doctorsApps.size());
            boolean test = false;
            if (doctorsApps.size() > 0) {
                for (int i = 0; i < doctorsApps.size(); i++) {
                    if (appointment.getDateTime().plusMinutes(30).isAfter(doctorsApps.get(i).getDateTime()) &&
                            appointment.getDateTime().isBefore(doctorsApps.get(i).getDateTime().plusMinutes(30))) {
                        test = true;
                        break;
                    }
                }
                if (test) {
                    return new ModelAndView("redirect:/patients/create");
                } else {
                    appointmentRepository.save(appointment);
                    return new ModelAndView("redirect:/patients/menu");
                }
            } else {
                appointmentRepository.save(appointment);
                return new ModelAndView("redirect:/patients/menu");
            }
        }
    }
}
