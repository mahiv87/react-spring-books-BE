package com.reactbooks.spring_boot_library.controller;

import com.reactbooks.spring_boot_library.requestmodels.ReviewRequest;
import com.reactbooks.spring_boot_library.service.ReviewService;
import com.reactbooks.spring_boot_library.utils.ExtractJWT;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private ReviewService reviewService;


    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/secure")
    public void postReview(@RequestHeader(value = "Authorization") String token, @RequestBody ReviewRequest reviewRequest) throws Exception {

        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");

        if (userEmail == null) {
            throw new Exception("User email is missing");
        }
        reviewService.postReview(userEmail, reviewRequest);
    }

}
