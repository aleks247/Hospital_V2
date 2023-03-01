package com.project.Hospital_V2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doctors")
public class DoctorController {
    @GetMapping
    public String menu(){
        return "/menu";
    }
    @GetMapping("/main")
    public String main(){
        return "/Main";
    }
}
