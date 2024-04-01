package org.example.reactiveprograming.service;

import org.example.reactiveprograming.dto.ProductDTO;
import org.example.reactiveprograming.mapper.modelmapper.ProductObjMapper;
import org.example.reactiveprograming.repository.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public record ProductService(ProductRepository productRepository, ProductObjMapper mapper) {

    public Mono<ProductDTO> getOne() {
        return mapper.mapDTO(productRepository::findOne);
    }

    public Flux<ProductDTO> getAll() {
        return mapper.mapDTOStream(productRepository::findAll);
    }

    public Mono<ProductDTO> createANewProduct(Mono<ProductDTO> newProduct) {
        return newProduct.map(mapper::toModel).doOnNext(productRepository::insertProduct).map(mapper::toDTO);
    }
}
