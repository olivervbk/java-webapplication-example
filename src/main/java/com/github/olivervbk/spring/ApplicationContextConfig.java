package com.github.olivervbk.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author felipe.santos
 * @version 1.0 Created on 10/09/2015
 */
@Configuration
@ComponentScan( basePackages = {"com.github.olivervbk"} )
@EnableTransactionManagement
@EnableWebMvc
public class ApplicationContextConfig
	extends WebMvcConfigurerAdapter
{

	/**
	 */
	public ApplicationContextConfig()
	{
		super();
	}

	/**
	 * @param registry
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addFormatters(org.springframework.format.FormatterRegistry)
	 */
	@Override
	public void addFormatters( final FormatterRegistry registry )
	{
		registry.addConverter( this.timezoneConverter );
	}

	/**
	 * @param registry
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addResourceHandlers(org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry)
	 */
	@Override
	public void addResourceHandlers( final ResourceHandlerRegistry registry )
	{
		registry.addResourceHandler( "/static/**" ).addResourceLocations( "/static/" ).setCachePeriod( 0 );
	}

	/**
	 * <p>
	 * Field <code>timezoneConverter</code>
	 * </p>
	 */
	@Autowired
	private TimezoneBeanConverter timezoneConverter;

}
