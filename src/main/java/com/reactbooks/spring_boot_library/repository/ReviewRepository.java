package com.reactbooks.spring_boot_library.repository;

import com.reactbooks.spring_boot_library.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
