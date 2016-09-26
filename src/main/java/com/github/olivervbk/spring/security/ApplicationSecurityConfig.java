package com.github.olivervbk.spring.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * @author oliver.kuster
 * @version 1.0 Created on 19 Jun 2016
 */
@EnableWebSecurity
@Configuration
public class ApplicationSecurityConfig
	extends WebSecurityConfigurerAdapter
{

	/**
	 */
	public ApplicationSecurityConfig()
	{
		super();
	}

	/**
	 * @param http
	 * @throws Exception
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
	 */
	@Override
	protected void configure( final HttpSecurity http )
		throws Exception
	{

		http
			.authorizeRequests()
			.antMatchers( "/", "/rest/register" )
			.permitAll()
			.antMatchers( "/rest/**" )
			.authenticated()
			.and()
			.httpBasic()
			.and()
			.sessionManagement()
			.sessionCreationPolicy( SessionCreationPolicy.STATELESS )
			.and()
			.csrf()
			.disable();
	}
}
