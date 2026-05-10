package com.mh.backend.service.impl;

import com.mh.backend.entity.Admin;
import com.mh.backend.repository.AdminRepository;
import com.mh.backend.service.AdminService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public boolean authenticate(String email, String password) {
        Admin admin = adminRepository.findByEmail(email);
        if (admin == null) {
            return false;
        }
        return passwordEncoder.matches(password, admin.getPassword());
    }

    @Override
    public Admin register(Admin admin) {
        // enforce unique email
        Admin existing = adminRepository.findByEmail(admin.getEmail());
        if (existing != null) {
            throw new RuntimeException("Admin with this email already exists");
        }
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminRepository.save(admin);
    }
}

