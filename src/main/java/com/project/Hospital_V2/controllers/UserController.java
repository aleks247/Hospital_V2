package com.project.Hospital_V2.controllers;

import com.project.Hospital_V2.entities.User;
import com.project.Hospital_V2.repositories.UserRepository;
import com.project.Hospital_V2.services.DoctorService;
import com.project.Hospital_V2.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    DoctorService doctorService;

    @Autowired
    PatientService patientService;
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "/register";
    }
    @PostMapping("/process_register")
    public ModelAndView processRegister(@Valid User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return new ModelAndView("/register");
        } else{
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setRole("PATIENT");
            user.setEnabled(true);
            user.setPassword(encodedPassword);
            userRepository.save(user);
            doctorService.insertDoctors();
            patientService.insertPatients();
            return new ModelAndView("redirect:/menu");
        }
    }
}
