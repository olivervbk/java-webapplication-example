package com.github.olivervbk.spring;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.github.olivervbk.Constants;

/**
 * @author felipe.santos
 * @version 1.0 Created on 10/09/2015
 */
public class ApplicationContextInit
	implements WebApplicationInitializer
{

	/**
	 */
	public ApplicationContextInit()
	{
		super();
	}

	/**
	 * @param container
	 * @throws ServletException
	 * @see org.springframework.web.WebApplicationInitializer#onStartup(javax.servlet.ServletContext)
	 */
	@Override
	public void onStartup( final ServletContext container )
		throws ServletException
	{
		final AnnotationConfigWebApplicationContext contextConfig = new AnnotationConfigWebApplicationContext();
		contextConfig.register( ApplicationContextConfig.class );
		contextConfig.setServletContext( container );
		contextConfig.setDisplayName( "timezone-proj" );

		contextConfig.registerShutdownHook();

		final ContextLoaderListener contextLoaderListener = new ContextLoaderListener( contextConfig );
		container.addListener( contextLoaderListener );

		container.setInitParameter( "defaultHtmlEscape", "true" );

		// final String filterUrlPattern = "/*";
		// final boolean isMatchAfter = false;
		// final EnumSet<DispatcherType> dispatcherTypes = null;
		// container.addFilter( "openSessionInViewFilter", OpenSessionInViewFilter.class
		// ).addMappingForUrlPatterns(
		// dispatcherTypes,
		// isMatchAfter,
		// filterUrlPattern );

		final DispatcherServlet dispatcherServlet = new DispatcherServlet( contextConfig );
		final ServletRegistration.Dynamic servlet = container
			.addServlet( Constants.SERVLET_DISPATCHER, dispatcherServlet );

		servlet.setAsyncSupported( true );
		servlet.setLoadOnStartup( 1 );
		servlet.addMapping( "/" );
	}
}
