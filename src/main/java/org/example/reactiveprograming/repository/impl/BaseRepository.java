package org.example.reactiveprograming.repository.impl;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public abstract class BaseRepository {
    private final SqlSessionFactory sqlSessionFactory;

    BaseRepository(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    protected <R> Mono<R> applyMono(Function<SqlSession, R> function) {
        var result = applyFunctionMono(function);
        return Objects.isNull(result) ? Mono.empty() : Mono.just(result);
    }

    protected <R> Flux<R> applyFlux(Function<SqlSession, List<R>> function) {
        return Flux.fromIterable(applyFunctionFlux(function));
    }

    private <R> R applyFunctionMono(Function<SqlSession, R> function) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return function.apply(session);
        }
    }

    private <R> List<R> applyFunctionFlux(Function<SqlSession, List<R>> function) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return function.apply(session);
        }
    }
}
