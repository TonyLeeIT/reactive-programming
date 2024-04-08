package org.example.reactiveprograming.controller;


import org.example.reactiveprograming.configuration.LogChannel;
import org.example.reactiveprograming.dto.CustomerDTO;
import org.example.reactiveprograming.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/customers")
public record CustomerController(CustomerService customerService) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDTO> getAll() {
        var customers = customerService.getAllCustomers();
        LogChannel.push(customers.toString());
        return customers;
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Flux<CustomerDTO> getAllCustomersStream() {
        return customerService.getAllCustomersStream();
    }
}
