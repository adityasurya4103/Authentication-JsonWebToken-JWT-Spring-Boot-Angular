package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.SignupRequest;
import com.example.demo.entities.Admin;
import com.example.demo.services.AuthService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/signup")
public class SignupController {

	private final AuthService authService;

	@Autowired
	public SignupController(AuthService authService) {
		this.authService = authService;
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping
	public ResponseEntity<?> signupAdmin(@RequestBody SignupRequest signupRequest) {
		Admin createdAdmin = authService.createAdmin(signupRequest);
		if (createdAdmin != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(createdAdmin);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create Admin");
		}
	}

}