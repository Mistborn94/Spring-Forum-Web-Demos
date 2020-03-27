package za.co.entelect.springforum.webfluxkotlin.dragons

import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.*
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import za.co.entelect.springforum.webfluxkotlin.dragons.repository.DragonRepository
import java.net.URI

class DragonHandler(private val dragonRepository: DragonRepository) {

    fun getAllDragons(serverRequest: ServerRequest?) = ok().body(dragonRepository.findAll())

    fun getDragonByName(serverRequest: ServerRequest): Mono<ServerResponse> =
            serverRequest.pathVariable("name")
                    .toMono()
                    .flatMap(dragonRepository::findByName)
                    .flatMap { ok().bodyValue(it) }
                    .switchIfEmpty(notFound().build())

    fun getDragonsByLocation(serverRequest: ServerRequest) =
            serverRequest.queryParamOrNull("location")
                    .toMonoOrEmpty()
                    .flatMapMany(dragonRepository::findByLocation)
                    .let { ok().body(it) }


    fun createDragon(serverRequest: ServerRequest) =
            serverRequest.bodyToMono<Dragon>()
                    .flatMap { dragonRepository.save(it) }
                    .flatMap {
                        created(URI("/dragons/${it.name}")).bodyValue(it)
                    }
}

private fun <T> T?.toMonoOrEmpty() = let { Mono.justOrEmpty(this) }
