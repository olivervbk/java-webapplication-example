package com.github.olivervbk.it;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntries;

import com.github.olivervbk.it.phantomjs.PhantomWebDriver;

public class PhantomJsIT {

	private static final Log LOG = LogFactory.getLog(PhantomJsIT.class);

	private static WebDriver driver;

	@BeforeClass
	public static void beforeClass() {
		driver = PhantomWebDriver.createPhantomFromMavenPlugin();
	}

	@AfterClass
	public static void afterClass() {
		driver.quit();
		driver = null;
	}

	private static String getBaseUrl() {
		final String reservedHttpPort = System.getProperty("http.port");
		final String httpPort = reservedHttpPort != null ? reservedHttpPort : "8080";
		final String baseURL = String.format("http://localhost:%s/", httpPort);
		return baseURL;
	}

	@Test
	public void testLoginScreen() throws InterruptedException {

		String baseUrl = getBaseUrl();
		if(LOG.isInfoEnabled()){
			LOG.info(String.format("Navigating to: %s", baseUrl));
		}
		
		final Navigation navigate = driver.navigate();
		navigate.to(baseUrl);
	

		final List<WebElement> loginElements = driver.findElements(By.id("username"));
		Assert.assertFalse(loginElements.isEmpty());
	}

}
