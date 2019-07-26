package org.hospitals.hospitalsapp.service;

import java.time.Duration;

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
        return hospitalRepository.findAll().delayElements(Duration.ofSeconds(2));
    }

    public Mono<Hospital> getHospital(String id) {
        return hospitalRepository.findById(id);
    }

    public Flux<Hospital> getHospitals() {
        return hospitalRepository.findAll();
    }

    public Mono<Hospital> addHospital(Mono<Hospital> hospital) {
        return hospital.flatMap(h -> {
            return hospitalRepository.insert(h);
        });
    }

    public Mono<Void> deleteById(String id) {
        return hospitalRepository.deleteById(id);
    }
}