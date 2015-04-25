package com.williamhill;

import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * A Selenium test that checks for Games links and Gamble Aware
 * links on the Home Page 
 */

public class HomePageTest extends TestCase {
	
	private WebDriver webDriver; 
	// This variable allows access to reusable functions to make assertions
	private Utilities util;

	@Before
	public void setUp() {
		webDriver = new FirefoxDriver();
		util = new Utilities(webDriver);
		webDriver.manage().window().maximize();
		webDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}
	
	/**
	 * Navigates to the home page and checks:
	 * 		1. if there are links to Football
	 * 		2. if there are links to Horse Racing
	 * 		3. if there are links to Tennis
	 * 		4. if there are links to Gamble Aware
	 */
	@Test
	public void testHomePage() {
		util.gotoBaseUrl();
		Assert.assertTrue(util.linksToGamePresent("Football") &&
				util.linksToGamePresent("Horse Racing") &&
				util.linksToGamePresent("Tennis") &&
				util.linksToGambleAwarePresent());
	}
	
	@After
	public void tearDown() {
		webDriver.close();
		webDriver.quit();
	}
}
