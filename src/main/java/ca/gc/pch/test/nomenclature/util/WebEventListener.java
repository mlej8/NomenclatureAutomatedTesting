package ca.gc.pch.test.nomenclature.util;

import java.io.IOException;

/*************************************** PURPOSE **********************************
- This class implements the WebDriverEventListener, which is included under events.
  The purpose of implementing this interface is to override all the methods and define useful Log statements 
  which would be displayed/logged as the application under test is being run.
- Do not call any of these methods, instead these methods will be invoked automatically when the action is done (click, findBy etc). 
*/

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

import ca.gc.pch.test.nomenclature.base.TestBase;

public class WebEventListener extends TestBase implements WebDriverEventListener {

	public void beforeNavigateTo(String url, WebDriver driver) {
		Reporter.getInstance().printDriverAction("Navigating to: \"" + url + "\"");
	}

	public void afterNavigateTo(String url, WebDriver driver) {
		Reporter.getInstance().printDriverAction("Successfully navigated to: \"" + url + "\"");
	}

	public void beforeChangeValueOf(WebElement element, WebDriver driver) {
		Reporter.getInstance().printDriverAction("Value of the:" + element.toString() + " before any changes made");
	}

	public void afterChangeValueOf(WebElement element, WebDriver driver) {
		Reporter.getInstance().printDriverAction("Element value changed to: " + element.toString());
	}

	public void beforeClickOn(WebElement element, WebDriver driver) {
		Reporter.getInstance().printDriverAction("Trying to click on: " + element.toString());
	}

	public void afterClickOn(WebElement element, WebDriver driver) {
		Reporter.getInstance().printDriverAction("Clicked on: " + element.toString());
	}

	public void beforeNavigateBack(WebDriver driver) {
		Reporter.getInstance().printDriverAction("Navigating back to previous page");
	}

	public void afterNavigateBack(WebDriver driver) {
		Reporter.getInstance().printDriverAction("Navigated back to previous page");
	}

	public void beforeNavigateForward(WebDriver driver) {
		Reporter.getInstance().printDriverAction("Navigating forward to next page");
	}

	public void afterNavigateForward(WebDriver driver) {
		Reporter.getInstance().printDriverAction("Navigated forward to next page");
	}

	public void onException(Throwable error, WebDriver driver) { 
		/**
		 * Whenever ANY exceptions (NoSuchElementException, TimeoutException, StaleElement, etc.) occur during runtime, this method will be called.
		 * The listener will catch this exception and print the error and take a screenshot
		 */
		Reporter.getInstance().printDriverAction("Exception occured: " + error.getMessage().split("\n")[0]); 		 // Print error on the console
		
		// Capture a screenshot whenever any exception occurs.
		try {
			TestUtil.takeScreenshot();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void beforeFindBy(By by, WebElement element, WebDriver driver) {
		Reporter.getInstance().printDriverAction("Trying to find Element(s) By: " + by.toString());
	}

	public void afterFindBy(By by, WebElement element, WebDriver driver) {
		Reporter.getInstance().printDriverAction("Found Element(s) By: " + by.toString());
	}

	/*
	 * not overridden methods of WebListener class
	 */
	
	public void beforeScript(String script, WebDriver driver) {
		// TODO Auto-generated method stub
	}

	public void afterScript(String script, WebDriver driver) {
		// TODO Auto-generated method stub
	}

	public void beforeAlertAccept(WebDriver driver) {
		// TODO Auto-generated method stub
	}

	public void afterAlertAccept(WebDriver driver) {
		// TODO Auto-generated method stub
	}

	public void afterAlertDismiss(WebDriver driver) {
		// TODO Auto-generated method stub
	}

	public void beforeAlertDismiss(WebDriver driver) {
		// TODO Auto-generated method stub
	}

	public void beforeNavigateRefresh(WebDriver driver) {
		// TODO Auto-generated method stub
	}

	public void afterNavigateRefresh(WebDriver driver) {
		// TODO Auto-generated method stub
	}

	public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		// TODO Auto-generated method stub
	}

	public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		// TODO Auto-generated method stub
	}

	public <X> void afterGetScreenshotAs(OutputType<X> arg0, X arg1) {
		// TODO Auto-generated method stub		
	}

	public void afterGetText(WebElement arg0, WebDriver arg1, String arg2) {
		// TODO Auto-generated method stub		
	}

	public void afterSwitchToWindow(String arg0, WebDriver arg1) {
		// TODO Auto-generated method stub		
	}

	public <X> void beforeGetScreenshotAs(OutputType<X> arg0) {
		// TODO Auto-generated method stub		
	}

	public void beforeGetText(WebElement arg0, WebDriver arg1) {
		// TODO Auto-generated method stub		
	}

	public void beforeSwitchToWindow(String arg0, WebDriver arg1) {
		// TODO Auto-generated method stub		
	}

}
