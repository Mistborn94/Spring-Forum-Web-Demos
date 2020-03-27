package webfluxdemo

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.SignalType

internal class ReactorDemo {
    @Test
    fun intro() {
        val mono = Mono.just("Hello World!")
        mono.map { obj: String -> obj.toUpperCase() }
                .subscribe { x: String? -> println(x) }
        val flux = Flux.just("Hello", "World", "!")
        flux.map { obj: String -> obj.toUpperCase() }
                .subscribe { x: String? -> println(x) }
    }

    @Test
    fun fluxOperators() {
        Flux.range(1, 10)
                .doFirst { println("Starting...") }
                .doFinally { println("Finished") }
                .doOnNext { x: Int -> println("Emit $x") }
                .map { value: Int -> value + 2 }
                .filter { value: Int -> value % 2 == 0 }
                .window(3)
                .flatMap { flux: Flux<Int> -> flux.reduce { a: Int?, b: Int? -> Integer.sum(a!!, b!!) } }
                .subscribe { x: Int -> println("Subscribe $x") }
    }
}