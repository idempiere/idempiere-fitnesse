package test;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.openqa.selenium.io.FileHandler;

/**
 * Web Driver + zk jq selector, doesn't required AdempiereIdGenerator
 * @author muriloht
 *
 */
public class TakeScreenshotTest extends AbstractTestCase {
	@Test
	public void testTakeScreenshot() throws Exception {
		login();			
		waitResponse();
		openWindow("Product");
		waitResponse();
		clickButton("$findWindow_1 $simpleSearch $btnOk");
		waitResponse();
		File screenshot = takeScreenshot();
		File file = null; 
		if (screenshot != null) {

			try {
				file = new File(System.getProperty("user.home") + "/Product.png");
				FileHandler.copy(screenshot, file);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		assertNotNull(file);
	}
}
