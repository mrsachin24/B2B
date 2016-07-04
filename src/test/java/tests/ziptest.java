package tests;
import org.testng.annotations.Test;
import util.Zip;

public class ziptest {

	@Test
	public void testCase(){

		// TODO Auto-generated method stub
		//Zip.test();
		try {
			Zip.zipDir(System.getProperty("user.dir")+"\\Results",System.getProperty("user.dir")+"\\email_xlst_reports.rar" );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}
