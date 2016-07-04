package HomePageLoadTime;

import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import util.Constants;
import util.Keywords;
import util.Utility;
import util.Xls_Reader;
import util.testCaseDataProvider;

public class testHomeARTHomePageLoading extends TestSuiteBase{
	@Test(dataProviderClass=testCaseDataProvider.class,dataProvider="getDataForPageLoading")
    public void testHomeARTHomePageLoading(Hashtable<String,String> data) throws Throwable{
    			Xls_Reader xls=new Xls_Reader(Constants.PATH_XLS_HOMEPAGELOADTIME);
    			Utility.validateTestExecution("testHomeARTHomePageLoading",data.get(Constants.RUNMODE_COL),data.get(Constants.ITERATION_COL), xls);
    			Keywords app=new Keywords("testHomeARTHomePageLoading");
    			Logger log=Utility.intiLogs("testHomeARTHomePageLoading_"+data.get(Constants.ITERATION_COL));
    			app.setLogger(log);
    			app.executeKeywords("testHomeARTHomePageLoading", xls, data);
    	        log.debug("Test Case - testHomeARTHomePageLoading");  
    	        
    }

}
