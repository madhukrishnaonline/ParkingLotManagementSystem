package com.parkinglot.security;

import java.io.IOException;
import java.util.StringTokenizer;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkinglot.SpringApplicationContext;
import com.parkinglot.dto.ManagerRequest;
import com.parkinglot.dto.ManagerResponse;
import com.parkinglot.exception.ManagerNotFoundException;
import com.parkinglot.service.ManagerService;
import com.parkinglot.shared.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationManagerFilter extends UsernamePasswordAuthenticationFilter {

	public AuthenticationManagerFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			ManagerRequest managerRequest = new ObjectMapper().readValue(request.getInputStream(),
					ManagerRequest.class);
			return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
					managerRequest.getUsername(), managerRequest.getPassword()));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String username = ((UserPrincipal) authResult.getPrincipal()).getUsername();
		ManagerService managerService = SpringApplicationContext.fetchBean("managerServiceImpl", ManagerService.class);
		ManagerResponse managerResponse = null;
		try {
			managerResponse = managerService.findByUsername(username);
		} catch (ManagerNotFoundException e) {
			e.printStackTrace();
		}
		String token = SpringApplicationContext.fetchBean("jwtUtils", JwtUtils.class)
				.generateToken(managerResponse.getUsername());

		StringTokenizer tokenizer = new StringTokenizer(managerResponse.getFullName(), " ");
		String firstName = tokenizer.nextToken();

		response.setHeader(SecurityConstants.AUTH_HEADER, SecurityConstants.TOKEN_TYPE + token);
		response.setHeader(SecurityConstants.MANAGER_ID, String.valueOf(managerResponse.getId()));
		response.setHeader(SecurityConstants.MANAGER_FIRST_NAME, firstName);
	}
}