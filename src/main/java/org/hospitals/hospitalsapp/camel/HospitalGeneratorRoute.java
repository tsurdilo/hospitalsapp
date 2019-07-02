package org.hospitals.hospitalsapp.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.hospitals.hospitalsapp.util.HospitalGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.hospitals.hospitalsapp.data.Hospital;
import org.hospitals.hospitalsapp.kafka.Sender;

@Component
public class HospitalGeneratorRoute extends RouteBuilder {

    public static final String ROUTE_NAME = "HOSPITAL_GENERATOR_ROUTE";

    @Autowired
    Sender sender;

    @Value("${hospitalapp.camel.process.routes}")
    private boolean processRoutes;

    @Override
    public void configure() throws Exception {

        // generate random hospital every 5 seconds
        // process sending kafka message
        from("timer:initial//start?period=5s").routeId(ROUTE_NAME).process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                if (processRoutes) {
                    Hospital hospital = HospitalGeneratorUtil.generateRandomHospital();
                    sender.send(hospital);
                }
            }
        });
    }

}