package org.hospitals.hospitalsapp.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class HospitalRequestRouter {

    @Bean
    public RouterFunction<?> routes(HospitalRequestHandler requestHandler) {
        return route(GET("/hospitals/{id}").and(accept(APPLICATION_JSON)),
                     requestHandler::get)
                .andRoute(GET("/hospitalsall").and(accept(APPLICATION_JSON)),
                          requestHandler::getAll)
                .andRoute(GET("/hospitalsstream"),
                          requestHandler::stream)
                .andRoute(POST("/hospitals/{id}").and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)),
                          requestHandler::post)
                .andRoute(DELETE("/hospitals/{id}"),
                          requestHandler::delete)
                .andRoute(POST("/queryhospitals").and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)),
                          requestHandler::getHospitalsGraphql);
    }
}