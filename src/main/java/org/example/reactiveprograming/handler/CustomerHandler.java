package org.example.reactiveprograming.handler;

import org.example.reactiveprograming.dto.CustomerDTO;
import org.example.reactiveprograming.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public record CustomerHandler() {
    public Mono<ServerResponse> loadCustomers(ServerRequest request){
        Flux<CustomerDTO> customerList = CustomerRepository.loadAllCustomerList();
        return ServerResponse.ok().body(customerList, CustomerDTO.class);
    }


    public Mono<ServerResponse> findCustomer(ServerRequest request){
        int customerId= Integer.valueOf( request.pathVariable("input"));
        // dao.getCustomerList().filter(c->c.getId()==customerId).take(1).single();
        Mono<CustomerDTO> customerMono = CustomerRepository.loadAllCustomerList().filter(c -> c.getId() == customerId).next();
        return ServerResponse.ok().body(customerMono, CustomerDTO.class);
    }


    public Mono<ServerResponse> saveCustomer(ServerRequest request){
        Mono<CustomerDTO> customerMono = request.bodyToMono(CustomerDTO.class);
        Mono<String> saveResponse = customerMono.map(dto -> dto.getId() + ":" + dto.getName());
        return ServerResponse.ok().body(saveResponse,String.class);
    }
}
