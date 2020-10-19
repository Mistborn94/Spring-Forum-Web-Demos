package za.co.entelect.springforum.webfluxdemo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

class ReactorDemo {

    @Test
    void just() {
        Mono.just("Hello World!")
                .subscribe(x -> println("Mono Item " + x), error -> println("Error " + error));

        println("=========");

        Flux.just("Hello", "World", "!")
                .subscribe(x -> println("Flux Item " + x), error -> println("Error " + error));

        println("=========");

        Mono.error(new RuntimeException("Some Error"))
                .subscribe(x -> println("Mono " + x), error -> println("Error " + error));
    }

    @Test
    void async() throws InterruptedException {

        Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(10))
                .subscribe(x -> println("Flux Item " + x), error -> println("Error " + error));

        Flux.just("1", "2", "3")
                .delayElements(Duration.ofMillis(10))
                .subscribe(x -> println("Flux Item " + x), error -> println("Error " + error));

        Thread.sleep(50);
    }

    @Test
    void hotAndCold() {
        //HOT -> Random happens before mono is subscribed to
        //'just' us hot
        final Mono<Integer> hotMono = Mono.just(ThreadLocalRandom.current().nextInt(20));

        hotMono.subscribe(x -> println("Hot Item " + x), error -> println("Error " + error));
        hotMono.subscribe(x -> println("Hot Item " + x), error -> println("Error " + error));
        hotMono.subscribe(x -> println("Hot Item " + x), error -> println("Error " + error));

        println("=========");

        //COLD -> Random is evaluated lazily
        final Mono<Integer> coldMono = Mono.fromCallable(() -> ThreadLocalRandom.current().nextInt(20));
        coldMono.subscribe(x -> println("Cold Item " + x), error -> println("Error " + error));
        coldMono.subscribe(x -> println("Cold Item " + x), error -> println("Error " + error));
        coldMono.subscribe(x -> println("Cold Item " + x), error -> println("Error " + error));


        println("=========");
        //COLD -> Defer makes random evaluation lazy
        final Mono<Integer> coldDeferred = Mono.defer(() -> Mono.just(ThreadLocalRandom.current().nextInt(20)));
        coldDeferred.subscribe(x -> println("Cold Item " + x), error -> println("Error " + error));
        coldDeferred.subscribe(x -> println("Cold Item " + x), error -> println("Error " + error));
        coldDeferred.subscribe(x -> println("Cold Item " + x), error -> println("Error " + error));

    }

    @Test
    void map() {
        Flux.just(1, 2, 3)
                .map(value -> getString(value, 2))
                .subscribe(value -> println("Flux Item " + value));
    }

    @Test
    void flatMap() throws InterruptedException {
        Flux.just(1, 2, 3)
                .flatMap(this::makeFlux)
                .subscribe(value -> println("Flux Item " + value));

        Thread.sleep(50);
    }

    private Flux<String> makeFlux(Integer value) {
        return Flux.just(getString(value, 1), getString(value, 2), getString(value, 3))
                .delayElements(Duration.ofMillis(1));
    }

    @Test
    void filter() throws InterruptedException {
        Flux.just(1, 2, 3, 4)
                .filter(value -> value % 2 == 0)
                .subscribe(value -> println("Flux Item " + value));
    }

    @Test
    void then() throws InterruptedException {
        Flux.just(1, 2, 3, 4)
                .doOnNext(value -> println("Saw Item " + value))
                .then(Mono.just(5))
                .subscribe(value -> println("Then Item " + value));
    }

    @Test
    void take() throws InterruptedException {
        Flux.just(1, 2, 3, 4, 5)
                .doOnNext(value -> println("Saw Item " + value))
                .take(2)
                .subscribe(value -> println("Flux Item " + value));

        final Mono<Integer> next = Flux.just(1, 2, 3, 4, 5)
                .doOnNext(value -> println("Saw Item " + value))
                .next(); //Like take 1, but it becomes a mono

        next.subscribe(value -> println("Mono Item " + value));
    }

    @Test
    void errorHandling() {
        final Mono<Object> errorMono = Mono.error(new RuntimeException("Some Error"));

        errorMono
                .doOnError(err -> println("DoOnError: " + err))
                .subscribe(value -> println("Subscribe: " + value),
                        err -> println("Error: " + err));

        println("============");

        errorMono
                .doOnError(err -> println("DoOnError: " + err))
                .onErrorMap(cause -> new IllegalArgumentException("Wrapper Exception", cause))
                .subscribe(value -> println("Subscribe: " + value),
                        err -> println("Error: " + err));

        println("============");

        errorMono
                .onErrorResume(err -> Mono.just("Fallback Mono for " + err))
                .subscribe(value -> println("Subscribe: " + value),
                        err -> println("Error: " + err));

        println("============");

        errorMono.onErrorReturn("Fallback Value")
                .subscribe(value -> println("Subscribe: " + value),
                        err -> println("Error: " + err));

    }

    @Test
    void publishOn() throws InterruptedException {
        Flux.just(1, 2, 3, 4, 5)
                .doOnNext(value -> println("Before PublishOn " + value))
                .publishOn(Schedulers.boundedElastic())
                .subscribe(value -> println("PublishOn Item " + value));

        Thread.sleep(50);
    }

    @Test
    void subscribeOn() throws InterruptedException {
        Flux.just(1, 2, 3, 4, 5)
                .doOnNext(value -> println("Before SubscribeOn " + value))
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(value -> println("SubscribeOn Item " + value));
    }

    private String getString(Integer value, int multiplier) {
        return String.format("%d * %d = %d", value, multiplier, value * multiplier);
    }

    private void println(String message) {
        System.out.println(String.format("[%s] %s", Thread.currentThread().getName(), message));
    }

}
