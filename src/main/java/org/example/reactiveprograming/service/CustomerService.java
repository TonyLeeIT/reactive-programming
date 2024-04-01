package org.example.reactiveprograming.service;

import org.example.reactiveprograming.dto.CustomerDTO;
import org.example.reactiveprograming.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
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
        var finishedTime = System.currentTimeMillis();
        System.out.println("Duration: " + (finishedTime - startedTime) / 1000 + "s");
        return customers;
    }
}
