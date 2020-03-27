package za.co.entelect.springforum.webfluxkotlin.dragons.repository

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import za.co.entelect.springforum.webfluxkotlin.dragons.Dragon

interface DragonRepository : ReactiveCrudRepository<Dragon, Int> {
    @Query("SELECT * FROM dragons d WHERE d.location = :location")
    fun findByLocation(location: String): Flux<Dragon>

    @Query("SELECT * FROM dragons d WHERE d.name = :name")
    fun findByName(name: String): Mono<Dragon>
}