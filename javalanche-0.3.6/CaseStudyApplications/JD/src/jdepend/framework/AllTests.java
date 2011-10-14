 package jdepend.framework;

import java.util.ArrayList;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author <b>Mike Clark</b>
 * @author Clarkware Consulting, Inc.
 */

public class AllTests {

    public static TestSuite suite() {
        /*
        TestSuite suite = new TestSuite("JDepend Tests");


        suite.addTestSuite(ClassFileParserTest.class);
        suite.addTestSuite(ComponentTest.class);
        suite.addTestSuite(JarFileParserTest.class);
        suite.addTestSuite(ConstraintTest.class);
        suite.addTestSuite(CycleTest.class);
        suite.addTestSuite(CollectAllCyclesTest.class);
        suite.addTestSuite(FileManagerTest.class);
        suite.addTestSuite(FilterTest.class);
        suite.addTestSuite(MetricTest.class);
        suite.addTestSuite(PropertyConfiguratorTest.class);
        suite.addTestSuite(ExampleTest.class);
         */
    	
        ArrayList<TestSuite> suiteList = new ArrayList<TestSuite>();
    	TestSuite finalSuite = new TestSuite();
    	
    	suiteList.add(new TestSuite(ClassFileParserTest.class));
    	suiteList.add(new TestSuite(ComponentTest.class));
    	suiteList.add(new TestSuite(JarFileParserTest.class));
    	suiteList.add(new TestSuite(ConstraintTest.class));
    	suiteList.add(new TestSuite(CycleTest.class));
    	suiteList.add(new TestSuite(CollectAllCyclesTest.class));
    	suiteList.add(new TestSuite(FileManagerTest.class));
    	suiteList.add(new TestSuite(FilterTest.class));
    	suiteList.add(new TestSuite(MetricTest.class));
    	suiteList.add(new TestSuite(MetricTest.class));
    	suiteList.add(new TestSuite(PropertyConfiguratorTest.class));
    	suiteList.add(new TestSuite(ExampleTest.class));
    	for(TestSuite suite : suiteList)
    	{
    		for(int i = 0; i < suite.countTestCases();i++)
    			finalSuite.addTest(suite.testAt(i));
    	}
    	
        return finalSuite;
        

    }

    public static void main(String[] args) 
    {
    	TestSuite suite = suite();
    	
    	if(args.length == 0)
    	{
    		for(int i = 0; i < suite.countTestCases();i++)
    		{
    			System.out.println(suite.testAt(i));
    			junit.textui.TestRunner.run(suite.testAt(i));
    	
    		}
    	}
    	else
    	{
    		for(int i = 0; i < args.length;i++)
    		{
    			System.out.println(suite.testAt(Integer.parseInt(args[i])));
    			junit.textui.TestRunner.run(suite.testAt(Integer.parseInt(args[i])));
    		}	
    	}
    	
    	System.out.println("Total Test Cases: "+ suite.countTestCases());
    }
}