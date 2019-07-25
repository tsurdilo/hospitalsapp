package org.hospitals.hospitalsapp.util;

import java.util.Random;

import org.hospitals.hospitalsapp.data.Hospital;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HospitalGeneratorUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HospitalGeneratorUtil.class);
    private static int counter = 0;

    public static Hospital generateRandomHospital() {
        LOGGER.info("generating random hospital...'");
        Hospital hospital = genrateRandomHospital(counter);
        counter++;
        return hospital;
    }

    private static Hospital genrateRandomHospital(int counter) {
        return new Hospital(generateRandomName(), "New Hospital " + counter, "some address", 0000, "", null);
    }

    private static String generateRandomName() {
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