package com.github.olivervbk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.olivervbk.dao.UserDao;
import com.github.olivervbk.model.User;

/**
 * @author oliver.kuster
 * @version 1.0 Created on 7 de jun de 2016
 */
@Transactional
@Service( "userService" )
public class UserService
	extends AbstractService<User>
{

	/**
	 * @param userDao
	 * @param encoder
	 */
	@Autowired
	public UserService( final UserDao userDao, final PasswordEncoder encoder )
	{
		super();
		this.dao = userDao;
		this.encoder = encoder;
	}

	/**
	 * @param user
	 * @return
	 */
	public User create( final User user )
	{
		encodeUserPassword( user );
		save( user );
		return user;
	}

	/**
	 * @param user
	 */
	protected void encodeUserPassword( final User user )
	{
		final String password = user.getPassword();
		final String passwordHash = this.encoder.encode( password );
		user.setPassword( passwordHash );
	}

	/**
	 * @return
	 * @see com.github.olivervbk.service.AbstractService#getDao()
	 */
	@Override
	protected UserDao getDao()
	{
		return this.dao;
	}

	/**
	 * @param name
	 * @param password
	 * @return
	 */
	public User getValidUser( final String name, final String password )
	{
		final UserDao dao = getDao();
		final User user = dao.findByUsername( name );
		if ( user == null )
		{
			return null;
		}

		final String passwordHash = user.getPassword();
		final boolean isPasswordValid = isHashEqual( passwordHash, password );
		if ( !isPasswordValid )
		{
			return null;
		}

		return user;
	}

	/**
	 * @param passwordHash
	 * @param password
	 * @return
	 */
	private boolean isHashEqual( final String passwordHash, final String password )
	{
		return this.encoder.matches( password, passwordHash );
	}

	/**
	 * @param bean
	 * @see com.github.olivervbk.service.AbstractService#update(com.github.olivervbk.model.AbstractBean)
	 */
	@Override
	public void update( final User user )
	{
		encodeUserPassword( user );
		super.update( user );
	}

	/**
	 * <p>
	 * Field <code>userDao</code>
	 * </p>
	 */
	private final UserDao dao;

	/**
	 * <p>
	 * Field <code>encoder</code>
	 * </p>
	 */
	private final PasswordEncoder encoder;
}
