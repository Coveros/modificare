/*---------------------------------------------------------------------
 *  File: $Id: TestReduceUsingDelayedGreedy.java,v 1.26 2007/07/02 13:36:17 smitha Exp $   
 *  Version:  $Revision: 1.26 $
 *
 *  Project: DIATOMS, Database drIven Application Testing tOol ModuleS
 *
 *--------------------------------------------------------------------*/

package redupr;

import java.util.Set;
import java.util.LinkedHashSet;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Vector;

import java.io.PrintWriter;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.TestCase;

import java.util.ArrayList;

//import diatoms.profiler.Profiler;

/**
 *  Test suite for the ReduceUsingDelayedGreedy class.
 *
 *  @author Adam M. Smith 7/01/2007
 */

public class AllTests extends TestCase
{
	public static TestSuite suite() 
	{
		ArrayList<TestSuite> suiteList = new ArrayList<TestSuite>();
		TestSuite finalSuite = new TestSuite();
/*
        suite.addTestSuite(TestReduceUsingHarroldGuptaSoffa.class);
        suite.addTestSuite(TestReduceUsingDelayedGreedy.class);
		suite.addTestSuite(TestReduceUsingKWay.class);
		suite.addTestSuite(TestReduceUsingGreedy.class);
        suite.addTestSuite(TestSetCover.class);
    */
        
        suiteList.add(new TestSuite(TestReduceUsingHarroldGuptaSoffa.class));
        suiteList.add(new TestSuite(TestReduceUsingDelayedGreedy.class));
        suiteList.add(new TestSuite(TestReduceUsingGreedy.class));
        suiteList.add(new TestSuite(TestReduceUsingKWay.class));
        suiteList.add(new TestSuite(TestSetCover.class));
        
        for(TestSuite suite : suiteList)
    	{
    		for(int i = 0; i < suite.countTestCases();i++)
    			finalSuite.addTest(suite.testAt(i));
    	}
    	
        return finalSuite;
    }	

   public static void main(String[] args) {

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

