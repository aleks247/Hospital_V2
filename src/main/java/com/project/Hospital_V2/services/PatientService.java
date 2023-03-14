package com.project.Hospital_V2.services;
import com.project.Hospital_V2.entities.Appointment;
import com.project.Hospital_V2.entities.Doctor;
import com.project.Hospital_V2.entities.Patient;
import com.project.Hospital_V2.enums.KindOfReview;
import com.project.Hospital_V2.repositories.AppointmentRepository;
import com.project.Hospital_V2.repositories.DoctorRepository;
import com.project.Hospital_V2.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    private PatientService patientService;

    public PatientService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertPatients() {
        String sql = "INSERT IGNORE  INTO patient (id, username, age, first_name, last_name) " +
                "SELECT user_id, username, age, first_name, last_name FROM users WHERE role = ?";
        Object[] params = {"PATIENT"};
        int rowsAffected = jdbcTemplate.update(sql, params);
    }

    public ModelAndView appointmentCreation(Appointment appointment, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("redirect:/patients/create");
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

    public String menu(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        Patient patient = patientRepository.findByUsername(name);
        Iterable<Appointment> appointmentIterable = appointmentRepository.findAll();
        List<Appointment> appointments = new ArrayList<>();
        for (Appointment appointment : appointmentIterable) {
            if (appointment.getPatient().equals(patient)) {
                appointments.add(appointment);
            }
        }
        model.addAttribute("appointments", appointments);
        return "patients/menu";
    }


    public String appointmentForm(Model model) {
        Iterable<Doctor> doctors = doctorRepository.findAll();
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("doctors", doctors);
        model.addAttribute("KindOfReview", KindOfReview.values());
        return "/patients/create";
    }


    public ModelAndView appointmentSubmit(@ModelAttribute("appointment") @Valid Appointment appointment, BindingResult bindingResult) {
        return patientService.appointmentCreation(appointment, bindingResult);
    }


    public String editAppointment(@PathVariable(name = "appointmentId") Integer appointmentId, Model model) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentId);
        if (optionalAppointment.isPresent()) {
            model.addAttribute("appointment", optionalAppointment.get());
        } else {
            model.addAttribute("appointment", "Error!");
            model.addAttribute("errorMsg", " Not existing appointment with id = " + appointmentId);
        }

        return "/patients/edit";
    }

    public ModelAndView updateAppointment(@Valid Appointment appointment, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("/patients/edit");
        } else {
            appointmentRepository.save(appointment);
            return new ModelAndView("redirect:/patients/menu");
        }
    }


    public ModelAndView deleteAppointment(@PathVariable(name = "appointmentId") Integer appointmentId) {
        appointmentRepository.deleteById(appointmentId);
        return new ModelAndView("redirect:/patients/menu");
    }
}

