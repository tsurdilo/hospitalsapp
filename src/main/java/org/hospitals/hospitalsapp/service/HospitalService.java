package org.hospitals.hospitalsapp.service;

import org.hospitals.hospitalsapp.data.Hospital;
import org.hospitals.hospitalsapp.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class HospitalService {

    @Autowired
    HospitalRepository hospitalRepository;

    public Flux<Hospital> streamHospitals() {
        return hospitalRepository.findAll();

    }

    public Mono<Hospital> getHospital(String id) {
        return hospitalRepository.findById(id);
    }

    public Flux<Hospital> getHospitals() {
        return hospitalRepository.findAll();

    }

}