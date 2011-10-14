/*****************************************************
 * Kristen Walcott
 * CS290- Lab6: Applying the Flyweight and 
 * Singleton Designs
 * October 7, 2004
 *
 * PLEDGE:________________________________
 *****************************************************/ 
package dataStructures;

import junit.framework.*;
//import identifier.*;
import java.util.*;

//JUnit test suite to test the methods of IdentifierTable
public class TestIdentifierTable extends TestCase 
{
    private IdentifierTable idtable;

    private IdentifierTable _table;
    
    public TestIdentifierTable(String name) 
    {
	super(name);
    }
    
    public static void main (String[] args) {
	junit.textui.TestRunner.run (suite());
    }
    
    //setUp: Do each time before a test is run 
    public void setUp(){
	idtable=new IdentifierTable();
	_table = new IdentifierTable();
    }
    
    public static Test suite() {
	return new TestSuite(TestIdentifierTable.class);
    }

    /* testMakeReserved1: Inserts reserved identifier and makes sure it
     * was successfully inserted.  
     * Note: This depends on idents() being correct.  However, idents()
     * just returns the Iterator for a hashtable and is quite simple and
     * not likely a place for error.  If this test fails, the correctness
     * of idents() should probably be checked further though. */
    public void testMakeReserved1()throws WrongKindOfIdentifierException{
	//NDC.push("testMakeReserved1");
	Identifier ident = idtable.makeReserved("try");
	Identifier inserted = (Identifier)((Iterator)idtable.idents()).next();
	//NDC.pop();
	assertEquals(inserted, ident);
    }

    /* testMakeNonReserved1: Inserts non-reserved identifier and makes sure it
     * was successfully inserted. */
    public void testMakeNonReserved1()throws WrongKindOfIdentifierException{
	//NDC.push("testMakeNonReserved1");
	Identifier ident = idtable.makeNonReserved("monkey");
	Identifier inserted = (Identifier)((Iterator)idtable.idents()).next();
	//NDC.pop();
	assertEquals(inserted, ident);
    }

    /* testMakeReserved2: Inserts a non-reserved identifier and then adds the
     * same identifier as a reserved identifier. An exception should be thrown. */
    public void testMakeReserved2(){
	//NDC.push("testMakeReserved2");
	try{
	    Identifier ident = idtable.makeNonReserved("gnome");
	    Identifier ident2 = idtable.makeReserved("gnome");
	    //NDC.pop();
	    fail("Should raise a WrongKindOfIdentifierException");
	}
	catch (WrongKindOfIdentifierException wkie){ //NDC.pop();
	}
    }
    
    /* testMakeNonReserved2: Inserts a reserved identifier and then adds the
     * same identifier as a nonreserved identifier. An exception should be thrown */
    public void testMakeNonReserved2(){
	//NDC.push("testMakeNonReserved2");
	try{
	    Identifier ident = idtable.makeReserved("catch");
	    Identifier ident2 = idtable.makeNonReserved("catch");
	    //NDC.pop();
	    fail("Should raise a WrongKindOfIdentifierException");
	}
	catch (WrongKindOfIdentifierException wkie){ //NDC.pop();

	}
    }

    /* testMakeReserved3: Inserts 2 reserved identifiers that are the same and 
     * makes sure only 1 is inserted. */
    public void testMakeReserved3()throws WrongKindOfIdentifierException{
	//NDC.push("testMakeReserved3");
	Identifier ident1 = idtable.makeReserved("new");
	Identifier ident2 = idtable.makeReserved("new");
	Iterator iterator = (Iterator)idtable.idents();
	int numelems=0;
	Identifier expected= null;
	while(iterator.hasNext()){
	    expected = (Identifier)iterator.next();
	    numelems++;
	}
	//NDC.pop();
	assertEquals(expected, ident2);
	assertEquals(numelems, 1);
	      
    }

    /* testMakeNonReserved3: Inserts 2 non-reserved identifiers that are the same and 
     * makes sure only 1 is inserted. */
    public void testMakeNonReserved3()throws WrongKindOfIdentifierException{
	//NDC.push("testMakeNonReserved3");
	Identifier ident1 = idtable.makeNonReserved("foo");
	Identifier ident2 = idtable.makeNonReserved("foo");
	Iterator iterator = (Iterator)idtable.idents();
	int numelems=0;
	Identifier expected = null;
	while(iterator.hasNext()){
	    expected = (Identifier)iterator.next();
	    numelems++;
	}
	//NDC.pop();
	assertEquals(expected, ident2);
	assertEquals(numelems, 1);
    }

    //Can trust this result once TestIdentifier has been run- need to know 
    //that getType() works first.
    /* testMakeMany: This test tries to add 4 reserved identifiers and 4
     * non-reserved identifiers. One of the non-reserved identifiers is the
     * same as an earlier one, and another of the non-reserved identifiers
     * was added previously as reserved.  
     * This test checks that repeats and errors are not added into the
     * IdentifierTable.
     */
    public void testMakeMany()throws WrongKindOfIdentifierException{
	//NDC.push("testMakeMany");
	int expectedReserved = 4;
	int expectedNonReserved = 2;
	idtable.makeReserved("catch");
	idtable.makeReserved("try");
	idtable.makeNonReserved("gnome");
	idtable.makeReserved("new");
	idtable.makeReserved("if");
	idtable.makeNonReserved("imp");
	idtable.makeNonReserved("gnome");
	try{
	    idtable.makeNonReserved("try");
	    //NDC.pop();
	    fail("Should raise a WrongKindOfIdentifierException");
	}
	catch(WrongKindOfIdentifierException wkie){

	    //NDC.pop();
	}
  	Iterator iterator = (Iterator)idtable.idents();
	int numReserved = 0;
	int numNonReserved = 0;
	while(iterator.hasNext()){
	    Identifier identifier = (Identifier)iterator.next();
	    if(identifier.getType()==true)
		numReserved++;
	    else if (identifier.getType()==false)
		numNonReserved++;
	}
	//NDC.pop();
	assertEquals(expectedReserved, numReserved);
	assertEquals(expectedNonReserved, numNonReserved);
    }
    
    /* testempty: Checks the idents method when the identifierTable 
     * is empty*/
    public void testEmpty(){
	//NDC.push("testEmpty");
	int expected = 0;
	Iterator iterator = (Iterator)idtable.idents();
	int i = 0;
	while(iterator.hasNext()){
	    iterator.next();
	    i++;
	}
	//NDC.pop();
	assertEquals(expected, i);
    }   

    // other tests from rummel

     /**
     * testMakeReserved
     *
     * @author Jim Clause
     * @author Brandon Taylor
     * @version 1.0
     */
    public void testMakeReserved_new() throws WrongKindOfIdentifierException 
    {

	
	_table.makeReserved("for");
         assertTrue(_table.contains("for"));
    }

     public void testMakeReserved_exists_reserved() {
        try {
            _table.makeReserved("for");

	    assertTrue(_table.contains("for"));
	    assertEquals(_table.makeReserved("for").getName(), "for");
	    
	}
        catch(Exception e) {
            fail("Could not make reserved word");
        }
        
    }

    public void testMakeReserved_exists_nonreserved() {
        
        try{
	    _table.makeNonReserved("for");
	    assertTrue(_table.contains("for"));
	    _table.makeReserved("for");
            fail();
        }
        catch(WrongKindOfIdentifierException e) {
            System.out.println("Caught the exception");
        }
    }

    /**
     * testMakeNonReserved
     *
     * @author Jim Clause
     * @author Brandon Taylor
     * @version 1.0
     */
    public void testMakeNonReserved() throws WrongKindOfIdentifierException
    {
        _table.makeReserved("x");
        assertTrue(_table.contains("x"));
    }

    public void testMakeNonReserved_exists_reserved() {
        try {

            _table.makeNonReserved("x");
    
	    assertTrue(_table.contains("x"));
	    assertEquals(_table.makeNonReserved("x").getName(), "x");
	    
	}
        catch(Exception e) {
            fail("Could not make non-reserved word");
        }
    
    }

     public void testMakeNonReserved_exists_nonreserved() {

        try{

	    _table.makeReserved("x");
	    assertTrue(_table.contains("x"));

            _table.makeNonReserved("x");
            fail();
        }
        catch(Exception e) {
            System.out.println("Caught the exception");
        }
    }

    /**
     * testIdents
     *
     * @author Jim Clause
     * @author Brandon Taylor
     * @version 1.0
     */
    public void testIdents() {
        if(_table.idents() instanceof Iterator) {}

        else {
            fail("Didn't get back an iterator");
        }
    }
    


}
