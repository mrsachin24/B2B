package B2B_PageLoadTime;

import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import util.Constants;
import util.Keywords;
import util.Utility;
import util.Xls_Reader;
import util.testCaseDataProvider;

public class testSKGLoginLogout extends TestSuiteBase{
	@Test(dataProviderClass=testCaseDataProvider.class,dataProvider="getDataForHomePageLoading")
    public void testSKGLoginLogout(Hashtable<String,String> data) throws Throwable{
    			Xls_Reader xls=new Xls_Reader(Constants.PATH_XLS_LOADINGPAGE);
    			Utility.validateTestExecution("testSKGLoginLogout",data.get(Constants.RUNMODE_COL),data.get(Constants.ITERATION_COL), xls);
    			Keywords app=new Keywords("testSKGLoginLogout");
    			Logger log=Utility.intiLogs("testSKGLoginLogout_"+data.get(Constants.ITERATION_COL));
    			app.setLogger(log);
    			app.executeKeywords("testSKGLoginLogout", xls, data);
    	        log.debug("Test Case - testSKGLoginLogout");  
    	        
    }



}
