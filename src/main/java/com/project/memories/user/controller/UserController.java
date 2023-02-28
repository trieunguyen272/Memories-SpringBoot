package com.project.memories.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.memories.user.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

	@Autowired
	private AuthenticationService service;

	/*
	 * @PostMapping("/signup") 
	 * public User createUser(@RequestBody User user) { User
	 * existUser = userRepository.findByEmail(user.getEmail()); if (existUser !=
	 * null) { throw new UsernameNotFoundException("email taken"); } return
	 * userRepository.save(user); }
	 * 
	 * @GetMapping("/signin") public String signIn() { return "success"; }
	 */

	@GetMapping("/demo")
	public ResponseEntity<String> sayHello() {
		return ResponseEntity.ok("Hello demo");
	}

	@PostMapping("/signup")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
		return ResponseEntity.ok(service.register(request));
	}

	@PostMapping("/signin")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
		return ResponseEntity.ok(service.authenticate(request));
	}

}
