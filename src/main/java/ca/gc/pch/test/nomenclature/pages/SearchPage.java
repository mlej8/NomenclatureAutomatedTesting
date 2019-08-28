package ca.gc.pch.test.nomenclature.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import ca.gc.pch.test.nomenclature.base.TestBase;
import ca.gc.pch.test.nomenclature.util.NomenclatureUtil;
import ca.gc.pch.test.nomenclature.util.Reporter;
import ca.gc.pch.test.nomenclature.util.StringProcessor;

public class SearchPage extends Page {
	
	private final String pageID = "recherche-search.app";
	
	/* Page Factory - OR (Object Repository)
	 * These WebElements will be initialized by calling the initElements method when the Page Object is created
	 * Every time we call a method on the WebElement, the driver will go and find it on the current page again. In an AJAX-heavy application this is what you would like to happen
	 * WebElements are evaluated lazily. That is, if you never use a WebElement field in a PageObject, there will never be a call to "findElement" for it.
	 * @FindBys : When the required WebElement objects need to match all of the given criteria use @FindBys annotation
	 * @FindAll : When required WebElement objects need to match at least one of the given criteria use @FindAll annotation. Use FindAll annotation to get series of @FindBy tags and search for all elements that match any of the FindBy criteria.	
	 */	
	
	@FindBy(id="searchForm-searchInput") 
	WebElement searchInputBox;	
	@FindBy(xpath="//input[@id=\"searchForm-searchSubmit\"]")
	WebElement searchBtn;
	@FindBy(xpath="//ul[contains(@id,\"ui-id\")]/li[@class=\"ui-menu-item\"]/div[@class=\"ui-menu-item-wrapper\"]")
	List<WebElement> suggestions;	
	@FindBy(xpath="//input[@id=\"searchForm-searchClear\"]")
	WebElement clearBtn;
	@FindBy(xpath="//table[@id=\"summary\"]/tbody//descendant::tr")
	List<WebElement> searchResultsDisplayed;
	@FindBy(xpath="//form[contains(@id,\"datatable-control-form\")]/span[1 and @class=\"mrgn-lft-sm\"]")
	WebElement numberOfEntries;
	@FindBy(xpath="//table[@id=\"summary\"]/tbody/tr/td/a")
	List<WebElement> resultLinks;
	@FindBy(xpath="//table[@id=\"summary\"]/tbody//descendant::tr/td[1]/a")
	List<WebElement> preferredTerms;
	@FindBy(xpath="//input[@type=\"submit\" and contains(@name,\"preferenceForm\")]") // TODO change xpath
	WebElement changePreferencesBtn;	
	@FindBy(xpath="//input[@checked=\"checked\" and contains(@id, \"preferenceForm-languagePref\")]//following::label[1]")
	WebElement linguisticVariant;
	@FindBy(xpath="//input[@checked=\"checked\" and contains(@id, \"preferenceForm-wordPref\")]//following::label[1]")
	WebElement termOrder;
	
	public SearchPage(){
		// Initialization 
		initialization();
		// Let user choose testing environment and run mode
	    initializeUserConfigurations();
	    this.url = this.userConfigurations.getTestEnv() + properties.getProperty("app.domain.name") + pageID;
		driver.get(this.url);
		PageFactory.initElements(driver, this); // initialize the WebElements using PageFatory
	}	
	
	public String getTermOrder() {
		/**
		 *  Method to verify what is the selected term order
		 *  This method finds an input tag that is has checked attribute = "checked" AND whose id contains "j_idt59-preferenceForm-wordPref" (without I or N at the end to be able to find both Inverted/Natural)
		 *  Then, it finds the label next to it to identify which label is toggled.
		 */
		String[] termOrders = {"Inverted","Natural","Inversé","Naturel"}; // array of possible Term Orders, used for clearing string using Regex.		
		String tOrder;
		
		try {
			tOrder = termOrder.getText();
		} catch (NoSuchElementException e){
			this.bError = true;
			tOrder = "PROGRAM ERROR (term order not found)";
			return tOrder;
		}		
		// Clean up string from "&nbsp;&nbsp; Inverted    (p. ex.)" using RegEx
		String regex =  String.join("|", termOrders); 
		Pattern pattern = Pattern.compile(regex);
		Matcher myMatcher = pattern.matcher(tOrder);		
		if(myMatcher.find()) {
			tOrder = myMatcher.group(0);
		}		
		return tOrder;		
	}
	
	
	public void changeTermOrder(String preference) {
		/**
		 * Method that changes the term order preference in Browse hierarchy
		 * Takes as argument Natural, Naturel, Inversé, Inverted
		 */
		preference = StringProcessor.capitalizeString(preference);
		HashMap<String, String> preferences = new HashMap<String,String>();
		preferences.put("Natural", "N");
		preferences.put("Naturel", "N");
		preferences.put("Inversé", "I");
		preferences.put("Inverted", "I");
		String xpath = "//div[@id=\"preferenceInputs\"]/input[contains(@id,\"preferenceForm-wordPref" + preferences.get(preference) + "\")]";
		try {
			WebElement termOrder = driver.findElement(By.xpath(xpath));
			this.action.moveToElement(termOrder).click().perform();
		} catch (NoSuchElementException e) {
			this.bError = true;
			Reporter.getInstance().printError("Coudn't change to " + preference + ", the current selected term order is " + getTermOrder());
		}
	}
	
	public String getLinguisticVariant() {		
		/**
		 *  Method to get the current selected linguistic variant
		 *  xpath: //input[@checked="checked" and contains(@id, "preferenceForm-languagePref")]//following::label[1]
		 */		
		String[] linguisticVariants = {"International","Canadian","Canadien"}; // array of possible Linguistic Variants, used for clearing string using Regex.	
		String lingVariant = "";
		
		try {
			lingVariant = linguisticVariant.getText();
		} catch(NoSuchElementException e) {
			this.bError = true;
			lingVariant = "PROGRAM ERROR (linguistic Variant not found)";
			return lingVariant;
		}		
		
		// Clean up String from indentations and unwanted characters
		String regex = String.join("|", linguisticVariants);
		Pattern pattern = Pattern.compile(regex);
		Matcher myMatcher = pattern.matcher(lingVariant);
		
		if(myMatcher.find()) {
			lingVariant = myMatcher.group();
		}
		return lingVariant;		
	}
	
	public void changeLinguisticVariant(String preference) {
		/**
		 * Method that changes the linguistic variant preference in Search page
		 * Takes as argument International, Canadian or Canadien
		 */
		preference = StringProcessor.capitalizeString(preference);
		HashMap<String, String> preferences = new HashMap<String,String>();
		preferences.put("Canadian", "CA");
		preferences.put("Canadien", "CA");
		preferences.put("International", "INT");
		String xpath = "//div[@id=\"spellingInputs\"]/input[contains(@id,\"preferenceForm-languagePref" + preferences.get(preference) + "\")]";		
		try {
			WebElement termOrder = this.driver.findElement(By.xpath(xpath));
			this.action.moveToElement(termOrder).click().perform();
		} catch (NoSuchElementException e) {
			this.bError = true;
			Reporter.getInstance().printError("Coudn't change to " + preference + ", the current linguistic variant is " + getLinguisticVariant());
		}
	
	}
	
	public void performChangePreferences() {
		/**
		 * Method that triggers the "change preferences" button
		 */
		try {
			this.action.moveToElement(changePreferencesBtn).click().perform();}
		catch (Exception e) {
			Reporter.getInstance().printError("Failed to Change Preferences");
		}
	}
	
	public boolean getbError() {
		return this.bError;
	}
	
	public void setbError(boolean bError) {
		this.bError = bError;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void search(String keyword) {
		/**
		 * Method that inputs keyword in the searchBox and triggers the searchButton.
		 */
		try {
			searchInputBox.clear();
			searchInputBox.sendKeys(keyword);
			action.moveToElement(searchBtn).click().perform();
		} catch (NoSuchElementException e) {
			this.bError = true;
			Reporter.getInstance().printError("Failed to search for " + keyword + "");
		} catch (StaleElementReferenceException e) {
			e.printStackTrace();
		}
	}
	
	public boolean areSuggestionsOrdered() {
		/**
		 * Method that verifies if suggestions are alphabetically ordered.
		 */
		boolean order = true;
		ArrayList<String> suggestionsText = new ArrayList<String>();
		if (suggestions.size() == 0 || suggestions == null) {
			Reporter.getInstance().printResult("No suggestions found");
			return false;
		}
		
		for (WebElement divTag: suggestions) {
			suggestionsText.add(divTag.getText());
		}
		
		suggestionsText = StringProcessor.stripAccents(suggestionsText);
				
		for(int i = 0; i < suggestionsText.size()-1; i++) {
			String currentSuggestion = suggestionsText.get(i);
			String nextSuggestion = suggestionsText.get(i+1);
			if(currentSuggestion.compareTo(nextSuggestion) > 0) {
				Reporter.getInstance().printResult(currentSuggestion + " and " + nextSuggestion + " are in the wrong order.");
				this.bError = true;
				order = false;
			}
		}
		return order;		
	}
	
	public void navigateSearchResult(String linkText) {
		/**
		 * Method that navigates to search result
		 */
		try {
			WebElement result = this.driver.findElement(By.linkText(linkText));
			this.action.moveToElement(result).click().perform();
		} catch (NoSuchElementException e) {
			this.bError = true;
			Reporter.getInstance().printError("Failed to navigate to result " + linkText);
		}
	}
	
	public void searchLetter(String letter) {
		/**
		 * Method that clicks on a letter in the search form of the Search page 
		 */
		String xpath = "//div[@id=\"keyword-input\"]//a[contains(@href,\"recherche-search.app?qi=" + letter + "\")]";
		try {
			WebElement letterLink = driver.findElement(By.xpath(xpath));
			action.moveToElement(letterLink).click().perform();
		} catch (NoSuchElementException e) {
			this.bError = true;
			Reporter.getInstance().printError("Failed to navigate to letter " + letter);
		}
	}
	
	public ArrayList<String> returnSuggestions() {
		/**
		 * Method that verifies if suggestions are alphabetically ordered.
		 */
		ArrayList<String> suggestionsText = new ArrayList<String>();
		if (suggestions.size() == 0) {
			return suggestionsText; // return an empty ArrayList if no suggestions is found
		}
				
		for (WebElement divTag: suggestions) {
			suggestionsText.add(divTag.getText());
		}
		return suggestionsText;
	}
	
	public String compareSuggestions(ArrayList<String> suggestions1, ArrayList<String> suggestions2) {
		String result = "OK";
		int numSuggestion1 = suggestions1.size();
		int numSuggestion2 = suggestions2.size();
		
		if( numSuggestion1 == 0 || numSuggestion2 == 0) {
			this.bError = true;
			result = "Suggestions are the same for the first and second element: ERROR (One or both of the arrays containing the suggestions is (are) empty). Suggestions 1: " + numSuggestion1 + " Suggestions 2: " + numSuggestion2;
			return result;
		} else if(numSuggestion1 != numSuggestion2) {
			this.bError = true;
			result = "Suggestions are the same for the first and second element: ERROR (number of suggestions for the first (" + numSuggestion1 + ") and second ("+ numSuggestion2 +") elements are different"; 
			return result;
		}
		
		for (String suggestion: suggestions1) {
			if(!suggestions2.contains(suggestion)) {
				this.bError = true;
				result = "ERROR suggestions for the first element contain an element that the second suggestions don't have.";
			}
		}
		return "Suggestions are the same for the first and second element: " +  result;
	}
		
	public void input(String keyword) {
		/**
		 * Method that inputs keyword in the search box
		 */
		try {
			searchInputBox.clear();
			searchInputBox.sendKeys(keyword);
			} catch (NoSuchElementException e) {
				this.bError = true;
				Reporter.getInstance().printError("Failed to input word " + keyword + " in the search box");
			}
	}
	
	public void clearSearch() {
		/**
		 * Method that clears Search.
		 */
		try { 
			action.moveToElement(clearBtn).click().perform();
		} catch (NoSuchElementException e) {
			this.bError = true;
			Reporter.getInstance().printError("Unable to clear search");
		}
	}
	
	public void toggle(String shortForm) {
		/**
		 * Takes as argument English`s or French`s SHORT FORM i.e. "en" or "fr" 
		 * xpath: //a[contains(@href,\"/parcourir-browse.app?lang=" + "en or fr")][text()=language]  
		 */		
		shortForm = shortForm.toLowerCase();							// Prevent case sensitive problems
		HashMap<String,String> language = new HashMap<String,String>();
		language.put("en", "English");
		language.put("fr", "Français");		
		String xpath = "//a[contains(@href, \"/recherche-search.app?lang=" + shortForm + "\") and text()=\"" + language.get(shortForm) + "\"]";
		try {
		WebElement languageToggle = driver.findElement(By.xpath(xpath));
		action.moveToElement(languageToggle).click().perform();
		} catch (NoSuchElementException e) {
			this.bError = true;
			Reporter.getInstance().printError("Language toggle not found, failed to switch Language from " + NomenclatureUtil.getLanguage() + " to " + language.get(shortForm));
			}
	}
	
	public int getNumberOfSearchResultDisplayed() {
		/** 
		 * Method that returns the number of terms on the page.
		 */
		return searchResultsDisplayed.size();
	}
	

	public boolean areResultsOrdered() {
		/**
		 * Method that verifies if the search results are alphabetically ordered
		 */
		boolean order = true;
		ArrayList<String> results = new ArrayList<String>();
		String currentResult;
		String nextResult;
		int numberOfResults = preferredTerms.size();
		
		switch(numberOfResults) { // Switch case for edge cases (0 and 1)
		case 1:
			return order;
		case 0: 
			Reporter.getInstance().printResult("Search has no result");
			return order;
		}
		
		// Get text from WebElements
		for(WebElement e: preferredTerms) {
			results.add(e.getText());
		}
		
		results = StringProcessor.stripAccents(results); // using Verifier class' stripAccents method to get rid of accents
		
		for(int i = 0; i < results.size()-1; i++) {
			currentResult = results.get(i);
			nextResult = results.get(i+1);
			if (currentResult.compareTo(nextResult) > 0) {
				Reporter.getInstance().printResult(currentResult + " and " + nextResult + " are in the wrong order.");
				this.bError = true;
				order = false;
			}
		}
		return order;
	}
	
	public int getNumberOfSearchResult() {
		/**
		 * Method that returns the total number of result in Search Terms.
		 * NOTE waiting for changes on span tag.
		 */
		int result = 0;
		String entries;
		try {
			entries = numberOfEntries.getText();
		} catch (NoSuchElementException e) {
			Reporter.getInstance().printError("Couldn't get the total number of entries.");
			entries = null;		
		}		
		
		if (entries == null) { // If no entries is found return 0
			return result;
		}
		
		entries = entries.replaceFirst("(Affiche [0-9]+ à [0-9]+ de [0-9]+|Showing [0-9]+ to [0-9]+ of [0-9]+)(,| )([0-9]+ entrées|[0-9]+ entries)", "$1$3");
		String[] arrOfString = entries.split("\\s+");
		result = Integer.parseInt(arrOfString[5]); 
		return result;
	}
	
	public boolean resultContainsWord(String keyword) {
		/**
		 * Method that finds if a keyword is contained in the results table.
		 * xpath: //*[contains(text(), "KEYWORD")]
		 * This xpath finds any tag that contains the KEYWORD in its text.
		 */
		for(WebElement anchorTag: resultLinks) {
			if(anchorTag.getText().contains(keyword)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean suggestionsContains(ArrayList<String> suggestions, String[] strarr) {
		/**
		 * Method to verify if keyword is contained in the list of suggestions
		 */
		for(String keyword: strarr) {
			if(suggestions.contains(keyword)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean allSuggestionsContains(ArrayList<String> suggestions, String[] strarr) {
		/**
		 * Method that verify all suggestions contains a list of keywords
		 */
		boolean contains = true;
		for(String keyword: strarr) {
			for(String suggestion: suggestions) {
				if (!suggestion.contains(keyword)) {
					contains = false;
				}
			}
		}
		return contains;		
	}		
}
