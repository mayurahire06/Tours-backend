package com.mh.backend.controller;

import com.mh.backend.service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class ForgotPasswordController {

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        System.out.println("request from "+ request.get("email"));

        if (email == null || email.trim().isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Email is required");
            return ResponseEntity.badRequest().body(response);
        }

        boolean success = forgotPasswordService.sendPasswordResetEmail(email);

        Map<String, String> response = new HashMap<>();
        if (success) {
            response.put("message", "Password reset link has been sent to your email address");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Failed to send reset link. Please try again.");
            return ResponseEntity.badRequest().body(response);
        }
    }

//    @PostMapping("/reset-password")
//    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody Map<String, String> request) {
//        String token = request.get("token");
//        String newPassword = request.get("newPassword");
//
//        if (token == null || newPassword == null || token.trim().isEmpty() || newPassword.trim().isEmpty()) {
//            Map<String, String> response = new HashMap<>();
//            response.put("message", "Token and new password are required");
//            return ResponseEntity.badRequest().body(response);
//        }
//
//        if (newPassword.length() < 6) {
//            Map<String, String> response = new HashMap<>();
//            response.put("message", "Password must be at least 6 characters long");
//            return ResponseEntity.badRequest().body(response);
//        }
//
//        boolean success = forgotPasswordService.resetPassword(token, newPassword);
//
//        Map<String, String> response = new HashMap<>();
//        if (success) {
//            response.put("message", "Password has been reset successfully");
//            return ResponseEntity.ok(response);
//        } else {
//            response.put("message", "Invalid or expired token");
//            return ResponseEntity.badRequest().body(response);
//        }
//    }

//    @GetMapping("/validate-reset-token")
//    public ResponseEntity<Map<String, Object>> validateToken(@RequestParam String token) {
//        boolean isValid = forgotPasswordService.validateToken(token);
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("valid", isValid);
//
//        if (isValid) {
//            response.put("message", "Token is valid");
//            return ResponseEntity.ok(response);
//        } else {
//            response.put("message", "Invalid or expired token");
//            return ResponseEntity.badRequest().body(response);
//        }
//    }
}