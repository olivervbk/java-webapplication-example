package com.github.olivervbk.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author oliver.kuster
 * @version 1.0 Created on 19 Jun 2016
 */
@MappedSuperclass
public abstract class AbstractBean
{

	/**
	 *
	 */
	public AbstractBean()
	{
		super();
	}

	/**
	 * @return Returns the id.
	 * @see #id
	 */
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	public Long getId()
	{
		return this.id;
	}

	/**
	 * @param id
	 *            The id to set.
	 * @see #id
	 */
	public void setId( final Long id )
	{
		this.id = id;
	}

	/**
	 * <p>
	 * Field <code>id</code>
	 * </p>
	 */
	private Long id;

}
