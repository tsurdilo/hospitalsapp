package org.hospitals.hospitalsapp;

import com.mongodb.reactivestreams.client.MongoClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class HospitalsApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalsApplication.class, args);
	}

	@Autowired
	MongoClient mongoClient;

	@Value("${hospitalapp.mongo.dbname}")
	String dbName;

	@Bean
	public ReactiveMongoTemplate reactiveMongoTemplate() {
		return new ReactiveMongoTemplate(mongoClient, dbName);
	}

}
