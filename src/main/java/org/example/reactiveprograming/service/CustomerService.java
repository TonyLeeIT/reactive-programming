package org.example.reactiveprograming.service;

import lombok.extern.slf4j.Slf4j;
import org.example.reactiveprograming.dto.CustomerDTO;
import org.example.reactiveprograming.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
@Slf4j
public record CustomerService() {

    public List<CustomerDTO> getAllCustomers() {
        var startedTime = System.currentTimeMillis();
        var customers = CustomerRepository.loadAllCustomers();
        var finishedTime = System.currentTimeMillis();
        System.out.println("Duration: " + (finishedTime - startedTime) / 1000 + "s");
        return customers;
    }

    public Flux<CustomerDTO> getAllCustomersStream() {
        var startedTime = System.currentTimeMillis();
        var customers = CustomerRepository.loadAllCustomersStream();
        log.info("hihihihihihihi");
        var finishedTime = System.currentTimeMillis();
        System.out.println("Duration: " + (finishedTime - startedTime) / 1000 + "s");
        return customers;
    }
}
