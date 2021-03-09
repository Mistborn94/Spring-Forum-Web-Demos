package za.co.entelect.springforum.webfluxdemo.dragons.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import za.co.entelect.springforum.webfluxdemo.dragons.Dragon;

@Repository
public interface DragonRepository extends ReactiveCrudRepository<Dragon, Integer> {

    Flux<Dragon> findByLocation(String location);

    Mono<Dragon> findByNameEqualsIgnoreCase(String name);
}
