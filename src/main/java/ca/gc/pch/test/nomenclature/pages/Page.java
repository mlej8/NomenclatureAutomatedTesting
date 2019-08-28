package ca.gc.pch.test.nomenclature.pages;

import ca.gc.pch.test.nomenclature.base.TestBase;
import ca.gc.pch.test.nomenclature.config.UserConfiguration;

public abstract class Page extends TestBase {
	
	protected String url;
	protected boolean bError = false; // Variable is assigned to true every time an error is caught
	protected UserConfiguration userConfigurations; // protected: same package and subclasses
	
	public void initializeUserConfigurations() {
		userConfigurations = new UserConfiguration();
		userConfigurations.configureTestEnvironment();
		userConfigurations.configureTestMode();
	}
	
	public UserConfiguration getUserConfiguration() {
		return this.userConfigurations;
	}
}	
