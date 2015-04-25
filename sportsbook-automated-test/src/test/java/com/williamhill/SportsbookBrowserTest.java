package com.williamhill;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * A data driven Selenium test that navigates to a sport and then an 
 * event under that sport. Test data - namely sport and event - are
 * read from DATA_FILE and the test is executed for each record in 
 * the file.
 */

@RunWith(value=Parameterized.class)
public class SportsbookBrowserTest extends TestCase {
	
	private WebDriver webDriver;
	private String sport;
	private String event;
	// This variable allows access to reusable functions to make assertions
	private Utilities util;
	// File that contains the test records
	private static final String DATA_FILE = "./databank.csv";
	 
	public SportsbookBrowserTest(String sport, String event) {
		this.sport = sport;
		this.event = event;
	}
	
	@Before
	public void setUp() {
		webDriver = new FirefoxDriver();
		util = new Utilities(webDriver);
		webDriver.manage().window().maximize();
		webDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	/**
	 * Reads data from DATA_FILE and returns the test data as a
	 * collection of object arrays
	 */
	@Parameters
	public static Collection<Object[]> data() throws IOException {
		Collection<Object[]> testData = new ArrayList<Object[]> ();
		FileInputStream fis = new FileInputStream(new File(DATA_FILE));
		CSVReader reader = new CSVReader(new InputStreamReader(fis, "UTF-8"));
		reader.readHeaders();
		while (reader.readRecord()) {
			String sport = reader.get("SPORT");
			String event = reader.get("EVENT");
			testData.add(new Object[] {sport, event});
		}
		reader.close();
		fis.close();
		return testData;
	}
	
	/**
	 * Navigates to a sport and tests:
	 * 		1. if an empty bet slip appears
	 * 		2. if link to Gamble Aware are present
	 * 		3. if page title contains the sport name
	 * And then navigates to an event under the sport and tests:
	 * 		1. if an empty bet slip appears
	 * 		2. if link to Gamble Aware are present
	 * 		3. if page title contains the event name	 
	 */
	@Test
	public void testNavigateToEvent() {
		util.gotoBaseUrl();		
		webDriver.findElement(By.linkText(sport)).click();
		Assert.assertTrue(util.emptyBetSlipAppears() &&
				util.linksToGambleAwarePresent() &&
				util.pageTitleContains(sport));

		webDriver.findElement(By.linkText(event)).click();
		Assert.assertTrue(util.emptyBetSlipAppears() &&
				util.linksToGambleAwarePresent() &&
				util.pageTitleContains(event.replaceAll(" +", " ")));
	}
	
	@After
	public void tearDown() {
		webDriver.close();
		webDriver.quit();
	}
}
