package org.example.reactiveprograming.repository.impl;

import org.apache.ibatis.session.SqlSessionFactory;
import org.example.reactiveprograming.model.Product;
import org.example.reactiveprograming.repository.ProductRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ProductRepositoryImpl extends BaseRepository implements ProductRepository {
    private static final String MAPPER_CLASS = "org.example.reactiveprograming.mapper.mybatis.ProductMapper.";

    public ProductRepositoryImpl(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }

    @Override
    public Flux<Product> findAll() {
        return this.applyFlux(sqlSession -> sqlSession.selectList(MAPPER_CLASS + "findAll"));
    }

    @Override
    public Mono<Integer> insertProduct(Product product) {
        return this.applyMono(sqlSession -> sqlSession.insert(MAPPER_CLASS + "insertProduct" , product));
    }

    @Override
    public Mono<Product> findOne() {
        return this.applyMono((session) -> session.selectOne(MAPPER_CLASS + "findOne"));
    }

}
