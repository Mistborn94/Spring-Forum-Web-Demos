package za.co.entelect.springforum.webfluxdemo;

import lombok.Data;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import za.co.entelect.springforum.webfluxdemo.dragons.DragonsException;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/intro")
public class SimpleRestController {

    private final WebClient webClient;
    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    public SimpleRestController(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping("/mono/basic")
    public Mono<Point> getMono() {
        return Mono.just(randomPoint());
    }

    @GetMapping("/flux/basic")
    public Flux<Point> getFlux() {
        return Flux.<Point>generate(sink -> sink.next(randomPoint()))
                .delayElements(Duration.ofMillis(200))
                .take(10);
    }

    private Point randomPoint() {
        return new Point(random.nextInt(20), random.nextInt(20));
    }

    @GetMapping("/mono/empty")
    public Mono<Point> getEmptyMono() {
        return Mono.empty();
    }

    @GetMapping("/mono/error")
    public Mono<Point> getErrorMono() {
        return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "Here be dragons!"));
    }

    @GetMapping("/flux/error")
    public Flux<Point> getErrorFlux() {
        return Flux.error(new DragonsException("Here be many dragons!"));
    }

    @GetMapping(value = "/flux/stream", produces = "application/stream+json")
    public Flux<Point> streamFlux() {
        return Flux.<Point>generate(sink -> sink.next(randomPoint()))
                .delayElements(Duration.ofMillis(200))
                .take(10);
    }

    @GetMapping(value = "/web-client")
    public Mono<Map<String, Object>> webClient() {

        return webClient
                .get()
                .uri("/get?hello=world")
                .header("x-spring-forum-test", "Spring Web")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {});
    }



    @Data
    public static class Point {
        final int x;
        final int y;
    }
}
