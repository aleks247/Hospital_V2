package com.project.Hospital_V2.controllers;

import com.project.Hospital_V2.entities.Appointment;
import com.project.Hospital_V2.repositories.AppointmentRepository;
import com.project.Hospital_V2.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/patients")
public class PatientController {
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    private PatientService patientService;

    @GetMapping("/menu")
    public String menu(Model model){
        return patientService.patientMenu(model);
    }

    @GetMapping("/create")
    public String appointmentForm(Model model) {
        return patientService.createAppointment(model);
    }


    @PostMapping("/submit")
    public ModelAndView appointmentSubmit(@ModelAttribute("appointment") @Valid Appointment appointment, BindingResult bindingResult) {
        return patientService.appointmentCreation(appointment, bindingResult);
    }


    @GetMapping("/edit/{appointmentId}")
    public String editAppointment(@PathVariable(name = "appointmentId") Integer appointmentId, Model model) {
        return patientService.editAppointment(appointmentId, model);
    }

    @PostMapping("/update")
    public ModelAndView updateAppointment(@Valid Appointment appointment, BindingResult bindingResult) {
        return patientService.updateAppointment(appointment, bindingResult);
    }

    @PostMapping("/delete/{appointmentId}")
    public ModelAndView deleteAppointment(@PathVariable(name = "appointmentId") Integer appointmentId) {
        appointmentRepository.deleteById(appointmentId);
        return new ModelAndView("redirect:/patients/menu");
    }
}
