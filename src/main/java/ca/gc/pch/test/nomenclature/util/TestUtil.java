package ca.gc.pch.test.nomenclature.util;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import ca.gc.pch.test.nomenclature.base.TestBase;

public class TestUtil extends TestBase {
	public static long PAGE_LOAD_TIMEOUT = 30;
	public static long IMPLICIT_WAIT= 15;
	public static long EXPLICIT_WAIT= 10;
	
	public static String currentTime() {
    // Date and Time
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();
    return dateFormat.format(date);
	}
	
	public static void takeScreenshot() throws IOException { // IOException occurs if an error happens during Input/Output file transfer.
		// Convert web driver object to TakeScreenshot
		TakesScreenshot scrShot = ((TakesScreenshot) driver);
		
		// Call getScreenshotAs method to create image file
		File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
		
		// Temporarily store file to user.dir in property files
		String currentDirectory = System.getProperty("user.dir");
		
		// Copy screenshot file to new file at new destination (currentDirectory/screenshots/) 
		FileUtils.copyFile(srcFile, new File(currentDirectory +"/screenshots/" + System.currentTimeMillis() + ".png"));	
	}
	
}
