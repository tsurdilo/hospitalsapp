package org.hospitals.hospitalsapp.kafka;

import java.util.concurrent.CountDownLatch;

import org.hospitals.hospitalsapp.data.Hospital;
import org.hospitals.hospitalsapp.repository.HospitalRepository;
import org.hospitals.hospitalsapp.rules.HospitalRuleService;
import org.kie.kogito.rules.RuleUnitInstance;
import org.kie.kogito.rules.impl.AbstractRuleUnit;
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
    AbstractRuleUnit<HospitalRuleService> hospitalRuleUnit;

    @Autowired
    HospitalRuleService hospitalRuleService;

    private CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return latch;
    }

    @KafkaListener(topics = "${kafka.topic.hospital}")
    public void receive(Hospital hospital) {
        LOGGER.info("received hospital='{}'", hospital.toString());

        hospitalRuleService.getHospitalsInStream().append(hospital);
        RuleUnitInstance<HospitalRuleService> hospitalRuleUnitInstance = hospitalRuleUnit
                .createInstance(hospitalRuleService);
        hospitalRuleUnitInstance.fire();

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