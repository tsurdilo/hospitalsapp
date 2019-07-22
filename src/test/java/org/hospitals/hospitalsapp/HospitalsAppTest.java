package org.hospitals.hospitalsapp;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hospitals.hospitalsapp.data.Hospital;
import org.hospitals.hospitalsapp.data.Patient;
import org.hospitals.hospitalsapp.kafka.Sender;
import org.hospitals.hospitalsapp.repository.HospitalRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = HospitalsApplication.class)
@DirtiesContext
@EmbeddedKafka(partitions = 1, topics = { "${kafka.topic.hospital}" })
public class HospitalsAppTest {

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        hospitalRepository.deleteAll().block();
        RestAssured.port = port;
    }

    @Autowired
    HospitalRepository hospitalRepository;

    @Autowired
    private Sender sender;

    @ClassRule
    public static EmbeddedKafkaRule embeddedKafka = new EmbeddedKafkaRule(1, true, "hospital");

    @After
    public void tearDown() throws Exception {
        hospitalRepository.deleteAll().block();
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
        Hospital hospital = new Hospital("12345", "Northside Hospital", "some address", 0000, null);

        hospitalRepository.deleteAll().block();
        sender.send(hospital);

        Thread.sleep(5000);

        Flux<Hospital> hospitalFlux = hospitalRepository.findAll();
        assertNotNull(hospitalFlux);
        List<Hospital> hospitals = hospitalFlux.collectList().block();

        assertNotNull(hospitals);
        assertEquals(1, hospitals.size());
        assertEquals("Northside Hospital", hospitals.get(0).getName());
        assertEquals(30040, hospitals.get(0).getZip());

    }

    @Test
    public void testKafkaMessageMultiStores() throws InterruptedException {
        Hospital hospital1 = new Hospital("northside", "Northside Hospital", "some address", 0000, null);
        Hospital hospital2 = new Hospital("piedmont", "Piedmont Hospital", "some address", 0000, null);

        sender.send(hospital1);
        sender.send(hospital2);

        Thread.sleep(5000);

        Flux<Hospital> hospitalFlux = hospitalRepository.findAll();
        assertNotNull(hospitalFlux);
        List<Hospital> hospitals = hospitalFlux.collectList().block();
        assertNotNull(hospitals);
        assertEquals(2, hospitals.size());
        assertEquals("Northside Hospital", hospitals.get(0).getName());
        assertEquals(30040, hospitals.get(0).getZip());
        assertEquals("Piedmont Hospital", hospitals.get(1).getName());
        assertEquals(30040, hospitals.get(1).getZip());

    }

    @Test
    public void testHospitalGetAll() throws InterruptedException {
        Hospital hospital1 = new Hospital("northside", "Northside Hospital", "some address", 30040, null);
        Hospital hospital2 = new Hospital("piedmont", "Piedmont Hospital", "some address", 30040, null);

        sender.send(hospital1);
        sender.send(hospital2);

        Thread.sleep(5000);

        given().accept(ContentType.JSON).when().get("/hospitalsall").then().statusCode(200)
                .body("$.size()",
                      is(2),
                      "[0].id",
                      is("northside"),
                      "[1].id",
                      is("piedmont"));

    }

    @Test
    public void testHospitalGetById() throws Exception {
        Hospital hospital1 = new Hospital("northside", "Northside Hospital", "some address", 30040, null);
        Hospital hospital2 = new Hospital("piedmont", "Piedmont Hospital", "some address", 30040, null);

        sender.send(hospital1);
        sender.send(hospital2);

        Thread.sleep(5000);


        String firstHospitalId = given().contentType(ContentType.JSON).accept(ContentType.JSON).when()
                .get("/hospitals/" + hospital1.getId()).then().statusCode(200).body("id",
                                                             notNullValue()).extract().path("id");

        String secondHospitalId = given().contentType(ContentType.JSON).accept(ContentType.JSON).when()
                .get("/hospitals/" + hospital2.getId()).then().statusCode(200).body("id",
                                                                                     notNullValue()).extract().path("id");


        assertNotNull(firstHospitalId);
        assertEquals("northside", firstHospitalId);

        assertNotNull(secondHospitalId);
        assertEquals("piedmont", secondHospitalId);

    }

    @Test
    public void testPutNewHospital() throws Exception {
        Hospital hospital1 = new Hospital("northside", "Northside Hospital", "some address", 30040, null);


        // create hospital
        String createdHospitalId = given().contentType(ContentType.JSON).accept(ContentType.JSON).body(hospital1).when()
                .post("/hospitals/" + hospital1.getId()).then().statusCode(200).body("id",
                                                             notNullValue()).extract().path("id");

        assertNotNull(createdHospitalId);
        assertEquals("northside", createdHospitalId);


        String receivedHospitalId = given().contentType(ContentType.JSON).accept(ContentType.JSON).when()
                .get("/hospitals/" + createdHospitalId).then().statusCode(200).body("id",
                                                                                    notNullValue()).extract().path("id");

        assertNotNull(receivedHospitalId);
        assertEquals("northside", receivedHospitalId);

    }

    @Test()
    public void testAddThenDeleteHospital() throws Exception {
        Hospital hospital1 = new Hospital("northside", "Northside Hospital", "some address", 30040, null);
        Hospital hospital2 = new Hospital("piedmont", "Piedmont Hospital", "some address", 30040, null);

        sender.send(hospital1);
        sender.send(hospital2);

        Thread.sleep(5000);

        // delete first
        given().accept(ContentType.JSON).when().delete("/hospitals/northside").then().statusCode(200);

        given().accept(ContentType.JSON).when().get("/hospitalsall").then().statusCode(200)
                .body("$.size()",
                      is(1),
                      "[0].id",
                      is("piedmont"));

    }
}