package ca.gc.pch.test.nomenclature.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ca.gc.pch.test.nomenclature.base.TestBase;
import ca.gc.pch.test.nomenclature.pages.BrowsePage;
import ca.gc.pch.test.nomenclature.util.NomenclatureUtil;

public class BrowsePageTest extends TestBase {
	
	BrowsePage browsePage;
		
	@BeforeMethod
	public void setUp() {
		initialization(); // initialize driver, implicit wait, actions, etc. We are using a fresh new Browser for every test cases in order to avoid having cached too many cookies or browser crashing/dying due to running out of memory or other reasons  
		browsePage = new BrowsePage(); // initialize object of BrowsePage class
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
	
	@Test(priority=4, groups="Default Parameter")
	public void browsePageDefaultLinguisticVariantTest() {
		String linguisticVariant = browsePage.getLinguisticVariant();
		Assert.assertEquals(linguisticVariant, "International");
	}
	
	@Test(priority=5, groups="Structure Tree Order")
	public void categoriesOrder() {
		Assert.assertTrue(browsePage.isCategoryOrdered());
	}
	
	@Test(priority=6, groups="Structure Tree Order")
	public void subTreeOrder() {
		browsePage.navigateCategory("Catégorie 01",false);
		boolean order = browsePage.isSubTreeOrdered();
		Assert.assertTrue(order);
	}
	
	@AfterMethod
	public void cleanUp() {
		tearDown();
	}
}
