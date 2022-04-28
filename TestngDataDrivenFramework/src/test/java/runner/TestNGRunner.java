package runner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlSuite.ParallelMode;
import org.testng.xml.XmlTest;

public class TestNGRunner {
	TestNG testNg;
	XmlSuite suite;
	List<XmlSuite> allSuites;
	XmlTest test;
	List<XmlTest> testcases;
	Map<String,String> testParameters;
	List<XmlClass> testClasses;
	
public TestNGRunner(int suitethreadPoolSize) {
	allSuites=new ArrayList<XmlSuite>();
	testNg=new TestNG();
	testNg.setSuiteThreadPoolSize(suitethreadPoolSize);
	testNg.setXmlSuites(allSuites);
		
	}
public void createSuite(String suiteName,boolean parallelTests)
{
	suite=new XmlSuite();
	suite.setName(suiteName);
	if(parallelTests)
	{
		suite.setParallel(ParallelMode.TESTS);
	};
	//testcases=new ArrayList<String>();
	allSuites.add(suite);    
}
public void addTest(String testName)
{
	test =new XmlTest(suite);
	test.setName(testName);
	testParameters=new HashMap<String,String>();
	testClasses=new ArrayList<XmlClass>();
	test.setParameters(testParameters);
	test.setXmlClasses(testClasses);
}
public void addTestParameter(String name,String value)
{
	testParameters.put(name, value);
}
public void addTestClass(String className,List<String> includeMethodNames)
{
	
	XmlClass testClass=new XmlClass(className);
	List<XmlInclude> classMethods=new ArrayList<XmlInclude>();
	int priority=1;
	for(String methodName:includeMethodNames)
	{
		XmlInclude method=new XmlInclude(methodName,priority);
		classMethods.add(method);
		priority++;
	}
	testClass.setIncludedMethods(classMethods);
	testClasses.add(testClass);
	
}
public void addListener(String listenerFile)
{
	suite.addListener(listenerFile);
}
public void run()
{
	testNg.run();
}
}
