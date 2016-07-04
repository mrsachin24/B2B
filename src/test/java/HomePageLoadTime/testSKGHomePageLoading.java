package HomePageLoadTime;

import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import util.Constants;
import util.Keywords;
import util.Utility;
import util.Xls_Reader;
import util.testCaseDataProvider;

public class testSKGHomePageLoading extends TestSuiteBase{
	


	@Test(dataProviderClass=testCaseDataProvider.class,dataProvider="getDataForPageLoading")
    public void testSKGHomePageLoading(Hashtable<String,String> data) throws Throwable{
    			Xls_Reader xls=new Xls_Reader(Constants.PATH_XLS_HOMEPAGELOADTIME);
    			Utility.validateTestExecution("testSKGHomePageLoading",data.get(Constants.RUNMODE_COL),data.get(Constants.ITERATION_COL), xls);
    			Keywords app=new Keywords("testSKGHomePageLoading");
    			Logger log=Utility.intiLogs("testSKGHomePageLoading_"+data.get(Constants.ITERATION_COL));
    			app.setLogger(log);
    			app.executeKeywords("testSKGHomePageLoading", xls, data);
    	        log.debug("Test Case - testSKGHomePageLoading");  
    	        
    }


}
