package org.hospitals.hospitalsapp.kafka;

import org.hospitals.hospitalsapp.data.Hospital;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public class Sender {

    private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

    @Autowired
    private KafkaTemplate<String, Hospital> kafkaTemplate;

    public void send(Hospital hospital) {
        LOGGER.info("sending hospital='{}'", hospital.toString());
        kafkaTemplate.send("hospital", hospital);
    }
}