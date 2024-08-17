package com.example.securitydemo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Autowired	
	DataSource dataSource;
	@Bean
	public SecurityFilterChain defaultSecurityFiletrChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests)->
		requests.requestMatchers("/h2-console/**").permitAll()
		.anyRequest().authenticated());
//		http.sessionManagement(session->
//		session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.formLogin();
		http.httpBasic();
		http.headers(headers->
		headers.frameOptions(frameOptions->frameOptions.sameOrigin()));
		http.csrf().disable();
		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user1=User.withUsername("user1")
				.password(passwordEncoder().encode("password1"))
				.roles("USER")
				.build();
		UserDetails admin=User.withUsername("admin")
				.password(passwordEncoder().encode("admin"))
				.roles("ADMIN")
				.build();
		JdbcUserDetailsManager userDetailsManager=new JdbcUserDetailsManager(dataSource);
		userDetailsManager.createUser(user1);
		userDetailsManager.createUser(admin);
		return userDetailsManager;
		//return new InMemoryUserDetailsManager(user1,admin);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
}