package com.reactbooks.spring_boot_library.requestmodels;


import lombok.Data;

// Object used to handle requests related to administrative actions on messages or questions
@Data
public class AdminQuestionRequest {
    private Long id;
    private String response;
}
