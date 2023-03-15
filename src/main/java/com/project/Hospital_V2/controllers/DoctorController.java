package com.project.Hospital_V2.controllers;

import com.project.Hospital_V2.entities.Appointment;
import com.project.Hospital_V2.entities.Doctor;
import com.project.Hospital_V2.entities.Patient;
import com.project.Hospital_V2.repositories.AppointmentRepository;
import com.project.Hospital_V2.repositories.DoctorRepository;
import com.project.Hospital_V2.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/doctors")
public class DoctorController {
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    DoctorService doctorService;

    @GetMapping("/menu")
    public String menu(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        Doctor doctor = doctorRepository.findByUsername(name);
        Iterable<Appointment> appointmentIterable = appointmentRepository.findAll();
        List<Appointment> appointments = new ArrayList<>();
        for (Appointment appointment: appointmentIterable) {
            if(appointment.getDoctor().equals(doctor)){
                appointments.add(appointment);
            }
        }
        model.addAttribute("appointments", appointments);
        return "doctors/menu";
    }

    @GetMapping("/searchDoctor")
    public String anotherDoctor(Model model, HttpSession session){
        Iterable<Doctor> doctors = doctorRepository.findAll();
        Iterable<Appointment> appointmentList = (Iterable<Appointment>) session.getAttribute("appointments");
        model.addAttribute("appointments", appointmentList);
        model.addAttribute("doctors", doctors);
        return "doctors/search";
    }

    @PostMapping("searchDoctors")
    public ModelAndView searchDoctors(@RequestParam("doctor") Integer doctor, HttpSession session){
        session.setAttribute("appointments", doctorService.getAllApointmentsFromSelectedDoctor(doctor));
        return new ModelAndView("redirect:/doctors/searchDoctor");
    }
}
