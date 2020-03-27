package za.co.entelect.springforum.webfluxdemo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class ReactorDemo {

	@Test
	public void intro() {
		Mono<String> mono = Mono.just("Hello World!");
		mono.map(String::toUpperCase)
				.subscribe(System.out::println);

		Flux<String> flux = Flux.just("Hello", "World", "!");
		flux.map(String::toUpperCase)
				.subscribe(System.out::println);
	}

	@Test
	void fluxOperators() {
		Flux.range(1, 10)
				.doFirst(() -> System.out.println("Starting..."))
				.doFinally(signalType -> System.out.println("Finished"))
				.doOnNext(x -> System.out.println("Emit " + x))
				.map(value -> value + 2)
				.filter(value -> value % 2 == 0)
				.window(3)
				.flatMap(flux -> flux.reduce(Integer::sum))
				.subscribe(x -> System.out.println("Subscribe " + x));
	}


}
