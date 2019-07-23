package org.hospitals.hospitalsapp.controller;

import org.hospitals.hospitalsapp.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.result.view.Rendering;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

@Controller
public class HospitalController {

    @Autowired
    HospitalService hospitalService;


    @GetMapping("/")
    Rendering index() {
        return Rendering.view("index")
                .modelAttribute("hospital",new ReactiveDataDriverContextVariable(hospitalService.streamHospitals(), 1)).build();
    }

}