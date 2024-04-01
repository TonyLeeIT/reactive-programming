package org.example.reactiveprograming;

import org.example.reactiveprograming.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

@SpringBootTest
class ReactiveProgramingApplicationTests {

    @Autowired
    private CustomerService customerService;

    @Test
    void contextLoads() throws InterruptedException {
        customerService.getAllCustomersStream().subscribe();
        Thread.sleep(Duration.ofSeconds(100));
    }
}
