package org.hospitals.hospitalsapp.controller;

import org.hospitals.hospitalsapp.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring5.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

@Controller
public class HospitalController {

    @Autowired
    HospitalService hospitalService;

//    @GetMapping("/")
//    Rendering index() {
//        return Rendering.view("index")
//                .modelAttribute("hospital",new ReactiveDataDriverContextVariable(hospitalService.streamHospitals(), 1)).build();
//    }

    @RequestMapping("/")
    public String index(final Model model) {

        IReactiveDataDriverContextVariable reactiveHospitalsInfo =
                new ReactiveDataDriverContextVariable(hospitalService.streamHospitals(),
                                                      1);

        model.addAttribute("hospital",
                           reactiveHospitalsInfo);

        return "index";
    }
}