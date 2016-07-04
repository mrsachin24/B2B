package util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.Hashtable;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.testng.SkipException;

import com.relevantcodes.extentreports.ExtentReports;

public class Utility {


    public static Object[][]  getData(String testCase,Xls_Reader xls){
        // find the rowNum for the test
        int testCaseRowNum=1;
        while(!xls.getCellData(Constants.TESTDATA_SHEET, 0, testCaseRowNum).equalsIgnoreCase(testCase)){
            testCaseRowNum++;
        }
        //System.out.println(testCaseRowNum);

        // row for Column and Data
        int colStartRowNum=testCaseRowNum+1;
        int dataStartRowNum=testCaseRowNum+2;
        int rows=0;
        // total rows of data in the test
        while(!xls.getCellData(Constants.TESTDATA_SHEET, 0, dataStartRowNum+rows).trim().equals("")){
            rows++;
        }
        //System.out.println("Total rows "+ rows);
        
        //total cols
        int cols=0;
        while(!xls.getCellData(Constants.TESTDATA_SHEET, cols, colStartRowNum).trim().equals("")){
            cols++;
        }
        //System.out.println("Total cols "+ cols);
        // print the data
        Object[][] testData = new Object[rows][1];
        int i =0;
        for(int rNum=dataStartRowNum;rNum<dataStartRowNum+rows;rNum++){
            Hashtable<String,String> table = new Hashtable<String,String> ();
            
            //if Result and Error col not applicable
            for(int cNum=0;cNum<cols;cNum++){
            //for(int cNum=0;cNum<cols-2;cNum++){
                String data = xls.getCellData(Constants.TESTDATA_SHEET, cNum, rNum);
                String colName = xls.getCellData(Constants.TESTDATA_SHEET, cNum, colStartRowNum);

                //	System.out.println(colName+" --- "+data);
                table.put(colName, data);
            }
            //put the hashtable in object Array
            testData[i][0]=table;
            i++;
            //	System.out.println("------------------------------------");
        }
        return testData;
    }
  //***************************************************************************
    
  //***************************************************************************
  	public static void isTCRunnableReport(String testcaseName,Xls_Reader xls_tc){
  		
  		int TCRow=1;
  		while(!xls_tc.getCellData(Constants.TESTDATA_SHEET,0, TCRow).equalsIgnoreCase(testcaseName)){
  			System.out.println("*********"+xls_tc.getCellData(Constants.TESTDATA_SHEET,0, TCRow)+"*********");
  			TCRow++;
  			
  		}
  		System.out.println("TC Found row number = "+TCRow);
  		
  		int resultCol=0;
  		while(!xls_tc.getCellData(Constants.TESTDATA_SHEET,resultCol,TCRow+1).equalsIgnoreCase(Constants.RESULT_COL)){
  			resultCol++;
  		}
  		System.out.println("result col numer = "+resultCol);
  		int testDatastartrow=TCRow+2;
  		int totalData=0;
  		while(!xls_tc.getCellData(Constants.TESTDATA_SHEET,0,testDatastartrow+totalData).equalsIgnoreCase("")){
  			totalData++;
  		}
  		System.out.println("Total test Data row = "+totalData);
  		
  		for(int itr=testDatastartrow;itr<testDatastartrow+totalData;itr++){
  			xls_tc.setResultCellDatawithColor(Constants.TESTDATA_SHEET,resultCol, itr, "SKIPPED");
  			xls_tc.setCellData(Constants.TESTDATA_SHEET,resultCol+1,itr,Constants.TC_SKIPMESSAGE);
  		}
  		

  		
  	}
      //***************************************************************************

 		
 
 		
 		
 		
  //***************************************************************************	
    public static boolean isSuiteRunnable(String suiteName) {
    	System.out.println("Inside isSuiteRunnable");
        Xls_Reader xls = new Xls_Reader(Constants.PATH_XLS_SUITE);
        int rows = xls.getRowCount(Constants.TESTSUITE_SHEET);
        for(int rNum=2;rNum<=rows;rNum++){
            String testSuiteName=xls.getCellData(Constants.TESTSUITE_SHEET, Constants.SUITENAME_COL, rNum);
           // System.out.println("is suite Runnable "+testSuiteName.toLowerCase() + suiteName.toLowerCase() );
            if(testSuiteName.toLowerCase().equals(suiteName.toLowerCase())){
                String runMode=xls.getCellData(Constants.TESTSUITE_SHEET, Constants.RUNMODE_COL, rNum);
                if(runMode.equalsIgnoreCase(Constants.RUNMODE_YES)){
                	System.out.println("Returning true");
                    return true;}
                else
                {System.out.println("Returning false");
                    return false;}
            }


        }
        return false;
    }
    
    

    public static boolean isTestCaseRunnable(String testCaseName,Xls_Reader xls) {
        int rows = xls.getRowCount(Constants.TESTCASES_SHEET);

        for(int rNum=2;rNum<=rows;rNum++){
            String testName=xls.getCellData(Constants.TESTCASES_SHEET, Constants.TESTCASENAME_COL, rNum);
            if(testName.toLowerCase().equals(testCaseName.toLowerCase())){
                String runMode=xls.getCellData(Constants.TESTCASES_SHEET, Constants.RUNMODE_COL, rNum);
                if(runMode.equals(Constants.RUNMODE_YES))
                    return true;
                else
                	
                	 return false;
            }

        }
        return false;

    }
    
    public static Logger intiLogs(String append){
    	FileAppender appender=new FileAppender();
    	appender.setFile(Constants.PATH_LOGGER_START+append+Constants.PATH_LOGGER_END);
    	appender.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"));
		appender.setAppend(false);
		appender.activateOptions();
		Logger APPLICATION_LOG = Logger.getLogger(append);
		APPLICATION_LOG.setLevel(Level.DEBUG);
		APPLICATION_LOG.addAppender(appender);
		return APPLICATION_LOG;
    }

    public static void validateTestExecution(String testCaseName,String dataRunmode,String Itration,Xls_Reader xls) {
    	
    	 	if(!isTestCaseRunnable(testCaseName,xls)){
    	 		isTCRunnableReport(testCaseName, xls);
throw new SkipException("Skipping the test "+testCaseName+" as runmode of test was NO" );
    	}

    	if(dataRunmode.equals(Constants.RUNMODE_NO)){
    		xls.checkRunmode(testCaseName,"Results",Itration);
    		//xls.setResultCellDatawithColor(Constants.TESTDATA_SHEET,xls.getCellRowNum(Constants.TESTDATA_SHEET,"Results", cellValue), rowNum, Result)
    		throw new SkipException("Skipping the test "+testCaseName+" as runmode of data set was NO" );
    	}

}	
    
    
    public static void creatingXlsReportFolder(String testCaseName) {
		String files;
			    
			    
			    File dir = new File(Constants.RESULT_DIRNAME);
			    if(dir.exists()){
			    	System.out.println("Folder Already Exits");
			    	File[] listOfFiles = dir.listFiles(); 
			        for (int i = 0; i < listOfFiles.length; i++) 
			        {
			            if (listOfFiles[i].isFile()) 
			            {
			                files = listOfFiles[i].getName();
			                //files.split(".",2);
			                if(files.equalsIgnoreCase(testCaseName+".xlsx")){
			                	System.out.println("xlsx extenstion = "+files);
			                	boolean issuccess=new File(listOfFiles[i].toString()).delete();
			                    System.err.println("Deletion Success "+issuccess);
			                }
			              /*  
			               if(!files.equalsIgnoreCase("test1.txt"))
			               {
			                  boolean issuccess=new File(listOfFiles[i].toString()).delete();
			                  System.err.println("Deletion Success "+issuccess);
			              }
			              */
			            }
			        }
			    }
			    else{
			    	 dir.mkdirs();
			    	 System.out.println("New directory created !!!");
			    }
			    copyFileUsingJava7Files(testCaseName);
			
			}

			
			
			
			
			
			public static void copyFileUsingJava7Files(String FileName) {
				System.out.println("Inside Copy function");
				File FileSource = new File(Constants.SOURCE_DIRNAME+FileName+".xlsx");
				File FileDest = new File(Constants.RESULT_DIRNAME+FileName+".xlsx");
				
				      try {
						Files.copy(FileSource.toPath(), FileDest.toPath());
						System.out.println("File copied");
					} catch (IOException e) {
						
						e.printStackTrace();
					}
			
			    }

  
			public static void isSuiteRunnableReport(String testCaaseName){
								
					Xls_Reader xls_TC=new Xls_Reader(Constants.RESULT_DIRNAME+testCaaseName+".xlsx");
					int TotaltestcaseRow=xls_TC.getRowCount(Constants.TESTCASES_SHEET);
					System.out.println("Constants.TESTCASES_SHEET = "+TotaltestcaseRow);
					for(int testcaselist=2;testcaselist<=TotaltestcaseRow;testcaselist++){
						
						String testCaseName=xls_TC.getCellData(Constants.TESTCASES_SHEET,0, testcaselist);
						//System.out.println(testCaseName);
						int TDSheet_curruntRow=1;
						while(!xls_TC.getCellData(Constants.TESTDATA_SHEET,0,TDSheet_curruntRow).equalsIgnoreCase(testCaseName)){
							TDSheet_curruntRow++;
						}
						System.out.println(testCaseName+"======="+TDSheet_curruntRow);
						int resultcellnumber=0;
						while(!xls_TC.getCellData(Constants.TESTDATA_SHEET,resultcellnumber,TDSheet_curruntRow+1).equalsIgnoreCase(Constants.RESULT_COL)){
							resultcellnumber++;
						}
						System.out.println("Results cell number = "+resultcellnumber);
						int testDataStartrow=TDSheet_curruntRow+2;
						int numberTestData=0;
						while(!xls_TC.getCellData(Constants.TESTDATA_SHEET,0,testDataStartrow+numberTestData).equalsIgnoreCase("")){
							numberTestData++;
						}
						System.out.println("Number of TestData = "+numberTestData);
						
						for(int rnum=testDataStartrow;rnum<testDataStartrow+numberTestData;rnum++ ){
							System.out.println("Row No = "+rnum+"*****"+xls_TC.getCellData(Constants.TESTDATA_SHEET, 0,rnum ));
							xls_TC.setResultCellDatawithColor(Constants.TESTDATA_SHEET, resultcellnumber, rnum, "SKIPPED");
							xls_TC.setCellData(Constants.TESTDATA_SHEET,resultcellnumber+1,rnum,Constants.TS_SKIPMESSAGE);
						}
					}
								
			} 
    
    

}
