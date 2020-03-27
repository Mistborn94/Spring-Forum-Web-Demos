package za.co.entelect.springforum.webfluxkotlin

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.client.WebClient
import za.co.entelect.springforum.webfluxkotlin.dragons.DragonHandler
import za.co.entelect.springforum.webfluxkotlin.dragons.dragonRoutes
import za.co.entelect.springforum.webfluxkotlin.dragons.repository.RepositoryInit

@SpringBootApplication
class WebfluxKotlinDemoApplication

val beans = beans {
	bean { WebClient.create("http://httpbin.org") }
	bean<RepositoryInit>()
	bean<DragonHandler>()
	bean(::dragonRoutes)
}

fun main(vararg args: String) {
	SpringApplication(WebfluxKotlinDemoApplication::class.java).apply {
		addInitializers(beans)
	}.run(*args)
}



