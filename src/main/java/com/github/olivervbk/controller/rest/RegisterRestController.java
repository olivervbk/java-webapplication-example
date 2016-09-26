package com.github.olivervbk.controller.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.olivervbk.ControllerConstants;
import com.github.olivervbk.model.User;
import com.github.olivervbk.service.UserService;

/**
 * @author oliver.kuster
 * @version 1.0 Created on 7 de jun de 2016
 */
@RestController( )
@RequestMapping( value = ControllerConstants.PATH_REST_REGISTER )
public class RegisterRestController
{

	/**
	 * @param userService
	 */
	@Autowired
	public RegisterRestController( final UserService userService )
	{
		super();
		this.userService = userService;
	}

	/**
	 * @param user
	 * @return
	 */
	@RequestMapping( method = RequestMethod.POST )
	public User register( @Valid @RequestBody final User user )
	{
		return this.userService.create( user );
	}

	/**
	 * <p>
	 * Field <code>userService</code>
	 * </p>
	 */
	private final UserService userService;
}
