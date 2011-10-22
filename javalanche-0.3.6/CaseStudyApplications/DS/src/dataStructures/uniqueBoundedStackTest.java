package dataStructures;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.util.*;


/* 
   This is an example of a TestSuite, the other component
   in the JUnit Test composite pattern
*/

public class uniqueBoundedStackTest extends TestCase 
{
	
   public uniqueBoundedStackTest(String name) {
      super(name);
   }

   public static Test suite() 
   /* Assembles and returns a test suite containing all known tests.
      New tests should be added here!
      @return A non-null test suite.
   */
   {

      TestSuite suite = new TestSuite(uniqueBoundedStackTest.class);
	

      return suite;
   }
   /* Sets up the text fixture.	 
      Called before every test case method.
   */	
   {
   }

   protected void tearDown() 
   /* Tears down the text fixture.
      Called after every test case method.
   */
   {
      numbers = null;
   }
   /* Tests the emptying of the stack.
   */
   {
	  int k = 3;
      assertTrue( !numbers.isEmpty() );
	  numbers.pop();
   }
   
   public void testFull() 
   /* Tests the emptying of the stack.
   */
   {
	  int k = 3;
	  int j = 2;
      assertTrue( !numbers.isFull() );
	  numbers.pop();
   }
   
   public void testMax() 
   /* Tests whether is a stack is full after a push of the stack.
   */
   {
	//max size is 2
	  assertTrue( numbers.maxSize() == 2 );				  
   }
   
   public void testMember() 
   /* Tests the emptying of the stack.
   */
   {
	  int k = 3;
	  int j = 2;
      assertTrue( numbers.isMember(3) );
	  numbers.pop();
   }
   /* Tests the num elem of a stack.
   */
	{
	    assertTrue(numbers.getNumberOfElements() == 0 );
		assertTrue( numbers.getNumberOfElements() == 1  );
		numbers.push(k);
		numbers.push(j);
		assertTrue( numbers.getNumberOfElements() == 2  );
		assertTrue( numbers.getNumberOfElements() == 2  );
	}
   
   public void testTop() 
   /* Tests the peeking at the top of the stack.
   */
   {
      int k = 3;
	  int j = 2;
	  int n = 1;
      assertTrue( numbers.top() == k );
	  numbers.push(j);
	  assertTrue( numbers.top() == j );
	  numbers.push(n);
	  assertTrue( numbers.top() == j );
   }
   /* Tests the popping of the stack.
   */
   {
	  int k = 3; 
	  assertTrue(numbers.getNumberOfElements() == 1);
	  assertTrue(numbers.isMember(k));
      assertTrue(numbers.getNumberOfElements() == 0);
   }
   
   public void testPush() 
   /* Tests the pushing of the stack.
   */
   {
	  int k = 3;
	  int j = 2;
	  assertTrue(numbers.getNumberOfElements() == 1);
	  assertTrue(numbers.isMember(k));
	  numbers.push(k);
	  assertTrue(numbers.getNumberOfElements() == 1);
	  assertTrue(numbers.isMember(k));
	  numbers.push(j);
	  assertTrue(numbers.getNumberOfElements() == 2);
	  assertTrue(numbers.isMember(j));
	  assertTrue(numbers.isMember(k));
	  assertTrue(numbers.top() == j);
	  numbers.push(k);
	  assertTrue(numbers.getNumberOfElements() == 2);
	  assertTrue(numbers.isMember(j));
	  assertTrue(numbers.isMember(k));
	  assertTrue(numbers.top() == k);
   }
   
   
   
   
   

    
   public static void main(String args[])
   /* select which runner to use and fire off the test suite
   */ 
   {
   /* in here we choose which runner to invoke and pass
      it the test case or suite we have built
   */
      String[] testCaseName = {uniqueBoundedStackTest.class.getName()};
      //junit.textui.TestRunner.main(testCaseName);
      //junit.swingui.TestRunner.main(testCaseName);
      //junit.ui.TestRunner.main(testCaseName);
   }

}
