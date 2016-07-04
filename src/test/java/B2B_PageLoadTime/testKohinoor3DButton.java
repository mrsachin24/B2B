package B2B_PageLoadTime;

import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import util.Constants;
import util.Keywords;
import util.Utility;
import util.Xls_Reader;
import util.testCaseDataProvider;

public class testKohinoor3DButton extends TestSuiteBase {
	@Test(dataProviderClass=testCaseDataProvider.class,dataProvider="getDataForHomePageLoading")
    public void testKohinoor3DButton(Hashtable<String,String> data) throws Throwable{
    			Xls_Reader xls=new Xls_Reader(Constants.PATH_XLS_LOADINGPAGE);
    			Utility.validateTestExecution("testKohinoor3DButton",data.get(Constants.RUNMODE_COL),data.get(Constants.ITERATION_COL), xls);
    			Keywords app=new Keywords("testKohinoor3DButton");
    			Logger log=Utility.intiLogs("testKohinoor3DButton_"+data.get(Constants.ITERATION_COL));
    			app.setLogger(log);
    			app.executeKeywords("testKohinoor3DButton", xls, data);
    	        log.debug("Test Case - testKohinoor3DButton");         
    }
}
