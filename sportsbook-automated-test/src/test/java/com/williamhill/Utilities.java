package com.williamhill;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * A set of utility functions that can  be reused by multiple test cases
 * for their assertions.
 */
public class Utilities {
	
	private WebDriver webDriver;
	// URL of the home page
	private final static String BASE_URL = "http://sports.williamhill.com";
	
	public Utilities (WebDriver webDriver) {
		this.webDriver = webDriver;
	}
	
	/**
	 * Returns true if there are links to Gamble Aware
	 */
	public boolean linksToGambleAwarePresent() {
		if (webDriver.findElements(By.cssSelector("a[href*='gambleaware.co.uk']")) == null)
			return false;
		else
			return true;
	}
	
	/**
	 * Returns true if there are links to a game
	 * 
	 * * @param gameName
	 *            The name of the game for which links are to be searched for.
	 */
	public boolean linksToGamePresent(String gameName) {
		if (webDriver.findElements(By.partialLinkText(gameName)) == null)
				return false;
		else
			return true;
	}
	
	/**
	 * Returns true if there is an empty Bet Slip
	 */
	public boolean emptyBetSlipAppears() {
		if (webDriver.findElement(By.xpath(".//*[@id='slipEmpty']/div[text()='Your bet slip is currently empty']")).isDisplayed())
			return true;
		else
			return false;
	}
	
	/**
	 * Returns true if the page title contains text
	 * 
	 * * @param titleText
	 *            The text that is to be looked for in the page title.
	 */
	public boolean pageTitleContains(String titleText) {
		if (webDriver.getTitle().contains(titleText))
			return true;
		else
			return false;
	}
	
	/**
	 * Navigates to the home page and clicks "Yes" on the modal dialog for the Date/Time 
	 * zone selection
	 */
	public void gotoBaseUrl() {
		webDriver.navigate().to(BASE_URL);
		List<WebElement> popups = webDriver.findElements(By.cssSelector("#modalPopup2:not([style*='none'])"));
		if (!popups.isEmpty()) {
			webDriver.findElement(By.id("yesBtn")).click();
		}
	}
}
