package com.reactbooks.spring_boot_library.repository;

import com.reactbooks.spring_boot_library.model.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

public interface HistoryRepository extends JpaRepository<History, Long> {

    Page<History> findBooksByUserEmail(@RequestParam("email") String userEmail, Pageable pageable);
}
