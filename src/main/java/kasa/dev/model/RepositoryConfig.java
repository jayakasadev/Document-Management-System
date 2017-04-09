package kasa.dev.model;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

/**
 * Using this class to expose fileIDs
 */
@Configuration
public class RepositoryConfig extends RepositoryRestConfigurerAdapter {
    // @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Upload.class);
    }
}
