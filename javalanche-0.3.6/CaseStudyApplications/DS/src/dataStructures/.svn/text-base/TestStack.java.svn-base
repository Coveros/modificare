package dataStructures;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/* 
   This is an example of a TestSuite, the other component
   in the JUnit Test composite pattern
*/

public class TestStack extends TestCase 
{
	
   public TestStack(String name) {
      super(name);
   }

   public static Test suite() 
   /* Assembles and returns a test suite containing all known tests.
      New tests should be added here!
      @return A non-null test suite.
   */
   {

      TestSuite suite = new TestSuite();
	
      // The stack tests go here.	  
      suite.addTest(Teststack3.suite());	 
      //suite.addTest(Teststack4.suite());
      //suite.addTest(Teststack3oldMethod.suite());
      //suite.addTest(Teststack4oldMethod.suite());
      return suite;
   
   }

    
   public static void main(String args[])
   /* select which runner to use and fire off the test suite
   */ 
   {
      String[] testCaseName = {TestStack.class.getName()};
      //junit.textui.TestRunner.main(testCaseName);
      //junit.swingui.TestRunner.main(testCaseName);
      //junit.ui.TestRunner.main(testCaseName);
   }

}
