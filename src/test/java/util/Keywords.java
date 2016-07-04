package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.record.PageBreakRecord.Break;
import org.apache.poi.hssf.record.pivottable.ExtendedPivotTableViewFieldsRecord;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import com.thoughtworks.selenium.webdriven.commands.IsEditable;


public class Keywords {
	Logger Application_Log;
	Properties prop;
	WebDriver driver;
	HashMap<String,WebDriver> map;
	String currentTestCaseName;
	String currentIteration;
	String currentBrowser;
	String TestStepDescription;
	String TestID;
	int curruntTestRow;
	String testResult;
	Xls_Reader m_xls;
	ExtentReports extent;
	long startTime;
	long HomePageLoadTime;
	DateFormat _DateFormat;
	Calendar _Calendar;
	//static Keywords k;
	static HashMap<String,Keywords> instanceMap = new HashMap<String,Keywords>();
	
	public Keywords(String m){
		// init map
		extent=ExtentReports.get(m);
		map = new HashMap<String,WebDriver>();
		map.put(Constants.MOZILLA, null);
		map.put(Constants.CHROME, null);
		map.put(Constants.IE, null);
		map.put(Constants.SAFARI, null);
		// initialize properties file
		prop=new Properties();
		try {
			FileInputStream fs = new FileInputStream(Constants.PROPERTIES_FILE_PATH);
			prop.load(fs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	public void executeKeywords(String testName, Xls_Reader xls,
			Hashtable<String, String> data) throws Throwable {

		// read the xls
		// call the keyword functions
		// report errors
		//20-05-2016 Time impllementation
		_DateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		m_xls=xls;
		currentTestCaseName=testName;
		currentIteration=data.get(Constants.ITERATION_COL);

		currentBrowser=data.get(Constants.BROWSER_COL);
		extent.init(System.getProperty("user.dir")+"//Results//ExtentReporting//"+currentTestCaseName+".html",false);
		//extent.init(filePath, replaceExisting, displayOrder, gridType);
		extent.startTest("Starting test case ="+currentTestCaseName+"_"+currentIteration);
		
		int rows = xls.getRowCount(Constants.KEYWORDS_SHEET);
		for(int rNum=2;rNum<=rows;rNum++){
			String tcName = xls.getCellData(Constants.KEYWORDS_SHEET, 0, rNum);
			if(tcName.equalsIgnoreCase(testName)){
				String keyword = xls.getCellData(Constants.KEYWORDS_SHEET, 3, rNum);
				String object = xls.getCellData(Constants.KEYWORDS_SHEET, 4, rNum);
				String dataCol = xls.getCellData(Constants.KEYWORDS_SHEET, 5, rNum);
				TestStepDescription=xls.getCellData(Constants.KEYWORDS_SHEET,2, rNum);
				TestID=xls.getCellData(Constants.KEYWORDS_SHEET,1, rNum);
				
				log(keyword +" --- "+object+" --- "+dataCol);
				ExtentLog(LogStatus.INFO,TestID+" : "+TestStepDescription);
				// TODO Auto-generated method stub
				switch (keyword) {
				case "openBrowser":
					openBrowser(data.get(dataCol));
					break;
				case "navigate":
					navigate();
					break;
				case "customNavigate":
					customNavigate(object);
					break;
				case "input":
					input(object,data.get(dataCol));
					break;
				case "click":
					click(object);
					break;
				case "closeBrowser":
					closeBrowser(data.get(Constants.BROWSER_COL));	
					break;
				case "waitAndClick"	:
					waitAndClick(object);
					break;
				case "clickAndWait":
					clickAndWait(object);	
					break;	
				case "clearTextField":
					clearTextField(object);	
					break;		
				case "clickAndAcceptAlert":
					clickAndAcceptAlert(object);	
					break;	
				case "waittoLoad":
					waittoLoad();	
					break;
				case "verifyLogin":
					verifyLogin(data);
					break;
				case "verifyForgotPassword":
					verifyForgotPassword(data);
					break;
				case "signupverification":
					signupverification(data);
					break;
				case "scrolltoElement":
					scrolltoElement(object);
					break;
				case "verifySignUp":
					verifySignUp(data);
					break;
				case "moveMousetoElement":
					moveMousetoElement(object);
					break;	
				case "moveMousetoElementClick":
					moveMousetoElementClick(object);
					break;	
				case "selectByVisibleText":
					selectByVisibleText(object,data.get(dataCol));
					break;
				case "verifyText":
					verifyText(object,data.get(dataCol));
					break;
				case "googleLogin":
					googleLogin(data);
					break;
				case "facebookLogin":
					facebookLogin(data);
					break;	
				case "isElementPresent":
					isElementPresent(object);
					break;
				case "selectRoomDesign":
					selectRoomDesign(data.get(dataCol));
					break;
				case "selectStylePreferences":
					selectStylePreferences(data.get(dataCol));
					break;
				case "selectColorPreferences":
					selectColorPreferences(object,data.get(dataCol));
					break;
				case "selectBudgetPreferences":
					selectBudgetPreferences(data.get(dataCol));
					break;
				case "uploadpicFloorplan":
					uploadpicFloorplan(data);
					break;
				case "clickOnFoyrLogo":
					clickOnFoyrLogo();
					break;
				case "verifyNavigationOnClick":
					verifyNavigationOnClick(object);
					break;
				case "LetsRollIntromessageText":
					LetsRollIntromessageText();
					break;
				case "RoomTypeStaticText":
					RoomTypeStaticText();
					break;
				case "isRoomSelected":
					isRoomSelected(data);
					break;
				case "nextButtonState":
					nextButtonState(object);
					break;
				case "BackButtonState":
					BackButtonState(object);
					break;
				case "verifyRoomTypes":
					verifyRoomTypes();
					break;
				case "isStyleSelected":
					isStyleSelected(data);
					break;
				case "verifyStyleTypes":
					verifyStyleTypes();
					break;
				case "isDefaultColorSelected":
					isDefaultColorSelected(data);
					break;
				case "selectCard":
					selectCard(object,data.get(dataCol));
					break;
				case "verifySelectedCard":
					verifySelectedCard(object,data.get(dataCol));
					break;
				case "verifyPageNumber":
					verifyPageNumber(object);
					break;
				case "isDefaultCardSelected":
					isDefaultCardSelected(object);
					break;
				case "defaultUserLogin":
					defaultUserLogin();
					break;
				case "verifyLandingPage":
					verifyLandingPage(object);
					break;
				case "logOut":
					logOut();
					break;
				case "back_next_list":
					back_next_list(object);
					break;
				case "removeThemeFromProfileWishlist":
					removeThemeFromProfileWishlist(data);
					break;
				case "selectCarouselSlide":
					selectCarouselSlide();
					break;
				case "selectRoomType":
					selectRoomType(data.get(dataCol));
					break;
				case "selectRandomRoomType":
					selectRandomRoomType();
					break;
				case "selectCity":
					selectCity(data.get(dataCol));
					break;
				case "selectTnC":
					selectTnC(data.get(dataCol));
					break;
				case "defaultUpload":
					defaultUpload();
					break;
				case "verifyFileUpload":
					verifyFileUpload(data.get(dataCol));
					break;
				case "selectRandomOffShelfDesign":
					selectRandomOffShelfDesign();
					break;
				case "selectOffShelfDesignTheme":
					selectOffShelfDesign(data.get(dataCol));
					break;
				case "LandingPage":
					LandingPage(object);
					break;
				case "linkClick":
					linkClick(object);
					break;
				case "selectWindowPopup":
					selectWindowPopup(object);
					break;
				case "tagWishlist":
					tagWishlist(data);
					break;
				case "displayWishlist":
					displayWishlist(data);
					break;
				case "verfiySelectedTheme":
					verfiySelectedTheme(data);
					break;
				case "MoveToElementClick":
					MoveToElementClick(object);
					break;
				case "verifyCity":
					verifyCity();
					break;
				case "isMultiple":
					isMultiple(object);
					break;
				case "editFieldMaxLength":
					editFieldMaxLength(object);
					break;
				case "verifyBTNtext":
					verifyBTNtext(object,data.get(dataCol));
					break;
				case "deleteFloorPlan":
					deleteFloorPlan(data.get(dataCol));
					break;
				case "verifyLoginPresent":
					verifyLoginPresent(object);
					break;
				case "doUserLogin":
					doUserLogin(data);
					break;
				case "verfiyContactPageforSubmitBTN":
					verfiyContactPageforSubmitBTN();
					break;
				case "verifyContactPage_ProfilePage":
					verifyContactPage_ProfilePage();
					break;
				case "verifyEditIcon_ProfilePage":
					verifyEditIcon_ProfilePage();
					break;	
				case "update_verify_ContactDetails":
					update_verify_ContactDetails(data);
					break;
				case "ClickdownloadFile":
					ClickdownloadFile(object,data);
					break;
				case "selectdesignByThemeID":
					selectdesignByThemeID(data.get(dataCol));
					break;
				case "verifyOffShelfdesign":
					verifyOffShelfdesign(data);
					break;
				case "verifywishlistLoginPopup":
					verifywishlistLoginPopup(data);
					break;
				case "verifyStaticTabNoLogin":
					verifyStaticTabNoLogin(data);
					break;
				case "verifyStaticTabLogin":
					verifyStaticTabLogin(data);
					break;	
				case "deselectCard":
					deselectCard(object,data.get(dataCol));
					break;
				case "verifyUserProfilePage":
					verifyUserProfilePage(data);
					break;
				case "selectRandomRoom":
					selectRandomRoom();
					break;
				case "selectRandomRoomPage3D":
					selectRandomRoomPage3D();
					break;
				case "addThemeIntoWishlist":
					addThemeIntoWishlist(data.get(dataCol));
					break;
				case "tagWishlistTheme":
					tagWishlistTheme(data.get(dataCol));
					break;
				case "SelectTower":
					SelectTower();
					break;
				case "testTower":
					testTower();
					break;
				case "testTheme":
					testTheme();
					break;
				case "testBudgetType":
					testBudgetType();
					break;
				case "testDesignType":
					testDesignType();
					break;
				case "TestRoomGallary":
					TestRoomGallary();
					break;
				default:
					break;
				
				}
				
			}
		}
		
		
	}
	
	
	public void PageTimer(String path,int PageLoadtime,long startTime,String colname){
		  while(((System.currentTimeMillis()/1000)-startTime)<PageLoadtime){
	          if(!isElementPresent(path)){
	   long endTime = System.currentTimeMillis()/1000;
	   System.err.println("The endTime is "+endTime);
	   long loadTime = endTime - startTime;
	   System.err.println("Totaltime: " +loadTime + " seconds");
	   ReportHomePageLoadTime(m_xls, currentTestCaseName, colname, currentIteration,loadTime+" seconds");
	   
	       break;
	   }  
	    }
		  if(((System.currentTimeMillis()/1000)-startTime)>=PageLoadtime){
			  System.err.println("Taking more the 60 Seconds to load the Page");
			  ReportHomePageLoadTime(m_xls, currentTestCaseName, colname, currentIteration,"Taking too much of time");
			  log("Current Page = "+driver.getCurrentUrl());
			  reportError("Page Taking too much time to Load"); 
		  }
	  }

	 public void PageTimer(WebElement element,int PageLoadtime,long startTime){
		  while(((System.currentTimeMillis()/1000)-startTime)<PageLoadtime){
	          if(element.isDisplayed()){
	   long endTime = System.currentTimeMillis()/1000;
	   System.err.println("The endTime is "+endTime);
	   long loadTime = endTime - startTime;
	   System.err.println("Totaltime: " +loadTime + " seconds"); 
	       break;
	   }  
	    }
		  if(((System.currentTimeMillis()/1000)-startTime)>=PageLoadtime){
			  System.err.println("Taking too much of time");
			  
		  }
	  }
	
	private void TestRoomGallary() {
		log("Inside function TestRoomGallary ");
		waitforTime(2);
		WebElement RoomGallaryCON=element("DesignPage_CON_RoomGallary_xpath");
		List<WebElement> imageTitleList=RoomGallaryCON.findElements(By.className("image-title"));
		System.err.println("image-title" +imageTitleList.size());
		/*
		int i;
		for (i = 0; i < imageTitleList.size(); i++) {
			System.out.println(imageTitleList.get(i).getText());
			if(imageTitleList.get(i).isDisplayed()){
				System.err.println(imageTitleList.get(i).getText());
				break;
			}
		}
		*/
		String SelectdFoyrThemeStartxpath=".//*[@id='designs']/div[1]/div[2]/div/div[4]/div[";
		String SelectdFoyrThemeEndxpath="]";
		WebElement FoyrTheme=null;
		int i;
		for(i=1;i<=imageTitleList.size();i++){
			FoyrTheme=element(SelectdFoyrThemeStartxpath+i+SelectdFoyrThemeEndxpath);
			
			if(FoyrTheme.getAttribute("style").equalsIgnoreCase("display: block;")){
				System.err.println(FoyrTheme.getAttribute("data-class"));
				break;
			}
		}if(i>imageTitleList.size()){
			log("By default non of the selected display: block;");
		}
		if(FoyrTheme.findElement(By.cssSelector(".customize_btn.customize_btn_left")).getAttribute("Style").equalsIgnoreCase("opacity: 0.4; pointer-events: none;")){
			System.err.println(FoyrTheme.findElement(By.cssSelector(".add_to_kart_btn.customize_btn_left")).isEnabled());
			scrolltoElementusingWebElement(FoyrTheme.findElement(By.cssSelector(".add_to_kart_btn.customize_btn_left")));
			waitforTime(2);
			FoyrTheme.findElement(By.cssSelector(".add_to_kart_btn.customize_btn_left")).click();
			
			return;
		}
		else if(!FoyrTheme.findElement(By.cssSelector(".customize_btn.customize_btn_left")).getAttribute("Style").equalsIgnoreCase("opacity: 0.4; pointer-events: none;")){
			scrolltoElementusingWebElement(FoyrTheme.findElement(By.cssSelector(".customize_btn.customize_btn_left")));
			waitforTime(1);
			FoyrTheme.findElement(By.cssSelector(".customize_btn.customize_btn_left")).click();
			waitforTime(1);
			}
		long PagetimeStart=System.currentTimeMillis()/1000;
		  System.err.println("The startTime is "+PagetimeStart);
		
		
		PageTimer("Room3D_IMG_LoadingBaar_xpath", 60, PagetimeStart,"3DRoomLoadTime");
				
		element("Room3D_BTN_Close_xpath").click();
		
	
	}


	/*
	private void TestRoomGallary() {
		// TODO Auto-generated method stub
		WebElement RoomGallaryCON=element("DesignPage_CON_RoomGallary_xpath");
		List<WebElement> imageTitleList=RoomGallaryCON.findElements(By.className("image-title"));
		System.err.println(imageTitleList.size());
		int i;
		for (i = 0; i < imageTitleList.size(); i++) {
			System.out.println(imageTitleList.get(i).getText());
			if(imageTitleList.get(i).isDisplayed()){
				System.err.println(imageTitleList.get(i).getText());
				break;
			}
		}
		int SelectedThemenumber=i+1;
		WebElement SelectedFoyrTheme=element(".//*[@id='designs']/div[1]/div[2]/div/div[4]/div["+SelectedThemenumber+"]/div");
		System.err.println(SelectedFoyrTheme.findElement(By.cssSelector(".customize_btn.customize_btn_left")).getAttribute("style"));
		System.err.println(SelectedFoyrTheme.findElement(By.cssSelector(".add_to_kart_btn.customize_btn_left")).isEnabled());
		if(SelectedFoyrTheme.findElement(By.cssSelector(".customize_btn.customize_btn_left")).getAttribute("style").equalsIgnoreCase("opacity: 0.4; pointer-events: none;")){
			return;
			
		}else if(!SelectedFoyrTheme.findElement(By.cssSelector(".customize_btn.customize_btn_left")).getAttribute("style").equalsIgnoreCase("opacity: 0.4; pointer-events: none;")){
			SelectedFoyrTheme.findElement(By.cssSelector(".customize_btn.customize_btn_left")).click();
		}
		//waittoLoad();
		//waittoLoad();
		
		//System.err.println(element("Room3D_BTN_Close_xpath").isEnabled());
		while(WebWait(driver, "Room3D_IMG_LoadingBaar_xpath")){

			waitforTime(2);
		}
		
		element("Room3D_BTN_Close_xpath").click();
		
	}

*/
	private void testDesignType() {
		// TODO Auto-generated method stub
		waitforTime(2);
		WebElement DesignsCON=element("DesignPage_CON_Rooms_xpath");
		List<WebElement> DesignLists=DesignsCON.findElements(By.tagName("li"));
		log("Number of Designs in = "+DesignLists.size());
		for(int i=0;i<DesignLists.size();i++){
			//System.err.println(DesignLists.get(i).getAttribute("class"));
			//.//*[@id='mCSB_3_container']/ul/li[1]
			System.err.println(driver.findElement(By.xpath(".//*[@id='content-3']/ul/li["+(i+1)+"]")).getAttribute("class"));
		}
		int RandomDesignNumber=randomnumber(1, DesignLists.size());
		System.err.println("RandomDesignNumber = "+RandomDesignNumber);
		scrolltoElementusingWebElement(driver.findElement(By.xpath(".//*[@id='content-3']/ul/li["+(RandomDesignNumber)+"]")));
		waitforTime(1);
		driver.findElement(By.xpath(".//*[@id='content-3']/ul/li["+(RandomDesignNumber)+"]")).click();
		
		//Actions act=new Actions(driver);
		//act.moveToElement(DesignLists.get(RandomDesignNumber)).click().build().perform();
		//DesignLists.get(RandomDesignNumber).findElement(By.tagName("i")).click();
	}


	private void testBudgetType() {
		// TODO Auto-generated method stub
		waitforTime(2);
		WebElement BudgetCON=element("ThemePage_CON_BudgetCON_xpath");
		WebElement BudgetPackCON=BudgetCON.findElement(By.className("package-navs"));
		List<WebElement> BudgetList=BudgetPackCON.findElements(By.tagName("li"));
		System.err.println(BudgetList.size());
		int RandomBudgetNumber=randomnumber(0, BudgetList.size()-1);
		log("RandomBudgetNumber = "+RandomBudgetNumber);
		log(BudgetList.get(RandomBudgetNumber).findElement(By.tagName("a")).getText());
		BudgetList.get(RandomBudgetNumber).click();
		waitforTime(1);
		
		
		List<WebElement> Listss=BudgetCON.findElements(By.cssSelector(".theme-badge.col-md-12"));
		System.err.println(Listss.size());
		for(int i=0;i<Listss.size();i++){
			System.err.println(Listss.get(i).isDisplayed());
			if(Listss.get(i).isDisplayed()){
				String xpathstart=".//*[@id='theme']/div/div[4]/div[";
				String xpathEnd ="]/a/img";
				driver.findElement(By.xpath(xpathstart+(i+1)+xpathEnd)).click();
				break;
				
			}
		}
	}


	private void scrollToEnd(){
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollTo(0,Math.max(document.documentElement.scrollHeight,document.body.scrollHeight,document.documentElement.clientHeight));");
	}

	private void testTheme() {
		// TODO Auto-generated method stub
		//WebElement TmemePage=element("ThemePage_CON_Whole_xpath");
		waitforTime(2);
		WebElement ThemeCON=element("ThemePage_CON_Theme_css");
		List<WebElement> ThemeLists=ThemeCON.findElements(By.tagName("li"));
		log("Number of themes in Theme Page = "+ThemeLists.size());
		for(int i=0;i<ThemeLists.size();i++){
			System.err.println(ThemeLists.get(i).getAttribute("class"));
		}
		int RandomulListNumber=randomnumber(0, ThemeLists.size()-1);
		System.err.println("RandomulListNumber = "+RandomulListNumber);
		System.err.println(ThemeLists.get(RandomulListNumber).getAttribute("class"));
		WebWait(driver, "ThemePage_CON_Theme_xpath");
		//scrollToEnd();
		scrolltoElementusingWebElement(ThemeLists.get(0));
		waitforTime(1);
		Actions act=new Actions(driver);
		act.moveToElement(ThemeLists.get(RandomulListNumber)).click().build().perform();
		waitforTime(2);
		//ThemeLists.get(RandomulListNumber).click();
		
		
	}


	private String testTower() {
		// TODO Auto-generated method stub
		WebElement RightTowerCON=element("TowerPage_CON_RightTower_xpath");
		System.err.println("RightTowerCON is displayed =" +RightTowerCON.isDisplayed());
		List<WebElement> RightTowerList=RightTowerCON.findElements(By.className("bx-wrapper"));
		System.err.println("++++++++++++++"+RightTowerList.size()+"++++++++++++++");
		int i;
		for(i=0;i<RightTowerList.size();i++){
			if(RightTowerList.get(i).getAttribute("style").equalsIgnoreCase("max-width: 1000px; margin: 0px auto; display: block;")){
				System.err.println("Value of i = "+i);
				break;
			}
			
		}if(i==RightTowerList.size()){
			for(i=0;i<RightTowerList.size();i++){
				if(RightTowerList.get(i).getAttribute("style").equalsIgnoreCase("max-width: 1000px; margin: 0px auto; display: block;")){
					System.err.println("Value of i = "+i);
					break;
				}
		}}
		log("Value of i = "+i);
		WebElement SelectedBuilding =RightTowerList.get(i);
		
		WebElement bx_viewport=SelectedBuilding.findElement(By.className("bx-viewport"));
		List<WebElement> viewport_ulList=bx_viewport.findElement(By.tagName("ul")).findElements(By.tagName("li"));
		System.err.println("viewport_ulList = "+viewport_ulList.size());
		for(int x=0;x<viewport_ulList.size();x++){
			System.err.println("viewport_ulList.get(x).isDisplayed() = "+viewport_ulList.get(x).isEnabled());
		}
		int RandomulListNumber=randomnumber(1, viewport_ulList.size());
		System.err.println("RandomulListNumber = "+RandomulListNumber);
		int clickEle=RandomulListNumber-1;
		while(clickEle!=0){
			//System.err.println(SelectedBuilding.findElement(By.className("bx-prev")).isDisplayed());
			SelectedBuilding.findElement(By.className("bx-next")).click();
			waitforTime(2);
			clickEle--;
		}
		//viewport_ulList.get(clickEle).click();
		viewport_ulList.get(RandomulListNumber-1).click();
		
		return TestID;
	}


	private String SelectTower() {
		log("Inside Function SelectTower");
		waitforTime(2);
		WebElement TowersCon=element("TowerPage_CON_TowerList_xpath");
		List<WebElement> TowerLists=TowersCon.findElements(By.tagName("li"));
		log("Number of Towers Present = "+TowerLists.size());
		int RandomTowerNumber=randomnumber(0, TowerLists.size()-1);
		log("Random Tower Number is "+RandomTowerNumber);
		scrolltoElementusingWebElement(TowerLists.get(RandomTowerNumber));
		String BlockID=TowerLists.get(RandomTowerNumber).getAttribute("data-blockid");
		log("Selected Tower Block ID is = "+BlockID);
		TowerLists.get(RandomTowerNumber).click();
		waitforTime(2);
		return BlockID;
		
	}


	private void moveMousetoElementClick(String object) {
		log("Inside function moveMousetoElementClick = "+object);
		Actions act=new Actions(driver);
		waitforTime(2);
		act.moveToElement(element(object)).click().build().perform();
				
	
	}


	private String customNavigate(String object) {
		//ExtentLog(LogStatus.INFO,TestID+" : "+TestStepDescription );
		log(LogStatus.INFO,"Starting function customNavigate");
		String TestURL=object.split(",")[0];
		String Element_xpath=object.split(",")[1];
		try{
			_Calendar=Calendar.getInstance();
			startTime = System.currentTimeMillis()/1000;
			System.err.println("The startTime is "+startTime);
			ReportTestStartTime(m_xls, currentTestCaseName, currentIteration,_DateFormat.format(_Calendar.getTime()));
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS); 
			driver.get(prop.getProperty(TestURL));
			//WebElement search =element(Element_xpath);
			//Iterate through the loop as long as time(60sec) is with in the acceptable Page load time
			long loadTime = 0;
			while(((System.currentTimeMillis()/1000)-startTime)<30){
			  if(isElementPresent(Element_xpath)){
			   HomePageLoadTime = System.currentTimeMillis()/1000;
			   log("The endTime is "+HomePageLoadTime);
			   loadTime = HomePageLoadTime - startTime;
			   log("Totaltime: " +loadTime + " seconds");
			   ExtentLog(LogStatus.INFO,"Totaltime: " +loadTime + " seconds");
			   ReportHomePageLoadTime(m_xls, currentTestCaseName, "HomePageLoadTime", currentIteration,loadTime+" seconds");
			       break;
			   }}if(((System.currentTimeMillis()/1000)-startTime)>=30){
				   ReportHomePageLoadTime(m_xls, currentTestCaseName, "HomePageLoadTime", currentIteration,loadTime+" seconds");
			       ExtentLog(LogStatus.FAIL,"HomePage is Taking more time to Load the Page");
			       testResult="FAIL";
			   }
		}catch(Exception e){ //error
			e.printStackTrace();
			reportError(Constants.NAVIGATE_ERROR+e.getMessage());
		}
		
		log(LogStatus.INFO,"Ending  function navigate with status "+Constants.PASS);
		return Constants.PASS;
	
		
	}


	private void TestLogin1(String UName) {
		// TODO Auto-generated method stub
		log("Inside testlogin1 = " +UName);
		click("HomePage_BTN_Login_xpath1");
		waittoLoad();
		input("LoginWindow_EB_Email_xpath",UName);
		log("Inside testlogin1 = " +UName);
	}


	private void testLogin() {
		// TODO Auto-generated method stub
		log("Inside function testLogin");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement ele=element("Profile_BTN_UniqueDesign_xpath");
		if(!ele.isDisplayed()){
			reportError("Unable to log in");
		}else{
			log("Logged in");
		}
	}


	private void addThemeIntoWishlist(String XlsThemeID) {
		log(" ++++++++Inside Function addThemeIntoWishlist+++++++ ");
		WebElement designCon=element("OffShelfDesign_CON_Designs_xpath");
		List<WebElement> list=designCon.findElements(By.tagName("li"));
		System.err.println(list.size());
		int i;
		for(i=0;i<list.size();i++){
		String WebThemeID=list.get(i).getAttribute("id");
		System.err.println(WebThemeID.trim()+"======="+XlsThemeID.trim());
		scrolltoElementusingWebElement(list.get(i));
		if(WebThemeID.equalsIgnoreCase(XlsThemeID)){
			//Actions act=new Actions(driver);
			//act.moveToElement(list.get(i)).build().perform();
			System.err.println("Value of i "+i);
			break;
		}
		}if(i==list.size()){
			System.err.println("Theme Not found");
			return;
		}
		
		Actions act= new Actions(driver);
		act.moveToElement(list.get(i)).build().perform();
		System.err.println(list.get(i).findElement(By.cssSelector(".fa.fa-heart")).getAttribute("style"));
		if(!list.get(i).findElement(By.cssSelector(".fa.fa-heart")).getAttribute("style").equalsIgnoreCase("color: rgb(230, 230, 230);")){
			System.err.println("Already add in Wishlist");
		}
		list.get(i).findElement(By.cssSelector(".fa.fa-heart")).click();
		if(WebWait(driver, "offShelfDesign_TXT_Msg_xpath")&& element("offShelfDesign_TXT_Msg_xpath").getText().equalsIgnoreCase("Added to your wishlist!")){
			System.err.println("Theme Added into Wishlist");
		}else{
			reportError("Some Erroe");
		}
		waitforTime(5);
		
	}


	private void selectRandomRoomPage3D() {
		// TODO Auto-generated method stub
		WebElement project_listleft=element("OTSTPage_CON_Listleft_xpath");
		List<WebElement> project_List=project_listleft.findElements(By.className("project_list"));
		System.err.println(project_List.size());
		WebElement projectList=null;
		String projectListName=null;
		for(int i=0;i<project_List.size();i++){
			System.err.println(project_List.get(i).getAttribute("id")+"----"+project_List.get(i).getAttribute("style"));
			if(project_List.get(i).getAttribute("style").equalsIgnoreCase("display: block;")){
				projectListName=project_List.get(i).getAttribute("id");
				projectList=project_List.get(i);
				System.err.println("Selected caro ="+projectListName);
				break;
			}
			
		}
		WebElement carousel_slide=projectList.findElement(By.cssSelector(".carousel.slide"));
		String CarouselID=carousel_slide.getAttribute("id");
		System.err.println(carousel_slide.getAttribute("id"));
		WebElement carousel_inner=element(".//*[@id='"+CarouselID+"']/div[1]");
		WebElement Button3D=element(".//*[@id='"+CarouselID+"']/div[2]/a/img");
		WebElement LeftArrow=null;
		WebElement RightArrow=null;
		//WebElement LeftArrow=element(".//*[@id='"+CarouselID+"']/a[1]");
		//WebElement RightArrow=element(".//*[@id='"+CarouselID+"']/a[2]");
		
		
		List<WebElement> innerItems=carousel_inner.findElements(By.className("inneritem"));
		System.err.println("inneritem size "+innerItems.size());
		for(int i=1;i<=innerItems.size();i++){
			WebElement carouselinner_Items=element(".//*[@id='"+CarouselID+"']/div[1]/div["+i+"]");
			System.err.println(carouselinner_Items.getAttribute("class"));
		}
		int randomNumberforinneritem=randomnumber(1,innerItems.size());
		if(randomNumberforinneritem>1){
			int item=1;
			while(item!=randomNumberforinneritem){
				element(".//*[@id='"+CarouselID+"']/a[2]").click();
				item++;
			}
		}
		
		if(!Button3D.isDisplayed()){
			reportError("Button3D is not Displayed");
		}else{
			System.err.println("Button3D is displayed");	
		}
		if(innerItems.size()>1){
			LeftArrow=element(".//*[@id='"+CarouselID+"']/a[1]");
			if(!LeftArrow.isDisplayed()){
				reportError("LeftArrow is not Displayed");
			}else{
				System.err.println("LeftArrow is displayed");	
			}
		}
		if(innerItems.size()>1){
			RightArrow=element(".//*[@id='"+CarouselID+"']/a[2]");
		if(!RightArrow.isDisplayed()){
			reportError("RightArrow is not Displayed");
		}else{
			System.err.println("RightArrow is displayed");	
		}}
		
		Button3D.click();
		waitForPageToLoad();
		String URL3D=driver.getCurrentUrl();
		if(!URL3D.contains(projectListName)){
			reportError("Wrong 3D Panaroma is opening");
		}
		
		
	}


	private void verifyUserProfilePage(Hashtable<String, String> data) {
		// TODO Auto-generated method stub
		verifyStaticTabLogin(data);
		//Verifying the userName
		String UserName=element("Profile_TXT_Name_xpath").getText().trim();
		//verifying with Profile Name
		if(!UserName.equalsIgnoreCase(element("Profile_TXT_NameProfileImage_xpath").getText().trim())){
			reportError("User Name and ProfileImage Name not Matching");
		}
		if(!UserName.equalsIgnoreCase(element("MainWrap_BTN_UserName_xpath").getText().trim())){
			reportError("User Name and ProfileLogin Name not Matching");
		}
	}


	private void deselectCard(String object, String items) {
		// TODO Auto-generated method stub
		//ExtentLog(LogStatus.INFO,"De Selecting the Selecting card");
		log("Inside Function deselectCard ");
		ArrayList<String> ArrayRoomDesign=new ArrayList<String>();
		for(int i=0;i<items.split(",").length;i++){
			ArrayRoomDesign.add(items.split(",")[i]);
		}
		//System.out.println("ArrayList = "+ArrayRoomDesign.toString());
		
		String CON_Start_xpath=".//*[@id='";
		String CON_End_xpath="']/div";
		String roomdesign=prop.getProperty(object);		
		
		String CON_path=CON_Start_xpath+roomdesign+CON_End_xpath;
		//System.err.println(isElementPresent("RoomDesign_BTN_Next_css",5));
		WebElement designContainer=element(CON_path);
		WebElement Header=designContainer.findElement(By.className("title_info")).findElement(By.tagName("h2"));
		WebElement Description=designContainer.findElement(By.className("title_info")).findElement(By.tagName("p"));
		//System.err.println(Header.getText());
		//System.err.println(Description.getText());
		List<WebElement> ColorPreferenceList=designContainer.findElements(By.className("roomdesign_view"));
		
		for(int roomtype=0;roomtype<ArrayRoomDesign.size();roomtype++){
		for(int i=0;i<ColorPreferenceList.size();i++){
			WebElement ColorPreferenceListimg=ColorPreferenceList.get(i).findElement(By.cssSelector(".roomdesign_viewimg"));
			WebElement ColorPreferenceListcontclearfix=ColorPreferenceList.get(i).findElement(By.cssSelector(".roomdesign_viewcont.clearfix"));
			WebElement RoomName=ColorPreferenceListcontclearfix.findElement(By.className("ng-binding"));
			WebElement RoomSelect=ColorPreferenceListcontclearfix.findElement(By.className("checked_img"));
			if(RoomName.getText().trim().equalsIgnoreCase(ArrayRoomDesign.get(roomtype))){
				if(!RoomSelect.isDisplayed()){
					reportError("card select not Selected for = "+ArrayRoomDesign.get(roomtype));
				}else if(RoomSelect.isDisplayed()){
					log("card select matched for = "+ArrayRoomDesign.get(roomtype));
					ColorPreferenceList.get(i).click();
					break;
				}
			}
	}}

	
	}

	
	private void verifyStaticTabLogin(Hashtable<String, String> data) {
		//ExtentLog(LogStatus.INFO,TestID+" : "+TestStepDescription );
		log("Inside Function verifyStaticTabNoLogin");
		verifyElement("MainWrap_IMG_Foyr_xpath",Constants.Element_True);
		verifyElement("HomePage_LINK_GetUniqueDesign_xpath",Constants.Element_True);
		verifyElement("HomePage_LINK_ExploreOff_xpath",Constants.Element_True);
		verifyElement("HomePage_BTN_Login_xpath",Constants.Element_False);

		verifyElement("MainWrap_BTN_UserName_xpath",Constants.Element_True);
		verifyElement("MainWrap_BTN_Logout_xpath",Constants.Element_True);
		
	}
	
	private void verifyStaticTabNoLogin(Hashtable<String, String> data) {
		// TODO Auto-generated method stub
		//ExtentLog(LogStatus.INFO,TestID+" : "+TestStepDescription );
		log("Inside Function verifyStaticTabNoLogin");
		verifyElement("MainWrap_IMG_Foyr_xpath",Constants.Element_True);
		verifyElement("HomePage_LINK_GetUniqueDesign_xpath",Constants.Element_True);
		verifyElement("HomePage_LINK_ExploreOff_xpath",Constants.Element_True);
		verifyElement("HomePage_BTN_Login_xpath",Constants.Element_True);

		verifyElement("MainWrap_BTN_UserName_xpath",Constants.Element_False);
		verifyElement("MainWrap_BTN_Logout_xpath",Constants.Element_False);
	}

	private void verifyElement(String object){
		String Element=object.split(",")[0];
		String ExpectedStatus=object.split(",")[1];
		
		if(!ExpectedStatus.equalsIgnoreCase(Boolean.toString(isElementPresent(Element)))){
			reportError("verifyElement : Expected status = "+ExpectedStatus+" Actual Status is = "+isElementPresent(Element));
		}
	}

	private void verifyElement(String object,String status){
		String Element=object;
		String ExpectedStatus=status;
		
		if(!ExpectedStatus.equalsIgnoreCase(Boolean.toString(element(Element).isDisplayed()))){
			reportError("verifyElement : "+object+" Expected status = "+ExpectedStatus+" Actual Status is = "+element(Element).isDisplayed());
		}
		
	}
	
	private void ClickdownloadFile(String object, Hashtable<String, String> data) {
		// TODO Auto-generated method stub
		click(object);
		if(data.get("Browser").equalsIgnoreCase("MOZILLA")||data.get("Browser").equalsIgnoreCase("SAFARI")){
			System.err.println(driver.getCurrentUrl());
		}else if(data.get("Browser").equalsIgnoreCase("Chrome")){
			try {
				Process process = new ProcessBuilder(System.getProperty("user.dir")+"\\upload\\fileDownload.exe",System.getProperty("user.dir")+"\\", "Open").start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	
	
	
	private void update_verify_ContactDetails(Hashtable<String, String> data) {
		// TODO Auto-generated method stub
		String Contact_userName=element("ContactDetailPage_EB_UserName_xpath").getAttribute("value");
		//System.err.println(driver.findElement(By.xpath(".//*[@id='u_name']")).getAttribute("value"));
		String Contact_Id=element("ContactDetailPage_EB_Email_xpath").getAttribute("value");
		//System.err.println(driver.findElement(By.xpath(".//*[@id='u_email']")).getAttribute("value"));
		String Contact_Mobile=element("ContactDetailPage_EB_Mobile_xpath").getAttribute("value");
		//System.err.println(driver.findElement(By.xpath(".//*[@id='u_mobile']")).getAttribute("value"));
		String Contact_Location=element("ContactDetailPage_DD_City_xpath").getAttribute("value");
		//System.err.println(driver.findElement(By.xpath(".//*[@id='roomdesign_9']/div/div[2]/div/form/div/div[4]/div/div/div[1]/button")).getAttribute("value"));
		
		element("ContactDetailPage_IMG_UserName_xpath").click();
		while(!element("ContactDetailPage_EB_UserName_xpath").getAttribute("value").equalsIgnoreCase("")){
			element("ContactDetailPage_EB_UserName_xpath").sendKeys(Keys.DELETE);
		}
		element("ContactDetailPage_EB_UserName_xpath").sendKeys(data.get("Data_userName"));
		element("ContactDetailPage_IMG_Mobile_xpath").click();
		while(!element("ContactDetailPage_EB_Mobile_xpath").getAttribute("value").equalsIgnoreCase("+91-")){
			element("ContactDetailPage_EB_Mobile_xpath").sendKeys(Keys.DELETE);
		}
		element("ContactDetailPage_EB_Mobile_xpath").sendKeys(data.get("Data_Mobile"));
		
		element("ContactDetailPage_IMG_Email_xpath").click();
		while(!element("ContactDetailPage_EB_Email_xpath").getAttribute("value").equalsIgnoreCase("")){
			element("ContactDetailPage_EB_Email_xpath").sendKeys(Keys.DELETE);
		}
		element("ContactDetailPage_EB_Email_xpath").sendKeys(data.get("Data_userID"));
		
		element("ContactDetailPage_DD_City_xpath").click();
		WebElement dropdown=element("ContactDetailPage_DD_City_xpath");
		List<WebElement> dropdownlist=dropdown.findElements(By.tagName("li"));
		for(int i=0;i<dropdownlist.size();i++){
			if(dropdownlist.get(i).findElement(By.className("text")).getText().equalsIgnoreCase(data.get("Data_Location"))){
				dropdownlist.get(i).click();
				break;
				
			}
		}
		element("ContactDetailPage_BTN_Submit_xpath").click();
		element("MainWrap_BTN_Close_xpath").click();
		element("MainWrap_BTN_Name_xpath").click();
		
		String profile_Email=driver.findElement(By.xpath(".//*[@id='profile-contents']/div/div/div[2]/div/div/div[1]/div/div[2]/div[1]/div[1]/div[2]/div[1]/div/p")).getText();
		String profile_Name=driver.findElement(By.xpath(".//*[@id='profile-contents']/div/div/div[2]/div/div/div[1]/div/div[2]/div[1]/div[2]/div[2]/div[1]/div/p")).getText();
		String profile_Mobile=driver.findElement(By.xpath(".//*[@id='profile-contents']/div/div/div[2]/div/div/div[1]/div/div[2]/div[1]/div[3]/div[2]/div[1]/div/p")).getText();
		String Profile_location=driver.findElement(By.xpath(".//*[@id='profile-contents']/div/div/div[2]/div/div/div[1]/div/div[2]/div[1]/div[4]/div[2]/div[1]/div/p")).getText();
		
		
		if(!profile_Email.equalsIgnoreCase(Contact_Id)){
			System.err.println("Report Error");
		}else{
			System.err.println("ID Working Correctly");
		}
		
		if(profile_Mobile.equalsIgnoreCase(data.get("Data_Mobile"))){
			System.err.println("Report Error"+profile_Mobile +Contact_Mobile);
		}else{
			System.err.println("Mobile Working Correctly");
		}
		
		if(Profile_location.equalsIgnoreCase(Contact_Location)){
			System.err.println("Report Error");
		}else{
			System.err.println("Location Working Correctly");
		}
		if(profile_Name.equalsIgnoreCase(data.get("Data_userName"))){
			System.err.println("Report Error");
		}else{
			System.err.println("username Working Correctly");
		}
	}

	private String verifyEditIcon_ProfilePage() {
		// TODO Auto-generated method stub
		waitForPageToLoad();
		WebElement UserName_Editicon=element("ContactDetailPage_IMG_UserName_xpath");
		WebElement Email_Editicon=element("ContactDetailPage_IMG_Email_xpath");
		WebElement Mobile_Editicon=element("ContactDetailPage_IMG_Mobile_xpath");
		if(!UserName_Editicon.isDisplayed()){
			reportError("UserName_Editicon is not Displayed");
		}
		if(!Email_Editicon.isDisplayed()){
			reportError("Email_Editicon is Displayed");
		}
		if(!Mobile_Editicon.isDisplayed()){
			reportError("Mobile_Editicon is Displayed");
		}
		return Constants.KEYWORD_RESULT_PASS;
	}
	
	
	private void verifyContactPage_ProfilePage() {
		// TODO Auto-generated method stub
		String ContactPage_userName=element("ContactDetailPage_EB_UserName_xpath").getAttribute("value").trim();
		String ContactPage_ID=element("ContactDetailPage_EB_Email_xpath").getAttribute("value").trim();
		String ContactPage_Mobile=element("ContactDetailPage_EB_Mobile_xpath").getAttribute("value").trim();
		String ContactPage_Location=element("ContactDetailPage_DD_City_xpath").getText().trim();
		System.err.println(ContactPage_userName);
		System.err.println(ContactPage_ID);
		System.err.println(ContactPage_Mobile);
		System.err.println(ContactPage_Location);
		click("MainWrap_BTN_UserName_xpath");
		String Profile_ID=element("Profile_TXT_Email_xpath").getText().trim();
		String Profile_userName=element("Profile_TXT_Name_xpath").getText().trim();
		String Profile_Mobile=element("Profile_TXT_Mobile_xpath").getText().trim();
		String Profile_Location=element("Profile_TXT_Location_xpath").getText().trim();
		System.err.println(Profile_userName);
		System.err.println(Profile_ID);
		System.err.println(Profile_Mobile);
		System.err.println(Profile_Location);
		if(!ContactPage_ID.equalsIgnoreCase(Profile_ID)){
			reportError("verifyContactPage_ProfilePage = User ID not matching ");
		}
		if(!ContactPage_userName.equalsIgnoreCase(Profile_userName)){
			reportError("verifyContactPage_ProfilePage = userName not matching ");
		}
		if(!ContactPage_Mobile.equalsIgnoreCase(Profile_Mobile)){
			reportError("verifyContactPage_ProfilePage = Mobile not matching ");
		}
		if(!Profile_Location.equalsIgnoreCase(ContactPage_Location)){
			//System.err.println("Inside if Loop");
			if(Profile_Location.equalsIgnoreCase("")&&ContactPage_Location.equalsIgnoreCase("Select City")){
				System.err.println("Workiing properly");
			}else{
				reportError("verifyContactPage_ProfilePage = Location not matching ");
			}
		}
	}


	private void verfiyContactPageforSubmitBTN() {
		// TODO Auto-generated method stub
		String Name=element("ContactDetailPage_EB_UserName_xpath").getAttribute("value");
		String email=element("ContactDetailPage_EB_Email_xpath").getAttribute("value");
		String mobile=element("ContactDetailPage_EB_Mobile_xpath").getAttribute("value");
		String City=element("ContactDetailPage_DD_City_xpath").getText().trim();
		System.err.println(City);
			if(Name.equalsIgnoreCase("")||email.equalsIgnoreCase("")||mobile.equals("+91-")||City.trim().equalsIgnoreCase("Select City")||City.trim().equalsIgnoreCase("Other City")){
		
			if(City.equalsIgnoreCase("Other City")){
				String otherCity=element("ContactDetailPage_EB_OtherCity_xpath").getAttribute("value");
				if(otherCity.equalsIgnoreCase("")){
					if(!element("ContactDetailPage_BTN_Submit_xpath").getAttribute("class").equalsIgnoreCase("next_btn")){
						reportError("Submit Button is enabled It should be disabled");
						return;
					}
				}
			}else if(!element("ContactDetailPage_BTN_Submit_xpath").getAttribute("class").equalsIgnoreCase("next_btn")){
				reportError("Submit Button is enabled It should be disabled");
			}else{
				System.err.println("Working fine");
			}

		}

		
	}

	public void waitforTime(long time){
		log("Waiting for Second = "+time);
		try {TimeUnit.SECONDS.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void doUserLogin(Hashtable<String, String> data) {
		// TODO Auto-generated method stub
		System.err.println("WLoginWindow_BTN_Login_xpath = "+WebWait(driver,"LoginWindow_BTN_Login_xpath"));
		if(WebWait(driver,"LoginWindow_BTN_Login_xpath")){
			waitforTime(2);
		}
		input("LoginWindow_EB_Email_xpath", data.get("Data_Email"));
		input("LoginWindow_EB_Password_xpath",data.get("Data_Password"));
		click("LoginWindow_BTN_Login_xpath");
		System.err.println(WebWait(driver,"MainWrap_BTN_Logout_xpath"));
		int i=0;
		while(!WebWait(driver,"MainWrap_BTN_Logout_xpath")){
			if(i==10){
				reportError("Unable To find Element MainWrap_BTN_Logout_xpath");
				break;
			}
			waitforTime(1);
			i++;
		}
		
	}


	private void verifyLoginPresent(String object) {
		// TODO Auto-generated method stub
		String visiblity = element("MainWrap_BTN_Login_xpath").getAttribute("class");
		System.err.println(visiblity);
		if(object.equalsIgnoreCase("YES")){
			if(visiblity.equalsIgnoreCase("disablenavigation")){
				reportError("verifyElementPresent : Element not Found");
			}	
		}else if (object.equalsIgnoreCase("NO")) {
			if(!visiblity.equalsIgnoreCase("disablenavigation")){
				reportError("verifyElementPresent : Element Found = ");
			}
		}else {
			reportError("No Expected Condition found : YES or NO");
		}
		
	}


	private void deleteFloorPlan(String string) {
		// TODO Auto-generated method stub
		WebElement datashow=element("Upload_CON_DataShow_xpath");
		List<WebElement> image_preview=datashow.findElements(By.id("image-preview"));
		System.err.println(image_preview.size());
		int i;
		for(i=0;i<image_preview.size();i++){
			String fileName=image_preview.get(i).findElement(By.tagName("img")).getAttribute("src");
			
			if(image_preview.get(i).findElement(By.tagName("img")).getAttribute("src").contains(string)){
				Actions act=new Actions(driver);
				act.moveToElement(image_preview.get(i));
				System.err.println(image_preview.get(i).findElement(By.tagName("img")).getAttribute("src"));
				String imagename=image_preview.get(i).findElement(By.tagName("a")).getAttribute("id");
				System.err.println(image_preview.get(i).findElement(By.tagName("a")).getAttribute("id"));
				System.err.println(element(".//*[@id='"+imagename+"']").isDisplayed());
				act.build().perform();	
				driver.findElement(By.xpath(".//*[@id='"+imagename+"']")).click();
				break;
			}
			
		}if(i==image_preview.size()){
			reportError("deleteFloorPlan = image not found "+string);
		}
	}


	private void verifyBTNtext(String object, String Name) {
		log(LogStatus.INFO, "Inside function verifyBTNtext");
		String BTN_Name=element(object).getText();
		if(!BTN_Name.equalsIgnoreCase(Name)){
			reportError("verifyBTNtext = Mismatched");
		}else{
			System.err.println("Text Matched "+BTN_Name +"===="+Name);
		}
	}


	public boolean isMultiple(String object){
		log(LogStatus.INFO, "Inside function selectCard");
		String CON_Start_xpath=".//*[@id='";
		String CON_End_xpath="']/div";
		String roomdesign=prop.getProperty(object.split(",")[0]);		
		String type=prop.getProperty(object.split(",")[1]);
		String CON_path=CON_Start_xpath+roomdesign+CON_End_xpath;
		//System.err.println(isElementPresent("RoomDesign_BTN_Next_css",5));
		WebElement designContainer=element(CON_path);
		WebElement Header=designContainer.findElement(By.className("title_info")).findElement(By.tagName("h2"));
		WebElement Description=designContainer.findElement(By.className("title_info")).findElement(By.tagName("p"));
		//System.err.println(Header.getText());
		//System.err.println(Description.getText());
		List<WebElement> ColorPreferenceList=designContainer.findElements(By.className("roomdesign_view"));
		int selectCount=0;
		
		for(int i=0;i<ColorPreferenceList.size();i++){
			WebElement ColorPreferenceListimg=ColorPreferenceList.get(i).findElement(By.cssSelector(".roomdesign_viewimg"));
			WebElement ColorPreferenceListcontclearfix=ColorPreferenceList.get(i).findElement(By.cssSelector(".roomdesign_viewcont.clearfix"));
			WebElement RoomName=ColorPreferenceListcontclearfix.findElement(By.className("ng-binding"));
			WebElement RoomSelect=ColorPreferenceListcontclearfix.findElement(By.className("checked_img"));
			if(RoomSelect.isDisplayed()){
				System.err.println(RoomName.getText());
				selectCount++;
			}
	}
		if(selectCount>1&&type.equalsIgnoreCase("Multiple")){
			System.err.println("Its a Multiselected");
			return true;
		}else if(selectCount==1&&type.equalsIgnoreCase("Single")){
		System.err.println("Its a Single selected");
		return true;}
		else{
			reportError(object.split(",")[0]+" is Not an "+type);
		}
		
		return false;
	}
	
	
	private void facebookLogin(Hashtable<String, String> data) {
		// TODO Auto-generated method stub
		Set<String> Handles=driver.getWindowHandles();
		driver.switchTo().window((String) Handles.toArray()[1]);
		System.err.println(driver.getTitle());
		String PageTitle=prop.getProperty("FacebookTitle");
		if(!driver.getTitle().trim().equalsIgnoreCase(PageTitle.trim())){
			reportError("Page Title Mismatched");
		}
		waittoLoad();
		element("FB_EB_Email_xpath").sendKeys(data.get("Data_Email"));
		element("FB_EB_Password_xpath").sendKeys(data.get("Data_Password"));
		element("FB_BTN_Login_xpath").click();
		waittoLoad();
		driver.switchTo().window((String) Handles.toArray()[0]);
		if(!element("MainWrap_BTN_Logout_xpath").isDisplayed()){
			reportError("Logout is not visible");
		}
	}


	private void verifyCity() {
		// TODO Auto-generated method stub
		ArrayList<String> Expected=new ArrayList<>();
		Expected.add("Select City");
		Expected.add("Hyderabad");
		Expected.add("Pune");
		Expected.add("Jaipur");
		Expected.add("Other City");
		
		WebElement CityCon=element("SignUp_CON_City_xpath");
		List<WebElement> CityList=CityCon.findElements(By.tagName("li"));
		//System.err.println(CityList.size());
		if(CityList.size()!=Expected.size()){
			reportError("Select City Dropdown size Mismatced");
		}
		for(int i=0;i<CityList.size();i++){
			
			int j;
			for(j=0;j<Expected.size();j++){
				//System.out.println(CityList.get(i).getText()+"------"+Expected.get(j));
				//System.out.println(i+"----"+j);
				if(CityList.get(i).getText().equalsIgnoreCase(Expected.get(j))){
					System.err.println(CityList.get(i).getText()+"------"+Expected.get(j));
					break;
				}
			}
			//System.out.println(i+"----"+j+"----"+Expected.size());
			if(j==Expected.size()){
				//System.err.println("Inside IF loop");
				reportError("City Name not found = "+CityList.get(i).getText());
			}
		}
	}


	private void MoveToElementClick(String xpath) {
		// TODO Auto-generated method stub
		WebElement _element=element(xpath);
		Actions _Actions=new Actions(driver);
		_Actions.moveToElement(_element).click().build().perform();
		
	}

	private void editFieldMaxLength(String Xpath_Length){
		String xpath=Xpath_Length.split(",")[0];
		String Length=Xpath_Length.split(",")[1];
		element(xpath).sendKeys("abcdefghijklmnopqrstuvwxyzaabbccddeeffgghhiijjkkllmmnnooppqqrrssttuuvvwwxxyyzz");
		String fatchedText=element(xpath).getAttribute("value");
		System.err.println(element(xpath).getAttribute("value")+ "====fatchedText length = "+element(xpath).getAttribute("value").length());
		System.err.println("Expected text lenght = "+Integer.parseInt(prop.getProperty(Length)));
		if(fatchedText.length()!=Integer.parseInt(prop.getProperty(Length))){
			// TODO Auto-generated method stub
			reportError("Field not matched");
		}
		
	}

	private void verfiySelectedTheme(Hashtable<String,String> data) {
		// TODO Auto-generated method stub
		ArrayList<String> themeName=new ArrayList<>();
		tagWishlist(data);
		WebElement ThemeCon=element("Profile_CON_MyWishlist_xpath");
		WebElement exploreDesign=ThemeCon.findElement(By.className("explore_design"));
		List<WebElement> themes=exploreDesign.findElements(By.cssSelector(".col-xs-12.col-md-6.col-lg-6.col-sm-6.ng-scope"));
		for(int i=0;i<themes.size();i++){
			themeName.add(themes.get(i).getAttribute("data-themeid"));
			System.err.println(themes.get(i).getAttribute("data-themeid"));
		}
		
		click("MainWrap_LINK_OffShelf_xpath");
		WebElement Design_Con=element("OffShelfDesign_CON_Designs_xpath");
		List<WebElement> Themes=Design_Con.findElements(By.tagName("li"));
		//System.err.println(Themes.size());
		int randomnumber=randomnumber(0, Themes.size()-1);
		System.err.println(randomnumber);
		System.err.println(Themes.get(randomnumber).getAttribute("id"));
		
		String ThemeCard=".//*[@id='"+Themes.get(randomnumber).getAttribute("id")+"']/div[1]/div/img";
		String Themewishlistheart=".//*[@id='"+Themes.get(randomnumber).getAttribute("id")+"']/i";
		WebElement Theme_Name_ele=element(ThemeCard);
		scrolltoElementusingWebElement(element(ThemeCard));
		Actions ac=new Actions(driver);
		ac.moveToElement(Theme_Name_ele).build().perform();
		String HeartPath=".//*[@id='Add"+Themes.get(randomnumber).getAttribute("id")+"']/i";
		System.err.println(element(HeartPath).isDisplayed());
		System.err.println(element(HeartPath).getAttribute("style"));
		if(element(HeartPath).getAttribute("style").equalsIgnoreCase("color: rgb(230, 230, 230)")){
			element(HeartPath).click();
		}
			
	}


	private void displayWishlist(Hashtable<String, String> data) {
		// TODO Auto-generated method stub
		ArrayList<String> themeName=new ArrayList<>();
		tagWishlist(data);
		WebElement ThemeCon=element("Profile_CON_MyWishlist_xpath");
		WebElement exploreDesign=ThemeCon.findElement(By.className("explore_design"));
		List<WebElement> themes=exploreDesign.findElements(By.cssSelector(".col-xs-12.col-md-6.col-lg-6.col-sm-6.ng-scope"));
		for(int i=0;i<themes.size();i++){
			themeName.add(themes.get(i).getAttribute("data-themeid"));
			System.err.println(themes.get(i).getAttribute("data-themeid"));
		}
		
		click("MainWrap_LINK_OffShelf_xpath");
		WebElement offshelfthemeCon=element("OffShelfDesign_CON_Designs_xpath");
		List<WebElement> themeList=offshelfthemeCon.findElements(By.tagName("li"));
		//System.err.println(themeList.size());
		
		for(int j=0;j<themeName.size();j++){
			int i=0;
			for(i=0;i<themeList.size();i++){
				System.err.println("============"+themeList.get(i).getAttribute("id")+"============");
				scrolltoElementusingWebElement(themeList.get(i));
				if(!themeName.get(j).equalsIgnoreCase(themeList.get(i).getAttribute("id"))){
					Actions act=new Actions(driver);
					act.moveToElement(themeList.get(i).findElement(By.className("explore_listimg"))).build().perform();
					//System.err.println(themeList.get(i).findElement(By.cssSelector(".des_wish_list")).isDisplayed());
					if(!themeList.get(i).findElement(By.cssSelector(".des_wish_list")).isDisplayed()){
						reportError("Wishlist added");
					}
				}else {
					System.out.println(themeList.get(i).getAttribute("id"));
					if(!themeList.get(i).findElement(By.cssSelector(".des_wish_list.activewishlist")).isDisplayed()){
						reportError("Wishlist not added");
					}break;
				}			
				}if(i==themeList.size()){
					System.err.println(themeList.get(i).getAttribute("id"));
				reportError("Theme not found");
			}
			
		}
		
		
		
	}
	
	
	
	private void tagWishlistTheme(String ThemeNumber) {
		//Verifying the theme is added or not
		isElementPresent("Profile_BTN_TagMyWishlist_xpath");
		int tagvalue=Integer.parseInt(element("Profile_BTN_TagMyWishlist_xpath").getText());
		System.err.println(tagvalue);
		click("Profile_BTN_MyWishlist_xpath");
		verifyText("Profile_TXT_MyWishlist_xpath","My Wishlist");
		WebElement ThemeCon=element("Profile_CON_MyWishlist_xpath");
		WebElement exploreDesign=ThemeCon.findElement(By.className("explore_design"));
		List<WebElement> themes=exploreDesign.findElements(By.cssSelector(".col-xs-12.col-md-6.col-lg-6.col-sm-6.ng-scope"));
		System.err.println(themes.size());
		if(tagvalue!=themes.size()){
			reportError("Tag Value and Theme list value not matched");
		}
		int i=0;
		for(i=0;i<themes.size();i++){
			System.err.println(themes.get(i).getAttribute("data-themeid"));
			if(themes.get(i).getAttribute("data-themeid").trim().equalsIgnoreCase(ThemeNumber.trim())){
				System.err.println("Theme is present");
				System.err.println("Value of i = "+i);
				break;
			}
		}
		System.err.println("Value of i = "+i);

		if(i==themes.size()){
		reportError("Theme Not Present = "+ThemeNumber);
	}
		
		
	
		
		
	}

	private void tagWishlist(Hashtable<String, String> data) {
		// TODO Auto-generated method stub
		isElementPresent("Profile_BTN_TagMyWishlist_xpath");
		int tagvalue=Integer.parseInt(element("Profile_BTN_TagMyWishlist_xpath").getText());
		System.err.println(tagvalue);
		click("Profile_BTN_MyWishlist_xpath");
		verifyText("Profile_TXT_MyWishlist_xpath","My Wishlist");
		WebElement ThemeCon=element("Profile_CON_MyWishlist_xpath");
		WebElement exploreDesign=ThemeCon.findElement(By.className("explore_design"));
		List<WebElement> themes=exploreDesign.findElements(By.cssSelector(".col-xs-12.col-md-6.col-lg-6.col-sm-6.ng-scope"));
		System.err.println(themes.size());
		if(tagvalue!=themes.size()){
			reportError("Tag Value and Theme list value not matched");
		}
	}


	private void selectWindowPopup(String object) {
		// TODO Auto-generated method stub
		String Element=object.split(",")[0];
		String WindowURL=object.split(",")[1];
		String parentHandle =driver.getWindowHandle();
		click(Element);
		for (String winHandle : driver.getWindowHandles()) {
		    driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
		}
		if(!driver.getCurrentUrl().equalsIgnoreCase(prop.getProperty(WindowURL))){
			reportError("Wrong popup window : CurrentURL = "+driver.getCurrentUrl()+" Expected Window URL = "+prop.getProperty(WindowURL));
		}
		driver.close();
		driver.switchTo().window(parentHandle);
		
	}


	private void linkClick(String object) {
		// TODO Auto-generated method stub
		driver.findElement(By.linkText(prop.getProperty(object))).click();
	}


	private void selectCarouselSlide() {
		// TODO Auto-generated method stub
		WebElement ProjectlistLeft=element("offShelfDesign_CON_ProjectList_css");
		String projectListID=null;
		List<WebElement> projectList=ProjectlistLeft.findElements(By.className("project_list"));
		for(int pro=0;pro<projectList.size();pro++){
			if(projectList.get(pro).getAttribute("style").equalsIgnoreCase("display: block;")){
				projectListID=projectList.get(pro).getAttribute("id");
				System.err.println(projectListID);
				break;
			}
			
		}
		
		WebElement SelectedProject=element(".//*[@id='"+projectListID+"']");
		WebElement carouselInner=SelectedProject.findElement(By.cssSelector(".carousel.slide"));
		System.err.println(carouselInner.getAttribute("id"));
		String nextButton=".//*[@id='"+carouselInner.getAttribute("id")+"']/a[2]";
		String GetItButton=".//*[@id='"+carouselInner.getAttribute("id")+"']/div[3]/a";
		//System.err.println("Next ="+element(nextButton).isDisplayed());
		//System.err.println("GetIT ="+element(GetItButton).isDisplayed());
		List<WebElement> InnerItems=carouselInner.findElements(By.cssSelector(".inneritem"));
		System.err.println(InnerItems.size());
		if(InnerItems.size()>1){
			int number=	randomnumber(0, InnerItems.size()-1);
			System.err.println("Number = "+number);
			if(number>0){
				for(int j=0;j<number;j++){
					
					click(nextButton);
				}
			}
			
			
		}
		click(GetItButton);
		
	}


	private void selectRoomType(String roomtype) {
		// TODO Auto-generated method stub
		WebElement project_listright=element("offShelfDesign_CON_Leftlist_css");
		List<WebElement> roomtypes=project_listright.findElements(By.tagName("li"));
		System.err.println(roomtypes.size());
		int i;
		for(i=0;i<roomtypes.size();i++){
			String room=roomtypes.get(i).getAttribute("h6");
			if(room.equalsIgnoreCase(roomtype)){
				scrolltoElementusingWebElement(roomtypes.get(i));
				roomtypes.get(i).click();
				break;
			}
		}if(i==roomtypes.size()){
			reportError("Room not found");
		}
		
	}


	private void selectRandomRoomType() {
		// TODO Auto-generated method stub
		WebElement project_listright=element("offShelfDesign_CON_Leftlist_css");
		List<WebElement> roomtypes=project_listright.findElements(By.tagName("li"));
		System.err.println(roomtypes.size());
		int Number=randomnumber(0, roomtypes.size()-1);
		scrolltoElementusingWebElement(roomtypes.get(Number));
		roomtypes.get(Number).click();
		
		
	}


	private void selectOffShelfDesign(String Theme) {

		WebElement Design_Con=element("OffShelfDesign_CON_Designs_xpath");
		List<WebElement> Themes=Design_Con.findElements(By.tagName("li"));
		int i;
		for(i=0;i<Themes.size();i++){
			String ThemeName=Themes.get(i).getAttribute("id");
			if(ThemeName.equalsIgnoreCase(Theme)){
				String ThemeCard=".//*[@id='"+Themes.get(i).getAttribute("id")+"']/div[1]/div/img";
				scrolltoElementusingWebElement(element(ThemeCard));
				element(ThemeCard).click();
				break;
			}
		}
		if(i==Themes.size()){
			reportError("Theme not availabe");
		}
		
	}
	private void selectRandomRoom(){
		WebElement project_listright=element("OTSTPage_CON_Listright_xpath");
		List<WebElement> projectList_list=project_listright.findElements(By.tagName("li"));
		System.out.println(projectList_list.size());
		int NumberRandom=randomnumber(0, projectList_list.size()-1);
		System.err.println(NumberRandom);
		scrolltoElementusingWebElement(projectList_list.get(NumberRandom));
		System.err.println(projectList_list.get(NumberRandom).findElement(By.tagName("h6")).getText());
		projectList_list.get(NumberRandom).click();
	}

	
	private void selectRandomOffShelfDesign(){
		WebElement Design_Con=element("OffShelfDesign_CON_Designs_xpath");
		List<WebElement> Themes=Design_Con.findElements(By.tagName("li"));
		//System.err.println(Themes.size());
		int randomnumber=randomnumber(0, Themes.size()-1);
		System.err.println(randomnumber);
		System.err.println(Themes.get(randomnumber).getAttribute("id"));
		String ThemeCard=".//*[@id='"+Themes.get(randomnumber).getAttribute("id")+"']/div[1]/div/img";
		scrolltoElementusingWebElement(element(ThemeCard));
		element(ThemeCard).click();
	}
	
	
	private void verifyFileUpload(String Filelist) {
		log(LogStatus.INFO, "Inside function verifyFileUpload");
		String[] files=Filelist.split(",");
		WebElement DataShow=element("Upload_CON_DataShow_xpath");
		List<WebElement> imagePriview=DataShow.findElements(By.id("image-preview"));
		for(int i=0;i<files.length;i++){
			int j;
			for(j=0;j<imagePriview.size();j++){
			String uploadFileName=	imagePriview.get(j).findElement(By.tagName("img")).getAttribute("src");
			if(uploadFileName.contains(files[i])){
				System.err.println(files[i]+"===="+uploadFileName);
				break;
			}
			}if(j==imagePriview.size()){
				reportError("File not found ="+files[i]);
			}
		}
	}


	private void defaultUpload() {
		log(LogStatus.INFO, "Inside function defaultUpload");
		if(!element("Upload_BTN_BrowseClick_xpath").getText().equalsIgnoreCase("or BROWSE")){
			reportError("Button Name is wrong");
		}
		if(!element("Upload_IMG_Upload_xpath").isDisplayed()){
			reportError("image is not displayed");
		}
		
	}


	public boolean FileExists(File _File){
		if (!_File.exists()) {
			System.err.println("File not Exists");
			return false;
		}
		System.out.println("File Exists");
		return true;
		}	

	 public boolean FileExtension(File _File){
		// System.err.println(_File.getAbsolutePath());
		// System.err.println(_File.getName());
		 String extension=FilenameUtils.getExtension(_File.getAbsolutePath());
		 if(extension.equals("jpg")||extension.equals("png")||extension.equals("pdf")){
				System.out.println("Accepted");
				return true;
			}else{
				System.err.println("File extenstion not accepted");
				return false;
			}
		 
	 }	
	 
	 public boolean FileSize(File _File){
		 double filebyte=_File.length();
		 double fileKB=filebyte/1024;
		 double fileMB=fileKB/1024;
		 if(fileMB<=4.0){
				return true;
			}else if(fileMB>4.0){
				return false;
			}
		return false;
		 
	 }
	
	
	
	private void selectTnC(String string) {
		log(LogStatus.INFO, "Inside function selectTnC = "+string);
		switch (string.toLowerCase()) {
		case "true":
			System.err.println("Inside true function");
			element("SignUp_CHK_Terms_xpath").click();
			break;	
		default:
			break;
		}
		
	}


	public void selectCity(String City){
		WebElement Elecity=element("SignUp_SEL_City_xpath");
		Elecity.click();
		WebElement CityCon=element("SignUp_CON_City_xpath");
		List<WebElement> CityList=CityCon.findElements(By.tagName("li"));
		for(int i=0;i<CityList.size();i++){
			System.err.println(CityList.get(i).findElement(By.className("text")).getText());
			if(CityList.get(i).findElement(By.className("text")).getText().equalsIgnoreCase(City)){
				CityList.get(i).click();
				break;
			}
		}
	}
	
	
	private void Test(){
		String ThemeNumber="THMID0001";
		String ThemeConPath=".//*[@id='"+ThemeNumber+"']";
		System.err.println(ThemeConPath);
		//CON.findElement(By.xpath(""))
		WebElement ThemeCon=element(ThemeConPath);
		//scrolltoElementusingWebElement(ThemeCon);
		String Theme_Name=".//*[@id='"+ThemeNumber+"']/div[2]/div[1]/h3";
		WebElement Theme_Name_ele=element(Theme_Name);
		Actions ac=new Actions(driver);
		ac.moveToElement(Theme_Name_ele).build().perform();
		String HeartPath=".//*[@id='Add"+ThemeNumber+"']/i";
		System.err.println(element(HeartPath).isDisplayed());
		//ac.perform();
		if(!element(HeartPath).getAttribute("style").equalsIgnoreCase("color: rgb(230, 230, 230);")){
			reportError("Wishlist still Selected");
		}
	}
	
	
	private void verifyThemeSelection(String ThemeNumber){
		//WebElement CON=element("OffShelfDesign_CON_Designs_xpath");				
		String ThemeConPath=".//*[@id='"+ThemeNumber+"']";
		System.err.println(ThemeConPath);
		//CON.findElement(By.xpath(""))
		WebElement ThemeCon=element(ThemeConPath);
		scrolltoElementusingWebElement(ThemeCon);
		String Theme_Name=".//*[@id='"+ThemeNumber+"']/div[2]/div[1]/h3";
		WebElement Theme_Name_ele=element(Theme_Name);
		Actions ac=new Actions(driver);
		ac.moveToElement(Theme_Name_ele).build();
		String HeartPath=".//*[@id='Add"+ThemeNumber+"']/i";
		System.err.println(HeartPath);
		System.err.println(element("HeartPath").isDisplayed());
		ac.perform();
		
				
			}
		
	
	
	
	private void removeThemeFromProfileWishlist(Hashtable<String, String> data) {
		WebElement MyWishlistsCon=element("Profile_CON_MyWishlist_xpath").findElement(By.className("explore_design"));
		List<WebElement> MyWishList=MyWishlistsCon.findElements(By.cssSelector(".col-xs-12.col-md-6.col-lg-6.col-sm-6.ng-scope"));
		if(MyWishList.size()==0){
			log("Wishlist is Empty");
			return;
		}
		int ranwish=randomnumber(0,MyWishList.size()-1);
		scrolltoElementusingWebElement(MyWishList.get(ranwish));
		String ThemeID=MyWishList.get(ranwish).getAttribute("data-themeid");
		String ThemeName=MyWishList.get(ranwish).findElement(By.cssSelector(".explore_thmb_info")).findElement(By.tagName("h3")).getText();
		System.err.println(ThemeID);
		MyWishList.get(ranwish).findElement(By.cssSelector(".explore_listimg")).click();
		verifyLandingPage("Profile_URL_Theme");
		String ThemeNamePage=element(".//*[@id='projectlist_1']/div[1]").findElement(By.tagName("h4")).getText();
		if(!ThemeName.equalsIgnoreCase(ThemeNamePage)){
			reportError("Theme Name Not Matched");
		}
		System.err.println(element("OffShelfDesign_Heart_css").getAttribute("style"));
		if(!element("OffShelfDesign_Heart_css").getAttribute("style").equalsIgnoreCase("color: rgb(255, 46, 99);"))
		{
			reportError("Heart image is already deselected");
		}
		click("OffShelfDesign_Heart_css");
		if(!element("offShelfDesign_TXT_Msg_xpath").getText().equalsIgnoreCase("Removed from your wishlist!")){
			reportError("Error not displayed");
		}else if(element("offShelfDesign_TXT_Msg_xpath").getText().equalsIgnoreCase("Removed from your wishlist!")){
			System.err.println("Correct Msg displayed");
		}
		click("offShelfDesign_BTN_Back_xpath");
		//verifyThemeSelection(ThemeID);
		Test();
		
		
	}


	private void back_next_list(String object) {
		log(LogStatus.INFO, "Inside function back_next_list = "+object);
		waitForPageToLoad();
		String Back=object.split(",")[0];
		String Next=object.split(",")[1];
		String List=object.split(",")[2];
		BackButtonState(Back);
		nextButtonState(Next);
		verifyPageNumber(List);
	}

	private int randomnumber(int min,int max){
        Random rand = new Random();
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
	
	
	
	private void logOut() {
		log(LogStatus.INFO, "Inside function Logout");
		if(!isElementPresent("MainWrap_BTN_Logout_xpath")){
			reportError("Logout Button is not present");
		}
		click("MainWrap_BTN_Logout_xpath");
		verifyLandingPage("Profile_URL_Logout");
	}


	private void verifySelectedCard(String object, String items) {
		log(LogStatus.INFO, "Inside function verifySelectedCard");
		ArrayList<String> ArrayRoomDesign=new ArrayList<String>();
		for(int i=0;i<items.split(",").length;i++){
			ArrayRoomDesign.add(items.split(",")[i]);
		}
		System.out.println("ArrayList = "+ArrayRoomDesign.toString());
		
		String CON_Start_xpath=".//*[@id='";
		String CON_End_xpath="']/div";
		String roomdesign=prop.getProperty(object);		
		
		String CON_path=CON_Start_xpath+roomdesign+CON_End_xpath;
		//System.err.println(isElementPresent("RoomDesign_BTN_Next_css",5));
		WebElement designContainer=element(CON_path);
		WebElement Header=designContainer.findElement(By.className("title_info")).findElement(By.tagName("h2"));
		WebElement Description=designContainer.findElement(By.className("title_info")).findElement(By.tagName("p"));
		//System.err.println(Header.getText());
		//System.err.println(Description.getText());
		List<WebElement> ColorPreferenceList=designContainer.findElements(By.className("roomdesign_view"));
		for(int roomtype=0;roomtype<ArrayRoomDesign.size();roomtype++){
		for(int i=0;i<ColorPreferenceList.size();i++){
			WebElement ColorPreferenceListimg=ColorPreferenceList.get(i).findElement(By.cssSelector(".roomdesign_viewimg"));
			WebElement ColorPreferenceListcontclearfix=ColorPreferenceList.get(i).findElement(By.cssSelector(".roomdesign_viewcont.clearfix"));
			WebElement RoomName=ColorPreferenceListcontclearfix.findElement(By.className("ng-binding"));
			WebElement RoomSelect=ColorPreferenceListcontclearfix.findElement(By.className("checked_img"));
			if(RoomName.getText().trim().equalsIgnoreCase(ArrayRoomDesign.get(roomtype))){
				if(!RoomSelect.isDisplayed()){
					reportError("card select not matched for = "+ArrayRoomDesign.get(roomtype));
				}else if(RoomSelect.isDisplayed()){
					log("card select matched for = "+ArrayRoomDesign.get(roomtype));
					break;
				}
				
			}
	}}	
	}


	private void verifyLandingPage(String object) {
		// TODO Auto-generated method stub
		log(LogStatus.INFO, "Inside function verifyLandingPage = "+object);
		String curruntURL=driver.getCurrentUrl();
		if(!curruntURL.equalsIgnoreCase(prop.getProperty(object))){
			reportError("URL NOT MATCHED - CurrentURL = "+curruntURL+" Expected URL = "+prop.getProperty(object));
		}
	}
	
	
	public boolean WebWait(WebDriver _Driver,String path){
		WebDriverWait wait=new WebDriverWait(_Driver, 10);
		try{
			if(path.contains("_id")){
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(prop.getProperty(path))));
			}
			else if(path.contains("_xpath")){
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty(path))));
			}else if(path.contains("_link")){
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty(path))));
			}else if(path.contains("_name")){
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(prop.getProperty(path))));
			}
		
		}
		catch(Exception e){
			System.err.println("Element not found");
			return false;
		}
		return true;
}
	
	private void LandingPage(String object) {
		// TODO Auto-generated method stub
		log(LogStatus.INFO, "Inside function LandingPage = "+object);
		String env=	prop.getProperty("env");
		String urlPath=object+"_"+env;
		String curruntURL=driver.getCurrentUrl();
		if(!curruntURL.equalsIgnoreCase(prop.getProperty(urlPath))){
			reportError("URL NOT MATCHED - CurrentURL = "+curruntURL+" Expected URL = "+urlPath+"====="+prop.getProperty(urlPath));
		}
	}
	
	private void defaultUserLogin() {
		//ExtentLog(LogStatus.INFO,TestID+" : "+TestStepDescription);
		log("Inside function defaultUserLogin");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		if(!isElementPresent("LoginWindow_CON_Popup_xpath")){
			reportError("Login Popup missing");
		}
		//System.err.println("isLogin() = "+isLogin());
		String userName=prop.getProperty("userName");
		String passWord=prop.getProperty("passWord");
		input("LoginWindow_EB_Email_xpath", userName);
		input("LoginWindow_EB_Password_xpath", passWord);
		click("LoginWindow_BTN_Login_xpath");
		/*
		int i=0;
		while(!isElementPresent("LoginWindow_CON_Popup_xpath")){
			if(i==15){
				System.err.println("i ="+i);
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}*/
		/*
		if(isElementPresent("Alertpop_CON_popup_xpath")){
			reportError("Unable to Login");
		}
		*/
		WebWait(driver, "MainWrap_BTN_Logout_xpath");
		System.err.println(isElementPresent("MainWrap_BTN_Logout_xpath"));
		/*
		if(!((isElementPresent("MainWrap_BTN_UserName_xpath")||isElementPresent("MainWrap_BTN_Name_xpath"))&& isElementPresent("MainWrap_BTN_Logout_xpath"))){
			System.err.println(isElementPresent("MainWrap_BTN_UserName_xpath"));
			System.err.println(isElementPresent("MainWrap_BTN_Name_xpath"));
			System.err.println(isElementPresent("MainWrap_BTN_Logout_xpath"));
			reportError("Error username or logout is not displayed");
		}
		*/
		
		
	}

	private boolean isLogin(){
		System.err.println(isElementPresent("MainWrap_BTN_Login_xpath"));
		if(isElementPresent("MainWrap_BTN_Login_xpath")){
			return false;
		}
		return true;
		}
	
	
	private void isDefaultCardSelected(String object) {
		log(LogStatus.INFO, "Inside function isDefaultCardSelected");
		String CON_Start_xpath=".//*[@id='";
		String CON_End_xpath="']/div";
		String roomdesign=prop.getProperty(object);		
		ArrayList<String> SelectedRoom=new ArrayList<>();
		String CON_path=CON_Start_xpath+roomdesign+CON_End_xpath;
		//System.err.println(isElementPresent("RoomDesign_BTN_Next_css",5));
		WebElement Container=element(CON_path);
		List<WebElement> RoomdesignView=Container.findElements(By.className("roomdesign_view"));
		//System.err.println(RoomdesignView.size());
		
			for(int i=0;i<RoomdesignView.size();i++){
				WebElement roomdesignviewimg=RoomdesignView.get(i).findElement(By.cssSelector(".roomdesign_viewimg"));
				WebElement roomdesignviewcontclearfix=RoomdesignView.get(i).findElement(By.cssSelector(".roomdesign_viewcont.clearfix"));
				WebElement RoomName=roomdesignviewcontclearfix.findElement(By.className("ng-binding"));
				WebElement RoomSelect=roomdesignviewcontclearfix.findElement(By.className("checked_img"));
				if(RoomSelect.isDisplayed()){
					SelectedRoom.add(RoomName.getText());
				}
				
		}
		if(SelectedRoom.size()!=0){
			reportError("By Default Card are selected in "+object+" = "+SelectedRoom.toString());
		}else{
			System.err.println("Default No Card selected in "+object);
		}
			
	
		
	}


	private void verifyPageNumber(String object) {
		log(LogStatus.INFO, "Inside function verifyPageNumber = "+object );
		String CurruntPageNumber=prop.getProperty(object);
		WebElement countList=element("MainWrap_TXT_PageNumber_xpath");
		//System.err.println("CurruntPageNumber.trim().equalsIgnoreCase(countList.getText()) = "+CurruntPageNumber.trim().equalsIgnoreCase(countList.getText()));
		if(!CurruntPageNumber.equalsIgnoreCase(countList.getText())){
			reportError("PageNumber Not Matched : CurruntPageNumber = " +CurruntPageNumber+" countList = "+countList.getText());
		}else{
			//System.err.println("Page Number Matched");
		}
	}


	private void selectCard(String object, String items) {
		log(LogStatus.INFO, "Inside function selectCard ="+object+"----"+items);
		
		ArrayList<String> ArrayRoomDesign=new ArrayList<String>();
		for(int i=0;i<items.split(",").length;i++){
			ArrayRoomDesign.add(items.split(",")[i]);
		}
		//System.out.println("ArrayList = "+ArrayRoomDesign.toString());
		
		String CON_Start_xpath=".//*[@id='";
		String CON_End_xpath="']/div";
		String roomdesign=prop.getProperty(object);		
		
		String CON_path=CON_Start_xpath+roomdesign+CON_End_xpath;
		//System.err.println(isElementPresent("RoomDesign_BTN_Next_css",5));
		WebElement designContainer=element(CON_path);
		WebElement Header=designContainer.findElement(By.className("title_info")).findElement(By.tagName("h2"));
		WebElement Description=designContainer.findElement(By.className("title_info")).findElement(By.tagName("p"));
		//System.err.println(Header.getText());
		//System.err.println(Description.getText());
		List<WebElement> ColorPreferenceList=designContainer.findElements(By.className("roomdesign_view"));
		
		for(int roomtype=0;roomtype<ArrayRoomDesign.size();roomtype++){
		for(int i=0;i<ColorPreferenceList.size();i++){
			WebElement ColorPreferenceListimg=ColorPreferenceList.get(i).findElement(By.cssSelector(".roomdesign_viewimg"));
			WebElement ColorPreferenceListcontclearfix=ColorPreferenceList.get(i).findElement(By.cssSelector(".roomdesign_viewcont.clearfix"));
			WebElement RoomName=ColorPreferenceListcontclearfix.findElement(By.className("ng-binding"));
			
			if(RoomName.getText().trim().equalsIgnoreCase(ArrayRoomDesign.get(roomtype))){
				ColorPreferenceList.get(i).click();
				break;
			}
	}}

	}


	private void isDefaultColorSelected(Hashtable<String, String> data) {
		log(LogStatus.INFO, "Inside function isDefaultColorSelected");
		ArrayList<String> SelectedRoom=new ArrayList<>();
		WebElement designContainer=element("ColorPref_CON_Style_xpath");
		WebElement Header=designContainer.findElement(By.className("title_info")).findElement(By.tagName("h2"));
		WebElement Description=designContainer.findElement(By.className("title_info")).findElement(By.tagName("p"));
		//System.err.println(Header.getText());
		//System.err.println(Description.getText());
		List<WebElement> RoomdesignView=designContainer.findElements(By.className("roomdesign_view"));
		//System.err.println(RoomdesignView.size());
		
			for(int i=0;i<RoomdesignView.size();i++){
				WebElement roomdesignviewimg=RoomdesignView.get(i).findElement(By.cssSelector(".roomdesign_viewimg"));
				WebElement roomdesignviewcontclearfix=RoomdesignView.get(i).findElement(By.cssSelector(".roomdesign_viewcont.clearfix"));
				WebElement RoomName=roomdesignviewcontclearfix.findElement(By.className("ng-binding"));
				WebElement RoomSelect=roomdesignviewcontclearfix.findElement(By.className("checked_img"));
				if(RoomSelect.isDisplayed()){
					SelectedRoom.add(RoomName.getText());
				}
				
		}
		if(SelectedRoom.size()!=0){
			reportError("By Default Color are selected = "+SelectedRoom.toString());
		}else{
			System.err.println("Default No Color selected");
		}
			
	}
	
	
	private void verifyStyleTypes() {
		log(LogStatus.INFO, "Inside function verifyStyleTypes");
		ArrayList<String> TotalRooms=new ArrayList<>();
		int dataEndRow=2;
		while(!m_xls.getCellData("StylePageList","Data_StyleName",dataEndRow).equalsIgnoreCase("")){
			//System.err.println(m_xls.getCellData("RoomDesignList","Data_RoomName",dataEndRow)+"=="+dataEndRow);
			TotalRooms.add(m_xls.getCellData("StylePageList","Data_StyleName",dataEndRow).trim());
			dataEndRow++;
		}
		//System.err.println("Total Number of Rows = "+(dataEndRow-1));
		//System.err.println(TotalRooms.toString());
		WebElement designContainer=element("StylePref_CON_Style_xpath");
		List<WebElement> RoomdesignView=designContainer.findElements(By.className("roomdesign_view"));
		//System.err.println(RoomdesignView.size());
		
			for(int i=0;i<RoomdesignView.size();i++){
				WebElement roomdesignviewimg=RoomdesignView.get(i).findElement(By.cssSelector(".roomdesign_viewimg"));
				WebElement roomdesignviewcontclearfix=RoomdesignView.get(i).findElement(By.cssSelector(".roomdesign_viewcont.clearfix"));
				WebElement RoomName=roomdesignviewcontclearfix.findElement(By.className("ng-binding"));
				WebElement RoomSelect=roomdesignviewcontclearfix.findElement(By.className("checked_img"));
				int xlsRoomNo;
				for(xlsRoomNo=0;xlsRoomNo<TotalRooms.size();xlsRoomNo++){
					if(TotalRooms.get(xlsRoomNo).equalsIgnoreCase(RoomName.getText())){
						System.err.println(TotalRooms.get(xlsRoomNo)+"===="+RoomName.getText());
						break;
					}
				}
				if(xlsRoomNo==TotalRooms.size()){
					reportError("Room Type Not matched");
				}		
		}	
	}


	private void isStyleSelected(Hashtable<String, String> data) {
		log(LogStatus.INFO, "Inside function isStyleSelected");
		ArrayList<String> SelectedRoom=new ArrayList<>();
		WebElement designContainer=element("StylePref_CON_Style_xpath");
		WebElement Header=designContainer.findElement(By.className("title_info")).findElement(By.tagName("h2"));
		WebElement Description=designContainer.findElement(By.className("title_info")).findElement(By.tagName("p"));
		//System.err.println(Header.getText());
		//System.err.println(Description.getText());
		List<WebElement> RoomdesignView=designContainer.findElements(By.className("roomdesign_view"));
		//System.err.println(RoomdesignView.size());
		
			for(int i=0;i<RoomdesignView.size();i++){
				WebElement roomdesignviewimg=RoomdesignView.get(i).findElement(By.cssSelector(".roomdesign_viewimg"));
				WebElement roomdesignviewcontclearfix=RoomdesignView.get(i).findElement(By.cssSelector(".roomdesign_viewcont.clearfix"));
				WebElement RoomName=roomdesignviewcontclearfix.findElement(By.className("ng-binding"));
				WebElement RoomSelect=roomdesignviewcontclearfix.findElement(By.className("checked_img"));
				if(RoomSelect.isDisplayed()){
					SelectedRoom.add(RoomName.getText());
				}
				
		}
		if(SelectedRoom.size()!=0){
			reportError("By Default Rooms are selected = "+SelectedRoom.toString());
		}
			
	}
	private void verifyRoomTypes() {
		log(LogStatus.INFO, "Inside function verifyRoomTypes");
		ArrayList<String> TotalRooms=new ArrayList<>();
		int dataEndRow=2;
		while(!m_xls.getCellData("RoomDesignList","Data_RoomName",dataEndRow).equalsIgnoreCase("")){
			//System.err.println(m_xls.getCellData("RoomDesignList","Data_RoomName",dataEndRow)+"=="+dataEndRow);
			TotalRooms.add(m_xls.getCellData("RoomDesignList","Data_RoomName",dataEndRow).trim());
			dataEndRow++;
		}
		//System.err.println("Total Number of Rows = "+(dataEndRow-1));
		System.err.println(TotalRooms.toString());
		WebElement designContainer=element("RoomDesign_CON_Designs_xpath");
		//WebElement Header=designContainer.findElement(By.className("title_info")).findElement(By.tagName("h2"));
		//WebElement Description=designContainer.findElement(By.className("title_info")).findElement(By.tagName("p"));
		//System.err.println(Header.getText());
		//System.err.println(Description.getText());
		List<WebElement> RoomdesignView=designContainer.findElements(By.className("roomdesign_view"));
		//System.err.println(RoomdesignView.size());
		
			for(int i=0;i<RoomdesignView.size();i++){
				WebElement roomdesignviewimg=RoomdesignView.get(i).findElement(By.cssSelector(".roomdesign_viewimg"));
				WebElement roomdesignviewcontclearfix=RoomdesignView.get(i).findElement(By.cssSelector(".roomdesign_viewcont.clearfix"));
				WebElement RoomName=roomdesignviewcontclearfix.findElement(By.className("ng-binding"));
				WebElement RoomSelect=roomdesignviewcontclearfix.findElement(By.className("checked_img"));
				int xlsRoomNo;
				for(xlsRoomNo=0;xlsRoomNo<TotalRooms.size();xlsRoomNo++){
					if(TotalRooms.get(xlsRoomNo).equalsIgnoreCase(RoomName.getText())){
						System.err.println(TotalRooms.get(xlsRoomNo)+"===="+RoomName.getText());
						break;
					}
				}
				if(xlsRoomNo==TotalRooms.size()){
					reportError("Room Type Not matched");
				}
				
		}
		
	}


	private void BackButtonState(String object) {
		log(LogStatus.INFO, "Inside function BackButtonState = "+object);
		WebElement BaackButton=element("MainWrap_BTN_BACK_xpath");
		//System.err.println("*********"+NextButton.isDisplayed()+"*********");
		boolean objectbool=Boolean.parseBoolean(prop.getProperty(object));
		if(objectbool!=BaackButton.isDisplayed()){
			reportError("BackButtonState Error - Expected is ="+objectbool +"Actual is = "+BaackButton.isDisplayed());
		}
		
	}


	private void nextButtonState(String object) {
		log(LogStatus.INFO, "Inside function NextButtonState = "+object);
		WebElement NextButton=element("MainWrap_BTN_Next_xpath");
		String ButtonCssValue=NextButton.getAttribute("class");
		//System.err.println("*********"+ButtonCssValue+"*********");
		boolean enable=false;
		boolean objectbool=Boolean.parseBoolean(prop.getProperty(object));
		if(ButtonCssValue.equalsIgnoreCase("next_btn btn_enable")){
			enable=true;
		}
		if(enable!=objectbool){
			reportError("NextButtonState Error - Expected is ="+objectbool +"Actual is = "+enable);
		}
	}

	

	private void isRoomSelected(Hashtable<String, String> data) {
		log(LogStatus.INFO, "Inside function isRoomSelected");
		ArrayList<String> SelectedRoom=new ArrayList<>();
		WebElement designContainer=element("RoomDesign_CON_Designs_xpath");
		WebElement Header=designContainer.findElement(By.className("title_info")).findElement(By.tagName("h2"));
		WebElement Description=designContainer.findElement(By.className("title_info")).findElement(By.tagName("p"));
		//System.err.println(Header.getText());
		//System.err.println(Description.getText());
		List<WebElement> RoomdesignView=designContainer.findElements(By.className("roomdesign_view"));
		//System.err.println(RoomdesignView.size());
		
			for(int i=0;i<RoomdesignView.size();i++){
				WebElement roomdesignviewimg=RoomdesignView.get(i).findElement(By.cssSelector(".roomdesign_viewimg"));
				WebElement roomdesignviewcontclearfix=RoomdesignView.get(i).findElement(By.cssSelector(".roomdesign_viewcont.clearfix"));
				WebElement RoomName=roomdesignviewcontclearfix.findElement(By.className("ng-binding"));
				WebElement RoomSelect=roomdesignviewcontclearfix.findElement(By.className("checked_img"));
				if(RoomSelect.isDisplayed()){
					SelectedRoom.add(RoomName.getText());
					
				}
				
		}
		if(SelectedRoom.size()!=0){
			reportError("By Default Rooms are selected = "+SelectedRoom.toString());
		}
			
		
		
	}


	private void RoomTypeStaticText() {
		log(LogStatus.INFO, "Inside function RoomTypeStaticText");
		String Header=element("RoomDesign_TXT_Header_xpath").getText();
		String Description=element("RoomDesign_TXT_description_xpath").getText();
		int textRow=2;
		while(!m_xls.getCellData("RoomDesignText","Data_String",textRow).equalsIgnoreCase("String_Header")){
			textRow++;
		}
		//System.err.println("TextRow Found at ="+textRow);
		if(!Header.equalsIgnoreCase(m_xls.getCellData("RoomDesignText","Data_Description",textRow).trim())){
			reportError("IntroMessage Not matced - Actual text is "+Header+" Expected text is = "+m_xls.getCellData("RoomDesignText","Data_Description",textRow));
		}else{
			System.err.println("IntroMessage matced");
			}
		
		int textRow1=2;
		while(!m_xls.getCellData("RoomDesignText","Data_String",textRow1).equalsIgnoreCase("String_Description")){
			textRow1++;
		}
		//System.err.println("TextRow Found at ="+textRow1);
		if(!Description.equalsIgnoreCase(m_xls.getCellData("RoomDesignText","Data_Description",textRow1).trim())){
			reportError("IntroMessage Not matced - Actual text is "+Description+" Expected text is = "+m_xls.getCellData("RoomDesignText","Data_Description",textRow1));
		}
		
	}


	private void LetsRollIntromessageText() {
		log(LogStatus.INFO, "Inside function LetsRollIntromessageText");
		String EleText=element("IntroScreen_TXT_Msg_xpath").getText().replaceAll("(\\t|\\r?\\n)+"," ");
		int textRow=2;
		while(!m_xls.getCellData("LetsRoll","Data_String",textRow).equalsIgnoreCase("Introduction_Message")){
			textRow++;
		}
		System.err.println("TextRow Found at ="+textRow);
		if(!EleText.equalsIgnoreCase(m_xls.getCellData("LetsRoll","Data_Description",textRow).trim())){
			reportError("IntroMessage Not matced - Actual text is "+EleText+" Expected text is = "+m_xls.getCellData("LetsRoll","Data_Description",textRow));
		}
			
		
	}


	private void verifyNavigationOnClick(String object) {
		// TODO Auto-generated method stub
		//ExtentLog(LogStatus.INFO,TestID+" : "+TestStepDescription);
		log("Inside function verifyNavigationOnClick = "+object);
		String ButtonXpath=object.split(",")[0];
		String ExpectedNavigationURL=object.split(",")[1];
		click(ButtonXpath);
		waitForPageToLoad();
		//LandingPage
		LandingPage(ExpectedNavigationURL);
	}


	private void clickOnFoyrLogo() {
		// TODO Auto-generated method stub
		log(LogStatus.INFO, "Inside function clickOnFoyrLogo");
		if(!isElementPresent("MainWrap_IMG_Foyr_xpath", 10)){
			reportError("Foyr logo is not present");
		}
		String CurrentPageUrl=driver.getCurrentUrl();
		log("CurrentPageUrl = "+CurrentPageUrl);
		click("MainWrap_IMG_Foyr_xpath");
		String AfterClickPageUrl=driver.getCurrentUrl();
		log("AfterClickPageUrl = "+AfterClickPageUrl);
		if(!AfterClickPageUrl.equalsIgnoreCase(prop.getProperty("url_Foyr_icon"))){
			reportError("Image navigation not working = "+AfterClickPageUrl+"------"+prop.getProperty("url_Foyr_icon"));
		}else if(!isElementPresent("HomePage_BTN_GETSTARTED_xpath",10)){
			reportError("Element Not found on Page = HomePage_BTN_GETSTARTED_xpath");
		}
	}


	private void uploadpicFloorplan(Hashtable<String, String> data) {
		// TODO Auto-generated method stub
		String[] filetoupload=data.get("Data_Upload").split(",");
		System.err.println("filetoupload = "+filetoupload.length);
		String Exepath=null;
		for(int i=0;i<filetoupload.length;i++){
			element("Upload_BTN_Browse_xpath").click();
			Exepath=exeFileupload(data.get("Browser"));
			String FilePath=System.getProperty("user.dir")+"\\upload\\"+filetoupload[i];
			File _File=new File(FilePath);
			try {
				waittoLoad();
				Process _Process=new ProcessBuilder(Exepath,FilePath,"Open").start();
				waittoLoad();
			} catch (IOException e) {			
				e.printStackTrace();
			}
			if(!FileExists(_File)){
				reportError("File Not Exists");
			}
			if(!FileExtension(_File)){
				if(!element("Upload_TXT_MSG_xpath").getText().equalsIgnoreCase(data.get("Data_Msg"))){
					reportError("Error Message not Matching : "+"Expected Msg is = "+data.get("Data_Msg")+" Actual Msg is "+element("Upload_TXT_MSG_xpath").getText());
				}
			}
			if(!FileSize(_File)){
				if(!element("Upload_TXT_MSG_xpath").getText().equalsIgnoreCase(data.get("Data_Msg"))){
					reportError("Error Message not Matching : "+"Expected Msg is = "+data.get("Data_Msg")+" Actual Msg is "+element("Upload_TXT_MSG_xpath").getText());
				}
			}
		}
		
	}

	private  String exeFileupload(String browser){
		if (browser.equalsIgnoreCase("Chrome")) {
			 return System.getProperty("user.dir")+"\\upload\\testchrome.exe";
		} else if(browser.equalsIgnoreCase("Mozilla")) {
			return System.getProperty("user.dir")+"\\upload\\testMozilla.exe";
		}
		return currentBrowser;
	}
	
	
	private void selectBudgetPreferences(String Preferences) {
		log(LogStatus.INFO,"Inside function selectBudgetPreferences = "+Preferences);
		//System.err.println(isElementPresent("RoomDesign_BTN_Next_css",5));
		WebElement designContainer=element("BudgetPref_CON_Style_xpath");
		WebElement Header=designContainer.findElement(By.className("title_info")).findElement(By.tagName("h2"));
		WebElement Description=designContainer.findElement(By.className("title_info")).findElement(By.tagName("p"));
		//System.err.println(Header.getText());
		//System.err.println(Description.getText());
		List<WebElement> ColorPreferenceList=designContainer.findElements(By.className("roomdesign_view"));
		for(int i=0;i<ColorPreferenceList.size();i++){
			WebElement ColorPreferenceListimg=ColorPreferenceList.get(i).findElement(By.cssSelector(".roomdesign_viewimg"));
			WebElement ColorPreferenceListcontclearfix=ColorPreferenceList.get(i).findElement(By.cssSelector(".roomdesign_viewcont.clearfix"));
			WebElement RoomName=ColorPreferenceListcontclearfix.findElement(By.className("ng-binding"));
			
			if(RoomName.getText().trim().equalsIgnoreCase(Preferences)){
				ColorPreferenceList.get(i).click();
				break;
			}
	}
		System.err.println(element("RoomDesign_BTN_Next_css").isDisplayed());
		Actions act=new Actions(driver);
		act.moveToElement(element("RoomDesign_BTN_Next_xpath")).click().build().perform();
		
	}


	
	
	private void selectColorPreferences(String type,String Preferences) {
		log(LogStatus.INFO,"Inside function selectColorPreferences = "+Preferences);
		
		String CON_Start_xpath=".//*[@id='";
		String CON_End_xpath="']/div";
		String roomdesign=prop.getProperty(type);		
		
		String CON_path=CON_Start_xpath+roomdesign+CON_End_xpath;
		//System.err.println(isElementPresent("RoomDesign_BTN_Next_css",5));
		WebElement designContainer=element(CON_path);
		WebElement Header=designContainer.findElement(By.className("title_info")).findElement(By.tagName("h2"));
		WebElement Description=designContainer.findElement(By.className("title_info")).findElement(By.tagName("p"));
		//System.err.println(Header.getText());
		//System.err.println(Description.getText());
		List<WebElement> ColorPreferenceList=designContainer.findElements(By.className("roomdesign_view"));
		for(int i=0;i<ColorPreferenceList.size();i++){
			WebElement ColorPreferenceListimg=ColorPreferenceList.get(i).findElement(By.cssSelector(".roomdesign_viewimg"));
			WebElement ColorPreferenceListcontclearfix=ColorPreferenceList.get(i).findElement(By.cssSelector(".roomdesign_viewcont.clearfix"));
			WebElement RoomName=ColorPreferenceListcontclearfix.findElement(By.className("ng-binding"));
			
			if(RoomName.getText().trim().equalsIgnoreCase(Preferences)){
				ColorPreferenceList.get(i).click();
				break;
			}
	}
		/*
		System.err.println(element("RoomDesign_BTN_Next_css").isDisplayed());
		Actions act=new Actions(driver);
		act.moveToElement(element("RoomDesign_BTN_Next_xpath")).click().build().perform();
		*/
		
	}


	private void selectStylePreferences(String Preferences) {
		log(LogStatus.INFO,"Inside function selectStylePreferences = "+Preferences);
		//System.err.println(isElementPresent("RoomDesign_BTN_Next_css",5));
		WebElement designContainer=element("StylePref_CON_Style_xpath");
		WebElement Header=designContainer.findElement(By.className("title_info")).findElement(By.tagName("h2"));
		WebElement Description=designContainer.findElement(By.className("title_info")).findElement(By.tagName("p"));
		//System.err.println(Header.getText());
		//System.err.println(Description.getText());
		List<WebElement> StylePreferenceList=designContainer.findElements(By.className("roomdesign_view"));
		for(int i=0;i<StylePreferenceList.size();i++){
			WebElement StylePreferenceListimg=StylePreferenceList.get(i).findElement(By.cssSelector(".roomdesign_viewimg"));
			WebElement StylePreferenceListcontclearfix=StylePreferenceList.get(i).findElement(By.cssSelector(".roomdesign_viewcont.clearfix"));
			WebElement RoomName=StylePreferenceListcontclearfix.findElement(By.className("ng-binding"));
			
			if(RoomName.getText().trim().equalsIgnoreCase(Preferences)){
				StylePreferenceList.get(i).click();
				break;
			}
	}
		/*
		System.err.println(element("RoomDesign_BTN_Next_css").isDisplayed());
		Actions act=new Actions(driver);
		act.moveToElement(element("RoomDesign_BTN_Next_xpath")).click().build().perform();
		*/
	}


	private void selectRoomDesign(String roomDesign) {
		log(LogStatus.INFO,"Inside function selectRoomDesign = "+roomDesign);
		System.err.println(isElementPresent("RoomDesign_BTN_Next_css",5));
		ArrayList<String> ArrayRoomDesign=new ArrayList<String>();
		for(int i=0;i<roomDesign.split(",").length;i++){
			ArrayRoomDesign.add(roomDesign.split(",")[i]);
		}
		System.out.println("ArrayList = "+ArrayRoomDesign.toString());
		
		
		WebElement designContainer=element("RoomDesign_CON_Designs_xpath");
		
		WebElement Header=designContainer.findElement(By.className("title_info")).findElement(By.tagName("h2"));
		WebElement Description=designContainer.findElement(By.className("title_info")).findElement(By.tagName("p"));
		//System.err.println(Header.getText());
		//System.err.println(Description.getText());
		List<WebElement> RoomdesignView=designContainer.findElements(By.className("roomdesign_view"));
		//System.err.println(RoomdesignView.size());
		for(int roomtype=0;roomtype<ArrayRoomDesign.size();roomtype++){
			for(int i=0;i<RoomdesignView.size();i++){
				WebElement roomdesignviewimg=RoomdesignView.get(i).findElement(By.cssSelector(".roomdesign_viewimg"));
				WebElement roomdesignviewcontclearfix=RoomdesignView.get(i).findElement(By.cssSelector(".roomdesign_viewcont.clearfix"));
				WebElement RoomName=roomdesignviewcontclearfix.findElement(By.className("ng-binding"));
				
				if(RoomName.getText().trim().equalsIgnoreCase(ArrayRoomDesign.get(roomtype))){
					RoomdesignView.get(i).click();
					break;
				}
		}
		
			
		}
		/*
		System.err.println(element("RoomDesign_BTN_Next_css").isDisplayed());
		Actions act=new Actions(driver);
		act.moveToElement(element("RoomDesign_BTN_Next_xpath")).click().build().perform();
		*/
		
	}


	private void googleLogin(Hashtable<String, String> data) {
		Set<String> Handles=driver.getWindowHandles();
		driver.switchTo().window((String) Handles.toArray()[1]);
		System.err.println(driver.getTitle());
		String PageTitle=prop.getProperty("GoogleTitle");
		if(!driver.getTitle().trim().equalsIgnoreCase(PageTitle.trim())){
			reportError("Page Title Mismatched");
		}
		waittoLoad();
		input("GW_EB_Email_xpath", data.get("Data_Email"));
		click("GW_BTN_Next_xpath");
		input("GW_EB_Password_xpath", data.get("Data_Password"));
		click("GW_BTN_SignIn_xpath");
		waittoLoad();
		driver.switchTo().window((String) Handles.toArray()[0]);
		if(!isElementPresent("HomePage_BTN_Logout_xpath",5000)){
			reportError("Login not successful");
		}
	}


	private void verifyLogin(Hashtable<String, String> data) {
		// TODO Auto-generated method stub
		System.err.println(data.get("Data_Correctness"));
		switch (data.get("Data_Correctness")) {
		case "Valid":
			//System.err.println("Inside Valid case = "+isElementPresent("HomePage_BTN_Logout_xpath", 5000));
			if(!isElementPresent("HomePage_BTN_Logout_xpath", 5000)){
				reportError("Unable to Login");
			}
			break;
		case "InValid":	
			//System.err.println("Inside InValid case"+element("Alertpop_CON_popup_xpath").isDisplayed());
			if(!element("Alertpop_CON_popup_xpath").isDisplayed()){
				reportError("No popup displlayed");
			}else{
				System.err.println("Error popup");
				WebElement WebErrorMsg=element("Alertpop_TXT_Errmsg_xpath");
				String xlsErrorMsg=data.get("Data_ErrorMsg");
				
				if(!WebErrorMsg.getText().trim().equalsIgnoreCase(xlsErrorMsg.trim())){
					System.err.println("Wrong Error msg");
					System.out.println(WebErrorMsg.getText().trim()+"======"+xlsErrorMsg.trim());
				}
				
			}
			break;
		default:
			break;
		}
	}

	private void verifywishlistLoginPopup(Hashtable<String, String> data) {
		WebElement designCon=element("OffShelfDesign_CON_Designs_xpath");
		String ThemeXpath=null;
		String HeartIconXpath=null;
		List<WebElement> list=designCon.findElements(By.tagName("li"));
		System.err.println(list.size());
		int i;
		for(i=0;i<list.size();i++){
			String WebThemeID=list.get(i).getAttribute("id");
			System.err.println(WebThemeID.trim());
			ThemeXpath=".//*[@id='"+WebThemeID.trim()+"']/div[1]/div/img";
			HeartIconXpath=".//*[@id='Add"+WebThemeID.trim()+"']/i";
			WebElement ThemeIcon=driver.findElement(By.xpath(ThemeXpath));
			scrolltoElementusingWebElement(ThemeIcon);
			WebElement HeartIcon=driver.findElement(By.xpath(HeartIconXpath));
			System.out.println(HeartIcon.isDisplayed());
			Actions act=new Actions(driver);
			act.moveToElement(ThemeIcon).build().perform();
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				// TODO: handle exception
			}
			if(!HeartIcon.isDisplayed()){
				reportError("Wishlist icon is not displayed for "+ WebThemeID);
			}
			HeartIcon.click();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			driver.findElement(By.xpath(".//*[@id='loginpopup']/div/div[1]/button")).click();
			
		}
	}
	
	
	
	private void selectdesignByThemeID(String XlsThemeID) {
		// TODO Auto-generated method stub
		WebElement designCon=element("OffShelfDesign_CON_Designs_xpath");
		List<WebElement> list=designCon.findElements(By.tagName("li"));
		System.err.println(list.size());
		int i;
		for(i=0;i<list.size();i++){
		String WebThemeID=list.get(i).getAttribute("id");
		System.err.println(WebThemeID.trim()+"======="+XlsThemeID.trim());
		if(WebThemeID.equalsIgnoreCase(XlsThemeID)){
			list.get(i).click();
			break;
		}
		}
		if(i==list.size()){
			reportError("Theme ID not found = "+XlsThemeID);
		}
		WebElement imgcard=list.get(i).findElement(By.cssSelector(".explore_thmb_info_container.clearfix"));
		Actions _Actions=new Actions(driver);
		_Actions.moveToElement(imgcard).click().build().perform();
		waittoLoad();
		int ThemeID_StartNo=2;
		while (!m_xls.getCellData("Rooms","Theme_id", ThemeID_StartNo).equalsIgnoreCase(XlsThemeID)) {
			ThemeID_StartNo++;
		}
		System.err.println("ThemeID_StartNo = "+ThemeID_StartNo);
		int noofItroom=0;
		while (m_xls.getCellData("Rooms","Theme_id", ThemeID_StartNo+noofItroom).equalsIgnoreCase(XlsThemeID)) {
			noofItroom++;
		}
		//System.err.println(noofItroom);
		int ThemeID_Endrow=ThemeID_StartNo+noofItroom-1;
		System.err.println("ThemeID End row = "+ThemeID_Endrow);
		for(int Theme=ThemeID_StartNo;i<=ThemeID_Endrow;i++){
			String xls_RoomType=m_xls.getCellData("Rooms","Room_type", Theme);
			String xls_Panorama=m_xls.getCellData("Rooms","Panorama", Theme);
			String xls_image1=m_xls.getCellData("Rooms","image1", Theme);
			String xls_image2=m_xls.getCellData("Rooms","image2", Theme);
			String xls_image3=m_xls.getCellData("Rooms","image3", Theme);
			String xls_image4=m_xls.getCellData("Rooms","image4", Theme);
			
		}
		
	}


	private void verifyOffShelfdesign(Hashtable<String, String> data) {
		//System.err.println("verifyOffShelfdesign" );
		WebElement designCon=element("OffShelfDesign_CON_Designs_xpath");
		List<WebElement> list=designCon.findElements(By.tagName("li"));
		//System.err.println(list.size());
		int totalrows=m_xls.getRowCount("Themes")-1;
		if(list.size()!=totalrows){
			reportError("Row size in Xls file mismatched");
		}else if(list.size()==totalrows){
			log("Row size Matched");
		}
		for(int i=0;i<list.size();i++){
			String WebDesignID=list.get(i).getAttribute("id");
			int datarow=2;
			while(!m_xls.getCellData("Themes","Theme_Image",datarow).contains(WebDesignID+".jpg")){
				datarow++;
				if(datarow>totalrows+1){
					System.err.println("Matched not found ");
					reportError("Design ID not matched "+WebDesignID);
					break;
				}
			}
			int Currentdatarow=datarow;
			//System.err.println(WebDesignID+".jpg"+"======"+datarow);
			WebElement image=list.get(i).findElement(By.cssSelector(".explore_thmb_info_container.clearfix")).findElement(By.tagName("img"));
			//System.err.println(WebDesignID+"======="+image.getAttribute("src"));
			WebElement Price=list.get(i).findElement(By.className("start_price"));
			WebElement WebThemeName=list.get(i).findElement(By.className("explore_title"));
			WebElement WebThemeDiscription=list.get(i).findElement(By.className("explore_thmb_info")).findElement(By.tagName("p"));
			String pricetext=Price.getText();
			String price[]=pricetext.split(" FOR ");
			String FirstPart=price[0].split(" ")[1]+" "+price[0].split(" ")[2];
			String SecondPart=price[1];
			//System.err.println(FirstPart+"++++++++"+SecondPart);
			//System.err.println(WebThemeName.getText());
			//System.err.println(WebThemeDiscription.getText());
			
			System.out.println(m_xls.getCellData("Themes","Budget",Currentdatarow).trim() +"+++++++"+FirstPart.trim());
			if(!m_xls.getCellData("Themes","Budget",Currentdatarow).trim().equalsIgnoreCase(FirstPart.trim())){
				reportError(m_xls.getCellData("Themes","Budget",Currentdatarow).trim()+"-------"+FirstPart.trim());
			}
			System.out.println(m_xls.getCellData("Themes","Unit_Type",Currentdatarow).trim() +"+++++++"+SecondPart.trim());
			if(!m_xls.getCellData("Themes","Unit_Type",Currentdatarow).trim().equalsIgnoreCase(SecondPart.trim())){
				reportError(m_xls.getCellData("Themes","Unit_Type",Currentdatarow).trim()+"-------"+SecondPart.trim());
			}
			System.out.println(m_xls.getCellData("Themes","Theme_Name",Currentdatarow).trim()+"+++++++"+WebThemeName.getText().trim());
			if(!m_xls.getCellData("Themes","Theme_Name",Currentdatarow).trim().equalsIgnoreCase(WebThemeName.getText().trim())){
				reportError(m_xls.getCellData("Themes","Theme_Name",Currentdatarow).trim()+"-------"+WebThemeName.getText().trim());
			}
			System.out.println(m_xls.getCellData("Themes","Description",Currentdatarow).trim()+"+++++++"+WebThemeDiscription.getText().trim());
			if(!m_xls.getCellData("Themes","Description",Currentdatarow).trim().equalsIgnoreCase(WebThemeDiscription.getText().trim())){
				reportError(m_xls.getCellData("Themes","Description",Currentdatarow).trim()+"+++++++"+WebThemeDiscription.getText().trim());
			}
			System.out.println(m_xls.getCellData("Themes","Theme_Image",Currentdatarow).trim()+"+++++++"+image.getAttribute("src").trim());
			if(!m_xls.getCellData("Themes","Theme_Image",Currentdatarow).trim().equalsIgnoreCase(image.getAttribute("src").trim())){
				reportError(m_xls.getCellData("Themes","Theme_Image",Currentdatarow).trim()+"+++++++"+image.getAttribute("src").trim());
			}
		}
		
		
	}

	
	
	
	
	private void selectSearchResults(Hashtable<String, String> data) {
		// TODO Auto-generated method stub
		WebElement container=element("Search_CON_Listing_xpath");
		List<WebElement> Noproject=container.findElements(By.cssSelector(".row.listing_section"));
		List<WebElement> ProjectCards=container.findElements(By.cssSelector(".col-md-12.listing_section"));
		if(Noproject.size()!=0){
			System.err.println(Noproject.get(0).findElement(By.tagName("h3")).getText());
			//System.err.println("No project");
			
		}else {
			
			System.out.println("********"+ProjectCards.size()+"********");
			
			
			if(!data.get("BHK_Type").equalsIgnoreCase("")){
				
			}
		}
		
	}


	private void verifyForgotPassword(Hashtable<String, String> data) {
		// TODO Auto-generated method stub
		switch (data.get(Constants.DATA_CASETYPE)) {
		case "Valid":
			if(!element("Alertpop_CON_popup_xpath").isDisplayed()){
				reportError("Alert message for Valid Email ID not matched");
			}else if(element("Alertpop_CON_popup_xpath").isDisplayed()){
				//System.err.println(element("Alertpop_CON_popup_xpath").findElement(By.className("growl-message")).getText());
				String XlsDataMsg=data.get("Data_ErrorMsg");
				if(!XlsDataMsg.equalsIgnoreCase(element("Alertpop_CON_popup_xpath").findElement(By.className("growl-message")).getText())){
					reportError("XlsDataMsg = "+XlsDataMsg+"========"+element("Alertpop_CON_popup_xpath").findElement(By.className("growl-message")).getText());
				}
			}
			break;
		case "InValid":
			if(!element("Alertpop_CON_popup_xpath").isDisplayed()){
				reportError("Alert message for Valid Email ID not matched");
			}else if(element("Alertpop_CON_popup_xpath").isDisplayed()){
				String XlsDataMsg=data.get("Data_ErrorMsg");
				//System.err.println(element("Alertpop_CON_popup_xpath").findElement(By.className("growl-message")).getText());
				if(!XlsDataMsg.equalsIgnoreCase(element("Alertpop_CON_popup_xpath").findElement(By.className("growl-message")).getText())){
					reportError("XlsDataMsg = "+XlsDataMsg+"========"+element("Alertpop_CON_popup_xpath").findElement(By.className("growl-message")).getText());
				}
			}
			break;
		case "NoRegistered":
			if(!element("Alertpop_CON_popup_xpath").isDisplayed()){
				reportError("Alert message for Valid Email ID not matched");
			}else if(element("Alertpop_CON_popup_xpath").isDisplayed()){
				//System.err.println(element("Alertpop_CON_popup_xpath").findElement(By.className("growl-message")).getText());
				String XlsDataMsg=data.get("Data_ErrorMsg");
				if(!XlsDataMsg.equalsIgnoreCase(element("Alertpop_CON_popup_xpath").findElement(By.className("growl-message")).getText())){
					reportError("XlsDataMsg = "+XlsDataMsg+"========"+element("Alertpop_CON_popup_xpath").findElement(By.className("growl-message")).getText());
				}
			}
			break;	
		default:
			break;
		}
	}



	private void verifyURL(String URLPart) {
		// TODO Auto-generated method stub
		try {
			String currentURL=driver.getCurrentUrl();
			if(!currentURL.contains(URLPart)){
				log(LogStatus.FAIL,"verifyURL Failed");
				reportError("verifyURL Failed Actual is "+currentURL+"====Expected is "+URLPart);
			}
			
		} catch (Exception e) {
			reportError("");
			// TODO: handle exception
		}
	}






	

	

	private void reloadPage() {
		// TODO Auto-generated method stub
		driver.navigate().refresh();
		
	}

	
	
	
	 public void validateEmailID(String email) {
		      String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		      String email1 =email;
		      Boolean b = email1.matches(EMAIL_REGEX);
		      System.out.println("is e-mail: "+email1+" :Valid = " + b);
		      
		   }
		


	private void moveMousetoElement(String object) {
		// TODO Auto-generated method stub
		Actions act=new Actions(driver);
		act.moveToElement(element(object)).build().perform();
		waittoLoad();		
	}


	private void scrolltoElement(String object) {
		log(LogStatus.INFO,"scrolltoElement = "+object);
		WebElement Scrollelement = element(object);
	    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", Scrollelement);	
	}
	
	private void scrolltoElementusingWebElement(WebElement objectElement) {
		log(LogStatus.INFO,"scrolltoElementusingWebElement = "+objectElement);
		WebElement Scrollelement = objectElement;
	    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", Scrollelement);	
	}

	private String waitAndClick(String object) throws Throwable {
		// TODO Auto-generated method stub
		for(int i=0;i<5;i++){
			if(element(object).isDisplayed())
				{element(object).click();
				return Constants.PASS;
				}
				else Thread.sleep(3000);
			
		}
		return Constants.PASS;
	}


	public String openBrowser(String browserType){
		//ExtentLog(LogStatus.INFO,TestID+" : "+TestStepDescription );
		log(LogStatus.INFO,"Starting function openBrowser - "+ browserType);
		try{
			testResult=Constants.TD_RESULT_PASS;
			if(map.get(browserType.toLowerCase()) == null){
				log("openBrowser----Number of Browser objects = "+map.size());
				if(browserType.equalsIgnoreCase(Constants.MOZILLA)){
					driver = new FirefoxDriver(FirefoxDriverProfile());
				}
				else if(browserType.equalsIgnoreCase(Constants.CHROME)){
					String downloadFilepath = System.getProperty("user.dir")+"//Downloads";
					HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
					chromePrefs.put("profile.default_content_settings.popups", 0);
					chromePrefs.put("download.default_directory", downloadFilepath);
					ChromeOptions options = new ChromeOptions();
					options.setExperimentalOption("prefs", chromePrefs);
					DesiredCapabilities cap = DesiredCapabilities.chrome();
					cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
					cap.setCapability(ChromeOptions.CAPABILITY, options);
					System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+prop.getProperty("chromedriverexe") );
					driver = new ChromeDriver(cap);
					
				}else if (browserType.equalsIgnoreCase(Constants.IE)) {
					System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+prop.getProperty("iedriverexe"));
					DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
					capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
					driver = new InternetExplorerDriver(capabilities);
				}else if (browserType.equalsIgnoreCase(Constants.SAFARI)) {
					driver = new SafariDriver();
				}
				map.put(browserType.toLowerCase(), driver);
			}
			else{ // flag
				log("Driver object already Running =" +browserType.toLowerCase());
				driver= map.get(browserType.toLowerCase());
			}
			
			
			/* Grid start 
			 DesiredCapabilities cap = null;
			 log(map.toString());
			 if(map.get(browserType)==null){ // browser not opened
				 log("Opening fresh browser");
				if(browserType.equalsIgnoreCase(Constants.MOZILLA))
				{
				  cap = DesiredCapabilities.firefox();
				  cap.setBrowserName("firefox");
				  cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
				}
				
				if(browserType.equalsIgnoreCase(Constants.CHROME))
				{
				  
		          // browser.add(setupDriver(new ChromeDriver()));
				  cap = DesiredCapabilities.chrome();
				  cap.setBrowserName("chrome");
				  cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
				}
				try {
					driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
					map.put(browserType, driver);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }else{
				 log("Using existing browser");
				 driver = map.get(browserType);
			 }
			 */
			 /* Grid end */
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			driver.manage().window().maximize();
		}catch(Exception e){ //error
			e.printStackTrace();
			reportError(Constants.OPENBROWSER_ERROR+browserType);
			return null;
		}
		
		log(LogStatus.INFO,"Ending  function openBrowser with status "+Constants.PASS);
		return Constants.PASS;
	}
	
	
	public String navigate(){
		//ExtentLog(LogStatus.INFO,TestID+" : "+TestStepDescription );
		log(LogStatus.INFO,"Starting function navigate");
		try{
			String env = prop.getProperty("env");
			String url = prop.getProperty("url_"+env);
			_Calendar=Calendar.getInstance();
			ReportTestStartTime(m_xls, currentTestCaseName, currentIteration,_DateFormat.format(_Calendar.getTime()));
			driver.get(url);
			waitForPageToLoad();
			//titles
		String actualTitle=driver.getTitle();
		String expectedTitle=prop.getProperty("homePageTitle");
		if(!actualTitle.equalsIgnoreCase(expectedTitle)){
			System.err.println("Page Title Not matched");
		}	
			
		}catch(Exception e){ //error
			e.printStackTrace();
			reportError(Constants.NAVIGATE_ERROR+e.getMessage());
		}
		
		log(LogStatus.INFO,"Ending  function navigate with status "+Constants.PASS);
		return Constants.PASS;
	}
	
	
	
	
	public static FirefoxProfile FirefoxDriverProfile() throws Exception {
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("browser.download.folderList", 2);
		profile.setPreference("browser.download.manager.showWhenStarting", false);
		profile.setPreference("browser.download.dir",System.getProperty("user.dir")+"//Downloads");
		profile.setPreference("browser.helperApps.neverAsk.openFile",
				"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
		profile.setPreference("browser.helperApps.alwaysAsk.force", false);
		profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
		profile.setPreference("browser.download.manager.focusWhenStarting", false);
		profile.setPreference("browser.download.manager.useWindow", false);
		profile.setPreference("browser.download.manager.showAlertOnComplete", false);
		profile.setPreference("browser.download.manager.closeWhenDone", false);
		return profile;
	}
	
	
	public void waittoLoad(){
		try {
			System.out.println("WAITING------");
			Thread.sleep(3000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	public String selectByVisibleText(String objectKey,String VisibleText){
		//ExtentLog(LogStatus.INFO,TestID+" : "+TestStepDescription );
		log("Starting function click"+ objectKey);

		try{
		Select _Select=new Select(element(objectKey));
		log("Default seclected option is = "+_Select.getFirstSelectedOption().getText());
		int i=0;
		String Value=null;
		for(i=0;i<_Select.getOptions().size();i++){
			if(_Select.getOptions().get(i).getText().trim().equalsIgnoreCase(VisibleText.trim())){
				System.out.println(_Select.getOptions().get(i).getText()+" = Element found");
				Value=_Select.getOptions().get(i).getAttribute("value");
				System.out.println(Value);
				break;
			}
			
		}
		if(i==_Select.getOptions().size()){
			System.err.println("Element not present = "+VisibleText);
			reportError("Element not found="+VisibleText);
		}
		if(Value!=null){
			System.err.println("Selecting option by Value = "+Value);
		_Select.selectByValue(Value);
		}
		log("Selected option is = "+_Select.getFirstSelectedOption().getText());
		
		}catch(StaleElementReferenceException e ){
			System.err.println(e);
			//log(LogStatus.FAIL,"selectByVisibleText_ERROR ="+VisibleText);
			//reportError("selectByVisibleText_ERROR ="+VisibleText);
			
		}
		catch (Exception e) {
			// TODO: handle exception
			log(LogStatus.FAIL,"selectByVisibleText_ERROR ="+VisibleText);
			reportError("selectByVisibleText_ERROR ="+VisibleText);
			
		}
		return "PASS";
	}
	
	
	public String selectByBuilderName(String objectKey,String VisibleText){
		try{
		log(LogStatus.INFO,"Starting function click"+ objectKey);
		WebElement builderList=element(objectKey);
		List<WebElement> builder=builderList.findElements(By.tagName("a"));
		
		System.err.println("Total number of builder "+builder.size());
		for(int i=0;i<builder.size();i++){
			System.out.println(builder.get(i).getAttribute("text"));
			if(builder.get(i).getAttribute("text").trim().equalsIgnoreCase(VisibleText)){
				System.err.println(builder.get(i).getAttribute("text"));
				builder.get(i).click();
				break;
			}
			
		}
		//Select _Select=new Select(element(objectKey));
		//log("Default seclected option is = "+_Select.getFirstSelectedOption().getText());
		//_Select.selectByVisibleText(VisibleText);
		//log("Selected option is = "+_Select.getFirstSelectedOption().getText());
		
		return "PASS";
		}catch(Exception e){
			log(LogStatus.FAIL,"selectByVisibleText_ERROR");
			reportError("selectByVisibleText_ERROR");
			
		}
		return "FAIL";
	}
	
	
	
	
	
	public void selectStatus(String ListsStatus) throws InterruptedException{
			WebElement Status=element("Search_CON_Status_xpath");
			List<WebElement> StatusList=Status.findElements(By.tagName("label"));
			System.out.println(StatusList.size());
				for(int j=0;j<ListsStatus.split(",").length;j++){
				for(int i=0;i<StatusList.size();i++){
					//System.out.println(AmenitiesList.get(i).getText());
					if(ListsStatus.split(",")[j].trim().equalsIgnoreCase(StatusList.get(i).getText().trim())){
						System.out.println(StatusList.get(i).getText());
						StatusList.get(i).click();
						Thread.sleep(2000);
					}
				}
				}}
	
	
	
	
	public String click(String objectKey){
		//ExtentLog(LogStatus.INFO,TestID+" : "+TestStepDescription );
		log(LogStatus.INFO,"Starting function click"+ objectKey);
		element(objectKey).click();
		log(LogStatus.INFO,"Ending  function click with status "+Constants.KEYWORD_RESULT_PASS);
		return Constants.KEYWORD_RESULT_PASS;
	}
	
	public String check(String objectKey,String data){
		log(LogStatus.INFO,"Starting function check "+ objectKey+" , "+data );
		if(data.equalsIgnoreCase("Y")){
			if(!element(objectKey).isSelected()){
		element(objectKey).click();}}
		else if (data.equalsIgnoreCase("N")||data.equalsIgnoreCase("")){
			if(element(objectKey).isSelected()){
				element(objectKey).click();
			}
		}
		
		return Constants.PASS;
	}
	
	public String input(String objectKey,String data){
		log(LogStatus.INFO,"Starting function input"+ objectKey+" , "+data );
		
		element(objectKey).sendKeys(data);
		
		log(LogStatus.INFO,"Ending  function click with status "+Constants.PASS);
		return Constants.PASS;
	}
	
	
	public String clearTextField(String objectKey){
		log(LogStatus.INFO,"Starting function clearTextField"+ objectKey);
		element(objectKey).clear();
		log(LogStatus.INFO,"Ending  function clearTextField with status "+Constants.PASS);
		return Constants.PASS;
	}
	
	public void closeBrowser(String browserName) {
		//ExtentLog(LogStatus.INFO, "Closing the Browser");
		if(!testResult.equalsIgnoreCase("FAIL")){
			reporttestResults(m_xls, currentTestCaseName, currentIteration, Constants.TD_RESULT_PASS);
			
		}else if(testResult.equalsIgnoreCase("FAIL")){
			reporttestResults(m_xls, currentTestCaseName, currentIteration, Constants.TD_RESULT_FAIL);
			_Calendar=Calendar.getInstance();
			ReportTestEndTime(m_xls, currentTestCaseName, currentIteration,_DateFormat.format(_Calendar.getTime()));
			extent.endTest();
			driver.quit();
			map.put(browserName.toLowerCase(), null);
			Assert.fail("FAIL");
		}
		_Calendar=Calendar.getInstance();
		ReportTestEndTime(m_xls, currentTestCaseName, currentIteration,_DateFormat.format(_Calendar.getTime()));
		extent.endTest();
		driver.quit();
		map.put(browserName.toLowerCase(), null);
	}

	public String clickAndWait(String object){
		log(LogStatus.INFO,"Starting function clickAndWait"+ object);
		
		try{
			Thread.sleep(3000);
			String temp[] = object.split(",");
			String elementToBeClicked=temp[0];
			String elementToBeVisible=temp[1];
			
			for(int i=0;i<5;i++){
				element(elementToBeClicked).click();
				if(isElementPresent(elementToBeVisible,5) && element(elementToBeVisible).isDisplayed()){
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}				return Constants.KEYWORD_RESULT_PASS;
				}else{
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
		}
		}catch(Exception e){
			reportError(Constants.GENERAL_ERROR +"clickAndWait" );
		}
		
		reportFailureAndStop(Constants.CLICKANDWAIT_FAILURE+ object);
		return null;
	}
	
	
	public WebElement element(String objectKey){
		
		log(LogStatus.INFO,"Finding element "+objectKey );
		try{
			if(objectKey.endsWith("_id"))
				return driver.findElement(By.id(prop.getProperty(objectKey)));
			else if(objectKey.endsWith("_name"))
				return driver.findElement(By.name(prop.getProperty(objectKey)));
			else if(objectKey.endsWith("_xpath"))
				return driver.findElement(By.xpath(prop.getProperty(objectKey)));
			else if(objectKey.endsWith("_css"))
				return driver.findElement(By.cssSelector(prop.getProperty(objectKey)));
			else if(objectKey.endsWith("_link"))
				return driver.findElement(By.linkText(prop.getProperty(objectKey)));
			else{// error
				//reportError(Constants.LOCATOR_ERROR+objectKey);
				return driver.findElement(By.xpath(objectKey));
				
			}
		}
		catch(NoSuchElementException e){//failure
			reportFailureAndStop(Constants.ELEMENT_NOT_FOUND_FAILURE + objectKey);
		}catch(Exception e){ // error
			reportError(Constants.FIND_ELEMENT_ERROR + objectKey);
		}
		
		return null;
	}
	
	public String clickAndAcceptAlert(String objectKey) {
		try{
			waitForPageToLoad();
			WebDriverWait wait = new WebDriverWait(driver,10);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(prop.getProperty(objectKey))));
			Thread.sleep(3000);
			element(objectKey).click();
			
			Alert al = driver.switchTo().alert();
			al.accept();
			driver.switchTo().defaultContent();
			
			
		}catch(Exception e){ // error
			e.printStackTrace();
			reportError(Constants.GENERAL_ERROR +" acceptAlert");
		}
		return Constants.KEYWORD_RESULT_PASS;
		
	}
	
	public int getTableRowNumWithText(String xpathExp,String text){
		int rowNum=0;
		//parse the table
		List<WebElement> rows = driver.findElements(By.xpath(prop.getProperty(xpathExp)+"/tr"));
		
		for(int rNum=0;rNum<rows.size();rNum++){
			WebElement row = rows.get(rNum);
			List<WebElement> cells =row.findElements(By.tagName("td"));
			
			for(int cNum=0;cNum<cells.size();cNum++){
				//System.out.println(cells.get(cNum).getText());
				if(!(cells.get(cNum).getText().toLowerCase().equals("")) && text.toLowerCase().startsWith(cells.get(cNum).getText().toLowerCase())){
					rowNum=rNum+1;
					return rowNum;
				}
			}
			
		
	    }
		
		return -1;
		
	}

	
	
	//This method gets all the cookies
	public Set<Cookie> getAllCookies() {
	   return driver.manage().getCookies();
	}
	

	//This method gets a specified cookie
	public Cookie getCookieNamed(String name) {
	   return driver.manage().getCookieNamed(name);
	}
	
	
	//This method adds or creates a cookie
	public void addCookie(String name, String value, String domain, String path, Date expiry) {
	   driver.manage().addCookie(
	   new Cookie(name, value, domain, path, expiry));
	}
	
	
	
	/************************************App Keywords*********************************/
	
	private void signupverification(Hashtable<String,String> data) {
		// TODO Auto-generated method stub
		switch (data.get(Constants.DATA_CASETYPE)) {
		case "valid":
			if(!element("User_BTN_Save_xpath").isEnabled()){
				reportError(Constants.ERROR_MSG_ADMINCONSOLE_ADDUSERSCREEN+"  "+"User_BTN_Save_xpath "+ "is not Enabled");
			}
			element("User_BTN_Save_xpath").click();
			break;
		case "No_EmailID":
			if(element("User_BTN_Save_xpath").isEnabled()&&!element("User_EB_Email_error_xpath").getText().equalsIgnoreCase("Required")){
				reportError(Constants.ERROR_MSG_ADMINCONSOLE_ADDUSERSCREEN);
			}
			break;
		default:
			log(LogStatus.ERROR,Constants.ERROR_MSG_ADMINCONSOLE_ADDUSERSCREEN+"  inside default switch case");
			break;
		}
	}
	
/*************************************************************************************************/
	private void developerverification(Hashtable<String,String> data) {
		// TODO Auto-generated method stub
		switch (data.get(Constants.DATA_CASETYPE)) {
		case "valid":
			if(!element("Developer_BTN_Save_xpath").isEnabled()){
				reportError(Constants.ERROR_MSG_ADMINCONSOLE_ADDDEVELOPERSCREEN+"  "+"Developer_BTN_Save_xpath "+ "is not Enabled");
			}
			element("Developer_BTN_Save_xpath").click();
			break;
		case "No_Developer":
			if(element("Developer_BTN_Save_xpath").isEnabled()&&!element("Developer_EB_Name_error_xpath").getText().equalsIgnoreCase("Required")){
				reportError(Constants.ERROR_MSG_ADMINCONSOLE_ADDDEVELOPERSCREEN);
			}
			break;
		case "No_Description":
			if(element("Developer_BTN_Save_xpath").isEnabled()&&!element("Developer_EB_Descrition_error_xpath").getText().equalsIgnoreCase("Required")){
				reportError(Constants.ERROR_MSG_ADMINCONSOLE_ADDDEVELOPERSCREEN);
			}
			break;
		case "No_Data":
			if(!element("Developer_BTN_Save_xpath").isEnabled()){
				reportError(Constants.ERROR_MSG_ADMINCONSOLE_ADDDEVELOPERSCREEN);
			}
			break;
		default:
			log(LogStatus.ERROR,Constants.ERROR_MSG_ADMINCONSOLE_ADDUSERSCREEN+"  inside default switch case");
			break;
		}
	}
	/***********************************************************************************************/
	/***********************************************************************************************/
	private void verifyText(String object,String PageText){
		if(element(object).getText().trim().equalsIgnoreCase(PageText)){
			log(LogStatus.INFO,Constants.TextVerification_PASS+"Actual text is = "+element(object).getText()+" Expected text is = "+PageText);
		}
		else{
			log(LogStatus.FAIL,Constants.TextVerification_FAIL+"Actual text is = "+element(object).getText()+" Expected text is = "+PageText);
			reportError("verifyText is Failed");
		}
	}
	/*********************************Admin User Page************************************/
	public void ElementTo(WebElement Element){
		WebDriverWait wat=new WebDriverWait(driver, 10);
		wat.until(ExpectedConditions.visibilityOf(Element));
		
	}
	 
		/*********************************Utility************************************/
	public boolean isElementPresent(String objectKey) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		int size=0;
		if(objectKey.endsWith("_id"))
			size= driver.findElements(By.id(prop.getProperty(objectKey))).size();
		else if(objectKey.endsWith("_name"))
			size= driver.findElements(By.name(prop.getProperty(objectKey))).size();
		else if(objectKey.endsWith("_xpath"))
			size= driver.findElements(By.xpath(prop.getProperty(objectKey))).size();
		else 
			size= driver.findElements(By.xpath(objectKey)).size();
		
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		if(size!=0){
			log(LogStatus.INFO,"Element Present = " +objectKey);
			return true;
		}
		else{
			log(LogStatus.FAIL,"Element Not Present = " +objectKey);
		return false;
		}
	}
	
	
	
	public boolean isElementPresent(String objectKey,int timeout) {
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
		int size=0;
		if(objectKey.endsWith("_id"))
			size= driver.findElements(By.id(prop.getProperty(objectKey))).size();
		else if(objectKey.endsWith("_name"))
			size= driver.findElements(By.name(prop.getProperty(objectKey))).size();
		else if(objectKey.endsWith("_xpath"))
			size= driver.findElements(By.xpath(prop.getProperty(objectKey))).size();
		else 
			size= driver.findElements(By.xpath(objectKey)).size();
		
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		if(size!=0){
			log(LogStatus.INFO,"Element Present = " +objectKey);
			return true;
		}
		else{
			log(LogStatus.FAIL,"Element Not Present = " +objectKey);
		return false;
		}
	}
	
	
	public boolean isElementPresent(List<WebElement> elements,int timeout) {
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
		int size=elements.size();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		if(size!=0)
			return true;
		else
		return false;
	}
	
	
	
	
	public void reportError(String msg){
		testResult=Constants.TD_RESULT_FAIL;
		takeScreenShot();
		log(LogStatus.FAIL,msg);
		ExtentLog(LogStatus.FAIL,msg);
		//String logPath=Constants.PATH_LOGGER_START+currentTestCaseName+"-"+currentIteration+Constants.PATH_LOGGER_END;
		//log(logPath);
		//m_xls.addHyperLinkNew(Constants.LOGS_COL, currentTestCaseName,Integer.parseInt(currentIteration),logPath,currentTestCaseName+"-"+currentIteration+".log");
		
		reporttestResults(m_xls, currentTestCaseName, currentIteration,testResult);
		closeBrowser(currentBrowser); // node browser becomes available again
		Assert.fail(msg);
	}
	
	public void reportFailureAndStop(String Errmsg) {
		testResult=Constants.TD_RESULT_FAIL;
		takeScreenShot();
		//Utility.reportDataSetResult(m_xls, currentTestCaseName, Integer.parseInt(currentIteration), "FAIL");
		log(LogStatus.FAIL,Errmsg);
		//String logPath=Constants.PATH_LOGGER_START+currentTestCaseName+"-"+currentIteration+Constants.PATH_LOGGER_END;
		//log(logPath);
		//m_xls.addHyperLinkNew(Constants.LOGS_COL, currentTestCaseName,Integer.parseInt(currentIteration),logPath,currentTestCaseName+"-"+currentIteration+".log");
		reporttestResults(m_xls, currentTestCaseName, currentIteration,testResult);
		closeBrowser(currentBrowser);// node browser becomes available again
		Assert.fail(Errmsg);		
	}
	
	public void takeScreenShot(){
		System.out.println("Inside takeeScreenshots function");
		reporttestResults(m_xls, currentTestCaseName, currentIteration,Constants.TD_RESULT_FAIL);
		
		String filePath=Constants.SCREENSHOT_PATH+currentTestCaseName+"-"+currentIteration+".png";
		File targetFile= new File(filePath);
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		System.out.println(Integer.parseInt(currentIteration));
	    m_xls.addHyperLinkNew(Constants.SCREENSHOTS_COL,currentTestCaseName,Integer.parseInt(currentIteration), filePath, currentTestCaseName+"-"+currentIteration+".png");
	    extent.attachScreenshot(filePath);
		try {
			FileUtils.copyFile(srcFile, targetFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}
	
	
	public void waitForPageToLoad(){
		
		JavascriptExecutor js = (JavascriptExecutor)driver;
		System.out.println(js.executeScript("return document.readyState").toString());
		while(!js.executeScript("return document.readyState").toString().equals("complete")){
			try {
				log(LogStatus.INFO,"Waiting for 2 sec for page to load");
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	

	
	public void setLogger(Logger log){
		Application_Log = log;
	}
	
	public void ExtentLog(LogStatus msgType,String message){
		extent.log(msgType, message);
	}
	
	public void log(LogStatus msgType,String message){
		System.out.println(message);
		//extent.log(msgType, message);
		Application_Log.debug(message);
	}
	
	public void log(String message){
		System.out.println(message);
		//extent.log(msgType, message);
		Application_Log.debug(message);
	}
/*
	public static Keywords getInstance(String instanceName) {
		if(instanceMap.get(instanceName) == null){
			instanceMap.put(instanceName, new Keywords(null));
		}
		return instanceMap.get(instanceName);
	}
	*/
	public static void ReportTestEndTime(Xls_Reader xls,String currentTestCase,String Itration,String Data){


		int rNum=1;
		
		while(!xls.getCellData(Constants.TESTDATA_SHEET,0,rNum).equalsIgnoreCase(currentTestCase)){
			rNum++;
		}
		int testcolrow=rNum+1;
		int testdataStartrow=rNum+2;
		int resultcol=0;
		while(!xls.getCellData(Constants.TESTDATA_SHEET,resultcol,testcolrow).equalsIgnoreCase("Test_EndTime")){
			resultcol++;
		}
		System.out.println("Test_StartTime = "+resultcol);
		int numberofItraton=0;
		while(!xls.getCellData(Constants.TESTDATA_SHEET,0,rNum+numberofItraton).equalsIgnoreCase(Itration)){
			numberofItraton++;
		}
		
		//System.out.println("currunt test row number = "+(numberofItraton+rNum));
		//searching for the 
		xls.setResultCellDatawithColor(Constants.TESTDATA_SHEET,resultcol,(numberofItraton+rNum),Data);
		
		
		
	
	
	}
	public static void ReportHomePageLoadTime(Xls_Reader xls,String currentTestCase,String ColName,String Itration,String Data){


		int rNum=1;
		
		while(!xls.getCellData(Constants.TESTDATA_SHEET,0,rNum).equalsIgnoreCase(currentTestCase)){
			rNum++;
		}
		int testcolrow=rNum+1;
		int testdataStartrow=rNum+2;
		int resultcol=0;
		while(!xls.getCellData(Constants.TESTDATA_SHEET,resultcol,testcolrow).equalsIgnoreCase(ColName)){
			resultcol++;
		}
		System.out.println("Test_StartTime = "+resultcol);
		int numberofItraton=0;
		while(!xls.getCellData(Constants.TESTDATA_SHEET,0,rNum+numberofItraton).equalsIgnoreCase(Itration)){
			numberofItraton++;
		}
		
		System.out.println("currunt test row number = "+(numberofItraton+rNum));
		//searching for the 
		xls.setResultCellDatawithColor(Constants.TESTDATA_SHEET,resultcol,(numberofItraton+rNum),Data);
	}
	
	public static void ReportTestStartTime(Xls_Reader xls,String currentTestCase,String Itration,String Data){

		int rNum=1;
		
		while(!xls.getCellData(Constants.TESTDATA_SHEET,0,rNum).equalsIgnoreCase(currentTestCase)){
			rNum++;
		}
		int testcolrow=rNum+1;
		int testdataStartrow=rNum+2;
		int resultcol=0;
		while(!xls.getCellData(Constants.TESTDATA_SHEET,resultcol,testcolrow).equalsIgnoreCase("Test_StartTime")){
			resultcol++;
		}
		System.out.println("Test_StartTime = "+resultcol);
		int numberofItraton=0;
		while(!xls.getCellData(Constants.TESTDATA_SHEET,0,rNum+numberofItraton).equalsIgnoreCase(Itration)){
			numberofItraton++;
		}
		
		System.out.println("currunt test row number = "+(numberofItraton+rNum));
		//searching for the 
		xls.setResultCellDatawithColor(Constants.TESTDATA_SHEET,resultcol,(numberofItraton+rNum),Data);
		
		
		
	
	}
	
	
	
	public static void reporttestResults(Xls_Reader xls,String currentTestCase,String Itration,String Data){
		int rNum=1;
		
		while(!xls.getCellData(Constants.TESTDATA_SHEET,0,rNum).equalsIgnoreCase(currentTestCase)){
			rNum++;
		}
		int testcolrow=rNum+1;
		int testdataStartrow=rNum+2;
		int resultcol=0;
		while(!xls.getCellData(Constants.TESTDATA_SHEET,resultcol,testcolrow).equalsIgnoreCase("Results")){
			resultcol++;
		}
		System.out.println("Results col = "+resultcol);
		int numberofItraton=0;
		while(!xls.getCellData(Constants.TESTDATA_SHEET,0,rNum+numberofItraton).equalsIgnoreCase(Itration)){
			numberofItraton++;
		}
		
		System.out.println("currunt test row number = "+(numberofItraton+rNum));
		//searching for the 
		xls.setResultCellDatawithColor(Constants.TESTDATA_SHEET,resultcol,(numberofItraton+rNum),Data);
		
		
		
	}
	
	
	
	//---------------------------Calculator functions------------------------------------------//

	
	
	
		
	
	
		 
	
	public void verifySignUp(Hashtable<String,String> data){
		
		switch (data.get("Data_Correctness")) {
		case "Valid":
			System.err.println(element("Upload_TXT_MSG_xpath").getText()+"+++++++++++++");
			if(!element("Upload_TXT_MSG_xpath").getText().equalsIgnoreCase(data.get("Data_ErrorMsg"))){
				System.err.println(element("Upload_TXT_MSG_xpath").getText()+"Not matched");
				reportError("verifySignUp = Message Not Matched Current Message is "+element("Upload_TXT_MSG_xpath").getText()+" Expected is "+data.get("Data_ErrorMsg"));
			}
			break;
		case "InValid" :
			System.err.println("Inside InValid Case");
			if(!element("Upload_TXT_MSG_xpath").getText().equalsIgnoreCase(data.get("Data_ErrorMsg"))){
				reportError("verifySignUp = Message Not Matched Current Message is "+element("Upload_TXT_MSG_xpath").getText()+" Expected is "+data.get("Data_ErrorMsg"));
				}
		break;	
		default:
			break;
		}
		
	}
	
	

		 

}
