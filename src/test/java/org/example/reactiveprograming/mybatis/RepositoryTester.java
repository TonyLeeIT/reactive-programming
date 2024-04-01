package org.example.reactiveprograming.mybatis;

import org.example.reactiveprograming.dto.ProductDTO;
import org.example.reactiveprograming.mapper.mybatis.ProductMapper;
import org.example.reactiveprograming.model.Product;
import org.example.reactiveprograming.repository.ProductRepository;
import org.example.reactiveprograming.repository.impl.ProductRepositoryImpl;
import org.example.reactiveprograming.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RepositoryTester {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper mapper;

    @Autowired
    private ProductService service;

//    public RepositoryTester(ProductMapper mapper) {
//        this.mapper = mapper;
//    }

    @Test
    void testRepository() {
        //System.out.println(mapper.findOne());;
        //System.out.println(mapper.findOne());
        //productRepository.findOne().log().subscribe();
        //productRepository.findAll().log().subscribe();
        //service.getOne().log().subscribe();

        var product = Product.builder()
                .id("4")
                .name("Macbook pro 14inch M2 Pro")
                .qty(2)
                .price(80000000L)
                .build();
        productRepository.insertProduct(product).log().subscribe(System.out::println);
    }
}
