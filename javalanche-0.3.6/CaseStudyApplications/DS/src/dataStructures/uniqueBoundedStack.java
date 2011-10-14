package dataStructures;

/**
 * uniqueBoundedStack.java 
 *
 */
 
import java.io.*;
import java.awt.*;

public class uniqueBoundedStack {

	private int[] elems;
	private int numberOfElements; 
	private int max;

/*
	public uniqueBoundedStack();
	public void push(int i);
	public void pop();
	public int top(); 
	public boolean isEmpty();
	public int maxSize();
	public boolean isMember(int i);
	public boolean equals(uniqueBoundedStack s);
	private int[] getArray;
	public int getNumberOfElements();
*/

	uniqueBoundedStack()
	{
		numberOfElements = 0;
		max = 2;
		elems = new int[max];
	}

	void push(int i)
	{
		int index;
		boolean alreadyMember;

		alreadyMember = false;

		for( index=0; index<numberOfElements; index++)
		{
			if( i==elems[index])
			{
				alreadyMember = true;
				break;
			}
		}
		
		if ( alreadyMember)
		{
			for ( int j=index; j<numberOfElements-1; j++)
			{
				elems[j] = elems[j+1];
			}
			elems[numberOfElements-1] = i;
		}
		else
		{
			if ( numberOfElements < max)
			{
				elems[numberOfElements] = i;
				numberOfElements++;
				return;
			}
			else
			{
				System.out.println("Stack full, cannot push");
				return;
			}
		}
	}

	void pop()
	{
		numberOfElements --;
	}

	int top()
	{
		if ( numberOfElements < 1)
		{
			System.out.println("Empty Stack");
			return -1;
		}
		else
			return elems[numberOfElements-1];
	}

	boolean isEmpty()
	{
		if ( numberOfElements==0)
			return true;
		else
			return false;
	}

	int maxSize()
	{
		return max;
	}

	boolean isMember(int i)
	{
		for( int index=0; index<numberOfElements; index++)
			if( i==elems[index])
				return true;
		return false;
			
	}

	boolean equals(uniqueBoundedStack s)
	{
		
		if ( s.maxSize() != max)
			return false;
		if ( s.getNumberOfElements() != numberOfElements)
			return false;
		
		int [] sElems = s.getArray();
		
		for ( int j=0; j<numberOfElements; j++)
		{
			if ( elems[j] != sElems[j])
				return false;
		}
		return true;
	}

	int[] getArray()
	{
		int [] a;
		a = new int[max];
		for ( int j=0; j<numberOfElements; j++)
			a[j] = elems[j];
		return a;
	}
	
	int getNumberOfElements()
	{
		return numberOfElements;
	}
	
	public boolean isFull() {
		return numberOfElements == max;
    }
	

	/* change max to 3 to use the following test */
	/*
	public static void main(String argv[])
	{

		uniqueBoundedStack myubs = new uniqueBoundedStack();
		uniqueBoundedStack myubs2 = new uniqueBoundedStack();


		int a = 5;
		myubs.push(a);
		myubs2.push(a);
		a = 7;
		myubs.push(a);
		myubs2.push(a);
		a = 5;
		myubs.push(a);
		myubs2.push(a);
		
		System.out.println("Top myubs " + myubs.top());
		
		System.out.println("isEmpty myubs " + myubs.isEmpty());
		
		int [] all1;
		all1 = myubs.getArray();
		for ( int i=0; i<myubs.getNumberOfElements(); i++)
			System.out.println("Element " + i + " " + all1[i]);
		
		System.out.println("myubs equals myubs2 ? "+myubs.equals(myubs2));

		myubs.pop();	
		
		System.out.println("isEmpty myubs " + myubs.isEmpty());
		
		System.out.println("myubs equals myubs2 ? "+myubs.equals(myubs2));
		
		int [] all2;
		all2 = myubs.getArray();
		for ( int i=0; i<myubs.getNumberOfElements(); i++)
			System.out.println("Element " + i + " " + all2[i]);
		myubs.pop();	
		
		System.out.println("isEmpty myubs " + myubs.isEmpty());
		
		int [] all3;
		all3 = myubs.getArray();
		for ( int i=0; i<myubs.getNumberOfElements(); i++)
			System.out.println("Element " + i + " " + all3[i]);

		
	}
	*/
};
