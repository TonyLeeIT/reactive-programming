package org.example.reactiveprograming.mapper.modelmapper;

import org.example.reactiveprograming.dto.ProductDTO;
import org.example.reactiveprograming.model.Product;
import org.mapstruct.Mapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.function.Supplier;

@Mapper(uses = {}, componentModel = "spring")
public interface ProductObjMapper extends ModelMapper<Product , ProductDTO>{

    /*
    * Convert Product to ProductDTO
    * Emit product to mono publisher
    */
    default Mono<ProductDTO> transformDTO(Product product){
        return Mono.just(this.toDTO(product));
    }

    default Mono<ProductDTO> mapDTO(Supplier<Mono<Product>> supplier){return supplier.get().map(this::toDTO);}

    default Flux<ProductDTO> mapDTOStream(Supplier<Flux<Product>> supplier){return supplier.get().map(this::toDTO);}
}
