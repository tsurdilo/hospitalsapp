package org.hospitals.hospitalsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import org.hospitals.hospitalsapp.data.Hospital;
import org.hospitals.hospitalsapp.services.HospitalTemplateOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class HospitalController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HospitalController.class);

    @Autowired
    HospitalTemplateOperations hospitalTemplate;

    @GetMapping(path = "/hospital/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Hospital> hospitalsfeed() {
        LOGGER.info("streaming hospitals...'");
        return this.hospitalTemplate.findAll();
    }

}