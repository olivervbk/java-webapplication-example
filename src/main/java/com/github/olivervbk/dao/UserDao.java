package com.github.olivervbk.dao;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.olivervbk.model.User;

/**
 * @author oliver.kuster
 * @version 1.0 Created on 7 de jun de 2016
 */
@Repository( "userDao" )
public class UserDao
	extends AbstractDao<User>
{

	/**
	 * @param sessionFactory
	 */
	@Autowired
	public UserDao( final SessionFactory sessionFactory )
	{
		super( sessionFactory, User.class );
	}

	/**
	 * @param name
	 * @return
	 */
	public User findByUsername( final String name )
	{
		final Criteria critaeria = createCriteria();
		critaeria.add( Restrictions.eq( "username", name ) );
		return ( User ) critaeria.uniqueResult();
	}

}
