package org.example.reactiveprograming.repository;

import org.example.reactiveprograming.dto.CustomerDTO;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
public interface CustomerRepository {

    static List<CustomerDTO> loadAllCustomers() {
        return IntStream.rangeClosed(1, 10)
                .peek(i -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
                .peek(i -> System.out.println("Thread-" +Thread.currentThread().getName() +" processing count : " + i))
                .mapToObj(i -> new CustomerDTO(i, "customer" + i))
                .collect(Collectors.toList());
    }

    static Flux<CustomerDTO> loadAllCustomersStream() {
        return Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(i -> System.out.println("Thread- " + Thread.currentThread().getName() + " processing count in stream flow : " + i))
                .map(i -> new CustomerDTO(i, "customer" + i));
    }

    static Flux<CustomerDTO> loadAllCustomerList()  {
        return Flux.range(1,50)
                .doOnNext(i -> System.out.println("Thread- " + Thread.currentThread().getName() + " processing count in stream flow : " + i))
                .map(i -> new CustomerDTO(i, "customer" + i));
    }
}
