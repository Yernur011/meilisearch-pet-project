package kz.torge.meilisearch.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "meilisearch")
public class MeilisearchProperties {
    private String host;
    private String masterKey;
}


