package com.mh.backend.service.impl;

import com.mh.backend.entity.PasswordResetToken;
import com.mh.backend.entity.User;

import com.mh.backend.repository.PasswordResetTokenRepository;
import com.mh.backend.repository.UserRepository;
import com.mh.backend.service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.frontend.base-url:http://localhost:5173}")
    private String frontendBaseUrl;

    public boolean sendPasswordResetEmail(String email) {
        try {
            // Check if user exists
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (!userOptional.isPresent()) {
                return false; // Don't reveal if user exists or not
            }

            // Delete any existing tokens for this email
            //userRepository.deleteByEmail(email);

            // Generate new token
            String token = UUID.randomUUID().toString();
            LocalDateTime expiryDate = LocalDateTime.now().plusHours(1); // Token expires in 1 hour

            // Save token
            PasswordResetToken resetToken = new PasswordResetToken(token, email, expiryDate);
            tokenRepository.save(resetToken);

            // Send email
            sendResetEmail(email, token);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void sendResetEmail(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset Request - Tour & Travels");

        String resetUrl = frontendBaseUrl + "/reset-password?token=" + token;

        String emailBody = String.format("""
            Dear User,

            You have requested to reset your password for your Tour & Travels account.

            Please click the link below to reset your password:
            %s

            This link will expire in 1 hour.

            If you did not request this password reset, please ignore this email.

            Best regards,
            Tour & Travels Team
            """, resetUrl);

        message.setText(emailBody);
        message.setFrom("noreply@tourtravels.com");

        mailSender.send(message);
    }

//    public boolean resetPassword(String token, String newPassword) {
//        try {
//            Optional<PasswordResetToken> tokenOptional = tokenRepository.findByToken(token);
//
//            if (!tokenOptional.isPresent()) {
//                return false;
//            }
//
//            PasswordResetToken resetToken = tokenOptional.get();
//
//            // Check if token is expired or already used
//            if (resetToken.isExpired() || resetToken.isUsed()) {
//                return false;
//            }
//
//            // Find user by email
//            Optional<User> userOptional = userRepository.findByEmail(resetToken.getEmail());
//            if (!userOptional.isPresent()) {
//                return false;
//            }
//
//            // Update password
//            User user = userOptional.get();
//            user.setPassword(passwordEncoder.encode(newPassword));
//            userRepository.save(user);
//
//            // Mark token as used
//            resetToken.setUsed(true);
//            tokenRepository.save(resetToken);
//
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

//    public boolean validateToken(String token) {
//        Optional<PasswordResetToken> tokenOptional = tokenRepository.findByToken(token);
//
//        if (!tokenOptional.isPresent()) {
//            return false;
//        }
//
//        PasswordResetToken resetToken = tokenOptional.get();
//        return !resetToken.isExpired() && !resetToken.isUsed();
//    }
}