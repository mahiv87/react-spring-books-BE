package com.reactbooks.spring_boot_library.repository;

import com.reactbooks.spring_boot_library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
