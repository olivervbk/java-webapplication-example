package com.github.olivervbk.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * @author oliver.kuster
 * @version 1.0 Created on 19 Jun 2016
 */
@Configuration
@EnableGlobalMethodSecurity( prePostEnabled = true, proxyTargetClass = true, securedEnabled = true )
class MethodSecurityConfig
	extends GlobalMethodSecurityConfiguration
{

	/**
	 * @return
	 * @see org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration#createExpressionHandler()
	 */
	@Override
	protected MethodSecurityExpressionHandler createExpressionHandler()
	{
		final DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
		expressionHandler.setPermissionEvaluator( this.permissionEvaluator );
		return expressionHandler;
	}

	/**
	 * <p>
	 * Field <code>permissionEvaluator</code>
	 * </p>
	 */
	@Autowired
	private PermissionEvaluator permissionEvaluator;
}
