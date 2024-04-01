package org.example.reactiveprograming;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ReactiveCoreTutorial {

    private Flux<String> testFlux() {
        var programmingLanguages = List.of("Java", "C++", "Python", "Typescript");
        return Flux.fromIterable(programmingLanguages).log().concatWith(Flux.error(new RuntimeException("Something went wrong")));
    }

    private Flux<String> testMap() {
        return testFlux().map(e -> e.toUpperCase(Locale.ROOT)).log();
    }

    private Flux<String> testSkip() {
        var flux = testFlux().delayElements(Duration.ofSeconds(1)).log();
        return flux.skip(Duration.ofSeconds(2));
    }
    private Flux<String> testFlatMap() {
        Flux<String> flux = Flux.just("Java", "Cpp", "Rust", "Dart");
        return flux
                .flatMap(s -> Mono.just(s.toUpperCase(Locale.ROOT)));
    }

    private Flux<String> testBasicSkip() {
        Flux<String> flux = Flux.just("Java", "Cpp", "Rust", "Dart")
                .delayElements(Duration.ofSeconds(1));
        //flux.skip(2);
        //return flux.skip(Duration.ofMillis(2010));
        return flux.skipLast(2);
    }

    private Flux<Integer> testComplexSkip() {
        Flux<Integer> flux = Flux.range(1, 20);
        return flux.skipUntil(integer -> integer == 30);
    }

    private Flux<Integer> testConcat() {
        Flux<Integer> flux1 = Flux.range(1, 20);
        Flux<Integer> flux2 = Flux.range(101, 20);
        Flux<Integer> flux3 = Flux.range(1001, 20);
        return Flux.concat(flux3, flux2, flux1);
    }

    private Flux<Integer> testMerge() {
        Flux<Integer> flux1 = Flux.range(1, 20)
                .delayElements(Duration.ofMillis(500));
        Flux<Integer> flux2 = Flux.range(101, 20)
                .delayElements(Duration.ofMillis(500));

        return Flux.merge(flux1, flux2);
    }

    private Flux<Tuple2<Integer, Integer>> testZip() {
        Flux<Integer> flux1 = Flux.range(1, 10)
                .delayElements(Duration.ofMillis(500));
        Flux<Integer> flux2 = Flux.range(101, 20)
                .delayElements(Duration.ofMillis(500));
        return Flux.zip(flux1, flux2);
    }

    private Flux<Tuple2<Integer, Integer>> testComplexZip() {
        Flux<Integer> flux = Flux.range(1, 10)
                .delayElements(Duration.ofMillis(500));
        Mono<Integer> mono = Mono.just(1);
        return Flux.zip(flux, mono);
    }

    private Mono<List<Integer>> testCollect() {
        Flux<Integer> flux = Flux.range(1, 10)
                .delayElements(Duration.ofMillis(1000));
        return flux.collectList();
    }

    private Flux<List<Integer>> testBuffer() {
        Flux<Integer> flux = Flux.range(1, 10)
                .delayElements(Duration.ofMillis(1000));
        return flux.buffer(Duration.ofMillis(3_100));
    }

    private Mono<Map<Integer, Integer>> testMapCollection() {
        //a, a*a
        //5, 25
        //6, 36
        Flux<Integer> flux = Flux.range(1, 10);
        return flux.collectMap(integer -> integer * 2, integer -> integer * integer);
    }

    private Flux<Integer> testDoFunctions() {
        Flux<Integer> flux = Flux.range(1, 10);
        return flux.doOnEach(signal -> {
            if (signal.getType() == SignalType.ON_COMPLETE) {
                System.out.println("I am done!");
            } else {
                System.out.println(signal.get());
            }
        });
    }

    private Flux<Integer> testDoFunctions2() {
        Flux<Integer> flux = Flux.range(1, 10);
        return flux.doOnComplete(() -> System.out.println("I am complete"));
    }

    private Flux<Integer> testDoFunctions3() {
        Flux<Integer> flux = Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(1));
        return flux.doOnCancel(() -> System.out.println("Cancelled!"));
    }

    private Flux<Integer> testErrorHandling() {
        Flux<Integer> flux = Flux.range(1, 10)
                .map(integer -> {
                    if (integer == 5) {
                        throw new RuntimeException("Unexpected number!");
                    }
                    return integer;
                });
        return flux
                .onErrorContinue((throwable, o) -> System.out.println("Don't worry about " + o));
    }

    private Flux<Integer> testErrorHandling2() {
        Flux<Integer> flux = Flux.range(1, 10)
                .map(integer -> {
                    if (integer == 5) {
                        throw new RuntimeException("Unexpected number!");
                    }
                    return integer;
                });
        return flux
                .onErrorResume(throwable -> Flux.range(100, 5));
    }

    private Flux<Integer> testErrorHandling3() {
        Flux<Integer> flux = Flux.range(1, 10)
                .map(integer -> {
                    if (integer == 5) {
                        throw new RuntimeException("Unexpected number!");
                    }
                    return integer;
                });
        return flux
                .onErrorMap(throwable -> new UnsupportedOperationException(throwable.getMessage()));
    }

    public static void main(String[] args) throws InterruptedException {
        final var main = new ReactiveCoreTutorial();


        main.testFlux().subscribe(System.out::println , System.out::println);
        //main.testMap().subscribe(System.out::println);
        //main.testSkip().subscribe(System.out::println);
        Thread.sleep(10_000);
    }
}
