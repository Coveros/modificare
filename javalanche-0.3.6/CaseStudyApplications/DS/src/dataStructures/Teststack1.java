package dataStructures;

//intstacktests
import junit.framework.TestCase;
import junit.framework.TestSuite;


/* 
   This is an example of a TestSuite, the other component
   in the JUnit Test composite pattern
*/

public class Teststack1 extends TestCase 
{
	
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
      assertTrue(numbers.isEmpty());
   }
   /* Tests the emptying of the stack.
   */
   {
	  int k = 3;
      assertTrue(!(numbers.push(k)).isEmpty());
   }
   /* Tests the popping of the stack with something being pushed on it.
   */
   {
	  int k = 3; 
      assertTrue( ( ( numbers.push(k) ).pop() ).equals(otherNumbers) );
   }
   public void testPopNew() 
   /* Tests the popping of the empty stack.
   */
   { 
      assertTrue( ( numbers.pop() ).equals(otherNumbers) );
   }
   
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

}
//	  int k = 3;
   
//    assertTrue(!numbers.equals(null));
//    assertTrue(numbers, numbers);
//    assertTrueEquals(numStack, new intStack()); // (1)
//    assertTrue(!numbers.equals(numStack));
//  }
