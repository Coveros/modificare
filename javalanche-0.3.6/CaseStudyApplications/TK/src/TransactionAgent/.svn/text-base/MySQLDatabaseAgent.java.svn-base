/*---------------------------------------------------------------------
 *  File: $Id: MySQLDatabaseAgent.java,v 1.1 2002/03/20 18:13:17 gkapfham Exp $   
 *  Version:  $Revision: 1.1 $
 *
 *  Project: Reverification Prototype Testbed, 
 *
 *--------------------------------------------------------------------*/

/** diatomsNODBdiatoms */

package TransactionAgent;

import org.hsqldb.Server;

import java.sql.*;

import java.io.*;

//import ATMEntry.*;

//import diatoms.analysis.marker.LocalDatabaseEntity;

/**
 *  This class implements the DatabaseAgent interface and thus provides
 *  concrete implementations for the operations that are necessary to 
 *  perform Bank transactions against a mySQL database.
 *
 *  @author Gregory M. Kapfhammer 4/12/2000
 */

public class MySQLDatabaseAgent implements DatabaseAgent
{

  /** String that describes the data source name (DSN) that the Agent 
      should interact with to provide the appropriate information to the 
      Bank. This refers to the name of the database inside mySQL.
  */
  private String m_bank_dsn;	
  
  /** Connection object that will be initialized when the DSN is set and 
      then used whenever the Agent must query or modify the Bank's 
      database.
  */
  private Connection m_connect;

    // begin stuff from findFile
    
    /** The default silentOption is false, if we go through the 
	create method then it can be set to true */
    private static boolean isSilent = true;

    /** The default system exit option is true, if we go through the
	create method then it can be set to false */
    private static boolean isSystemExit = true;
    
    /**  Use the existing facilities within FindFile to create 
	 a connection to our database server (use the same 
	 variable name as was used in the FindFile code to ensure
	 that it is easy to compile the entire system) */  
    private static Connection conn; 

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

    // end stuff from findfile

  /** Flags that are used because it is not possible to store true 
      "boolean" values inside a mySQL database : must instead store integers */
  public static final int TRUE = 1;
  public static final int FALSE = 0;					 

  /**
     Constructor for the class that simply calls the constructor in the 
     superclass, which is UnicastRemoteObject.  This ensures that the service
     can be invokved through RMI as long as the service is alive.
     
     @author Gregory M. Kapfhammer 4/13/2000
  */
  public MySQLDatabaseAgent() 
  {

    super();
    
  }

    public MySQLDatabaseAgent(DatabaseDescription describe)
    {

	databaseConnection = 
	    createDatabaseConnection(describe);

    }

  /**
     Constructor for the class that simply calls the constructor in the 
     superclass, which is UnicastRemoteObject.  This ensures that the service
     can be invokved through RMI as long as the service is alive.  This 
     constructor also sets the Banks data source name and sets up the 
     necessary connection with the database.
     
     @author Gregory M. Kapfhammer 4/13/2000
  */
  public MySQLDatabaseAgent(String bank_dsn) throws SQLException,
  ClassNotFoundException
  {

    super();

    // HSQLDB additions: try to create the new database
    
    // Exceptions may occur
    try {

	// try to create the default database server 
	createDefaultDatabaseServer();

	// Load the HSQL Database Engine JDBC driver
	Class.forName("org.hsqldb.jdbcDriver");


    }

    catch(Exception e)
	{

	    System.out.println("Exception happened during Server Creation");
	    e.printStackTrace();

	}

    setBankDSN(bank_dsn);

  }

    public static boolean createDefaultDatabaseServer()
    {

	boolean databaseServerExists = false;

	// NOTE: should have a DatabaseServerDescription
	// class just like we have a DatabaseDescription
	// class (better abstraction)
			
	// ensure that the database server is as silent as possible
	// (i.e., do not echo all of the SQL statements) and ensure
	// that the entire program exits when the server exits
	isSilent = true;
	isSystemExit = false;

	MySQLDatabaseAgent.
	    createDatabaseServer("localhost",
				 "TransactionManagerDBMain",
				 "/home/gkapfham/working/research/diatoms/resources/examples/atmsim/TransactionAgent/db/TransactionManagerDBMain");
	
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

		// set the server to call system.exit when it 
		// is shut down (this is useful for the FindFile
		// application because shutting down the database
		// server is the last thing that FindFile does)
		//databaseServer.setNoSystemExit(isSystemExit);

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
     This methods sets the data source name with which the Agent will interact.
     After the DSN is established, the Connection object is created so that 
     other methods can easily query and modify the currently used database. 
     
     @author Gregory M. Kapfhammer 4/12/2000
  */
  public void setBankDSN(String bank_dsn) throws SQLException, 
  ClassNotFoundException
  {
    
    m_bank_dsn = bank_dsn;
    
    // this is the old version of the code that uses MySQL

    //System.out.println("bankDSN: " + m_bank_dsn);
    
    // password is currently hardcoded so that one user can access 
    // all of the possible database accounts that might ever be created
    //Class.forName("org.gjt.mm.mysql.Driver");	
    //String url = "jdbc:mysql://alden28/" + m_bank_dsn; 
    
    //System.out.println("Complete URL: " + url);
    
    //m_connect = DriverManager.getConnection(url,"atm","atm111");

    //System.out.println("Connection:" + m_connect);

    // this is the new version of the code that is supposed to use
    // HSQLDB -- since this is where the connection code was we
    // will put the new connection code here as well!

    m_connect = createDatabaseConnection(defaultDatabaseDescription);

  }
  
  /**
      This method returns the data source name with which the 
      MySQLDatabaseAgent is interacting.  This is just the name of the 
      database that is registered with mySQL.
      
      @author Gregory M. Kapfhammer 4/12/2000
  */
  public String getBankDSN()
  {
    
    return m_bank_dsn;	
    
  }

    // this method has been added in order to create the database 
    // tables inside of hsqldb and then place the default data inside
    // of the database -- this code was basically taken from the script
    // create_bankDB_Account inside the directory:
    // /home/gkapfham/working/research/diatoms/resources/examples/atmsim/bankDB
    public void createDefaultBankState()
    {

	try
	    {

		// Create a statement object
		Statement stat = databaseConnection.createStatement();

		// Try to drop the table
		try {
		    stat.executeUpdate("DROP TABLE Account");
		} catch (SQLException e) {    // Ignore Exception,
					      // because the table may
					      // not yet exist
		}

		// **** populate the UserInfo table
		// **** with data -- use the
		// data that is from the MySQL script for now

		// Try to drop the table
		try {
		    stat.executeUpdate("DROP TABLE UserInfo");
		} catch (SQLException e) {    // Ignore Exception,
					      // because the table may
					      // not yet exist
		}

		stat.executeUpdate("CREATE TABLE UserInfo ( card_number int not null identity, pin_number int not null, user_name varchar(50) not null, acct_lock int );");

		// 1. INSERT INTO UserInfo values (1, 12345, "Alden100", 0);

		stat.executeUpdate("INSERT INTO UserInfo values (1, 12345, \'Alden100\', 0);");

		// 2. INSERT INTO UserInfo values (2, 12345, "Alden101", 0);
		
		stat.executeUpdate("INSERT INTO UserInfo values (2, 12345, \'Alden101\', 0);");

		// 3. INSERT INTO UserInfo values (3, 12345, "Alden102", 0);

		stat.executeUpdate("INSERT INTO UserInfo values (3, 12345, \'Alden102\', 0);");

		// 4. INSERT INTO UserInfo values (4, 12345, "Alden103", 0);

		stat.executeUpdate("INSERT INTO UserInfo values (4, 12345, \'Alden103\', 0);");
		
		// 5. INSERT INTO UserInfo values (5, 12345, "Alden104", 0);
		
		stat.executeUpdate("INSERT INTO UserInfo values (5, 12345, \'Alden104\', 0);");

		// 6. INSERT INTO UserInfo values (6, 12345, "Alden105", 0);

		stat.executeUpdate("INSERT INTO UserInfo values (6, 12345, \'Alden105\', 0);");

		// 7. INSERT INTO UserInfo values (7, 12345, "Alden106", 0);

		stat.executeUpdate("INSERT INTO UserInfo values (7, 12345, \'Alden106\', 0);");

		// 8. INSERT INTO UserInfo values (8, 12345, "Alden107", 0);

		stat.executeUpdate("INSERT INTO UserInfo values (8, 12345, \'Alden107\', 0);");
		
		// 9. INSERT INTO UserInfo values (9, 12345, "Alden108", 0);

		stat.executeUpdate("INSERT INTO UserInfo values (9, 12345, \'Alden108\', 0);");

 		// Close the Statement object, it is no longer used
	 	//stat.close();

		// For compatibility to other database, use
		// varchar(255) In HSQL Database Engine, length is
		// unlimited, like Java Strings -- note that this had
		// to be changed somewhat significantly in order to 
		// work correctly with HSQLDB -- not sure if all of the 
		// translation was correct or not!
		stat.execute("CREATE TABLE Account ( id int identity, account_name varchar(50) not null, user_name varchar(50) not null, balance int default '0', card_number int, FOREIGN KEY(card_number) REFERENCES UserInfo(card_number) );");

		// **** populate the database table Account with some
		// basic data for now -- also taken from the script

		// 1. INSERT INTO Account values (1, "Primary
		//  Checking", "Alden100", 1800, 1);

		stat.
		    executeUpdate("INSERT INTO Account values (1, \'Primary Checking\', \'Alden100\', 1800, 1);");

		// 2. INSERT INTO Account values (2, "Primary Checking",
		// "Alden101", 1800, 2);

		stat.
		    executeUpdate("INSERT INTO Account values (2, \'Primary Checking\', \'Alden101\', 1800, 2);");

		// 3. INSERT INTO Account values (3, "Primary Checking",
		// "Alden102", 3000, 3);

		stat.
		    executeUpdate("INSERT INTO Account values (3, \'Primary Checking\', \'Alden102\', 3000, 3);");

		// 4. INSERT INTO Account values (4, "Primary Checking",
		// "Alden103", 2100, 4);

		stat.
		    executeUpdate("INSERT INTO Account values (4, \'Primary Checking\', \'Alden103\', 2100, 4);");

		// 5. INSERT INTO Account values (5, "Primary Checking",
		// "Alden104", 5700, 5);

		stat.
		    executeUpdate("INSERT INTO Account values (5, \'Primary Checking\', \'Alden104\', 5700, 5);");
		
		// 6. INSERT INTO Account values (6, "Secondary
		// Checking", "Alden104", 5700, 5);

		stat.
		    executeUpdate("INSERT INTO Account values (6, \'Secondary Checking\', \'Alden104\', 5700, 5);");

		// 7. INSERT INTO Account values (7, "Primary Checking",
		// "Alden106", 3700, 6);

		stat.
		    executeUpdate("INSERT INTO Account values (7, \'Primary Checking\', \'Alden106\', 3700, 6);");

		// 8. INSERT INTO Account values (8, "Primary Checking",
		// "Alden107", 3700, 7);
		    
		stat.
		    executeUpdate("INSERT INTO Account values (8, \'Primary Checking\', \'Alden107\', 3700, 7);");

		// 9. INSERT INTO Account values (9, "Primary Checking",
		// "Alden108", 3700, 8);
		
		stat.
		    executeUpdate("INSERT INTO Account values (9, \'Primary Checking\', \'Alden108\', 3700, 8);");

		// INSERT INTO Account values (10, "Primary Checking",
		// "Alden109", 3700, 9);

		stat.
		    executeUpdate("INSERT INTO Account values (10, \'Primary Checking\', \'Alden109\', 3700, 9);");

    }		

		catch(Exception e)
	    {

		e.printStackTrace();

	    }

    }

    public boolean removeAccountWrongOne(int id) throws SQLException
    {

	boolean completed = false;
	String removeAcct = "DELETE FROM Account where card_number = " + id;
	Statement removeStmt = m_connect.createStatement();
	int removeAccountResult = removeStmt.executeUpdate(removeAcct);
	if( removeAccountResult == 1 )
	    {
		completed = true;
	    }

	// check to make sure that the appropriate info was removed
	// from the account relation
	
	return completed;

    }

    public boolean removeAccountWrongTwo(int id) 
	throws SQLException
    {

	boolean completed = false;
	String removeAcct = "DELETE FROM Account where id = " + id;
	Statement removeStmt = m_connect.createStatement();
	int removeAccountResult = removeStmt.executeUpdate(removeAcct);
	
	// check to make sure that the appropriate info was removed
	// from the account relation
	String selectAccount = "SELECT * from Account where balance = "
	    + id;
	Statement selectStmt = m_connect.createStatement();
	ResultSet selectAccountResult = selectStmt.
	    executeQuery(selectAccount);

	if( removeAccountResult == 1 && !selectAccountResult.next() )
	    {
		
		completed = true;
		
	    }

	return completed;

    }

//     public boolean removeAccount(int id) throws SQLException
//     {

// 	return removeAccountWrongTwo(id);

//     }

    /** Example that is going to be used in the new proposal */
  public boolean removeAccount(int id) throws SQLException
  {

    boolean completed = false;

    // 1. a SELECT statement to determine the card_number of the 
    // account that we are going to remove
    String determineCardNumber = 
	"SELECT card_number FROM Account WHERE id = " + id;
    Statement determineStmt = databaseConnection.createStatement();
    ResultSet determineResultSet = determineStmt.
      executeQuery(determineCardNumber);

    int currentCardNumber = 0;

    // 2. extract the card_number of the account that is being removed
    while( determineResultSet.next() && currentCardNumber == 0 )
    {

      currentCardNumber = determineResultSet.getInt("card_number");

    }

    System.out.println("*** currentCardNumber " + currentCardNumber);

    // 3. actually remove the account that was specific by parameter
    String removeAccount = "DELETE FROM Account where id = " + id; 
    Statement removeStmt = databaseConnection.createStatement();
    int removeAccountResult = removeStmt.executeUpdate(removeAccount);

    // 4. determine if the user should be removed as well (only in the 
    // circumstance when there are no more accounts with the current
    // card number)
    String otherAccountsExist = "SELECT id FROM Account where card_number = " +
      currentCardNumber;
    Statement otherAccountStmt = databaseConnection.createStatement();
    ResultSet otherAccountResultSet = otherAccountStmt.
      executeQuery(otherAccountsExist);
    
    // assume that no removal is needed and prove otherwise; if
    // removal is needed, then the removal must be completed
    boolean removeUserCompleted = false;
    boolean noRemovalNeeded = true;

    // there are no other accounts for this card number and thus we 
    // must remove this user from the entire bank
    if( !otherAccountResultSet.next() )
    {

	System.out.println("About to remove user with cCN : " +
			   currentCardNumber);
      
      // 5. user should be removed, remove the user from the UserInfo table 
      noRemovalNeeded = false;
      removeUserCompleted = removeUser(currentCardNumber);

      System.out.println("removeUserCompleted = " +
			 removeUserCompleted);

    }

    // a single row was deleted from the database, as expected (this is
    // an auto-number field and thus we can only have one acocunt with
    // this specific ID)
    if( removeAccountResult == 1 && (noRemovalNeeded || 
				     removeUserCompleted ) )
    {

      completed = true;

    }

    return completed;

  }
 
  /** The removeUser method that is needed for our new proposal example */
  public boolean removeUser(int cardNumber) throws SQLException
  {

      System.out.println("removeUser(" + cardNumber + ")");

    boolean completed = false;

    String removeUser = "DELETE FROM UserInfo where card_number = " +
      cardNumber;
    Statement removeUserStmt = databaseConnection.createStatement();
    int removeUserResult = removeUserStmt.executeUpdate(removeUser);

    if( removeUserResult == 1 )
    {

      completed = true;

    }

    return completed;

  }

  /**
     This method determines if the information that is stored on an ATM "card" 
     is actually valid.  This is accomplished by performing a SQL query that 
     retrieves the information from the UserInfo table in the specific 
     database.  This information is then traversed to determine if the desired 
     card information actually exists in the Bank's database.
   
     @author Gregory M. Kapfhammer 4/12/2000
  */
  public boolean verifyCard(String user_name, int card_num, int pin_num) 
    throws SQLException
  {

    /** Flag that is used to indicate whether or not the given parameters 
     * are actually in the Bank's database and considered "valid."  Assume that
     * the information is not located in the database and prove otherwise
     */
    boolean verified = false;
		
    // create a select string and then execute it against the loaded DB 
    String qs = "SELECT user_name, card_number, pin_number FROM UserInfo";
    Statement stmt = databaseConnection.createStatement();

    ResultSet rs = stmt.executeQuery(qs);	
    
    // loop through the result set and check to see if the correct information 
    // is actually stored in the database
    while( rs.next() )
    {
			
      // we found the correct information in the database and the 
      // card should be considered to be "verified"
      if( user_name.equals( rs.getString("user_name") ) && 
	  card_num == rs.getInt("card_number") && 
	  pin_num == rs.getInt("pin_number") )
      {
	
	verified = true;	
	
      }
			
    }
		
    // return the status of the verification now that we have examined
    // the entire result set of the query
    return verified;
    
  }

  /**
     This method determines if the account for a specific card actually exists.
     It returns the Account ID if the account actually exists or returns 0.
     This method is first used by the client when it wants to use other 
     methods that require a specific account unique ID.  
     
     @author Gregory M. Kapfhammer 4/12/2000
  */
  public int accountExists(String account_type, String rank, 
			       int card_number) 
    throws SQLException
  {
    
    /** Variable that will return the unique account ID if the account 
	does exist and otherwise it will return 0 if the account not found */
    int exists = FALSE;
		
    String account_name = rank + " " + account_type;
		
    // create a select string and then execute it against the loaded DB 
    String qs = "SELECT account_name, card_number, id FROM Account";
    Statement stmt = databaseConnection.createStatement();
    
    ResultSet rs = stmt.executeQuery(qs);	
		
    // loop through the result set and see if we can find the account 
    while( rs.next() )
    {
			
      // we found the account in the result set and we can assign a value 
      if( account_name.equals( rs.getString("account_name") ) && 
	  card_number == rs.getInt("card_number") )
      {
	
	exists = rs.getInt("id");	
	
      }
      
    }
    
    // return the status of the existence now that we have traversed resultset
    return exists;
		
  }
  
  /**
     This method returns the balance for a specifc account given the unique ID 
     for that account.  The unique ID can only be obtained through the usage 
     of accountExists().  Method returns -1 if the account unique ID cannot be 
     found in the result set.
   
     @author Gregory M. Kapfhammer 4/12/2000
  */
  public double getAccountBalance(int uid) throws SQLException
  {
    
    /** Variable that is used to store the balance of the account found */
    double balance = -1.0;
		
    // create a select string and then execute it against the loaded DB 
    String qs = "SELECT ID, Balance FROM Account;";
    Statement stmt = databaseConnection.createStatement();
    
    ResultSet rs = stmt.executeQuery(qs);	
    
    // loop through the result set and check to see if we found the right 
    // unique ID and then return that balance appropriately
    while( rs.next() )
    {
			
      // we found the appropriate account and can assign the balance
      if( rs.getInt("id") == uid )
      {
	
	balance = rs.getDouble("balance");	
				
      }
			
    }
		
    // return the current value of balance since we have finished traversing 
    return balance;
		
  }
  
  /**
     This method performs a withdraw on account.  It contains the conditional 
     logic that is neccessary to ensure that an invalid withdraw does not take 
     place. 
     
     @author Gregory M. Kapfhammer 4/12/2000
  */
  public boolean accountWithdraw(int uid, double amount) 
    throws SQLException
  {
		
    /** Variable that indicates whether we were able to finish the withdraw */
    boolean completed = false; 
    
    // create a select string and then execute it against the loaded DB 
    String qs = "SELECT id, balance FROM Account;";
    Statement stmt = databaseConnection.createStatement();
    
    ResultSet rs = stmt.executeQuery(qs);		
    
    // loop through the result set to find the appropriate account
    while( rs.next() )
    {
      
      int id = rs.getInt("id");
      double balance = rs.getDouble("balance");
      
      //System.out.println("ID: " + id + " Balance: " + balance); 
			
      // we found the appropriate account and amt does not exceed the balance
      if( id == uid && amount <= balance )
      {
	
	String qu = "UPDATE Account SET balance = balance-" + 
	  amount + " WHERE id = " + uid + ";";
			
	Statement update = databaseConnection.createStatement();
	
	int result = update.executeUpdate(qu);

	// we succesfully updated one row inside the database
	if( result == 1 )
	{
	   
	  completed = true;	
	  
	}
	
      }
      
    }

    return completed;
		
  }

  /**
     This method performs a deposit upon an account based upon a given 
     account unique ID and an ammount for deposit
   
     @author Gregory M. Kapfhammer 4/12/2000
  */
  public boolean accountDeposit(int uid, double amount) 
    throws SQLException
  {
	
    /** Variable that indicates whether we were able to complete withdraw */
    boolean completed = false; 
		
    String qu = "UPDATE Account SET balance = balance+" + 
      amount + " WHERE id = " + uid + ";";
	
    Statement update = databaseConnection.createStatement();

    int result = update.executeUpdate(qu);
		
    // we updated the one row that we were attempting to update
    // and the deposit is completed
    if( result == 1 )
    {
      
      completed = true;	
	
    }
    
    return completed;
    
  }

  /**
     This method performs a transfer between two accounts.  Checks are made to 
     ensure that the source account actually has enough money for the transfer 
     to succeed.
   
     @author Gregory M. Kapfhammer 7/7/99
  */
  public boolean accountTransfer(int source_uid, int dest_uid, double amount) 
    throws SQLException
  {

    /** Flag to indicate whether or not the transa was valid and completed */
    boolean completed = false;
		
    // create a select string and then execute it against the loaded DB 
    String qs = "SELECT id, balance FROM Account;";
    Statement stmt = databaseConnection.createStatement();
    
    ResultSet rs = stmt.executeQuery(qs);		
    
    // loop through the result set to find the appropriate account
    while( rs.next() )
    {
      
      int id = rs.getInt("id");
      double balance = rs.getDouble("balance");

      // we found the appropriate source account and the amount 
      // does not exceed the balance
      if( id == source_uid && amount <= balance )
      {
				
	// perform update for the withdraw
	String qu_withdraw = "UPDATE Account SET balance = balance-" + 
	  amount + " WHERE id = " + source_uid + ";";
	Statement update_withdraw = databaseConnection.createStatement();
	
	int result_withdraw = update_withdraw.executeUpdate(qu_withdraw);
		
	// perform update for the deposit
	String qu_deposit = "UPDATE Account SET balance = balance+" + 
	  amount + " WHERE id = " + dest_uid + ";";
				
	Statement update_deposit = databaseConnection.createStatement();
	
	int result_deposit = update_deposit.executeUpdate(qu_deposit);
					
	// we sucessfully update one row in the database
	if( result_withdraw == 1 && result_deposit == 1)
	  completed = true;
			
      }
      
    }
  
    return completed;
		
  }

  /**
     This method locks a given account inside the database.  This method
     will normally be used after a user has taken the ATM user interface
     through five iterations of inserting their card and still done things
     incorrectly.
     
     @author Gregory M. Kapfhammer 4/16/2000
  */
  public int lockAccount(int card_number) throws SQLException
  {

    /** Flag to indicate that the lockAccount operation is completed */
    int completed = FALSE;
		
    String qu_lock = 
      "UPDATE UserInfo SET acct_lock = 1 WHERE card_number = " + 
      card_number + ";";
    Statement update_lock = databaseConnection.createStatement();
    
    int result_lock = update_lock.executeUpdate(qu_lock);
    
    // we were able to update the one row in the database successfully
    if( result_lock == 1)
      completed = TRUE;
		
    return completed;
    
  }

  
  /**
     This method returns a boolean that indicates whether or not a specific 
     card has been locked from the user.  Note that this lock exists for all 
     of the accounts that a card could be used for.
   
     @author Gregory M. Kapfhammer 7/7/99
  */
  public int isLocked(int card_number) throws SQLException
  {

  //   /** Variable to indicate whether or not the account is locked */	
    int is_locked = FALSE;
		
    // create a select string and then execute it against the loaded DB
    String qs_lock = 
      "SELECT card_number, acct_lock FROM UserInfo;";
    Statement stmt_lock = databaseConnection.createStatement();

    ResultSet rs_lock = stmt_lock.executeQuery(qs_lock);

    // loop through the result set to determine whether or not the given
    // card number is actually locked
    while( rs_lock.next() )
    {

      // we found the appropriate card and we can determine if it is locked
      if( rs_lock.getInt("card_number") == card_number )
      {

	is_locked = rs_lock.getInt("acct_lock");	

      }
			
    }
  
    // return the result of our querying
    return is_locked;
    
  }

  /**
   *  This method is responsible for deleting a user account from the 
   *  UserInfo table.  This is the correctly implemented version of this 
   *  functionality.  We have also produced an incorrect implementatation 
   *  to illustrate the fact that a MySQL database does not test for foreign
   *  key constraint violations and as such we would need to test for these
   *  types of problems.  Note that all of the other methods inside of this
   *  system happen to use 'int' as the return value because they were 
   *  previously incorporated into a system that uses CORBA.  Now, we are back
   *  to using a boolean return value.  This code will only be called from
   *  a main() method in this class and not really be integrated into the 
   *  overall GUI.
   *  
   *  @author Gregory M. Kapfhammer 6/10/2001
   */
  public boolean deleteAccount(int card_number) throws SQLException
  {

    // assume that interactions with the database do not work correctly
    // and attempt to prove otherwise
    boolean completed = false;
    
    // we need to delete from both the UserInfo and the Account tables; 
    // this will ensure that we handle the foreign key constraints correctly
    String delete_account_ui = "DELETE FROM UserInfo WHERE card_number = " +
      card_number + ";";

    String delete_account_account = 
      "DELETE FROM Account WHERE card_number = " + card_number + ";";

    Statement delete = databaseConnection.createStatement();

    int result_delete_ui = delete.executeUpdate(delete_account_ui);
    
    int result_delete_acct = delete.executeUpdate(delete_account_account);
    
    // this is the conditional logic for the non-broken version
    if( result_delete_ui == 1 && result_delete_acct == 1)
      //if( result_delete_ui == 1 )
    {

      completed = true;

    }

    return completed;

  }

  /*
  public boolean deleteAccountAgain(int card_number, String acct_name) 
    throws SQLException
  {

    // assume that interactions with the database do not work correctly
    // and attempt to prove otherwise
    boolean completed = false;
    
    // we need to delete from both the UserInfo and the Account tables; 
    // this will ensure that we handle the foreign key constraints correctly
    String delete_account_ui = "DELETE from UserInfo WHERE card_number = " +
      card_number + ";";

    String delete_account_account = 
      "DELETE from Account WHERE card_number = " + card_number + ";";

    Statement delete = m_connect.createStatement();
    int result_delete_ui = delete.executeUpdate(delete_account_ui);
    
    // if we do not execute this line, then we will violate the foreign
    // key constraints that are implicitly placed upon the database
    int result_delete_acct = delete.executeUpdate(delete_account_account);
    
    // this is the conditional logic for the non-broken version
    //if( result_delete_ui == 1 && result_delete_acct == 1)
    if( result_delete_ui == 1 )
    {

      completed = true;

    }

    return completed;
    
  }
  */
  
  /*
  public static void main(String[] args)
  {

    try
    {

      MySQLDatabaseAgent test_agent = new MySQLDatabaseAgent("bankDB");
      //test_agent.deleteAccount(2);
      test_agent.deleteAccountAgain(3, "Primary Checking");

    }

    catch(SQLException e)
    {

      System.out.println("Problem with DB Interaction");

    }

    catch(ClassNotFoundException e)
    {

      System.out.println("Problem connecting to the database");

    }

  }
  */

    public static void main(String[] args)
    {
	
	System.out.println("CREATE TABLE UserInfo ( card_number int not null identity, pin_number int not null, user_name varchar(50) not null, acct_lock int );");	
	
	System.out.println("CREATE TABLE Account ( id int identity, account_name varchar(50) not null, user_name varchar(50) not null, balance int default '0', card_number int, FOREIGN KEY(card_number) REFERENCES UserInfo(card_number) );");

    }

}


