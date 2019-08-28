package ca.gc.pch.test.nomenclature.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import ca.gc.pch.test.nomenclature.base.TestBase;

public class NomenclatureUtil extends TestBase {
	/** This class contains functions that can be used across all pages of the Nomenclature application */
	
	public static boolean containsWord(String keyword) {
		/**
		 * Method that finds if a word is contained in the HTML page.
		 * xpath: //*[contains(text(), "KEYWORD")]
		 * This xpath finds any tag that contains the KEYWORD in its text.
		 */
		List<WebElement> tagsWithKeyword = driver.findElements(By.xpath("//*[contains(text(),\"" + keyword + "\")]"));
		if (tagsWithKeyword.size() == 0) {
			return false;
		}
		return true;
	}
	
	public static String getLanguage() {
		/**
		 * Method that returns current toggled Language
		 * xpath: //span[text()="(current)"]//parent::li[@class="curr"]
		 * It finds a span tag whose text = "(current)", then it looks for a <li> tag parent that contains the attribute @class = "curr".
		 * It uses RegEx to return the current language.
		 */
		String currentLanguage;
		try {
			currentLanguage = driver.findElement(By.xpath("//span[text()=\"(current)\"]//parent::li[@class=\"curr\"]")).getText();
		} catch (NoSuchElementException e) {
			currentLanguage = "PROGRAM ERROR (language not found)";
			return currentLanguage;
		}
		
		// Clean up string using RegEx
		Pattern languageRegex = Pattern.compile("(.+\\S)");
		Matcher myLanguage = languageRegex.matcher(currentLanguage);			
		if(myLanguage.find()) {
			currentLanguage = myLanguage.group(0);
		}		
		return currentLanguage;
	}
}
