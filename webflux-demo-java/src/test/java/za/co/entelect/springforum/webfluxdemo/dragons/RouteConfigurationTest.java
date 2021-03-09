package za.co.entelect.springforum.webfluxdemo.dragons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import za.co.entelect.springforum.webfluxdemo.dragons.repository.DragonRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RouteConfigurationTest {

    @Mock
    DragonRepository dragonRepository;

    @InjectMocks
    DragonsFunctionalRoutes routeConfiguration;

    WebTestClient webTestClient;

    @BeforeEach
    public void init() {
        webTestClient = WebTestClient.bindToRouterFunction(routeConfiguration.dragonRoutes()).build();
    }

    @Test
    void dragonByLocation() {
        when(dragonRepository.findByLocation(any())).thenReturn(Flux.just(new Dragon(1, "Toothless", "Berk")));

        webTestClient.get()
                .uri("/fn/dragons?location=Berk")
                .exchange()
                .expectStatus().isOk();

        verify(dragonRepository).findByLocation("Berk");
    }

}