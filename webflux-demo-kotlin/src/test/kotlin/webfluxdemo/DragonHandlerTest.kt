package webfluxdemo

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.mock.web.reactive.function.server.MockServerRequest
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import za.co.entelect.springforum.webfluxkotlin.dragons.Dragon
import za.co.entelect.springforum.webfluxkotlin.dragons.DragonHandler
import za.co.entelect.springforum.webfluxkotlin.dragons.repository.DragonRepository

@ExtendWith(MockitoExtension::class)
internal class DragonHandlerTest {
    @Mock
    lateinit var dragonRepository: DragonRepository

    @InjectMocks
    lateinit var dragonHandler: DragonHandler

    @Test
    fun getDragonByName() {
        val serverRequest = MockServerRequest.builder()
                .pathVariable("name", "Smaug")
                .build()

        `when`(dragonRepository.findByName("Smaug")).thenReturn(Mono.just(Dragon("Smaug", "Middle Earth", 1)))

        StepVerifier.create(dragonHandler.getDragonByName(serverRequest))
                .expectNextMatches { serverResponse -> serverResponse.statusCode().is2xxSuccessful }
                .verifyComplete()

        verify(dragonRepository).findByName("Smaug")
    }

    @Test
    fun getDragonByNameNotFound() {
        `when`(dragonRepository.findByName("Nothing")).thenReturn(Mono.empty())

        val serverRequest = MockServerRequest.builder().pathVariable("name", "Nothing").build()

        StepVerifier.create(dragonHandler.getDragonByName(serverRequest))
                .expectNextMatches { serverResponse -> serverResponse.statusCode() == HttpStatus.NOT_FOUND }
                .verifyComplete()
    }
}