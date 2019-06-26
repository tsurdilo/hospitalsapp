package org.hospitals.hospitalsapp.data;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalsRepository extends ReactiveMongoRepository<Hospital, String> {

}