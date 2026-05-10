package com.mh.backend.service;

import com.mh.backend.entity.Admin;

public interface AdminService {

    public boolean authenticate(String email, String password);

    public Admin register(Admin admin);
}
