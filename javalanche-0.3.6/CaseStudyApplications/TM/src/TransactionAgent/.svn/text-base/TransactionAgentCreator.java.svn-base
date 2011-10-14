package TransactionAgent;


import org.hsqldb.Server;

import java.sql.*;

/**
 *  This class creates the table(s) that handle TransactionAgent.
 *
 *  @author Gregory M. Kapfhammer 2/22/2005
 */

public class TransactionAgentCreator
{

        /** This is the connection to the database that we 
	are managing in this class */
    private static Connection databaseConnection = null;

    /** This is the HSQLDB server that manages all of the 
	connections to the database */
    private static Server databaseServer = null;

    /** Flag to indicate whether we have created the server */
    private static boolean databaseServerCreated = false;

    /** The database Index: we assume that there is just one 
	database that we will be connecting to in order to 
	store all important data */
    private static final int DATABASE_INDEX = 0;

    /** Error code to indicate that server is correctly online */
    private static final int SERVER_ONLINE = 1;

    /** Constants required during default usage */
    private static final String URL = 
	"jdbc:hsqldb:hsql://localhost/TransactionManagerDBMain";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    /** The default DatabaseDescription */
    public static final DatabaseDescription 
	defaultDatabaseDescription = 
	new DatabaseDescription(URL, USERNAME, PASSWORD);

    /** The default silentOption is false, if we go through the 
	create method then it can be set to true */
    private static boolean isSilent = true;

    /**
     *  Creates the default database server.
     *  
     *  @author Gregory M. Kapfhammer 3/7/2005
     */
    public static boolean createDefaultDatabaseServer()
    {

	boolean databaseServerExists = false;

	// NOTE: should have a DatabaseServerDescription
	// class just like we have a DatabaseDescription
	// class (better abstraction)
			
	isSilent = true;

	TransactionAgentCreator.
	    createDatabaseServer("localhost",
				 "TransactionManagerDBMain",
				 "/home/gkapfham/working/research/diatoms/resources/examples/Student/db/TransactionManagerDBMain");
	
	databaseServerExists = true;

	try
	    {

		// pause so that the server has time to start
		// up correctly
		Thread.currentThread().sleep(5000);

	    }

	catch(InterruptedException e)
	    {

		System.out.println("Unable to sleep thread");

	    }

	return databaseServerExists;

    }

    /**
     *  Creates a in-process HSQLDB server and thus avoids the need 
     *  to run an extra program on the command line.
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    public static boolean createDatabaseServer(String address,
					       String name,
					       String path)
    {

	// flag indicates whether we have actually tried to 
	// create a server inside this specific method call;
	// assume that we have not and prove otherwise
	boolean attemptedStart = false;

	// the server has never been constructed and we need 
	// to create it first
	if( !databaseServerCreated )
	    {

		// create the database server and then assign
		// the important characteristics to it
		databaseServer = new Server();

		// this will throw an exception if the server is 
		// somehow running (i.e. there was a previous start
		// and then an external crash, etc)
		//databaseServer.checkRunning(false);
	
		databaseServer.setAddress(address);
		databaseServer.setDatabaseName(DATABASE_INDEX,
					       name);
		databaseServer.setDatabasePath(DATABASE_INDEX,
					       path);

		// set the server to operate in silent mode
		databaseServer.setSilent(isSilent);

		// we are required to start the server
		startDatabaseServer(databaseServer);

		attemptedStart = true;
	
	    }

	return attemptedStart;

    }

    /**
     *  Start the database server.  Indicate that the server was started
     *  using a flag that is available for access to any GBC that is 
     *  loaded in the same address space (note: we cannot rely upon this
     *  when using JUnit/DBUnit because it is loaded with a separate
     *  classloader).
     *  
     *  @author Gregory M. Kapfhammer 2/22/2005
     */
    private static void startDatabaseServer(Server pleaseStart)
    {

	// start the database server and create some 
	// diagnostic information 
	pleaseStart.start();
	databaseServerCreated = true;

    }

    /**
     *  Checks to see if the server is runing correctly.  See HSQLDB
     *  JavaDoc for information about the error codes from getState().
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    public static boolean databaseServerIsRunning()
    {

	// not quite right when we have multiple address spaces...
	if( databaseServer != null && 
	    databaseServer.getState() == SERVER_ONLINE )
	    {

		return true;

	    }

	return false;

    }

    /**
     *  Creates a connection to the database, using provided description.
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    public static Connection createDatabaseConnection(DatabaseDescription
						      describe)    
    {
	
	try 
	    {

		// the connection is not null (avoid NPEs) and it is
		// closed so we do not need to re-connect
		if( databaseConnection != null && 
		    !databaseConnection.isClosed() )
		    {
			
			return databaseConnection;
			
		    }

		// Load the HSQL Database Engine JDBC driver
		Class.forName("org.hsqldb.jdbcDriver");

		// extract the information from the database 
		// description that explains how we connect
		// (following the HSQLDB 1.7.2 syntax for socket)
		databaseConnection =
		    DriverManager.
		    getConnection(describe.getUrl(), 
				  describe.getUserName(), 
				  describe.getPassword());
	    
	    }

	catch(Exception e)
	    {
		
		e.printStackTrace();
		
	    }
		
    	return databaseConnection;

    }						      

    /**
     *  Creates a connection to an HSQLDB Database. **File version
     *  is being maintained, but not to be used in the future.**
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    public static Connection createDatabaseConnection(String dbName,
						      String directory,
						      String username,
						      String password)
    {

	try 
	    {

		// Load the HSQL Database Engine JDBC driver
		Class.forName("org.hsqldb.jdbcDriver");

		// Connect to the database It will be create
		// automatically if it does not yet exist
		// 'testfiles' in the URL is the name of the
		// database "sa" is the user name and "" is
		// the (empty) password
		databaseConnection =
		    DriverManager.
		    getConnection("jdbc:hsqldb:" + directory +
				  dbName, username, password);

	    }
		
	catch(Exception e)
	    {
		
		e.printStackTrace();
		
	    }

	return databaseConnection;

    }

    /**
     *  Closes the connection to the database.  Used for testing purposes
     *  and otherwise.  Closes through HSQLDB SHUTDOWN command.
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    public static void closeConnection()
    {

	try
	    {

		if( !databaseConnection.isClosed() )
		    {

			// Create a statement object
			Statement stat = 
			    databaseConnection.createStatement();

			databaseConnection.close();
			
			// this actually closes down the HSQLDB
			// database server and we do not want that
			//stat.execute("SHUTDOWN");

		    }

	    }

	catch(SQLException e)
	    {

		e.printStackTrace();

	    }


    }

    /**
     *  Closes the connection to the database.  Used for testing purposes
     *  and otherwise.  Closes through HSQLDB SHUTDOWN command.
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    public static void shutdownThroughStatement()
    {

	try
	    {

		if( !databaseConnection.isClosed() )
		    {

			// Create a statement object
			Statement stat = 
			    databaseConnection.createStatement();

			//databaseConnection.close();
			
			// this actually closes down the HSQLDB
			// database server and we do not want that
			stat.execute("SHUTDOWN");
			
		    }

	    }

	catch(SQLException e)
	    {

		e.printStackTrace();

	    }


    }

    /**
     *  Closes the connection to the database.  Used for testing purposes
     *  and otherwise.  Closes through HSQLDB SHUTDOWN command.
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    public static void shutdown()
    {

	try
	    {

		databaseServer.shutdown();
		
	    }

	catch(Exception e)
	    {

		e.printStackTrace();

	    }

    }

    /**
     *  This method simply creates a database conncetion (if this was
     *  not already done) and then creates a statement.  The Statement
     *  is returned so that it can be used by other methods that need
     *  to execute the preamble.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2005
     */
    private static Statement preamble(DatabaseDescription describe)
    {

	Statement stat = null;

	try
	    {

		// get a connection to the database ; if we are already
		// connected then we will just use the same connection
		createDatabaseConnection(describe);
		
		// Create a statement object
		stat = databaseConnection.createStatement();
		
	    }

	catch(SQLException e)
	    {

		e.printStackTrace();

	    }
		
	// we can use this statement object
	return stat;

    }

    /**
     *  Drops the specified table.  This will be invoked by all of the 
     *  other DROP methods.  We can create this parameterized method 
     *  because the only thing that varies is the name of the table.
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    private static void dropTable(DatabaseDescription describe, 
				  String tableName)
	throws SQLException
    {

	Statement stat = preamble(describe);
	stat.executeUpdate("DROP TABLE " + tableName);
		
    }

}
