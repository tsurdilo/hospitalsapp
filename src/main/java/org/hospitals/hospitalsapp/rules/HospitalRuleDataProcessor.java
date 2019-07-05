package org.hospitals.hospitalsapp.rules;

import org.hospitals.hospitalsapp.data.Hospital;
import org.hospitals.hospitalsapp.repository.HospitalRepository;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.kogito.rules.DataHandle;
import org.kie.kogito.rules.DataProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class HospitalRuleDataProcessor implements DataProcessor {

    @Autowired
    HospitalRepository hospitalRepository;


    @Override
    public FactHandle insert(DataHandle dataHandle,
                             Object o) {
        return null;
    }

    @Override
    public void update(DataHandle dataHandle,
                       Object o) {

    }

    @Override
    public void delete(DataHandle dataHandle) {

    }

    @Override
    public void insert(Object object) {
        // do something with object (hospital)
    }
}
