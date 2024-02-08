package com.example.demo.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.services.jwt.AdminServiceImpl;
import com.example.demo.utils.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	private final AdminServiceImpl adminService;
	private final JwtUtil jwtUtil;

	@Autowired
	public JwtRequestFilter(AdminServiceImpl adminService, JwtUtil jwtUtil) {
		this.adminService = adminService;
		this.jwtUtil = jwtUtil;
	}

	// doFilterInternal It is the main method where the actual filtering logic is
	// implemented. When a
	// request comes into the server, the doFilterInternal method is invoked to
	// process that request.

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
			username = jwtUtil.extractUsername(token);
		}

		// SecurityContextHolder.getContext().getAuthentication() == null is used to
		// check whether there is already an authentication object associated with the
		// current security context

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = adminService.loadUserByUsername(username);

			if (jwtUtil.validateToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}

		}

		filterChain.doFilter(request, response);

	}
}