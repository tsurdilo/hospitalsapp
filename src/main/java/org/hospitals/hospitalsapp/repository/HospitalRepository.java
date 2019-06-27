package org.hospitals.hospitalsapp.repository;

import org.hospitals.hospitalsapp.data.Hospital;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;

@Repository
public interface HospitalRepository extends ReactiveMongoRepository<Hospital, String> {
    @Tailable
    Flux<Hospital> findBy();
}