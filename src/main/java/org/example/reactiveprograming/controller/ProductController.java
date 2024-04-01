package org.example.reactiveprograming.controller;

import org.example.reactiveprograming.dto.ProductDTO;
import org.example.reactiveprograming.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/product")
public record ProductController(ProductService service) {

    @GetMapping("/first")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ProductDTO> takeFirstProduct(){return service.getOne();}

    @GetMapping(value = "/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Flux<ProductDTO> takeAll(){return service.getAll();}

    @PostMapping("new-product")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductDTO> insertNewProduct(@RequestBody Mono<ProductDTO> newProduct){return service.createANewProduct(newProduct);}
}
