package com.example.volunteerhub.Controller;

import com.example.volunteerhub.API.ApiResponse;
import com.example.volunteerhub.Model.Review;
import com.example.volunteerhub.Service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService service;

    @PostMapping("/opportunities/{opportunityId}/reviews")
    public ResponseEntity<Object> add(@PathVariable Integer opportunityId,
                                      @Valid @RequestBody Review body) {
        service.addReview(opportunityId, body);
        return ResponseEntity.status(200).body(new ApiResponse("Review submitted"));
    }

    @GetMapping("/users/{userId}/reviews/received")
    public ResponseEntity<Object> received(@PathVariable Integer userId) {
        return ResponseEntity.status(200).body(service.listReceived(userId));
    }

    @GetMapping("/users/{userId}/reviews/given")
    public ResponseEntity<Object> given(@PathVariable Integer userId) {
        return ResponseEntity.status(200).body(service.listGiven(userId));
    }

    @GetMapping("/users/{userId}/reviews/average-rating")
    public ResponseEntity<Object> avg(@PathVariable Integer userId) {
        double avg = service.averageRating(userId);
        return ResponseEntity.status(200).body(avg);
    }
}
