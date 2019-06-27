package org.hospitals.hospitalsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import org.hospitals.hospitalsapp.data.Hospital;
import org.hospitals.hospitalsapp.repository.HospitalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class HospitalRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HospitalRestController.class);

    @Autowired
    HospitalRepository hospitalRepository;

    @GetMapping(path = "/hospital/tail", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Hospital> hospitalstail() {
        LOGGER.info("streaming hospitals...'");
        return hospitalRepository.findBy();
    }

    @GetMapping(path = "/hospital/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Hospital> hospitalsstream() {
        LOGGER.info("streaming hospitals...'");
        return hospitalRepository.findAll();
    }

}