package com.IcecreamApp.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.IcecreamApp.entity.ERole;
import com.IcecreamApp.entity.Role;
import com.IcecreamApp.entity.User;
import com.IcecreamApp.payload.request.LoginRequest;
import com.IcecreamApp.payload.request.SignupRequest;
import com.IcecreamApp.payload.response.JwtResponse;
import com.IcecreamApp.payload.response.MessageResponse;
import com.IcecreamApp.repository.RoleRepository;
import com.IcecreamApp.repository.UserRepository;
import com.IcecreamApp.security.ApplicationUser;
import com.IcecreamApp.security.filter.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("auth")
public class AuthenticationController {
	
	private final AuthenticationManager authenticationManager;

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	private final PasswordEncoder encoder;
	
	private final JwtUtils jwtUtils;

	
	public AuthenticationController(AuthenticationManager authenticationManager, UserRepository userRepository,
			RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.encoder = encoder;
		this.jwtUtils = jwtUtils;
	}


	@PostMapping("/login")
	public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
		

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        		loginRequest.getUsername(),
        		loginRequest.getPassword(),
                new HashSet<>());
		Authentication authentication = authenticationManager.authenticate(authenticationToken);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwtToken = jwtUtils.generateJwtToken(authentication);
		
		ApplicationUser appUser = (ApplicationUser) authentication.getPrincipal();		
		List<String> roles = appUser.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return new ResponseEntity<JwtResponse>(new JwtResponse(jwtToken, appUser.getUsername(), roles), HttpStatus.OK);
	}

	@PostMapping("/signup")
	public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}


		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} 
		else {
			strRoles.forEach(role -> {
				switch (role) {
				case "ADMIN":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "STAFF":
					Role modRole = roleRepository.findByName(ERole.ROLE_STAFF)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
					break;
				}
			});
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()),
				1,new HashSet<Role>());
		
		user.setRoles(roles);
		userRepository.save(user);

		return new ResponseEntity<MessageResponse>(new MessageResponse("User registered successfully!"), HttpStatus.OK);
	}

}