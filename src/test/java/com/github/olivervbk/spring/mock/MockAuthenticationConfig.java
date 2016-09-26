package com.github.olivervbk.spring.mock;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import com.github.olivervbk.Constants;
import com.github.olivervbk.model.User;

/**
 * @author oliver.kuster
 * @version 1.0 Created on 19 Jun 2016
 */
@Configuration
public class MockAuthenticationConfig
{

	/**
	 * @param auth
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal( final AuthenticationManagerBuilder auth )
		throws Exception
	{
		auth
			.inMemoryAuthentication()
			.withUser( "user" )
			.password( "user" )
			.authorities( Constants.ROLE_USER )
			.and()
			.withUser( "admin" )
			.password( "admin" )
			.authorities( Constants.ROLE_ADMIN );

		auth.authenticationProvider( new AuthenticationProvider()
		{

			@Override
			public Authentication authenticate( final Authentication authentication )
				throws AuthenticationException
			{
				final String username = ( String ) authentication.getPrincipal();

				final User principal = new User();
				principal.setUsername( username );
				principal.setId( 1L );
				principal.setRole( username.toUpperCase() );

				final Authentication auth = new Authentication()
				{

					/**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public Collection< ? extends GrantedAuthority> getAuthorities()
					{
						return authentication.getAuthorities();
					}

					@Override
					public Object getCredentials()
					{
						return authentication.getCredentials();
					}

					@Override
					public Object getDetails()
					{
						return authentication.getDetails();
					}

					@Override
					public String getName()
					{
						return username;
					}

					@Override
					public Object getPrincipal()
					{
						return principal;
					}

					@Override
					public boolean isAuthenticated()
					{
						return true;
					}

					@Override
					public void setAuthenticated( final boolean isAuthenticated )
						throws IllegalArgumentException
					{
						// TODO Auto-generated method stub

					}
				};
				return auth;
			}

			@Override
			public boolean supports( final Class< ? > authentication )
			{
				return authentication == UsernamePasswordAuthenticationToken.class;
			}
		} );
	}
}
