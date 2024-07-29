package com.reactbooks.spring_boot_library.repository;

import com.reactbooks.spring_boot_library.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
//    Finds books where the title contains the specified string
    Page<Book> findByTitleContaining(@RequestParam("title") String title, Pageable pageable);

//    Finds books by their category
    Page<Book> findByCategory(@RequestParam("category") String category, Pageable pageable);

//    Finds books with IDs specified in the list
    @Query("select o from Book o where id in :book_ids")
    List<Book> findBookById (@Param("book_ids") List<Long> bookId);
}
