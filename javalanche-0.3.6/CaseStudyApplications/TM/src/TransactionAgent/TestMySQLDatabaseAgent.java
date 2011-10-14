/*---------------------------------------------------------------------
 *  File: $Id: TestFindFile.java,v 1.3 2005/03/09 21:12:38 gkapfham Exp $   
 *  Version:  $Revision: 1.3 $
 *
 *  Project: DIATOMS, Database drIven Application Testing tOol ModuleS
 *
 *--------------------------------------------------------------------*/

package TransactionAgent;

import org.dbunit.Assertion;
import org.dbunit.DatabaseTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.dataset.SortedTable;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;

import java.io.FileInputStream;

/**
 *  Test Suite just starts up the database server.  Also test to ensure
 *  that the functionality of the FindFile program is correct.
 *
 *  @author Gregory M. Kapfhammer 2/22/2005
 */

public class TestMySQLDatabaseAgent extends DatabaseTestCase
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
	"jdbc:hsqldb:hsql://localhost/TransactionManagerDBTestOnly";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";
    
    MySQLDatabaseAgent transaction;

    /**
     *  Required default constructor.
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    public TestMySQLDatabaseAgent(String name)
    {

	super(name);

	transaction = new MySQLDatabaseAgent();

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
			
			MySQLDatabaseAgent.
			createDatabaseServer("localhost",
					   "TransactionManagerDBTestOnly",
					   "db/TransactionManagerDBTestOnly");
		
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
	
	transaction = new MySQLDatabaseAgent(testDatabaseDescription);

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
     *  Completely clears out the state of the database as part of the
     *  setUp code.  This seems to ensure that there is not state when
     *  we are ready to perform an INSERT in the tests.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2004
     */
    protected DatabaseOperation getSetUpOperation()
	throws Exception
    {

	return DatabaseOperation.DELETE_ALL;

    }

    /**
     *  Completely clears out the state of the database as part of the
     *  tearDown code.  This seems to ensure that there is not state when
     *  we are ready to perform an INSERT in the tests.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2004
     */
    protected DatabaseOperation getTearDownOperation()
	throws Exception
    {

	return DatabaseOperation.DELETE_ALL;

    }

    /*
     *  The database server should be running since it was started
     *  one time inside of the setUp code.
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    public void testDatabaseServerIsRunning()
    {

	assertTrue(MySQLDatabaseAgent.databaseServerIsRunning());

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

	assertTrue(!MySQLDatabaseAgent.
		   createDatabaseServer("localhost",
					"TransactionManagerDBTestOnly",
					"db/TransactionManagerDBTestOnly"));

	assertTrue(!MySQLDatabaseAgent.
		   createDatabaseServer("localhost",
					"TransactionManagerDBTestOnly",
					"db/TransactionManagerDBTestOnly"));

	assertTrue(!MySQLDatabaseAgent.
		   createDatabaseServer("localhost",
					"TransactionManagerDBTestOnly",
					"db/TransactionManagerDBTestOnly"));
	
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

	try
	    {
		testConnection = 
		    MySQLDatabaseAgent.
		    createDatabaseConnection(testDatabaseDescription);

		assertTrue(testConnection != null);

		// close the connection to the database
		MySQLDatabaseAgent.closeConnection();

	    }

	catch(Exception e)
	    {

		fail("Connecting to database caused exception");

	    }

    }

    public void testBasicTestFramework()
    {

	try
	    {


		// note that you have to reset the state since
		// these test cases are not independent

		transaction.createDefaultBankState();

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("Test Case should not throw an exception");
	
	    }

    }

    public void testMakeDatabaseConnectionRepeatedly10()
    {

	for(int i = 0; i < 10; i++)
	    {

		try
		    {
			testConnection = 
			    MySQLDatabaseAgent.
			    createDatabaseConnection(testDatabaseDescription);

			assertTrue(testConnection != null);

			// close the connection to the database
			MySQLDatabaseAgent.closeConnection();

		    }

		catch(Exception e)
		    {

			fail("Connecting to database caused exception");

		    }

	    }

    }    

    public void testCreateDefaultBankState()
    {

	try
	    {

		transaction.createDefaultBankState();

	    }

	catch(Exception e)
	    {

		fail("Should be able to generate state");

	    }

    }    

    public void testRemoveAccountNotPossibleInitialState()
    {

	try
	    {

		// the state carries over!

		boolean removeAccountDidOccur = transaction.removeAccount(1);
		assertEquals(true, removeAccountDidOccur);

		boolean removeAccountDidOccurReallyNotThere = 
		    transaction.removeAccount(1000);
		assertEquals(false, removeAccountDidOccurReallyNotThere);

		removeAccountDidOccurReallyNotThere = 
		    transaction.removeAccount(100000);
		assertEquals(false, removeAccountDidOccurReallyNotThere);

		removeAccountDidOccurReallyNotThere = 
		    transaction.removeAccount(10000000);
		assertEquals(false, removeAccountDidOccurReallyNotThere);
	
	    }

	catch(SQLException e)
	    {

		fail("Exception thrown");

	    }

    }

    public void testRemoveAccountTwiceDoesNotWorkCorrectly()
    {

	try
	    {

		// note that you have to reset the state since
		// these test cases are not independent

		transaction.createDefaultBankState();

		// the state carries over!

		boolean removeAccountDidOccur = transaction.removeAccount(1);
		assertEquals(true, removeAccountDidOccur);

		removeAccountDidOccur = transaction.removeAccount(1);
		assertEquals(false, removeAccountDidOccur);

		// keep these in again		

		boolean removeAccountDidOccurReallyNotThere = 
		    transaction.removeAccount(1000);
		assertEquals(false, removeAccountDidOccurReallyNotThere);

		removeAccountDidOccurReallyNotThere = 
		    transaction.removeAccount(100000);
		assertEquals(false, removeAccountDidOccurReallyNotThere);

		removeAccountDidOccurReallyNotThere = 
		    transaction.removeAccount(10000000);
		assertEquals(false, removeAccountDidOccurReallyNotThere);
	
	    }

	catch(SQLException e)
	    {

		fail("Exception thrown");

	    }

    }

    public void testRemoveDifferentAccountNumberTwice()
    {

	try
	    {


		// note that you have to reset the state since
		// these test cases are not independent

		transaction.createDefaultBankState();

		// the state carries over!

		boolean removeAccountDidOccur = transaction.removeAccount(5);
		assertEquals(true, removeAccountDidOccur);

		removeAccountDidOccur = transaction.removeAccount(5);
		assertEquals(false, removeAccountDidOccur);

		removeAccountDidOccur = transaction.removeAccount(1);
		assertEquals(true, removeAccountDidOccur);	

	    }

	catch(Exception e)
	    {

		fail("Test Case should not throw an exception");

	    }

    }

    public void testRemoveASingleUser()
    {

	try
	    {


		// note that you have to reset the state since
		// these test cases are not independent

		transaction.createDefaultBankState();

		boolean userRemoved = transaction.removeUser(1);
		assertEquals(true, userRemoved);

		fail("Test should cause an integrity constr viol");

	    }

	catch(Exception e)
	    {

		// nothing needed

	    }

    }

    public void testVerifySingleUser()
    {

	try
	    {


		// note that you have to reset the state since
		// these test cases are not independent

		transaction.createDefaultBankState();

		boolean cardVerified = 
		    transaction.verifyCard("Alden100", 1, 12345);
		assertEquals(true, cardVerified);

		cardVerified = 
		    transaction.verifyCard("Alden100", 5, 12345);
		assertEquals(false, cardVerified);

		cardVerified = 
		    transaction.verifyCard("Alden100", 1, 12345678);
		assertEquals(false, cardVerified);		

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("Test Case should not throw an exception");
	
	    }

    }

    public void testVerifySingleUserRepeatedlyTenTimes()
    {

	try
	    {

		for(int i = 0; i < 10; i++)
		    {


			// note that you have to reset the state since
			// these test cases are not independent

			transaction.createDefaultBankState();

			boolean cardVerified = 
			    transaction.verifyCard("Alden100", 1, 12345);
			assertEquals(true, cardVerified);

			cardVerified = 
			    transaction.verifyCard("Alden100", 5, 12345);
			assertEquals(false, cardVerified);

			cardVerified = 
			    transaction.verifyCard("Alden100", 1, 12345678);
			assertEquals(false, cardVerified);

		    }		

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("Test Case should not throw an exception");
	
	    }

    }

   public void testAccountExistsSingleAccount()
    {

	try
	    {

		// note that you have to reset the state since
		// these test cases are not independent

		transaction.createDefaultBankState();

		/**

  public int accountExists(String account_type, String rank, 
			       int card_number) 		

		*/

		int foundAccount = transaction.
		    accountExists("Checking", "Primary", 1);

		assertEquals(1, foundAccount);

		foundAccount = transaction.
		    accountExists("Checking", "Primary", 2);

		assertEquals(2, foundAccount);

		// go again with invalid data

		foundAccount = transaction.
		    accountExists("Checking", "PrimaryFred", 1);
		assertEquals(0, foundAccount);

		foundAccount = transaction.
		    accountExists("CheckingFred", "Primary", 1);
		assertEquals(0, foundAccount);

		foundAccount = transaction.
		    accountExists("Checking", "Primary", 1000);
		assertEquals(0, foundAccount);

		foundAccount = transaction.
		    accountExists("Checking", "PrimaryFred", 2);

		assertEquals(0, foundAccount);		

		foundAccount = transaction.
		    accountExists("CheckingFred", "Primary", 2);

		assertEquals(0, foundAccount);		

		foundAccount = transaction.
		    accountExists("Checking", "Primary", 200000);

		assertEquals(0, foundAccount);		

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("Test Case should not throw an exception");
	
	    }

    }    

   public void testAccountExistsSingleAccountRepeatedly()
    {

	try
	    {

		for(int i = 0; i < 10; i++)
		    {

			// note that you have to reset the state since
			// these test cases are not independent

			transaction.createDefaultBankState();

			/**
			   
			public int accountExists(String account_type,
			String rank, int card_number)

			*/

			int foundAccount = transaction.
			    accountExists("Checking", "Primary", 1);

			assertEquals(1, foundAccount);

			foundAccount = transaction.
			    accountExists("Checking", "Primary", 2);

			assertEquals(2, foundAccount);

			// go again with invalid data
			
			foundAccount = transaction.
			    accountExists("Checking", "PrimaryFred", 1);
			assertEquals(0, foundAccount);

			foundAccount = transaction.
			    accountExists("CheckingFred", "Primary", 1);
			assertEquals(0, foundAccount);

			foundAccount = transaction.
			    accountExists("Checking", "Primary", 1000);
			assertEquals(0, foundAccount);

			foundAccount = transaction.
			    accountExists("Checking", "PrimaryFred", 2);

			assertEquals(0, foundAccount);		

			foundAccount = transaction.
			    accountExists("CheckingFred", "Primary", 2);

			assertEquals(0, foundAccount);		

			foundAccount = transaction.
			    accountExists("Checking", "Primary", 200000);

			assertEquals(0, foundAccount);		

		    }

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("Test Case should not throw an exception");
	
	    }

    }    

    public void testGetAccountBalance()
    {

	try
	    {


		// note that you have to reset the state since
		// these test cases are not independent

		transaction.createDefaultBankState();

		double accountBalance = transaction.getAccountBalance(1);
		assertEquals(1800, accountBalance, 0.0);

		accountBalance = transaction.getAccountBalance(45);
		assertEquals(-1.0, accountBalance, 0.0);

		accountBalance = transaction.getAccountBalance(450);
		assertEquals(-1.0, accountBalance, 0.0);

		accountBalance = transaction.getAccountBalance(450000);
		assertEquals(-1.0, accountBalance, 0.0);

		accountBalance = transaction.getAccountBalance(0);
		assertEquals(-1.0, accountBalance, 0.0);

		accountBalance = transaction.getAccountBalance(-1);
		assertEquals(-1.0, accountBalance, 0.0);

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("Test Case should not throw an exception");
	
	    }

    }

    public void testGetAccountBalanceRepeatedly()
    {

	try
	    {


		for(int i =0; i < 10; i++)
		    {

		// note that you have to reset the state since
		// these test cases are not independent

		transaction.createDefaultBankState();

		double accountBalance = transaction.getAccountBalance(1);
		assertEquals(1800, accountBalance, 0.0);

		accountBalance = transaction.getAccountBalance(45);
		assertEquals(-1.0, accountBalance, 0.0);

		accountBalance = transaction.getAccountBalance(450);
		assertEquals(-1.0, accountBalance, 0.0);

		accountBalance = transaction.getAccountBalance(450000);
		assertEquals(-1.0, accountBalance, 0.0);

		accountBalance = transaction.getAccountBalance(0);
		assertEquals(-1.0, accountBalance, 0.0);

		accountBalance = transaction.getAccountBalance(-1);
		assertEquals(-1.0, accountBalance, 0.0);

		    }

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("Test Case should not throw an exception");
	
	    }

    }

    public void testGetAccountBalanceAndThenWithdraw()
    {

	try
	    {


		// note that you have to reset the state since
		// these test cases are not independent

		transaction.createDefaultBankState();

		double accountBalance = transaction.getAccountBalance(1);
		assertEquals(1800, accountBalance, 0.0);

		boolean withdrawPerformed = 
		    transaction.accountWithdraw(1, 100.00);
		assertEquals(true, withdrawPerformed);

		accountBalance = transaction.getAccountBalance(1);
		assertEquals(1700, accountBalance, 0.0);

		// still test the incorrect gets		
		// test the incorrect withdraws

		accountBalance = transaction.getAccountBalance(45);
		assertEquals(-1.0, accountBalance, 0.0);

		withdrawPerformed = 
		    transaction.accountWithdraw(45, 100.00);
		assertEquals(false, withdrawPerformed);

		accountBalance = transaction.getAccountBalance(450);
		assertEquals(-1.0, accountBalance, 0.0);

		withdrawPerformed = 
		    transaction.accountWithdraw(450, 100.00);
		assertEquals(false, withdrawPerformed);

		accountBalance = transaction.getAccountBalance(450000);
		assertEquals(-1.0, accountBalance, 0.0);

		withdrawPerformed = 
		    transaction.accountWithdraw(450000, 100.00);
		assertEquals(false, withdrawPerformed);

		accountBalance = transaction.getAccountBalance(0);
		assertEquals(-1.0, accountBalance, 0.0);

		withdrawPerformed = 
		    transaction.accountWithdraw(0, 100.00);
		assertEquals(false, withdrawPerformed);

		accountBalance = transaction.getAccountBalance(-1);
		assertEquals(-1.0, accountBalance, 0.0);

		withdrawPerformed = 
		    transaction.accountWithdraw(-1, 100.00);
		assertEquals(false, withdrawPerformed);

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("Test Case should not throw an exception");
	
	    }

    }

    public void testGetAccountBalanceAndThenWithdrawRepeatedly()
    {

	try
	    {

		transaction.createDefaultBankState();

		for(int i = 1; i < 10; i++)
		    {

			// note that you have to reset the state since
			// these test cases are not independent

			boolean withdrawPerformed = 
			    transaction.accountWithdraw(1, 100.00);
			assertEquals(true, withdrawPerformed);

			double accountBalance = 
			    transaction.getAccountBalance(1);
			assertEquals(1800 - (100*i), accountBalance, 0.0);

			// still test the incorrect gets		
			// test the incorrect withdraws

			accountBalance = transaction.getAccountBalance(45);
			assertEquals(-1.0, accountBalance, 0.0);

			withdrawPerformed = 
			    transaction.accountWithdraw(45, 100.00);
			assertEquals(false, withdrawPerformed);

			accountBalance = transaction.getAccountBalance(450);
			assertEquals(-1.0, accountBalance, 0.0);

			withdrawPerformed = 
			    transaction.accountWithdraw(450, 100.00);
			assertEquals(false, withdrawPerformed);

			accountBalance = transaction.getAccountBalance(450000);
			assertEquals(-1.0, accountBalance, 0.0);

			withdrawPerformed = 
			    transaction.accountWithdraw(450000, 100.00);
			assertEquals(false, withdrawPerformed);

			accountBalance = transaction.getAccountBalance(0);
			assertEquals(-1.0, accountBalance, 0.0);

			withdrawPerformed = 
			    transaction.accountWithdraw(0, 100.00);
			assertEquals(false, withdrawPerformed);

			accountBalance = transaction.getAccountBalance(-1);
			assertEquals(-1.0, accountBalance, 0.0);

			withdrawPerformed = 
			    transaction.accountWithdraw(-1, 100.00);
			assertEquals(false, withdrawPerformed);

		    }

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("Test Case should not throw an exception");
	
	    }

    }

    public void testDepositForSingleUser()
    {

	try
	    {


		// note that you have to reset the state since
		// these test cases are not independent

		transaction.createDefaultBankState();

		double accountBalance = transaction.getAccountBalance(1);
		assertEquals(1800, accountBalance, 0.0);

		boolean depositPerformed = 
		    transaction.accountDeposit(1, 100.00);
		assertEquals(true, depositPerformed);

		accountBalance = transaction.getAccountBalance(1);
		assertEquals(1900, accountBalance, 0.0);

		depositPerformed = 
		    transaction.accountDeposit(1, 100.00);
		assertEquals(true, depositPerformed);

		accountBalance = transaction.getAccountBalance(1);
		assertEquals(2000, accountBalance, 0.0);


	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("Test Case should not throw an exception");
	
	    }

    }

    /**
  public boolean accountTransfer(int source_uid, int dest_uid, double amount) 
    throws SQLException
  {

    */

    
    // 1. INSERT INTO Account values (1, "Primary
    //  Checking", "Alden100", 1800, 1);

    // 2. INSERT INTO Account values (2, "Primary Checking",
    // "Alden101", 1800, 2);

    public void testAccountTransfer()
    {

	try
	    {


		// note that you have to reset the state since
		// these test cases are not independent

		transaction.createDefaultBankState();

		transaction.accountTransfer(1, 2, 100);

		assertEquals(1700, transaction.getAccountBalance(1), 0.0);
		assertEquals(1900, transaction.getAccountBalance(2), 0.0);
		
		
	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("Test Case should not throw an exception");
	
	    }

    }

    public void testAccountTransferMultipleTimes()
    {

	try
	    {

		transaction.createDefaultBankState();

		for(int i = 1; i < 10; i++)
		    {

			// note that you have to reset the state since
			// these test cases are not independent

			transaction.accountTransfer(1, 2, 100);

			assertEquals( (1800 - (i*100)) , transaction.
				     getAccountBalance(1), 0.0);
			assertEquals( (1800 + (i*100)), transaction.
				     getAccountBalance(2), 0.0);
			
		    }

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("Test Case should not throw an exception");
	
	    }

    }

    // 1. INSERT INTO Account values (1, "Primary
    //  Checking", "Alden100", 1800, 1);

    // 2. INSERT INTO Account values (2, "Primary Checking",
    // "Alden101", 1800, 2);

    public void testLockTwoAccounts()
    {

	try
	    {


		// note that you have to reset the state since
		// these test cases are not independent
		
		transaction.createDefaultBankState();

		transaction.lockAccount(1);
		assertEquals(1, transaction.isLocked(1));

		transaction.lockAccount(2);
		assertEquals(1, transaction.isLocked(2));

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("Test Case should not throw an exception");
	
	    }

    }

    public void testLockUnlockLockTwoAccountsRepeatedlySmall()
    {

	try
	    {

		for(int i = 0; i < 10; i++)
		    {


			// note that you have to reset the state since
			// these test cases are not independent
		
			transaction.createDefaultBankState();
			
			transaction.lockAccount(1);
			assertEquals(1, transaction.isLocked(1));
		
			transaction.lockAccount(2);
			assertEquals(1, transaction.isLocked(2));

		    }

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("Test Case should not throw an exception");
	
	    }

    }

    // 1. INSERT INTO Account values (1, "Primary
    //  Checking", "Alden100", 1800, 1);

    // 2. INSERT INTO Account values (2, "Primary Checking",
    // "Alden101", 1800, 2);

    public void testDeleteAccounts()
    {

	try
	    {


		// note that you have to reset the state since
		// these test cases are not independent
		
		transaction.createDefaultBankState();

		boolean deleted = transaction.deleteAccount(1);
		//assertEquals(true, deleted);
		fail("should have violated integrity constraints");

	    }

	catch(Exception e)
	    {

		//e.printStackTrace();
	
	    }

    }

    // 1. INSERT INTO Account values (1, "Primary
    //  Checking", "Alden100", 1800, 1);

    // 2. INSERT INTO Account values (2, "Primary Checking",
    // "Alden101", 1800, 2);

    public void testDeleteAccountsRepeatedSmall()
    {

	for( int i = 0; i < 10; i++)
	    {

		try
		    {


			// note that you have to reset the state since
			// these test cases are not independent
		
			transaction.createDefaultBankState();

			boolean deleted = transaction.deleteAccount(1);
			//assertEquals(true, deleted);
			fail("should have violated integrity constraints");

		    }

		catch(Exception e)
		    {

			//e.printStackTrace();
	
		    }

	    }

    }

    public void testDeleteAccountsRepeatedMedium()
    {

	for( int i = 0; i < 50; i++)
	    {

		try
		    {


			// note that you have to reset the state since
			// these test cases are not independent
		
			transaction.createDefaultBankState();

			boolean deleted = transaction.deleteAccount(1);
			//assertEquals(true, deleted);
			fail("should have violated integrity constraints");

		    }

		catch(Exception e)
		    {

			//e.printStackTrace();
	
		    }

	    }

    }

    public void testDeleteAccountsRepeatedLarge()
    {

	for( int i = 0; i < 100; i++)
	    {

		try
		    {


			// note that you have to reset the state since
			// these test cases are not independent
		
			transaction.createDefaultBankState();

			boolean deleted = transaction.deleteAccount(1);
			//assertEquals(true, deleted);
			fail("should have violated integrity constraints");

		    }

		catch(Exception e)
		    {

			//e.printStackTrace();
	
		    }

	    }

    }

}
