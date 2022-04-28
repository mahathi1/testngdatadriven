package testcases.rediff;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import keywords.ApplicationsKeywords;
import testbase.BaseTest;

import org.testng.annotations.Test;

public class PortfolioManagement extends BaseTest{

	@Test
	public void createPortfolio(ITestContext context)
	{
		app.click("createPortfolio_xpath");
		app.clear("porfolioname_xpath");
		app.type("porfolioname_xpath", "Own");
		app.click("createPortfolioButton_css");
		app.waitForPageToLoad();
		app.validateSelectedValueInDropDown("portfolioid_dropdown_xpath","Own");
	}
	@Test
	
	public void deletePortfolio(ITestContext context)
	{
		app.selectByVisibleText("portfolioid_dropdown_xpath","Own");
		app.click("deletePortfolio_id");
		app.acceptAlert();
		app.waitForPageToLoad();
		app.validateSelectedValueNotInDropDown("portfolioid_dropdown_xpath","Own");
	}
	@Test
	public void selectPortFolio()
	{
		app.log("Selecting Profolio");
		app.selectByVisibleText("portfolioid_dropdown_xpath","Own");
        app.waitForPageToLoad();
	}
}
