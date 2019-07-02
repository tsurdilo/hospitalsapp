package org.hospitals.hospitalsapp.kafka;

import org.hospitals.hospitalsapp.data.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

public class Sender {

    @Autowired
    private KafkaTemplate<String, Hospital> kafkaTemplate;

    @Value("${hospitalapp.kafka.topic}")
    private String hospitalTopicName;

    public void send(Hospital hospital) {
        kafkaTemplate.send(hospitalTopicName, hospital);
    }
}