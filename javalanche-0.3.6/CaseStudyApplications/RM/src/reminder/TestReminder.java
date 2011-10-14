/*---------------------------------------------------------------------
 *  File: $Id: TestReminder.java,v 1.6 2005/07/09 23:09:36 gkapfham Exp gkapfham $   
 *  Version:  $Revision: 1.6 $
 *
 *  Project: DIATOMS, Database drIven Application Testing tOol ModuleS
 *
 *--------------------------------------------------------------------*/

package reminder;

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
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *  Test Suite for the Reminder class.
 *
 *  @author Gregory M. Kapfhammer 2/22/2005
 */

public class TestReminder extends DatabaseTestCase
{

    /** This is the connection that will be passed to the other
	GradeBookCreator methods when we are testing them */
    private Connection testConnection;

    /** This is the DBUnit DatabaseConnection */
    private DatabaseConnection testDatabaseConnection;

    /** This describes the database that we will use during testing */
    private DatabaseDescription testDatabaseDescription;
    
    /** Flag to indicate whether the database server exists */
    private static boolean tablesExist = false;

    /** Constants required during testing */
    private static final String URL = 
	"jdbc:hsqldb:hsql://localhost/ReminderDBTestOnly";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    /** The gradebook that we will use to perform all of the testing */
    private Reminder testReminder;	

    /**
     *  Required default constructor.
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    public TestReminder(String name)
    {

	super(name);

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

	// create a new gradebook that uses this database description;
	// this will ensure that it can connect to the same database
	// as the test cases connect to
	testReminder = new Reminder(testDatabaseDescription);

	if( !tablesExist )
	    {

		// create each of the tables that will be populated during
		// testing inside of this test suite
		ReminderCreator.
		    createReminderTable(testDatabaseDescription);

		tablesExist = true;

	    }
	
	// Load the HSQL Database Engine JDBC driver
	Class.forName("org.hsqldb.jdbcDriver");

	testConnection =
	    DriverManager.
	    getConnection(testDatabaseDescription.getUrl(), 
			  testDatabaseDescription.getUserName(), 
			  testDatabaseDescription.getPassword());

	// create a DatabaseConnection class (this is part of the 
	// DBUnit framework) that wraps a java.SQL.Connection and
	// then return it to the other parts of DBUnits / tests
	testDatabaseConnection = new DatabaseConnection(testConnection);
	return testDatabaseConnection;

    }

    /**
     *  Retrieve the data set from a local XML file.
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    protected IDataSet getDataSet() throws Exception
    {

	return 
	    new FlatXmlDataSet(new FileInputStream("data/FullDataSet.xml"));

    }

    /**
     *  Remove the tables that have been created.  I don't think that this 
     *  is really necessary, but the test cases are not passing 
     *  otherwise, even though I am telling DBUnit to DELETE_ALL --
     *  that command must mean to **just** remove the data.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2005
     */
    protected void tearDown()
    {

	try
	    {

		// drop the tables that were created for the test case
		ReminderCreator.dropReminderTable(testDatabaseDescription);

		// signal that none of the tables are now inside
		// of the database
		tablesExist = false;

	    }

	catch(SQLException e)
	    {

		fail("Could not drop table in tearDown");

	    }

    }

    public void testGetEmptyEvents()
    {

	for(int i = 0; i < 10; i++)
	    {

		String expectedEvents = "";
		String actualEvents = testReminder.getAllEvents();
		assertEquals(expectedEvents, actualEvents);

	    }

    }

    /**
     *  Add in one reminder.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2005
     */
    public void testAddReminderOneReminder()
    {

	/**
	       public void addReminder(String name, int month, int day,
			    int monthTrigger, int dayTrigger)

	*/

	try
	    {

		testReminder.addEvent("Steve Clulow Birthday",
					 9, 14, 1953, 1, 7);

		// create the expected Reminder Table from the XML
		IDataSet expectedDatabaseState = 
		    new 
		    FlatXmlDataSet(new 
				   FileInputStream("data/OneRecDataSet.xml"));
		ITable expectedReminderTable = 
		    expectedDatabaseState.
		    getTable(ReminderConstants.REMINDER);

		// create the actual Master Table from the database
		IDataSet actualDatabaseState = 
		    getConnection().createDataSet();
		ITable actualReminderTable = 
		    actualDatabaseState.
		    getTable(ReminderConstants.REMINDER);
		
		// expected should be equal to actual in terms 
		// of both the number of rows and the data
		Assertion.assertEquals(expectedReminderTable,
				       actualReminderTable);
		
	    }

	catch(ReminderDataException e)
	    {

		e.printStackTrace();
		fail("Should not have problems adding reminder");

	    }

	catch(SQLException e)
	    {

		e.printStackTrace();
		fail("Should not have problems interacting with DB");

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("Some other problem with the testing");

	    }

    }    

    /**
     *  Add in one reminder.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2005
     */
    public void testAddReminderOneReminderNewNamesAndDate()
    {

	/**
	       public void addReminder(String name, int month, int day,
			    int monthTrigger, int dayTrigger)

	*/

	try
	    {

		testReminder.addEvent("Gregory M. Kapfhammer Birthday",
					 11, 21, 1976, 1, 7);

		// create the expected Reminder Table from the XML
		IDataSet expectedDatabaseState = 
		    new 
		    FlatXmlDataSet(new 
				   FileInputStream("data/OneRecDataSetGMK.xml"));
		ITable expectedReminderTable = 
		    expectedDatabaseState.
		    getTable(ReminderConstants.REMINDER);

		// create the actual Master Table from the database
		IDataSet actualDatabaseState = 
		    getConnection().createDataSet();
		ITable actualReminderTable = 
		    actualDatabaseState.
		    getTable(ReminderConstants.REMINDER);
		
		// expected should be equal to actual in terms 
		// of both the number of rows and the data
		Assertion.assertEquals(expectedReminderTable,
				       actualReminderTable);
		
	    }

	catch(ReminderDataException e)
	    {

		e.printStackTrace();
		fail("Should not have problems adding reminder");

	    }

	catch(SQLException e)
	    {

		e.printStackTrace();
		fail("Should not have problems interacting with DB");

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("Some other problem with the testing");

	    }

    }    

    /**
     *  Add in multiple reminders.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2005
     */
    public void testAddReminderMultipleReminders()
    {

	/**
	       public void addReminder(String name, int month, int day,
			    int monthTrigger, int dayTrigger)

	*/

	try
	    {

		testReminder.addEvent("Steve Clulow Birthday",
					 9, 14, 1953, 1, 7);

		testReminder.addEvent("Deana Clulow Birthday",
					 8, 20, 1954, 1, 7);

		testReminder.addEvent("Stevie Clulow Birthday",
					 6, 28, 1973, 1, 7);

		testReminder.addEvent("Jenny Rogan Birthday",
					 10, 9, 1974, 1, 7);

		// create the expected Reminder Table from the XML
		IDataSet expectedDatabaseState = 
		    new 
		    FlatXmlDataSet(new 
				   FileInputStream("data/MultipleRecDataSet.xml"));
		ITable expectedReminderTable = 
		    expectedDatabaseState.
		    getTable(ReminderConstants.REMINDER);

		// create the actual Master Table from the database
		IDataSet actualDatabaseState = 
		    getConnection().createDataSet();
		ITable actualReminderTable = 
		    actualDatabaseState.
		    getTable(ReminderConstants.REMINDER);
		
		// expected should be equal to actual in terms 
		// of both the number of rows and the data
		Assertion.assertEquals(expectedReminderTable,
				       actualReminderTable);
		
	    }

	catch(ReminderDataException e)
	    {

		e.printStackTrace();
		fail("Should not have problems adding reminder");

	    }

	catch(SQLException e)
	    {

		e.printStackTrace();
		fail("Should not have problems interacting with DB");

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("Some other problem with the testing");

	    }

    }    

    /**
     *  Add in multiple reminders and determine which ones 
     *  are future critical events with different current dates.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2005
     */
    public void testAddReminderMultipleRemindersCriitcalMonths()
    {

	/**
	       public void addEvent(String name, int month, int day,
			    int monthTrigger, int dayTrigger)

	*/

	try
	    {

		testReminder.addEvent("Steve Clulow Birthday",
					 9, 14, 1953, 1, 7);

		testReminder.addEvent("Deana Clulow Birthday",
					 8, 20, 1954, 1, 7);

		testReminder.addEvent("Stevie Clulow Birthday",
					 6, 28, 1973, 1, 7);

		testReminder.addEvent("Jenny Rogan Birthday",
					 10, 9, 1974, 1, 7);

		// create the expected Reminder Table from the XML
		IDataSet expectedDatabaseState = 
		    new 
		    FlatXmlDataSet(new 
				   FileInputStream("data/MultipleRecDataSet.xml"));
		ITable expectedReminderTable = 
		    expectedDatabaseState.
		    getTable(ReminderConstants.REMINDER);

		// create the actual Master Table from the database
		IDataSet actualDatabaseState = 
		    getConnection().createDataSet();
		ITable actualReminderTable = 
		    actualDatabaseState.
		    getTable(ReminderConstants.REMINDER);
		
		// expected should be equal to actual in terms 
		// of both the number of rows and the data
		Assertion.assertEquals(expectedReminderTable,
				       actualReminderTable);
		
		/**     public ArrayList getFutureCriticalEvents(int
		 *     currentMonth)*/
		
		int currentMonth = 8;
		ArrayList currentMonthList = 
		    testReminder.getFutureCriticalEvents(currentMonth);

		assertEquals(1, currentMonthList.size());

		currentMonth = 7;
		currentMonthList = 
		    testReminder.getFutureCriticalEvents(currentMonth);

		assertEquals(1, currentMonthList.size());

		currentMonth = 5;
		currentMonthList = 
		    testReminder.getFutureCriticalEvents(currentMonth);

		assertEquals(1, currentMonthList.size());

	    }

	catch(ReminderDataException e)
	    {

		e.printStackTrace();
		fail("Should not have problems adding reminder");

	    }

	catch(SQLException e)
	    {

		e.printStackTrace();
		fail("Should not have problems interacting with DB");

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("Some other problem with the testing");

	    }

    }    

    /**
     *  Add in multiple reminders and determine which ones 
     *  are future critical events with different current dates.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2005
     */
    public void testAddReminderMultipleRemindersCriticalDays()
    {

	/**
	       public void addEvent(String name, int month, int day,
			    int monthTrigger, int dayTrigger)

	*/

	try
	    {

		testReminder.addEvent("Steve Clulow Birthday",
					 9, 14, 1953, 1, 7);

		testReminder.addEvent("Deana Clulow Birthday",
					 8, 20, 1954, 1, 7);

		testReminder.addEvent("Stevie Clulow Birthday",
					 6, 28, 1973, 1, 7);

		testReminder.addEvent("Jenny Rogan Birthday",
					 10, 9, 1974, 1, 7);

		// create the expected Reminder Table from the XML
		IDataSet expectedDatabaseState = 
		    new 
		    FlatXmlDataSet(new 
				   FileInputStream("data/MultipleRecDataSet.xml"));
		ITable expectedReminderTable = 
		    expectedDatabaseState.
		    getTable(ReminderConstants.REMINDER);

		// create the actual Master Table from the database
		IDataSet actualDatabaseState = 
		    getConnection().createDataSet();
		ITable actualReminderTable = 
		    actualDatabaseState.
		    getTable(ReminderConstants.REMINDER);
		
		// expected should be equal to actual in terms 
		// of both the number of rows and the data
		Assertion.assertEquals(expectedReminderTable,
				       actualReminderTable);
		
		/**     public ArrayList getCurrentCriticalEvents(int
					      currentMonth, int
					      currentDay) */
		
		// here we actually want the real month in 
		// which the birthday occurs

		int currentMonth = 9;
		int currentDay = 9;
		ArrayList currentMonthList = 
		    testReminder.getCurrentCriticalEvents(currentMonth,
							  currentDay);

		assertEquals(1, currentMonthList.size());

		currentMonth = 8;
		currentDay = 16;
		currentMonthList = 
		    testReminder.getCurrentCriticalEvents(currentMonth,
							  currentDay);

		assertEquals(1, currentMonthList.size());

		currentMonth = 6;
		currentDay = 22;
		currentMonthList = 
		    testReminder.getCurrentCriticalEvents(currentMonth,
							 currentDay);

		assertEquals(1, currentMonthList.size());

		// there should be no dates within this range

		currentMonth = 6;
		currentDay = 10;
		currentMonthList = 
		    testReminder.getCurrentCriticalEvents(currentMonth,
							 currentDay);

		assertEquals(0, currentMonthList.size());

	    }

	catch(ReminderDataException e)
	    {

		e.printStackTrace();
		fail("Should not have problems adding reminder");

	    }

	catch(SQLException e)
	    {

		e.printStackTrace();
		fail("Should not have problems interacting with DB");

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("Some other problem with the testing");

	    }

    }    


    /**
     *  Add in multiple reminders and determine which ones 
     *  are future critical events with different current dates.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2005
     */
    public void testAddReminderMultipleRemindersGetEventInfo()
    {

	/**
	       public void addEvent(String name, int month, int day,
			    int monthTrigger, int dayTrigger)

	*/

	try
	    {

		testReminder.addEvent("Steve Clulow Birthday",
					 9, 14, 1953, 1, 7);

		testReminder.addEvent("Deana Clulow Birthday",
					 8, 20, 1954, 1, 7);

		testReminder.addEvent("Stevie Clulow Birthday",
					 6, 28, 1973, 1, 7);

		testReminder.addEvent("Jenny Rogan Birthday",
					 10, 9, 1974, 1, 7);

		// create the expected Reminder Table from the XML
		IDataSet expectedDatabaseState = 
		    new 
		    FlatXmlDataSet(new 
				   FileInputStream("data/MultipleRecDataSet.xml"));
		ITable expectedReminderTable = 
		    expectedDatabaseState.
		    getTable(ReminderConstants.REMINDER);

		// create the actual Master Table from the database
		IDataSet actualDatabaseState = 
		    getConnection().createDataSet();
		ITable actualReminderTable = 
		    actualDatabaseState.
		    getTable(ReminderConstants.REMINDER);
		
		// expected should be equal to actual in terms 
		// of both the number of rows and the data
		Assertion.assertEquals(expectedReminderTable,
				       actualReminderTable);
		
		/**     public ArrayList getCurrentCriticalEvents(int
					      currentMonth, int
					      currentDay) */
		
		// here we actually want the real month in 
		// which the birthday occurs

		int currentMonth = 9;
		int currentDay = 9;
		ArrayList currentMonthList = 
		    testReminder.getCurrentCriticalEvents(currentMonth,
							  currentDay);

		Iterator currentMonthIterator = 
		    currentMonthList.iterator();
		
		// since there is only one item inside of the 
		// list, we will only produce one label

		String eventLabel = "Id: 0\n" + 
		    "Name: Steve Clulow Birthday\n" + 
		    "Month: 9\n" +
		    "Day: 14\n" + 
		    "Year: 1953\n" +
		    "M Trigger: 1\n" + 
		    "D Trigger: 7\n";

		while( currentMonthIterator.hasNext() )
		    {

			Integer currentEventIdI = 
			    (Integer)currentMonthIterator.next();

			int currentEventId = 
			    currentEventIdI.intValue();

			assertEquals(eventLabel,
				     testReminder.
				     getEvent(currentEventId));

		    }

	    }

	catch(ReminderDataException e)
	    {

		e.printStackTrace();
		fail("Should not have problems adding reminder");

	    }

	catch(SQLException e)
	    {

		e.printStackTrace();
		fail("Should not have problems interacting with DB");

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("Some other problem with the testing");

	    }

    }        

    /**
     *  Add in multiple reminders and determine if we can delete them.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2005
     */
    public void testAddReminderMultipleRemindersDeleteEvent()
    {

	/**     public void deleteEvent(int id) */

	try
	    {

		testReminder.addEvent("Steve Clulow Birthday",
					 9, 14, 1953, 1, 7);

		testReminder.addEvent("Deana Clulow Birthday",
					 8, 20, 1954, 1, 7);

		testReminder.addEvent("Stevie Clulow Birthday",
					 6, 28, 1973, 1, 7);

		testReminder.addEvent("Jenny Rogan Birthday",
					 10, 9, 1974, 1, 7);

		testReminder.deleteEvent(0);
		testReminder.deleteEvent(1);
		testReminder.deleteEvent(2);
		testReminder.deleteEvent(3);

		String emptyEvent = 
		    testReminder.getEvent(0);
		    
		assertEquals("", emptyEvent);

		assertEquals("", testReminder.getEvent(1));
		assertEquals("", testReminder.getEvent(2));
		assertEquals("", testReminder.getEvent(3));
		
		
	    }

	catch(ReminderDataException e)
	    {

		e.printStackTrace();
		fail("Should not have problems adding reminder");

	    }

	catch(SQLException e)
	    {

		e.printStackTrace();
		fail("Should not have problems interacting with DB");

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("Some other problem with the testing");

	    }

    }        

    /**
     *  Add in multiple reminders and determine if we can get a 
     *  String representation of all of the events.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2005
     */
    public void testAddReminderMultipleRemindersFullEventList()
    {

	try
	    {

		testReminder.addEvent("Steve Clulow Birthday",
					 9, 14, 1953, 1, 7);

		testReminder.addEvent("Deana Clulow Birthday",
					 8, 20, 1954, 1, 7);

		testReminder.addEvent("Stevie Clulow Birthday",
					 6, 28, 1973, 1, 7);

		testReminder.addEvent("Jenny Rogan Birthday",
					 10, 9, 1974, 1, 7);

		String eventLabel1 = "\nId: 0\n" + 
		    "Name: Steve Clulow Birthday\n" + 
		    "Month: 9\n" +
		    "Day: 14\n" + 
		    "Year: 1953\n" +
		    "M Trigger: 1\n" + 
		    "D Trigger: 7\n";

		String eventLabel2 = "\nId: 1\n" + 
		    "Name: Deana Clulow Birthday\n" + 
		    "Month: 8\n" +
		    "Day: 20\n" + 
		    "Year: 1954\n" +
		    "M Trigger: 1\n" + 
		    "D Trigger: 7\n";

		String eventLabel3 = "\nId: 2\n" + 
		    "Name: Stevie Clulow Birthday\n" + 
		    "Month: 6\n" +
		    "Day: 28\n" + 
		    "Year: 1973\n" +
		    "M Trigger: 1\n" + 
		    "D Trigger: 7\n";

		String eventLabel4 = "\nId: 3\n" + 
		    "Name: Jenny Rogan Birthday\n" + 
		    "Month: 10\n" +
		    "Day: 9\n" + 
		    "Year: 1974\n" +
		    "M Trigger: 1\n" + 
		    "D Trigger: 7\n";

		String expectedFullEventListing =
		    eventLabel1 + eventLabel2 +
		    eventLabel3 + eventLabel4;

		String fullEventListing = 
		    testReminder.getAllEvents();

		assertEquals(expectedFullEventListing,
			     fullEventListing);

	    }

	catch(ReminderDataException e)
	    {

		e.printStackTrace();
		fail("Should not have problems adding reminder");

	    }

	catch(SQLException e)
	    {

		e.printStackTrace();
		fail("Should not have problems interacting with DB");

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("Some other problem with the testing");

	    }

    }        

    /**
     *  Add in multiple reminders and determine if we can get a 
     *  String representation of all of the events.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2005
     */
    public void testAddReminderMultipleRemindersFullEventListDUPL()
    {

	try
	    {

		testReminder.addEvent("Steve Clulow Birthday",
					 9, 14, 1953, 1, 7);

		testReminder.addEvent("Deana Clulow Birthday",
					 8, 20, 1954, 1, 7);

		testReminder.addEvent("Stevie Clulow Birthday",
					 6, 28, 1973, 1, 7);

		testReminder.addEvent("Jenny Rogan Birthday",
					 10, 9, 1974, 1, 7);

		String eventLabel1 = "\nId: 0\n" + 
		    "Name: Steve Clulow Birthday\n" + 
		    "Month: 9\n" +
		    "Day: 14\n" + 
		    "Year: 1953\n" +
		    "M Trigger: 1\n" + 
		    "D Trigger: 7\n";

		String eventLabel2 = "\nId: 1\n" + 
		    "Name: Deana Clulow Birthday\n" + 
		    "Month: 8\n" +
		    "Day: 20\n" + 
		    "Year: 1954\n" +
		    "M Trigger: 1\n" + 
		    "D Trigger: 7\n";

		String eventLabel3 = "\nId: 2\n" + 
		    "Name: Stevie Clulow Birthday\n" + 
		    "Month: 6\n" +
		    "Day: 28\n" + 
		    "Year: 1973\n" +
		    "M Trigger: 1\n" + 
		    "D Trigger: 7\n";

		String eventLabel4 = "\nId: 3\n" + 
		    "Name: Jenny Rogan Birthday\n" + 
		    "Month: 10\n" +
		    "Day: 9\n" + 
		    "Year: 1974\n" +
		    "M Trigger: 1\n" + 
		    "D Trigger: 7\n";

		String expectedFullEventListing =
		    eventLabel1 + eventLabel2 +
		    eventLabel3 + eventLabel4;

		String fullEventListing = 
		    testReminder.getAllEvents();

		assertEquals(expectedFullEventListing,
			     fullEventListing);

	    }

	catch(ReminderDataException e)
	    {

		e.printStackTrace();
		fail("Should not have problems adding reminder");

	    }

	catch(SQLException e)
	    {

		e.printStackTrace();
		fail("Should not have problems interacting with DB");

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("Some other problem with the testing");

	    }

    }        

}
