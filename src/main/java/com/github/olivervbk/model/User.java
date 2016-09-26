package com.github.olivervbk.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author oliver.kuster
 * @version 1.0 Created on 7 de jun de 2016
 */
@Entity
@Table( name = "user" )
public class User
	extends AbstractBean
{

	/**
	 *
	 */
	public User()
	{
		super();
	}

	/**
	 * @param username
	 */
	public User( final String username )
	{
		super();
		setUsername( username );
	}

	/**
	 * @return Returns the password.
	 * @see #password
	 */
	@JsonIgnore
	@NotEmpty
	@Column( name = "password", nullable = false )
	public String getPassword()
	{
		return this.password;
	}

	/**
	 * @return
	 */
	@NotEmpty
	@Column( name = "role", nullable = false )
	public String getRole()
	{
		return this.role;
	}

	/**
	 * @return Returns the username.
	 * @see #username
	 */
	@NotEmpty
	@Column( name = "username", unique = true )
	public String getUsername()
	{
		return this.username;
	}

	/**
	 * @param password
	 *            The password to set.
	 * @see #password
	 */
	@JsonProperty( "password" )
	public void setPassword( final String password )
	{
		this.password = password;
	}

	/**
	 * @param role
	 */
	public void setRole( final String role )
	{
		this.role = role;
	}

	/**
	 * @param username
	 *            The username to set.
	 * @see #username
	 */
	public void setUsername( final String username )
	{
		this.username = username;
	}

	/**
	 * <p>
	 * Field <code>password</code>
	 * </p>
	 */
	private String password;

	/**
	 * <p>
	 * Field <code>role</code>
	 * </p>
	 */
	private String role;

	/**
	 * <p>
	 * Field <code>username</code>
	 * </p>
	 */
	private String username;
}
