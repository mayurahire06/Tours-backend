package com.mh.backend.service.impl;

import com.mh.backend.entity.Admin;
import com.mh.backend.repository.AdminRepository;
import com.mh.backend.service.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public boolean authenticate(String email, String password) {
        Admin admin = adminRepository.findByEmail(email);
        if(admin.getPassword().equals(password) && admin.getEmail().equals(email)) {
            // In real apps, use BCrypt for password hashing
            return true;
        }
        return false;
    }
}

