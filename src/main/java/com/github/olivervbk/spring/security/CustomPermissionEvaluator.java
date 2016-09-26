package com.github.olivervbk.spring.security;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.github.olivervbk.model.Timezone;
import com.github.olivervbk.model.User;

/**
 * @author oliver.kuster
 * @version 1.0 Created on 19 Jun 2016
 */
@Component
public class CustomPermissionEvaluator
	implements PermissionEvaluator
{

	/**
	 * @param currentUser
	 * @param user
	 * @return
	 */
	protected static boolean isUserEqual( final User currentUser, final User user )
	{
		final String currentUsername = currentUser.getUsername();
		final boolean userIsSame = currentUsername.equals( user.getUsername() );
		return userIsSame;
	}

	/**
	 * @param authentication
	 * @param targetDomainObject
	 * @param permission
	 * @return
	 * @see org.springframework.security.access.PermissionEvaluator#hasPermission(org.springframework.security.core.Authentication,
	 *      java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean hasPermission(
		final Authentication authentication,
		final Object targetDomainObject,
		final Object permission )
	{
		if ( targetDomainObject == null )
		{
			return false;
		}

		final User currentUser = ( User ) authentication.getPrincipal();
		if ( "ADMIN".equals( permission ) )
		{
			final String currentUserRole = currentUser.getRole();
			if ( "ADMIN".equals( currentUserRole ) )
			{
				return true;
			}
			return false;
		}

		if ( "OWNER".equals( permission ) )
		{
			if ( targetDomainObject instanceof User )
			{
				final User user = ( User ) targetDomainObject;

				final boolean userIsSame = isUserEqual( currentUser, user );
				if ( userIsSame )
				{
					return true;
				}

			}
			else if ( targetDomainObject instanceof Timezone )
			{
				final Timezone timezone = ( Timezone ) targetDomainObject;
				final User owner = timezone.getOwner();
				final boolean userIsSame = isUserEqual( currentUser, owner );
				if ( userIsSame )
				{
					return true;
				}
			}
			return false;
		}
		return false;
	}

	/**
	 * @param authentication
	 * @param targetId
	 * @param targetType
	 * @param permission
	 * @return
	 * @see org.springframework.security.access.PermissionEvaluator#hasPermission(org.springframework.security.core.Authentication,
	 *      java.io.Serializable, java.lang.String, java.lang.Object)
	 */
	@Override
	public boolean hasPermission(
		final Authentication authentication,
		final Serializable targetId,
		final String targetType,
		final Object permission )
	{
		// TODO Auto-generated method stub
		return false;
	}

}
