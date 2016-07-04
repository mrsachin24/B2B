package HomePageLoadTime;

import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import util.Constants;
import util.Keywords;
import util.Utility;
import util.Xls_Reader;
import util.testCaseDataProvider;

public class testKohinoorHomePageLoading extends TestSuiteBase{

	@Test(dataProviderClass=testCaseDataProvider.class,dataProvider="getDataForPageLoading")
    public void testKohinoorHomePageLoading(Hashtable<String,String> data) throws Throwable{
    			Xls_Reader xls=new Xls_Reader(Constants.PATH_XLS_HOMEPAGELOADTIME);
    			Utility.validateTestExecution("testKohinoorHomePageLoading",data.get(Constants.RUNMODE_COL),data.get(Constants.ITERATION_COL), xls);
    			Keywords app=new Keywords("testKohinoorHomePageLoading");
    			Logger log=Utility.intiLogs("testKohinoorHomePageLoading_"+data.get(Constants.ITERATION_COL));
    			app.setLogger(log);
    			app.executeKeywords("testKohinoorHomePageLoading", xls, data);
    	        log.debug("Test Case - testKohinoorHomePageLoading");  
    	        
    }
}
