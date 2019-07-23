package org.hospitals.hospitalsapp.graphql;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.hospitals.hospitalsapp.data.Hospital;
import org.hospitals.hospitalsapp.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AllHospitalsFetcher implements DataFetcher<List<Hospital>> {

    @Autowired
    HospitalRepository hospitalRepository;

    @Override
    public List<Hospital> get(DataFetchingEnvironment dataFetchingEnvironment) {

        CompletableFuture<List<Hospital>> retList = hospitalRepository.findAll().collectList().toFuture();

        try {
            return retList.get();
        } catch(Exception e) {
            return null;
        }
    }
}
