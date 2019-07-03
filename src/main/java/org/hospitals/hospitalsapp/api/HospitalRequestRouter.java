package org.hospitals.hospitalsapp.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.http.MediaType.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.*;

@Configuration
public class HospitalRequestRouter {

    @Bean
    public RouterFunction<?> routes(HospitalRequestHandler requestHandler) {
        return route(GET("/hospitals/{id}").and(accept(APPLICATION_JSON)), requestHandler::get)
                .andRoute(GET("/hospitalsall").and(accept(APPLICATION_JSON)), requestHandler::getAll)
                .andRoute(GET("/hospitalsstream"), requestHandler::stream)
                .andRoute(PUT("/hospitals/{id}").and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)),
                        requestHandler::put)
                .andRoute(DELETE("/hospitals/{id}"), requestHandler::delete);
    }
}