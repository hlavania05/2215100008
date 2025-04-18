package com.example.socialmediaanalytics.controller;

import com.example.socialmediaanalytics.service.SocialMediaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class SocialMediaController {

    private final SocialMediaService socialMediaService;

    public SocialMediaController(SocialMediaService socialMediaService) {
        this.socialMediaService = socialMediaService;
    }

    // Endpoint to get users
    @GetMapping("/users")
    public Mono<List<Object>> getUsers() {
        return socialMediaService.getUsers(); // Call service to get users
    }

    // Endpoint to get posts by userId
    @GetMapping("/users/{userId}/posts")
    public Mono<List<Object>> getPostsByUserId(@PathVariable Integer userId) {
        return socialMediaService.getPostsByUserId(userId); // Call service to get posts
    }

    // Endpoint to get comments for a postId
    @GetMapping("/posts/{postId}/comments")
    public Mono<List<Object>> getCommentsByPostId(@PathVariable Integer postId) {
        return socialMediaService.getCommentsByPostId(postId); // Call service to get comments
    }
}
