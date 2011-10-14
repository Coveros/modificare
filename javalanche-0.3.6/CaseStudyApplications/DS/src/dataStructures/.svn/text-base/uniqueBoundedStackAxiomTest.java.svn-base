package dataStructures;

//intstackmaxtestsimport junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;import java.util.*;


/* 
   This is an example of a TestSuite, the other component
   in the JUnit Test composite pattern
*/

public class uniqueBoundedStackAxiomTest extends TestCase 
{	   private uniqueBoundedStack numbers;   private uniqueBoundedStack otherNumbers;   
	
   public uniqueBoundedStackAxiomTest(String name) {
      super(name);
   }

   public static Test suite() 
   /* Assembles and returns a test suite containing all known tests.
      New tests should be added here!
      @return A non-null test suite.
   */
   {

      TestSuite suite = new TestSuite(uniqueBoundedStackAxiomTest.class);
	

      return suite;
   }      protected void setUp() 
   /* Sets up the text fixture.	 
      Called before every test case method.
   */	
   {	   numbers = new uniqueBoundedStack();	   otherNumbers = new uniqueBoundedStack();
   }

   protected void tearDown() 
   /* Tears down the text fixture.
      Called after every test case method.
   */
   {
      numbers = null;	  otherNumbers = null;
   }      public void testEmptyNew() 
   /* Tests the emptying of the stack.
   */
   {	   //new == true
      assertTrue(numbers.isEmpty());
   }      public void testEmptyPush() 
   /* Tests whether a stack is empty after a push.
	 */
   {	   //push == false assuming stack maxsize > 0
	  int k = 3;	  numbers.push(k);
      assertTrue(!numbers.isEmpty());
   }
   
   public void testFullNew() 
   /* Tests whether a new stack is full.
   */
   {
      assertTrue(!numbers.isFull());
   }      
       public void testFullPush() 
   /* Tests whether is a stack is full after a push of the stack.
    */
    {
	   /*
	   if ( otherNumbers.isMember(k) )	  {		  assertTrue( numbers.isFull() == otherNumbers.isFull() );	  }	  else if( otherNumbers.getNumberOfElements() >= (otherNumbers.maxSize()- 1) )	  {
		  assertTrue(numbers.isFull());
	  }
	  else
	  {
		  assertTrue(!numbers.isFull());
	  }
	   */	  int k = 3;	  int j = 2;
	//max size is 2
	  numbers.push(k);
	  assertTrue(!numbers.isFull());	  	  otherNumbers.push(k);	  numbers.push(j);
	  assertTrue(numbers.isFull());	 	  otherNumbers.push(j);	  numbers.push(k);
	  assertTrue( numbers.isFull() == otherNumbers.isFull() );
   }
   
   public void testMemberNew() 
   /* Tests membership in a new stack
   */
   {	   int k = 2;
      assertTrue(!numbers.isMember(k));
   }      public void testMemberPush() 
   /* Tests membership after a push.
	 */
   {	   //push == false assuming stack maxsize > 0
	  int k = 3;
	  int j = 2;
	  int n = 1;	  numbers.push(k);
      assertTrue(numbers.isMember(k));
	  numbers.push(j);
	  numbers.push(n);	  otherNumbers.push(k);
	  otherNumbers.push(j);
	  assertTrue( numbers.isMember(n) == otherNumbers.isMember(n) );	  
   }   
   
    public void testPopNew() 
   /* Tests the popping of the empty stack.
   */
   {	  numbers.pop();
       //assertTrue( numbers.equals(otherNumbers) );
   }      public void testPopPush() 
   /* Tests the popping of the stack with something being pushed on it.
   */
   {	   //if element pushed is not a member of the stack	   
		int k = 3;
		int j = 2;
		int n = 1;		numbers.push(k);		numbers.pop();
		assertTrue( numbers.equals(otherNumbers) );	   //now test the case where stack is full
	   //max is 2
		numbers.push(k);		numbers.push(j);
		otherNumbers.push(k);		otherNumbers.push(j);
		assertTrue(numbers.equals(otherNumbers));	   //if the stack is full then the push has no effect.
		numbers.push(n);
		numbers.pop();
		otherNumbers.pop();		//pop(push) equals a pop on the original stack
		assertTrue( numbers.equals(otherNumbers) );				//if top(s) == k		numbers.push(k);		numbers.pop();
		otherNumbers.pop();		//pop(push) when element pushed is		//already the top of the stack equals a pop on the original stack
		assertTrue( numbers.equals(otherNumbers) );				numbers.push(k);		numbers.push(j);		otherNumbers.push(k);		otherNumbers.push(j);
		
		numbers.push(k);		numbers.pop();		int oldTop = otherNumbers.top();
		otherNumbers.pop();		otherNumbers.push(k);		otherNumbers.pop();		otherNumbers.push(oldTop);		//else elem pushed is a non top member of the stack
		assertTrue( numbers.equals(otherNumbers) );	
   }         
  
    public void testTopNew() 
   /* Tests the peeking at the top of the empty stack.
   */
   {
      assertTrue(  numbers.top() == -1 );
   }
	   public void testTopPush() 
   /* Tests the peeking at the top of the stack after a push.
   */
   {
		int k = 3;
		int j = 2;
		int n = 1;		numbers.push(k);
		assertTrue( numbers.top() == k );		//now test full stack
		//max size is 2		numbers.push(j);
		otherNumbers.push(k);		otherNumbers.push(j);
		assertTrue( numbers.equals(otherNumbers) );
		assertTrue( numbers.maxSize() == numbers.getNumberOfElements() );		numbers.push(n);
		assertTrue( numbers.top() == otherNumbers.top() );		// if the elem pushed is a member...full or not
		numbers.push(k);
		assertTrue( numbers.top() == k );		
   }
   
    public void testGetNumberOfElementsNew() 
   /* Tests num elements of empty stack.
   */
   {
      assertTrue(numbers.getNumberOfElements() == 0);
   }       public void testGetNumberOfElements() 
   /* Tests the num elem of a stack after a push.
   */
	{		int k = 3;
		int j = 2;
		int n = 1;		numbers.push(k);
		assertTrue( numbers.getNumberOfElements() ==  otherNumbers.getNumberOfElements() + 1  );
		otherNumbers.push(k);
		//size is maxsize-1		numbers.push(k);		//k is already a member of numbers
		assertTrue( numbers.getNumberOfElements() ==  otherNumbers.getNumberOfElements() );		numbers.push(j);		otherNumbers.push(j);
		//size now equals maxsize(2) 		assertTrue( numbers.maxSize() == numbers.getNumberOfElements() );		numbers.push(n);
		assertTrue( numbers.getNumberOfElements() ==  otherNumbers.getNumberOfElements() );	}		public void testNewNewEquals() {
	//two new stacks	
		assertTrue(numbers.equals(otherNumbers));
    }		public void testNewPushEquals() {
		//new stack: numbers, push stack: otherNumbers
		int j = 4;	    otherNumbers.push(j);
		assertTrue(!numbers.equals(otherNumbers));
    }
	
	public void testPushNewEquals() {
	//push stack: numbers, new stack: otherNumbers
		int k = 3;	    numbers.push(k);
		assertTrue(!numbers.equals(otherNumbers));
    }
	
	public void testPushPushEquals() {
	//push stack: numbers, push stack: otherNumbers
		uniqueBoundedStack oldNumbers = new uniqueBoundedStack();
		uniqueBoundedStack oldOtherNumbers = new uniqueBoundedStack();
		int k = 3;
		int j = 4;
		int n = 1;
		//if the stack doesn't contain the pushed element	    numbers.push(k);	    otherNumbers.push(j);
		assertTrue(!numbers.equals(otherNumbers));
		
		oldNumbers = numbers;
		oldOtherNumbers = otherNumbers;
		numbers.push(n);	    otherNumbers.push(n);
		assertTrue( numbers.equals(otherNumbers) == oldNumbers.equals(oldOtherNumbers) );
		
		oldNumbers = numbers;
		oldOtherNumbers = otherNumbers;
		numbers.push(j);	    otherNumbers.push(k);
		assertTrue( numbers.equals(otherNumbers) == oldNumbers.equals(oldOtherNumbers) );
		
		otherNumbers.pop();
		oldNumbers = numbers;
		oldOtherNumbers = otherNumbers;
		numbers.push(j);	    otherNumbers.push(k);
		assertTrue( numbers.equals(otherNumbers) == oldNumbers.equals(otherNumbers) );
		
		oldNumbers = numbers;
		oldOtherNumbers = otherNumbers;
		numbers.push(j);	    otherNumbers.push(j);
		//since both full and isMember is true for otherNumbers but not numbers
		assertTrue( !numbers.equals(otherNumbers) );
		
		numbers.pop();
		oldNumbers = numbers;
		oldOtherNumbers = otherNumbers;
		numbers.push(j);
		otherNumbers.push(j);
		//since j equals top of otherNumbers
		oldOtherNumbers.pop();
		//if neither tops equal j and they equal each other then stacks are equal
		assertTrue( numbers.equals(otherNumbers) == ( oldNumbers.top() == oldOtherNumbers.top() ) );
		
		oldNumbers = numbers;
		oldOtherNumbers = otherNumbers;
		numbers.push(j);	    otherNumbers.push(k);
		//since both full and isMember is true for both and j != k .
		assertTrue( !numbers.equals(otherNumbers) );
				
		
		
		
		
    }	
  

    
   public static void main(String args[])
   /* select which runner to use and fire off the test suite
   */ 
   {
   /* in here we choose which runner to invoke and pass
      it the test case or suite we have built
   */
      String[] testCaseName = {uniqueBoundedStackAxiomTest.class.getName()};
      //junit.textui.TestRunner.main(testCaseName);
      //junit.swingui.TestRunner.main(testCaseName);
      //junit.ui.TestRunner.main(testCaseName);
   }

}//  public void testEquals() {
//	  int k = 3;//	  numbers.push(k);
   
//    assertTrue(!numbers.equals(null));
//    assertTrue(numbers, numbers);
//    assertEquals(numStack, new intStack()); // (1)
//    assertTrue(!numbers.equals(numStack));
//  }

/*
		if (oldNumbers.isFull() )
		{
			if ( oldOtherNumbers.isFull() )
			{
				if(oldNumbers.equals(oldOtherNumbers))
				{
					assertTrue(numbers.equals(otherNumbers));
				}
				else
				{
					assertTrue(!numbers.equals(otherNumbers));	
				}
			}
			else 
			{
				if ( oldNumbers.equals(otherNumbers))
				{
					assertTrue(numbers.equals(otherNumbers));
				}
				else
				{
					assertTrue(!numbers.equals(otherNumbers));	
				}
			}
		}
		else
		{
			if ( oldOtherNumbers.isFull() )
			{
				if( numbers.equals(oldOtherNumbers))
				{
					assertTrue(numbers.equals(otherNumbers));
				}
				else
				{
					assertTrue(!numbers.equals(otherNumbers));	
				}
			}
			else
			{
				if(k == j)
				{
					assertTrue(numbers.equals(otherNumbers)== oldNumbers.equals(oldOtherNumbers));
				}
				else
				{
					assertTrue(!numbers.equals(otherNumbers));	
				}
			}
		}
		*/
