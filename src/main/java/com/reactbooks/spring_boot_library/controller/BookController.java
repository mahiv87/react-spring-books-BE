package com.reactbooks.spring_boot_library.controller;

import com.reactbooks.spring_boot_library.model.Book;
import com.reactbooks.spring_boot_library.responsemodels.ShelfCurrentLoansResponse;
import com.reactbooks.spring_boot_library.service.BookService;
import com.reactbooks.spring_boot_library.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("https://react-spring-library-87d43a3bbf09.herokuapp.com")
@RestController
@RequestMapping("/api/books")
public class BookController {
    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/secure/currentloans")
    public List<ShelfCurrentLoansResponse> currentLoans(@RequestHeader(value = "Authorization") String token) throws Exception {
        String userEmail = extractUserEmail(token);
        return bookService.currentLoans(userEmail);
    }

    @GetMapping("/secure/currentloans/count")
    public int currentLoansCount(@RequestHeader(value = "Authorization") String token) {
        String userEmail = extractUserEmail(token);
        return bookService.currentLoansCount(userEmail);
    }

    @GetMapping("/secure/ischeckedout/byuser")
    public Boolean isBookCheckedOutByUser(@RequestHeader(value = "Authorization") String token, @RequestParam Long bookId) {
        String userEmail = extractUserEmail(token);
        return bookService.isBookCheckedOutByUser(userEmail, bookId);
    }

    @PutMapping("/secure/checkout")
    public Book checkoutBook (@RequestHeader(value = "Authorization") String token, @RequestParam Long bookId) throws Exception {
        String userEmail = extractUserEmail(token);
        return bookService.checkoutBook(userEmail, bookId);
    }

    @PutMapping("/secure/return")
    public void returnBook(@RequestHeader(value = "Authorization") String token, @RequestParam Long bookId) throws Exception {
        String userEmail = extractUserEmail(token);
        bookService.returnBook(userEmail, bookId);
    }

    @PutMapping("/secure/renew/loan")
    public void renewLoan(@RequestHeader(value = "Authorization") String token, @RequestParam Long bookId) throws Exception {
        String userEmail = extractUserEmail(token);
        bookService.renewLoan(userEmail, bookId);
    }

    private String extractUserEmail(String token) {
        return ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
    }


}
