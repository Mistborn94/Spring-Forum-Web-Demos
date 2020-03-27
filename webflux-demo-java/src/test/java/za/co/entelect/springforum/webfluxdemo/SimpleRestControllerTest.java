package za.co.entelect.springforum.webfluxdemo;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.*;

@WebFluxTest(SimpleRestController.class)
class SimpleRestControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void doSomething() {
        assertNull(null);
    }

    @Mock
    ExchangeFunction exchangeFunction;

    @Test
    public void test() {
        final WebClient webClient = WebClient.builder()
                .exchangeFunction(exchangeFunction)
                .build();

        assertNull(null);
    }

}