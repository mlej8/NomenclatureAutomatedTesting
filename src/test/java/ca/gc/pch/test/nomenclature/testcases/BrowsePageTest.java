package ca.gc.pch.test.nomenclature.testcases;

import static org.testng.Assert.assertNotNull;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ca.gc.pch.test.nomenclature.base.TestBase;
import ca.gc.pch.test.nomenclature.pages.BrowsePage;
import ca.gc.pch.test.nomenclature.util.LinkVerifier;
import ca.gc.pch.test.nomenclature.util.NomenclatureUtil;

public class BrowsePageTest extends TestBase {
	
	BrowsePage browsePage;
	String testURL = "https://dev.nomenclature.info/parcourir-browse.app?";
	int subTreeIndex = 1;      
	LinkVerifier linkVerifier = new LinkVerifier(); // Create a LinkVerifier Object
	
	@BeforeMethod
	public void setUp() {		
		/*
		 * The constructor of BrowsePage calls the initialization method from TestBase Class which initializes driver, implicit wait, actions, etc. 
		 * We are using a fresh new Browser for every test cases in order to avoid having cached too many cookies or browser crashing/dying due to running out of memory or other reasons
		 */
		browsePage = new BrowsePage(testURL); // initialize object of BrowsePage class.		
	}
	
	// Test cases are all INDEPENDENT from each other.
	@Test(priority=1, groups="Title")
	public void browsePageTitleTest() {
		String title = browsePage.validateBrowsePageTitle();
		Assert.assertEquals(title, "Parcourir la hiérarchie - Nomenclature");
	}
	
	@Test(priority=2, groups="Default Parameter")
	public void browsePageDefaultLanguageTest() {
		String language = NomenclatureUtil.getLanguage();
		Assert.assertEquals(language, "Français");
	}
	
	@Test(priority=3, groups="Default Parameter", dependsOnMethods="browsePageDefaultLanguageTest")
	public void browsePageDefaultTermOrderTest() {
		String termOrder = browsePage.getTermOrder();
		Assert.assertEquals(termOrder, "Inversé");
	}
	
	@Test(priority=4, groups="Default Parameter", dependsOnMethods="browsePageDefaultLanguageTest")
	public void browsePageDefaultLinguisticVariantTest() {
		String linguisticVariant = browsePage.getLinguisticVariant();
		Assert.assertEquals(linguisticVariant, "International");
	}
	
	@Test(priority=5, groups="Structure Tree Order")
	public void categoriesOrder() {
		/* Test the order of the categories - First Level */
		Assert.assertTrue(browsePage.isCategoryOrdered());
	}
	
	@Test(priority=6, groups="Structure Tree Order")
	public void subTreeOrder() {
		/* Navigate Nomenclature Structure Tree and verify every subTree order */
		browsePage.navigateCategory("Catégorie 01",false);
		boolean order = browsePage.isSubTreeOrdered();
		System.out.println();
		
		// Ensure the selected term appears in the tree (left) and in the detail (right) is the same
		Assert.assertTrue(browsePage.getSelectedTerm().contains(browsePage.getHeaderText()));
		// Ensure subTree is ordered
		Assert.assertTrue(order);
		
		// Navigate subTree until there's no more node
		// TODO: This test is a bit long and very complex.... This is an integration test and should not be written as an unit test 
		//		while(browsePage.navigateChild(subTreeIndex) != null) {			
		//		}		
	}
	
	@Test(groups="Language Toggle")
	public void languageToggleENTest() {
		/* Test English language toggle */
		browsePage.toggle("en");
		Assert.assertEquals(NomenclatureUtil.getLanguage(), "English");
		Assert.assertEquals(browsePage.getTermOrder(), "Inverted");
		Assert.assertEquals(browsePage.getLinguisticVariant(), "International");
	}
	
	@Test(groups="Language Toggle")
	public void languageToggleFRTest() {
		/* Test toggles in English, then toggles back in French */
		browsePage.toggle("en");
		browsePage.toggle("fr");
		Assert.assertEquals(NomenclatureUtil.getLanguage(), "Français");
		Assert.assertEquals(browsePage.getTermOrder(), "Inversé");
		Assert.assertEquals(browsePage.getLinguisticVariant(), "International");
	}
	
	@Test(groups="Sorting")
	public void sortingWithAccentTest() {
		// Testing sorting with accents in Storage & Display Furniture
		driver.get(testURL + "id=1215");
		Assert.assertTrue(browsePage.isSubTreeOrdered());
	}
	
	@Test(groups="Chair")
	public void chairFRTest() {
		driver.get(testURL + "id=1090");
		
		// Ensure the selected term in the structure tree == the header term in the right detail pane.		
		Assert.assertTrue(browsePage.getSelectedTerm().equals(browsePage.getHeaderText()));
		// Ensure sub-categories are ordered alpahabetically ascending including accents
		Assert.assertTrue(browsePage.isSubTreeOrdered());		
		// Ensure the page contains the word Chair
		Assert.assertTrue(NomenclatureUtil.containsWord("Chair"));
		// Ensure the page containing the word 'Chair' is tag lang in English as we are in a French page
        Assert.assertTrue(browsePage.hasSpan("en","Chair")); 
        // Ensure the page contains the word 'f.'
        Assert.assertTrue(NomenclatureUtil.containsWord("f."));
        // Ensure the page contains the word "Siège à une seule place"
        Assert.assertTrue(NomenclatureUtil.containsWord("Siège à une seule place"));
        // Ensure the page contains the word "Le Dictionnaire descriptif et visuel d'objets de Parcs Canada"
    	Assert.assertTrue(NomenclatureUtil.containsWord("Le Dictionnaire descriptif et visuel d'objets de Parcs Canada"));
        // Ensure the page contains the word "Silla"
        Assert.assertTrue(NomenclatureUtil.containsWord("Silla"));
        // Ensure the page contains the word "1978-2010"
        Assert.assertTrue(NomenclatureUtil.containsWord("1978-2010"));
        // Ensure the page contains the word "02-00147"
        Assert.assertTrue(NomenclatureUtil.containsWord("1978-2010"));
	}
	
	@Test(groups="Chair")
	public void chairENTest() {
		driver.get(testURL + "lang=en&id=1090");
		
		// Ensure the selected term in the structure tree == the header term in the right detail pane.		
		Assert.assertTrue(browsePage.getSelectedTerm().equals(browsePage.getHeaderText()));
		// Ensure sub-categories are ordered alpahabetically ascending including accents
		Assert.assertTrue(browsePage.isSubTreeOrdered());	
		// Ensure the page contains the word "Chaise"
		Assert.assertTrue(NomenclatureUtil.containsWord("Chaise"));
        // Ensure the page contains the word 'Chaise' is tag lang in French as we are in an English page", isFluent);
        Assert.assertTrue(browsePage.hasSpan("fr","Chaise"));        
        // Ensure the page contains the word "f."
        Assert.assertTrue(NomenclatureUtil.containsWord("f."));
        // Ensure the page contains the word "A movable seat with a back"
        Assert.assertTrue(NomenclatureUtil.containsWord("A movable seat with a back"));
        // Ensure the page contains the word 'Parks Canada Descriptive and Visual Dictionary of Objects'
        Assert.assertTrue(NomenclatureUtil.containsWord("Parks Canada Descriptive and Visual Dictionary of Objects"));
        // Ensure the page contains the word 'Silla'
        Assert.assertTrue(NomenclatureUtil.containsWord("Silla"));
        // Ensure the page contains the word '1978-2010'
        Assert.assertTrue(NomenclatureUtil.containsWord("1978-2010"));
        // Ensure the page contains the word '02-00147'
        Assert.assertTrue(NomenclatureUtil.containsWord("02-00147"));	
	}
	
	@Test(groups="Chair")
	public void chairThumbNailTest() {
	    /* image and thumbnail test in FR */
        driver.get(testURL + "id=1090");
		
        //Ensure the thumbnail image appears", isFluent);		
        Assert.assertNotNull(browsePage.getThumbnail());
        
        // Click on thumbnail
        browsePage.clickThumbnail();
       
        // Ensure the full image appears and the alt and caption is Chaise
        Assert.assertTrue(browsePage.isThumbnailClicked("Chaise"));		  // verify that the thumbnail is displayed after being clicked on and its alt attribute contains "Chaise"
        // Ensure ThumbnailCaption contains "Chaise"
        Assert.assertNotNull(browsePage.getThumbNailCaption("Chaise"));   // verify that the thumbnail caption contains "Chaise"
  
	}
	
	@Test(groups = "Links", dependsOnMethods="languageToggleFRTest")
	public void browsePageLinksTest() {   
        driver.get(testURL + "id=1090&lang=en");
        
        // Verify all the links in English
        Assert.assertFalse(linkVerifier.hasBrokenLinks(), "ERROR: Found Broken Links"); 
   
	}
	
	@Test(groups = "Links")
	public void browsePageFRLinksTest() {
	    driver.get(testURL + "id=1090"); 
		
		// Verify all the links in French
        Assert.assertFalse(linkVerifier.hasBrokenLinks(), "ERROR: Found Broken Links");
	}
	
	@Test(groups="Term Preferences")
	public void termPreferencesTest() {
		// TODO
	}
	
	@AfterMethod
	public void cleanUp() {
		tearDown();
	}
}
