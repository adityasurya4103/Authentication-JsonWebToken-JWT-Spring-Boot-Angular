package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.services.jwt.AdminServiceImpl;
import com.example.demo.utils.JwtUtil;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/login")
public class LoginController {
	private final AuthenticationManager authenticationManager;

	private final AdminServiceImpl adminService;

	private final JwtUtil jwtUtil;

	@Autowired
	public LoginController(AuthenticationManager authenticationManager, AdminServiceImpl customerService,
			JwtUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.adminService = customerService;
		this.jwtUtil = jwtUtil;
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response)
			throws IOException, Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Incorrect email or password.");
		} catch (DisabledException disabledException) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Admin is not activated");
			return null;
		}
		final UserDetails userDetails = adminService.loadUserByUsername(loginRequest.getEmail());
		final String jwt = jwtUtil.generateToken(userDetails.getUsername());
		LoginResponse jwtResponse = new LoginResponse(jwt);
		return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
	}

}