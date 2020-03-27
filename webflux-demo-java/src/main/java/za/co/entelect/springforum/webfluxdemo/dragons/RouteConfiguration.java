package za.co.entelect.springforum.webfluxdemo.dragons;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouteConfiguration {

    private final DragonHandler dragonHandler;

    public RouteConfiguration(DragonHandler dragonHandler) {
        this.dragonHandler = dragonHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> dragonRoutes() {
        return route()
                .GET("/dragons",
                        request -> request.queryParam("location").isPresent(),
                        dragonHandler::getDragonsByLocation
                )
                .GET("/dragons", dragonHandler::getAllDragons)
                .GET("/dragons/{name}", dragonHandler::getDragonByName)
                .POST("/dragons", dragonHandler::createDragon)
                .build();
    }
}

