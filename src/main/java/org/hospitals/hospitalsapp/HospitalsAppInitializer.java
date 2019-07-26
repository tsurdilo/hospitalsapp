package org.hospitals.hospitalsapp;

import javax.annotation.PostConstruct;

import org.hospitals.hospitalsapp.kafka.Sender;
import org.hospitals.hospitalsapp.util.HospitalGeneratorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class HospitalsAppInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(HospitalsApplication.class);

    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate;

    @Autowired
    Sender sender;

    @Value("${hospitalapp.mongo.setdefaults}")
    private boolean setDefaults;

    @PostConstruct
    private void init() {
        LOGGER.info("HospitalsApp initializer logic ...");

        if (setDefaults) {
            HospitalGeneratorUtil.setupMongoDb(reactiveMongoTemplate,
                                               sender);
        } else {
            LOGGER.info("Opted not to set mongo defaults ...");
        }
    }
}
