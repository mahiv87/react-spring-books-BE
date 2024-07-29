package com.reactbooks.spring_boot_library.repository;

import com.reactbooks.spring_boot_library.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

public interface MessageRepository extends JpaRepository<Message, Long> {

//    Find messages associated with the user's email
    Page<Message> findByUserEmail(@RequestParam("user_email") String userEmail, Pageable pageable);

//    Find messages that are marked "closed"
    Page<Message> findByClosed(@RequestParam("closed") boolean closed, Pageable pageable);
}
