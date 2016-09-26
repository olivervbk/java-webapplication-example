package com.github.olivervbk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.olivervbk.ControllerConstants;

/**
 * @author oliver.kuster
 * @version 1.0 Created on 7 de jun de 2016
 */
@Controller
@RequestMapping( value = ControllerConstants.PATH_ROOT )
public class DefaultController
{

	/**
	 */
	public DefaultController()
	{
		super();
	}

	/**
	 * @return Test
	 */
	@RequestMapping( "/" )
	public String home()
	{
		return "redirect:/static";
	}
}
