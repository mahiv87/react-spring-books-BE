package com.reactbooks.spring_boot_library.controller;

import com.reactbooks.spring_boot_library.model.Message;
import com.reactbooks.spring_boot_library.requestmodels.AdminQuestionRequest;
import com.reactbooks.spring_boot_library.service.MessagesService;
import com.reactbooks.spring_boot_library.utils.ExtractJWT;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("https://reactspringlibrary.netlify.app")
@RestController
@RequestMapping("/api/messages")
public class MessagesController {

    private MessagesService messagesService;

    public MessagesController(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

//    Posts a new message
    @PostMapping("/secure/add/message")
    public void postMessage(@RequestHeader(value = "Authorization") String token,
                            @RequestBody Message messageRequest) {
        String userEmail = extractUserEmail(token);

        messagesService.postMessage(messageRequest, userEmail);

    }

//    Updates a message. Requires an authorization token with administrative privileges
    @PutMapping("/secure/admin/message")
    public void putMessage(@RequestHeader(value = "Authorization") String token,
                           @RequestBody AdminQuestionRequest adminQuestionRequest) throws Exception {
        String userEmail = extractUserEmail(token);

        String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");

        if (admin == null || !admin.equals("admin")) {
            throw new Exception("Administration access only");
        }

        messagesService.putMessage(adminQuestionRequest, userEmail);
    }


//    Extracts the user's email from the JWT token
    private String extractUserEmail(String token) {
        return ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
    }

}
