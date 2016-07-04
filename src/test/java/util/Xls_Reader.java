package util;





import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;


public class Xls_Reader {
	public static String filename = System.getProperty("user.dir")+"\\src\\config\\testcases\\TestData.xlsx";
	public  String path;
	public  FileInputStream fis = null;
	public  FileOutputStream fileOut =null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row   =null;
	private XSSFCell cell = null;
	
	public Xls_Reader(String path) {
		
		this.path=path;
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			//sheet = workbook.getSheetAt(0);
			fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	// returns the row count in a sheet
	public int getRowCount(String sheetName){
		int index = workbook.getSheetIndex(sheetName);
		if(index==-1)
			return 0;
		else{
		sheet = workbook.getSheetAt(index);
		int number=sheet.getLastRowNum()+1;
		return number;
		}
		
	}
	
	// returns the data from a cell
	public String getCellData(String sheetName,String colName,int rowNum){
		try{
			if(rowNum <=0)
				return "";
		
		int index = workbook.getSheetIndex(sheetName);
		int col_Num=-1;
		if(index==-1)
			return "";
		
		sheet = workbook.getSheetAt(index);
		row=sheet.getRow(0);
		for(int i=0;i<row.getLastCellNum();i++){
			//System.out.println(row.getCell(i).getStringCellValue().trim());
			if(row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
				col_Num=i;
		}
		if(col_Num==-1)
			return "";
		
		sheet = workbook.getSheetAt(index);
		row = sheet.getRow(rowNum-1);
		if(row==null)
			return "";
		cell = row.getCell(col_Num);
		
		if(cell==null)
			return "";
		//System.out.println(cell.getCellType());
		if(cell.getCellType()==Cell.CELL_TYPE_STRING)
			  return cell.getStringCellValue();
		else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC || cell.getCellType()==Cell.CELL_TYPE_FORMULA ){
			  
			  String cellText  = String.valueOf(cell.getNumericCellValue());
			  if (HSSFDateUtil.isCellDateFormatted(cell)) {
		           // format in form of M/D/YY
				  double d = cell.getNumericCellValue();

				  Calendar cal =Calendar.getInstance();
				  cal.setTime(HSSFDateUtil.getJavaDate(d));
		            cellText =
		             (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
		           cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" +
		                      cal.get(Calendar.MONTH)+1 + "/" + 
		                      cellText;
		           
		           //System.out.println(cellText);

		         }

			  
			  
			  return cellText;
		  }else if(cell.getCellType()==Cell.CELL_TYPE_BLANK)
		      return ""; 
		  else 
			  return String.valueOf(cell.getBooleanCellValue());
		
		}
		catch(Exception e){
			
			e.printStackTrace();
			return "row "+rowNum+" or column "+colName +" does not exist in xls";
		}
	}
	
	// returns the data from a cell
	public String getCellData(String sheetName,int colNum,int rowNum){
		try{
			if(rowNum <=0)
				return "";
		
		int index = workbook.getSheetIndex(sheetName);

		if(index==-1)
			return "";
		
	
		sheet = workbook.getSheetAt(index);
		row = sheet.getRow(rowNum-1);
		if(row==null)
			return "";
		cell = row.getCell(colNum);
		if(cell==null)
			return "";
		
	  if(cell.getCellType()==Cell.CELL_TYPE_STRING)
		  return cell.getStringCellValue();
	  else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC || cell.getCellType()==Cell.CELL_TYPE_FORMULA ){
		  
		  String cellText  = String.valueOf(cell.getNumericCellValue());
		 /* if (HSSFDateUtil.isCellDateFormatted(cell)) {
	           // format in form of M/D/YY
			  double d = cell.getNumericCellValue();

			  Calendar cal =Calendar.getInstance();
			  cal.setTime(HSSFDateUtil.getJavaDate(d));
	            cellText =
	             (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
	           cellText = cal.get(Calendar.MONTH)+1 + "/" +
	                      cal.get(Calendar.DAY_OF_MONTH) + "/" +
	                      cellText;
	           
	          // System.out.println(cellText);

	         }

		  */
		  
		  return cellText;
	  }else if(cell.getCellType()==Cell.CELL_TYPE_BLANK)
	      return "";
	  else 
		  return String.valueOf(cell.getBooleanCellValue());
		}
		catch(Exception e){
			
			e.printStackTrace();
			return "row "+rowNum+" or column "+colNum +" does not exist  in xls";
		}
	}
	
	// returns the data from a cell
		public boolean setCellData(String sheetName,int colNum,int rowNum,String data){
			try{
				fis = new FileInputStream(path); 
				workbook = new XSSFWorkbook(fis);
				if(rowNum <=0)
					return false;
			
			int index = workbook.getSheetIndex(sheetName);

			if(index==-1)
				return false;
			
		
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum-1);
			if (row == null)
				row = sheet.createRow(rowNum-1);
			
			cell = row.getCell(colNum);	
			if (cell == null)
		        cell = row.createCell(colNum);

		    // cell style
		    CellStyle cs = workbook.createCellStyle();
		    cs.setWrapText(true);
		    cell.setCellStyle(cs);
		    cell.setCellValue(data);

		    fileOut = new FileOutputStream(path);

			workbook.write(fileOut);

		    fileOut.close();	

			}
			catch(Exception e){
				e.printStackTrace();
				return false;
			}
			return true;
		}
	// returns true if data is set successfully else false
	public boolean setCellData(String sheetName,String colName,int rowNum, String data){
		try{
		fis = new FileInputStream(path); 
		workbook = new XSSFWorkbook(fis);

		if(rowNum<=0)
			return false;
		
		int index = workbook.getSheetIndex(sheetName);
		int colNum=-1;
		if(index==-1)
			return false;
		
		
		sheet = workbook.getSheetAt(index);
		

		row=sheet.getRow(0);
		for(int i=0;i<row.getLastCellNum();i++){
			//System.out.println(row.getCell(i).getStringCellValue().trim());
			if(row.getCell(i).getStringCellValue().trim().equals(colName))
				colNum=i;
		}
		if(colNum==-1)
			return false;

		sheet.autoSizeColumn(colNum); 
		row = sheet.getRow(rowNum-1);
		if (row == null)
			row = sheet.createRow(rowNum-1);
		
		cell = row.getCell(colNum);	
		if (cell == null)
	        cell = row.createCell(colNum);

	    // cell style
	    CellStyle cs = workbook.createCellStyle();
	    cs.setWrapText(true);
	    cell.setCellStyle(cs);
	    cell.setCellValue(data);

	    fileOut = new FileOutputStream(path);

		workbook.write(fileOut);

	    fileOut.close();	

		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	// returns true if data is set successfully else false
	public boolean setCellData(String sheetName,String colName,int rowNum, String data,String url){
		//System.out.println("setCellData setCellData******************");
		try{
		fis = new FileInputStream(path); 
		workbook = new XSSFWorkbook(fis);

		if(rowNum<=0)
			return false;
		
		int index = workbook.getSheetIndex(sheetName);
		int colNum=-1;
		if(index==-1)
			return false;
		
		
		sheet = workbook.getSheetAt(index);
		//System.out.println("A");
		row=sheet.getRow(0);
		for(int i=0;i<row.getLastCellNum();i++){
			//System.out.println(row.getCell(i).getStringCellValue().trim());
			if(row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName))
				colNum=i;
		}
		
		if(colNum==-1)
			return false;
		sheet.autoSizeColumn(colNum); //ashish
		row = sheet.getRow(rowNum-1);
		if (row == null)
			row = sheet.createRow(rowNum-1);
		
		cell = row.getCell(colNum);	
		if (cell == null)
	        cell = row.createCell(colNum);
			
	    cell.setCellValue(data);
	    XSSFCreationHelper createHelper = workbook.getCreationHelper();

	    //cell style for hyperlinks
	    //by default hypelrinks are blue and underlined
	    CellStyle hlink_style = workbook.createCellStyle();
	    XSSFFont hlink_font = workbook.createFont();
	    hlink_font.setUnderline(XSSFFont.U_SINGLE);
	    hlink_font.setColor(IndexedColors.BLUE.getIndex());
	    hlink_style.setFont(hlink_font);
	    //hlink_style.setWrapText(true);

	    XSSFHyperlink link = createHelper.createHyperlink(XSSFHyperlink.LINK_FILE);
	    link.setAddress(url);
	    cell.setHyperlink(link);
	    cell.setCellStyle(hlink_style);
	      
	    fileOut = new FileOutputStream(path);
		workbook.write(fileOut);

	    fileOut.close();	

		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	//custom function for hyperlink 
	//--------------SACHIN------------------------------------------
		public boolean setCellDataNew(String sheetName,String TC,String colName,int rowNum, String data,String url){
			System.out.println("setCellData setCellData******************");
			try{
			fis = new FileInputStream(path); 
			workbook = new XSSFWorkbook(fis);

			if(rowNum<=0)
				return false;
			
			int index = workbook.getSheetIndex(sheetName);
			int colNum=0;
			if(index==-1)
				return false;
			
			
			sheet = workbook.getSheetAt(index);
			int testfoundrow=1;
			while(!getCellData(sheetName, 0,testfoundrow).equalsIgnoreCase(TC)){
				System.out.println(getCellData(sheetName, 0,testfoundrow));
				testfoundrow++;
			}
			System.out.println("==============Testcase found==============="+getCellData(sheetName, 0,testfoundrow));
			int testdatatypecol=testfoundrow+1;
			row=sheet.getRow(testdatatypecol);
			int testdatastartrow=testfoundrow+2;
			System.out.println("Number of  Cells = "+row.getLastCellNum());
			System.out.println("Before ="+colNum);
			
			while(!getCellData(sheetName, colNum, testfoundrow+1).equalsIgnoreCase(colName)){
				colNum++;
			}
			System.out.println("After ="+colNum);
			/*
			for(int i=0;i<row.getLastCellNum();i++){
				System.out.println("Inside For loop");
				System.out.println(row.getCell(i).getStringCellValue().trim()+"-------"+i);
				if(row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName))
					colNum=i;
				break;//Edited
			}*/
			//ColName found
			System.out.println(colName +"========Found======="+colNum);
			if(colNum==-1)
				return false;
			sheet.autoSizeColumn(colNum); //ashish
			row = sheet.getRow(testdatatypecol+rowNum-1);
			if (row == null)
				row = sheet.createRow(testdatatypecol+rowNum-1);
			
			cell = row.getCell(colNum);	
			if (cell == null)
		        cell = row.createCell(colNum);
				
		    cell.setCellValue(data);
		    XSSFCreationHelper createHelper = workbook.getCreationHelper();

		    //cell style for hyperlinks
		    //by default hypelrinks are blue and underlined
		    CellStyle hlink_style = workbook.createCellStyle();
		    XSSFFont hlink_font = workbook.createFont();
		    hlink_font.setUnderline(XSSFFont.U_SINGLE);
		    hlink_font.setColor(IndexedColors.BLUE.getIndex());
		    hlink_style.setFont(hlink_font);
		    //hlink_style.setWrapText(true);

		    XSSFHyperlink link = createHelper.createHyperlink(XSSFHyperlink.LINK_FILE);
		    link.setAddress(url);
		    cell.setHyperlink(link);
		    cell.setCellStyle(hlink_style);
		      
		    fileOut = new FileOutputStream(path);
			workbook.write(fileOut);

		    fileOut.close();	

			}
			catch(Exception e){
				e.printStackTrace();
				return false;
			}
			return true;
		}
	//----------------------------Sachin---------------------------------------

	
	// returns true if sheet is created successfully else false
	public boolean addSheet(String  sheetname){		
		
		FileOutputStream fileOut;
		try {
			 workbook.createSheet(sheetname);	
			 fileOut = new FileOutputStream(path);
			 workbook.write(fileOut);
		     fileOut.close();		    
		} catch (Exception e) {			
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	// returns true if sheet is removed successfully else false if sheet does not exist
	public boolean removeSheet(String sheetName){		
		int index = workbook.getSheetIndex(sheetName);
		if(index==-1)
			return false;
		
		FileOutputStream fileOut;
		try {
			workbook.removeSheetAt(index);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
		    fileOut.close();		    
		} catch (Exception e) {			
			e.printStackTrace();
			return false;
		}
		return true;
	}
	// returns true if column is created successfully
	public boolean addColumn(String sheetName,String colName){
		//System.out.println("**************addColumn*********************");
		
		try{				
			fis = new FileInputStream(path); 
			workbook = new XSSFWorkbook(fis);
			int index = workbook.getSheetIndex(sheetName);
			if(index==-1)
				return false;
			
		XSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		sheet=workbook.getSheetAt(index);
		
		row = sheet.getRow(0);
		if (row == null)
			row = sheet.createRow(0);
		
		//cell = row.getCell();	
		//if (cell == null)
		//System.out.println(row.getLastCellNum());
		if(row.getLastCellNum() == -1)
			cell = row.createCell(0);
		else
			cell = row.createCell(row.getLastCellNum());
	        
	        cell.setCellValue(colName);
	        cell.setCellStyle(style);
	        
	        fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
		    fileOut.close();		    

		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		return true;
		
		
	}
	// removes a column and all the contents
	public boolean removeColumn(String sheetName, int colNum) {
		try{
		if(!isSheetExist(sheetName))
			return false;
		fis = new FileInputStream(path); 
		workbook = new XSSFWorkbook(fis);
		sheet=workbook.getSheet(sheetName);
		XSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
		XSSFCreationHelper createHelper = workbook.getCreationHelper();
		style.setFillPattern(HSSFCellStyle.NO_FILL);
		
	    
	
		for(int i =0;i<getRowCount(sheetName);i++){
			row=sheet.getRow(i);	
			if(row!=null){
				cell=row.getCell(colNum);
				if(cell!=null){
					cell.setCellStyle(style);
					row.removeCell(cell);
				}
			}
		}
		fileOut = new FileOutputStream(path);
		workbook.write(fileOut);
	    fileOut.close();
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
  // find whether sheets exists	
	public boolean isSheetExist(String sheetName){
		int index = workbook.getSheetIndex(sheetName);
		if(index==-1){
			index=workbook.getSheetIndex(sheetName.toUpperCase());
				if(index==-1)
					return false;
				else
					return true;
		}
		else
			return true;
	}
	
	// returns number of columns in a sheet	
	public int getColumnCount(String sheetName){
		// check if sheet exists
		if(!isSheetExist(sheetName))
		 return -1;
		
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(0);
		
		if(row==null)
			return -1;
		
		return row.getLastCellNum();
		
		
		
	}
	//String sheetName, String testCaseName,String keyword ,String URL,String message
	public boolean addHyperLink(String sheetName,String screenShotColName,String testCaseName,int index,String url,String message){
		//System.out.println("ADDING addHyperLink******************");
		
		url=url.replace('\\', '/');
		if(!isSheetExist(sheetName))
			 return false;
		
	    sheet = workbook.getSheet(sheetName);
	    
	    for(int i=2;i<=getRowCount(sheetName);i++){
	    	if(getCellData(sheetName, 0, i).equalsIgnoreCase(testCaseName)){
	    		//System.out.println("**caught "+(i+index));
	    		setCellData(sheetName, screenShotColName, i+index, message,url);
	    		break;
	    	}
	    }
		return true; 
	}
	
	//-------------------Sachin-----------------------------------------------
		public boolean addHyperLinkNew(String screenShotColName,String testCaseName,int index,String url,String message){
			System.out.println("ADDING addHyperLink******************");
			
			url=url.replace('\\', '/');
			System.out.println(url);
			if(!isSheetExist(Constants.TESTDATA_SHEET)){
				 return false;}
			else{
				System.out.println("Sheet Exits");
			}
			
		      
		   
		    		setCellDataNew(Constants.TESTDATA_SHEET, testCaseName, screenShotColName, index, message, url);
		    		//setCellData(sheetName, screenShotColName, i+index, message,url);
		    
			return true; 
		}
		//-------------------Sachin-----------------------------------------------
		
	
	
	public int getCellRowNum(String sheetName,String colName,String cellValue){
		
		for(int i=2;i<=getRowCount(sheetName);i++){
	    	if(getCellData(sheetName,colName , i).equalsIgnoreCase(cellValue)){
	    		return i;
	    	}
	    }
		return -1;
		
	}
	
	
	
	public boolean setResultCellDatawithColor(String sheetName,int colNum,int rowNum,String Result){
		try{
			fis = new FileInputStream(path); 
			workbook = new XSSFWorkbook(fis);
			if(rowNum <=0)
				return false;
		
		int index = workbook.getSheetIndex(sheetName);

		if(index==-1)
			return false;
		
	
		sheet = workbook.getSheetAt(index);
		row = sheet.getRow(rowNum-1);
		if (row == null)
			row = sheet.createRow(rowNum-1);
		
		cell = row.getCell(colNum);	
		if (cell == null)
	        cell = row.createCell(colNum);

	    // cell style
	    CellStyle my_style = workbook.createCellStyle();
	    my_style.setFillPattern(XSSFCellStyle.FINE_DOTS );
	    my_style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		Font font = workbook.createFont();
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		if(Result.equalsIgnoreCase("PASSED")||Result.equalsIgnoreCase("PASS")){
			my_style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			my_style.setFillBackgroundColor(IndexedColors.GREEN.getIndex());
		}else if (Result.equalsIgnoreCase("FAILED")||Result.equalsIgnoreCase("FAIL")) {
			my_style.setFillForegroundColor(IndexedColors.RED.getIndex());
			my_style.setFillBackgroundColor(IndexedColors.RED.getIndex());
		}else if (Result.equalsIgnoreCase("SKIPPED")||Result.equalsIgnoreCase("SKIP")){
			my_style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
	        my_style.setFillBackgroundColor(IndexedColors.ORANGE.getIndex());
		}
		
        font.setColor(IndexedColors.BLACK.getIndex());
        my_style.setFont(font);
        my_style.setWrapText(true);
        
	    cell.setCellStyle(my_style);
	    cell.setCellValue(Result);

	    fileOut = new FileOutputStream(path);

		workbook.write(fileOut);

	    fileOut.close();	

		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	

	public void setBackGroundcolor(){
		XSSFCellStyle my_style=workbook.createCellStyle();
		my_style.setFillPattern(XSSFCellStyle.FINE_DOTS );
		Font font = workbook.createFont();
		my_style.setFillForegroundColor(new HSSFColor.RED().getIndex());
        my_style.setFillBackgroundColor(new HSSFColor.RED().getIndex());
        font.setColor(IndexedColors.BLACK.getIndex());
        
        my_style.setFont(font);
        
		}
        
        
	//----------------------Sachin------------------------
		public  void checkRunmode(String TC,String resultCol,String Itration){
			int rowNum=1;
			while(!getCellData(Constants.TESTDATA_SHEET,0,rowNum).equalsIgnoreCase(TC)){
				rowNum++;
			}
			System.out.println("Test case Name Found = "+rowNum);
			int testColustart=rowNum+1;
			int testDatastartRow=rowNum+2;
			int colNum=0;
			while(!getCellData(Constants.TESTDATA_SHEET, colNum,testColustart).equalsIgnoreCase("Runmode")){
				colNum++;
			}
			int runmodecell=0;
			while(!getCellData(Constants.TESTDATA_SHEET,runmodecell,testColustart).equalsIgnoreCase(resultCol)){
				runmodecell++;
			}
			
			System.out.println("Column Name found"+colNum);
			System.out.println("Runmode cell number = "+runmodecell);
			int totalTD=0;
			while(!getCellData(Constants.TESTDATA_SHEET, 0, testDatastartRow+totalTD).equalsIgnoreCase("")){
				totalTD++;
			}
			System.out.println("Total test data rows = "+totalTD);
			
			int currentRow=testDatastartRow+Integer.valueOf(Itration)-1;
			//System.out.println(m_xls.getCellData(Constants.TESTDATA_SHEET,colNum,currentRow));
			if(getCellData(Constants.TESTDATA_SHEET,colNum,currentRow).equalsIgnoreCase("N")){
				
				setResultCellDatawithColor(Constants.TESTDATA_SHEET,runmodecell,currentRow, "SKIPPED");
			}
		}//--------------------------------------------------------------------------------------
		   
        
		
	
	
}
