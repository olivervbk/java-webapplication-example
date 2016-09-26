package com.github.olivervbk.controller.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.olivervbk.Constants;
import com.github.olivervbk.ControllerConstants;
import com.github.olivervbk.model.User;
import com.github.olivervbk.service.UserService;

/**
 * @author oliver.kuster
 * @version 1.0 Created on 7 de jun de 2016
 */
@RestController( )
@RequestMapping( value = ControllerConstants.PATH_REST_USER )
public class UserRestController
{

	/**
	 * @param userService
	 */
	@Autowired
	public UserRestController( final UserService userService )
	{
		super();
		this.userService = userService;
	}

	/**
	 * @param currentUser
	 * @return
	 */
	@RequestMapping( "/auth" )
	public User auth( @AuthenticationPrincipal final User currentUser )
	{
		return currentUser;
	}

	/**
	 * @param user
	 * @return
	 */
	@Secured( Constants.ROLE_ADMIN )
	@RequestMapping( method = RequestMethod.POST )
	public User create( @Valid @RequestBody final User user )
	{
		return this.userService.create( user );
	}

	/**
	 * @param id
	 */
	@Secured( Constants.ROLE_ADMIN )
	@RequestMapping( value = "/{id}", method = RequestMethod.DELETE )
	public void delete( @PathVariable final Long id )
	{
		this.userService.delete( id );
	}

	/**
	 * @param id
	 * @return
	 */
	@Secured( Constants.ROLE_ADMIN )
	@RequestMapping( value = "/{id}", method = RequestMethod.GET )
	public User get( @PathVariable final Long id )
	{
		return this.userService.get( id );
	}

	/**
	 * @param principal
	 * @return
	 */
	@Secured( Constants.ROLE_ADMIN )
	@RequestMapping( method = RequestMethod.GET )
	public List<User> list()
	{
		return this.userService.list();
	}

	/**
	 * @param id
	 * @param user
	 */
	@Secured( Constants.ROLE_ADMIN )
	@RequestMapping( value = "/{id}", method = RequestMethod.PUT )
	public void save( @PathVariable final Long id, @Valid @RequestBody final User user )
	{
		user.setId( id );
		this.userService.update( user );
	}

	/**
	 * <p>
	 * Field <code>userService</code>
	 * </p>
	 */
	private final UserService userService;
}
