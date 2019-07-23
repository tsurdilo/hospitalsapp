package org.hospitals.hospitalsapp.graphql;

import java.util.concurrent.CompletableFuture;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.hospitals.hospitalsapp.data.Hospital;
import org.hospitals.hospitalsapp.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HospitalFetcher implements DataFetcher<Hospital>  {

    @Autowired
    HospitalRepository hospitalRepository;

    @Override
    public Hospital get(DataFetchingEnvironment dataFetchingEnvironment) {

        System.out.println("******************* In SINGLE HOSPUTAL FETCHER!!!!!");

        String id = dataFetchingEnvironment.getArgument("id");

        System.out.println("************** ID IS: " + id);

        CompletableFuture<Hospital> hospitalFuture = hospitalRepository.findById(id).toFuture();

        try {
            return hospitalFuture.get();
        } catch(Exception e) {
            return null;
        }


    }
}
