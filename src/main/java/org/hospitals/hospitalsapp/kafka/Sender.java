package org.hospitals.hospitalsapp.kafka;

import org.hospitals.hospitalsapp.data.Hospital;
import org.hospitals.hospitalsapp.data.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

public class Sender {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic.hospital}")
    private String hospitalTopicName;

    public void send(Hospital hospital) {
        kafkaTemplate.send(hospitalTopicName,
                           hospital);
    }

    public void send(Patient patient) {
        kafkaTemplate.send(hospitalTopicName,
                           patient);
    }
}