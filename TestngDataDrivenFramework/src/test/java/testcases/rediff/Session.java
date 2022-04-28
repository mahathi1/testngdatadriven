package testcases.rediff;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import keywords.ApplicationsKeywords;
import testbase.BaseTest;

import org.testng.annotations.Test;

public class Session  extends BaseTest {
	@Test
	public void doLogin(ITestContext context)
	{
		app.log("Logging In");
		//ApplicationsKeywords app=(ApplicationsKeywords)context.getAttribute("app");
		app.openBrowser("Chrome");
		
		app.navigate("url");
		app.type("username_id", "palavarapu.m@gmail.com");
		app.type("password_id", "Rediff@1234" );
		//app.reportFailure("first failure-not critical", false);
		//app.validateElementPresent("login_submit_id");
		app.click("login_submit_id");
		//app.validateLogin();
		//app.selectDateFromCalendar();
		//app.navigate("url");
		
	}
		@Test
		public void doLogout()
		{
			test.log(Status.INFO,"Logging Out");
		}

}
