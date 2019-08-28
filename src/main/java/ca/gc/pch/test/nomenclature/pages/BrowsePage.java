package ca.gc.pch.test.nomenclature.pages;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import ca.gc.pch.test.nomenclature.util.NomenclatureUtil;
import ca.gc.pch.test.nomenclature.util.Reporter;
import ca.gc.pch.test.nomenclature.util.StringProcessor;

public class BrowsePage extends Page {
	
	/*  This class stores the location of the WebElements of Browse page and contains methods which perform operations on those WebElements.*/

	private final String pageID = "parcourir-browse.app";
	
	@FindBy(xpath="//input[@checked=\"checked\" and contains(@id, \"preferenceForm-wordPref\")]//following::label[1]")
	WebElement termOrder;	
	@FindBy(xpath="//img[@id=\"current-term-thumbnail\"]")
	WebElement currentTermThumbnail;
	@FindBy(xpath="//input[@type=\"submit\" and contains(@name,\"preferenceForm\")]")
	WebElement changePreferences;
	@FindBy(xpath="//a[contains(@id,\"current-term-uri\")]")
	WebElement currentTermURI;
	@FindBy(xpath="//button[@id=\"hdrClose\"]")
	WebElement closeThumbnail;
	@FindBy(xpath="//input[@checked=\"checked\" and contains(@id, \"preferenceForm-languagePref\")]//following::label[1]")
	WebElement linguisticVariant;
	@FindBy(xpath="//span[@id=\"current-term-tree\"]")
	WebElement currentSelectedTerm;
	@FindBy(xpath="//h2[@id=\"current-term-heading\"]")
	WebElement currentHeader;
	@FindBy(xpath="//span[@id=\"current-term-tree\"]//ancestor::li[1]//following-sibling::li[1]//descendant::a")
	List<WebElement> subTree;
	@FindBy(xpath="//section[@id=\"nomenclature-tree-content\"]/div[@id=\"nomenclature-tree\"]/ul[@class = \"tree_no_padding\"]/descendant::a")
	List<WebElement> categoriesLinks;
	@FindBy(xpath="//table[@id=\"summary\"]//descendant::a")
	List<WebElement> referenceAnchorTags;
	
	public BrowsePage() {
		// Initialization 
		initialization();
		initializeUserConfigurations(); // configure user configurations (running in break/fluent mode)
		this.url = this.userConfigurations.getTestEnv() + properties.getProperty("app.domain.name") + this.pageID;
		driver.get(this.url);
		PageFactory.initElements(driver, this); // initialize the WebElements using PageFatory at the end once the page is loaded.
	}
	
	public String getUrl() {
		return url;
	}
	
	public String validateBrowsePageTitle() {
		return driver.getTitle().trim();
	}	
	
	public boolean getbError() {
		return this.bError;
	}
	
	public void setbError(boolean bError) {
		this.bError = bError;
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
	
	public WebElement navigateChild(int index) {
	/**
	 *  IMPORTANT: This method CAN ONLY be called after .navigateCategory() has been called.
	 *  It verifies if the current WebElement has a child element
	 *  xpath: (//span[@id="current-term-tree"]//ancestor::li[1]//following-sibling::li[1]//descendant::a)[index]
	 *  This xpath finds the nth child in the subtree determined by the "index" and navigate to the link contained by its href attribute.
	 */
		WebElement nextElement;
		try {
			nextElement = driver.findElement(By.xpath("(//span[@id=\"current-term-tree\"]//ancestor::li[1]//following-sibling::li[1]//descendant::a)[" + index + "]"));
			this.action.moveToElement(nextElement).click().perform();
			Reporter.getInstance().printAction("Navigate into child: #" + index, this.getUserConfiguration().getIsFluent());
		} catch (NoSuchElementException e) {
			nextElement = null;
			Reporter.getInstance().printResult("Last element of this tree is " + getSelectedTerm());
		}	
		return nextElement;
	}
	
	public void toggle(String shortForm) {
		/**
		 * Takes as argument English`s or French`s SHORT FORM i.e. "en" or "fr" 
		 */		
		shortForm = shortForm.toLowerCase();							// Prevent case sensitive problems
		HashMap<String,String> language = new HashMap<String,String>();
		language.put("en", "English");
		language.put("fr", "Français");		
		String xpath = "//a[contains(@href, \"/parcourir-browse.app?lang=" + shortForm + "\") and text()=\"" + language.get(shortForm) + "\"]";
		try {
		WebElement languageToggle = driver.findElement(By.xpath(xpath));
		this.action.moveToElement(languageToggle).click().perform();
		} catch (NoSuchElementException e) {
			this.bError = true;
			Reporter.getInstance().printError("Language toggle not found, failed to switch Language from " + NomenclatureUtil.getLanguage() + " to " + language.get(shortForm));
			}
	}
	
	public void clickThumbnail() {
		/**
		 * Method that opens the thumbnail
		 */
		try {
			this.action.moveToElement(currentTermThumbnail).click().perform();
		} catch (NoSuchElementException e) {
			this.bError = true;
			Reporter.getInstance().printError("Failed to find thumbnail");
		}
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
	
	public WebElement navigateCategory(String category, boolean inEnglish) {
		/**
		 * IMPORTANT: This method has to be called when the browser is either on the "Browse hierarchy" or the "Parcourir la hiérarchie" page.
		 * Argument 1: Takes as argument the Category we want to navigate to written in the corresponding language 
		 * Argument 2: inEnglish = true or false        
		 */
		WebElement firstCategory;
		if (inEnglish) {
			this.toggle("en");
		}
		try {
			firstCategory = this.driver.findElement(By.partialLinkText(category));
			this.action.moveToElement(firstCategory).click().perform();
			Reporter.getInstance().printAction("Navigate into category: " + category, this.getUserConfiguration().getIsFluent());
		} catch (NoSuchElementException e) {
			this.bError = true;
			Reporter.getInstance().printError("Failed to find " + category);
			firstCategory = null;
		}
		return firstCategory;
	}
	
	public void changeLinguisticVariant(String preference) {
		/**
		 * Method that changes the linguistic variant preference in Browse page
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
			this.action.moveToElement(changePreferences).click().perform();}
		catch (Exception e) {
			Reporter.getInstance().printError("Failed to Change Preferences");
		}
	}
	
	public void clickNomenclatureURI() {
		/**
		 * Method that navigates to the Nomenclature URI link and prints the URL. 
		 * Then, it navigates back to the previous page.
		 */
		try {
			this.action.moveToElement(currentTermURI).click().perform();
			Reporter.getInstance().printResult("Nomenclature URI URL: " + this.driver.getCurrentUrl()); // print URI url				
		} catch (NoSuchElementException e) {
			this.bError = true;
			Reporter.getInstance().printError("Nomenclature URI was not found");
		}		
	}
	
	public void closeThumbnail() {
		/**
		 * Method that closes the thumbnail
		 */
		try {
			this.action.moveToElement(closeThumbnail).click().perform();
		} catch (NoSuchElementException e) {
			this.bError = true;
			Reporter.getInstance().printError("Failed to close thumbnail");
		}
	}
	
	public String getLinguisticVariant() {		
		/**
		 *  Method to verify what is the selected linguistic variant
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

    public String getSelectedTerm() {
       /**
        * Method that extracts the selected term in Browse Hierarchy.
        * Locate id current_term_tree using xpath: //span[@id="current-term-tree"]
        */
       String selectedTerm;
       try {
           selectedTerm = currentSelectedTerm.getText();
       } catch (NoSuchElementException e) {
           this.bError = true;
           selectedTerm = "PROGRAM ERROR (selected term couldn't be found by driver)";
           return selectedTerm;
       }
       return selectedTerm.trim();
    }
    
	public String getHeaderText() {
		/**
		 * Method that extracts the header text in Browse Hierarchy.
		 * Locate header using xpath: //h2[@id = "current-term-heading"]
		 */ 
		String headerText;
		try {
			headerText = currentHeader.getText();
		} catch (NoSuchElementException e) {
			this.bError = true;
			headerText = "PROGRAM ERROR (No header detected in the right pane)";
			return headerText;
		}
		return headerText.trim();
	}

	public List<WebElement> getSubTree(){
		/**
		 * Method that gets the current node's subTree
		 * xpath: //span[@id="current-term-tree"]//ancestor::li[1]//following-sibling::li[1]//descendant::a
		 * This method first locates the current Selected term, then it finds all <a> tags in the subtree
		 */	
		return subTree;
		}
	
	public boolean isSubTreeOrdered() {
		/**
		 * Method that verifies if the last subtree in the structure tree is in order
		 */
		boolean order = true;		
		ArrayList<String> subCategories = new ArrayList<String>();
		String currentCategory;
		String nextCategory; 
		// Edge case where subtree contains only 1 or 0 element (no subtree, current node is the last element)
		// The case subTree.size() == 0 is handled by NavigateChild, as it won't call isSubTreeOrdered if there's no child for him to navigate to, therefore it will never happen that subTree.size() == 0
		
		if(subTree.size() == 1) {
			return order; // Order is always true if there's only one 
		}
		
		for(WebElement subTreeBranch: subTree) {
			currentCategory = subTreeBranch.getText();
			subCategories.add(currentCategory);
			}
		
		// Getting rid of accents
		subCategories = StringProcessor.stripAccents(subCategories);
	
		for(int i = 0; i < subCategories.size()-1 ;i++) {
			currentCategory = subCategories.get(i);
			nextCategory = subCategories.get(i+1);
			if(currentCategory.compareTo(nextCategory) > 0) {
				Reporter.getInstance().printResult(currentCategory + " and " + nextCategory + " are in the wrong order.");
				order = false;
				this.bError = true;
			}
		}		
		return order;
	}
		
	public boolean isCategoryOrdered() {
		/**
		 * Method that verifies if the text in the structure tree is in order
		 * Locate tree links using xpath: //section[@id="nomenclature-tree-content"]/div[@id="nomenclature-tree"]/ul[@class = "tree_no_padding"]/descendant::a
		 */
		boolean isAscendingOrder = true;
		ArrayList<String> categories = new ArrayList<String>();		
		if(categoriesLinks.size() <= 0){
			this.bError = true;
			Reporter.getInstance().printError("No categories found");
			return false;
		}
		
		String currentCategory;
		String nextCategory; 
		
		for(WebElement aCategory: categoriesLinks) {
			currentCategory = aCategory.getText();
			categories.add(currentCategory);
		}	
		
		// Getting rid of accents 
		categories = StringProcessor.stripAccents(categories);
		
		for(int i=0; i<categories.size()-1;i++) {
			currentCategory = categories.get(i);
			nextCategory = categories.get(i+1);
			if(currentCategory.compareTo(nextCategory) > 0) { // If the first string is smaller than the second, it will return -1, if the first string is bigger than the second it returns 1, therefore the order is wrong (decreasing).
				Reporter.getInstance().printResult(currentCategory + " and " + nextCategory + " are in the wrong order.");
				isAscendingOrder = false;
				this.bError = true;
			}
		}
		return isAscendingOrder;
	}
	
	public WebElement getThumbnail() {
		/**
		 * Method that gets the thumbnail WebElement and returns it. If it doesn't find it, it will return null. 
		 * xpath: //img[@id="current-term-thumbnail"]
		 */
		
		WebElement image;
		try {
			image = currentTermThumbnail;
		} catch (NoSuchElementException e) {
			this.bError = true;
			image = null;
		}
		return image;
	}

	public String getThumbNailCaption(String altKeyword) {
		/**
		 * Method that get the caption when the ThumbNail is clicked. Therefore, it has to be called after navigator.clickThumbNail()
		 */
		String thumbNailText;
		String xpath = "//div[@id=\"lbx-title\"]/div[@class=\"mfp-title\" and contains(text(),\""+ altKeyword + "\")]";
		try {
			thumbNailText = this.driver.findElement(By.xpath(xpath)).getText();
		} catch (NoSuchElementException e) {
			this.bError = true;
			thumbNailText = null;
		}
		return thumbNailText;
	}
	
	public String getNomenclatureURI() {
		/**
		 * Method that finds the current term uri
		 * xpath: //a[contains(@id,"current-term-uri")]
		 */
		String url;
		try {
			url = currentTermURI.getText();
		} catch (NoSuchElementException e) {
			this.bError = true;
			url = "No Nomenclature URI found";
			return url;
		}
		return url;
	}
	
	public String compareNomenclatureURI(String frNomenclatureURI, String enNomenclatureURI) {
		/**
		 * Method that compares if the Nomenclature URI is the same in French and English 
		 */
		String result = "OK";
		if(!frNomenclatureURI.equals(enNomenclatureURI)) {
			this.bError = true;
			result = "ERROR frURI (" + frNomenclatureURI + ") is not the same as enURI (" + enNomenclatureURI + ")";			
		}
		return "Nomenclature URI is the same in French and English: " + result;
	}
	
	public ArrayList<String> getReferenceLinks(){
		/**
		 * Method that gets all the reference links of an object
		 * xpath: //table[@id="summary"]//descendant::a
		 */
		ArrayList<String> referenceLinks = new ArrayList<String>();
		if(referenceAnchorTags.size() <= 0) {
			return referenceLinks;
		} else {
		for(WebElement anchorTag: referenceAnchorTags) {
			referenceLinks.add(anchorTag.getAttribute("href"));
		}
		Reporter.getInstance().printResult("Reference links:");
		
		for(String link: referenceLinks) { 
			Reporter.getInstance().printResult(link);
			}
		}
		return referenceLinks;		
	}	
}
