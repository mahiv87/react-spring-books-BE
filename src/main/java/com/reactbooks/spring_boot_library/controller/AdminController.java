package com.reactbooks.spring_boot_library.controller;

import com.reactbooks.spring_boot_library.requestmodels.AddBookRequest;
import com.reactbooks.spring_boot_library.service.AdminService;
import com.reactbooks.spring_boot_library.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/secure/add/book")
    public void postBook(@RequestHeader(value = "Authorization") String token,
                         @RequestBody AddBookRequest addBookRequest) throws Exception {
        String admin = extractAdmin(token);

        if (admin == null || !admin.equals("admin")) {
            throw new Exception("Administration access only");
        }

        adminService.postBook(addBookRequest);
    }

    @PutMapping("/secure/increase/book/quantity")
    public void increaseBookQty(@RequestHeader(value = "Authorization") String token,
                                @RequestParam Long bookId) throws Exception {
        String admin = extractAdmin(token);

        if (admin == null || !admin.equals("admin")) {
            throw new Exception("Administration access only");
        }

        adminService.increaseBookQty(bookId);
    }

    private String extractAdmin(String token) {
        return ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
    }


}
