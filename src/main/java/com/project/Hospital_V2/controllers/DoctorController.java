package com.project.Hospital_V2.controllers;

import com.project.Hospital_V2.entities.Appointment;
import com.project.Hospital_V2.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doctors")
public class DoctorController {
    @Autowired
    AppointmentRepository appointmentRepository;

    @GetMapping("/menu")
    public String menu(){
        return "doctors/menu";
    }

    @GetMapping
    public String getAllResorts(Model model) {
        Iterable<Appointment> appointments = appointmentRepository.findAll();
        model.addAttribute("appointments", appointments);
        return "/doctors/menu";
    }
}
