package org.hospitals.hospitalsapp.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hospitals.hospitalsapp.data.Hospital;
import org.hospitals.hospitalsapp.kafka.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

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
        return new Hospital(generateRandomName(),
                            "New Hospital " + counter,
                            "",
                            "some address",
                            0000,
                            "",
                            null);
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

    public static void setupMongoDb(ReactiveMongoTemplate template,
                                    Sender sender) {
        LOGGER.info("dropping hospital collection");
        template.dropCollection(Hospital.class).block();
        LOGGER.info("setting up default hospitals");

        List<Hospital> defaultHospitals = getDefaultHospitals();
        defaultHospitals.stream().forEach(h -> {
            sender.send(h);
        });
    }

    private static List<Hospital> getDefaultHospitals() {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Hospital>> typeReference = new TypeReference<List<Hospital>>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream("/defaulthospitals.json");
        try {
            List<Hospital> defaultHospitals = mapper.readValue(inputStream,
                                                               typeReference);
            return defaultHospitals;
        } catch (IOException e) {
            LOGGER.warn("Unable to read default hospitals: " + e.getMessage());
            return null;
        }
    }
}