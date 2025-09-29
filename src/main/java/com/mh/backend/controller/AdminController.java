package com.mh.backend.controller;

import com.mh.backend.entity.Admin;
import com.mh.backend.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Admin admin) {
        boolean authenticated = adminService.authenticate(admin.getEmail(), admin.getPassword());
        if (authenticated) {
            return ResponseEntity.ok().body(java.util.Map.of(
                    "status", "success",
                    "message", "Login successful"
            ));
        }
        return ResponseEntity.status(401).body(java.util.Map.of(
                "status", "error",
                "message", "Invalid email or password"
        ));
    }

    // Register endpoint
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Admin admin) {
        try {
            Admin created = adminService.register(admin);
            // hide password in response
            created.setPassword(null);
            return ResponseEntity.ok(java.util.Map.of(
                    "status", "success",
                    "message", "Admin registered successfully",
                    "admin", created
            ));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(java.util.Map.of(
                    "status", "error",
                    "message", ex.getMessage()
            ));
        }
    }
}
