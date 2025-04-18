package com.example.socialmediaanalytics.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class SocialMediaService {

    private final WebClient webClient;

    // Constructor injecting WebClient
    public SocialMediaService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://20.244.56.144/evaluation-service").build();
    }

    // Get Users (Top Users)
    public Mono<List<Object>> getUsers() {
        return webClient.get()
                .uri("/users") // Third-Party API endpoint for users
                .retrieve() // Execute the request
                .bodyToMono(List.class); // Deserialize response to List of objects
    }

    // Get Posts of a User
    public Mono<List<Object>> getPostsByUserId(Integer userId) {
        return webClient.get()
                .uri("/users/{userId}/posts", userId) // Dynamic URI with userId
                .retrieve()
                .bodyToMono(List.class);
    }

    // Get Comments for a Post
    public Mono<List<Object>> getCommentsByPostId(Integer postId) {
        return webClient.get()
                .uri("/posts/{postId}/comments", postId) // Dynamic URI with postId
                .retrieve()
                .bodyToMono(List.class);
    }
}
