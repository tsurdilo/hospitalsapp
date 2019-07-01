package org.hospitals.hospitalsapp.rules;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.drools.model.Declaration;
import org.drools.model.Rule;
import org.drools.model.Model;
import org.drools.model.impl.ModelImpl;
import org.drools.modelcompiler.builder.KieBaseBuilder;
import org.hospitals.hospitalsapp.data.Hospital;
import org.kie.api.runtime.KieSession;

import static org.drools.model.DSL.on;
import static org.drools.model.PatternDSL.declarationOf;
import static org.drools.model.PatternDSL.pattern;
import static org.drools.model.PatternDSL.rule;

@Configuration
public class RulesConfig {

    @Bean
    public Model getHospitalRuleModel() {
        Declaration<Hospital> hospital = declarationOf(Hospital.class);

        Rule r = rule("Set Random Hospital Zip").build(pattern(hospital),
                on(hospital).execute((h1) -> h1.setZip(30040)));

        return new ModelImpl().addRule(r);
    }

    @Bean
    public KieSession getHospitalsKieSession(Model hospitalRuleModel) {
        return KieBaseBuilder.createKieBaseFromModel(hospitalRuleModel).newKieSession();
    }
}