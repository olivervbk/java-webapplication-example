package com.github.olivervbk.dao;

import java.util.List;

import javax.annotation.Nullable;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.olivervbk.model.Timezone;
import com.github.olivervbk.model.User;

/**
 * @author oliver.kuster
 * @version 1.0 Created on 19 Jun 2016
 */
@Repository( "timezoneDao" )
public class TimezoneDao
	extends AbstractDao<Timezone>
{

	/**
	 * @param factory
	 */
	@Autowired
	public TimezoneDao( final SessionFactory factory )
	{
		super( factory, Timezone.class );
	}

	/**
	 * @param name
	 * @param owner
	 * @return
	 */
	public List<Timezone> findByName( final String name, @Nullable final User owner )
	{
		final Criteria criteria = createCriteria();
		criteria.add( Restrictions.like( "name", "%" + name + "%" ) );
		if ( owner != null )
		{
			criteria.add( Restrictions.eq( "owner", owner ) );
		}
		criteria.addOrder( Order.asc( "name" ) );
		return criteria.list();
	}

	/**
	 * @param user
	 * @return
	 */
	public List<Timezone> findByOwner( final User user )
	{
		final Criteria criteria = createCriteria();
		criteria.add( Restrictions.eq( "owner", user ) );
		criteria.addOrder( Order.asc( "name" ) );
		return criteria.list();
	}

}
