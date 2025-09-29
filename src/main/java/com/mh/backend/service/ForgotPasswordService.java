package com.mh.backend.service;

public interface ForgotPasswordService {
    boolean sendPasswordResetEmail(String email);
//    boolean resetPassword(String token, String newPassword);
//    boolean validateToken(String token);
}
