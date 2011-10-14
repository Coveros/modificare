package TransactionAgent;

import java.util.ArrayList;

import junit.framework.*;

public class AllTests
{

    private static String DO_SHUTDOWN_PROPERTY = "diatoms.shutdown";

    public static TestSuite suite()
    {
    	/*
        TestSuite suite = new TestSuite("AllTests");

        suite.addTestSuite(TransactionAgent.TestMySQLDatabaseAgent.class);

        return suite;
        */
    	
    	ArrayList<TestSuite> suiteList = new ArrayList<TestSuite>();
        
    	TestSuite finalSuite = new TestSuite();
    	
    	suiteList.add(new TestSuite(TransactionAgent.TestMySQLDatabaseAgent.class));
    	
    	for(TestSuite suite : suiteList)
    	{
    		for(int i = 0; i < suite.countTestCases();i++)
    			finalSuite.addTest(suite.testAt(i));
    	}
    	
        return finalSuite;
    }

    public static void main(String[] args) 
    {
    
// 	String newargs[] = new String[1];
// 	newargs[0] = "gradebook.";

//	System.out.println("Before test suite run");
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
// 	String newargs[] = new String[1];
// 	newargs[0] = "gradebook.AllTests";
// 	junit.textui.TestRunner.main(newargs);

	System.out.println("After test suite run");

// 	Runtime.getRuntime().addShutdownHook( 
// 	    new Thread()
// 	    {
		
// 		public void run()
// 		{

// 		    ReminderCreator.shutdown();
		
// 		}
		
// 	    }
		
// 	    );

	// NOTE: this needs to be uncommented if we are going to run
	// the test suite by itself -- no idea why we do not have to
	// do this for FindFile but we do for the GradeBook.

	// NOTE: if you are running the test suite in the instrumented
	// fashion then you want to have this line commented out
	// because we need to have a hook into the after main so that
	// we can write out the tree structure.
	
	String doShutdownString = 
	    System.getProperty(DO_SHUTDOWN_PROPERTY);
	Boolean doShutdownBoolean = new Boolean(doShutdownString);
	boolean doShutdown = doShutdownBoolean.booleanValue();

	// we are supposed to perform test coverage monitoring
	// and thus we need to set the flag properly
	if( doShutdown )
	    {

		System.out.println("trying doShutdown");
		    
		// this will work but it seems to give some type of minor
		// error message if you print the stack trace; could enough
		// for our purposes in terms of running the experiments
		MySQLDatabaseAgent.shutdownThroughStatement();
		MySQLDatabaseAgent.closeConnection();
		System.exit(1);

	    }

	//System.out.println("After the close connection");
	
	return;
    
    } 
}
