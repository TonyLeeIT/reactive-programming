package org.example.reactiveprograming.mapstruct;

import org.example.reactiveprograming.dto.ProductDTO;
import org.example.reactiveprograming.mapper.modelmapper.ProductObjMapper;
import org.example.reactiveprograming.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ModelMapperTest {

    @Autowired
    private ProductObjMapper mapper;

    @Test
    void testMapper() {
        var product = Product.builder()
                .id("1")
                .name("Apple Watch")
                .qty(1)
                .price(500L)
                .build();

        var productDTO = ProductDTO.builder()
                .id("1")
                .name("Apple Watch")
                .qty(1)
                .price(500L)
                .build();

        var productStream = mapper.transformDTO(product).log();
        System.out.println(mapper.toDTO(product));
        System.out.println(mapper.toModel(productDTO));
        productStream.subscribe();
    }
}
