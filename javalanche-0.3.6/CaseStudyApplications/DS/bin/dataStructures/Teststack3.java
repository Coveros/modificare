package dataStructures;

//intstacktestsimport junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;import java.util.*;


/* 
   This is an example of a TestSuite, the other component
   in the JUnit Test composite pattern
*/

public class Teststack3 extends TestCase 
{	   private intStackArray numbers;   private intStackArray otherNumbers;   
	
   public Teststack3(String name) {
      super(name);
   }

   public static Test suite() 
   /* Assembles and returns a test suite containing all known tests.
      New tests should be added here!
      @return A non-null test suite.
   */
   {

       // GMK NOTE: this is the normal way that this would be executed

       // TestSuite suite = new TestSuite(Teststack3.class);

       // GNK NOTE: this is what we would do if we want to run 
       // a single test case ; we need to devise an approach that
       // would let us run the tests separately and then sum
       // the time for all in some automated way

       TestSuite suite= new TestSuite();
       suite.addTest(new Teststack3("testEmpty"));


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
   }      public void testEmpty() 
   /* Tests the emptying of the stack.
   */
   {
      assertTrue(numbers.isEmpty());
   }      public void testNotEmpty() 
   /* Tests the emptying of the stack.
   */
   {
	  int k = 3;	  numbers.push(k);
      assertTrue(!numbers.isEmpty());
   }   
   public void testPopNew() 
   /* Tests the popping of the empty stack.
   */
   {
	  assertTrue( numbers.equals(otherNumbers) ); 	  numbers.pop(); 
      assertTrue( numbers.equals(otherNumbers) );
   }
      public void testPop() 
   /* Tests the popping of the stack with something being pushed on it.
   */
   {
	  int k = 3; 	  assertTrue( numbers.equals(otherNumbers) );	  numbers.push(k);	  numbers.pop();
      assertTrue( numbers.equals(otherNumbers) );
   }   
         public void testTopNew() 
   /* Tests the peeking at the top of the empty stack.
   */
   {
      assertTrue(  numbers.top() == -1 );
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
      String[] testCaseName = {Teststack3.class.getName()};
      //junit.textui.TestRunner.main(testCaseName);
      //junit.swingui.TestRunner.main(testCaseName);
      //junit.ui.TestRunner.main(testCaseName);
   }

}
