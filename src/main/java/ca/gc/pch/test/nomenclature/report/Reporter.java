package ca.gc.pch.test.nomenclature.report;

import javax.swing.JOptionPane;

import ca.gc.pch.test.nomenclature.base.TestBase;

public abstract class Reporter extends TestBase {
    	
    int idAction = 1;
    boolean foundError = false;
    
    public Reporter() {
    }
    
    public boolean getFoundError() {
    	return this.foundError;
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
                this.foundError = true;                
            }
        } else {
        	printInfo(message);
        	}    	
    }
    
    //print actions
    public void printAction(String message, int isFluent) {
        message = "  " + this.idAction + ") Step => " + message;
        printInfo(message, isFluent, true);
        idAction = this.idAction + 1;
    }

    //print actions
    public void printAction(String message, int isFluent, int indexAction) {
    	this.idAction = indexAction;
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
