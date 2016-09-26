package com.github.olivervbk.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.github.olivervbk.model.Timezone;
import com.github.olivervbk.service.TimezoneService;

/**
 * @author oliver.kuster
 * @version 1.0 Created on 19 Jun 2016
 */
@Component
public class TimezoneBeanConverter
	implements Converter<String, Timezone>
{

	/**
	 * Logger for this class
	 */
	private static final Log LOG = LogFactory.getLog( TimezoneBeanConverter.class );

	/**
	 * @param timezoneService
	 */
	@Autowired
	public TimezoneBeanConverter( final TimezoneService timezoneService )
	{
		super();
		this.timezoneService = timezoneService;
	}

	/**
	 * @param idStr
	 * @return
	 * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
	 */
	@Override
	public Timezone convert( final String idStr )
	{
		try
		{
			final Long id = Long.valueOf( idStr );
			return this.timezoneService.get( id );
		}
		catch ( final NumberFormatException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				final String logMsg = String.format( "Error converting '%s' to Long.", idStr );
				LOG.error( logMsg );
			}// if
			return null;
		}
	}

	/**
	 * <p>
	 * Field <code>timezoneService</code>
	 * </p>
	 */
	private final TimezoneService timezoneService;
}
