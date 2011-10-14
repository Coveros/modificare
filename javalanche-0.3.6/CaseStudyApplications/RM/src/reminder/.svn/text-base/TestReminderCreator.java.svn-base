/*---------------------------------------------------------------------
 *  File: $Id: TestReminderCreator.java,v 1.1 2005/07/08 13:02:18 gkapfham Exp gkapfham $   
 *  Version:  $Revision: 1.1 $
 *
 *  Project: DIATOMS, Database drIven Application Testing tOol ModuleS
 *
 *--------------------------------------------------------------------*/

package reminder;

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
 *  Test case for the ReminderCreator class.
 *
 *  @author Gregory M. Kapfhammer 2/19/2005
 */

public class TestReminderCreator extends DatabaseTestCase
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
	"jdbc:hsqldb:hsql://localhost/ReminderDBTestOnly";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    /**
     *  Required default constructor.
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    public TestReminderCreator(String name)
    {

	super(name);

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
      	//createDatabaseServerOnce();

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

    /**
     *  Can we return a valid connection w/o exceptions?  Should be 
     *  able to make a connection to the database server **even** if 
     *  this test suite did not make the database server (previous 
     *  test setup code performed this operation).
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    public void testMakeDatabaseConnection()
    {

	for(int i = 0; i < 10; i++ )
	    {

	try
	    {
		testConnection = 
		    ReminderCreator.
		    createDatabaseConnection(testDatabaseDescription);

		assertTrue(testConnection != null);

		// close the connection to the database
		ReminderCreator.closeConnection();

	    }

	catch(Exception e)
	    {

		fail("Connecting to database caused exception");

	    }

	    }

    }

}
