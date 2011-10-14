/*****************************************************
 * Kristen Walcott
 * CS290- Lab6: Applying the Flyweight and 
 * Singleton Designs
 * October 7, 2004
 *
 * PLEDGE:________________________________
 *****************************************************/ 

package dataStructures;

import java.util.*;
//import org.apache.log4j.*;


// An IdentifierTable is a mutable collection of Identifier
// objects; each distinct string has at most one entry in the table.
public class IdentifierTable{
    private Hashtable hash;
    //private static Logger log = null;

    //constructor:
    public IdentifierTable(){
	//Effects: Makes this be the empty IdentifierTable
	//	log = getLogger();
	//log.info("Making new IdentifierTable");
	hash = new Hashtable();
    }
    
    //methods:
    public Identifier makeReserved(String s)
	throws WrongKindOfIdentifierException{
	//Modifies: this
	//Effects: If s is already in this as a reserved word, returns
	//  the prestored object.  Else if it is this as a nonreserved word
        //  throws WrongKindOfIdentifierException. Else adds s to this as 
	//  a reserved word.
	//NDC.push("makeReserved");
	//log = getLogger();
	//log.info("In makeReserved");
	Identifier value = (Identifier)hash.get(s);
	//log.debug("Identifier equals null: " + (value==null));
	if(value!=null){
	    //log.info("String already exists in IdentifierTable");
	    //log.debug("Type is " + value.getType());
	    if(value.getType()==true){
		//NDC.pop();
		return value;
	    }
	    else{
		//log.info("String was labeled as nonreserved.  ERROR");
		//NDC.pop();
		throw new WrongKindOfIdentifierException();
	    }  
	}
	else{
	    //log.info("String does not exist in IdentifierTable.");
	    //log.info("Making new entry.");
	    //log.debug("String = " + s);
	    Identifier ident = new Identifier(s,true); 
	    hash.put(s, ident);
	    ///NDC.pop();
	    return ident;
	}
    }
    
    public Identifier makeNonReserved(String s)
	throws WrongKindOfIdentifierException{
	//Modifies: this
	//Effects: If s is already in this as a nonreserved word, returns
	//  the prestored object.  Else if it is this as a reserved word
        //  throws WrongKindOfIdentifierException. Else adds s to this as 
	//  a nonreserved word.
	//NDC.push("makeNonReserved");
	//log = getLogger();
	//log.info("In makeNonReserved");
	Identifier value = (Identifier)hash.get(s);
	//log.debug("Identifier equals null: " + (value==null));
	if(value!=null){
	    //log.info("String already exists in IdentifierTable");
	    //log.debug("Type is " + value.getType());
	    if(value.getType()==false){
		//NDC.pop();
		return value;
	    }
	    else{
		//log.info("String was labeled as reserved.  ERROR");
		//NDC.pop();
		throw new WrongKindOfIdentifierException();  
	    }
	}
	else{
	    //log.info("String does not exist in IdentifierTable.");
	    //log.info("Making new entry.");
	    //log.debug("New identifier string = " + s);
	    Identifier ident = new Identifier(s,false); 
	    hash.put(s, ident);
	    //NDC.pop();
	    return ident;
	}
    }
    
    public Iterator idents(){
	//Effects: Returns a generator that will produce all identifiers in this,
	//   each exactly once, in arbitrary order.
	//Requires: this not be modified what generator is in use.
	return (hash.values()).iterator();
    }    

    public boolean contains(String str) {
        Iterator iter = idents();
        while(iter.hasNext()) {
            //return false;
            
	    if(((Identifier)iter.next()).getName().equals(str)) 
		return true; 
	    // error 3
        }
        return false;
    }

    
//     public static Logger getLogger(){
// 	// extract the name of the file that stores the logging configuration
// 	// and call the method that performs configuration.  Note that the
// 	// variable log can be used to produce all logging output.
// 	String logging_file = System.getProperty("idstuff.logging");
// 	//PropertyConfigurator.configure(logging_file);
// 	return Logger.getLogger(IdentifierTable.class);
//     }
}
