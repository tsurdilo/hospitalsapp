package org.hospitals.hospitalsapp.controller;

import org.hospitals.hospitalsapp.repository.HospitalRepository;
import org.hospitals.hospitalsapp.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.result.view.Rendering;
import org.thymeleaf.spring5.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

@Controller
public class HospitalController {

    @Autowired
    HospitalService hospitalService;


//    @GetMapping("/")
//    Rendering index() {
//        return Rendering.view("index")
//                .modelAttribute("hospitals",new ReactiveDataDriverContextVariable(hospitalService.streamHospitals(), 100)).build();
//    }

    @RequestMapping("/")
    public String index(final Model model) {

        // loads 1 and display 1, stream data, data driven mode.
        IReactiveDataDriverContextVariable reactiveDataDrivenMode =
                new ReactiveDataDriverContextVariable(hospitalService.streamHospitals(), 1);

        model.addAttribute("hospital", reactiveDataDrivenMode);

        // classic, wait repository loaded all and display it.
        //model.addAttribute("movies", movieRepository.findAll());

        return "index";

    }

//    @RequestMapping("/")
//    public String index(final Model model) {
//
//        IReactiveDataDriverContextVariable reactiveDataDrivenMode =
//                new ReactiveDataDriverContextVariable(hospitalRepository.findAll(), 1);
//
//        model.addAttribute("hospitals", reactiveDataDrivenMode);
//
//        // classic, wait repository loaded all and display it.
//        //model.addAttribute("movies", movieRepository.findAll());
//
//        return "index";
//
//    }

}