package util;

import java.io.File;

public class deleteFile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		deleteFile(System.getProperty("user.dir")+"//Results//ExtentReporting");
		deleteFile(System.getProperty("user.dir")+"//Results//Logs");
		deleteFile(System.getProperty("user.dir")+"//Results//Screenshots");
		deleteFile(System.getProperty("user.dir")+"//Results//XlsReports");
		//deleteFile("C:\\Users\\Sachin\\Desktop\\testFolder");
		
		
	}
	public static void deleteFile(String FilePath){
		File file=new File(FilePath);
		String[] myString;
		if(file.isDirectory()){
			myString=file.list();
			for(int i=0;i<myString.length;i++){
				File myFile = new File(file, myString[i]); 
				System.err.println(myFile.getAbsolutePath());
	           myFile.delete();
			}
		}
	}
}
