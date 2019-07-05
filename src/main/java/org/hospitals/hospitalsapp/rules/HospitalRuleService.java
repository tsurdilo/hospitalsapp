package org.hospitals.hospitalsapp.rules;

import org.drools.core.ruleunit.impl.ListDataStream;
import org.hospitals.hospitalsapp.data.Hospital;
import org.kie.kogito.rules.DataStream;
import org.kie.kogito.rules.RuleUnitMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HospitalRuleService implements RuleUnitMemory {

    private final DataStream<Hospital> hospitalsInStream = new ListDataStream<>();
    private final DataStream<Hospital> hospitalsOutStream = new ListDataStream<>();

    public DataStream<Hospital> getHospitalsInStream() {
        return hospitalsInStream;
    }

    public DataStream<Hospital> getHospitalsOutStream() {
        return hospitalsOutStream;
    }

    @Autowired
    public HospitalRuleService(HospitalRuleDataProcessor hospitalRuleDataProcessor) {
        hospitalsOutStream.subscribe(hospitalRuleDataProcessor);
    }
}
