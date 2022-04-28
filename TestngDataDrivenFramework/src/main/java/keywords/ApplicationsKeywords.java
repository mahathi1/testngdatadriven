package keywords;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;

public class ApplicationsKeywords  extends ValidationKeywords{
	public ApplicationsKeywords()
	{
		String path  = System.getProperty("user.dir")+"//src//test//resources//env.properties";
	prop = new Properties();
	envProp= new Properties();
	
		try {
			FileInputStream fs = new FileInputStream(path);
			prop.load(fs);
			String env=prop.getProperty("env")+".properties";
			path  = System.getProperty("user.dir")+"//src//test//resources//"+env;
			 fs = new FileInputStream(path);
			 envProp.load(fs);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.print(prop.getProperty("url"));
		softAssert=new SoftAssert();
	}
public void login()
{
	
}
public void selectDateFromCalendar()
{
	
}
public void verifyStockAdded()
{
	
}
public void setReport(ExtentTest test)
{
	this.test=test;
}
public void selectDateFromCalendar(String date) {
	log("Selecting Date "+date);
	
	try {
		Date currentDate = new Date();
		Date dateToSel=new SimpleDateFormat("d-MM-yyyy").parse(date);
		String day=new SimpleDateFormat("dd").format(dateToSel);
		String month=new SimpleDateFormat("MMMM").format(dateToSel);
		String year=new SimpleDateFormat("yyyy").format(dateToSel);
		String monthYearToBeSelected=month+" "+year;
		String monthYearDisplayed=getElement("monthyear_css").getText();
		
		while(!monthYearToBeSelected.equals(monthYearDisplayed)) {
			click("datebackButoon_xpath");
			monthYearDisplayed=getElement("monthyear_css").getText();
		}
		driver.findElement(By.xpath("//td[text()='"+day+"']")).click();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
public int findCurrentStockQuantity(String companyName) {
	int row = getRowNumWithCellData("stocktable_xpath",companyName);
	if(row==-1) {
		log("Current Stock Quantity is 0 as Stock not present in list");
		return 0;
	}
	
		
	String qua=driver.findElement(By.xpath(prop.getProperty("stocktable_xpath")+"/tr['"+row+"']/td[4]")).getText();
	int quantity=Integer.parseInt(qua);
	return quantity;
}
public void goToBuySell(String companyName) {
	
	log("Selecting the company row "+companyName );
	int row = getRowNumWithCellData("stocktable_xpath", companyName);
	driver.findElement(By.xpath(prop.getProperty("stocktable_xpath")+"/tr['"+row+"']/td[1]")).click();	
	driver.findElement(By.xpath(prop.getProperty("stocktable_xpath")+"/tr['"+row+"']/td[3]/div/input[1]")).click();	
	
}
public void goToTransactionHistory(String companyName) {
    log("Selecting the company row "+companyName );
	int row = getRowNumWithCellData("stocktable_css", companyName);
	if(row==-1) {
		log("Stock not present in list");
		// report failure
	}
	driver.findElement(By.xpath(prop.getProperty("stocktable_xpath")+"/tr['"+row+"']/td[1]")).click();	
	driver.findElement(By.xpath(prop.getProperty("stocktable_xpath")+"/tr['"+row+"']/td[3]/div/input[3]")).click();	
	
}
}
