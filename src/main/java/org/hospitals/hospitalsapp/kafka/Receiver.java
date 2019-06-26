package org.hospitals.hospitalsapp.kafka;

import java.util.concurrent.CountDownLatch;

import org.hospitals.hospitalsapp.data.Hospital;
import org.hospitals.hospitalsapp.services.HospitalTemplateOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import reactor.core.publisher.Mono;

public class Receiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    @Autowired
    HospitalTemplateOperations hospitalTemplate;

    private CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return latch;
    }

    @KafkaListener(topics = "hospital")
    public void receive(Hospital hospital) {
        LOGGER.info("received hospital='{}'", hospital.toString());

        Mono<Hospital> hospitalMonoResult = hospitalTemplate.findById(hospital.getId());
        Hospital foundHospital = hospitalMonoResult.block();
        if (foundHospital != null) {
            foundHospital.setAddress(hospital.getAddress());
            foundHospital.setName(hospital.getName());
            foundHospital.setZip(hospital.getZip());
            foundHospital.setPatients(hospital.getPatients());

            hospitalTemplate.save(Mono.just(foundHospital)).block();
        } else {
            hospitalTemplate.save(Mono.just(hospital)).block();
        }
        latch.countDown();
    }

}