package runner;

import java.util.ArrayList;
import java.util.List;

public class Runner {

	public static void main(String[] args) {
		TestNGRunner testNG=new TestNGRunner(1);
		testNG.createSuite("Stock Management", false);
		testNG.addListener("listener.MyTestNGListener");
		testNG.addTest("Add New Stock Test");
		
		testNG.addTestParameter("action", "addstock");
		List<String> includeMethods=new ArrayList<String>();
		includeMethods.add("doLogin");
		testNG.addTestClass("testcases.rediff.Session",includeMethods );
		 includeMethods=new ArrayList<String>();
			includeMethods.add("selectPortFolio");
			
			testNG.addTestClass("testcases.rediff.PortfolioManagement",includeMethods );
			 includeMethods=new ArrayList<String>();
				includeMethods.add("addNewStock" );
				includeMethods.add("verifyStockPresent");

				includeMethods.add("verifyStockQuantity");

				includeMethods.add("verifyTransactionHistory");

				
				testNG.addTestClass("testcases.rediff.StockManagement",includeMethods );
				testNG.run();


	}

}
