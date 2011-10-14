package dataStructures;

import junit.framework.*;
import java.util.ArrayList;

public class AllTestDataStructures 
{
    public static TestSuite suite() 
    {
    	/*  This is the old way
		TestSuite suite = new TestSuite("stack");
		
		
		suite.addTest(dataStructures.TestStack.suite());
	        suite.addTest(dataStructures.Teststack1.suite());
		suite.addTest(dataStructures.TestUniquedataStructures.suite());
		suite.addTest(dataStructures.AllTests.suite());
		 */
		
		/* This is my old way
		//suite.addTestSuite(dataStructures.TestUniquedataStructures.class);
		suite.addTestSuite(dataStructures.uniquedataStructuresTest.class);
		suite.addTestSuite(dataStructures.uniquedataStructuresAxiomTest.class);
		
		//suite.addTestSuite(dataStructures.AllTests.class);
		suite.addTestSuite(dataStructures.TestdataStructures.class);
		suite.addTestSuite(dataStructures.TestdataStructuresTable.class);
		
		//suite.addTestSuite(dataStructures.TestStack.class);
		suite.addTestSuite(dataStructures.Teststack3.class);
		suite.addTestSuite(dataStructures.Teststack4.class);
		suite.addTestSuite(dataStructures.Teststack3oldMethod.class);
		suite.addTestSuite(dataStructures.Teststack4oldMethod.class);
		
		suite.addTestSuite(dataStructures.Teststack1.class);
		suite.addTestSuite(dataStructures.TestStack2.class);
		suite.addTestSuite(dataStructures.Teststack1oldMethod.class);
		suite.addTestSuite(dataStructures.Teststack2oldMethod.class);
		*/
		
    	ArrayList<TestSuite> suiteList = new ArrayList<TestSuite>();
    	
    	TestSuite finalSuite = new TestSuite();
    	
    	//suiteList.add(new TestSuite(dataStructures.uniquedataStructuresTest.class));
    	//suiteList.add(new TestSuite(dataStructures.uniquedataStructuresAxiomTest.class));
		
    	//suiteList.add(new TestSuite(dataStructures.TestDataStructures.class));
    	//suiteList.add(new TestSuite(dataStructures.TestDataStructuresTable.class));
		
		
    	//suiteList.add(new TestSuite(dataStructures.Teststack3.class));
    	//suiteList.add(new TestSuite(dataStructures.Teststack4.class));
    	//suiteList.add(new TestSuite(dataStructures.Teststack3oldMethod.class));
    	//suiteList.add(new TestSuite(dataStructures.Teststack4oldMethod.class));
		
    	//suiteList.add(new TestSuite(dataStructures.Teststack1.class));
    	//suiteList.add(new TestSuite(dataStructures.TestStack2.class));
    	//suiteList.add(new TestSuite(dataStructures.Teststack1oldMethod.class));
    	//suiteList.add(new TestSuite(dataStructures.Teststack2oldMethod.class));
		
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
