package com.reactbooks.spring_boot_library.requestmodels;

import lombok.Data;

// Object used to encapsulate the information required to add a new book to the system
@Data
public class AddBookRequest {

    private String title;

    private String author;

    private String description;

    private int copies;

    private String category;

    private String img;


}
