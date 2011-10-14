package dataStructures;

//intstacktestsimport junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;import java.util.*;


/* 
   This is an example of a TestSuite, the other component
   in the JUnit Test composite pattern
*/

public class Teststack1 extends TestCase 
{	   private intStackRetObjectArray numbers;   private intStackRetObjectArray otherNumbers;   
	
   public Teststack1(String name) {
      super(name);
   }

   public static Test suite() 
   /* Assembles and returns a test suite containing all known tests.
      New tests should be added here!
      @return A non-null test suite.
   */
   {

      TestSuite suite = new TestSuite(Teststack1.class);
	

      return suite;
   }      protected void setUp() 
   /* Sets up the text fixture.	 
      Called before every test case method.
   */	
   {	   numbers = new intStackRetObjectArray();	   otherNumbers = new intStackRetObjectArray();
   }

   protected void tearDown() 
   /* Tears down the text fixture.
      Called after every test case method.
   */
   {
      numbers = null;	  otherNumbers = null;
   }      public void testEmpty() 
   /* Tests the emptying of the stack.
   */
   {
      assertTrue(numbers.isEmpty());
   }      public void testNotEmpty() 
   /* Tests the emptying of the stack.
   */
   {
	  int k = 3;
      assertTrue(!(numbers.push(k)).isEmpty());
   }      public void testPop() 
   /* Tests the popping of the stack with something being pushed on it.
   */
   {
	  int k = 3; 	  assertTrue( numbers.equals(otherNumbers) );
      assertTrue( ( ( numbers.push(k) ).pop() ).equals(otherNumbers) );
   }   
   public void testPopNew() 
   /* Tests the popping of the empty stack.
   */
   { 
      assertTrue( ( numbers.pop() ).equals(otherNumbers) );
   }
      public void testTop() 
   /* Tests the peeking at the top of the stack.
   */
   {
      int k = 3;
      assertTrue( ( numbers.push(k) ).top() == k );
   }

   public void testTopNew() 
   /* Tests the peeking at the top of the empty stack.
   */
   {
      assertTrue(  numbers.top() == -1 );
   }

    
   public static void main(String args[])
   /* select which runner to use and fire off the test suite
   */ 
   {
   /* in here we choose which runner to invoke and pass
      it the test case or suite we have built
   */
      String[] testCaseName = {Teststack1.class.getName()};
      //junit.textui.TestRunner.main(testCaseName);
      //junit.swingui.TestRunner.main(testCaseName);
      //junit.ui.TestRunner.main(testCaseName);
   }

}//  public void testEquals() {
//	  int k = 3;//	  numbers.push(k);
   
//    assertTrue(!numbers.equals(null));
//    assertTrue(numbers, numbers);
//    assertTrueEquals(numStack, new intStack()); // (1)
//    assertTrue(!numbers.equals(numStack));
//  }

