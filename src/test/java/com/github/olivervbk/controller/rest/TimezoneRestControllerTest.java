package com.github.olivervbk.controller.rest;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.github.olivervbk.model.Timezone;
import com.github.olivervbk.model.User;
import com.github.olivervbk.service.TimezoneService;
import com.github.olivervbk.spring.TimezoneBeanConverter;
import com.github.olivervbk.spring.mock.MockAuthenticationConfig;
import com.github.olivervbk.spring.security.ApplicationSecurityConfig;
import com.github.olivervbk.spring.security.CustomPermissionEvaluator;

/**
 * @author oliver.kuster
 * @version 1.0 Created on 19 Jun 2016
 */
@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = {	ApplicationSecurityConfig.class,
									MockAuthenticationConfig.class,
									CustomPermissionEvaluator.class,} )
@WebAppConfiguration
public class TimezoneRestControllerTest
{

	/**
	 * @return
	 */
	private static RequestPostProcessor admin()
	{
		return httpBasic( "admin", "admin" );
	}

	/**
	 * @param calculatedEpoch
	 * @param expectedEpoch
	 * @return
	 */
	protected static boolean equalsRange( final long calculatedEpoch, final long expectedEpoch )
	{
		return ( ( calculatedEpoch - 5 ) < expectedEpoch ) && ( ( calculatedEpoch + 5 ) > expectedEpoch );
	}

	/**
	 * @return
	 */
	private static RequestPostProcessor user()
	{
		return httpBasic( "user", "user" );
	}

	/**
	 *
	 */
	@Before
	public void setUp()
	{
		MockitoAnnotations.initMocks( this );

		final TimezoneBeanConverter timezoneConverter = new TimezoneBeanConverter( this.service );

		final FormattingConversionService conversionService = new FormattingConversionService();
		conversionService.addConverter( timezoneConverter );

		this.mockMvc = MockMvcBuilders
			.standaloneSetup( this.controller )
			.alwaysDo( MockMvcResultHandlers.print() )
			.apply( SecurityMockMvcConfigurers.springSecurity( this.springSecurityFilterChain ) )
			.setConversionService( conversionService )
			.setCustomArgumentResolvers( new AuthenticationPrincipalArgumentResolver() )
			.build();
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void shouldGetDifferentTime()
		throws Exception
	{
		final int offset = -3;

		final Timezone timezone = new Timezone();
		timezone.setOffset( offset );
		Mockito.when( this.service.get( Matchers.anyLong() ) ).thenReturn( timezone );

		final MvcResult result = this.mockMvc
			.perform( get( "/rest/timezone/1/time" ).with( admin() ) )
			.andExpect( MockMvcResultMatchers.status().isOk() )
			.andReturn();

		final String content = result.getResponse().getContentAsString();
		final long time = Long.valueOf( content );

		final Instant calculated = Instant.ofEpochSecond( time );
		final Instant reference = Instant.now();
		final Instant expected = reference.plus( offset, ChronoUnit.HOURS );

		final long calculatedEpoch = calculated.getEpochSecond();
		final long referenceEpoch = reference.getEpochSecond();
		final long expectedEpoch = expected.getEpochSecond();

		final boolean isCorrect = equalsRange( calculatedEpoch, expectedEpoch );
		Assert.assertTrue( isCorrect );

		final boolean referenceFalse = equalsRange( referenceEpoch, expectedEpoch );
		Assert.assertFalse( "Should NOT be similar: " + referenceEpoch + ", and: " + expectedEpoch, referenceFalse );

	}

	/**
	 * @throws Exception
	 */
	@Test
	public void shouldGetLocalTime()
		throws Exception
	{
		final Timezone timezone = new Timezone();
		timezone.setOffset( 0 );
		Mockito.when( this.service.get( Matchers.anyLong() ) ).thenReturn( timezone );

		final MvcResult result = this.mockMvc
			.perform( get( "/rest/timezone/1/time" ).with( admin() ) )
			.andExpect( MockMvcResultMatchers.status().isOk() )
			.andReturn();

		final String content = result.getResponse().getContentAsString();
		final long time = Long.valueOf( content );

		final long seconds = Instant.now().getEpochSecond();

		final boolean shouldBeEqual = equalsRange( time, seconds );
		Assert.assertTrue( "Should be similar: " + time + ", and: " + seconds, shouldBeEqual );
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void shouldLetAdminDeleteAnyTimezone()
		throws Exception
	{
		Mockito.doNothing().when( this.service ).delete( Matchers.anyLong() );

		final Timezone timezone = new Timezone();

		Mockito.when( this.service.get( Matchers.anyLong() ) ).thenReturn( timezone );

		this.mockMvc
			.perform( delete( "/rest/timezone/1" ).with( admin() ) )
			.andExpect( MockMvcResultMatchers.status().isOk() );
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void shouldLetOwnerDeleteTimezone()
		throws Exception
	{
		Mockito.doNothing().when( this.service ).delete( Matchers.anyLong() );

		final User owner = new User();
		owner.setUsername( "user" );
		owner.setId( 1L );

		final Timezone timezone = new Timezone();
		timezone.setOwner( owner );

		Mockito.when( this.service.get( Matchers.anyLong() ) ).thenReturn( timezone );

		this.mockMvc
			.perform( delete( "/rest/timezone/1" ).with( user() ) )
			.andExpect( MockMvcResultMatchers.status().isOk() );

		verify( this.service, times( 1 ) ).delete( Matchers.anyLong() );
		verify( this.service, times( 1 ) ).get( Matchers.anyLong() );
		verifyNoMoreInteractions( this.service );
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void shouldLetOwnerUpdateTimezone()
		throws Exception
	{
		doNothing().when( this.service ).update( Matchers.anyObject() );

		final User owner = new User( "user" );
		owner.setId( 1L );

		final Timezone timezone = new Timezone();
		timezone.setOwner( owner );

		when( this.service.get( Matchers.anyLong() ) ).thenReturn( timezone );

		this.mockMvc.perform( put( "/rest/timezone/1", timezone ).with( user() ) ).andExpect(
			MockMvcResultMatchers.status().isOk() );
	}

	/**
	 * <p>
	 * Field <code>controller</code>
	 * </p>
	 */
	@InjectMocks
	private TimezoneRestController controller;

	/**
	 * <p>
	 * Field <code>mockMvc</code>
	 * </p>
	 */
	private MockMvc mockMvc;

	/**
	 * <p>
	 * Field <code>service</code>
	 * </p>
	 */
	@Mock
	private TimezoneService service;

	/**
	 * <p>
	 * Field <code>springSecurityFilterChain</code>
	 * </p>
	 */
	@Autowired
	private FilterChainProxy springSecurityFilterChain;

}
