package org.hospitals.hospitalsapp.controller;

import org.hospitals.hospitalsapp.data.Patient;
import org.hospitals.hospitalsapp.kafka.Sender;
import org.hospitals.hospitalsapp.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.result.view.Rendering;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

@Controller
public class HospitalController {

    @Autowired
    HospitalService hospitalService;

    @Autowired
    private Sender sender;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    Rendering index() {

        return Rendering.view("index")
                .modelAttribute("hospitals",new ReactiveDataDriverContextVariable(hospitalService.getHospitals(), 10)).build();
    }

    @RequestMapping(value = "/hospital/{hid}", method = RequestMethod.GET)
    Rendering hospital(@PathVariable("hid") String hospitalid) {

        return Rendering.view("hospitalinfo")
                .modelAttribute("hospital", hospitalService.getHospital(hospitalid))
                .modelAttribute("patient", new Patient()).build();
    }

    @RequestMapping(value = "/newpatient", method = RequestMethod.POST)
    Rendering newPatient(@ModelAttribute Patient patient) {

        System.out.println("************* NEW PATIENT \n " + patient.toString());
        sender.send(patient);

        return Rendering.view("newpatient").build();
    }
}