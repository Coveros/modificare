/*---------------------------------------------------------------------
 *  File: $Id: ReminderConstants.java,v 1.3 2005/07/08 16:31:34 gkapfham Exp $   
 *  Version:  $Revision: 1.3 $
 *
 *  Project: DIATOMS, Database drIven Application Testing tOol ModuleS
 *
 *--------------------------------------------------------------------*/

package reminder;

/**
 *  Constants that are used by other Reminder classes.
 *
 *  @author Gregory M. Kapfhammer 2/22/2005
 */

public abstract class ReminderConstants
{

    /** Names of all the tables in the database */
    public static final String REMINDER = "Reminder";

    /** The SQL keywords that will be commonly used during the 
	GradeBook application's execution */
    public static final String INSERT = "INSERT";
    public static final String SELECT = "SELECT";
    public static final String UPDATE = "UPDATE";
    public static final String DELETE = "DELETE";
    public static final String INTO = "INTO";
    public static final String NULL = "NULL";
    public static final String VALUES = "VALUES";
    public static final String FROM = "FROM";
    public static final String WHERE = "WHERE";
    public static final String AND = "AND";
    public static final String SET = "SET";
    public static final String ALL = "*";
    public static final String BETWEEN = "BETWEEN";

    /** Specific fields within the different tables */
    
    /** Student table */
    public static final String ID = "ID";
    public static final String EVENT_NAME = "Event_Name";
    public static final String MONTH = "MONTH";
    public static final String DAY = "DAY";
    public static final String YEAR = "YEAR";
    public static final String MONTH_TRIGGER = "MONTH_TRIGGER";
    public static final String DAY_TRIGGER = "DAY_TRIGGER";

}
