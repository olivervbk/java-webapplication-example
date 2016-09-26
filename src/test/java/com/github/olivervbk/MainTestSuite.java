package com.github.olivervbk;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.github.olivervbk.controller.rest.RestControllerTestSuite;

/**
 * @author oliver.kuster
 * @version 1.0 Created on 19 Jun 2016
 */
@RunWith( Suite.class )
@Suite.SuiteClasses( {RestControllerTestSuite.class} )
public class MainTestSuite
{
	// empty
}
