package testcases;

import org.testng.annotations.Test;
import org.testng.annotations.Test;

import keywords.ApplicationsKeywords;

public class CreatePortfolioTest {
	@Test
	public void createPortFolioTest()
	{
ApplicationsKeywords app=new ApplicationsKeywords();
app.openBrowser("Mozilla");
app.navigate("url");
app.type("username_id", "palavarapu.m@gmail.com");
app.type("password_id", "Rediff@1234" );
app.validateElementPresent("login_submit_id");
app.click("login_submit_id");
app.validateLogin();
app.selectDateFromCalendar();
//app.validateTitle();
//app.validateElementPresent();
//app.type("username", "palavarapu.m@gmail.com");
//app.selectDateFromCalendar();
	}
}
