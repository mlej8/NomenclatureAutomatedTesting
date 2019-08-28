package ca.gc.pch.test.nomenclature.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverFactory {
	
	/**
	 * This class handles the WebDriver binaries (ChromeDriver, Firefox, IE, etc.) versions.
	 * This class implements the Factory pattern which 
	 * It also implements the factory pattern which handles WebDriver instantiation
	 */
	 	
	private static final WebDriverFactory webDriverFactory = new WebDriverFactory();	 
	 
	private WebDriverFactory() {}
	
	public static WebDriverFactory getWebDriverFactory() {
		return webDriverFactory;
	}
	
	public WebDriver getWebDriver(String driverName) {
		if (driverName != null) {
			if(driverName.equalsIgnoreCase("FIREFOX")) {
				WebDriverManager.firefoxdriver().proxy("pchproxy.in.pch.gc.ca").setup();
				return new FirefoxDriver();
			} else if(driverName.equalsIgnoreCase("CHROME")) {
				WebDriverManager.chromedriver().proxy("pchproxy.in.pch.gc.ca").setup();
				return new ChromeDriver();
			} else if(driverName.equalsIgnoreCase("INTERNET EXPLORER")) {
				WebDriverManager.iedriver().proxy("pchproxy.in.pch.gc.ca").setup();
				return new InternetExplorerDriver();
			} else if(driverName.equalsIgnoreCase("EDGE")) {
				WebDriverManager.edgedriver().proxy("pchproxy.in.pch.gc.ca").setup();
				return new EdgeDriver();
			}
		}
		return null;		
	}
}
