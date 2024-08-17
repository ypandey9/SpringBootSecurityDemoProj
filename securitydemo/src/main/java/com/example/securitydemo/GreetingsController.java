package com.example.securitydemo;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingsController {

	@GetMapping("/hello")
	public String hello()
	{
		return "Hello";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin")
	public String endPointAdmin()
	{
		return "Hello , Admin";
	}
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/user")
	public String endPointUser()
	{
		return "Hello , User";
	}
}
