package ca.gc.pch.test.nomenclature.ExtentReportListener;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentReporterNG implements IReporter { //implementing IReporter Interface from TestNG
	
	/** 
	 * ExtentReporterNG is a Listener Class that will listen to the execution of each and every test case.
	 * This class is a Standard Template provided for Extent Report
	 * 
	 	IT IS VERY IMPORTANT TO ADD THE LISTENER BELOW IN testng.xml
	 	<listeners>
			<listener class-name= "ca.gc.pch.test.nomenclature.ExtentReportListener.ExtentReporterNG" />
		</listeners>
		This listener is included in testng.xml and will listen to all test classes in ca.gc.pch.test.nomenclature.testcases
	 */

	private ExtentReports extent;

	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		
		// When we run testng.xml an extent report called "NomenclatureUITest.html" will be generated in the test-output folder
		extent = new ExtentReports(outputDirectory + File.separator + "NomenclatureUITest.html", true); 
									// ^ means test-output directory
		
		for (ISuite suite : suites) {
			Map<String, ISuiteResult> result = suite.getResults(); // getting all testing results 

			for (ISuiteResult r : result.values()) {
				ITestContext context = r.getTestContext();

				// fetching pass, fail, skip tests
				buildTestNodes(context.getPassedTests(), LogStatus.PASS); 
				buildTestNodes(context.getFailedTests(), LogStatus.FAIL);
				buildTestNodes(context.getSkippedTests(), LogStatus.SKIP);
			}
		}

		extent.flush();
		extent.close();
	}

	private void buildTestNodes(IResultMap tests, LogStatus status) {
		ExtentTest test;

		if (tests.size() > 0) {
			for (ITestResult result : tests.getAllResults()) {
				test = extent.startTest(result.getMethod().getMethodName());

				test.setStartedTime(getTime(result.getStartMillis()));
				test.setEndedTime(getTime(result.getEndMillis()));

				for (String group : result.getMethod().getGroups())
					test.assignCategory(group);

				if (result.getThrowable() != null) {
					test.log(status, result.getThrowable());
				} else {
					test.log(status, "Test " + status.toString().toLowerCase() + "ed"); // log test passed or failed 
				}

				extent.endTest(test);
			}
		}
	}

	private Date getTime(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}
}
