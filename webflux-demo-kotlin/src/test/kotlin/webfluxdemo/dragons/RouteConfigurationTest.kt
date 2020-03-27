package webfluxdemo.dragons

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.server.ServerResponse
import za.co.entelect.springforum.webfluxkotlin.dragons.DragonHandler
import za.co.entelect.springforum.webfluxkotlin.dragons.dragonRoutes

@ExtendWith(MockitoExtension::class)
class RouteConfigurationTest {
    @Mock
    lateinit var dragonHandler: DragonHandler

    lateinit var webTestClient: WebTestClient

    @BeforeEach
    fun init() {
        val routes = dragonRoutes(dragonHandler)

        webTestClient = WebTestClient.bindToRouterFunction(routes).build()
    }

    @Test
    fun dragonByLocation() {
        `when`(dragonHandler.getDragonsByLocation(ArgumentMatchers.any())).thenReturn(ServerResponse.ok().build())

        webTestClient.get()
                .uri("/dragons?location=Berk")
                .exchange()
                .expectStatus().isOk

        verify(dragonHandler).getDragonsByLocation(ArgumentMatchers.any())
    }
}