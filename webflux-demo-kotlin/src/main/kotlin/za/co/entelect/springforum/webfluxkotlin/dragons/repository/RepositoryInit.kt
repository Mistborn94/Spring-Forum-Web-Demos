package za.co.entelect.springforum.webfluxkotlin.dragons.repository

import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.data.r2dbc.connectionfactory.init.ResourceDatabasePopulator
import javax.annotation.PostConstruct

class RepositoryInit(private val connectionFactory: ConnectionFactory) {

    private val resourceDatabasePopulator = ResourceDatabasePopulator(
            ClassPathResource("schema.sql"),
            ClassPathResource("data.sql")
    )

    @PostConstruct
    fun init() {
        resourceDatabasePopulator.execute(connectionFactory).subscribe()
    }

}