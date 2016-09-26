package com.github.olivervbk.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author oliver.kuster
 * @version 1.0 Created on 19 Jun 2016
 */
@Entity
@Table( name = "timezone" )
public class Timezone
	extends AbstractBean
{

	/**
	 *
	 */
	public Timezone()
	{
		super();
	}

	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	@NotEmpty
	@Size( min = 1, max = 255 )
	@Column( name = "city", nullable = false )
	public String getCity()
	{
		return this.city;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	@NotEmpty
	@Size( min = 1, max = 255 )
	@Column( name = "name", nullable = false )
	public String getName()
	{
		return this.name;
	}

	/**
	 * Gets the offset.
	 *
	 * @return the offset
	 */
	@NotNull
	@Max( 18 )
	@Min( -18 )
	@Column( name = "offset", nullable = false )
	public int getOffset()
	{
		return this.offset;
	}

	/**
	 * Gets the owner.
	 *
	 * @return the owner
	 */
	@ManyToOne
	@JoinColumn( name = "owner", nullable = false )
	public User getOwner()
	{
		return this.owner;
	}

	/**
	 * Sets the city.
	 *
	 * @param city
	 *            the new city
	 */
	public void setCity( final String city )
	{
		this.city = city;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName( final String name )
	{
		this.name = name;
	}

	/**
	 * Sets the offset.
	 *
	 * @param offset
	 *            the new offset
	 */
	public void setOffset( final int offset )
	{
		this.offset = offset;
	}

	/**
	 * Sets the owner.
	 *
	 * @param owner
	 *            the new owner
	 */
	public void setOwner( final User owner )
	{
		this.owner = owner;
	}

	/**
	 * <p>
	 * Field <code>city</code>
	 * </p>
	 */
	private String city;

	/**
	 * <p>
	 * Field <code>name</code>
	 * </p>
	 */
	private String name;

	/**
	 * <p>
	 * Field <code>offset</code>
	 * </p>
	 */
	private int offset;

	/**
	 * <p>
	 * Field <code>owner</code>
	 * </p>
	 */
	private User owner;
}
