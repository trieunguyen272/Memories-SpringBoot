package com.project.memories.user.service;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.memories.user.config.JwtService;
import com.project.memories.user.controller.AuthenticationRequest;
import com.project.memories.user.controller.AuthenticationResponse;
import com.project.memories.user.controller.RegisterRequest;
import com.project.memories.user.model.User;
import com.project.memories.user.repository.UserRepository;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public AuthenticationResponse register(RegisterRequest request) {
		
		Optional<User> userOptional = userRepository.findUserByEmail(request.getEmail());
		
		if(userOptional.isPresent()) {
			throw new IllegalStateException("User already exist.");
		}
		
		var user = User.builder()
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.build();
		var savedUser = userRepository.save(user);
		var jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder()
				.result(savedUser)
				.token(jwtToken)
				.build();

	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		Optional<User> userOptional = userRepository.findUserByEmail(request.getEmail());
		
		if(!userOptional.isPresent()) {
			throw new IllegalStateException("User doesn't exist.");
		}
		
		try {
			authenticationManager
			.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		}	
		catch(Exception e) {
			throw new IllegalStateException("Password wrong.");
		}	
		var user = userRepository.findUserByEmail(request.getEmail())
				.orElseThrow();
		var jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder()
				.result(user)
				.token(jwtToken)
				.build();
	}

}
