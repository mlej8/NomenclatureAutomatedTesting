package ca.gc.pch.test.nomenclature.report;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ca.gc.pch.test.nomenclature.base.TestBase;
import ca.gc.pch.test.nomenclature.config.UserConfiguration;
import ca.gc.pch.test.nomenclature.pages.BrowsePage;
import ca.gc.pch.test.nomenclature.pages.SearchPage;
import ca.gc.pch.test.nomenclature.testdata.TestData;
import ca.gc.pch.test.nomenclature.util.NomenclatureUtil;
import ca.gc.pch.test.nomenclature.util.Reporter;
import ca.gc.pch.test.nomenclature.util.TestUtil;

public class SearchPageReporter extends TestBase {
	    
	private SearchPage searchPage = new SearchPage();
	private static String keyword;
	
	private String verifySuggestionOrdered() {
		/**
		 * Method that verifies the suggestions are ordered 
		 */
		String result = "OK";
		if (!this.searchPage.areSuggestionsOrdered()) {
			this.searchPage.setbError(true);
			result = "ERROR ";
		}
		return "The suggestions displayed are ordered: " +  result;
	}
	
	private String compareResults(int frResult, int enResult) {
		String result = "OK";
		if (frResult != enResult) {
			this.searchPage.setbError(true);
			result = "ERROR (" + frResult + " and " + enResult + " are not the same)";
		}
		return "The number of results in French and English are the same : " + result;
	}
	
	private String verifyNumberOfSearchResultDisplayed(int correctNumber) {
		/**
		 * Method that verifies the number of Search Result against the correct number
		 */
		String result = "OK";
		int numberOfSearchResultDisplayed = this.searchPage.getNumberOfSearchResultDisplayed(); 
		if ( numberOfSearchResultDisplayed != correctNumber) {
			this.searchPage.setbError(true);
			result = "ERROR " + numberOfSearchResultDisplayed + " is not equal to " + correctNumber;
		}
		return "The number of result displayed is equal to " + correctNumber + ": " + result;
	}
	
	   private String verifyResultOrder() {
		   /**
		    * Method that verifies if the sub categories are ordered
		    */
	        String result = "OK";
	        if (!this.searchPage.areResultsOrdered()){
	        	this.searchPage.setbError(true);
	            result = "ERROR";
	        } 
	        return "The current search results are ordered alphabetically: " + result;
	    }
		
	   private String verifyNumberOfSearchResult(int lowerBound, int upperBound, String keyWord) {
			/**
			 * Method that verifies the total number of search results is inclusively between the lowerBound and the upperBound.
			 */
			String result = "OK";
			int numResult = this.searchPage.getNumberOfSearchResult();
			if (!(lowerBound <= numResult && numResult <= upperBound)) {
				result = "ERROR";
				this.searchPage.setbError(true);			
			}
			return "The number of entries for " + keyWord + ", " + numResult + ", is between " + Integer.toString(lowerBound) + " and " + Integer.toString(upperBound) + ": "+ result;
		}
	
	   private String verifyResultContainsWord(String keyword) {	
			/**
			 * Method that verifies if the document contains keyword and returns a string message.
			 */
		    String result = "OK";
	        if(!this.searchPage.resultContainsWord(keyword)){
	        	this.searchPage.setbError(true);
	                result = "ERROR";
	            }
	        return "The word " + keyword + " is contained in the results of this page: " + result;
		}
		
	   private String verifyResultNotContainsWord(String keyword) {	
			/**
			 * Method that verifies if the document contains keyword and returns a string message.
			 */
		    String result = "OK";
		    
	        if(this.searchPage.resultContainsWord(keyword)){
	        		result = "ERROR";    
	        		this.searchPage.setbError(true);              
	            }
	        return "The word " + keyword + " is NOT contained in the results of this page: " + result;
		}
		
	   private String verifySuggestionsConstains(ArrayList<String> suggestions, String[] strarr) {
			String result = "OK";
			if(!this.searchPage.suggestionsContains(suggestions, strarr)) {
				result = "ERROR";
				this.searchPage.setbError(true); 
			}
			return "Suggestions contain keyword(s) " + Arrays.toString(strarr) + ": " + result;
		}
		
	   private String verifySuggestionsNotContains(ArrayList<String> suggestions, String[] strarr) {
			String result = "OK";
			if(this.searchPage.suggestionsContains(suggestions, strarr)) {
				result = "ERROR";
				this.searchPage.setbError(true); 
			}
			return "Suggestions does NOT contain keyword(s) " + Arrays.toString(strarr) + ": " + result;
		}
		
	   private String verifyNoSuggestions(String keyword) {
			String result = "OK";
			if (this.searchPage.returnSuggestions().size() != 0) {
				result = "ERROR";
				this.searchPage.setbError(true);
			}
			return "No suggestion is given for keyword \"" + keyword + "\": " + result;
		}
		
	   private String verifySameNumberOfResults(int resultEn, int resultFr) {	
			/**
			 * Method that verifies if the document contains keyword and returns a string message.
			 */
		    String result = "OK";
	        if(resultEn != resultFr){
	        	this.searchPage.setbError(true);
	                result = "ERROR";
	            }
	        return "The number of result in French (" + resultFr + ") and English ("+ resultEn +") are the same: " + result;
		}

	   private String verifyAllSuggestionsContainsWords(ArrayList<String> suggestions, String[] strarr) {
			String result = "OK";
			if(!(this.searchPage.allSuggestionsContains(suggestions, strarr))) {
				result = "ERROR";
				this.searchPage.setbError(true); 
			}
			return "All suggestions contain keyword(s) " + Arrays.toString(strarr) + ": " + result;
		
		}
		
	   private String verifyContainsWord(String keyword) {
			/**
			 * Method that verifies if the document contains keyword and returns a string
			 * message.
			 */
			String result = "OK";
			if (!NomenclatureUtil.containsWord(keyword)) {
				this.searchPage.setbError(true);
				result = "ERROR";
			}
			return "The word " + keyword + " is contained in the page: " + result;
		}

	   private String verifyLanguage(String lang) {
			String result = "OK";
			String currentLanguage = NomenclatureUtil.getLanguage();
			if (!lang.equals(currentLanguage)) {
				result = "ERROR (got " + currentLanguage + ")";
				this.searchPage.setbError(true);
			}
			return "The current language is " + lang + ": " + result;
		}
		
	   private String verifyTermOrder(String order) {
			String result = "OK";
			String currentTermOrder = this.searchPage.getTermOrder();
			if (!order.equals(currentTermOrder)) {
				result = "ERROR (got " + currentTermOrder + ")";
				this.searchPage.setbError(true);
			}
			return "The current term order is " + order + ": " + result;
		}
		
	   private String verifyLinguisticVariant(String langvariant) {
			String result = "OK";
			String currentLinguisticVariant = this.searchPage.getLinguisticVariant();
			if (!langvariant.equals(currentLinguisticVariant)) {
				result = "ERROR (got " + currentLinguisticVariant + ")";
				this.searchPage.setbError(true);
			}
			return "The current linguistic variant is " + langvariant + ": " + result;
		}
		
	   private void reportSearchPageInitialTest(int isFluent) {
			// 5. Search Page (Initial Test) on all 4 preference combinations <Natural, Canadian>, <Natural, International>, <Inverted, Canadian>, <Inverted, International>
			for(Entry<String, List<String>> parameter: TestData.preferences.entrySet()) { // iterating over key-value Set from preferences (Map)
				for(String linguisticVariant: parameter.getValue()) {            // iterating over list of linguisticVariants
					
					driver.get(this.searchPage.getUrl() +"?lang=fr");  // navigate back to French				
					Reporter.getInstance().printInfo("	Currently verifying with parameters: <" + parameter.getKey() + ", " + linguisticVariant + ">");
					// Setting test preferences.
					this.searchPage.changeTermOrder(parameter.getKey());
					this.searchPage.changeLinguisticVariant(linguisticVariant);
					this.searchPage.performChangePreferences();
			    
					/* Test */ 	
					Reporter.getInstance().printInfo("Test default parameters");
					Reporter.getInstance().printInfo("--------------------");
					Reporter.getInstance().printCheck("Ensure the language of the page is French and contains Chercher", isFluent);
					Reporter.getInstance().printResult(verifyLanguage("Français"));
					Reporter.getInstance().printResult(verifyContainsWord("Chercher"));
					Reporter.getInstance().printCheck("Ensure initial search is search all terms and displays the first 50 terms of total terms (between 14,000 and 15,000).", isFluent);
					Reporter.getInstance().printResult(verifyNumberOfSearchResultDisplayed(50));
					Reporter.getInstance().printResult(verifyNumberOfSearchResult(14000, 15000, "default the search page")); //There is a REGEX ERROR HERE
					int frResult = this.searchPage.getNumberOfSearchResult();
					Reporter.getInstance().printAction("Switch to English", isFluent);
					this.searchPage.toggle("en");
				    Reporter.getInstance().printCheck("Verify the language of the page is English, contains Search", isFluent);
				    Reporter.getInstance().printResult(verifyLanguage("English"));
				    Reporter.getInstance().printResult(verifyContainsWord("Search"));
				    Reporter.getInstance().printCheck("Ensure the total search result are the same in FR and EN." , isFluent);
				    int enResult = this.searchPage.getNumberOfSearchResult();
				    Reporter.getInstance().printResult(compareResults(frResult, enResult));
				    Reporter.getInstance().printInfo("\n");
					
				    //Test for guitar
				    Reporter.getInstance().printInfo("Test for guitar");
				    Reporter.getInstance().printInfo("--------------------");
				    Reporter.getInstance().printAction("Search for guitar", isFluent);
				    keyword = "guitar";
				    this.searchPage.search(keyword);
				    Reporter.getInstance().printCheck("Ensure the number of entries is between 5 and 15.", isFluent);
				    Reporter.getInstance().printResult(verifyNumberOfSearchResult(5,15,"guitar"));
				    Reporter.getInstance().printCheck("Ensure results are ordered alphabetically.", isFluent);
				    Reporter.getInstance().printResult(verifyResultOrder());
					enResult = this.searchPage.getNumberOfSearchResult();	
					 Reporter.getInstance().printAction("Switch to French", isFluent);
					 this.searchPage.toggle("fr");
					 Reporter.getInstance().printCheck("Ensure the total search result are the same in FR and EN.", isFluent);
				    frResult = this.searchPage.getNumberOfSearchResult();
				    Reporter.getInstance().printResult(compareResults(frResult, enResult));
				    Reporter.getInstance().printCheck("Ensure results are ordered alphabetically.", isFluent);
				    Reporter.getInstance().printResult(verifyResultOrder());
				    Reporter.getInstance().printInfo("\n");		
				    
				    //Test for color
				    Reporter.getInstance().printInfo("Test for color / colour");
				    Reporter.getInstance().printInfo("--------------------");
				    driver.get(this.searchPage.getUrl()+ "?lang=en");
				    Reporter.getInstance().printAction("Select the linguistic variant International", isFluent);
				    Reporter.getInstance().printCheck("Ensure default parameters are English, order Inverted and linguistic variant International", isFluent);
				    Reporter.getInstance().printResult(verifyLanguage("English"));
				    Reporter.getInstance().printResult(verifyTermOrder(parameter.getKey()));
				    Reporter.getInstance().printResult(verifyLinguisticVariant(linguisticVariant));
				    String keywordCanadian = "Colour"; // Canadian spell it the British way - "Colour" (as in the application)
				    String keywordInternational = "Color"; // Americans considered "International" spell it "Color"
				    Reporter.getInstance().printAction("Search for " + keywordCanadian, isFluent);
				    this.searchPage.search(keywordCanadian);
				    Reporter.getInstance().printCheck("Ensure the number of entries is between 5 and 10.", isFluent);
				    Reporter.getInstance().printResult(verifyNumberOfSearchResult(5,10,keywordCanadian)); 		    
				    if (linguisticVariant.equals("Canadian")) {
				    	 Reporter.getInstance().printCheck("Ensure the result does not contain '" + keywordInternational + "' that is the International variant.", isFluent);
				    	 Reporter.getInstance().printResult(verifyResultNotContainsWord(keywordInternational));
				    	} else if (linguisticVariant.equals("International")) {
				    		 Reporter.getInstance().printCheck("Ensure the result does not contain '" + keywordCanadian + "' that is the Canadian variant.", isFluent);
				    		 Reporter.getInstance().printResult(verifyResultNotContainsWord(keywordCanadian));			    
				    	}
				    Reporter.getInstance().printAction("Search for " + keywordInternational, isFluent);
				    this.searchPage.search(keywordInternational);
				    Reporter.getInstance().printCheck("Ensure the number of entries is between 5 and 10.", isFluent);
				    Reporter.getInstance().printResult(verifyNumberOfSearchResult(5,10,keywordInternational)); 
				    if (linguisticVariant.equals("Canadian")) {
				    	Reporter.getInstance().printCheck("Ensure the result does not contain '" + keywordInternational + "' that is the International variant.", isFluent);
				    	Reporter.getInstance().printResult(verifyResultNotContainsWord(keywordInternational));
					    } else if (linguisticVariant.equals("International")) {
					    	Reporter.getInstance().printCheck("Ensure the result does not contain '" + keywordCanadian + "' that is the Canadian variant.", isFluent);
					    	Reporter.getInstance().printResult(verifyResultNotContainsWord(keywordCanadian));			    
					    }
				    keyword = "col";
				    Reporter.getInstance().printAction("Search for " + keyword, isFluent);
				    this.searchPage.search(keyword);
				    Reporter.getInstance().printCheck("Ensure the number of entries is between 15 and 25.", isFluent);
				    Reporter.getInstance().printResult(verifyNumberOfSearchResult(15,25,keyword)); 
				    if (linguisticVariant.equals("Canadian")) {
				    	 Reporter.getInstance().printCheck("Ensure the result does NOT contain '" + keywordInternational + "' as we search for full words.", isFluent);
				    	 Reporter.getInstance().printResult(verifyResultNotContainsWord(keywordInternational));
					    } else if (linguisticVariant.equals("International")) {
					    	 Reporter.getInstance().printCheck("Ensure the result does NOT contain '" + keywordCanadian + "' as we search for full words.", isFluent);
					    	 Reporter.getInstance().printResult(verifyResultNotContainsWord(keywordCanadian));			    
					    }
				    Reporter.getInstance().printInfo("\n");
				    }
			}		
		}
		
	   private void reportTestA(int isFluent) {
			// Test A- Non-Preferred Term search	
			Reporter.getInstance().printAction("Navigating to: " + this.searchPage.getUrl() + "?lang=en", isFluent);
			driver.get(this.searchPage.getUrl() + "?lang=en");
			if (this.searchPage.getTermOrder() != "Inverted") {
				this.searchPage.changeTermOrder("Inverted");
				this.searchPage.performChangePreferences();
			}
			if (this.searchPage.getLinguisticVariant() != "International") {
				this.searchPage.changeLinguisticVariant("International");
				this.searchPage.performChangePreferences();	
			}
			Reporter.getInstance().printCheck("Ensure the radio button is to Inverted for Term order and International for Linguistic variant preference", isFluent);
			Reporter.getInstance().printResult(verifyLinguisticVariant("International"));
			Reporter.getInstance().printResult(verifyTermOrder("Inverted"));
			Reporter.getInstance().printAction("Search for acoumeter", isFluent);
			keyword = "acoumeter";
			this.searchPage.search(keyword);
			Reporter.getInstance().printResult(verifyNumberOfSearchResult(0, 3, keyword));
			Reporter.getInstance().printResult(verifyNumberOfSearchResultDisplayed(1));
			Reporter.getInstance().printCheck("Ensure " + keyword + " appears in the results.", isFluent);
			Reporter.getInstance().printResult(verifyResultContainsWord(keyword));
			keyword = "color wheel";
			Reporter.getInstance().printAction("Search for " + keyword, isFluent);
			this.searchPage.search(keyword);
			Reporter.getInstance().printCheck("Ensure results always display the preferred label like \"Color Chart\" and never \"Color Wheel\", which is a hidden label.", isFluent);
			Reporter.getInstance().printResult(verifyResultContainsWord("Chart, Color"));
			Reporter.getInstance().printResult(verifyResultNotContainsWord(keyword));
		    if (this.searchPage.getLinguisticVariant().equals("Canadian")) {
		    	Reporter.getInstance().printCheck("Ensure Chart, Color (International variant) is not displayed", isFluent);
		    	Reporter.getInstance().printResult(verifyResultNotContainsWord("Chart, Color"));
		     } else if (this.searchPage.getLinguisticVariant().equals("International")) {
		     	Reporter.getInstance().printCheck("Ensure Chart, Colour (Canadian variant) is not displayed", isFluent);
		     	Reporter.getInstance().printResult(verifyResultNotContainsWord("Chart, Colour"));
		    }
		    Reporter.getInstance().printResult(verifyNumberOfSearchResult(0, 3, keyword));
		    Reporter.getInstance().printResult(verifyNumberOfSearchResultDisplayed(1));		
		}
		
	   private void reportTestB(int isFluent) {
		    // Test B- Multiple words search
			Reporter.getInstance().printCheck("Ensure the radio button is to Inverted for Term order and International for spelling preference.",isFluent);
			driver.get(this.searchPage.getUrl() + "?lang=en");
			Reporter.getInstance().printResult(verifyTermOrder("Inverted"));
			Reporter.getInstance().printResult(verifyLinguisticVariant("International"));
			keyword = "phonograph cabinet";
			Reporter.getInstance().printAction("Search for " + keyword , isFluent);	
			this.searchPage.search(keyword);
			Reporter.getInstance().printCheck("Ensure you get one record", isFluent);
			Reporter.getInstance().printResult(verifyNumberOfSearchResult(0, 2, keyword));
			keyword = "cabinet phonograph";
			Reporter.getInstance().printAction("Search for " + keyword , isFluent);	
			this.searchPage.search(keyword);
			Reporter.getInstance().printCheck("Ensure you get one record", isFluent);
			Reporter.getInstance().printResult(verifyNumberOfSearchResult(0, 2, keyword));
		}
		
		private void reportTestC(int isFluent) {
		    // Test C- Wildcard search and word-boundary search
			Reporter.getInstance().printAction("Execute Test C", isFluent);
			driver.get(this.searchPage.getUrl() + "?lang=en");
			if (this.searchPage.getTermOrder() != "Inverted") {
				this.searchPage.changeTermOrder("Inverted");
				this.searchPage.performChangePreferences();
			}
			if (this.searchPage.getLinguisticVariant() != "International") {
				this.searchPage.changeLinguisticVariant("International");
				this.searchPage.performChangePreferences();	
			}
			Reporter.getInstance().printCheck("Ensure the radio button is to Inverted for Term order and International for spelling preference.",isFluent);
			Reporter.getInstance().printResult(verifyTermOrder("Inverted"));
			Reporter.getInstance().printResult(verifyLinguisticVariant("International"));
			keyword = "man";
			Reporter.getInstance().printAction("Search for " + keyword, isFluent);	
			this.searchPage.search(keyword);
			Reporter.getInstance().printCheck("Ensure you get 6 results", isFluent);
			Reporter.getInstance().printResult(verifyNumberOfSearchResult(4, 8, keyword));
			Reporter.getInstance().printCheck("Ensure you get the records with the work man like \"man car\" or \"Hand Hay Rake\" as it contains man in the hidden labels.", isFluent);
			Reporter.getInstance().printResult(verifyResultContainsWord("Car, Man"));
			Reporter.getInstance().printResult(verifyResultContainsWord("Rake, Hand Hay"));
			Reporter.getInstance().printResult(verifyResultNotContainsWord("Mantel"));
			Reporter.getInstance().printResult(verifyResultNotContainsWord("Almanac"));
			Reporter.getInstance().printResult(verifyResultNotContainsWord("Dolman"));	
			keyword = "*man*";
			Reporter.getInstance().printAction("Search for " + keyword, isFluent);	
			this.searchPage.search(keyword);
			Reporter.getInstance().printCheck("Ensure you get 290 results", isFluent);
			Reporter.getInstance().printResult(verifyNumberOfSearchResult(250, 300, keyword));
			Reporter.getInstance().printCheck("Ensure you get the records with the work man like \"man car\" or \"Hand Hay Rake\" as it contains man in the hidden labels.", isFluent);
			Reporter.getInstance().printResult(verifyResultOrder());
			Reporter.getInstance().printResult(verifyResultContainsWord("Mantel")); 
			Reporter.getInstance().printResult(verifyResultContainsWord("Almanac"));
			Reporter.getInstance().printResult(verifyResultContainsWord("Dolman")); // TODO : problem here this element is not on the first page, therefore can't evaluate, we would need to navigate to next page until it finds it which is sophisticated and time consuming.
			keyword = "man*";
			Reporter.getInstance().printAction("Search for " + keyword, isFluent);	
			this.searchPage.search(keyword);
			Reporter.getInstance().printCheck("Ensure you get 196 results", isFluent);
			Reporter.getInstance().printResult(verifyNumberOfSearchResult(175, 225, keyword));
			Reporter.getInstance().printResult(verifyResultOrder());
			Reporter.getInstance().printResult(verifyResultContainsWord("Mantel")); // TODO : problem here this element is not on the first page, therefore can't evaluate, we would need to navigate to next page until it finds it which is sophisticated and time consuming.
			Reporter.getInstance().printResult(verifyResultNotContainsWord("Almanac"));
			Reporter.getInstance().printResult(verifyResultNotContainsWord("Dolman"));	
			keyword = "*man";
			Reporter.getInstance().printAction("Search for " + keyword, isFluent);	
			this.searchPage.search(keyword);
			Reporter.getInstance().printCheck("Ensure you get 29 results", isFluent);
			Reporter.getInstance().printResult(verifyNumberOfSearchResult(10, 50, keyword));
			Reporter.getInstance().printResult(verifyResultOrder());
			Reporter.getInstance().printResult(verifyResultNotContainsWord("Mantel")); // TODO : problem here this elmeent is not on the first page, therefore can't evaluate
			Reporter.getInstance().printResult(verifyResultNotContainsWord("Almanac"));
			Reporter.getInstance().printResult(verifyResultContainsWord("Dolman"));
			keyword = "mant*l";
			Reporter.getInstance().printAction("Search for " + keyword, isFluent);	
			this.searchPage.search(keyword);
			Reporter.getInstance().printCheck("Ensure you get 5 results", isFluent);
			Reporter.getInstance().printResult(verifyNumberOfSearchResult(1, 10, keyword));
			Reporter.getInstance().printCheck("Ensure you get 5 records starting with mant and finishing by l, like mantel or mantel lamp or lambrequin as mantel is in the hidden labels", isFluent);
			// TODO How..?^
		}
		
		private void reportTestD(int isFluent) {
			// Test D- Id search
			driver.get(this.searchPage.getUrl() + "?lang=en");
			Reporter.getInstance().printCheck("Ensure the radio button is to Natural for Term order and International for spelling preference", isFluent);
			this.searchPage.changeLinguisticVariant("International");
			this.searchPage.changeTermOrder("Natural");
			this.searchPage.performChangePreferences();
			Reporter.getInstance().printResult(verifyTermOrder("Natural"));
			Reporter.getInstance().printResult(verifyLinguisticVariant("International"));
			Reporter.getInstance().printAction("Search for \"1228\"", isFluent);
			this.searchPage.search("1228");
			keyword = "Phonograph Cabinet";
			Reporter.getInstance().printCheck("Ensure you get \""+ keyword + "\"", isFluent);
			Reporter.getInstance().printResult(verifyResultContainsWord(keyword));
			Reporter.getInstance().printResult(verifyNumberOfSearchResult(0, 2, "1228"));
			keyword = "02-00171";
			Reporter.getInstance().printAction("Search for \"" + keyword + "\"", isFluent);
			this.searchPage.search(keyword);
			keyword = "Phonograph Cabinet";
			Reporter.getInstance().printCheck("Ensure you get \""+ keyword + "\"", isFluent);
			Reporter.getInstance().printResult(verifyResultContainsWord(keyword));
			Reporter.getInstance().printResult(verifyNumberOfSearchResult(0, 2, "02-00171"));
			Reporter.getInstance().printCheck("Ensure you get the thumbnail image", isFluent);
			// TODO ^: How to implement thumbnail 
		}
		
		private void reportTestE(int isFluent) {
			// Test E- Accents and uppercase search
			Reporter.getInstance().printAction("Execute Test E", isFluent);
			driver.get(this.searchPage.getUrl() + "?lang=en");
			if (this.searchPage.getTermOrder() != "Inverted") {
				this.searchPage.changeTermOrder("Inverted");
				this.searchPage.performChangePreferences();
			}
			if (this.searchPage.getLinguisticVariant() != "International") {
				this.searchPage.changeLinguisticVariant("International");
				this.searchPage.performChangePreferences();	
			}
			driver.get(this.searchPage.getUrl() + "?lang=en");	
			Reporter.getInstance().printCheck("Ensure the radio button is to Inverted for Term order and International for spelling preference.",isFluent);
			Reporter.getInstance().printResult(verifyTermOrder("Inverted"));
			Reporter.getInstance().printResult(verifyLinguisticVariant("International"));
			keyword = "école";
			Reporter.getInstance().printAction("Search for " + keyword, isFluent);
			this.searchPage.search(keyword);
			Reporter.getInstance().printCheck("Ensure you get 6 records", isFluent);
			Reporter.getInstance().printResult(verifyNumberOfSearchResult(1, 10, keyword));
			keyword = "ecole";
			Reporter.getInstance().printAction("Search for " + keyword, isFluent);
			this.searchPage.search(keyword);
			Reporter.getInstance().printCheck("Ensure you get 6 records", isFluent);
			Reporter.getInstance().printResult(verifyNumberOfSearchResult(1, 10, keyword));
			keyword = "École";
			Reporter.getInstance().printAction("Search for " + keyword, isFluent);
			this.searchPage.search(keyword);
			Reporter.getInstance().printCheck("Ensure you get 6 records", isFluent);
			Reporter.getInstance().printResult(verifyNumberOfSearchResult(1, 10, keyword));
			keyword = "Ecole";
			Reporter.getInstance().printAction("Search for " + keyword, isFluent);
			this.searchPage.search(keyword);
			Reporter.getInstance().printCheck("Ensure you get 6 records", isFluent);
			Reporter.getInstance().printResult(verifyNumberOfSearchResult(1, 10, keyword));			
		}
		
		private void reportTestF(int isFluent) {
			// Test F- Diacritics and special characters search
			Reporter.getInstance().printAction("Execute Test F", isFluent);
			driver.get(this.searchPage.getUrl() + "?lang=en");
			if (this.searchPage.getTermOrder() != "Inverted") {
				this.searchPage.changeTermOrder("Inverted");
				this.searchPage.performChangePreferences();
			}
			if (this.searchPage.getLinguisticVariant() != "International") {
				this.searchPage.changeLinguisticVariant("International");
				this.searchPage.performChangePreferences();	
			}
			driver.get(this.searchPage.getUrl() +"?lang=en");
			Reporter.getInstance().printResult(verifyTermOrder("Inverted"));
			Reporter.getInstance().printResult(verifyLinguisticVariant("International"));
			keyword = "oeuf";
			Reporter.getInstance().printAction("Search for " + keyword, isFluent);
			this.searchPage.search(keyword);
			Reporter.getInstance().printCheck("Ensure you get 13 records", isFluent);
			Reporter.getInstance().printResult(verifyNumberOfSearchResult(10, 15, keyword));
			Reporter.getInstance().printResult(verifyResultNotContainsWord("oeufs"));
			Reporter.getInstance().printResult(verifyResultNotContainsWord("boeuf"));
			keyword = "œuf";
			Reporter.getInstance().printAction("Search for " + keyword, isFluent);
			this.searchPage.search(keyword);
			Reporter.getInstance().printCheck("Ensure you get 12 records", isFluent);
			Reporter.getInstance().printResult(verifyNumberOfSearchResult(10, 15, keyword));
			keyword = "t-square";
			Reporter.getInstance().printAction("Search for " + keyword, isFluent);
			this.searchPage.search(keyword);
			Reporter.getInstance().printCheck("Ensure you get 1 records", isFluent);
			Reporter.getInstance().printResult(verifyNumberOfSearchResult(0, 2, keyword));			
		}

		private void reportTestG(int isFluent) {
			// Test G- Small word search
			Reporter.getInstance().printAction("Execute Test G", isFluent);
			driver.get(this.searchPage.getUrl() + "?lang=fr");
			if (this.searchPage.getTermOrder() != "Inversé") {
				this.searchPage.changeTermOrder("Inversé");
				this.searchPage.performChangePreferences();
			}
			if (this.searchPage.getLinguisticVariant() != "International") {
				this.searchPage.changeLinguisticVariant("International");
				this.searchPage.performChangePreferences();	
			}
			driver.get(this.searchPage.getUrl());
			Reporter.getInstance().printResult(verifyTermOrder("Inversé"));
			Reporter.getInstance().printResult(verifyLinguisticVariant("International"));
			keyword = "Map";
			Reporter.getInstance().printAction("Search for " + keyword, isFluent);
			this.searchPage.search(keyword);
			Reporter.getInstance().printCheck("Ensure you get less than 4 records", isFluent);
			Reporter.getInstance().printResult(verifyNumberOfSearchResult(0, 5, keyword));
			keyword = "té";
			Reporter.getInstance().printAction("Search for " + keyword, isFluent);
			this.searchPage.search(keyword);
			Reporter.getInstance().printCheck("Ensure you get less than 3 records", isFluent);
			Reporter.getInstance().printResult(verifyNumberOfSearchResult(0, 3, keyword));
		}
		
		private void reportTestH(int isFluent) {
			// Test H- Order
			driver.get(this.searchPage.getUrl()+"?lang=en");
			Reporter.getInstance().printAction("Execute Test H", isFluent);
			driver.get(this.searchPage.getUrl() + "?lang=en");
			if (this.searchPage.getTermOrder() != "Inverted") {
				this.searchPage.changeTermOrder("Inverted");
				this.searchPage.performChangePreferences();
			}
			if (this.searchPage.getLinguisticVariant() != "International") {
				this.searchPage.changeLinguisticVariant("International");
				this.searchPage.performChangePreferences();	
			}
			Reporter.getInstance().printCheck("Ensure the radio button is to Inverted for Term order and International for spelling preference", isFluent);
			Reporter.getInstance().printResult(verifyTermOrder("Inverted"));
			Reporter.getInstance().printResult(verifyLinguisticVariant("International"));
			keyword = "scale";
			Reporter.getInstance().printAction("Search for " + keyword, isFluent);
			this.searchPage.search(keyword);
			Reporter.getInstance().printResult(verifyResultOrder());
			int resultEn = this.searchPage.getNumberOfSearchResult();
				// Switch to French 
			this.searchPage.toggle("fr");
			keyword = "Échelle";
			Reporter.getInstance().printCheck("Ensure results contains " + keyword, isFluent);
			Reporter.getInstance().printResult(verifyResultContainsWord(keyword));
			Reporter.getInstance().printResult(verifyResultOrder());
			int resultFr = this.searchPage.getNumberOfSearchResult();
			Reporter.getInstance().printResult(verifySameNumberOfResults(resultEn, resultFr));			
		}
		
		private void reportTestI(int isFluent) {
			// Test I- Search - Term preferences
			driver.get(this.searchPage.getUrl() + "?lang=en");
			this.searchPage.changeLinguisticVariant("International");
			this.searchPage.changeTermOrder("Natural");
			this.searchPage.performChangePreferences();
			Reporter.getInstance().printCheck("Ensure the radio button is to Natural for Term order and International for  Linguistic variant preference", isFluent);
			Reporter.getInstance().printResult(verifyTermOrder("Natural"));
			Reporter.getInstance().printResult(verifyLinguisticVariant("International"));
			keyword = "Phonograph Cabinet";
			Reporter.getInstance().printAction("Search for " + keyword, isFluent);
			this.searchPage.search(keyword);
			Reporter.getInstance().printCheck("Ensure there is only one result and its Preferred Term field is Phonograph Cabinet", isFluent);
			Reporter.getInstance().printResult(verifyNumberOfSearchResult(0, 2, keyword));
			Reporter.getInstance().printResult(verifyResultContainsWord(keyword));
			Reporter.getInstance().printAction("Change the radio button to Inverted", isFluent);
			this.searchPage.changeTermOrder("Inverted");
			this.searchPage.performChangePreferences();
			keyword = "Cabinet, Phonograph";
			Reporter.getInstance().printCheck("Ensure the result Preferred Term field becomes " + keyword, isFluent);
			Reporter.getInstance().printResult(verifyResultContainsWord(keyword));
			Reporter.getInstance().printAction("Navigate to " + keyword, isFluent);
			this.searchPage.navigateSearchResult(keyword);
			Reporter.getInstance().printCheck("Ensure Cabinet, Entertainment and Cabinet, Phonograph are contained in the structure tree.", isFluent);
			Reporter.getInstance().printResult(verifyContainsWord("Cabinet, Entertainment"));
			Reporter.getInstance().printResult(verifyContainsWord("Cabinet, Phonograph"));
			Reporter.getInstance().printAction("Switch to Natural term order", isFluent);
			this.searchPage.changeTermOrder("Natural");
			this.searchPage.performChangePreferences();
			Reporter.getInstance().printCheck("Ensure Entertainment Cabinet and Phonograph Cabinet are contained in the structure tree.", isFluent);
			Reporter.getInstance().printResult(verifyContainsWord("Entertainment Cabinet"));
			Reporter.getInstance().printResult(verifyContainsWord("Phonograph Cabinet"));
		}
		
		private void reportTestJ(int isFluent) {	
		    // Test J- Search - Spelling preferences
			driver.get(this.searchPage.getUrl() + "?lang=en");
			if (this.searchPage.getTermOrder() != "Inverted") {
				this.searchPage.changeTermOrder("Inverted");
				this.searchPage.performChangePreferences();
			}
			if (this.searchPage.getLinguisticVariant() != "International") {
				this.searchPage.changeLinguisticVariant("International");
				this.searchPage.performChangePreferences();	
			}
			Reporter.getInstance().printCheck("Ensure the radio button is to Inverted for Term order and International for spelling preference", isFluent);
			Reporter.getInstance().printResult(verifyTermOrder("Inverted"));
			Reporter.getInstance().printResult(verifyLinguisticVariant("International"));
			keyword = "Godendard";
			Reporter.getInstance().printAction("Search for " + keyword, isFluent);
			this.searchPage.search(keyword);
			keyword = "Saw, Two-Handed Crosscut";
			Reporter.getInstance().printCheck("Ensure search give one result, and its Preferred Term shows " + keyword, isFluent);
			Reporter.getInstance().printResult(verifyNumberOfSearchResult(0, 2, keyword));
			Reporter.getInstance().printResult(verifyResultContainsWord(keyword));
			Reporter.getInstance().printAction("Change the radio button Linguistic Variant to Canadian", isFluent);
			this.searchPage.changeLinguisticVariant("Canadian");
			this.searchPage.performChangePreferences();
			keyword = "Godendard";
			Reporter.getInstance().printAction("Search for " + keyword, isFluent);
			this.searchPage.search(keyword);
			keyword = "Saw, Two-Handed Crosscut";
			Reporter.getInstance().printCheck("Ensure search give one result, and its Preferred Term shows " + keyword, isFluent);
			Reporter.getInstance().printResult(verifyNumberOfSearchResult(0, 2, keyword));
			Reporter.getInstance().printResult(verifyResultContainsWord(keyword));
			Reporter.getInstance().printAction("Change into French Interface", isFluent);
			this.searchPage.toggle("fr");
			keyword = "Godendard";
			Reporter.getInstance().printCheck("Ensure search give one result, and its Preferred Term shows " + keyword, isFluent);
			Reporter.getInstance().printResult(verifyNumberOfSearchResult(0, 2, keyword));
			Reporter.getInstance().printResult(verifyResultContainsWord(keyword));
		}
		
		private void reportTestK(int isFluent) {
			// Test K- Search - Alphabets
			driver.get(this.searchPage.getUrl() + "?lang=en");
			Reporter.getInstance().printAction("Search by letter A", isFluent);
			this.searchPage.searchLetter("A");
			Reporter.getInstance().printCheck("Ensure all Preferred Term are displayed in inverted term (If it has more than two terms, comma is used for separation)", isFluent);
			// TODO: implement isResultInvertedTermOrder() Can't check if they are all displayed in Inverted term or not, because Categories are not displayed in Inverted terms.
		}
		
		private void reportTestL(int isFluent) {
			// Test L- Search - Detail link
			driver.get(this.searchPage.getUrl() + "?lang=en");
			Reporter.getInstance().printCheck("Ensure the radio button is to Natural for Term order and International for spelling preference", isFluent);
			this.searchPage.changeTermOrder("Natural");
			this.searchPage.performChangePreferences();
			Reporter.getInstance().printCheck("Ensure the radio button is to Normal for Term order and International for spelling preference", isFluent);
			Reporter.getInstance().printResult(verifyTermOrder("Natural"));
			Reporter.getInstance().printResult(verifyLinguisticVariant("International"));
			keyword = "Phonograph";
			Reporter.getInstance().printAction("Search for " + keyword, isFluent);
			this.searchPage.search(keyword);
			this.searchPage.navigateSearchResult("Phonograph Cabinet");
			Reporter.getInstance().printCheck("Ensure the detail appears like from the browse page", isFluent);
			back();
			this.searchPage.navigateSearchResult("Entertainment Cabinet");
			Reporter.getInstance().printCheck("Ensure the detail appears like from the browse page", isFluent);
			back();
			this.searchPage.navigateSearchResult("Phonograph");
			Reporter.getInstance().printCheck("Ensure the detail appears like from the browse page", isFluent);
			back();
			this.searchPage.navigateSearchResult("Phonograph");
			Reporter.getInstance().printCheck("Ensure the detail appears like from the browse page", isFluent);
			back();
			// TODO: Testing scenario non sense... how to ensure the detail appears like from the browse page?			
		}
		
		private void reportTestM(int isFluent) {
			// Test M- Search - input validation	
			driver.get(this.searchPage.getUrl() + "?lang=en");
			Reporter.getInstance().printCheck("Ensure you have validation error when you enter search keyword contains more than 5 words and longer than 100 characters", isFluent);
			Reporter.getInstance().printResult(verifyContainsWord("Validation error ??")); 
			// TODO: What is the validation error?		
		}
		
		private void reportTestN(int isFluent) {

			// Test N- Autosuggestion TODO 
		    Reporter.getInstance().printInfo("Testing the autosuggest starts only after 3 letters");
		    
				//Bilingual/all orders/all languages list (testing in all language preferences)
		    Reporter.getInstance().printInfo("Testing Bilingual/All orders/All languages list");
		    Reporter.getInstance().printInfo("--------------------");		    
		    // FR, CA, NA
		    Reporter.getInstance().printAction("Go to the search with French UI, canadian language, natural order", isFluent);
			driver.get(this.searchPage.getUrl() + "?lang=fr");
			this.searchPage.changeTermOrder("natural");
			this.searchPage.changeLinguisticVariant("canadian");
			this.searchPage.performChangePreferences();
			
			keyword = "monn";
			String[] contains = new String[] {"Échangeur de monnaie"};
			String[] notContains = new String[]{"Money Changer", "Moneychanger"};
			Reporter.getInstance().printAction("Enter the keyword : " + keyword, isFluent);      
			this.searchPage.input(keyword);
			Reporter.getInstance().printCheck("Ensure the autosuggest list contains at least " + Arrays.toString(contains), isFluent);
			Reporter.getInstance().printResult(verifySuggestionsConstains(this.searchPage.returnSuggestions(), contains));
			Reporter.getInstance().printCheck("Ensure the autosuggest list does NOT contains " + Arrays.toString(notContains), isFluent);
			Reporter.getInstance().printResult(verifySuggestionsNotContains(this.searchPage.returnSuggestions(), notContains));
			refresh();
			
			keyword = "moneych";
			contains = new String[] {"Échangeur de monnaie"};
			notContains = new String[]{"Money Changer", "Moneychanger"};
		    Reporter.getInstance().printAction("Enter the keyword : " + keyword, isFluent);      
			this.searchPage.input(keyword);
			Reporter.getInstance().printCheck("Ensure the autosuggest list contains at least " + Arrays.toString(contains), isFluent);
			Reporter.getInstance().printResult(verifySuggestionsConstains(this.searchPage.returnSuggestions(), contains));
			Reporter.getInstance().printCheck("Ensure the autosuggest list does NOT contains " + Arrays.toString(notContains), isFluent);
			Reporter.getInstance().printResult(verifySuggestionsNotContains(this.searchPage.returnSuggestions(), notContains));
			refresh();
			
			keyword = "chang";
			contains = new String[] {"Échangeur de monnaie"};
			notContains = new String[]{"Money Changer", "Moneychanger"};
			Reporter.getInstance().printAction("Enter the keyword : " + keyword, isFluent);      
			this.searchPage.input(keyword);
			Reporter.getInstance().printCheck("Ensure the autosuggest list contains at least " + Arrays.toString(contains), isFluent);
			Reporter.getInstance().printResult(verifySuggestionsConstains(this.searchPage.returnSuggestions(), contains));
			Reporter.getInstance().printCheck("Ensure the autosuggest list does NOT contains " + Arrays.toString(notContains), isFluent);
			Reporter.getInstance().printResult(verifySuggestionsNotContains(this.searchPage.returnSuggestions(), notContains));
			refresh();
			
			keyword = "aceto";
			contains = new String[] {"Pèse-acide"};
			Reporter.getInstance().printAction("Enter the keyword : " + keyword, isFluent);      
			this.searchPage.input(keyword);
			Reporter.getInstance().printCheck("Ensure the autosuggest list contains at least " + Arrays.toString(contains), isFluent);
		    Reporter.getInstance().printResult(verifySuggestionsConstains(this.searchPage.returnSuggestions(), contains));
			refresh();
			
			// EN, CA, NA
			Reporter.getInstance().printAction("Change for English UI, canadian language, natural order", isFluent);
			this.searchPage.toggle("en");
			keyword = "monn";
			contains = new String[] {"Money Changer"};
			notContains = new String[]{"échangeur de monnaie", "Moneychanger", "Changer, Money"};
			Reporter.getInstance().printAction("Enter the keyword : " + keyword, isFluent);      
			this.searchPage.input(keyword);
			Reporter.getInstance().printCheck("Ensure the autosuggest list contains at least " + Arrays.toString(contains), isFluent);
			Reporter.getInstance().printResult(verifySuggestionsConstains(this.searchPage.returnSuggestions(), contains));
			Reporter.getInstance().printCheck("Ensure the autosuggest list does NOT contains " + Arrays.toString(notContains), isFluent);
			Reporter.getInstance().printResult(verifySuggestionsNotContains(this.searchPage.returnSuggestions(), notContains));
			refresh();
			
			keyword = "moneych";
			contains = new String[] {"Money Changer"};
			notContains = new String[]{"échangeur de monnaie", "Moneychanger", "Changer, Money"};
			Reporter.getInstance().printAction("Enter the keyword : " + keyword, isFluent);      
			this.searchPage.input(keyword);
			Reporter.getInstance().printCheck("Ensure the autosuggest list contains at least " + Arrays.toString(contains), isFluent);
			Reporter.getInstance().printResult(verifySuggestionsConstains(this.searchPage.returnSuggestions(), contains));
			Reporter.getInstance().printCheck("Ensure the autosuggest list does NOT contains " + Arrays.toString(notContains), isFluent);
			Reporter.getInstance().printResult(verifySuggestionsNotContains(this.searchPage.returnSuggestions(), notContains));
			refresh();
			
			keyword = "chang";
			contains = new String[] {"Money Changer"};
			notContains = new String[]{"échangeur de monnaie", "Moneychanger", "Changer, Money"};
			Reporter.getInstance().printAction("Enter the keyword : " + keyword, isFluent);      
			this.searchPage.input(keyword);
			Reporter.getInstance().printCheck("Ensure the autosuggest list contains at least " + Arrays.toString(contains), isFluent);
			Reporter.getInstance().printResult(verifySuggestionsConstains(this.searchPage.returnSuggestions(), contains));
			Reporter.getInstance().printCheck("Ensure the autosuggest list does NOT contains " + Arrays.toString(notContains), isFluent);
			Reporter.getInstance().printResult(verifySuggestionsNotContains(this.searchPage.returnSuggestions(), notContains));
			refresh();
			
			keyword = "duelling";
			contains = new String[] {"Duelling Pistol"};
			notContains = new String[]{"Dueling Pistol", "Pistol, Duelling"};
			Reporter.getInstance().printAction("Enter the keyword : " + keyword, isFluent);      
			this.searchPage.input(keyword);
			Reporter.getInstance().printCheck("Ensure the autosuggest list contains at least " + Arrays.toString(contains), isFluent);
		    Reporter.getInstance().printResult(verifySuggestionsConstains(this.searchPage.returnSuggestions(), contains));
		    Reporter.getInstance().printCheck("Ensure the autosuggest list does NOT contains " + Arrays.toString(notContains), isFluent);
		    Reporter.getInstance().printResult(verifySuggestionsNotContains(this.searchPage.returnSuggestions(), notContains));
			refresh();
			
			
			keyword = "dueling";
			contains = new String[] {"Duelling Pistol"};
			notContains = new String[]{"Dueling Pistol", "Pistol, Duelling"};
			Reporter.getInstance().printAction("Enter the keyword : " + keyword, isFluent);      
			this.searchPage.input(keyword);
			Reporter.getInstance().printCheck("Ensure the autosuggest list contains at least " + Arrays.toString(contains), isFluent);
		    Reporter.getInstance().printResult(verifySuggestionsConstains(this.searchPage.returnSuggestions(), contains));
		    Reporter.getInstance().printCheck("Ensure the autosuggest list does NOT contains " + Arrays.toString(notContains), isFluent);
		    Reporter.getInstance().printResult(verifySuggestionsNotContains(this.searchPage.returnSuggestions(), notContains));
			refresh();
			
			// EN, CA, INV
			Reporter.getInstance().printAction("Change for English UI, canadian language, inverted order", isFluent);
			this.searchPage.changeTermOrder("inverted");
			this.searchPage.performChangePreferences();
			keyword = "monn";
			contains = new String[] {"Changer, Money"};
			notContains = new String[]{"échangeur de monnaie", "Moneychanger", "Money Changer"};
			Reporter.getInstance().printAction("Enter the keyword : " + keyword, isFluent);      
			this.searchPage.input(keyword);
			Reporter.getInstance().printCheck("Ensure the autosuggest list contains at least " + Arrays.toString(contains), isFluent);
			Reporter.getInstance().printResult(verifySuggestionsConstains(this.searchPage.returnSuggestions(), contains));
			Reporter.getInstance().printCheck("Ensure the autosuggest list does NOT contains " + Arrays.toString(notContains), isFluent);
			Reporter.getInstance().printResult(verifySuggestionsNotContains(this.searchPage.returnSuggestions(), notContains));
			refresh();
			
			keyword = "moneych";
			contains = new String[] {"Changer, Money"};
			notContains = new String[]{"échangeur de monnaie", "Moneychanger", "Money Changer"};
			Reporter.getInstance().printAction("Enter the keyword : " + keyword, isFluent);      
			this.searchPage.input(keyword);
			Reporter.getInstance().printCheck("Ensure the autosuggest list contains at least " + Arrays.toString(contains), isFluent);
			Reporter.getInstance().printResult(verifySuggestionsConstains(this.searchPage.returnSuggestions(), contains));
			Reporter.getInstance().printCheck("Ensure the autosuggest list does NOT contains " + Arrays.toString(notContains), isFluent);
			Reporter.getInstance().printResult(verifySuggestionsNotContains(this.searchPage.returnSuggestions(), notContains));
			refresh();
			
			keyword = "chang";
			contains = new String[] {"Changer, Money"};
			notContains = new String[]{"échangeur de monnaie", "Moneychanger", "Money Changer"};
			Reporter.getInstance().printAction("Enter the keyword : " + keyword, isFluent);      
			this.searchPage.input(keyword);
			Reporter.getInstance().printCheck("Ensure the autosuggest list contains at least " + Arrays.toString(contains), isFluent);
			Reporter.getInstance().printResult(verifySuggestionsConstains(this.searchPage.returnSuggestions(), contains));
			Reporter.getInstance().printCheck("Ensure the autosuggest list does NOT contains " + Arrays.toString(notContains), isFluent);
			Reporter.getInstance().printResult(verifySuggestionsNotContains(this.searchPage.returnSuggestions(), notContains));
			refresh();
			
			keyword = "chang";
			contains = new String[] {"Changer, Money"};
			notContains = new String[]{"échangeur de monnaie", "Moneychanger", "Money Changer"};
			Reporter.getInstance().printAction("Enter the keyword : " + keyword, isFluent);      
			this.searchPage.input(keyword);
			Reporter.getInstance().printCheck("Ensure the autosuggest list contains at least " + Arrays.toString(contains), isFluent);
			Reporter.getInstance().printResult(verifySuggestionsConstains(this.searchPage.returnSuggestions(), contains));
			Reporter.getInstance().printCheck("Ensure the autosuggest list does NOT contains " + Arrays.toString(notContains), isFluent);
			Reporter.getInstance().printResult(verifySuggestionsNotContains(this.searchPage.returnSuggestions(), notContains));
			refresh();
			
			keyword = "money chan";
			contains = new String[] {"Changer, Money"};
			Reporter.getInstance().printAction("Enter the keyword : " + keyword, isFluent);      
			this.searchPage.input(keyword);
			Reporter.getInstance().printCheck("Ensure the autosuggest list contains at least " + Arrays.toString(contains), isFluent);
			Reporter.getInstance().printResult(verifySuggestionsConstains(this.searchPage.returnSuggestions(), contains));
		    String[] words = {"Changer", "Money"};
		    Reporter.getInstance().printCheck("Ensure the autosuggest list contains words " + Arrays.toString(words), isFluent);
		    Reporter.getInstance().printResult(verifyAllSuggestionsContainsWords(this.searchPage.returnSuggestions(), words)); 
			refresh();
			
			keyword = "changer mon";
			contains = new String[] {"Changer, Money"};
			Reporter.getInstance().printAction("Enter the keyword : " + keyword, isFluent);      
			this.searchPage.input(keyword);
			Reporter.getInstance().printCheck("Ensure the autosuggest list contains at least " + Arrays.toString(contains), isFluent);
			Reporter.getInstance().printResult(verifySuggestionsConstains(this.searchPage.returnSuggestions(), contains));
			Reporter.getInstance().printCheck("Ensure the autosuggest list contains words " + Arrays.toString(words), isFluent);
			Reporter.getInstance().printResult(verifyAllSuggestionsContainsWords(this.searchPage.returnSuggestions(), words)); 
			refresh();
			
			keyword = "col";
			contains = new String[] {"Chart, Colour", "Filter, Colour Correcting"};
			Reporter.getInstance().printAction("Enter the keyword : " + keyword, isFluent);      
			this.searchPage.input(keyword);
			Reporter.getInstance().printCheck("Ensure the autosuggest list contains at least " + Arrays.toString(contains), isFluent);
			Reporter.getInstance().printResult(verifySuggestionsConstains(this.searchPage.returnSuggestions(), contains)); 
			refresh();
			
			keyword = "color";
			contains = new String[] {"Chart, Colour", "Filter, Colour Correcting"};
			Reporter.getInstance().printAction("Enter the keyword : " + keyword, isFluent);      
		    this.searchPage.input(keyword);
		    Reporter.getInstance().printCheck("Ensure the autosuggest list contains at least " + Arrays.toString(contains), isFluent);
		    Reporter.getInstance().printResult(verifySuggestionsConstains(this.searchPage.returnSuggestions(), contains)); 
			refresh();
			
			keyword = "colour";
			contains = new String[] {"Chart, Colour", "Filter, Colour Correcting"};
			Reporter.getInstance().printAction("Enter the keyword : " + keyword, isFluent);      
			this.searchPage.input(keyword);
			Reporter.getInstance().printCheck("Ensure the autosuggest list contains at least " + Arrays.toString(contains), isFluent);
			Reporter.getInstance().printResult(verifySuggestionsConstains(this.searchPage.returnSuggestions(), contains)); 
			refresh();
			
			keyword = "Colour Corre";
			contains = new String[] {"Filter, Colour Correcting"};
			Reporter.getInstance().printAction("Enter the keyword : " + keyword, isFluent);      
			this.searchPage.input(keyword);
			Reporter.getInstance().printCheck("Ensure the autosuggest list contains at least " + Arrays.toString(contains), isFluent);
			Reporter.getInstance().printResult(verifySuggestionsConstains(this.searchPage.returnSuggestions(), contains)); 
			refresh();
			
			keyword = "Colour Correcting Filt";
			contains = new String[] {"Filter, Colour Correcting"};
			Reporter.getInstance().printAction("Enter the keyword : " + keyword, isFluent);      
			this.searchPage.input(keyword);
			Reporter.getInstance().printCheck("Ensure the autosuggest list contains at least " + Arrays.toString(contains), isFluent);
			Reporter.getInstance().printResult(verifySuggestionsConstains(this.searchPage.returnSuggestions(), contains)); 
			refresh();
			
			keyword = "Colour Filt";
			contains = new String[] {"Filter, Colour Correcting"};
			Reporter.getInstance().printAction("Enter the keyword : " + keyword, isFluent);      
			this.searchPage.input(keyword);
			Reporter.getInstance().printCheck("Ensure the autosuggest list contains at least " + Arrays.toString(contains), isFluent);
		    Reporter.getInstance().printResult(verifySuggestionsConstains(this.searchPage.returnSuggestions(), contains)); 
			refresh();
				
			keyword = "duelling";
			contains = new String[] {"Pistol, Duelling"};
			notContains = new String[]{"Pistol Dueling", "Duelling Pistol"};
			Reporter.getInstance().printAction("Enter the keyword : " + keyword, isFluent);      
			this.searchPage.input(keyword);
			Reporter.getInstance().printCheck("Ensure the autosuggest list contains at least " + Arrays.toString(contains), isFluent);
			Reporter.getInstance().printResult(verifySuggestionsConstains(this.searchPage.returnSuggestions(), contains));
			Reporter.getInstance().printCheck("Ensure the autosuggest list does NOT contains " + Arrays.toString(notContains), isFluent);
			Reporter.getInstance().printResult(verifySuggestionsNotContains(this.searchPage.returnSuggestions(), notContains));
			refresh();
				
			keyword = "dueling";
			contains = new String[] {"Pistol, Duelling"};
			notContains = new String[]{"Pistol Dueling", "Duelling Pistol"};
			Reporter.getInstance().printAction("Enter the keyword : " + keyword, isFluent);      
			this.searchPage.input(keyword);
			Reporter.getInstance().printCheck("Ensure the autosuggest list contains at least " + Arrays.toString(contains), isFluent);
			Reporter.getInstance().printResult(verifySuggestionsConstains(this.searchPage.returnSuggestions(), contains));
			Reporter.getInstance().printCheck("Ensure the autosuggest list does NOT contains " + Arrays.toString(notContains), isFluent);
			Reporter.getInstance().printResult(verifySuggestionsNotContains(this.searchPage.returnSuggestions(), notContains));
			refresh();		
			
			// Language
			driver.get(this.searchPage.getUrl() +"?lang=en");
			keyword = "echarp";
			Reporter.getInstance().printAction("Enter the keyword : " + keyword, isFluent);      
			this.searchPage.input(keyword);
			Reporter.getInstance().printResult(keyword  + " has " + this.searchPage.returnSuggestions().size() + " results in " + NomenclatureUtil.getLanguage() + " on " + TestUtil.currentTime());
			this.searchPage.toggle("fr");	
			this.searchPage.input(keyword);
			Reporter.getInstance().printResult(keyword  + " has " + this.searchPage.returnSuggestions().size() + " results in " + NomenclatureUtil.getLanguage() + " on " + TestUtil.currentTime());
			
			// TODO: Automatic search, not implemented yet in DEV  
			
			// No autosuggestion
			refresh();
		    keyword = "cellphone";
		    Reporter.getInstance().printAction("Enter the keyword : " + keyword, isFluent);      
		    this.searchPage.input(keyword);
		    Reporter.getInstance().printCheck("Ensure the autosuggest list is empty", isFluent);
		    Reporter.getInstance().printResult(verifyNoSuggestions(keyword));
			this.searchPage.search(keyword);
			Reporter.getInstance().printResult(verifyNumberOfSearchResult(0, 0, keyword));
			keyword = "te";
			Reporter.getInstance().printAction("Enter the keyword : " + keyword, isFluent);      
		    this.searchPage.input(keyword);
		    Reporter.getInstance().printCheck("Ensure the autosuggest list does NOT contains anything as the keyword contains less than 3 letters", isFluent);
		    Reporter.getInstance().printResult(verifyNoSuggestions(keyword));
		    this.searchPage.search(keyword);
		    Reporter.getInstance().printResult(verifyNumberOfSearchResult(1, 10000, keyword));				
		}
		
	public static void main(String[] args) {	
		
	    // Creating an instance of SearchPageReporter 
	    SearchPageReporter searchPageReporter = new SearchPageReporter();
  	    
		// Setting test run mode, if isFluent == 1, the test runs in "Break" mode, else it runs in "Fluent" mode  
		int isFluent = searchPageReporter.searchPage.getUserConfiguration().getIsFluent();
		
		// Get testing url from SearchPage		
		String url = searchPageReporter.searchPage.getUrl();
	    
		// Get an instance of Reporter
        Reporter reporter = Reporter.getInstance();
		
		// Testing time
        reporter.printInfo("Testing time: " + TestUtil.currentTime());
        
        // Current testing URL
		reporter.printInfo("Testing executed on URL: " + driver.getCurrentUrl());
		
        // Creating a string to store search keywords 	
		String searchedKeyword = "";
		 
		// Search Page
		reporter.printInfo("\n");
		reporter.printInfo("************");
		reporter.printInfo("Search Page");
		reporter.printInfo("************");
		reporter.printInfo("\n");
		reporter.printAction("Navigating to: " + url, isFluent);
		
		/* Tests */
		searchPageReporter.reportSearchPageInitialTest(isFluent);
		searchPageReporter.reportTestA(isFluent);
		searchPageReporter.reportTestB(isFluent);
		searchPageReporter.reportTestC(isFluent);
		searchPageReporter.reportTestD(isFluent);
		searchPageReporter.reportTestE(isFluent);
		searchPageReporter.reportTestF(isFluent);
		searchPageReporter.reportTestG(isFluent);
		searchPageReporter.reportTestH(isFluent);
		searchPageReporter.reportTestI(isFluent);
		searchPageReporter.reportTestJ(isFluent);
		searchPageReporter.reportTestK(isFluent);
		searchPageReporter.reportTestL(isFluent);
		searchPageReporter.reportTestM(isFluent);
		searchPageReporter.reportTestN(isFluent);		
		
	    // Closing driver after tests are done
		tearDown();
	    
		// Print out any error
	    reporter.printInfo("\nGlobal result of this testing page");
	    reporter.printInfo("--------------------");       
	    if (searchPageReporter.searchPage.getbError()) {
			System.out.println("Please verify for errors (ERROR)");
		} else {
			System.out.println("No error found");
		}  
	    
	    reporter.printTesterReport(isFluent);
		
		}	
}
