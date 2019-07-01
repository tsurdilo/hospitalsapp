package org.hospitals.hospitalsapp;

import com.mongodb.reactivestreams.client.MongoClient;

import org.hospitals.hospitalsapp.kafka.Sender;
import org.hospitals.hospitalsapp.util.HospitalGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableReactiveMongoRepositories
@EnableScheduling
public class HospitalsApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalsApplication.class, args);
	}

	@Autowired
	MongoClient mongoClient;

	@Autowired
	private Sender sender;

	@Bean
	public ReactiveMongoTemplate reactiveMongoTemplate() {
		return new ReactiveMongoTemplate(mongoClient, "hospitals");
	}

	@Bean
	@ConditionalOnProperty(value = "hospitalgen.enabled", matchIfMissing = true, havingValue = "true")
	public HospitalGenerator generateHospital() {
		return new HospitalGenerator(sender);
	}

}
