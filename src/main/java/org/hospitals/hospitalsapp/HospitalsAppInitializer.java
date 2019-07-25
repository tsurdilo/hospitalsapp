package org.hospitals.hospitalsapp;

import javax.annotation.PostConstruct;

import org.hospitals.hospitalsapp.data.Hospital;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class HospitalsAppInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(HospitalsApplication.class);


    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate;

    @PostConstruct
    private void init() {
        LOGGER.info("HospitalsApp initializer logic ...");

        LOGGER.info("dropping hospital collection");
        reactiveMongoTemplate.dropCollection(Hospital.class).block();

    }
}
