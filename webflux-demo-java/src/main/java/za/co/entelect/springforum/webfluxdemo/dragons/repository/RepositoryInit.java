package za.co.entelect.springforum.webfluxdemo.dragons.repository;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

import javax.annotation.PostConstruct;

@Configuration
public class RepositoryInit {

    private final ConnectionFactory connectionFactory;

    private final ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(
            new ClassPathResource("schema.sql"),
            new ClassPathResource("data.sql")
    );

    public RepositoryInit(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @PostConstruct
    public void init() {
        resourceDatabasePopulator.populate(connectionFactory).subscribe();
    }
}
