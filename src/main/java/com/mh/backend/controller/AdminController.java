package com.mh.backend.controller;


import com.mh.backend.entity.Admin;
import com.mh.backend.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
//@CrossOrigin(origins = "http://localhost:5173") // React dev server
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Admin admin) {
        boolean authenticated = adminService.authenticate(admin.getEmail(), admin.getPassword());
        System.out.println("Im in Admin Controller");
        if (authenticated) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
