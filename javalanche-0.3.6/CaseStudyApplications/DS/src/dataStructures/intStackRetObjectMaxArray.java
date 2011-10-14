package dataStructures;

//intstackmaxarray.java

import java.util.*;

                  
public class intStackRetObjectMaxArray 
{
	
	private int[] elems;
	private int numberOfElements; 
	private int max;
         
	public intStackRetObjectMaxArray() {
		max = 2;
		numberOfElements = 0;
		elems = new int[2];
	}
                     
	public intStackRetObjectMaxArray push(int i)  {
		if (!this.isFull())
		{
			elems[numberOfElements++] = i;
		}
		return this;
	}

	public intStackRetObjectMaxArray pop()  {
		if (!this.isEmpty())
		{
		  numberOfElements--;
		}
		 return this;
	}
	
	public int top()  {
		int temp;
			try {
				temp = elems[(numberOfElements-1)];
			}catch (ArrayIndexOutOfBoundsException exception){
				System.out.println("stack is empty");
				temp = -1;
			}
			return temp;
	}
        
	public boolean isEmpty() {
		return numberOfElements == 0;
	}
	
	public int maxSize() {
		return max;
	}
	
	public boolean isFull() {
		return numberOfElements == max;
    }
	
	
	
	public boolean equals(intStackRetObjectMaxArray s) {
		if (numberOfElements != s.getNumberOfElements())
		{
			return false;
		}
		for(int i = 0; i < numberOfElements ; i++)
		{
			if( elems[i] != (s.getArray())[i] )
			{
				return false;
			}
		}
		return true;
	}
	 
	 private int[] getArray(){
		return elems;
	}
	
	 public int getNumberOfElements(){
		return numberOfElements;
	}
} // End intStackRetObjectMaxArray


 
