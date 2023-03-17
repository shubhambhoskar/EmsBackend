package com.employee.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import com.employee.Security.jwtAuthRequest;
import com.employee.Security.jwtAuthResponse;
import com.employee.Security.jwtTokenHelper;
import com.employee.model.Employee;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

	@Autowired
	private jwtTokenHelper jhelper;
	
	@Autowired
	private UserDetailsService uService;
	
	@Autowired
	private AuthenticationManager aManager;
	
 
	@PostMapping("/jwtlogin")
	public ResponseEntity<jwtAuthResponse>createToken(@RequestBody jwtAuthRequest request) throws Exception{
		
		System.out.println(request.toString());
		this.authenticate(request.getUsername(),request.getPassword());
	
		UserDetails userdetails=this.uService.loadUserByUsername(request.getUsername());
		
		String generatedToken=this.jhelper.generateTokens(userdetails);
	
		jwtAuthResponse res= new jwtAuthResponse();
		
		res.setToken(generatedToken);
		return new ResponseEntity<jwtAuthResponse>(res,HttpStatus.OK);
		
	}
	



	private void authenticate(String username, String password) throws Exception {
		// TODO Auto-generated method stub
		
		UsernamePasswordAuthenticationToken auntheticationToken=new UsernamePasswordAuthenticationToken(username, password);
	try {
		aManager.authenticate(auntheticationToken);
		
	} catch(DisabledException e) {
	throw new Exception("user diables"+ e.getMessage());	
	}catch(BadCredentialsException b) {
		throw new Exception("Bad credientials"+ b.getMessage());
	}
	
}
	
	//returns the details of current employee
	@GetMapping("/curr")
	public Employee getCurrentEmployee(Principal principal) {
		
		return (Employee)this.uService.loadUserByUsername(principal.getName());
	}
}
