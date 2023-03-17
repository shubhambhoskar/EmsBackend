package com.employee.Security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class jwtAuthFilter extends OncePerRequestFilter{

	@Autowired
	private UserDetailsService uService;
	
	@Autowired
	private jwtTokenHelper jhelper;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//1.  fetch jwt tokens
		// get token
		String requestToken=request.getHeader("Authorization");
		
		System.out.println(requestToken);
		
		String username=null;
		
		String token=null; // from bearer we get the token
		
		if(requestToken!=null && requestToken.startsWith("Bearer")) {
			
			// token without Bearer
			token=requestToken.substring(7);
			 
			try {
			username=this.jhelper.getUsernameFromToken(token);
			}
			catch(IllegalArgumentException e) {
				System.out.println("Unable to get jwt ");
			}
			catch(ExpiredJwtException e) {
				System.out.println(e);
				System.out.println("jwt token expired");
			}catch(MalformedJwtException e) {
				System.out.println("Invalid Exception");
			}
			
			
		}else{
			System.out.println("Jwt does not begin with bearer");
		}
		
		//we get here token
		// validate
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null ) {
			
			UserDetails usedetails=this.uService.loadUserByUsername(username);
			
			
			if(this.jhelper.validateToken(token, usedetails)) {
				// going correct then authenticate
				
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(usedetails,null, usedetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
			}else {
				System.out.println("Invalid JWT token");
			}
			
		}else {
			System.out.println("Username is null or context not null");
		}
		
		
		filterChain.doFilter(request, response);
	}

}
