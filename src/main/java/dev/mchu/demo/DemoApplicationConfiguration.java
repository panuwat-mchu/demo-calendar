package dev.mchu.demo;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.mapping.R2dbcMappingContext;
import org.springframework.data.relational.core.mapping.DefaultNamingStrategy;
import org.springframework.data.relational.core.mapping.NamingStrategy;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.util.Assert;

import java.util.Optional;

@Configuration
public class DemoApplicationConfiguration {
    @Bean
    public ConnectionFactoryInitializer databaseInitializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);

        CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
        populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("schema/schema.sql")));
        populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("schema/data.sql")));
        initializer.setDatabasePopulator(populator);

        return initializer;
    }

    @Bean
    public R2dbcMappingContext r2dbcMappingContext(Optional<NamingStrategy> namingStrategy,
                                                   R2dbcCustomConversions r2dbcCustomConversions) {

        Assert.notNull(namingStrategy, "NamingStrategy must not be null!");

        R2dbcMappingContext context = new R2dbcMappingContext(namingStrategy
                .orElse(DefaultNamingStrategy.INSTANCE));
        context.setSimpleTypeHolder(r2dbcCustomConversions.getSimpleTypeHolder());
        context.setForceQuote(true);

        return context;
    }
}
