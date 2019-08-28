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
		initialization(); // initialize driver, implicit wait, actions, etc.
		browsePage = new BrowsePage(); // initialize object of BrowsePage class
	}
	
	@Test(priority=1)
	public void browsePageTitle() {
		String title = browsePage.validateBrowsePageTitle();
		Assert.assertEquals(title, "Parcourir la hiérarchie - Nomenclature");
	}
	
	@Test(priority=2)
	public void browsePageDefaultLanguage() {
		String language = NomenclatureUtil.getLanguage();
		Assert.assertEquals(language, "Français");
	}
	
	@Test(priority=3)
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
