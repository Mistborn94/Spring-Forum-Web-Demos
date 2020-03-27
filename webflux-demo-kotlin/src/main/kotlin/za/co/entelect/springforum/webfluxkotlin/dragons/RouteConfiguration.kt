package za.co.entelect.springforum.webfluxkotlin.dragons

import org.springframework.web.reactive.function.server.RouterFunctionDsl
import org.springframework.web.reactive.function.server.router

fun dragonRoutes(dragonHandler: DragonHandler) = router {
    "/dragons".nest {
        GET("/{name}") { dragonHandler.getDragonByName(it) }
        (GET("/") and queryParam("location")) { dragonHandler.getDragonsByLocation(it) }
        (GET("/") and !queryParam("location")){ dragonHandler.getAllDragons(it) }
        POST("/") { dragonHandler.createDragon(it) }
    }
}

private fun RouterFunctionDsl.queryParam(name: String) = queryParam(name) { true }


