package com.project.Hospital_V2.controllers;

import com.project.Hospital_V2.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/patients")
public class PatientController {
    @Autowired
    PatientRepository patientRepository;

    @GetMapping("/menu")
    public String menu(){
        return "patients/menu";
    }
}
