package dataStructures;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/* 
   This is an example of a TestSuite, the other component
   in the JUnit Test composite pattern
*/

public class Teststack2oldMethod extends TestCase 
{
	
   public Teststack2oldMethod(String name) {
      super(name);
   }

   public static Test suite() 
   /* Assembles and returns a test suite containing all known tests.
      New tests should be added here!
      @return A non-null test suite.
   */
   {

      TestSuite suite = new TestSuite(Teststack2oldMethod.class);
	

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
      assertTrue(!(numbers.push(k)).isEmpty());
   }
   /* Tests the popping of the stack.
   */
   {
	  int k = 3; 
      assertTrue( ( ( numbers.push(k) ).pop() ).equals(otherNumbers) );
   }
   
   public void testPush() 
   /* Tests the pushing of the stack.
   */
   {
	  int k = 3;
      assertTrue( !( numbers.push(k) ).equals(otherNumbers) );
   }
   
   public void testFull() 
   /* Tests whether is a stack is full after a push of the stack.
   */
   {
	//max size is 2
	  numbers.push(k);				
	//size is now max-1
   }
   
   public void testGetNumberOfElements() 
   /* Tests the num elem of a stack.
   */
	{
		assertTrue( ( numbers.push(k) ).getNumberOfElements() == ( otherNumbers.getNumberOfElements() + 1 ) );
		otherNumbers.push(k);
		//size is maxsize-1
		assertTrue( numbers.getNumberOfElements() ==  otherNumbers.getNumberOfElements() );
		assertTrue( ( numbers.push(k) ).getNumberOfElements() ==  ( otherNumbers.getNumberOfElements() + 1 ) );
		//size now equals maxsize(2) 
		assertTrue( ( numbers.push(k) ).getNumberOfElements() ==  otherNumbers.getNumberOfElements() );
	*/
	{
		assertTrue(numbers.maxSize() == max);
   
   /* Tests the peeking at the top of the stack.
   */
   {
      int k = 3;
      assertTrue( ( numbers.push(k) ).top() == k );
   }

    
   public static void main(String args[])
   /* select which runner to use and fire off the test suite
   */ 
   {
   /* in here we choose which runner to invoke and pass
      it the test case or suite we have built
   */
      String[] testCaseName = {Teststack2oldMethod.class.getName()};
      //junit.textui.TestRunner.main(testCaseName);
      //junit.swingui.TestRunner.main(testCaseName);
      //junit.ui.TestRunner.main(testCaseName);
   }

}
