package org.example.reactiveprograming;

import org.springframework.scheduling.annotation.Async;
import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class BackPressureTutorial {

    private Flux<Long> createNoOverflow(){
        return Flux.range(1, 20).log()
                .concatMap(x -> Mono.delay(Duration.ofMillis(1000)));
    }

    protected Flux<Long> createDropOnBackpressureFlux() {
        return Flux.interval(Duration.ofMillis(1))
                .onBackpressureDrop()
                .concatMap(a -> Mono.delay(Duration.ofMillis(100)).thenReturn(a))
                .doOnNext(a -> System.out.println("Element kept by consumer: " + a));
    }

    private Flux<Long> createOverflowFlux() {
        return Flux.interval(Duration.ofMillis(1))
                .log()
                .concatMap(x -> Mono.delay(Duration.ofMillis(100)));
    }

    private Flux<Long> createBufferOnBackpressureFlux() {
        return Flux.interval(Duration.ofMillis(1))
                .onBackpressureBuffer(50, BufferOverflowStrategy.DROP_LATEST)
                .concatMap(a -> Mono.delay(Duration.ofMillis(100)).thenReturn(a))
                .doOnNext(a -> System.out.println("Element kept by consumer: " + a));
    }


    public static void main(String[] args) {
        BackPressureTutorial main  = new BackPressureTutorial();
        var startedTime = System.currentTimeMillis();
        System.out.println("Start Time: " +startedTime);

        //main.createNoOverflow().blockLast();
        main.createDropOnBackpressureFlux().blockLast();

        var finishedTime = System.currentTimeMillis();
        System.out.println("Finish Time: " + finishedTime);
        System.out.println("Duration Time " + (finishedTime -startedTime) /1000 + "s");
    }
}
