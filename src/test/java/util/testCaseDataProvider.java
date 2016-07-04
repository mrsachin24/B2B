package util;

import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;

/**
 * Created by Sachin on 25-01-2016.
 */
public class testCaseDataProvider{
	@DataProvider(name="getDataForHomePageLoading")
    public static Object[][] getDataForHomePageLoading(Method m){
        //System.out.println(m.getName());
        String testCase=m.getName();
        Xls_Reader xls = new Xls_Reader(Constants.PATH_XLS_LOADINGPAGE);
        return Utility.getData(testCase,xls);
    }
	
	@DataProvider(name="getDataForPageLoading")
    public static Object[][] getDataForPageLoading(Method m){
        //System.out.println(m.getName());
        String testCase=m.getName();
        Xls_Reader xls = new Xls_Reader(Constants.PATH_XLS_HOMEPAGELOADTIME);
        return Utility.getData(testCase,xls);
    }
	
}
