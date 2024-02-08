package com.example.demo.services.jwt;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Admin;
import com.example.demo.repository.AdminRepository;

@Service
public class AdminServiceImpl implements UserDetailsService {

	private final AdminRepository adminRepository;

	@Autowired
	public AdminServiceImpl(AdminRepository adminRepository) {
		this.adminRepository = adminRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// Write logic to fetch admin from DB
		Admin admin = adminRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Admin not found with email: " + email));

		return new User(admin.getEmail(), admin.getPassword(), Collections.emptyList());
	}

	// Collections.emptyList() is used to represent an empty collection of
	// authorities for the user being loaded
}