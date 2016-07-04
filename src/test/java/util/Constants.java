package util;

/**
 * Created by Sachin on 25-01-2016.
 */
public class Constants {

	
public static final String URL_TESTFOYRHOMEPAGELOADING="http://foyrv3stag.cloudapp.net/";	
public static final String SOURCE_DIRNAME = System.getProperty("user.dir") + "//Data//";
public static final String RESULT_DIRNAME = System.getProperty("user.dir") + "//Results//XlsReports//";
	
public static final String PATH_XLS_SUITE=Constants.SOURCE_DIRNAME+"TestSuite.xlsx";
public static final String PATH_XLS_LOADINGPAGE = Constants.RESULT_DIRNAME+"B2B_PageLoadTime.xlsx";
public static final String PATH_XLS_HOMEPAGELOADTIME = Constants.RESULT_DIRNAME+"HomePageLoadTime.xlsx";



public static final String PROPERTIES_FILE_PATH = System.getProperty("user.dir")+"//Data//project.properties";
public static final String SCREENSHOT_PATH = System.getProperty("user.dir")+"//Results//Screenshots//";
public static final String XLS_RESULT_DIRECTORY = System.getProperty("user.dir")+"//Results//reports//";
public static final String PATH_LOGGER_START = System.getProperty("user.dir")+"//Results//Logs//";
public static final String PATH_LOGGER_END = ".log"; 





 // sheet names
 public static final String TESTSUITE_SHEET = "TestSuite";
 public static final String TESTDATA_SHEET = "TestData";
 public static final String TESTCASES_SHEET = "TestCases";
 public static final String KEYWORDS_SHEET = "Keywords";

 // col names
 public static final String SUITENAME_COL = "SuiteName";
 public static final String RUNMODE_COL = "Runmode";
 public static final String TESTCASENAME_COL = "TestCaseName";
 public static final String BROWSER_COL = "Browser";;
 public static final String PORTFOLIONAME_COL = "PortFolioName";
 public static final String CASE_COL = "Case";
 public static final String Data_COL = "Data";
 public static final String STOCKNAME_COL = "StockName";
 public static final String QUANTITY_COL = "Quantity";
 public static final String ACTION_COL = "Action";

//Test Data Headers
 public static final String DATA_USEREMAIL = "Email";
 public static final String DATA_PASSWORD = "Password";
 public static final String DATA_LOGINTYPE = "LoginType";
 public static final String DATA_DEFAULT = "default";
 public static final String DATA_CASETYPE = "Data_CaseType";
 
 
 public static final String Element_True="true";
 public static final String Element_False="false";

 public static final String PASS = "PASS";
 public static final String RUNMODE_YES ="Y" ;
 public static final String KEYWORD_RESULT_PASS="PASS";
 public static final String TD_RESULT_PASS="PASS";
 public static final String KEYWORD_RESULT_FAIL="FAIL";
 public static final String TD_RESULT_FAIL="FAIL";

 
 
 //error message
	public static final String GENERAL_ERROR = "ERROR - FAILED KEYWORD - ";
	public static final String OPENBROWSER_ERROR = "ERROR - FAILED TO OPEN BROWSER - ";
	public static final String NAVIGATE_ERROR = "ERROR - FAILING TO NAVIGATE - ";
	public static final String LOCATOR_ERROR = "ERROR - INVALID LOCATOR - ";
	public static final String FIND_ELEMENT_ERROR = "ERROR - UNABLE TO FIND ELEMET -  ";
	public static final String MSG_ALERT = "Wrong Email-id or Password";
	
	
	
	// failure
	public static final String ELEMENT_NOT_FOUND_FAILURE = "FAIL - ELEMENT NOT FOUND - ";
	public static final String TITLE_NOT_MATCHES_FAILURE = "FAIL - Titles do not match. Expected -  ";
	public static final String DEFAULT_LOGIN_FAILURE = "FAIL - Not able to Login with Deault Username/password ";
	public static final String CLICKANDWAIT_FAILURE = "FAIL - Could not click and wait - ";
	public static final String PORTFOLIONAMENOTPRESENT_FAILURE = "FAIL - Portfolio name not present  - ";
	public static final String DUPLICATE_FAILURE = "FAIL - Duplicate Element expected but not found";
	public static final String AJAX_COMAPNY_ERR = "FAIL - Could not select the company name - ";
	public static final String ADD_NEW_STOCK_FAILURE = "FAIL - Stock name not entered in table ";
	public static final String STOCK_NOT_PRESENT_FAILURE = "FAIL - Stock name not present  - ";
	public static final String TRANSACTION_QUANTITY_FAILURE = "FAIL - Transaction Quantities do not match ";
	
	//Database testing
	public static final String dbUrl = "jdbc:mysql://localhost:3036/emp";
	//public static final Object ITERTION_COL = "Iteration";
	public static final Object RUNMODE_NO = "N";
	public static final String MOZILLA = "mozilla";
	public static final String CHROME = "chrome";
	public static final String IE = "ie";
	public static final String SAFARI = "safari";
	public static final String ITERATION_COL = "Iteration";
	
	
	public static final String INT_1 = "Integer1";
	public static final String INT_2 = "Integer2";
	public static final String OPERATION = "Operation";
	public static final String INT_FINAL="FinalInt";
	public static final String LOGS_COL = "Logs";
	public static final String SCREENSHOTS_COL = "Screenshots";
	public static final String RESULT_COL = "Results";
	public static final String TC_SKIPMESSAGE = "SKIPPING the Test as Runmode of test is set to NO";
	public static final String TS_SKIPMESSAGE = "Skipping the Test as Runmode of Test Suite is set to No";
	public static final String TextVerification_PASS = "Verification of Text is PASSED = ";
	public static final String TextVerification_FAIL = "Verification of Text is FAILED = ";
	public static final String PROJECT_NAME = "name";
	public static final Object PROJECT_LOCATION = "location";
	public static final Object PROJECT_DWELLING_TYPE = "dwelling_type";
	public static final Object PROJECT_UNITTYPE = "unit_types";
	public static final Object PROJECT_DESCRIPTION = "description";
	public static final String ERROR_MSG_ADMINCONSOLE = "ERROR_MSG_ADMINCONSOLE";
	public static final String ERROR_MSG_SIGNUPSCREEN = "ERROR_MSG_SIGNUPSCREEN";
	public static final String ERROR_MSG_ADMINCONSOLE_ADDUSERSCREEN = "ERROR_MSG_ADMINCONSOLE_ADDUSERSCREEN";
	public static final String ERROR_MSG_ADMINCONSOLE_ADDDEVELOPERSCREEN = "ERROR_MSG_ADMINCONSOLE_ADDDEVELOPERSCREEN";
	public static final String ERROR_MSG_ADMINCONSOLE_ADDAMENITIESSCREEN = "ERROR_MSG_ADMINCONSOLE_ADDAMENITIESSCREEN";
	public static final String ERROR_MSG_HOMESCREEN = "ERROR_MSG_HOMESCREEN";
	public static final String BHK_TYPE = "BHK_Type";
	public static final String DATA_NAVIGATIONPATH = "Data_ProjectPath";
	public static final String DATA_UNITTYPES = "Data_UnitTypes";
	public static final String DATAPROJECTPATH = "Data_ProjectPath";
	public static final String SHEET_UNITTYPE = "UNITTYPES";
	public static final String DATABHKTYPES = "BHK_Type";
	public static final String ERROR_MSG_SELECTVERIFICATIONBHK = "ERROR_MSG_SELECTVERIFICATIONBHK";
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static String result_FolderName="Results";
	
	
	
	
}
