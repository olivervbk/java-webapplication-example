package com.github.olivervbk.controller.rest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.github.olivervbk.model.User;
import com.github.olivervbk.service.UserService;
import com.github.olivervbk.spring.mock.MockAuthenticationConfig;
import com.github.olivervbk.spring.security.ApplicationSecurityConfig;

/**
 * @author oliver.kuster
 * @version 1.0 Created on 19 Jun 2016
 */
@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = {	ApplicationSecurityConfig.class,
									MockAuthenticationConfig.class} )
public class UserRestControllerTest
{

	/**
	 *
	 */
	@Before
	public void setUp()
	{
		MockitoAnnotations.initMocks( this );

		this.mockMvc = MockMvcBuilders
			.standaloneSetup( this.controller )
			.alwaysDo( MockMvcResultHandlers.print() )
			.apply( SecurityMockMvcConfigurers.springSecurity( this.springSecurityFilterChain ) )
			.addFilter( this.springSecurityFilterChain, "/**" )
			.build();
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void shouldAllowAdminListUsers()
		throws Exception
	{
		Mockito.when( this.service.list() ).thenReturn( Arrays.asList( new User() ) );

		this.mockMvc.perform( get( "/rest/user" ).with( httpBasic( "admin", "admin" ) ) ).andExpect( status().isOk() );
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void shouldFailToListAsUser()
		throws Exception
	{
		this.mockMvc.perform( get( "/rest/user" ).with( httpBasic( "user", "user" ) ) ).andExpect(
			status().isUnauthorized() );
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void shouldFailToListWhenUnauthenticated()
		throws Exception
	{
		this.mockMvc.perform( get( "/rest/user" ) ).andExpect( status().isUnauthorized() );
	}

	/**
	 * <p>
	 * Field <code>controller</code>
	 * </p>
	 */
	@InjectMocks
	private UserRestController controller;

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
	private UserService service;

	/**
	 * <p>
	 * Field <code>springSecurityFilterChain</code>
	 * </p>
	 */
	@Autowired
	private FilterChainProxy springSecurityFilterChain;

}
