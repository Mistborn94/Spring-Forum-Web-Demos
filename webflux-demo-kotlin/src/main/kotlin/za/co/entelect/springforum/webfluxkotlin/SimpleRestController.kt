package za.co.entelect.springforum.webfluxkotlin

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.SynchronousSink
import za.co.entelect.springforum.webfluxkotlin.dragons.DragonsException
import java.time.Duration
import java.util.concurrent.ThreadLocalRandom

@RestController
class SimpleRestController(private val webClient: WebClient) {

    private val random = ThreadLocalRandom.current()

    @GetMapping("/mono/basic")
    fun getMono(): Mono<Point> = Mono.just(randomPoint())

    @GetMapping("/flux/basic")
    fun getFlux(): Flux<Point?> = Flux.generate { sink: SynchronousSink<Point?> -> sink.next(randomPoint()) }
            .delayElements(Duration.ofMillis(200))
            .take(10)

    private fun randomPoint(): Point {
        return Point(random.nextInt(20), random.nextInt(20))
    }

    @GetMapping("/mono/empty")
    fun getEmptyMono(): Mono<Point> = Mono.empty()

    @GetMapping("/mono/error")
    fun getErrorMono(): Mono<Point> = Mono.error(ResponseStatusException(HttpStatus.FORBIDDEN, "Here be dragons!"))

    @GetMapping("/flux/error")
    fun getErrorFlux(): Flux<Point> = Flux.error(DragonsException("Here be many dragons!"))

    @GetMapping(value = ["/flux/stream"], produces = ["application/stream+json"])
    fun streamFlux(): Flux<Point?> {
        return Flux.generate { sink: SynchronousSink<Point?> -> sink.next(randomPoint()) }
                .delayElements(Duration.ofMillis(200))
                .take(10)
    }

    @GetMapping("/web-client")
    fun webClient(): Mono<Map<String, Any>> = webClient
            .get()
            .uri("/get?hello=world")
            .header("x-spring-forum-test", "Spring Web Kotlin")
            .retrieve()
            .bodyToMono()

    data class Point(val x: Int, val y: Int)

}