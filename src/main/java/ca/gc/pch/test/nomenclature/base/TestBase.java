package ca.gc.pch.test.nomenclature.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import ca.gc.pch.test.nomenclature.config.UserConfiguration;
import ca.gc.pch.test.nomenclature.driver.WebDriverFactory;
import ca.gc.pch.test.nomenclature.util.TestUtil;
import ca.gc.pch.test.nomenclature.util.WebEventListener;

public class TestBase {
	
	/** This class takes care of all the Browser setup/initialization steps
	 *  It defines the common (repeated) properties/methods ONCE which will be used by all pages and test classes i.e. every class in the framework needs a driver, implicit wait, maximize_window(), get(url), etc.
	 *	We use the concept of INHERITANCE to pass all those methods/properties from the BASE CLASS to all the CHILD CLASSES directly.
	 *  All classes in this project extends this class
	 *  AVOIDING DUPLICATE CODE WITH THIS LAYER (i.e. avoid WebDriver driver = new ChromeDriver in EVERY PAGE).
	 *	The same WebDriver, waits, actions objects will be given to BrowsePage/SearchPage, etc (code is reused at its full potential).
	 */
	
	// These global variables can be used in the TestBase class and all its children classes.
	private static final WebDriverFactory webDriverFactory = WebDriverFactory.getWebDriverFactory(); // Only TestBase class has a private instance of WebDriverFactory 
	public static WebDriver driver = null; 
	public static Properties properties = null;
	public static Actions action = null;
	public static WebDriverWait wait = null;
	public static EventFiringWebDriver eventFiringWebDriver = null;
	public static WebEventListener eventListener = null;
	
	public static void initialization() {
		if (properties == null) {
			// read/load my properties file and initialize properties object
			FileInputStream inStream = null;
			try {
				properties = new Properties();			
				inStream = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/ca/gc/pch/test/nomenclature/config/config.properties");
				properties.load(inStream);
			} catch (FileNotFoundException e) {
				System.out.println("Properties File not found.");
			} catch (IOException e) {
				e.printStackTrace();
			} finally { // closing FileInputStream
				if (inStream != null) {
					try {
						inStream.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
			// Setting up Proxy 
		    System.setProperty("http.proxyHost", properties.getProperty("http.proxyHost"));
		    System.setProperty("http.proxyPort",  properties.getProperty("http.proxyPort"));
		    System.setProperty("https.proxyHost", properties.getProperty("https.proxyHost"));
		    System.setProperty("https.proxyPort", properties.getProperty("https.proxyPort"));
		}
		
		if (driver == null) { // only initializing one driver at a time		    
			// Read the properties file and get a driver corresponding to the selected browser
			driver = webDriverFactory.getWebDriver(properties.getProperty("browser")); // create and launching driver
			
			// initialize Event Firing Driver to track every action driver does with a WebEventListener object
			driver = initializeEventFiringDriver(driver);
			
			// Setting up driver properties 
			driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
			driver.manage().deleteAllCookies();
			driver.manage().window().maximize();
			
			// initialize action
			action = new Actions(driver);
			
			// initialize wait
			wait = new WebDriverWait(driver, TestUtil.EXPLICIT_WAIT);
		}
	}
	
	public static WebDriver initializeEventFiringDriver(WebDriver driver) {
		// Create an object of EventFiringWebDriver class
		eventFiringWebDriver = new EventFiringWebDriver(driver);
		
		// Create EventListenerHandler to register it with EventFiringWebDriver
		eventListener = new WebEventListener();		  // object of WebEventListener class
		eventFiringWebDriver.register(eventListener); // register WebEventListener class object with EventFiringWebDriver object 
		return eventFiringWebDriver;				  // return an EventFiringWebDriver
	}
	
	public static void tearDown() {		
		/** Close method that cleans up all the objects instantiated in the "initialization()" method. */
		
		// Close driver object
		if (driver != null) {
			driver.quit();
			driver = null;
		}
		action = null;
		wait = null;
		eventFiringWebDriver = null;
		eventListener = null;
	}

	public static void closeTab() {
		/* Close current tab on browser */
		driver.close();
	}
		
	public static void refresh() {
		/* Method that refreshes the page */
		driver.navigate().refresh();
	}
	
	public static void back() {
		/* Method that navigates back to previous page */
		driver.navigate().back();
	}
}
