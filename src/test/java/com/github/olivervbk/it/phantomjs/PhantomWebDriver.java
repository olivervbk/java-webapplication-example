package com.github.olivervbk.it.phantomjs;

import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

public class PhantomWebDriver {
	public static PhantomJSDriver createPhantomFromMavenPlugin() {
		String mavenPhantomJsBinary = System.getProperty( "phantomjs.binary" );

	    final String phantomJsBinaryPath;
	    if(mavenPhantomJsBinary == null) {
	        final String userDir = System.getProperty("user.dir");
	        //phantomJsBinaryPath = userDir+"/target/phantomjs-maven-plugin/phantomjs.exe";
	        phantomJsBinaryPath = userDir +"/target/phantomjs-maven-plugin/phantomjs-1.9.8-linux-x86_64/bin/phantomjs";
	    }else{
	        phantomJsBinaryPath = mavenPhantomJsBinary;
	    }

		final PhantomJSDriver phantomJSDriver = createPhantomForBinary(phantomJsBinaryPath);
		return phantomJSDriver;
	}

	public static PhantomJSDriver createPhantomForBinary(final String phantomJsBinary) {
		final DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, phantomJsBinary);
		capabilities.setJavascriptEnabled(true);

		// reduce logs somewhat
		final String[] phantomArgs = new String[] { "--webdriver-loglevel=ERROR" };
		capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, phantomArgs);

		final PhantomJSDriver phantomJSDriver = new PhantomJSDriver(capabilities);
		return phantomJSDriver;
	}

}
