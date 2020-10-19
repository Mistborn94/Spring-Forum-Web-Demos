package za.co.entelect.springforum.webfluxdemo.dragons;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import za.co.entelect.springforum.webfluxdemo.dragons.repository.DragonRepository;

import java.net.URI;

@Component
public class DragonHandler {

    private final DragonRepository dragonRepository;

    public DragonHandler(DragonRepository dragonRepository) {
        this.dragonRepository = dragonRepository;
    }

    public Mono<ServerResponse> getAllDragons(ServerRequest serverRequest) {
        return ServerResponse.ok().body(dragonRepository.findAll(), Dragon.class);
    }

    public Mono<ServerResponse> getDragonByName(ServerRequest serverRequest) {
        final String name = serverRequest.pathVariable("name");
        return dragonRepository.findByName(name)
                .flatMap(dragon -> ServerResponse.ok().bodyValue(dragon))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getDragonsByLocation(ServerRequest serverRequest) {
        final Flux<Dragon> locationDragons = Mono.justOrEmpty(serverRequest.queryParam("location"))
                .flatMapMany(dragonRepository::findByLocation);

        return ServerResponse.ok().body(locationDragons, Dragon.class);
    }

    public Mono<ServerResponse> createDragon(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Dragon.class)
                .flatMap(dragonRepository::save)
                .flatMap(dragon -> ServerResponse.created(URI.create("/dragons/" + dragon.getName())).bodyValue(dragon));
    }
}
