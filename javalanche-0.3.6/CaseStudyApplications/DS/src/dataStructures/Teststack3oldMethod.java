package dataStructures;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;import java.util.*;


/* 
   This is an example of a TestSuite, the other component
   in the JUnit Test composite pattern
*/

public class Teststack3oldMethod extends TestCase 
{	   private intStackArray numbers;   private intStackArray otherNumbers;   
	
   public Teststack3oldMethod(String name) {
      super(name);
   }

   public static Test suite() 
   /* Assembles and returns a test suite containing all known tests.
      New tests should be added here!
      @return A non-null test suite.
   */
   {

      TestSuite suite = new TestSuite(Teststack3oldMethod.class);
	

      return suite;
   }      protected void setUp() 
   /* Sets up the text fixture.	 
      Called before every test case method.
   */	
   {	   numbers = new intStackArray();	   otherNumbers = new intStackArray();
   }

   protected void tearDown() 
   /* Tears down the text fixture.
      Called after every test case method.
   */
   {
      numbers = null;	  otherNumbers = null;
   }         public void testEmpty() 
   /* Tests the emptying of the stack.
   */
   {
	  int k = 3;	  numbers.push(k);
      assertTrue(!numbers.isEmpty() );
	  numbers.pop();	  assertTrue( numbers.isEmpty() );
   }      public void testPop() 
   /* Tests the popping of the stack.
   */
   {
	  int k = 3;	  assertTrue( numbers.equals(otherNumbers) );	  numbers.push(k);	  numbers.pop();
      assertTrue( numbers.equals(otherNumbers) );
   }
   
   public void testPush() 
   /* Tests the pushing of the stack.
   */
   {
	  int k = 3;	  assertTrue( numbers.equals(otherNumbers) );	  numbers.push(k);
      assertTrue( !numbers.equals(otherNumbers) );
   }   
      public void testTop() 
   /* Tests the peeking at the top of the stack.
   */
   {
      int k = 3;	  numbers.push(k);
      assertTrue( numbers.top() == k );
   }

    
   public static void main(String args[])
   /* select which runner to use and fire off the test suite
   */ 
   {
   /* in here we choose which runner to invoke and pass
      it the test case or suite we have built
   */
      String[] testCaseName = {Teststack3oldMethod.class.getName()};
      //junit.textui.TestRunner.main(testCaseName);
      //junit.swingui.TestRunner.main(testCaseName);
      //junit.ui.TestRunner.main(testCaseName);
   }

}

