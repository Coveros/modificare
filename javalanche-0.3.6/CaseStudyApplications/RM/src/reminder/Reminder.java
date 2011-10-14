/*---------------------------------------------------------------------
 *  File: $Id: Reminder.java,v 1.5 2005/07/09 23:09:22 gkapfham Exp $   
 *  Version:  $Revision: 1.5 $
 *
 *  Project: DIATOMS, Database drIven Application Testing tOol ModuleS
 *
 *--------------------------------------------------------------------*/

package reminder;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Date;
import java.util.Calendar;

import java.io.FileInputStream;

/**
 *  Main functionality of the Reminder program.
 *
 *  @author Gregory M. Kapfhammer 2/22/2005
 */

public class Reminder
{

    /** This is the connection to the database that we 
	are managing in this class */
    private static Connection databaseConnection = null;

    /**
     *  Constructor for the GradeBook.  We can have different GradeBooks
     *  that are associated with different DatabaseDescriptions.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2005
     */
    public Reminder(DatabaseDescription describe)
    {

	databaseConnection = ReminderCreator.
	    createDatabaseConnection(describe);

    }

    /**
     *  Adds a reminder to the database.
     *  
     *  @author Gregory M. Kapfhammer 7/8/2005
     */
    public void addEvent(String name, int month, int day, int year,
			    int monthTrigger, int dayTrigger)
	throws ReminderDataException, SQLException
    {

	String insertStmtStr =  ReminderConstants.INSERT + " " +
	    " " + ReminderConstants.INTO + " " +
	    ReminderConstants.REMINDER + " " + 
	    ReminderConstants.VALUES + " " + "(" +
	    ReminderConstants.NULL + ",'" +
	    name + "'," + month + "," + day + "," + year + "," + 
	    monthTrigger + "," + dayTrigger + ")";
	
	Statement insertStatement = databaseConnection.createStatement();
	
	// execute an INSERT INTO statement
	insertStatement.executeUpdate(insertStmtStr);

    }

    /**
     *  Produces a listing of all of the important reminders within
     *  the specified month trigger.  The listing is the IDs that we 
     *  have interest in.
     *  
     *  @author Gregory M. Kapfhammer 7/8/2005
     */
    public ArrayList getFutureCriticalEvents(int currentMonth)
					     //      int currentDay)
    {

	ArrayList futureEventsIds = new ArrayList();

	try
	    {
	
		// it we use the calendar, it makes it hard to test;
		// we will assume that currentMonth is wrt to the way
		// that people normally number them -- i.e. start with 1!
	
		//Calendar rightNow = Calendar.getInstance();
		
		// System.out.println("January " +
		// rightNow.get(Calendar.JANUARY));
		// System.out.println("July " +
		// rightNow.get(Calendar.JULY));

		// System.out.println("Month " +
		// rightNow.get(Calendar.MONTH));
		// System.out.println("Day " +
		// rightNow.get(Calendar.DAY_OF_MONTH));

		//int currentMonth = rightNow.get(Calendar.MONTH);

		String selectStmtStr = 
		    ReminderConstants.SELECT + " " + 
		    ReminderConstants.ALL + " " +
		    ReminderConstants.FROM + " " + 
		    ReminderConstants.REMINDER + " " + 
		    ReminderConstants.WHERE + " " + 
		    ReminderConstants.MONTH + " - " + 
		    ReminderConstants.MONTH_TRIGGER + " = " +
		    currentMonth;

// 		+ " " + 
// 		    ReminderConstants.AND + " " + 
// 		    ReminderConstants.MONTH + " = " +
// 		    currentMonth;
	
		// the data is correct in terms of last name` and we
		// should go ahead and place this into the database
		Statement selectStatement = 
		    databaseConnection.createStatement();

		// execute the query
		ResultSet criticalEventsResultSet = 
		    selectStatement.executeQuery(selectStmtStr);

		// go through looking for all of the critical events 
		// and store their information in the string buffer
		while( criticalEventsResultSet.next() )
		    {

			futureEventsIds.
			    add( new Integer(criticalEventsResultSet.
					     getInt(ReminderConstants.ID)) );
			
		    }

	    }

	catch(Exception e)
	    {

		e.printStackTrace();

	    }
	
	return futureEventsIds;

    }

    /**
     *  Produces a listing of all of the important reminders within
     *  the specified month trigger.  The listing is the IDs that we 
     *  have interest in.
     *  
     *  @author Gregory M. Kapfhammer 7/8/2005
     */
    public ArrayList getCurrentCriticalEvents(int currentMonth,
					      int currentDay)
    {

	ArrayList currentEventsIds = new ArrayList();

	try
	    {
	
		// it we use the calendar, it makes it hard to test;
		// we will assume that currentMonth is wrt to the way
		// that people normally number them -- i.e. start with 1!
	
		//Calendar rightNow = Calendar.getInstance();
		
		// System.out.println("January " +
		// rightNow.get(Calendar.JANUARY));
		// System.out.println("July " +
		// rightNow.get(Calendar.JULY));

		// System.out.println("Month " +
		// rightNow.get(Calendar.MONTH));
		// System.out.println("Day " +
		// rightNow.get(Calendar.DAY_OF_MONTH));

		//int currentMonth = rightNow.get(Calendar.MONTH);

		String selectStmtStr = 
		    ReminderConstants.SELECT + " " + 
		    ReminderConstants.ALL + " " +
		    ReminderConstants.FROM + " " + 
		    ReminderConstants.REMINDER + " " + 
		    ReminderConstants.WHERE + " " + 
		    currentDay + " " +
		    ReminderConstants.BETWEEN + " " +
		    ReminderConstants.DAY + " - " +
		    ReminderConstants.DAY_TRIGGER + " " +
		    ReminderConstants.AND + " " +
		    ReminderConstants.DAY + " " + 
		    ReminderConstants.AND + " " + 
		    ReminderConstants.MONTH + " = " +
		    currentMonth;
	
		// the data is correct in terms of last name` and we
		// should go ahead and place this into the database
		Statement selectStatement = 
		    databaseConnection.createStatement();

		// execute the query
		ResultSet criticalEventsResultSet = 
		    selectStatement.executeQuery(selectStmtStr);

		// go through looking for all of the critical events 
		// and store their information in the string buffer
		while( criticalEventsResultSet.next() )
		    {

			currentEventsIds.
			    add( new Integer(criticalEventsResultSet.
					     getInt(ReminderConstants.ID)) );
			
		    }

	    }

	catch(Exception e)
	    {

		e.printStackTrace();

	    }
	
	return currentEventsIds;

    }

    /**
     *  This method returns the complete set of information about 
     *  a specific event.
     *  
     *  @author Gregory M. Kapfhammer 7/8/2005
     */
    public String getEvent(int id)
    {

	StringBuffer eventInfo = new StringBuffer();
	
	String selectStmtStr = 
	    ReminderConstants.SELECT + " " +
	    ReminderConstants.ALL + " " + 
	    ReminderConstants.FROM + " " + 
	    ReminderConstants.REMINDER + " " +
	    ReminderConstants.WHERE + " " +
	    ReminderConstants.ID + " = " + 
	    id;

	try
	    {
		
		// the data is correct in terms of last name` and we
		// should go ahead and place this into the database
		Statement selectStatement = 
		    databaseConnection.createStatement();

		// execute the query
		ResultSet eventsResultSet = 
		    selectStatement.executeQuery(selectStmtStr);

		// go through looking for all of the critical events 
		// and store their information in the string buffer
		while( eventsResultSet.next() )
		    {

			eventInfo.
			    append( "Id: " + eventsResultSet.
				    getInt(ReminderConstants.ID) + 
				    "\n"); 

			eventInfo.
			    append( "Name: " + eventsResultSet.
				    getString(ReminderConstants.EVENT_NAME) + 
				    "\n"); 

			eventInfo.
			    append( "Month: " + eventsResultSet.
				    getInt(ReminderConstants.MONTH) + 
				    "\n"); 

			eventInfo.
			    append( "Day: " + eventsResultSet.
				    getInt(ReminderConstants.DAY) + 
				    "\n"); 

			eventInfo.
			    append( "Year: " + eventsResultSet.
				    getInt(ReminderConstants.YEAR) + 
				    "\n"); 

			eventInfo.
			    append( "M Trigger: " + eventsResultSet.
				    getInt(ReminderConstants.MONTH_TRIGGER) + 
				    "\n"); 

			eventInfo.
			    append( "D Trigger: " + eventsResultSet.
				    getInt(ReminderConstants.DAY_TRIGGER) + 
				    "\n"); 
			    
		    }

	    }

	catch(Exception e)
	    {

		e.printStackTrace();

	    }

	return eventInfo.toString();

    }

    /**
     *  This method deletes the complete set of information about 
     *  a specific event with respect to the ID.
     *  
     *  @author Gregory M. Kapfhammer 7/8/2005
     */
    public void deleteEvent(int id)
    {

	String deleteStmtStr = 
	    ReminderConstants.DELETE + " " +
	    ReminderConstants.FROM + " " + 
	    ReminderConstants.REMINDER + " " +
	    ReminderConstants.WHERE + " " +
	    ReminderConstants.ID + " = " + 
	    id;

	try
	    {
		
		Statement deleteStatement = 
		    databaseConnection.createStatement();

		deleteStatement.executeUpdate(deleteStmtStr);

	    }

	catch(Exception e)
	    {

		e.printStackTrace();

	    }

    }

    /**
     *  This method returns a string representation of all of the events.
     *  
     *  @author Gregory M. Kapfhammer 7/8/2005
     */
    public String getAllEvents()
    {

	StringBuffer allEventsBuffer = new StringBuffer();


	// find the id of all of the events that are currently 
	// inside of the database (we can then make a call with this
	// id and get all of the information about it in String form)
	String selectIdStmtStr = 
	    ReminderConstants.SELECT + " " +
	    ReminderConstants.ID + " " + 
	    ReminderConstants.FROM + " " + 
	    ReminderConstants.REMINDER;

	try
	    {

		Statement selectStatement =
		    databaseConnection.createStatement();

		// execute the query
		ResultSet eventIdResultSet = 
		    selectStatement.executeQuery(selectIdStmtStr);

		// go through looking for all of the critical events 
		// and store their information in the string buffer
		while( eventIdResultSet.next() )
		    {
	    
			allEventsBuffer.append("\n");

			int currentEventId = 
			    eventIdResultSet.
			    getInt(ReminderConstants.ID);

			allEventsBuffer.
			    append(this.getEvent(currentEventId));

		    }

	    }

	catch(Exception e)
	    {

		e.printStackTrace();

	    }
	    

	return allEventsBuffer.toString();

    }

}
