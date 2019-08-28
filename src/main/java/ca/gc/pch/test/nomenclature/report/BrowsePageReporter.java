package ca.gc.pch.test.nomenclature.report;

import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import ca.gc.pch.test.nomenclature.base.TestBase;
import ca.gc.pch.test.nomenclature.pages.BrowsePage;
import ca.gc.pch.test.nomenclature.testdata.TestData;
import ca.gc.pch.test.nomenclature.util.LinkVerifier;
import ca.gc.pch.test.nomenclature.util.NomenclatureUtil;
import ca.gc.pch.test.nomenclature.util.Reporter;
import ca.gc.pch.test.nomenclature.util.TestUtil;

public class BrowsePageReporter extends TestBase {

	private BrowsePage browsePage = new BrowsePage();

	public BrowsePageReporter() {}
	
	public String verifyLanguage(String lang) {
		String result = "OK";
		String currentLanguage = NomenclatureUtil.getLanguage();
		if (!lang.equals(currentLanguage)) {
			result = "ERROR (got " + currentLanguage + ")";
			this.browsePage.setbError(true);
		}
		return "The current language is " + lang + ": " + result;
	}

	public String verifyTermOrder(String order) {
		String result = "OK";
		String currentTermOrder = this.browsePage.getTermOrder();
		if (!order.equals(currentTermOrder)) {
			result = "ERROR (got " + currentTermOrder + ")";
		}
		return "The current term order is " + order + ": " + result;
	}

	public String verifyLinguisticVariant(String langvariant) {
		String result = "OK";
		String currentLinguisticVariant = this.browsePage.getLinguisticVariant();
		if (!langvariant.equals(currentLinguisticVariant)) {
			result = "ERROR (got " + currentLinguisticVariant + ")";
			this.browsePage.setbError(true);
		}
		return "The current linguistic variant is " + langvariant + ": " + result;
	}

	public String verifyGetSelectedTerm(String term) {
		/**
		 * Method that compares the selected term to a given term. This method is CASE
		 * INSENSITIVE.
		 */
		String result = "OK";
		String selectedTerm = this.browsePage.getSelectedTerm();
		if (term.compareToIgnoreCase(selectedTerm) != 0) { // https://beginnersbook.com/2013/12/java-string-comparetoignorecase-method-example/
			this.browsePage.setbError(true);
			result = "ERROR (got " + selectedTerm + ")";
		}
		return "The selected term is " + term + ": " + result;
	}

	public String verifySelectedTermRightPane() {
		/**
		 * Method that verifies if the header text in the right pane is the same than
		 * the selected term in the left pane
		 */
		String result = "OK";
		String selectedTerm = this.browsePage.getSelectedTerm();
		String headerTerm = this.browsePage.getHeaderText();
		if (!selectedTerm.equals(headerTerm)) {
			this.browsePage.setbError(true);
			result = "ERROR header text is (" + headerTerm + ") and selected term's text is (" + selectedTerm + ")";
		}
		return "The term \"" + headerTerm
				+ "\" in the header of the right detail pane is the same as the selected term in the tree: " + result;
	}

	public String verifySubCategoryOrder() {
		/**
		 * Method that verifies if the sub categories are ordered
		 */
		String result = "OK";
		if (!this.browsePage.isSubTreeOrdered()) {
			this.browsePage.setbError(true);
			result = "ERROR";
		}
		return "The sub categories list is ordered alphabetically: " + result;
	}

	public String verifyCategoryOrder() {
		/**
		 * Method that verifies if categories are ordered alphabetically including
		 * accents.
		 */
		String result = "OK";
		if (!this.browsePage.isCategoryOrdered()) {
			this.browsePage.setbError(true);
			result = "ERROR";
		}
		return "The categories list is ordered alphabetically: " + result;
	}

	public String verifyCategoryLabel() {
		/**
		 * Method that verifies if the header text in the right detail pane is contained
		 * in the text of the selected term in the left structure tree. This method is
		 * strictly for the FIRST LEVEL OF CATEGORIES (i.e. after clicking on one of the
		 * categories)
		 */
		String result = "OK";
		String selectedTerm = this.browsePage.getSelectedTerm().toLowerCase();
		String headerTerm = this.browsePage.getHeaderText().toLowerCase();
		if (!selectedTerm.contains(headerTerm)) {
			this.browsePage.setbError(true);
			result = "ERROR header text is (" + headerTerm + ") and selected term's text is (" + selectedTerm + ")";
		}
		return "The term \"" + headerTerm + "\" in the detail right pane is the same as the selected term in the tree: "
				+ result;
	}

	public String verifyContainsWord(String keyword) {
		/**
		 * Method that verifies if the document contains keyword and returns a string
		 * message.
		 */
		String result = "OK";
		if (!NomenclatureUtil.containsWord(keyword)) {
			this.browsePage.setbError(true);
			result = "ERROR";
		}
		return "The word " + keyword + " is contained in the page: " + result;
	}

	public String verifySpan(String language, String keyword) {
		/**
		 * Method that finds a <span> tag in the right detail pane containing the
		 * attribute @lang = "language" and returns the word.
		 * xpath://section[@id="nomenclature-detail-content"]//descendant::span[@lang="language"]
		 * 1st argument: language must be short form i.e. "fr" or "en" 2nd argument:
		 * text contained in the span tag
		 */
		String xpath = "//section[@id=\"nomenclature-detail-content\"]//descendant::span[@lang=\"" + language
				+ "\" and text()=\"" + keyword + "\"]";
		String result = "OK";
		try {
			WebElement foreignLanguage = driver.findElement(By.xpath(xpath));
		} catch (NoSuchElementException e) {
			this.browsePage.setbError(true);
			result = "ERROR (Did not find keyword " + keyword + " with attribute @lang=\"" + language + "\" on "
					+ driver.getCurrentUrl() + ")";
		}
		return "The word " + keyword + " has attribute @lang=\"" + language + "\": " + result;
	}

	public String verifyThumbnail() {
		/**
		 * Method that verifies if the thumbnail can be found in the page.
		 */
		String result = "OK";
		String selectedTerm = this.browsePage.getSelectedTerm(); 
		if (this.browsePage.getThumbnail() == null) {
			this.browsePage.setbError(true);
			result = "ERROR (Thumbnail not found)";
		}
		return selectedTerm + " has a thumbnail in the right detail pane: " + result;
	}

	public String verifyThumbnailClicked(String altKeyword) {
		/**
		 * Method that verifies the thumbnail is displayed after being clicked.
		 */
		String result = "OK";
		String xpath = "//img[@class=\"mfp-img\" and contains(@alt,\"" + altKeyword + "\")]";
		if (driver.findElements(By.xpath(xpath)).size() == 0){
			this.browsePage.setbError(true);
			result = "ERROR (Thumbnail is not displayed after being clicked)";
		}
		return "The thumbnail is displayed after being clicked: " + result;
	}

	public String verifyThumbNailCaption(String altKeyword) {
		/**
		 * Method that verifies if the thumbnail has a caption describing the image
		 */
		String result = "OK";
		String thumbNailCaption = this.browsePage.getThumbNailCaption(altKeyword);
		if (thumbNailCaption == null || thumbNailCaption.equals("")) {
			this.browsePage.setbError(true);
			result = "ERROR (Thumbnail caption is empty or was not found)";
		}
		return "Thumnail has text in its caption: " + result;
	}

	public String verifyReferenceLinks(ArrayList<String> frReferenceLinks, ArrayList<String> enReferenceLinks) {
		/**
		 * Method that verifies the references links in the "Other references" tab are
		 * the same in French and English.
		 */

		String result = "OK";
		int frReferenceLinksSize = frReferenceLinks.size();
		int enReferenceLinksSize = enReferenceLinks.size();
		if (frReferenceLinksSize == 0 || enReferenceLinksSize == 0) {
			this.browsePage.setbError(true);
			result = "Other reference links are the same in French (" + frReferenceLinksSize + ") and English ("
					+ enReferenceLinksSize
					+ "): ERROR (One or both of the arrays containing the reference links is (are) empty)";
			return result;
		}

		if (frReferenceLinksSize != enReferenceLinksSize) {
			this.browsePage.setbError(true);
			result = "Other reference links are the same in French (" + frReferenceLinksSize + ") and English ("
					+ enReferenceLinksSize + "): ERROR (Different number of links in French and English)";
			return result;
		}

		for (String link : frReferenceLinks) {
			if (!enReferenceLinks.contains(link)) {
				this.browsePage.setbError(true);
				result = "ERROR";
			}
		}
		return "Other reference links are the same in French (" + frReferenceLinksSize + ") and English ("
				+ enReferenceLinksSize + "): " + result;
	}

	public String verifyNotContainsWord(String keyword) {
		/**
		 * Method that verifies if a keyword is NOT contained in a document. Returns
		 * "OK" if keyword is not contained in the document.
		 */
		String result = "OK";
		if (NomenclatureUtil.containsWord(keyword)) {
			this.browsePage.setbError(true);
			result = "ERROR";
		}
		return "The word " + keyword + " is NOT contained in the page: " + result;
	}

	public String verifyPreviousElements(ArrayList<String> structureTree) {
		/**
		 * Method that verifies if the previous elements in the structure tree still
		 * appears
		 */
		String result = "OK";
		for (String node : structureTree) {
			if (!NomenclatureUtil.containsWord(node)) {
				this.browsePage.setbError(true);
				result = "ERROR (" + node + " was not found in the structure tree)";
			}
		}
		return "Previous elements of the structure tree are displayed on the current page: " + result;
	}
			
	public static void main(String[] args) {
		
		// Create an instance of BrowsePageReporter
		BrowsePageReporter browsePageReporter = new BrowsePageReporter();
		
		// Setting test run mode, if isFluent == 1, the test runs in "Break" mode, else it runs in "Fluent" mode  
		int isFluent = browsePageReporter.browsePage.getUserConfiguration().getIsFluent();
		
		// Get testing url from BrowsePage		
		String url = browsePageReporter.browsePage.getUrl();
		
        // Get an instance of Reporter
        Reporter reporter = Reporter.getInstance(); 
        
        // Testing time
        reporter.printInfo("Testing time: " + TestUtil.currentTime());
        
        // Current testing URL
        reporter.printInfo("Testing executed on URL: " + driver.getCurrentUrl());
        
		/* Test */ 
		//Browse Page
		reporter.printInfo("\n");
        reporter.printInfo("************");
        reporter.printInfo("Browse Page");
        reporter.printInfo("************");
        reporter.printInfo("\n");
        reporter.printAction("Go to " + url, isFluent);
        reporter.printInfo("\n");
        				
		reporter.printInfo("Test default parameters");
        reporter.printInfo("--------------------");
        reporter.printCheck("Verify default parameters are Français, ordre " + TestData.hOrder.get("frInv") + " and linguistic variant " + TestData.hLingVariant.get("frInt"), isFluent);
        reporter.printResult(browsePageReporter.verifyLanguage("Français"));
        reporter.printResult(browsePageReporter.verifyTermOrder(TestData.hOrder.get("frInv").toString()));
        reporter.printResult(browsePageReporter.verifyLinguisticVariant(TestData.hLingVariant.get("frInt").toString()));
        reporter.printInfo("\n");
		
        reporter.printInfo("Test language toggle");
        reporter.printInfo("--------------------");
        reporter.printAction("Toggle in English", isFluent);
        browsePageReporter.browsePage.toggle("en");
        reporter.printCheck("Verify the language of the page is English, contains Browse and Category", isFluent);
        reporter.printResult(browsePageReporter.verifyLanguage("English"));
        reporter.printResult(browsePageReporter.verifyContainsWord("Browse"));
        reporter.printResult(browsePageReporter.verifyContainsWord("Category"));
        reporter.printAction("Toggle in French", isFluent);
        browsePageReporter.browsePage.toggle("fr");
        reporter.printCheck("Verify the language of the page is French, contains Parcourir and Catégorie", isFluent);
        reporter.printResult(browsePageReporter.verifyLanguage("Français"));
        reporter.printResult(browsePageReporter.verifyContainsWord("Parcourir"));
        reporter.printResult(browsePageReporter.verifyContainsWord("Catégorie"));
        reporter.printInfo("\n");
        
        reporter.printInfo("Test the order of the categories - First Level");
        reporter.printInfo("--------------------");
        reporter.printAction("Navigate to " + url, isFluent);
        driver.get(url); // Navigate to testing URL
        reporter.printCheck("Ensure the categories alpahabetically ascending ordered including accents", isFluent);
        reporter.printResult(browsePageReporter.verifyCategoryOrder());        
        reporter.printInfo("\n");
                
        reporter.printInfo("Test the categories");
        reporter.printInfo("--------------------");
        ArrayList<String> structureTree = new ArrayList<String>(); // ArrayList that will store the string of all the nodes we've navigated through
        reporter.printAction("Navigate the category tree until the last child", isFluent);
        browsePageReporter.browsePage.navigateCategory("Catégorie 01", false); // navigate to Catégorie 01
        structureTree.add(browsePageReporter.browsePage.getSelectedTerm());   
        reporter.printCheck("Ensure the selected term appears in the tree (left) and in the detail (right)", isFluent);
        reporter.printResult(browsePageReporter.verifyCategoryLabel()); 
        reporter.printCheck("Ensure the sub-categories are ordered alpahabetically ascending including accents", isFluent);
        reporter.printResult(browsePageReporter.verifySubCategoryOrder());
        
        // Navigating child until there's no more link
        int childIndex = 1; // xPath doesn't use zero indexing, hence this index will chose the first child of every subtree
        while(browsePageReporter.browsePage.navigateChild(childIndex) != null) {
            structureTree.add(browsePageReporter.browsePage.getSelectedTerm());
            reporter.printCheck("Ensure the previous selected term still appears in the tree (left)", isFluent);
            reporter.printResult(browsePageReporter.verifyPreviousElements(structureTree));
            reporter.printCheck("Ensure the selected term appears in the tree (left) and in the detail pane (right)", isFluent);
            reporter.printResult(browsePageReporter.verifySelectedTermRightPane()); // Method that compares the String from the Selected term in the Structure Tree and the Header text on the detail pane on the right.   
            if(browsePageReporter.browsePage.getSubTree().size() > 0) {					  // Only verify subTree order if it's not empty
                reporter.printCheck("Ensure the sub-categories are ordered alpahabetically ascending including accents", isFluent);
                reporter.printResult(browsePageReporter.verifySubCategoryOrder());           
            }
        }    
        reporter.printInfo("\n");

        // Testing sort with accents in Storage & Display Furniture
        reporter.printInfo("Testing the sort on Storage & Display Furniture (as Étagère has an accent)");
        reporter.printInfo("--------------------");  
        reporter.printAction("Navigate to " + url + "?id=1215" , isFluent);
        driver.get(url+"?id=1215");
        reporter.printCheck("Ensure the sub-categories are ordered alpahabetically ascending including accents", isFluent);
        reporter.printResult(browsePageReporter.verifySubCategoryOrder());
        reporter.printInfo("\n");
        
        //Testing Chair
        reporter.printInfo("Testing the data on Chair and comparing FR/EN");
        reporter.printInfo("--------------------");  
        reporter.printAction("Navigate to " + url + "?id=1090" , isFluent);
        driver.get(url+ "?id=1090");
            //titles and order in FR toggle to FR verifyRightPane toggle back to english 
        reporter.printCheck("Ensure the selected term appears in the tree (left) and in the detail pane (right)", isFluent);
        reporter.printResult(browsePageReporter.verifySelectedTermRightPane());       
        reporter.printCheck("Ensure the sub-categories are ordered alpahabetically ascending including accents", isFluent);
        reporter.printResult(browsePageReporter.verifySubCategoryOrder());
            //data check in FR toggle to FR ? 
        reporter.printCheck("Ensure the page contains the word 'Chair'", isFluent);
        reporter.printResult(browsePageReporter.verifyContainsWord("Chair"));
        reporter.printCheck("Ensure the page containing the word 'Chair' is tag lang in English as we are in a French page", isFluent);
        reporter.printResult(browsePageReporter.verifySpan("en","Chair")); 
        reporter.printCheck("Ensure the page contains the word 'f.'", isFluent);
        reporter.printResult(browsePageReporter.verifyContainsWord("f."));
        reporter.printCheck("Ensure the page contains the word 'Siège à une seule place'", isFluent);
        reporter.printResult(browsePageReporter.verifyContainsWord("Siège à une seule place"));
        reporter.printCheck("Ensure the page contains the word 'Le Dictionnaire descriptif et visuel d'objets de Parcs Canada'", isFluent);
        reporter.printResult(browsePageReporter.verifyContainsWord("Le Dictionnaire descriptif et visuel d'objets de Parcs Canada"));
        reporter.printCheck("Ensure the page contains the word 'Silla'", isFluent);
        reporter.printResult(browsePageReporter.verifyContainsWord("Silla"));
        reporter.printCheck("Ensure the page contains the word '1978-2010'", isFluent);
        reporter.printResult(browsePageReporter.verifyContainsWord("1978-2010"));
        reporter.printCheck("Ensure the page contains the word '02-00147'", isFluent);
        reporter.printResult(browsePageReporter.verifyContainsWord("1978-2010"));
            //image and thumbnail in FR
        reporter.printCheck("Ensure the thumbnail image appears", isFluent);
        reporter.printResult(browsePageReporter.verifyThumbnail()); 	
        reporter.printAction("Click to the thumbnail to display the image", isFluent);
        browsePageReporter.browsePage.clickThumbnail();
        String altKeyword = "Chaise";
        reporter.printCheck("Ensure the full image appears and the alt and caption is " + altKeyword, isFluent);
        reporter.printResult(browsePageReporter.verifyThumbnailClicked(altKeyword));	   // verify that the thumbnail is displayed after being clicked on and its alt attribute contains "Chaise"
        reporter.printResult(browsePageReporter.verifyThumbNailCaption(altKeyword));     // verify that the thumbnail caption contains "Chaise" 
        reporter.printAction("Close the thumbnail", isFluent);
        browsePageReporter.browsePage.closeThumbnail();
        	// other references and Nomenclature URI in FR
        reporter.printAction("Note all the URLS in the other references tab", isFluent);
        ArrayList<String> frReferenceLinks = browsePageReporter.browsePage.getReferenceLinks(); 
        reporter.printAction("Note the Nomenclature URI of the term", isFluent);
        String frNomenclatureURI = browsePageReporter.browsePage.getNomenclatureURI();
        reporter.printResult("Nomenclature URI in French: " + frNomenclatureURI);
            // Switch to English 
        reporter.printAction("Navigate to the English page", isFluent);      
        browsePageReporter.browsePage.toggle("en");
        	// check data in English
        reporter.printCheck("Ensure the page contains the word 'Chaise'", isFluent);
        reporter.printResult(browsePageReporter.verifyContainsWord("Chaise"));
        reporter.printCheck("Ensure the page contains the word 'Chaise' is tag lang in French as we are in an English page", isFluent);
        reporter.printResult(browsePageReporter.verifySpan("fr","Chaise"));        
        reporter.printCheck("Ensure the page contains the word 'f.'", isFluent);
        reporter.printResult(browsePageReporter.verifyContainsWord("f."));
        reporter.printCheck("Ensure the page contains the word 'A movable seat with a back'", isFluent);
        reporter.printResult(browsePageReporter.verifyContainsWord("A movable seat with a back"));
        reporter.printCheck("Ensure the page contains the word 'Parks Canada Descriptive and Visual Dictionary of Objects'", isFluent);
        reporter.printResult(browsePageReporter.verifyContainsWord("Parks Canada Descriptive and Visual Dictionary of Objects"));
        reporter.printCheck("Ensure the page contains the word 'Silla'", isFluent);
        reporter.printResult(browsePageReporter.verifyContainsWord("Silla"));
        reporter.printCheck("Ensure the page contains the word '1978-2010'", isFluent);
        reporter.printResult(browsePageReporter.verifyContainsWord("1978-2010"));
        reporter.printCheck("Ensure the page contains the word '02-00147'", isFluent);
        reporter.printResult(browsePageReporter.verifyContainsWord("02-00147"));
        	//other references and Nomenclature URI in EN
        reporter.printCheck("Ensure the list of URLs in the other references tab is the same in both languages", isFluent);
        ArrayList<String> enReferenceLinks = browsePageReporter.browsePage.getReferenceLinks(); 
        reporter.printResult(browsePageReporter.verifyReferenceLinks(frReferenceLinks, enReferenceLinks));
        reporter.printCheck("Ensure the Nomenclature URIs are the same in both languages", isFluent);
        String enNomenclatureURI = browsePageReporter.browsePage.getNomenclatureURI();
        reporter.printResult("Nomenclature URI in English: " + enNomenclatureURI);
        reporter.printResult(browsePageReporter.browsePage.compareNomenclatureURI(frNomenclatureURI, enNomenclatureURI));
        reporter.printInfo("\n");        
        
        //Testing term preferences
        reporter.printInfo("Testing the term preferences");
        reporter.printInfo("--------------------");  
        reporter.printAction("Navigate to the Chair, Rocking page: " + url + "?id=1128", isFluent);      
        driver.get(url+ "?id=1128&lang=en");
        	//EN, inverted, International
        reporter.printCheck("Verify default parameters are English, order " + TestData.hOrder.get("enInv") + " and linguistic variant " + TestData.hLingVariant.get("enInt"), isFluent);
        reporter.printResult(browsePageReporter.verifyLanguage("English"));
        reporter.printResult(browsePageReporter.verifyTermOrder(TestData.hOrder.get("enInv").toString()));
        reporter.printResult(browsePageReporter.verifyLinguisticVariant(TestData.hLingVariant.get("enInt").toString()));
        reporter.printCheck("Ensure the displayed term is Chair, Rocking", isFluent);
        reporter.printResult(browsePageReporter.verifyGetSelectedTerm("Chair, Rocking"));
            //EN, natural, International
        reporter.printAction("Change the term preferences to " + TestData.hOrder.get("enNat"), isFluent);
        browsePageReporter.browsePage.changeTermOrder(TestData.hOrder.get("enNat").toString());
        browsePageReporter.browsePage.performChangePreferences();
        reporter.printCheck("Ensure the displayed term is Rocking Chair", isFluent);
        reporter.printResult(browsePageReporter.verifyGetSelectedTerm("Rocking Chair"));
            //FR, natural, International
        reporter.printAction("toggle in French", isFluent);
        browsePageReporter.browsePage.toggle("fr");
        reporter.printCheck("Verify default parameters are Français, ordre " + TestData.hOrder.get("frNat") + " and linguistic variant " + TestData.hLingVariant.get("frInt"), isFluent);
        reporter.printResult(browsePageReporter.verifyLanguage("Français"));
        reporter.printResult(browsePageReporter.verifyTermOrder(TestData.hOrder.get("frNat").toString()));
        reporter.printResult(browsePageReporter.verifyLinguisticVariant(TestData.hLingVariant.get("frInt").toString()));
        reporter.printCheck("Ensure the displayed term is Berceuse", isFluent);
        reporter.printResult(browsePageReporter.verifyGetSelectedTerm("Berceuse"));
        reporter.printCheck("Ensure the English term 'Rocking Chair' appears in the page", isFluent);
        reporter.printResult(browsePageReporter.verifyContainsWord("Rocking Chair"));
            //FR, inverted, International
        reporter.printAction("Change the term preferences to " + TestData.hOrder.get("frInv") , isFluent);
        browsePageReporter.browsePage.changeTermOrder(TestData.hOrder.get("frInv").toString());
        browsePageReporter.browsePage.performChangePreferences();
        reporter.printCheck("Ensure the English term 'Chair, Rocking' appears in the page", isFluent);
        reporter.printResult(browsePageReporter.verifyContainsWord("Chair, Rocking"));
        reporter.printInfo("\n");
        
        //Testing spelling preferences
        reporter.printInfo("Testing the spelling preferences");
        reporter.printInfo("--------------------");  
        reporter.printAction("Navigate to the Pistol, Dueling page:" + url + "?id=8284", isFluent);      
        driver.get(url+ "?id=8284&lang=en");
            //EN, inverted, International
        reporter.printCheck("Verify default parameters are English, ordre " + TestData.hOrder.get("enInv") + " and linguistic variant " + TestData.hLingVariant.get("enInt"), isFluent);
        reporter.printResult(browsePageReporter.verifyLanguage("English"));
        reporter.printResult(browsePageReporter.verifyTermOrder(TestData.hOrder.get("enInv").toString()));
        reporter.printResult(browsePageReporter.verifyLinguisticVariant(TestData.hLingVariant.get("enInt").toString()));
        reporter.printCheck("Ensure the displayed term is Pistol, Dueling", isFluent);
        reporter.printResult(browsePageReporter.verifyGetSelectedTerm("Pistol, Dueling"));
        reporter.printCheck("Ensure the French term 'Pistolet de duel' appears in the page", isFluent);
        reporter.printResult(browsePageReporter.verifyContainsWord("Pistolet de duel"));
            //EN, Inverted, Canadian
        reporter.printAction("Change the spelling preferences to " + TestData.hLingVariant.get("enCan"), isFluent);
        browsePageReporter.browsePage.changeLinguisticVariant(TestData.hLingVariant.get("enCan").toString());
        browsePageReporter.browsePage.performChangePreferences();        
        reporter.printCheck("Ensure the displayed term is Pistol, Duelling", isFluent);
        reporter.printResult(browsePageReporter.verifyGetSelectedTerm("Pistol, Duelling"));
        reporter.printCheck("Ensure the French term 'Pistolet de duel' appears in the page", isFluent);
        reporter.printResult(browsePageReporter.verifyContainsWord("Pistolet de duel"));
            //FR, Inverted, Canadian
        reporter.printAction("Toggle in French", isFluent);
        browsePageReporter.browsePage.toggle("fr");
        reporter.printCheck("Verify default parameters are Français, ordre " + TestData.hOrder.get("frInv") + " and linguistic variant " + TestData.hLingVariant.get("frCan"), isFluent);
        reporter.printResult(browsePageReporter.verifyLanguage("Français"));
        reporter.printResult(browsePageReporter.verifyTermOrder(TestData.hOrder.get("frInv").toString()));
        reporter.printResult(browsePageReporter.verifyLinguisticVariant(TestData.hLingVariant.get("frCan").toString()));
        reporter.printCheck("Ensure the displayed term is Pistolet de duel", isFluent);
        reporter.printResult(browsePageReporter.verifyGetSelectedTerm("Pistolet de duel"));
        reporter.printCheck("Ensure the English term 'Pistol, Duelling' appears in the page", isFluent);
        reporter.printResult(browsePageReporter.verifyContainsWord("Pistol, Duelling"));
            //FR, Inverted, International
        reporter.printAction("Change the spelling preferences to " + TestData.hLingVariant.get("frInt"), isFluent);
        browsePageReporter.browsePage.changeLinguisticVariant(TestData.hLingVariant.get("frInt").toString());
        browsePageReporter.browsePage.performChangePreferences();
        reporter.printCheck("Ensure the English term 'Pistol, Dueling' appears in the page", isFluent);
        reporter.printResult(browsePageReporter.verifyContainsWord("Pistol, Dueling")); // this has to be changed to Pistol, Duelling I think, because that's what is written on the webpage.
        reporter.printInfo("\n");
        
        //Testing the Nomenclature URI
        reporter.printInfo("Testing the nomenclature URI");
        reporter.printInfo("--------------------");   
        reporter.printAction("Navigate to " + url + "?id=1090&lang=en", isFluent);      
        driver.get(url+ "?id=1090&lang=en");
        String enWord_URI="Chair";
        String frWord_URI="Chaise";
        reporter.printCheck("Ensure the displayed term is " + enWord_URI, isFluent);
        reporter.printResult(browsePageReporter.verifyGetSelectedTerm(enWord_URI));
            //click on the Nomenclature URI and print the URL
        reporter.printAction("Click on the Nomenclature URI", isFluent);
        browsePageReporter.browsePage.clickNomenclatureURI();
        reporter.printCheck("Ensure the displayed term is " + enWord_URI, isFluent);
        reporter.printResult(browsePageReporter.verifyGetSelectedTerm(enWord_URI));
        reporter.printAction("Toggle in French", isFluent);
        browsePageReporter.browsePage.toggle("fr");
        reporter.printCheck("Ensure the displayed term is " + frWord_URI, isFluent);
        reporter.printResult(browsePageReporter.verifyGetSelectedTerm(frWord_URI));
            //click on the Nomenclature URI and print the URL
        reporter.printAction("Click on the Nomenclature URI", isFluent);
        browsePageReporter.browsePage.clickNomenclatureURI();
        reporter.printCheck("Ensure the displayed term is " + frWord_URI, isFluent);
        reporter.printResult(browsePageReporter.verifyGetSelectedTerm(frWord_URI));
        reporter.printInfo("\n");
        
        //Testing the tabs for other references
        reporter.printInfo("Testing the tabs for other references");
        reporter.printInfo("--------------------");     
        String enWord_ref = "Other references to this concept";
        String frWord_ref = "Autres références à ce concept";
            //test the tabs on chair - with few references
        reporter.printAction("Navigate to " + url + "?id=1090&lang=en", isFluent);      
        driver.get(url+ "?id=1090&lang=en");
        reporter.printCheck("Ensure the page contains the word: " + enWord_ref, isFluent);
        reporter.printResult(browsePageReporter.verifyContainsWord(enWord_ref));
        reporter.printCheck("Ensure in Dev, Chair has only few references (less than 7)", isFluent);
        enReferenceLinks = browsePageReporter.browsePage.getReferenceLinks(); 
        reporter.printAction("Toggle in French", isFluent);
        browsePageReporter.browsePage.toggle("fr");
        reporter.printCheck("Ensure the page contains the word: " + frWord_ref, isFluent);
        reporter.printResult(browsePageReporter.verifyContainsWord(frWord_ref));
        reporter.printCheck("Ensure in Dev, Chair has only few references (less than 7)", isFluent);
        frReferenceLinks = browsePageReporter.browsePage.getReferenceLinks(); 
        reporter.printCheck("Ensure we have the same reference links in both languages", isFluent);
        reporter.printResult("Chair has " + enReferenceLinks.size() + " links in English and "+ frReferenceLinks.size() + " links in French");
        reporter.printResult(browsePageReporter.verifyReferenceLinks(frReferenceLinks, enReferenceLinks));
            //test the tabs on armchair - with all the references
        reporter.printAction("Navigate to " + url + "?id=1091&lang=en", isFluent);      
        driver.get(url+ "?id=1091&lang=en");
        reporter.printCheck("Ensure the page contains the word: " + enWord_ref, isFluent);
        reporter.printResult(browsePageReporter.verifyContainsWord(enWord_ref));
        reporter.printCheck("Ensure in Dev, Armchair has all the references (7)", isFluent);
        enReferenceLinks = browsePageReporter.browsePage.getReferenceLinks(); 
        reporter.printAction("Toggle in French", isFluent);
        browsePageReporter.browsePage.toggle("fr");
        reporter.printCheck("Ensure the page contains the word: " + frWord_ref, isFluent);
        reporter.printResult(browsePageReporter.verifyContainsWord(frWord_ref));
        reporter.printCheck("Ensure in Dev, Armchair has all the references (7)", isFluent);
        frReferenceLinks = browsePageReporter.browsePage.getReferenceLinks();
        reporter.printCheck("Ensure we have the same reference links in both languages", isFluent);
        reporter.printResult("Armchair has " + enReferenceLinks.size() + " links in English and "+ frReferenceLinks.size() + " links in French");
        reporter.printResult(browsePageReporter.verifyReferenceLinks(frReferenceLinks, enReferenceLinks));
            //test there are no tabs on klismos - with only few the references
        reporter.printAction("Navigate to " + url + "?id=1142&lang=en", isFluent);      
        driver.get(url+ "?id=1142&lang=en");
        reporter.printCheck("Ensure the page DOES NOT contains the word: " + enWord_ref, isFluent);
        reporter.printResult(browsePageReporter.verifyNotContainsWord(enWord_ref));
        reporter.printAction("Toggle in French", isFluent);
        browsePageReporter.browsePage.toggle("fr");
        reporter.printCheck("Ensure the page DOES NOT contains the word: " + frWord_ref, isFluent);
        reporter.printResult(browsePageReporter.verifyNotContainsWord(frWord_ref));         
        reporter.printInfo("\n");
        
        // Testing the links        
        LinkVerifier linkVerifier = new LinkVerifier(); // Create a LinkVerifier Object
        reporter.printInfo("Testing the links");
        reporter.printInfo("--------------------");    
        reporter.printAction("Navigate to " + url + "?id=1090&lang=en", isFluent);      
        driver.get(url+ "?id=1090&lang=en");
        reporter.printAction("Verify all the links", isFluent);
        linkVerifier.hasBrokenLinks(); 
        reporter.printAction("Toggle in French", isFluent);
        browsePageReporter.browsePage.toggle("fr");
        reporter.printCheck("Check all the links", isFluent);
        linkVerifier.hasBrokenLinks();
        reporter.printInfo("\n");
        
        // Closing driver after tests are done 
        tearDown();
        
        // Print out any error
        reporter.printInfo("Global result of this testing page");
        reporter.printInfo("--------------------");        
        if (browsePageReporter.browsePage.getbError() || linkVerifier.getbError()) {
            reporter.printInfo("Please verify for errors (ERROR)");
		} else {
		    reporter.printInfo("No error found");
			}  
        
        // Print Tester report if test is ran in Break mode 
        reporter.printTesterReport(isFluent);
	}
}
