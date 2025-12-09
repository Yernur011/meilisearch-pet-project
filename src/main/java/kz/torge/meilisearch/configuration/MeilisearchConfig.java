package kz.torge.meilisearch.configuration;

import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MeilisearchConfig {
    @Bean
    public Client meilisearchClient( MeilisearchProperties properties) {
        Config config = new Config(properties.getHost(), properties.getMasterKey());
        Client client = new Client(config);
        return client;
    }
}
