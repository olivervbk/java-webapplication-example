package com.github.olivervbk.spring;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import com.github.olivervbk.ConstantsBeanNames;

/**
 * @author oliver.kuster
 * @version 1.0 Created on 8 de jun de 2016
 */
@Configuration
public class DataBaseConfig
{

	/**
	 */
	public DataBaseConfig()
	{
		super();
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	@Bean( name = ConstantsBeanNames.DATA_SOURCE )
	public DataSource dataSource()
		throws SQLException
	{
		final boolean useEmbeddedDatabaseBuilder = false;
		if ( !useEmbeddedDatabaseBuilder )
		{
			final DriverManagerDataSource ds = new DriverManagerDataSource();

			ds.setDriverClassName( "org.hsqldb.jdbcDriver" );
			//ifexists=true
			ds.setUrl( "jdbc:hsqldb:file:h2db;files_readonly=true;ifexists=false" );
			ds.setUsername( "sa" );
			ds.setPassword( "" );

			return ds;
		}

		final EmbeddedDatabaseType databaseType = EmbeddedDatabaseType.HSQL;
		// final EmbeddedDatabaseType databaseType = EmbeddedDatabaseType.H2;

		final EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		builder.setType( databaseType );
		final EmbeddedDatabase ds = builder.build();

		if ( EmbeddedDatabaseType.H2.equals( databaseType ) )
		{
			final Connection csConnection = ds.getConnection();
			final Statement statement = csConnection.createStatement();
			statement.execute( "SET MODE PostgreSQL" );
		}
		return ds;
	}

	/**
	 * @return
	 */
	protected Properties getHibernateProperties()
	{
		final Properties ps = new Properties();

		ps.put( "hibernate.dialect", "org.hibernate.dialect.HSQLDialect" );

		// Propriedade abaixo causa erro de permissão em teste do Maven / Jenkins!
		// ps.put( "hibernate.hbm2ddl.auto", "create-drop" );
		ps.put( "hibernate.hbm2ddl.auto", "update" );
		ps.put( "hibernate.event.merge.entity_copy_observer", "allow" );

		// DEBUG
		// ps.put( "hibernate.show_sql", "true" );
		// ps.put( "hibernate.format_sql", "true" );
		// ps.put( "hibernate.use_sql_comments", "true" );
		return ps;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param dataSource
	 * @return
	 */
	@Autowired
	@Bean( name = ConstantsBeanNames.SESSION_FACTORY )
	public LocalSessionFactoryBean sessionFactory( final DataSource dataSource )
	{
		final Properties ps = getHibernateProperties();

		final LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource( dataSource );
		sessionFactory.setPackagesToScan( "com.github.olivervbk.model" );
		sessionFactory.setHibernateProperties( ps );

		return sessionFactory;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param sessionFactory
	 * @return
	 */
	@Autowired
	@Bean( name = "transactionManager" )
	public HibernateTransactionManager transactionManager( final SessionFactory sessionFactory )
	{
		final HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory( sessionFactory );

		return txManager;
	}

}
