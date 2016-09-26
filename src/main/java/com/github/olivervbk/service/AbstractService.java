package com.github.olivervbk.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.github.olivervbk.dao.AbstractDao;
import com.github.olivervbk.model.AbstractBean;

/**
 * @param <T>
 * @author oliver.kuster
 * @version 1.0 Created on 19 Jun 2016
 */
@Transactional
public abstract class AbstractService<T extends AbstractBean>
{

	/**
	 *
	 */
	public AbstractService()
	{
		super();
	}

	/**
	 * @param id
	 */
	public void delete( final Long id )
	{
		final AbstractDao<T> dao = getDao();
		final T bean = dao.get( id );
		delete( bean );
	}

	/**
	 * @param bean
	 */
	public void delete( final T bean )
	{
		getDao().delete( bean );
	}

	/**
	 * @param id
	 * @return
	 */
	public T get( final Long id )
	{
		return getDao().get( id );
	}

	/**
	 * @return
	 */
	protected abstract <D extends AbstractDao<T>> D getDao();

	/**
	 * @return
	 */
	public List<T> list()
	{
		return getDao().list();
	}

	/**
	 * @param bean
	 */
	public void save( final T bean )
	{
		getDao().saveOrUpdate( bean );
	}

	/**
	 * @param bean
	 */
	public void update( final T bean )
	{
		getDao().saveOrUpdate( bean );
	}
}
