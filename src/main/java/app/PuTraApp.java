package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

// Could not get it to work :(
@SpringBootApplication
@EnableScheduling
public class PuTraApp {
    public static void main(String[] args) {
        SpringApplication.run(PuTraApp.class, args);
    }
}
