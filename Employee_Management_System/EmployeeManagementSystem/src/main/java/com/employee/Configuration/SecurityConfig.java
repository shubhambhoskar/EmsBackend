package com.employee.Configuration;
import com.employee.Security.CustomUserService;
import com.employee.Security.jwtAuthFilter;
import com.employee.Security.jwtauthEntry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter{

	@Autowired
	private CustomUserService eService;
	
	@Autowired
//	private jwtauthEntry jwtauthEntry;
	private jwtauthEntry jwtauthEntry;
	
	@Autowired
//	private jwtAuthFilter jwtAuthFilter;
	private jwtAuthFilter jwtAuthFilter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors();
		http
		 .csrf()
		 .disable()
         .authorizeHttpRequests()
         .antMatchers("/jwtlogin","/Employee").permitAll()
         .anyRequest() 
         .authenticated()
         .and()
		.exceptionHandling().authenticationEntryPoint(this.jwtauthEntry)
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	
	
		http.addFilterBefore(this.jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(this.eService).passwordEncoder(passwordEncoder());
	}
	
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	
//	@Bean
//	public WebSecurityConfigurerAdapter webSecurity() {
//	    return new WebSecurityConfigurerAdapter() {
//
//	        @Override
//	        protected void configure(HttpSecurity http) throws Exception {
//	            http.headers().addHeaderWriter(
//	                    new StaticHeadersWriter("Access-Control-Allow-Origin", "*"));
//
//
//	        }
//	    };
//	}
}
