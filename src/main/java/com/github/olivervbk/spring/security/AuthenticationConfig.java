package com.github.olivervbk.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

/**
 * @author oliver.kuster
 * @version 1.0 Created on 19 Jun 2016
 */
@Configuration
public class AuthenticationConfig
{

	/**
	 * @param auth
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal( final AuthenticationManagerBuilder auth )
	{
		final AuthenticationProvider authProvider = getAuthProvider();
		auth.authenticationProvider( authProvider );
	}

	/**
	 * @return Returns the authProvider.
	 * @see #authProvider
	 */
	public AuthenticationProvider getAuthProvider()
	{
		return this.authProvider;
	}

	/**
	 * <p>
	 * Field <code>authProvider</code>
	 * </p>
	 */
	@Autowired
	@Qualifier( "authProvider" )
	private AuthenticationProvider authProvider;
}
