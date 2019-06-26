package org.hospitals.hospitalsapp.services;

import org.hospitals.hospitalsapp.data.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.ReactiveRemoveOperation;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class HospitalTemplateOperations {

    @Autowired
    ReactiveMongoTemplate template;

    public Mono<Hospital> findById(String id) {
        return template.findById(id, Hospital.class);
    }

    public Flux<Hospital> findAll() {
        return template.findAll(Hospital.class);
    }

    public Mono<Hospital> save(Mono<Hospital> hospital) {
        return template.save(hospital);
    }

    public ReactiveRemoveOperation.ReactiveRemove<Hospital> deleteAll() {
        return template.remove(Hospital.class);
    }

}
