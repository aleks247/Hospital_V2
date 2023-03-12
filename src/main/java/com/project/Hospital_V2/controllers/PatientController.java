package com.project.Hospital_V2.controllers;

import com.project.Hospital_V2.entities.Appointment;
import com.project.Hospital_V2.entities.Doctor;
import com.project.Hospital_V2.entities.Patient;
import com.project.Hospital_V2.enums.KindOfReview;
import com.project.Hospital_V2.repositories.AppointmentRepository;
import com.project.Hospital_V2.repositories.DoctorRepository;
import com.project.Hospital_V2.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/patients")
public class PatientController {
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    DoctorRepository doctorRepository;
    @GetMapping("/menu")
    public String menu(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        Patient patient = patientRepository.findByUsername(name);
        Iterable<Appointment> appointmentIterable = appointmentRepository.findAll();
        List<Appointment> appointments = new ArrayList<>();
        for (Appointment appointment: appointmentIterable) {
            if(appointment.getPatient().equals(patient)){
                appointments.add(appointment);
            }
        }
        model.addAttribute("appointments", appointments);
        return "patients/menu";
    }

    @GetMapping("/create")
    public String appointmentForm(Model model) {
        Iterable<Doctor> doctors = doctorRepository.findAll();
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("doctors", doctors);
        model.addAttribute("KindOfReview", KindOfReview.values());
        return "/patients/create";
    }


    @PostMapping("/submit")
    public ModelAndView appointmentSubmit(@ModelAttribute("appointment") @Valid Appointment appointment, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return  new ModelAndView("redirect:/patients/create");
        } else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String name = auth.getName();
            Patient patient = patientRepository.findByUsername(name);
            appointment.setPatient(patient);
            appointmentRepository.save(appointment);
            return new ModelAndView("redirect:/patients/menu");
        }
    }


    @GetMapping("/edit/{appointmentId}")
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

    @PostMapping("/update")
    public ModelAndView updateAppointment(@Valid Appointment appointment, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return new ModelAndView("/patients/edit");
        } else {
            appointmentRepository.save(appointment);
            return new ModelAndView("redirect:/patients/menu");
        }
    }

    @PostMapping("/delete/{appointmentId}")
    public ModelAndView deleteAppointment(@PathVariable(name = "appointmentId") Integer appointmentId) {
        appointmentRepository.deleteById(appointmentId);
        return new ModelAndView("redirect:/patients/menu");
    }
}
