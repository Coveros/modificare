/*---------------------------------------------------------------------
 *  File: $Id: GradeBookCreator.java,v 1.6 2005/03/08 15:25:38 gkapfham Exp gkapfham $   
 *  Version:  $Revision: 1.6 $
 *
 *  Project: DIATOMS, Database drIven Application Testing tOol ModuleS
 *
 *--------------------------------------------------------------------*/

package gradebook;

import org.hsqldb.Server;

import java.sql.*;

/**
 *  This class creates the tables that are used by the GradeBook.
 *
 *  @@author Gregory M. Kapfhammer 2/19/2005
 */

public class GradeBookCreator
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
	"jdbc:hsqldb:hsql://localhost/GradeBookDBMain";
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
     *  @@author Gregory M. Kapfhammer 3/7/2005
     */
    public static boolean createDefaultDatabaseServer()
    {

	boolean databaseServerExists = false;

	// NOTE: should have a DatabaseServerDescription
	// class just like we have a DatabaseDescription
	// class (better abstraction)
			
	isSilent = true;
	//isSystemExit = false;

	GradeBookCreator.
	    createDatabaseServer("localhost",
				 "GradeBookDBMain",
				 "/home/gkapfham/working/research/diatoms/resources/examples/GradeBook/db/GradeBookDBMain");
	
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
     *  @@author Gregory M. Kapfhammer 2/19/2005
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
     *  @@author Gregory M. Kapfhammer 2/22/2005
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
     *  @@author Gregory M. Kapfhammer 2/19/2005
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
     *  @@author Gregory M. Kapfhammer 2/19/2005
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
     *  @@author Gregory M. Kapfhammer 2/19/2005
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
     *  @@author Gregory M. Kapfhammer 2/19/2005
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
     *  @@author Gregory M. Kapfhammer 2/19/2005
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
     *  Closes the connection to the database.  Used for testing purposes
     *  and otherwise.  Closes through HSQLDB SHUTDOWN command.
     *  
     *  @@author Gregory M. Kapfhammer 2/19/2005
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

		//e.printStackTrace();

	    }


    }

    /**
     *  This method simply creates a database conncetion (if this was
     *  not already done) and then creates a statement.  The Statement
     *  is returned so that it can be used by other methods that need
     *  to execute the preamble.
     *  
     *  @@author Gregory M. Kapfhammer 2/22/2005
     */
    private static Statement preamble(DatabaseDescription describe)
    {

	Statement stat = null;

	try
	    {

		// get a connection to the database ; if we are already
		// connected then we will just use the same connection
		createDatabaseConnection(describe);
		
		// GMK MAKES CHANGE

		// Create a statement object
		stat = databaseConnection.
		    createStatement();
		
// 		databaseConnection.
// 		    createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
// 				    ResultSet.CONCUR_UPDATABLE);
	    }

	catch(SQLException e)
	    {

		System.out.println("from the app");
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
     *  @@author Gregory M. Kapfhammer 2/19/2005
     */
    private static void dropTable(DatabaseDescription describe, 
				  String tableName)
	throws SQLException
    {

	Statement stat = preamble(describe);
	stat.executeUpdate("DROP TABLE " + tableName);
		
    }

    /**
     *  Creates all of the tables that should be inside of the database.
     *  
     *  @@author Gregory M. Kapfhammer 3/7/2005
     */
    public static void createAllTables(DatabaseDescription describe)
	throws SQLException
    {

	createMasterTable(describe);
	createStudentTable(describe);

	createExamMasterTable(describe);
	createExamScoresTable(describe);
	
	createLabMasterTable(describe);
	createLabScoresTable(describe);

	createFinalProjectScoresTable(describe);

	createHomeworkMasterTable(describe);
	createHomeworkScoresTable(describe);

    }

    /**
     *  Creates the Master table that stores the percentages to 
     *  the different categories for the course.  Assumes that the
     *  table does not already exist in the database.
     *  
     *  @@author Gregory M. Kapfhammer 2/19/2005
     */
    public static void createMasterTable(DatabaseDescription describe)
	throws SQLException
    {

	// perform the preamble computation to create a statement that
	// can be used for creation
	Statement stat = preamble(describe);

        // For compatibility to other database, use varchar(255)
        // In HSQL Database Engine, length is unlimited, like Java Strings
        stat.execute("CREATE TABLE Master" + 
		     "(" +
		     "Id INTEGER IDENTITY, " +
		     "Participation DECIMAL, " +
		     "ExamOne DECIMAL, " +
		     "ExamTwo DECIMAL, " +
		     "ExamThree DECIMAL, " +
		     "Laboratory DECIMAL, " +
		     "Homework DECIMAL," +
		     "FinalProject DECIMAL" + 
		     ")" );

	// Close the Statement object, it is no longer used
        stat.close();

    }

    /** NOTE: For all of the contains... methods, we could be using
	DBUnit and the schema extraction code.  This is actually more 
	useful than simply trying to create the method again and 
	getting an exception! */

    /**
     *  Tries to create the table again, gets an error and knows that 
     *  the table does indeed exist.
     *  
     *  @@author Gregory M. Kapfhammer 2/19/2005
     */
    public static boolean containsMasterTable(DatabaseDescription 
					      describe)
    {

	try
	    {

		createMasterTable(describe);

	    }

	catch(SQLException e)
	    {

		return true;

	    }

	return false;

    }

    /**
     *  Drops the master table.
     *  
     *  @@author Gregory M. Kapfhammer 2/19/2005
     */
    public static void dropMasterTable(DatabaseDescription describe)
	throws SQLException
    {

	dropTable(describe, GradeBookConstants.MASTER);

    }

    /**
     *  Creates the table that stores the information about students.
     *  
     *  @@author Gregory M. Kapfhammer 2/19/2005
     */
    public static void createStudentTable(DatabaseDescription describe)
	throws SQLException
    {

	// perform the preamble computation to create a statement that
	// can be used for creation
	Statement stat = preamble(describe);

        // For compatibility to other database, use varchar(255)
        // In HSQL Database Engine, length is unlimited, like Java Strings
        stat.execute("CREATE TABLE Student" + 
		     "(" +
		     "Id INTEGER IDENTITY, " +
		     "FirstName VarChar(255), " +
		     "LastName VarChar(255), " +
		     "Email VarChar(255)" + 
		     ")" );

 	// Close the Statement object, it is no longer used
	stat.close();

    }

    /**
     *  Tries to create the table again, gets an error and knows that 
     *  the table does indeed exist.
     *  
     *  @@author Gregory M. Kapfhammer 2/19/2005
     */
    public static boolean containsStudentTable(DatabaseDescription 
					      describe)
    {

	try
	    {

		createStudentTable(describe);

	    }

	catch(SQLException e)
	    {

		return true;

	    }

	return false;

    }

    /**
     *  Drops the Student table.
     *  
     *  @@author Gregory M. Kapfhammer 2/19/2005
     */
    public static void dropStudentTable(DatabaseDescription describe)
	throws SQLException
    {

	dropTable(describe, GradeBookConstants.STUDENT);

    }

    /**
     *  Creates the table that stores the information about ExamMaster.
     *  
     *  @@author Gregory M. Kapfhammer 2/19/2005
     */
    public static void createExamMasterTable(DatabaseDescription describe)
	throws SQLException
    {

	// perform the preamble computation to create a statement that
	// can be used for creation
	Statement stat = preamble(describe);

        // For compatibility to other database, use varchar(255)
        // In HSQL Database Engine, length is unlimited, like Java Strings
        stat.execute("CREATE TABLE ExamMaster" + 
		     "(" +
		     "ExamId INTEGER IDENTITY, " +
		     "Name VarChar(255), " +
		     "TotalPoints INTEGER, " +
		     "Curve INTEGER" + 
		     ")" );

 	// Close the Statement object, it is no longer used
	stat.close();

    }

    /**
     *  Tries to create the table again, gets an error and knows that 
     *  the table does indeed exist.
     *  
     *  @@author Gregory M. Kapfhammer 2/19/2005
     */
    public static boolean containsExamMasterTable(DatabaseDescription 
					      describe)
    {

	try
	    {

		createExamMasterTable(describe);

	    }

	catch(SQLException e)
	    {

		return true;

	    }

	return false;

    }

    /**
     *  Drops the ExamMaster table.
     *  
     *  @@author Gregory M. Kapfhammer 2/19/2005
     */
    public static void dropExamMasterTable(DatabaseDescription describe)
	throws SQLException
    {

	dropTable(describe, GradeBookConstants.EXAMMASTER);

    }

    /**
     *  Creates the table that stores the information about ExamMaster.
     *  
     *  @@author Gregory M. Kapfhammer 2/19/2005
     */
    public static void createExamScoresTable(DatabaseDescription describe)
	throws SQLException
    {

	// perform the preamble computation to create a statement that
	// can be used for creation
	Statement stat = preamble(describe);

	// NOTE: This should not have set ExamId to an IDENTITY

        // For compatibility to other database, use varchar(255)
        // In HSQL Database Engine, length is unlimited, like Java Strings
        stat.execute("CREATE TABLE ExamScores" + 
		     "(" +
		     "ExamId INTEGER, " +
		     "StudentID INTEGER, " +
		     "EarnedPoints INTEGER " +
		     ")" );

 	// Close the Statement object, it is no longer used
	stat.close();

    }

    /**
     *  Tries to create the table again, gets an error and knows that 
     *  the table does indeed exist.
     *  
     *  @@author Gregory M. Kapfhammer 2/19/2005
     */
    public static boolean containsExamScoresTable(DatabaseDescription 
						  describe)
    {

	try
	    {

		createExamScoresTable(describe);

	    }

	catch(SQLException e)
	    {

		return true;

	    }

	return false;

    }

    /**
     *  Drops the ExamScores table.
     *  
     *  @@author Gregory M. Kapfhammer 2/19/2005
     */
    public static void dropExamScoresTable(DatabaseDescription describe)
	throws SQLException
    {

	dropTable(describe, GradeBookConstants.EXAMSCORES);

    }

    /**
     *  Creates the table that stores the information about LabMaster.
     *  
     *  @@author Gregory M. Kapfhammer 2/19/2005
     */
    public static void createLabMasterTable(DatabaseDescription describe)
	throws SQLException
    {

	// perform the preamble computation to create a statement that
	// can be used for creation
	Statement stat = preamble(describe);

        // For compatibility to other database, use varchar(255)
        // In HSQL Database Engine, length is unlimited, like Java Strings
        stat.execute("CREATE TABLE LabMaster" + 
		     "(" +
		     "LabId INTEGER IDENTITY, " +
		     "Name VARCHAR(255), " +
		     "TotalPoints INTEGER" + 
		     ")" );

 	// Close the Statement object, it is no longer used
	stat.close();

    }

    /**
     *  Tries to create the table again, gets an error and knows that 
     *  the table does indeed exist.
     *  
     *  @@author Gregory M. Kapfhammer 2/19/2005
     */
    public static boolean containsLabMasterTable(DatabaseDescription 
						 describe)
    {

	try
	    {

		createLabMasterTable(describe);

	    }

	catch(SQLException e)
	    {

		return true;

	    }

	return false;

    }

    /**
     *  Drops the LabMaster table.
     *  
     *  @@author Gregory M. Kapfhammer 2/19/2005
     */
    public static void dropLabMasterTable(DatabaseDescription describe)
	throws SQLException
    {

	dropTable(describe, GradeBookConstants.LABMASTER);

    }

    /**
     *  Creates the table that stores the information about LabScores.
     *  
     *  @@author Gregory M. Kapfhammer 2/19/2005
     */
    public static void createLabScoresTable(DatabaseDescription describe)
	throws SQLException
    {

	// perform the preamble computation to create a statement that
	// can be used for creation
	Statement stat = preamble(describe);

        // For compatibility to other database, use varchar(255)
        // In HSQL Database Engine, length is unlimited, like Java Strings
        stat.execute("CREATE TABLE LabScores" + 
		     "(" +
		     "LabId INTEGER, " +
		     "StudentId INTEGER, " +
		     "EarnedPoints INTEGER" + 
		     ")" );

 	// Close the Statement object, it is no longer used
	stat.close();

    }

    /**
     *  Tries to create the table again, gets an error and knows that 
     *  the table does indeed exist.
     *  
     *  @@author Gregory M. Kapfhammer 2/19/2005
     */
    public static boolean containsLabScoresTable(DatabaseDescription 
						 describe)
    {

	try
	    {

		createLabScoresTable(describe);

	    }

	catch(SQLException e)
	    {

		return true;

	    }

	return false;

    }

    /**
     *  Drops the LabScores table.
     *  
     *  @@author Gregory M. Kapfhammer 2/19/2005
     */
    public static void dropLabScoresTable(DatabaseDescription describe)
	throws SQLException
    {

	dropTable(describe, GradeBookConstants.LABSCORES);

    }
    
    /**
     *  Creates the table that stores the information about FinalProjectScores.
     *  
     *  @@author Gregory M. Kapfhammer 2/19/2005
     */
    public static void createFinalProjectScoresTable(DatabaseDescription 
						     describe)
	throws SQLException
    {

	// perform the preamble computation to create a statement that
	// can be used for creation
	Statement stat = preamble(describe);

        // For compatibility to other database, use varchar(255)
        // In HSQL Database Engine, length is unlimited, like Java Strings
        stat.execute("CREATE TABLE FinalProjectScores" + 
		     "(" +
		     "StudentId INTEGER, " +
		     "EarnedPoints INTEGER" + 
		     ")" );

 	// Close the Statement object, it is no longer used
	stat.close();

    }

    /**
     *  Drops the FinalProjectScores table.
     *  
     *  @@author Gregory M. Kapfhammer 2/19/2005
     */
    public static void dropFinalProjectScoresTable(DatabaseDescription 
						   describe)
	throws SQLException
    {

	dropTable(describe, GradeBookConstants.FINALPROJECTSCORES);

    }

    /**
     *  Tries to create the table again, gets an error and knows that 
     *  the table does indeed exist.
     *  
     *  @@author Gregory M. Kapfhammer 2/19/2005
     */
    public static boolean containsFinalProjectScoresTable(DatabaseDescription 
							   describe)
    {

	try
	    {

		createFinalProjectScoresTable(describe);

	    }

	catch(SQLException e)
	    {

		return true;

	    }

	return false;

    }

    /**
     *  Creates the table that stores the information about LabMaster.
     *  
     *  @@author Gregory M. Kapfhammer 2/19/2005
     */
    public static void createHomeworkMasterTable(DatabaseDescription describe)
	throws SQLException
    {

	// perform the preamble computation to create a statement that
	// can be used for creation
	Statement stat = preamble(describe);

        // For compatibility to other database, use varchar(255)
        // In HSQL Database Engine, length is unlimited, like Java Strings
        stat.execute("CREATE TABLE HomeworkMaster" + 
		     "(" +
		     "HomeworkId INTEGER IDENTITY, " +
		     "Name VARCHAR(255), " +
		     "TotalPoints INTEGER" + 
		     ")" );

 	// Close the Statement object, it is no longer used
	stat.close();

    }

    /**
     *  Tries to create the table again, gets an error and knows that 
     *  the table does indeed exist.
     *  
     *  @@author Gregory M. Kapfhammer 2/19/2005
     */
    public static boolean containsHomeworkMasterTable(DatabaseDescription 
						      describe)
    {

	try
	    {

		createHomeworkMasterTable(describe);

	    }

	catch(SQLException e)
	    {

		return true;

	    }

	return false;

    }

    /**
     *  Drops the HomeworkMaster table.
     *  
     *  @@author Gregory M. Kapfhammer 2/19/2005
     */
    public static void dropHomeworkMasterTable(DatabaseDescription describe)
	throws SQLException
    {

	dropTable(describe, GradeBookConstants.HOMEWORKMASTER);

    }

    /**
     *  Creates the table that stores the information about HomeworkScores.
     *  
     *  @@author Gregory M. Kapfhammer 2/19/2005
     */
    public static void createHomeworkScoresTable(DatabaseDescription describe)
	throws SQLException
    {

	// perform the preamble computation to create a statement that
	// can be used for creation
	Statement stat = preamble(describe);

        // For compatibility to other database, use varchar(255)
        // In HSQL Database Engine, length is unlimited, like Java Strings
        stat.execute("CREATE TABLE HomeworkScores" + 
		     "(" +
		     "HomeworkId INTEGER, " +
		     "StudentId INTEGER, " +
		     "EarnedPoints INTEGER" + 
		     ")" );

 	// Close the Statement object, it is no longer used
	stat.close();

    }

    /**
     *  Tries to create the table again, gets an error and knows that 
     *  the table does indeed exist.
     *  
     *  @@author Gregory M. Kapfhammer 2/19/2005
     */
    public static boolean containsHomeworkScoresTable(DatabaseDescription 
						      describe)
    {

	try
	    {

		createHomeworkScoresTable(describe);

	    }

	catch(SQLException e)
	    {

		return true;

	    }

	return false;

    }

    /**
     *  Drops the HomeworkScores table.
     *  
     *  @@author Gregory M. Kapfhammer 2/19/2005
     */
    public static void dropHomeworkScoresTable(DatabaseDescription describe)
	throws SQLException
    {

	dropTable(describe, GradeBookConstants.HOMEWORKSCORES);

    }

    /**
     *  Prints out the SQL statements that are executed by this 
     *  program.  This could help us to generate the schemas.
     *  
     *  @author Gregory M. Kapfhammer 3/7/2005
     */
    public static void main(String[] args)
    {

	System.out.println("CREATE TABLE Master" + 
			   "(" +
			   "Id INTEGER IDENTITY, " +
			   "Participation DECIMAL, " +
			   "ExamOne DECIMAL, " +
			   "ExamTwo DECIMAL, " +
			   "ExamThree DECIMAL, " +
			   "Laboratory DECIMAL, " +
			   "Homework DECIMAL," +
			   "FinalProject DECIMAL" + 
			   ");" );

        System.out.println("CREATE TABLE Student" + 
		     "(" +
		     "Id INTEGER IDENTITY, " +
		     "FirstName VarChar(255), " +
		     "LastName VarChar(255), " +
		     "Email VarChar(255)" + 
		     ");" );

	System.out.println("CREATE TABLE ExamMaster" + 
			   "(" +
			   "ExamId INTEGER IDENTITY, " +
			   "Name VarChar(255), " +
			   "TotalPoints INTEGER, " +
			   "Curve INTEGER" + 
			   ");" );

        System.out.println("CREATE TABLE ExamScores" + 
		     "(" +
		     "ExamId INTEGER, " +
		     "StudentID INTEGER, " +
		     "EarnedPoints INTEGER " +
		     ");" );

	System.out.println("CREATE TABLE LabMaster" + 
		     "(" +
		     "LabId INTEGER IDENTITY, " +
		     "Name VARCHAR(255), " +
		     "TotalPoints INTEGER" + 
		     ");" );
	
	System.out.println("CREATE TABLE LabScores" + 
			   "(" +
			   "LabId INTEGER, " +
			   "StudentId INTEGER, " +
			   "EarnedPoints INTEGER" + 
			   ");" );

        System.out.println("CREATE TABLE FinalProjectScores" + 
		     "(" +
		     "StudentId INTEGER, " +
		     "EarnedPoints INTEGER" + 
		     ");" );

	System.out.println("CREATE TABLE HomeworkMaster" + 
		     "(" +
		     "HomeworkId INTEGER IDENTITY, " +
		     "Name VARCHAR(255), " +
		     "TotalPoints INTEGER" + 
		     ");" );

	System.out.println("CREATE TABLE HomeworkScores" + 
		     "(" +
		     "HomeworkId INTEGER, " +
		     "StudentId INTEGER, " +
		     "EarnedPoints INTEGER" + 
		     ");" );

    }

}
