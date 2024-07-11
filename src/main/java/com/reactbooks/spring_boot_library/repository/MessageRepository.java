package com.reactbooks.spring_boot_library.repository;

import com.reactbooks.spring_boot_library.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
