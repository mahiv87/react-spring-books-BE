package com.reactbooks.spring_boot_library.controller;

import com.reactbooks.spring_boot_library.model.Message;
import com.reactbooks.spring_boot_library.service.MessagesService;
import com.reactbooks.spring_boot_library.utils.ExtractJWT;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/api/messages")
public class MessagesController {

    private MessagesService messagesService;

    public MessagesController(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    public void postMessage(@RequestHeader(value = "Authorization") String token, @RequestBody Message messageRequest) {
        String userEmail = extractUserEmail(token);

        messagesService.postMessage(messageRequest, userEmail);

    }


    private String extractUserEmail(String token) {
        return ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
    }

}
