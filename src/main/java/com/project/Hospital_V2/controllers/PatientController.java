package com.project.Hospital_V2.controllers;
import com.project.Hospital_V2.entities.Appointment;
import com.project.Hospital_V2.repositories.AppointmentRepository;
import com.project.Hospital_V2.repositories.DoctorRepository;
import com.project.Hospital_V2.repositories.PatientRepository;
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
    PatientRepository patientRepository;
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    private PatientService patientService;

    @GetMapping("/menu")
    public String menu(Model model) {
        return "patients/menu";
    }

    @GetMapping("/create")
    public String appointmentForm(Model model) {
        return "/patients/create";
    }


    @PostMapping("/submit")
    public ModelAndView appointmentSubmit(@ModelAttribute("appointment") @Valid Appointment appointment, BindingResult bindingResult) {
        return patientService.appointmentCreation(appointment, bindingResult);
    }


    @GetMapping("/edit/{appointmentId}")
    public String editAppointment(@PathVariable(name = "appointmentId") Integer appointmentId, Model model) {
        return "/patients/edit";
    }

    @PostMapping("/update")
    public ModelAndView updateAppointment(@Valid Appointment appointment, BindingResult bindingResult) {
        return new ModelAndView("redirect:/patients/menu");
    }


    @PostMapping("/delete/{appointmentId}")
    public ModelAndView deleteAppointment(@PathVariable(name = "appointmentId") Integer appointmentId) {
        return new ModelAndView("redirect:/patients/menu");
    }
}
