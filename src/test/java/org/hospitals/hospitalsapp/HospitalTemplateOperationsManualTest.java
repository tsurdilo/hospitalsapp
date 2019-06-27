package org.hospitals.hospitalsapp;

import org.hospitals.hospitalsapp.data.Hospital;
import org.hospitals.hospitalsapp.data.Patient;
import org.hospitals.hospitalsapp.kafka.Receiver;
import org.hospitals.hospitalsapp.kafka.Sender;
import org.hospitals.hospitalsapp.repository.HospitalRepository;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = HospitalsApplication.class)
@DirtiesContext
@EmbeddedKafka(partitions = 1, topics = { "hospital" })
public class HospitalTemplateOperationsManualTest {

    @Autowired
    private WebTestClient webClient;

    @Autowired
    HospitalRepository hospitalRepository;

    @Autowired
    private Sender sender;

    @Autowired
    private Receiver receiver;

    @ClassRule
    public static EmbeddedKafkaRule embeddedKafka = new EmbeddedKafkaRule(1, true, "hospital");

    @Before
    public void setUp() throws Exception {
        hospitalRepository.deleteAll();
    }

    @Test
    public void testSaveHospital() {
        Hospital hospital = hospitalRepository
                .save(new Hospital("northside", "Northside Hospital", "some address", 30040, null)).block();
        assertNotNull(hospital.getId());
    }

    @Test
    public void testfindHospitalById() {
        Mono<Hospital> hospitalMono = hospitalRepository
                .save(new Hospital("northside", "Northside Hospital", "some address", 30040, null));
        Mono<Hospital> hospitalMonoResult = hospitalRepository.findById(hospitalMono.block().getId());
        assertNotNull(hospitalMonoResult.block().getId());
        assertEquals(hospitalMonoResult.block().getName(), "Northside Hospital");
    }

    @Test
    public void testFindAllHospitals() {
        Hospital hospital1 = hospitalRepository
                .save(new Hospital("northside", "Northside Hospital", "some address", 30040, null)).block();
        Hospital hospital2 = hospitalRepository
                .save(new Hospital("northside", "Northside Hospital", "some address", 30040, null)).block();
        Flux<Hospital> hospitalFlux = hospitalRepository.findAll();
        List<Hospital> hospitals = hospitalFlux.collectList().block();
        assertTrue(hospitals.stream().anyMatch(x -> hospital1.getId().equals(x.getId())));
        assertTrue(hospitals.stream().anyMatch(x -> hospital2.getId().equals(x.getId())));
    }

    @Test
    public void testFindAllHospitalsWithPatients() {
        Patient patient1 = new Patient("123", "Michael", "Jordan", "address", 12345, null);
        Patient patient2 = new Patient("123", "Charles", "Barkley", "address", 12345, null);
        Hospital hospital = new Hospital("northside", "Northside Hospital", "some address", 30040,
                Stream.of(patient1, patient2).collect(Collectors.toCollection(ArrayList<Patient>::new)));

        Mono<Hospital> hospitalMono = hospitalRepository.save(hospital);

        Mono<Hospital> hospitalMonoResult = hospitalRepository.findById(hospitalMono.block().getId());
        assertNotNull(hospitalMonoResult.block().getId());
        assertEquals(hospitalMonoResult.block().getName(), "Northside Hospital");
        assertNotNull(hospitalMonoResult.block().getPatients());
        assertEquals(2, hospitalMonoResult.block().getPatients().size());

    }

    @Test
    public void testKafkaMessageStores() throws InterruptedException {
        Hospital hospital = new Hospital("northside", "Northside Hospital", "some address", 30040, null);

        sender.send(hospital);
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        assertEquals(0, receiver.getLatch().getCount());

        Flux<Hospital> hospitalFlux = hospitalRepository.findAll();
        assertNotNull(hospitalFlux);
        List<Hospital> hospitals = hospitalFlux.collectList().block();
        assertNotNull(hospitals);
        assertEquals(1, hospitals.size());
        assertEquals("Northside Hospital", hospitals.get(0).getName());

    }

    @Test
    public void testKafkaMessageMultiStores() throws InterruptedException {
        Hospital hospital1 = new Hospital("northside", "Northside Hospital", "some address", 30040, null);
        Hospital hospital2 = new Hospital("piedmont", "Piedmont Hospital", "some address", 30040, null);

        sender.send(hospital1);
        sender.send(hospital2);

        Thread.sleep(5000);

        Flux<Hospital> hospitalFlux = hospitalRepository.findAll();
        assertNotNull(hospitalFlux);
        List<Hospital> hospitals = hospitalFlux.collectList().block();
        assertNotNull(hospitals);
        assertEquals(2, hospitals.size());
        assertEquals("Northside Hospital", hospitals.get(0).getName());
        assertEquals("Piedmont Hospital", hospitals.get(1).getName());

    }

    @Test
    public void testHotelStream() throws InterruptedException {
        Hospital hospital1 = new Hospital("northside", "Northside Hospital", "some address", 30040, null);
        Hospital hospital2 = new Hospital("piedmont", "Piedmont Hospital", "some address", 30040, null);

        sender.send(hospital1);
        sender.send(hospital2);

        Thread.sleep(5000);

        List<Hospital> hospitals = webClient.get().uri("/hospital/stream")
                .accept(MediaType.valueOf(MediaType.TEXT_EVENT_STREAM_VALUE)).exchange().expectStatus().isOk()
                .returnResult(Hospital.class).getResponseBody().take(2).collectList().block();

        assertNotNull(hospitals);
        assertEquals(2, hospitals.size());
    }
}