package com.github.olivervbk.spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author oliver.kuster
 * @version 1.0 Created on 19 Jun 2016
 */
@Configuration
public class PasswordEncoderConfig
{

	/**
	 * @return
	 */
	@Bean( name = "passwordEncoder" )
	public PasswordEncoder createBcryptEncoder()
	{
		return new BCryptPasswordEncoder();
	}
}
