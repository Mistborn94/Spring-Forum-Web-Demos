package za.co.entelect.springforum.webfluxdemo.dragons;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import za.co.entelect.springforum.webfluxdemo.dragons.repository.DragonRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebFluxTest(DragonsFunctionalRoutes.class)
class RouteConfigurationTest {

    @MockBean
    DragonRepository dragonRepository;

    @Autowired
    WebTestClient webTestClient;

    @Test
    void dragonByLocation() {
        when(dragonRepository.findByLocation(any())).thenReturn(Flux.just(new Dragon(1, "Toothless", "Berk")));

        webTestClient.get()
                .uri("/fn/dragons?location=Berk")
                .exchange()
                .expectStatus().isOk();

        verify(dragonRepository).findByLocation("Berk");
    }

    @Test
    void allDragons() {
        when(dragonRepository.findAll()).thenReturn(Flux.just(new Dragon(1, "Toothless", "Berk")));

        webTestClient.get()
                .uri("/fn/dragons")
                .exchange()
                .expectStatus().isOk();

        verify(dragonRepository).findAll();
    }

}