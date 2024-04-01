package org.example.reactiveprograming.api;

import org.example.reactiveprograming.controller.ProductController;
import org.example.reactiveprograming.dto.ProductDTO;
import org.example.reactiveprograming.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(ProductController.class)
class ApiTesting {

    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private ProductService service;

    @Test
    void getProductsTest() {
        Flux<ProductDTO> productDtoFlux = Flux.just(
                new ProductDTO("102", "mobile", 1, 10000),
                new ProductDTO("103", "TV", 1, 50000));
        when(service.getAll()).thenReturn(productDtoFlux);

        Flux<ProductDTO> responseBody = webTestClient.get().uri("/product/all")
                .exchange()
                .expectStatus().isOk()
                .returnResult(ProductDTO.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNext(new ProductDTO("102", "mobile", 1, 10000))
                .expectNext(new ProductDTO("103", "TV", 1, 50000))
                .verifyComplete();
    }

    @Test
    void addProductTest(){
        Mono<ProductDTO> productDtoMono=Mono.just(new ProductDTO("102","mobile",1,10000));
        when(service.createANewProduct(productDtoMono)).thenReturn(productDtoMono);

        webTestClient.post().uri("/product/new-product")
                .body(Mono.just(productDtoMono),ProductDTO.class)
                .exchange()
                .expectStatus().isCreated();//201

    }

}
