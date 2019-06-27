package org.hospitals.hospitalsapp.util;

import java.util.Random;

import org.hospitals.hospitalsapp.data.Hospital;
import org.hospitals.hospitalsapp.kafka.Sender;
import org.springframework.scheduling.annotation.Scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HospitalGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(HospitalGenerator.class);

    private Sender sender;
    private int counter = 0;

    public HospitalGenerator(Sender sender) {
        this.sender = sender;
    }

    @Scheduled(fixedDelay = 5000)
    public void generateRandomHospital() {
        LOGGER.info("generating random hospital...'");
        sender.send(genrateRandomHospital(counter));
        counter++;
    }

    private Hospital genrateRandomHospital(int counter) {
        return new Hospital(generateRandomName(), "New Hospital " + counter, "some address", 30040, null);
    }

    private String generateRandomName() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;

        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }
}