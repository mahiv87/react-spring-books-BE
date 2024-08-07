package com.reactbooks.spring_boot_library.controller;

import com.reactbooks.spring_boot_library.requestmodels.ReviewRequest;
import com.reactbooks.spring_boot_library.service.ReviewService;
import com.reactbooks.spring_boot_library.utils.ExtractJWT;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("https://reactspringlibrary.netlify.app")
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private ReviewService reviewService;


    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

//    Checks if a user has reviewed a specific book
    @GetMapping("/secure/user/book")
    public Boolean reviewBookByUser (@RequestHeader(value = "Authorization") String token, @RequestParam Long bookId) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");

        if (userEmail == null) {
            throw new Exception("User email is missing");
        }

        return reviewService.userReviewListed(userEmail, bookId);
    }

//    Create a new review
    @PostMapping("/secure")
    public void postReview(@RequestHeader(value = "Authorization") String token, @RequestBody ReviewRequest reviewRequest) throws Exception {

        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");

        if (userEmail == null) {
            throw new Exception("User email is missing");
        }
        reviewService.postReview(userEmail, reviewRequest);
    }

}
