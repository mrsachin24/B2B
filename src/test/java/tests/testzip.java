package tests;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import util.Zip;

public class testzip {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Zip.zipDir(System.getProperty("user.dir")+"\\Results",System.getProperty("user.dir")+"\\"+TimeStamp()+".rar" );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
public static String TimeStamp(){
		
		SimpleDateFormat dt=new SimpleDateFormat("yyyy MMM dd HH:mm:ss");
		Calendar _Calendar=new GregorianCalendar();
		int year       = _Calendar.get(Calendar.YEAR);
		int month      = _Calendar.get(Calendar.MONTH); // Jan = 0, dec = 11
		int dayOfMonth = _Calendar.get(Calendar.DAY_OF_MONTH); 
		String GetDate=dayOfMonth+"-"+month+"-"+year;
		int Hours=_Calendar.get(Calendar.HOUR_OF_DAY);
		int Min=_Calendar.get(Calendar.MINUTE);
		
		String Time=Hours+"-"+Min;
		return GetDate+"_"+Time;
		
	}
}
