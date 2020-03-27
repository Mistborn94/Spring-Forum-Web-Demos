package za.co.entelect.springforum.webfluxdemo.dragons.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import za.co.entelect.springforum.webfluxdemo.dragons.Dragon;

@Repository
public interface DragonRepository extends ReactiveCrudRepository<Dragon, Integer> {

    @Query("SELECT * FROM dragons d WHERE d.location = :location")
    Flux<Dragon> findByLocation(String location);

    @Query("SELECT * FROM dragons d WHERE d.name = :name")
    Mono<Dragon> findByName(String name);
}
