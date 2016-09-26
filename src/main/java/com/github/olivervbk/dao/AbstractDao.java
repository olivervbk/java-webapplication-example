package com.github.olivervbk.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.github.olivervbk.model.AbstractBean;

/**
 * @param <T>
 * @author oliver.kuster
 * @version 1.0 Created on 19 Jun 2016
 */
public abstract class AbstractDao<T extends AbstractBean>
{

	/**
	 * @param factory
	 * @param beanClazz
	 */
	public AbstractDao( final SessionFactory factory, final Class<T> beanClazz )
	{
		super();
		this.sessionFactory = factory;
		this.beanClazz = beanClazz;
	}

	/**
	 * @return
	 */
	protected Criteria createCriteria()
	{

		final Criteria criteria = getCurrentSession().createCriteria( getBeanClazz() );
		return criteria;
	}

	/**
	 * @param bean
	 */
	public void delete( final T bean )
	{
		getCurrentSession().delete( bean );
	}

	/**
	 * @param id
	 * @return
	 */
	public T get( final Long id )
	{
		final Session currentSession = getCurrentSession();
		final T bean = ( T ) currentSession.get( getBeanClazz(), id );
		return bean;
	}

	/**
	 * @return
	 */
	protected Class<T> getBeanClazz()
	{
		return this.beanClazz;
	}

	/**
	 * @return
	 */
	protected Session getCurrentSession()
	{
		final Session currentSession = this.sessionFactory.getCurrentSession();
		return currentSession;
	}

	/**
	 * @return
	 */
	public List<T> list()
	{
		final Criteria criteria = createCriteria();
		final List<T> list = criteria.setFlushMode( FlushMode.MANUAL ).list();
		return list;
	}

	/**
	 * @param bean
	 */
	public void saveOrUpdate( final T bean )
	{
		getCurrentSession().saveOrUpdate( bean );
	}

	/**
	 * <p>
	 * Field <code>beanClazz</code>
	 * </p>
	 */
	private final Class<T> beanClazz;

	/**
	 * <p>
	 * Field <code>sessionFactory</code>
	 * </p>
	 */
	protected final SessionFactory sessionFactory;
}
