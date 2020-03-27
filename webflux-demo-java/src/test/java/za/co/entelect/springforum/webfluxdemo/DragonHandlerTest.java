package za.co.entelect.springforum.webfluxdemo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import za.co.entelect.springforum.webfluxdemo.dragons.Dragon;
import za.co.entelect.springforum.webfluxdemo.dragons.DragonHandler;
import za.co.entelect.springforum.webfluxdemo.dragons.repository.DragonRepository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DragonHandlerTest {

    @Mock
    DragonRepository dragonRepository;

    @InjectMocks
    DragonHandler dragonHandler;

    @Test
    void getDragonByName() {
        final MockServerRequest serverRequest = MockServerRequest.builder()
                .pathVariable("name", "Smaug")
                .build();

        when(dragonRepository.findByName("Smaug")).thenReturn(Mono.just(new Dragon(1, "Smaug", "Middle Earth")));

        StepVerifier.create(dragonHandler.getDragonByName(serverRequest))
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();

        verify(dragonRepository).findByName("Smaug");
    }

    @Test
    void getDragonByNameNotFound() {
        when(dragonRepository.findByName("Nothing")).thenReturn(Mono.empty());

        final MockServerRequest serverRequest = MockServerRequest.builder().pathVariable("name", "Nothing").build();

        StepVerifier.create(dragonHandler.getDragonByName(serverRequest))
                .expectNextMatches(serverResponse -> serverResponse.statusCode().equals(HttpStatus.NOT_FOUND))
                .verifyComplete();
    }
}