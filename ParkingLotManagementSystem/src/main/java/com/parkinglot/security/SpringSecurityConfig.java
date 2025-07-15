package com.parkinglot.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.parkinglot.service.ManagerService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SpringSecurityConfig {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity security, ManagerService service,
			BCryptPasswordEncoder passwordEncoder) throws Exception {
		AuthenticationManagerBuilder managerBuilder = security.getSharedObject(AuthenticationManagerBuilder.class);
		managerBuilder.userDetailsService(service).passwordEncoder(passwordEncoder);
		AuthenticationManager authenticationManager = managerBuilder.build();

		return security
				.csrf(csrf -> csrf
						.disable())
				.cors(cors -> cors.configurationSource(registerCors()))
				.authorizeHttpRequests(
						request -> request.requestMatchers("/spots/**", "/vehicle/**", "/parkingManager/register", "/",
								"/h2-console/**", "/login/**").permitAll().anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilter(new AuthenticationManagerFilter(authenticationManager))
				.addFilter(new AuthorizationManagerFilter(authenticationManager))
				.authenticationManager(authenticationManager)
				.headers(headers -> headers.frameOptions(frames -> frames.sameOrigin())).build();
	}

	@Bean
	CorsConfigurationSource registerCors() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of("http://localhost:4200"));
		configuration.setAllowedMethods(List.of("*"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(List.of("*"));

		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", configuration);
		return urlBasedCorsConfigurationSource;
	}
}