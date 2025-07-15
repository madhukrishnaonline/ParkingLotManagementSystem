package com.parkinglot.security;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.parkinglot.SpringApplicationContext;
import com.parkinglot.entity.ParkingManagerEntity;
import com.parkinglot.repository.ParkingManagerRepository;
import com.parkinglot.shared.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthorizationManagerFilter extends BasicAuthenticationFilter {

	public AuthorizationManagerFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String authToken = request.getHeader(SecurityConstants.AUTH_HEADER);
		String username = null;
		String token = null;
		JwtUtils jwtUtils = SpringApplicationContext.fetchBean("jwtUtils", JwtUtils.class);
		if (authToken != null && authToken.startsWith(SecurityConstants.TOKEN_TYPE)) {
			token = authToken.substring(7).trim();
			username = jwtUtils.extractUsername(token);
		}
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			ParkingManagerEntity managerEntity = SpringApplicationContext
					.fetchBean("parkingManagerRepository", ParkingManagerRepository.class).findByUsername(username).orElseThrow();
			UserPrincipal principal = new UserPrincipal(managerEntity);
			if (jwtUtils.validateToken(token, principal)) {
				SecurityContextHolder.getContext().setAuthentication(
						new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities()));
			}
		}
		chain.doFilter(request, response);
	}
}