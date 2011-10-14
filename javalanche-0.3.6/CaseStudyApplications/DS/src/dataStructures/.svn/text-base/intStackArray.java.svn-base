package dataStructures;

import java.util.*;
 
 class intStackArray {
	private int[] elems;
	private int top; 
         
	public intStackArray() {
		top = 0;
		elems = new int[10];
	}
                     
	public void push(int i)  {
		if (top < 10)
		{
			elems[top++] = i;
		}
	}

	public void pop()  {
		if (top > 0)
		{
		  top--;
		}
	}
	
	public int top()  {
		int temp;
			try {
				temp = elems[(top-1)];
			}catch (ArrayIndexOutOfBoundsException exception){
				System.out.println("stack is empty");
				temp = -1;
			}
			return temp;
	}
        
	public boolean isEmpty() {
		return top == 0;
	}
	
	public boolean equals(intStackArray s) {
		if (top != s.getTop())
		{
			return false;
		}
		for(int i = 0; i<top ; i++)
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
	
	 private int getTop(){
		return top;
	}
}
