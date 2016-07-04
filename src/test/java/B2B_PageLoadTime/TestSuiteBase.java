package B2B_PageLoadTime;
import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;


import util.Utility;

public class TestSuiteBase {

	@BeforeSuite
	public void checkSuitRun(){
		System.out.println("checking checkSuitRun ="+Utility.isSuiteRunnable("B2B_PageLoadTime") );
		Utility.creatingXlsReportFolder("B2B_PageLoadTime");
		if(!Utility.isSuiteRunnable("B2B_PageLoadTime")){
			System.out.println("Inside checkSuitRun");
			Utility.isSuiteRunnableReport("B2B_PageLoadTime");
    	throw	new SkipException("Skipping the testsuite as runmmode is set to ");
    	}
		else{
			System.out.println("Running the testCase");
		}
	}
}
