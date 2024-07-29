package com.reactbooks.spring_boot_library.service;

import com.reactbooks.spring_boot_library.model.Message;
import com.reactbooks.spring_boot_library.repository.MessageRepository;
import com.reactbooks.spring_boot_library.requestmodels.AdminQuestionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class MessagesService {

    private MessageRepository messageRepository;

    @Autowired
    public MessagesService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

//    Posts a new message to the repository. Associates the message with the user who posted it
    public void postMessage(Message messageRequest, String userEmail) {
        Message message = new Message(messageRequest.getTitle(), messageRequest.getQuestion());

        message.setUserEmail(userEmail);
        messageRepository.save(message);
    }

//    Updates an existing message with a response from an administrator and marks it as closed
    public void putMessage(AdminQuestionRequest adminQuestionRequest, String userEmail) throws Exception {
        Optional<Message> message = messageRepository.findById(adminQuestionRequest.getId());

        if (message.isEmpty()) {
            throw new Exception("Message was not found");
        }

        message.get().setAdminEmail(userEmail);
        message.get().setResponse(adminQuestionRequest.getResponse());
        message.get().setClosed(true);

        messageRepository.save(message.get());

    }


}
