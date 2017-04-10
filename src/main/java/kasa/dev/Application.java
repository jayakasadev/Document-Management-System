package kasa.dev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Application Class starts up the REST Service
 *
 * Using @SpringBootApplication to enable auto configuration
 * Using @EnableScheduling to enable scheduling of tasks (i.e email)
 */
@SpringBootApplication
@EnableScheduling
public class Application {
    public static void main(String[] args){

        // this line will startup the application
        SpringApplication.run(Application.class, args);
    }
}
