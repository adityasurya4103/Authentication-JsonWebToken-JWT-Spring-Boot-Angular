package com.example.demo.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.SignupRequest;
import com.example.demo.entities.Admin;
import com.example.demo.repository.AdminRepository;

@Service
public class AuthServiceImpl implements AuthService {

	private final AdminRepository adminRepository;

	private final PasswordEncoder passwordEncoder;

	@Autowired
	public AuthServiceImpl(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
		this.adminRepository = adminRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Admin createAdmin(SignupRequest signupRequest) {
		// Check if Admin already exist
		if (adminRepository.existsByEmail(signupRequest.getEmail())) {
			return null;
		}

		Admin admin = new Admin();
		BeanUtils.copyProperties(signupRequest, admin);
		// above statement does the task of admin.setEmail(signupRequest.getEmail()) for
		// all the properties

		// Hash the password before saving
		String hashPassword = passwordEncoder.encode(signupRequest.getPassword());
		admin.setPassword(hashPassword);
		Admin createdAdmin = adminRepository.save(admin);
		admin.setId(createdAdmin.getId());
		return admin;
	}
}