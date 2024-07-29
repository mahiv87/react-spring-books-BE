package com.reactbooks.spring_boot_library.requestmodels;

import lombok.Data;

import java.util.Optional;

// Object used for submitting book reviews
@Data
public class ReviewRequest {
    private double rating;

    private Long bookId;

    private Optional<String> reviewDescription;
}
