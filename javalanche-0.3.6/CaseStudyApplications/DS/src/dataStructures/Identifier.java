/*****************************************************
 * Kristen Walcott
 * CS290- Lab6: Applying the Flyweight and 
 * Singleton Designs
 * October 7, 2004
 *
 * PLEDGE:________________________________
 *****************************************************/ 

package dataStructures;
//import org.apache.log4j.*;

//Overview: An Identifier is an identifier that occurs in a program.
//   Identifiers are immutable.  They are created by using an IdentifierTable. 

public class Identifier{
    //Instance variables
    private String s;                 //String representing identifier
    private boolean r;                //true=reserved, false=nonReserved

    //private static Logger log = null;
  
    //Constructor - package visible
    protected Identifier(String s, boolean r){ 
	this.s=s;
	this.r=r;
    }
    
     //Getters
    public String getName() {
	//log = getLogger();
	//NDC.push("getWord");
	//log.debug("Returning string: " + s);
	//NDC.pop();
	return s;
    }

    //Getters
    public String getWord() {
	//log = getLogger();
	//NDC.push("getWord");
	//log.debug("Returning string: " + s);
	//NDC.pop();
	return s;
    }
    public boolean getType() {
	//log = getLogger();
	//NDC.push("getType");
	//log.debug("Returning type: " + r);
	//NDC.pop();
	return r;
    }

    
//     public static Logger getLogger(){
// 	// extract the name of the file that stores the logging configuration
// 	// and call the method that performs configuration.  Note that the
// 	// variable log can be used to produce all logging output.
// 	String logging_file = System.getProperty("idstuff.logging");
// 	PropertyConfigurator.configure(logging_file);
// 	return Logger.getLogger(Identifier.class);
//     }
}
