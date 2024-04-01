package org.example.reactiveprograming.repository;

import org.example.reactiveprograming.model.Product;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository {
    Mono<Product> findOne();
    Flux<Product> findAll();
    Mono<Integer> insertProduct(Product product);
}
