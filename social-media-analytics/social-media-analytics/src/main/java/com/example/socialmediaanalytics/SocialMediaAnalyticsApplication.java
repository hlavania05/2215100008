package com.example.socialmediaanalytics;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class SocialMediaAnalyticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialMediaAnalyticsApplication.class, args);
    }

    // WebClient Bean configuration
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
