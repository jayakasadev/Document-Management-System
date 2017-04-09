package kasa.dev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Application Class starts up the REST Service
 *
 * Using @SpringBootApplication to enable auto configuration
 */
@SpringBootApplication
@EnableJpaRepositories
public class Application {
    public static void main(String[] args){

        // this line will startup the application
        SpringApplication.run(Application.class, args);
    }
}
