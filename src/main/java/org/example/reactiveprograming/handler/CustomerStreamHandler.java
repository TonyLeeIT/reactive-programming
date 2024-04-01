package org.example.reactiveprograming.handler;

import org.example.reactiveprograming.dto.CustomerDTO;
import org.example.reactiveprograming.repository.CustomerRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public record CustomerStreamHandler() {
    public Mono<ServerResponse> getCustomers(ServerRequest request) {
        Flux<CustomerDTO> customersStream = CustomerRepository.loadAllCustomersStream();
        return ServerResponse.ok().
                contentType(MediaType.TEXT_EVENT_STREAM)
                .body(customersStream, CustomerDTO.class);
    }
}
