package ca.gc.pch.test.nomenclature.util;

import javax.swing.JOptionPane;

import ca.gc.pch.test.nomenclature.base.TestBase;

public class Reporter {
	/**
	 * This class implements the Singleton pattern 
	 * It serves as a tool to format the output of the testing scenario into a report
	 * A singleton class is a class that can have only one object (an instance of the class) at a time.
	 */
    	
    int idAction = 1;
    boolean foundError = false;
    private static Reporter reporter = null;
    
    private Reporter() {}
    
    public static Reporter getInstance() {
    	if (reporter == null) {
    		reporter = new Reporter();
    	}
    	return reporter;
    }
    
    public boolean getFoundError() {
    	return reporter.foundError;
    }  
    
    public void printInfo(String message) {
    	/**
    	 * Print message in the log
    	 */
        System.out.println(message);
    }

    public void printInfo(String message, int isFluent, boolean isAction) {
    	/**
    	 * Print in the log and pop up the UI
    	 */
    	if (isAction && isFluent == 1) {
    		printInfo(message);
    		JOptionPane.showMessageDialog(null, message);
    	} else if(!isAction && isFluent == 1 ) {
        	String[] options = {"OK", "Cancel"}; // creating options for tester  
        	
        	// If OK is clicked, the value of testerAnswer will be 0, if Cancel is choosen, then the value of testerAnswer will be 1
        	int testerAnswer = JOptionPane.showOptionDialog(null, 												  					   		    
        			message,
        			"Manual verification options",
        			JOptionPane.YES_NO_OPTION,																
        			JOptionPane.INFORMATION_MESSAGE,														
        			null,																					
        			options,																				
        			options[0]);						
            if (testerAnswer==0) {
            		printInfo(message + ": Tester OK");
            } else {
                printInfo(message + ": Tester Cancel");
                this.reporter.foundError = true;                
            }
        } else {
        	printInfo(message);
        	}    	
    }
    
    //print actions
    public void printAction(String message, int isFluent) {
        message = "  " + reporter.idAction + ") Step => " + message;
        printInfo(message, isFluent, true);
        idAction = reporter.idAction + 1;
    }
    
    //print Driver action
    public void printDriverAction(String message) {
    	message = "         Driver => " + message;
    	printInfo(message);
    }
    
    //print error
    public void printError(String message) {
    	message = "           PROGRAM ERROR => " + message;
    	printInfo(message);
    }

    //print actions
    public void printAction(String message, int isFluent, int indexAction) {
    	reporter.idAction = indexAction;
        printAction(message, isFluent);
    }

    //print check
    public void printCheck(String message, int isFluent) {
        message = "       Check ==> " + message;
        printInfo(message, isFluent, false);
    }

    //print result
    public void printResult(String message) {
        message = "           Result ===> " + message;
        printInfo(message);
    }
    
    //print tester report
    public void printTesterReport(int isFluent) {
    	if (isFluent == 1) {
    	if (getFoundError()) {
    		System.out.println("Tester found error(s), please search for \"Tester Cancel\" in the report.");    		
    	} else {
    		System.out.println("Test found NO error");
    		}
    	}
    }
}
