package za.co.entelect.springforum.webfluxdemo.dragons;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import za.co.entelect.springforum.webfluxdemo.dragons.repository.DragonRepository;

import java.util.Optional;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class DragonsFunctionalRoutes {

    private final DragonRepository dragonRepository;

    public DragonsFunctionalRoutes(DragonRepository dragonRepository) {
        this.dragonRepository = dragonRepository;
    }

    @Bean
    public RouterFunction<ServerResponse> dragonRoutes() {
        return route()
                .path("/fn", builder -> builder
                        .GET("/dragons",
                                serverRequest -> serverRequest.queryParam("location").isPresent(), //predicate
                                serverRequest -> findDragonsByLocation(serverRequest.queryParam("location")) //handler
                        )
                        .GET("/dragons", serverRequest -> ServerResponse.ok().body(dragonRepository.findAll(), Dragon.class))
                        .GET("/dragons/{name}", serverRequest -> findDragonsByName(serverRequest.pathVariable("name")))
                        .POST("/dragons", this::createDragon)
                ).build();
    }

    private Mono<ServerResponse> findDragonsByLocation(Optional<String> location) {
        final Flux<Dragon> locationDragons = Mono.justOrEmpty(location)
                .flatMapMany(dragonRepository::findByLocation);

        return ServerResponse.ok().body(locationDragons, Dragon.class);
    }

    private Mono<ServerResponse> findDragonsByName(String name) {
        return dragonRepository.findByNameEqualsIgnoreCase(name)
                .flatMap(dragon -> ServerResponse.ok().bodyValue(dragon))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    private Mono<ServerResponse> createDragon(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Dragon.class)
                .flatMap(dragonRepository::save)
                .flatMap(dragon -> ServerResponse.created(dragon.uri()).bodyValue(dragon));
    }
}

