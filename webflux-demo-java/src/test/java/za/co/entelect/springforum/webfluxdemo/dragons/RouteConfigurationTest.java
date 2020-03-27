package za.co.entelect.springforum.webfluxdemo.dragons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RouteConfigurationTest {

    @Mock
    DragonHandler dragonHandler;

    @InjectMocks
    RouteConfiguration routeConfiguration;

    WebTestClient webTestClient;

    @BeforeEach
    public void init() {
        webTestClient = WebTestClient.bindToRouterFunction(routeConfiguration.dragonRoutes()).build();
    }

    @Test
    public void dragonByLocation() {
        when(dragonHandler.getDragonsByLocation(any())).thenReturn(ServerResponse.ok().build());

        webTestClient.get()
                .uri("/dragons?location=Berk")
                .exchange()
                .expectStatus().isOk();

        verify(dragonHandler).getDragonsByLocation(any());
    }

}