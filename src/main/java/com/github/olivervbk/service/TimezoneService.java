package com.github.olivervbk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.olivervbk.dao.TimezoneDao;
import com.github.olivervbk.model.Timezone;
import com.github.olivervbk.model.User;

/**
 * @author oliver.kuster
 * @version 1.0 Created on 19 Jun 2016
 */
@Transactional
@Service( "timezoneService" )
public class TimezoneService
	extends AbstractService<Timezone>
{

	/**
	 * @param dao
	 */
	@Autowired
	public TimezoneService( final TimezoneDao dao )
	{
		super();
		this.dao = dao;
	}

	/**
	 * @param timezone
	 * @param owner
	 * @return
	 */
	public Timezone create( final Timezone timezone, final User owner )
	{
		timezone.setOwner( owner );

		getDao().saveOrUpdate( timezone );
		return timezone;
	}

	/**
	 * @param name
	 * @return
	 */
	public List<Timezone> findByName( final String name )
	{
		return getDao().findByName( name, null );
	}

	/**
	 * @param name
	 * @param currentUser
	 * @return
	 */
	public List<Timezone> findByName( final String name, final User currentUser )
	{
		return getDao().findByName( name, currentUser );
	}

	/**
	 * @return
	 * @see com.github.olivervbk.service.AbstractService#getDao()
	 */
	@Override
	protected TimezoneDao getDao()
	{
		return this.dao;
	}

	/**
	 * @param user
	 * @return
	 */
	public List<Timezone> list( final User user )
	{
		return getDao().findByOwner( user );
	}

	/**
	 * <p>
	 * Field <code>dao</code>
	 * </p>
	 */
	private final TimezoneDao dao;

}
