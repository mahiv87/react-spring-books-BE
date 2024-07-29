package com.reactbooks.spring_boot_library.repository;

import com.reactbooks.spring_boot_library.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

public interface ReviewRepository extends JpaRepository<Review, Long> {

//    Retrieves a paginated list of Review entities associated with a specific book ID
    Page<Review> findByBookId(@RequestParam("book_id") Long bookId, Pageable pageable);

//    Finds a specific Review entity based on the user's email and book ID
    Review findByUserEmailAndBookId(String userEmail, Long bookId);

//    Deletes all Review entities associated with a specific book ID
    @Transactional
    @Modifying
    @Query("delete from Review r where r.bookId = :bookId")
    void deleteAllByBookId(@Param("bookId") Long bookId);

}
