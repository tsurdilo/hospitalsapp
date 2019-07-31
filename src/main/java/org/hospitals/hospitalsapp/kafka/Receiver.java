package org.hospitals.hospitalsapp.kafka;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.hospitals.hospitalsapp.data.Doctor;
import org.hospitals.hospitalsapp.data.Hospital;
import org.hospitals.hospitalsapp.data.Patient;
import org.hospitals.hospitalsapp.process.HospitalProcessComponent;
import org.hospitals.hospitalsapp.repository.HospitalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import reactor.core.publisher.Mono;

public class Receiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    @Autowired
    HospitalRepository hospitalRepository;

    @Autowired
    HospitalProcessComponent hospitalProcessComponent;

    private CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return latch;
    }

    @KafkaListener(topics = "${kafka.topic.hospital}")
    public void receive(Object receivedObject) {

        ConsumerRecord consumerRecord = (ConsumerRecord) receivedObject;

        if(consumerRecord.value() instanceof Hospital) {
            handleReceivedHospital((Hospital) consumerRecord.value());
        } else if(consumerRecord.value() instanceof Patient) {
            handleReceivedPatient((Patient) consumerRecord.value());
        } else {
            LOGGER.warn("Unable to handle consumer record: " + consumerRecord);
        }

        latch.countDown();
    }

    private void handleReceivedHospital(Hospital hospital) {
        LOGGER.info("received hospital='{}'",
                    hospital.toString());

        // kogito -- start process
        hospitalProcessComponent.startNewHospitalProcess(hospital);

        Mono<Hospital> hospitalMonoResult = hospitalRepository.findById(hospital.getId());
        Hospital foundHospital = hospitalMonoResult.block();
        if (foundHospital != null) {
            foundHospital.setAddress(hospital.getAddress());
            foundHospital.setName(hospital.getName());
            foundHospital.setDesc(hospital.getDesc());
            foundHospital.setZip(hospital.getZip());
            foundHospital.setImg(hospital.getImg());
            foundHospital.setDoctors(hospital.getDoctors());

            hospitalRepository.save(foundHospital).block();
        } else {
            hospitalRepository.save(hospital).block();
        }
    }

    private void handleReceivedPatient(Patient patient) {
        LOGGER.info("received patient='{}'",
                    patient.toString());

        // kogito -- start process
        hospitalProcessComponent.startNewPatientProcess(patient);

//        Mono<Hospital> hospitalMonoResult = hospitalRepository.findById(patient.getHospitalId());
//        Hospital foundHospital = hospitalMonoResult.block();
//
//        if(foundHospital != null) {
//            List<Doctor> doctorList = foundHospital.getDoctors();
//            for(Doctor d : doctorList) {
//                if(d.getId().equals(patient.getDoctorId())) {
//                    if(d.getPatients() == null) {
//                        d.setPatients(new ArrayList<>());
//                    }
//                    d.getPatients().add(patient);
//                }
//            }
//            LOGGER.info("Updateing hospital: " + foundHospital.toString());
//            hospitalRepository.save(foundHospital);
//        } else {
//            LOGGER.warn("Unable to find hospital with id: " + patient.getHospitalId());
//        }

    }
}