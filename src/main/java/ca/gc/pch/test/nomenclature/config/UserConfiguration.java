package ca.gc.pch.test.nomenclature.config;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

public class UserConfiguration {
	/* This class contains the UserConfigurations variables */
	private String testEnv = null; // stores the environment 
	private int isFluent = -1;     // stored the run mode 
	
	public UserConfiguration(){}
	
	public void configureTestEnvironment() {
		/* Environment */
		// Creating environment Map if it is null
		if (this.testEnv == null) {
		String[] environment = {"DEV", "STG", "PROD"};
		String[] urls = {"https://dev.", "https://stg.", "https://www."};
		Map<String, String> envMap = new HashMap<String,String>();
		for(int i = 0; i < urls.length; i ++) {
			envMap.put(environment[i], urls[i]);
		}
		
		// Choosing test environment
		int index = JOptionPane.showOptionDialog(null,
				"Choose your test environment", 
				"Testing environment", 
				JOptionPane.DEFAULT_OPTION, 
				JOptionPane.INFORMATION_MESSAGE, 
				null, 
				environment, 
				environment[0]);
		
		// Get URL associated with testing environment
		this.testEnv = (envMap.get(environment[index]));
		}
	}
	
	
	public void configureTestMode() {
		if (this.isFluent == -1) {

	// Creating options to chose how testing is ran
	String[] options = {"Fluent", "Break"};   

	// If Fluent is clicked, the value of isFluent will be 0, if Break is choosen, then the value of isFluent will be 1
	this.isFluent = JOptionPane.showOptionDialog(null, 												   // Component parentComponent
			"Click \"Fluent\" to run test without interruption or click \"Break\" to run with breaks", // Object message  
			"Testing options",													  					   // String title 
			JOptionPane.YES_NO_OPTION,																   // int optionType				
			JOptionPane.INFORMATION_MESSAGE,														   // int messageType
			null,																					   // Icon icon
			options,																				   // Object[] options
			options[0]);
		}
	}

	public int getIsFluent() {
		return isFluent;
	}

	public void setIsFluent(int isFluent) {
		this.isFluent = isFluent;
	}

	public String getTestEnv() {
		return testEnv;
	}
	
	public void setTestEnv(String testEnv) {
		testEnv = testEnv.toUpperCase();
		String[] environment = {"DEV", "STG", "PROD"};
		String[] urls = {"https://dev.", "https://stg.", "https://www."};
		Map<String, String> envMap = new HashMap<String,String>();
		for(int i = 0; i < urls.length; i ++) {
			envMap.put(environment[i], urls[i]);
		}
		this.testEnv = envMap.get(testEnv);
	}
}
