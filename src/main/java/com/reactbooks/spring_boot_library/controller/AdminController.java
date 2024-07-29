package com.reactbooks.spring_boot_library.controller;

import com.reactbooks.spring_boot_library.requestmodels.AddBookRequest;
import com.reactbooks.spring_boot_library.service.AdminService;
import com.reactbooks.spring_boot_library.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("https://reactspringlibrary.netlify.app")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

//    Adds a new book to the library. Requires an authorization token with administrative privileges
    @PostMapping("/secure/add/book")
    public void postBook(@RequestHeader(value = "Authorization") String token,
                         @RequestBody AddBookRequest addBookRequest) throws Exception {

        String admin = extractAdmin(token);
        checkAdmin(admin);
        adminService.postBook(addBookRequest);
    }

//    Increases the quantity of a book in the library.
//    Requires an authorization token with administrative privileges
    @PutMapping("/secure/increase/book/quantity")
    public void increaseBookQty(@RequestHeader(value = "Authorization") String token,
                                @RequestParam Long bookId) throws Exception {

        String admin = extractAdmin(token);
        checkAdmin(admin);
        adminService.increaseBookQty(bookId);
    }

//    Decreases the quantity of a book in the library.
//    Requires an authorization token with administrative privileges
    @PutMapping("/secure/decrease/book/quantity")
    public void decreaseBookQty(@RequestHeader(value = "Authorization") String token,
                                @RequestParam Long bookId) throws Exception {

        String admin = extractAdmin(token);
        checkAdmin(admin);
        adminService.decreaseBookQty(bookId);
    }

//    Deletes a book from the library.
//    Requires an authorization token with administrative privileges
    @DeleteMapping("/secure/delete/book")
    public void deleteBook(@RequestHeader(value = "Authorization") String token,
                           @RequestParam Long bookId) throws Exception {

        String admin = extractAdmin(token);
        checkAdmin(admin);
        adminService.deleteBook(bookId);
    }


//    Extracts the user type from the JWT token
    private String extractAdmin(String token) {
        return ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
    }

//    Checks if the user type is admin
    private void checkAdmin(String admin) throws Exception {
        if (admin == null || !admin.equals("admin")) {
            throw new Exception("Administration access only");
        }
    }

}
