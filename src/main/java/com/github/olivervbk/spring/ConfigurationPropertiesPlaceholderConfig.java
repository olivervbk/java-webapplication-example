package com.github.olivervbk.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * <p>
 * </p>
 *
 * @author oliver.kuster
 * @version 1.0 Created on 07/10/2015
 */
@Configuration
@PropertySource( "classpath:config.properties" )
public class ConfigurationPropertiesPlaceholderConfig
{

	/**
	 */
	public ConfigurationPropertiesPlaceholderConfig()
	{
		super();
	}

	/**
	 * <p>
	 * To resolve ${} in @Value. Must be static. (I think..)
	 * </p>
	 *
	 * @return
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev()
	{
		final PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
		return configurer;
	}

	/**
	 */
	public void dummyMethod()
	{
		// does nothing, just to remove complaint about utility class constructor
	}

}
