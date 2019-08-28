package ca.gc.pch.test.nomenclature.TestNGBasics;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestNGBasics {
	
	/*
	@BeforeSuite -- Setup system property for Chrome -- I
	@BeforeTest -- Launch Chrome Browser -- II
	@BeforeClass -- Login method -- III
	
	@BeforeMethod -- Enter URL
	@Test -- Google Title Test
	@AfterMethod -- Log out from app
	
	@BeforeMethod -- Enter URL
	@Test -- Search Google Test
	@AfterMethod -- Log out from app
	
	@AfterClass -- Close Browser
	@AfterTest -- Delete all cookies 
	@AfterSuite -- Generate Test Report */
		
	
	// Part 1: Pre-conditions annotations -- starting with @Before
	@BeforeSuite //1
	public void setUp() {
		System.out.println("@BeforeSuite -- Setup system property for Chrome");
	}
	
	@BeforeTest //2
	public void launchBrowser() {
		System.out.println("@BeforeTest -- Launch Chrome Browser");
	}
	
	@BeforeClass //3
	public void login() {
		System.out.println("@BeforeClass -- Login method");
	}
	
	@BeforeMethod //4
	public void enterURL() {
	System.out.println("@BeforeMethod -- Enter URL");	
	}

	
	// Part 2: Test Cases -- starting with @Test
	@Test(priority = 1) //5
	public void googleTitleTest() {
		/**
		 * Test that verifies the Title of google
		 */
		System.out.println("@Test -- Google Title Test");
	}
	
	@Test(priority = 2)
	public void searchTest() {
		System.out.println("@Test -- Search Google Test");
	}
	
	// Part 3: Post-conditions -- starting with @After
	@AfterMethod //6
	public void logOut() {
		System.out.println("@AfterMethod -- Log out from app");
	}
	
	@AfterClass //7
	public void closeBrowser() {
		System.out.println("@AfterClass -- Close Browser");
	}
	
	@AfterTest //8 
	public void deleteAllCookies() {
		System.out.println("@AfterTest -- Delete all cookies");
	}
		
	@AfterSuite //9
	public void generateTestReport() {
		System.out.println("@AfterSuite -- Generate Test Report");
	}
}
