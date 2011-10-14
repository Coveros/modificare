/*---------------------------------------------------------------------
 *  File: $Id: TestBeforeAllTests.java,v 1.1 2005/02/22 09:28:32 gkapfham Exp gkapfham $   
 *  Version:  $Revision: 1.1 $
 *
 *  Project: DIATOMS, Database drIven Application Testing tOol ModuleS
 *
 *--------------------------------------------------------------------*/

package gradebook;

import org.dbunit.DatabaseTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.xml.FlatXmlDataSet;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;

import java.io.FileInputStream;

/**
 *  Test Suite just starts up the database server. 
 *
 *  @author Gregory M. Kapfhammer 2/22/2005
 */

public class TestBeforeAllTests extends DatabaseTestCase
{

    /** This is the connection that will be passed to the other
	GradeBookCreator methods when we are testing them */
    private Connection testConnection;

    /** This describes the database that we will use during testing */
    private DatabaseDescription testDatabaseDescription;
    
    /** Flag to indicate whether the database server exists */
    private static boolean databaseServerExists = false;

    /** Constants required during testing */
    private static final String URL = 
	"jdbc:hsqldb:hsql://localhost/GradeBookDBTestOnly";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    /**
     *  Required default constructor.
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    public TestBeforeAllTests(String name)
    {

	super(name);

    }

    /**
     *  Convenience method that creates the database server for all of
     *  the test cases using the GradeBookCreator.  Uses static method
     *  to share data between test instances.
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    private void createDatabaseServerOnce()
    {

	try
	    {

		// need to start the HSQLDB server
		if( !databaseServerExists )
		    {

			// NOTE: should have a DatabaseServerDescription
			// class just like we have a DatabaseDescription
			// class (better abstraction)
			
			GradeBookCreator.
			    createDatabaseServer("localhost",
						 "GradeBookDBTestOnly",
						 "db/GradeBookDBTestOnly");
		
			databaseServerExists = true;

			// pause so that the server has time to start
			// up correctly
			Thread.currentThread().sleep(5000);

		    }

	    }

	catch(InterruptedException e)
	    {

		e.printStackTrace();
		System.out.println("Could not create database server");

	    }

    }

    /**
     *  Retrieves a connection (uses code that will be tested).
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    protected IDatabaseConnection getConnection() throws Exception
    {

	// describe the database
	testDatabaseDescription = 
	    new DatabaseDescription(URL, USERNAME, PASSWORD);

	// only start the database server once ; 
	// otherwise, we will see error messages related to sockets
      	createDatabaseServerOnce();
	
	// Load the HSQL Database Engine JDBC driver
	Class.forName("org.hsqldb.jdbcDriver");

	testConnection =
	    DriverManager.
	    getConnection(testDatabaseDescription.getUrl(), 
			  testDatabaseDescription.getUserName(), 
			  testDatabaseDescription.getPassword());

	return new DatabaseConnection(testConnection);

    }

    /**
     *  Retrieve the data set from a local XML file.
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    protected IDataSet getDataSet() throws Exception
    {

	return 
	    new FlatXmlDataSet(new FileInputStream("data/EmptyDataSet.xml"));

    }

    /*
     *  The database server should be running since it was started
     *  one time inside of the setUp code.
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    public void testDatabaseServerIsRunning()
    {

	assertTrue(GradeBookCreator.databaseServerIsRunning());

    }

    /**
     *  We should not be able to create the database server again;
     *  the method should return false to indicate that it was not
     *  started again.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2005
     */
    public void testDoesNotStartServerAgain()
    {

	// try multiple times to ensure that there are no sequencing
	// or timing issues assocated with creating the server

	int i = 0;
	while( i < 10 )
	    {

		assertTrue(!GradeBookCreator.
			   createDatabaseServer("localhost",
						"GradeBookDBTestOnly",
						"db/GradeBookDBTestOnly"));

		assertTrue(!GradeBookCreator.
			   createDatabaseServer("localhost",
						"GradeBookDBTestOnly",
						"db/GradeBookDBTestOnly"));

		assertTrue(!GradeBookCreator.
			   createDatabaseServer("localhost",
						"GradeBookDBTestOnly",
						"db/GradeBookDBTestOnly"));
	
		i++;

	    }

    }

}
