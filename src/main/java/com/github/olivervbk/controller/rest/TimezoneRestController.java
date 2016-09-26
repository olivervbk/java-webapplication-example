package com.github.olivervbk.controller.rest;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.olivervbk.ControllerConstants;
import com.github.olivervbk.model.Timezone;
import com.github.olivervbk.model.User;
import com.github.olivervbk.service.TimezoneService;

/**
 * The Class TimezoneRestController.
 *
 * @author oliver.kuster
 * @version 1.0 Created on 7 de jun de 2016
 */
@RestController( )
@RequestMapping( value = ControllerConstants.PATH_REST_TIMEZONE )
public class TimezoneRestController
{

	/**
	 * Instantiates a new timezone rest controller.
	 *
	 * @param service
	 *            the service
	 */
	@Autowired
	public TimezoneRestController( final TimezoneService service )
	{
		super();
		this.userService = service;
	}

	/**
	 * Checks if is admin.
	 *
	 * @param currentUser
	 *            the current user
	 * @return true, if is admin
	 */
	protected static boolean isAdmin( final User currentUser )
	{
		return "ADMIN".equals( currentUser.getRole() );
	}

	/**
	 * @param timezone
	 *            the timezone
	 * @param currentUser
	 *            the current user
	 * @param result
	 * @return the response entity
	 */
	@RequestMapping( method = RequestMethod.POST )
	public ResponseEntity<Timezone> create(
		@Valid @RequestBody final Timezone timezone,
		@AuthenticationPrincipal final User currentUser,
		final BindingResult result )
	{
		if ( result.hasErrors() )
		{
			return new ResponseEntity<Timezone>( HttpStatus.BAD_REQUEST );
		}
		final TimezoneService service = getService();
		final Timezone newTz = service.create( timezone, currentUser );
		return new ResponseEntity<Timezone>( newTz, HttpStatus.CREATED );
	}

	/**
	 * @param tz
	 * @return
	 */
	@PreAuthorize( "hasPermission(#tz, 'OWNER') || hasPermission(#tz, 'ADMIN')" )
	@RequestMapping( value = "/{tz}", method = RequestMethod.DELETE )
	public void delete( @PathVariable final Timezone tz )
	{
		final TimezoneService service = getService();
		service.delete( tz.getId() );
	}

	/**
	 * Gets the.
	 *
	 * @param tz
	 *            the tz
	 * @return the timezone
	 */
	@RequestMapping( value = "/{tz}", method = RequestMethod.GET )
	public Timezone get( @PathVariable final Timezone tz )
	{
		return tz;
	}

	/**
	 * @return
	 */
	protected TimezoneService getService()
	{
		return this.userService;
	}

	/**
	 * @param currentUser
	 * @return
	 */
	@RequestMapping( method = RequestMethod.GET )
	public List<Timezone> list( final @AuthenticationPrincipal User currentUser )
	{
		final TimezoneService service = getService();

		if ( isAdmin( currentUser ) )
		{
			return service.list();
		}

		return service.list( currentUser );
	}

	/**
	 * @param tz
	 * @param tzToSave
	 * @param currentUser
	 * @return
	 */
	@PreAuthorize( "hasPermission(#tz, 'ADMIN') || hasPermission(#tz, 'OWNER')" )
	@RequestMapping( value = "/{tz}", method = RequestMethod.PUT )
	public void save( @PathVariable final Timezone tz, @Valid @RequestBody final Timezone tzToSave )
	{
		final User owner = tz.getOwner();

		tzToSave.setId( tz.getId() );
		tzToSave.setOwner( owner );

		final TimezoneService service = getService();
		service.update( tzToSave );
	}

	/**
	 * @param name
	 * @param currentUser
	 * @return
	 */
	@RequestMapping( value = "/search", method = RequestMethod.GET )
	public List<Timezone> search( @RequestParam final String name, @AuthenticationPrincipal final User currentUser )
	{
		final TimezoneService service = getService();

		if ( isAdmin( currentUser ) )
		{
			return service.findByName( name );
		}

		return service.findByName( name, currentUser );
	}

	/**
	 * @param tz
	 *            the tz
	 * @return the long
	 */
	@RequestMapping( value = "/{tz}/time", method = RequestMethod.GET )
	public ResponseEntity<Long> time( @PathVariable final Timezone tz )
	{
		if ( tz == null )
		{
			return new ResponseEntity( HttpStatus.BAD_REQUEST );
		}

		final int offset = tz.getOffset();
		final ZoneId zone = ZoneOffset.ofOffset( "GMT", ZoneOffset.ofHours( offset ) );

		final Instant now = Instant.now();
		final ZonedDateTime zonedTime = now.atZone( zone );

		final long epochSecond = zonedTime.toLocalDateTime().toEpochSecond( ZoneOffset.UTC );
		return new ResponseEntity<Long>( epochSecond, HttpStatus.OK );
	}

	/**
	 * <p>
	 * Field <code>userService</code>
	 * </p>
	 */
	private final TimezoneService userService;
}
