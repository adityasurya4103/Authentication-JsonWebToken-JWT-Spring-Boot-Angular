package com.example.demo.services;

import com.example.demo.dto.SignupRequest;
import com.example.demo.entities.Admin;

public interface AuthService {
	Admin createAdmin(SignupRequest signupRequest);
}