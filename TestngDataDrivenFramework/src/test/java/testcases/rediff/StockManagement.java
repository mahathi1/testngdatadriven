package testcases.rediff;

import org.json.simple.JSONObject;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import testbase.BaseTest;

import org.testng.annotations.Test;

public class StockManagement  extends BaseTest{

	@Test
	public void addNewStock(ITestContext context) {
		JSONObject data=(JSONObject) context.getAttribute("data");
		String companyName =(String) data.get("stockname");
		String selectionDate=(String) data.get("date");// dd-MM-yyyy
		String stockQuantity=(String) data.get("quantity");
		String stockPrice=(String) data.get("price");
		app.log("Adding "+stockQuantity+" stocks of  "+ companyName);
		int quatityBeforeModification = app.findCurrentStockQuantity(companyName);
		
		context.setAttribute("quatityBeforeModification", quatityBeforeModification);
		app.click("addStock_xpath");
		app.type("addstockname_xpath", companyName);
		app.wait(2);
		app.clickEnterButton("addstockname_xpath");
		app.click("stockPurchaseDate_id");
		app.selectDateFromCalendar(selectionDate);
		app.type("addstockqty_xpath", stockQuantity);
		app.type("addstockprice_xpath",stockPrice );
		app.click("addStockButton_id");
		app.waitForPageToLoad();
		app.log("stock added");

		
	}
	// sell or buy existing stock
	@Parameters ({"action"})
	@Test
	public void modifyStock(String action,ITestContext context)
	{
		String companyName = "Birla Corporation Ltd";
		String selectionDate="14-12-2020";
		String stockQuantity="100";
		String stockPrice="200";
		int quatityAfterModification;
		//app.log("Selling "+stockQuantity +" of company "+ companyName);
		int quatityBeforeModification = app.findCurrentStockQuantity(companyName);
		context.setAttribute("quatityBeforeModification", quatityBeforeModification);
		app.goToBuySell(companyName);
		app.waitForPageToLoad();
		if(action.equals("Sell"))
		{
			Select s=new Select(app.getElement("equityaction_xpath"));
			s.selectByVisibleText(action);
		}
		else
		{
			Select s=new Select(app.getElement("equityaction_xpath"));
			s.selectByVisibleText(action);
		}
		app.click("buySellCalendar_id");
		app.selectDateFromCalendar(selectionDate);
		app.type("buysellqty_xpath", stockQuantity);
		app.type("buysellprice_xpath", stockPrice);
		app.click("buySellStockButton_id");
		app.waitForPageToLoad();
		app.log("Stock Sold ");
		
		
	}
	@Test
	public void verifyStockPresesnt()
	{
		String companyName = "Birla Corporation Ltd";
		int row=app. getRowNumWithCellData("stocktable_xpath",companyName);
		if(row ==-1)
			app.reportFailure("Stock Not present "+companyName, true);
		
		app.log("Stock Found in list "+companyName );
	}
	@Parameters ({"action"})
	@Test
	public void verifyStockQuantity(String action, ITestContext context)
	{
		String companyName = "Birla Corporation Ltd";
		String selectionDate="14-12-2020";
		String stockQuantity="100";
		String stockPrice="200";
		int quatityAfterModification = app.findCurrentStockQuantity(companyName);
		int quatityBeforeModification=(Integer) context.getAttribute("quatityBeforeModification");
		int expectedResult=0;
		if(action.equals("addstock"))
		{
			expectedResult= quatityAfterModification-quatityBeforeModification;
		}
		else if(action.equals("sellstock"))
				{
			expectedResult=quatityBeforeModification-quatityAfterModification;
		}
		int actualResults= app.findCurrentStockQuantity(companyName);
		if(expectedResult==actualResults)
		{
			app.log("Stock Quantity Changed as per expected "+ actualResults);
		}
		 app.reportFailure("Quantity did not match", true);
		

	}
	@Parameters ({"action"})
	@Test

	public void verifyTransactionHistroy(String action)
	{
		String companyName = "Birla Corporation Ltd";
		String selectionDate="14-12-2020";
		String stockQuantity="100";
		String stockPrice="200";
		
		app.log("Verifying transaction History for "+action+"for quantity "+stockQuantity);
		app.goToTransactionHistory(companyName);
		String changedQuantityDisplayed  = app.getText("latestShareChangeQuantity_xpath");
		app.log("Got Changed Quantity "+ changedQuantityDisplayed);
		
		if(action.equals("sellstock"))
			stockQuantity="-"+stockQuantity;
		
		if(!changedQuantityDisplayed.equals(stockQuantity))
		   app.reportFailure("Got changed quantity in transaction history as "+ changedQuantityDisplayed, true);	
		
		app.log("Transaction History OK");
	}
}
