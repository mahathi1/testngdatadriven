package keywords;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
//import org.testng.asserts.SoftAssert;

import reports.ExtentManager;

public class GenericKeywords {
	public WebDriver driver;
	public Properties prop;
	public Properties envProp;
	public ExtentTest test;
	public SoftAssert softAssert;
public void openBrowser(String bName)
{
	
	log("opening browser"+bName);
	if(prop.get("grid_run").equals("Y"))
	{
		DesiredCapabilities cap=new DesiredCapabilities();
		if(bName.equals("Mozilla")){
			
			cap.setBrowserName("firefox");
			cap.setJavascriptEnabled(true);
			cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
			
		}else if(bName.equals("Chrome")){
			 cap.setBrowserName("chrome");
			 cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
		}
		try {
			// hit the hub
			driver = new RemoteWebDriver(new URL("http://localhost:4444"), cap);
		} catch (Exception e) {
		  e.printStackTrace();
		}
		
	}
	else {
	if(bName.equals("Chrome")) {
		System.setProperty("webdriver.chrome.driver", "/Users/nagarjunasoma/Documents/softwares_Selenium/driver_webrowsers/chromedriver");
		driver = new ChromeDriver();  
	}else if(bName.equals("Mozilla")) {
		System.setProperty("webdriver.gecko.driver", "/Users/nagarjunasoma/Documents/softwares_Selenium/driver_webrowsers/geckodriver");
		driver = new FirefoxDriver();
	}
	}
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
	//driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
}
public void navigate(String urlKey)
{
	log("navgating to"+urlKey);
	driver.get(envProp.getProperty(urlKey));
}
public void click(String locatorKey)
{
log("clicking on"+locatorKey);
	getElement(locatorKey).click();
}
public void type(String locatorKey,String data)
{
log("Typing into"+locatorKey+data);
	getElement(locatorKey).sendKeys(data);
}
public void select(String locator,String data)
{
	
}
public String getText(String locator)
{
	return getElement(locator).getText();
	
}
public WebElement getElement(String locatorKey)

{
	if(!isElementPresent(locatorKey))
	{
		log("Element not present"+locatorKey);
	}
	if(!isElementVisible(locatorKey))
	{
		log("Element not visible"+locatorKey);
	}
	
	
	WebElement e=driver.findElement(getLocator(locatorKey));
	
	return e;
}
public boolean isElementPresent(String locatorKey)
{

	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	
	try {
		//getLocator(locatorKey);
		wait.until(ExpectedConditions.presenceOfElementLocated(getLocator(locatorKey)));

	//wait.until(ExpectedConditions.presenceOfElementLocated(By.id(locator)));
	}
	catch(Exception e)
	{
		return false;
	}
	return true;
}
public boolean isElementVisible(String locatorKey)
{
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	try {
		
			wait.until(ExpectedConditions.visibilityOfElementLocated(getLocator(locatorKey)));
	
	
	}
	catch(Exception e)
	{
		return false;
	}
	return true;
}
public By getLocator(String locatorKey)
{
	By by=null;
	if(locatorKey.endsWith("_id"))
		by=By.id(prop.getProperty(locatorKey));
else if(locatorKey.endsWith("_xpath"))
	by=By.xpath(prop.getProperty(locatorKey));
else if(locatorKey.endsWith("_css"))
	by=By.cssSelector(prop.getProperty(locatorKey));
else if(locatorKey.endsWith("_name"))
	by=By.name(prop.getProperty(locatorKey));
	
	return by;
}
public void log(String msg)
{
	System.out.println(msg);
	test.log(Status.INFO, msg);
}
public void reportFailure(String failureMsg,boolean stopOnFailure)
{
	System.out.println(failureMsg);
	//takeScreenShot();
	test.log(Status.FAIL,failureMsg);
	takeScreenShot();
	softAssert.fail(failureMsg);
	if(stopOnFailure)
	{
		Reporter.getCurrentTestResult().getTestContext().setAttribute("criticalFailure", "Y");

		asserAll();
	}
}
public void asserAll()
{
	Reporter.getCurrentTestResult().getTestContext().setAttribute("criticalFailure", "Y");
	softAssert.assertAll();
	
}
public void takeScreenShot(){
	// fileName of the screenshot
	Date d=new Date();
	String screenshotFile=d.toString().replace(":", "_").replace(" ", "_")+".png";
	// take screenshot
	File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	try {
		// get the dynamic folder name
		FileUtils.copyFile(srcFile, new File(ExtentManager.screenshotFolderPath+"//"+screenshotFile));
		//put screenshot file in reports
		test.log(Status.INFO,"Screenshot-> "+ test.addScreenCaptureFromPath(ExtentManager.screenshotFolderPath+"//"+screenshotFile));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
public void clear(String locatorKey) {
	log("Clearing text field "+ locatorKey);
	getElement(locatorKey).clear();
}
public void waitForPageToLoad(){
	JavascriptExecutor js = (JavascriptExecutor)driver;
	int i=0;
	// ajax status
	while(i!=10){
	String state = (String)js.executeScript("return document.readyState;");
	System.out.println(state);

	if(state.equals("complete"))
		break;
	else
		wait(2);

	i++;
	}
	// check for jquery status
	i=0;
	while(i!=10){

		Long d= (Long) js.executeScript("return jQuery.active;");
		System.out.println(d);
		if(d.longValue() == 0 )
		 	break;
		else
			 wait(2);
		 i++;
			
		}
	
	}
public void wait(int time) {
	try {
		Thread.sleep(time*1000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
public void selectByVisibleText(String locatorKey, String data) {
	Select s = new Select(getElement(locatorKey));
	s.selectByVisibleText(data);
}
public void acceptAlert(){
	test.log(Status.INFO, "Switching to alert");
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	wait.until(ExpectedConditions.alertIsPresent());
	try{
		driver.switchTo().alert().accept();
		//driver.switchTo().defaultContent();
		test.log(Status.INFO, "Alert accepted successfully");
	}catch(Exception e){
			reportFailure("Alert not found when mandatory",true);
	}
	
}
public void clickEnterButton(String locatorKey)
{
	getElement(locatorKey).sendKeys(Keys.ENTER);
}
//finds the row number of the data
	public int getRowNumWithCellData(String tableLocator, String data) {
		WebElement table = getElement(tableLocator);
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		//int rowNum=0;
		for(int i=0;i<rows.size();i++)
		{
			//rowNum=i;
			WebElement row = rows.get(i);
			List<WebElement> coloumns = row.findElements(By.tagName("td"));
			for(int j=0;j<coloumns.size();j++)
			{
				//String results=coloumns.get(j).getText();
				WebElement cell = coloumns.get(j);
				if(!cell.getText().trim().equals(""))
				if(data.startsWith(cell.getText()))
				{
					return i+1;
				}
			}
			
		}
		
		return -1;
	}
	public void quit() {
		driver.quit();
		
	}

	
}
