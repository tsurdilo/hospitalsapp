package org.hospitals.hospitalsapp.kafka;

import java.util.concurrent.CountDownLatch;

import org.hospitals.hospitalsapp.data.Hospital;
import org.hospitals.hospitalsapp.repository.HospitalRepository;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import reactor.core.publisher.Mono;

public class Receiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    @Autowired
    HospitalRepository hospitalRepository;

    @Autowired
    KieSession kieSession;

    private CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return latch;
    }

    @KafkaListener(topics = "${kafka.topic.hospital}")
    public void receive(Hospital hospital) {
        LOGGER.info("received hospital='{}'", hospital.toString());

        kieSession.insert(hospital);
        kieSession.fireAllRules();

        Mono<Hospital> hospitalMonoResult = hospitalRepository.findById(hospital.getId());
        Hospital foundHospital = hospitalMonoResult.block();
        if (foundHospital != null) {
            foundHospital.setAddress(hospital.getAddress());
            foundHospital.setName(hospital.getName());
            foundHospital.setZip(hospital.getZip());
            foundHospital.setPatients(hospital.getPatients());

            hospitalRepository.save(foundHospital).block();
        } else {
            hospitalRepository.save(hospital).block();
        }
        latch.countDown();
    }

}