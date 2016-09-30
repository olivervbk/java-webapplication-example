package com.github.olivervbk.it.phantomjs;

import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;

public class WebDriverWrapper {

	private final WebDriver driver;
	
	public WebDriverWrapper(WebDriver driver){
		super();
		this.driver = driver;
	}
	
	public String getLogs(){
		LogEntries logEntries = this.driver.manage()
				.logs()
				.get("browser");
		String log = logEntries.getAll().stream().map(entry -> entry.toString()).collect(Collectors.joining("\n"));
		return log;
	}
	
	public String getPageSource(){
		
		String pageSource = this.driver.getPageSource();
		return pageSource;
	}

}
