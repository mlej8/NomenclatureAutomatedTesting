package ca.gc.pch.test.nomenclature.util;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import ca.gc.pch.test.nomenclature.base.TestBase;

public class LinkVerifier extends TestBase {
	
	private boolean bError = false;
	
	public boolean hasBrokenLinks() {
		/**
		 * hasBrokenLinks method will try to establish a HttpURLConnection using the href attribute of every anchor tag. 
		 * If the response code from this connection is not equal than 200, then it will output an error saying this link is broken. 
		 */
		
		Reporter.getInstance().printDriverAction("Verifying every link on current page: " + this.driver.getCurrentUrl());
		String linksXPath = "//a[@href and not(@href=\"\")]";
		ArrayList<String> allLinks = new ArrayList<String>();    // Storing all the links of the Web page in an ArrayList.
	    ArrayList<String> brokenLinks= new ArrayList<String>();
	    String url = "";										 // String to store URL
	    HttpURLConnection huc; 									 // Instantiating HTTP URL Connection
	    int successRespCode = 200; 								 // Response code criteria: indicating whether a specific HTTP request has been successfully completed. 200 means "OK": The request has succeeded. https://developer.mozilla.org/en-US/docs/Web/HTTP/Status	
	    List<WebElement> allAnchor = null;
	    
	    try {
	    allAnchor = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(linksXPath)));
	    } catch (ElementNotVisibleException e) {
	    	Reporter.getInstance().printInfo("Links are not visible.");
	    } catch (TimeoutException e) {
	    	Reporter.getInstance().printInfo("No links have been found.");
	    }
	    
	    for(WebElement anchorTag : allAnchor) {
	    	try {
		    	url = anchorTag.getAttribute("href");	
		    	allLinks.add(url); 	
		    } catch (StaleElementReferenceException e) {
		    	Reporter.getInstance().printError("Couldn't get the @href attribute of an Anchor tag due to StaleElementReferenceException.");
		    	continue;
		    }						           								
	        try { 									
	            huc = (HttpURLConnection)(new URL(url)).openConnection(); // try sending a HTTP request                               
	            huc.connect();                
	            int linkrespCode = huc.getResponseCode();                
	            if(linkrespCode != successRespCode){   
	                brokenLinks.add(anchorTag.getText() + " " + url);
	            }             
	        } 
	        catch(Exception e) {
	        	e.printStackTrace();
	        	brokenLinks.add(anchorTag.getText() + " " + url);
	        }
	    }

	    if(!brokenLinks.isEmpty()) {
	    	Reporter.getInstance().printResult("Broken links (ERROR):");
	    	for(String link: brokenLinks) {
	    		Reporter.getInstance().printResult(link);
	    	}
	    	this.bError = true;
	    	return true;
	    } else {
	    	return false;
	    }
	}

	public boolean hasEmptyAnchorTag() {
		/**
		 * Method that finds if the document contains anchor tag whose href attribute is either empty i.e. \"\" or not configured.
		 */
		if (driver.findElements(By.xpath(("//a[not(href) or @href=\"\"]"))).size() != 0){
			this.bError = true;
			return true;
		}   		
		return false;
	}
	
	
	public boolean getbError() {
		return this.bError;
	}
}
