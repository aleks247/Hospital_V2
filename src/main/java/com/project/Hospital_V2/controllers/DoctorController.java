package com.project.Hospital_V2.controllers;

import com.project.Hospital_V2.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
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
    DoctorService doctorService;

    @GetMapping("/menu")
    public String menu(Model model){
        return doctorService.doctorMenu(model);
    }

    @GetMapping("/searchDoctor")
    public String anotherDoctor(Model model, HttpSession session){
        return doctorService.searchDoctor(model, session);
    }

    @PostMapping("searchDoctors")
    public ModelAndView searchDoctors(@RequestParam("doctor") Integer doctor, HttpSession session){
        session.setAttribute("appointments", doctorService.getAllApointmentsFromSelectedDoctor(doctor));
        return new ModelAndView("redirect:/doctors/searchDoctor");
    }
}
