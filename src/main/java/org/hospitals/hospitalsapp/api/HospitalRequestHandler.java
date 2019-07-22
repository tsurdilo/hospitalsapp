package org.hospitals.hospitalsapp.api;

import org.hospitals.hospitalsapp.data.Hospital;
import org.hospitals.hospitalsapp.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class HospitalRequestHandler {

    @Autowired
    HospitalService hospitalService;

    public Mono<ServerResponse> stream(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(hospitalService.streamHospitals(), Hospital.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> get(ServerRequest request) {
        String id = request.pathVariable("id");
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(hospitalService.getHospital(id), Hospital.class).switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(hospitalService.getHospitals(), Hospital.class).switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> post(ServerRequest request) {
        Mono<Hospital> hospital = request.bodyToMono(Hospital.class);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(hospitalService.addHospital(hospital),
                Hospital.class);
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(hospitalService.deleteById(id),
                Void.class);
    }

}