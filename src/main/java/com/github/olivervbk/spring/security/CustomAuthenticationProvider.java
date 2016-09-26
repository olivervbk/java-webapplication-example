package com.github.olivervbk.spring.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.github.olivervbk.model.User;
import com.github.olivervbk.service.UserService;

/**
 * @author oliver.kuster
 * @version 1.0 Created on 8 de jun de 2016
 */
@Component( "authProvider" )
public class CustomAuthenticationProvider
	implements AuthenticationProvider
{

	/**
	 * <p>
	 * Field <code>ROLE_PREFIX</code>
	 * </p>
	 */
	private static final String ROLE_PREFIX = "ROLE_";

	/**
	 */
	public CustomAuthenticationProvider()
	{
		super();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param authentication
	 * @return
	 * @see org.springframework.security.authentication.AuthenticationProvider#authenticate(org.springframework.security.core.Authentication)
	 */
	@Override
	public Authentication authenticate( final Authentication authentication )
	{
		final String name = authentication.getName();
		final Object credentials = authentication.getCredentials();
		final String password = ( String ) credentials;

		final UserService userService = getUserService();
		final User user = userService.getValidUser( name, password );

		if ( user != null )
		{
			final String userRole = ROLE_PREFIX + user.getRole().toUpperCase();
			final SimpleGrantedAuthority userAuthority = new SimpleGrantedAuthority( userRole );

			final List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
			grantedAuths.add( userAuthority );

			final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
				user,
				null,
				grantedAuths );
			return authToken;
		} // if

		return null;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return Returns the userService.
	 * @see #userService
	 */
	public UserService getUserService()
	{
		return this.userService;
	}

	/**
	 * @param arg0
	 * @return
	 * @see org.springframework.security.authentication.AuthenticationProvider#supports(java.lang.Class)
	 */
	@Override
	public boolean supports( final Class< ? > arg0 )
	{
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom( arg0 );
	}

	/**
	 * <p>
	 * Field <code>userService</code>
	 * </p>
	 */
	@Autowired
	@Qualifier( "userService" )
	private UserService userService;
}
