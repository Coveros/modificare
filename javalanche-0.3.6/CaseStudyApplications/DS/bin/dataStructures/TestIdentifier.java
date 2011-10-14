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

//JUnit test suite to test the Identifier class
public class TestIdentifier extends TestCase 
{
    private IdentifierTable idtable;

    private Identifier _id;
    
    public TestIdentifier(String name) 
    {
	super(name);
    }
    
    public static void main (String[] args) {
	junit.textui.TestRunner.run (suite());
    }

    // setup: Runs each time a new test is run
    public void setUp(){

	idtable=new IdentifierTable();
	_id = new Identifier("When",true);

    }
    
    public static Test suite() {
	return new TestSuite(TestIdentifier.class);
    }

    //Can trust these results once testMakeReserved(1-3)() and 
    //testMakeNonReserved(1-3)() have passed in TestIdentifierTable
    /* testGetReservedWord: Tests the getWord() method
     * assuming that we have confidence that makeReserved works.
     */
    public void testGetReservedWord() throws WrongKindOfIdentifierException{
	//NDC.push("testGetReservedWord");
	String expectedWord = "try";
	Identifier ident = idtable.makeReserved("try");
	String word = ident.getWord();
	//NDC.pop();
	assertEquals(expectedWord, word);
    }
    
    /* testGetReservedType: Tests the getType() method
     * assuming that we have confidence that makeReserved works.
     */
    public void testGetReservedType() throws WrongKindOfIdentifierException{
	//NDC.push("testGetReservedType");
	boolean expectedType = true;
	Identifier ident = idtable.makeReserved("try");
	boolean type = ident.getType();
	//NDC.pop();
	assertEquals(expectedType, type);
    }

    /* testGetNonReservedWord: Tests the getWord() method
     * assuming that we have confidence that makeNonReserved works.
     */
    public void testGetNonReservedWord()throws WrongKindOfIdentifierException{
	//NDC.push("testGetNonReservedWord");
	String expectedWord = "monkey";
	Identifier ident = idtable.makeNonReserved("monkey");
	String word = ident.getWord();
	//NDC.pop();
	assertEquals(expectedWord, word);
    }

    /* testGetNonReservedType: Tests the getType() method
     * assuming that we have confidence that makeNonReserved works.
     */
     public void testGetNonReservedType()throws WrongKindOfIdentifierException{
	 //NDC.push("testGetNonReservedType");
	 boolean expectedType = false;
	 Identifier ident = idtable.makeNonReserved("monkey");
	 boolean type = ident.getType();
	 //NDC.pop();
	 assertEquals(expectedType, type);
     }

    // rummel tests

    
    /**
     * testConstructor1
     *
     * Tests the first constructor for the Identifer class
     *
     *@author Jim Clause
     *@author Brandon Taylor
     *@see identifier.Identifier
     */
    public void testConstructor1() {
        try {
            _id = new Identifier("While", false);
        }
        catch (Exception e) {
            fail("Could not construct Identifier");
        }
    }

     /**
     * testGetReserved
     *
     * Tests the getReserved method for the Identifier method
     * @author Chris Howell
     * @author Brandon Taylor
     * @version 1.0
     */
    public void testGetReservedfail() {
        String s = null;
        try {
            _id = new Identifier(s, true);
        }
        catch (Exception e) {
            fail("Get reserved failed");
        }
    }

        /**
     * testGetnonReserved
     *
     * Tests the getnonReserved method for the Identifier method
     * @author Chris Howell
     * @author Brandon Taylor
     * @version 1.0
     */
    public void testGetNonReservedfail() {
        String s = null;
        try {
            _id = new Identifier(s, true);

        }
        catch (Exception e) {
            fail("Get nonreserved failed");
        }
    }

    /**
     * testGetName
     *
     * Tests the getName method for the Identifier method
     * @author Jim Clause
     * @author Brandon Taylor
     * @version 1.0
     */
    public void testGetName() {
        assertEquals("When", _id.getName());
    }

    /**
     * testSetReserved
     *
     * Tests the getReserved method for the Identifier method
     * @author Jim Clause
     * @author Brandon Taylor
     * @version 1.0
     */
    public void testSetReserved() {
	
        assertEquals(true, _id.getType());
    }
    
}

