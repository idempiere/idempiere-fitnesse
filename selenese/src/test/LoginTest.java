package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;

/**
 * Generated from selenium ide, required AdempiereIdGenerator
 * @author hengsin
 *
 */
public class LoginTest {
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();
	protected Actions actions;

	@Before
	public void setUp() throws Exception {

	    String userDir = System.getProperty("user.dir");
		System.setProperty("webdriver.gecko.driver", userDir + "/../org.idempiere.fitnesse.server/resources/geckodriver");

	    var options = new FirefoxOptions();
		// options.setHeadless(true);
		options.setCapability("marionette", true);
		driver = new FirefoxDriver(options);
		actions = new Actions(driver);
		baseUrl = "http://127.0.0.1:6002/";
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test
	public void testLogin() throws Exception {

		// open | /webui/ | 
		driver.get(baseUrl + "webui/");
		waitResponse(5000);

		// borderlayout_center_box_window_loginPanel_txtUserId
		// type | id=borderlayout_center_box_window_loginPanel_txtUserId | GardenAdmin
		driver.findElement(By.id("borderlayout_center_box_window_loginPanel_txtUserId")).clear();
		driver.findElement(By.id("borderlayout_center_box_window_loginPanel_txtUserId")).sendKeys("GardenAdmin");
		
		// fireEvent | id=loginPanel_grdLogin_rowUser_txtUserId | blur
		// not needed for webdriver
		// type | id=borderlayout_center_box_window_loginPanel_txtPassword | GardenAdmin
		driver.findElement(By.id("borderlayout_center_box_window_loginPanel_txtPassword")).clear();
		driver.findElement(By.id("borderlayout_center_box_window_loginPanel_txtPassword")).sendKeys("GardenAdmin");
		// fireEvent | id=loginPanel_grdLogin_rowPassword_txtPassword | blur
		// not needed for webdriver
		// click | id=borderlayout_center_box_window_loginPanel_chkSelectRole-real | 
		driver.findElement(By.id("borderlayout_center_box_window_loginPanel_chkSelectRole-real")).click();
		// click | borderlayout_center_box_window_loginPanel_Ok | 10
		driver.findElement(By.id("borderlayout_center_box_window_loginPanel_Ok")).click();
		// waitForElementPresent | id=borderlayout_center_box_window_rolePanel_lstClient-btn | 
		for (int second = 0;; second++) {
			if (second >= 60) fail("timeout");
			try { if (isElementPresent(By.id("borderlayout_center_box_window_rolePanel_lstClient-btn"))) break; } catch (Exception e) {}
			Thread.sleep(1000);
		}

		WebElement lstClient = driver.findElement(By.id("borderlayout_center_box_window_rolePanel_lstClient"));
		boolean lstClientEnable = Arrays.asList(lstClient.getAttribute("class").split(" ")).contains("z-combobox-disabled");
		if (lstClient != null && lstClient.isDisplayed() && !lstClientEnable) {
			// click | id=borderlayout_center_box_window_rolePanel_lstClient-btn | 
			driver.findElement(By.id("borderlayout_center_box_window_rolePanel_lstClient-icon")).click();
			// click | id=borderlayout_center_box_window_rolePanel_lstClient_GardenWorld | 
			driver.findElement(By.id("borderlayout_center_box_window_rolePanel_lstClient_GardenWorld")).click();
			Thread.sleep(1000);
		}
		// click | id=rolePanel_grdChooseRole_rowRole_lstRole-btn | 
		driver.findElement(By.id("borderlayout_center_box_window_rolePanel_lstRole-icon")).click();
		// click | id=borderlayout_center_box_window_rolePanel_lstRole_GardenWorld_Admin | 
		driver.findElement(By.id("borderlayout_center_box_window_rolePanel_lstRole_GardenWorld_Admin")).click();
		Thread.sleep(1000);
		// click | id=borderlayout_center_box_window_rolePanel_lstOrganisation-btn | 
		driver.findElement(By.id("borderlayout_center_box_window_rolePanel_lstOrganisation-btn")).click();
		// click | css=#borderlayout_center_box_window_rolePanel_lstOrganisation_HQ > td.z-comboitem-text | 
		driver.findElement(By.id("borderlayout_center_box_window_rolePanel_lstOrganisation_HQ")).click();
		// click | rolePanel_Ok | 
		driver.findElement(By.id("borderlayout_center_box_window_rolePanel_Ok")).click();
		// waitForElementPresent | div_borderlayout_north_include_div_box_box_1_box_label | 
		for (int second = 0;; second++) {
			if (second >= 60) fail("timeout");
			try { if (isElementPresent(By.id("div_borderlayout_north_include_div_box_box_1_box_label"))) break; } catch (Exception e) {}
			Thread.sleep(1000);
		}
		// assertText | div_borderlayout_north_include_div_box_box_1_box_label | GardenAdmin@GardenWorld.HQ/GardenWorld Admin
		assertEquals("GardenAdmin@GardenWorld.HQ/GardenWorld Admin", driver.findElement(By.id("div_borderlayout_north_include_div_box_box_1_box_label")).getText());
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	protected void waitResponse(int timeout) {
		long s = System.currentTimeMillis();
		int i = 0;
		int ms = 500;

		String script = "!!zAu.processing() || !!jq.timers.length";
		while (i < 2) { // make sure the command is triggered.
			while (Boolean.valueOf(getEval(script))) {
				if (System.currentTimeMillis() - s > timeout) {
					break;
				}
				i = 0;// reset
				sleep(ms);
			}
			i++;
			sleep(ms);
		}
	}
	

	/**
	 * Causes the currently executing thread to sleep for the specified number of
	 * milliseconds, subject to the precision and accuracy of system timers and
	 * schedulers. The thread does not lose ownership of any monitors.
	 *
	 * @param millis the length of time to sleep in milliseconds.
	 */
	protected void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}
	
	public String getEval(String script) {
		return String.valueOf(((JavascriptExecutor) driver).executeScript("return (" + script + ");"));
	}
}
