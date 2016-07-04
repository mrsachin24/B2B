package HomePageLoadTime;
import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;


import util.Utility;

public class TestSuiteBase {

	@BeforeSuite
	public void checkSuitRun(){
		System.out.println("checking checkSuitRun ="+Utility.isSuiteRunnable("HomePageLoadTime") );
		Utility.creatingXlsReportFolder("HomePageLoadTime");
		if(!Utility.isSuiteRunnable("HomePageLoadTime")){
			System.out.println("Inside checkSuitRun");
			Utility.isSuiteRunnableReport("HomePageLoadTime");
    	throw	new SkipException("Skipping the testsuite as runmmode is set to ");
    	}
		else{
			System.out.println("Running the testCase");
		}
	}
}
