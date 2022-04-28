package testbase;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import keywords.ApplicationsKeywords;
import reports.ExtentManager;
import runner.DataUtil;

public class BaseTest {
	public ApplicationsKeywords app;
	public ExtentReports rep;
	public ExtentTest test;
	@BeforeTest(alwaysRun=true)
	public void beforeTest(ITestContext context) throws NumberFormatException, FileNotFoundException, IOException, ParseException
	{
		System.out.println("before test");
		String datafilepath=context.getCurrentXmlTest().getParameter("datafilepath");

		String dataFlag=context.getCurrentXmlTest().getParameter("dataflag");

		String iterations=context.getCurrentXmlTest().getParameter("iterations");
		JSONObject data=new DataUtil().getTestData(datafilepath,dataFlag,Integer.parseInt(iterations));
	context.setAttribute("data", data);
		String runmode=(String) data.get("runmode");
	
		app=new ApplicationsKeywords();
		context.setAttribute("app", app);
		rep=ExtentManager.getReports();
		test=rep.createTest(context.getCurrentXmlTest().getName());
		test.log(Status.INFO,"Starting test"+context.getCurrentXmlTest().getName());
		context.setAttribute("report",rep);
		context.setAttribute("test",test);
		
		if(!runmode.equals("Y"))
		{
			test.log(Status.SKIP,"Skipping as Runmode is N");
			throw new SkipException("Skipping as Runmode is N");
		}
		app.setReport(test);
		
	
		
	}
	@BeforeMethod(alwaysRun=true)
	
	public void beforeMethod(ITestContext context)
	{
		System.out.println("before method");
		 test=(ExtentTest) context.getAttribute("test");
	String criticalFailure=(String)context.getAttribute("criticalFailure");
		//app=app;
	if(criticalFailure!=null && criticalFailure.equals("Y"))
	{
		test.log(Status.SKIP, "critical failure");
		throw new SkipException("critical failure");
	}
app=(ApplicationsKeywords)context.getAttribute("app");
		//app=new ApplicationsKeywords();
		
	 rep=(ExtentReports) context.getAttribute("report");
	// test=(ExtentTest) context.getAttribute("test");
	 

	}
	@AfterTest
	public void quit()
	{
		if(rep!=null)
			rep.flush();
	}
	@AfterTest(alwaysRun = true)
	public void quit(ITestContext context) {
		app = (ApplicationsKeywords)context.getAttribute("app");
		if(app!=null)
			app.quit();
		
		rep = (ExtentReports)context.getAttribute("report");

		if(rep !=null)
			rep.flush();
	}
}
