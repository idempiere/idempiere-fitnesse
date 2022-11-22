package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.idempiere.ui.zk.selenium.Widget;
import org.idempiere.ui.zk.selenium.Zk;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

public class AbstractTestCase {

	protected WebDriver driver;
	protected StringBuffer verificationErrors = new StringBuffer();
	private String baseUrl;
	protected Actions actions;

	@Before
	public void setUp() throws Exception {
		
	    String userDir = System.getProperty("user.dir");
		System.setProperty("webdriver.gecko.driver", userDir + "/../org.idempiere.fitnesse.server/resources/geckodriver");

		driver = new FirefoxDriver();
		actions = new Actions(driver);
//		driver = new ChromeDriver();
		baseUrl = "https://localhost:8080/webui/";
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().window().setSize(new Dimension(1920, 1080));
	}
	
	protected void type(WebElement element, String value, Boolean sendEnter) {
		element.click();
		actions.sendKeys(value);
		if (sendEnter) {
			actions.sendKeys(Keys.ENTER);
		}
		actions.perform();
	}

	protected void setReadOnlyTextBox(String locator, String value) {
		WebElement element = driver.findElement(Zk.jq(locator));
		element.click();
		WebElement listBox = driver.findElement(Zk.jq("$listSelectBox @rows @row @select"));
		listBox.isDisplayed();
		listBox.click();

	}

	protected void type(String locator, String value, Boolean sendEnter) {
		WebElement element = driver.findElement(Zk.jq(locator));
		type(element, value, sendEnter);
	}
	
	protected void comboboxSelectItem(String locator, String label) {
		Widget widget = new Widget(locator);
		WebElement element = widget.$n(driver, "real");
		element.click();

		do {
			actions.sendKeys(Keys.BACK_SPACE).perform();
			actions.sendKeys(Keys.DELETE).perform();
		} while (element.getAttribute("value").length() > 0);

		waitResponse();
		type(element, label, true);
		waitResponse();
	}
	
	protected void search(String locator, String label) {
		Widget widget = new Widget(locator + " @combobox");
		WebElement element = widget.findElement(driver);
		type(element, label, true);
		waitResponse();
	}

	protected void comboboxSetText(String locator, String text) {
		Widget widget = new Widget(locator);
		widget.execute(driver, "setValue('" + text + "', true)");
		widget.execute(driver, "fireOnChange()");
		WebElement element = widget.$n(driver, "real");
		element.click();
		waitResponse();

	}
	
	
	protected void selectCheckbox(String locator, boolean select) {
		StringBuilder selector = new StringBuilder();
		selector.append(locator.startsWith("$") ? "" : "$").append(locator).append("~ input");
		final WebElement element = driver.findElement(Zk.jq(selector.toString()));
		if (element.isSelected()) {
			if (!select) {
				element.click();
			}
		} else {
			if (select) {
				element.click();
			}
		}
	}
	
	protected void clickButton(String locator) {
		driver.findElement(Zk.jq(locator)).click();
	}
	
	protected void clearElement(String locator) {
		driver.findElement(Zk.jq(locator)).clear();
	}

	public void closeWindow(String $label) {
		Widget widget = new Widget("$desktop_tabbox @tabs @tab[label=\"" + $label + "\"]");
		widget.$n(driver, "cnt").click();

	}

	protected void selectTab(String locator, int index) {
		Widget widget = new Widget(locator);
		WebElement element = (WebElement) widget.eval(driver, "getTabs().getChildAt("+index+").$n('cnt')");
		element.click();
	}
	
	protected void selectTab(String locator, String label) {
		Widget widget = new Widget(locator + " @tab[label=\""+label+"\"]");
		widget.$n(driver, "cnt").click();
	}
	
	protected String selectedTab(String locator) {
		Widget widget = new Widget(locator);
		return (String) widget.eval(driver, "getSelectedTab().getLabel()");
	}
	
	/**
	 * Waits for Ajax response with default timeout value.
	 */
	protected void waitResponse() {
		waitResponse(5000);
	}
	
	/**
	 * Waits for Ajax response according to the timeout attribute.
	 * @param timeout
	 * 
	 */
	protected void waitResponse(int timeout) {
		long s = System.currentTimeMillis();
		int i = 0;
		int ms = 500;
		
		String script = "!!zAu.processing() || !!jq.timers.length";
		while (i < 2) { // make sure the command is triggered.
			while(Boolean.valueOf(getEval(script))) {
				if (System.currentTimeMillis() - s > timeout) {
					break;
				}
				i = 0;//reset
				sleep(ms);
			}
			i++;
			sleep(ms);
		}
	}
	
	public String getEval(String script) {
		return String.valueOf(((JavascriptExecutor) driver).executeScript("return ("+ script+");"));
	}
	
	/**
     * Causes the currently executing thread to sleep for the specified number
     * of milliseconds, subject to the precision and accuracy of system timers
     * and schedulers. The thread does not lose ownership of any monitors.
     * @param millis the length of time to sleep in milliseconds.
     */
	protected void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}
	
	protected void login() throws Exception {
		login(false);
	}

	protected void login(boolean isSystem) throws Exception {
		driver.get(baseUrl);
		
		String userName = "GardenAdmin";
		String password = "GardenAdmin";
		if (isSystem) {
			userName = "System";
			password = "System";
		}
		
		waitResponse();

		// enter user name
		type("$loginPanel $txtUserId", userName, false);

		// enter password
		type("$loginPanel $txtPassword", password, false);
		
		//select language
		comboboxSelectItem("$loginPanel $lstLanguage", "English");

		// check select role
		selectCheckbox("$loginPanel $chkSelectRole", true);		
		// click ok button
		clickButton("$loginPanel $Ok");		
		
		if (isSystem)
			selectRole("System", "System Administrator", "*", "");
		else
			selectRole("GardenWorld", "GardenWorld Admin", "HQ", "HQ Warehouse");

		// wait for home page
		WebElement loginUserElement = waitForElement("$loginUserAndRole");

		// assert login user and role
		if (isSystem)
			assertEquals("System@System.*/System Administrator", loginUserElement.getText());
		else
			assertEquals("GardenAdmin@GardenWorld.HQ/GardenWorld Admin", loginUserElement.getText());
	}

	protected WebElement waitForElement(String locator) throws InterruptedException {
		By loginUserQuery = Zk.jq(locator);
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (isElementPresent(loginUserQuery))
					break;
			} catch (Exception e) {
			}
			sleep(1000);
		}
		return driver.findElement(loginUserQuery);
	}

	protected void selectRole(String client, String role, String org, String warehouse) throws InterruptedException {
		// wait for role panel
		WebElement lstClient = waitForElement("$rolePanel $lstClient");
		
		// select client
		if (lstClient != null && lstClient.isDisplayed() && !lstClient.getAttribute("class").contains("z-combobox-disabled")) {
			comboboxSelectItem("$rolePanel $lstClient", client);
		}

		// select role
		WebElement lstRole = waitForElement("$rolePanel $lstRole");
		if (lstRole != null && lstRole.isDisplayed() && !lstRole.getAttribute("class").contains("z-combobox-disabled")) {
			comboboxSelectItem("$rolePanel $lstRole", role);
		}
		
		// select organization
		WebElement lstOrg = waitForElement("$rolePanel $lstOrganisation");
		if (lstOrg != null && lstOrg.isDisplayed() && !lstOrg.getAttribute("class").contains("z-combobox-disabled")) {
			comboboxSelectItem("$rolePanel $lstOrganisation", org);
		}

		if (warehouse != null && !warehouse.isBlank())
			comboboxSelectItem("$rolePanel $lstWarehouse", warehouse);

		// click ok button
		clickButton("$rolePanel $Ok");
	}
	
	protected boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	protected void openWindow(String label) {
		comboboxSelectItem("$globalSearchBox", label);
		actions.sendKeys(Keys.ENTER).perform();
		waitResponse(1000);
	}

	protected void logout() {
		WebElement logout = driver.findElement(Zk.jq("$logout"));
		logout.click();
	}

	protected void clickProcessButton(String windowId, String btnId) {
		clickButton("$"+windowId + " $windowToolbar $BtnProcess");		
		waitResponse();
		clickButton("@window[instanceName=\"processButtonPopup\"] $" + btnId);
	}
	
	protected void clickToolbarButton(String windowId, String toolBarButtonId) {
		clickButton("$" + windowId + " $windowToolbar $" + toolBarButtonId);
	}
	
	protected void clickDetailToolbarButton(String windowId, String toolBarButtonId) {
		clickButton("$" + windowId + " $detailPane $" + toolBarButtonId + ":visible");
	}
	
	protected WebElement getWindowMessageLabel(String windowId) {
		return driver.findElement(Zk.jq("$"+windowId +" $messages @label"));
	}
	
	protected void nextRecord(String windowId) {
		clickButton("$"+windowId+" $breadCrumb $Next");
	}
	
	protected void previousRecord(String windowId) {
		clickButton("$"+windowId+" $breadCrumb $Previous");
	}
	
	@After
	public void tearDown() throws Exception {
		//driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	public File takeScreenshot() {
		File prt = null;
		
		waitResponse(2000);
		prt = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			
		return prt;
	}

}
