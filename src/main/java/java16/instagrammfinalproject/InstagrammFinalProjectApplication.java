package java16.instagrammfinalproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "java16.instagrammfinalproject.repo")
@EntityScan(basePackages = "java16.instagrammfinalproject.models")
public class InstagrammFinalProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(InstagrammFinalProjectApplication.class, args);
    }
}

