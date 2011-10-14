/*---------------------------------------------------------------------
 *  File: $Id: ReminderDataException.java,v 1.1 2005/07/08 14:33:19 gkapfham Exp $   
 *  Version:  $Revision: 1.1 $
 *
 *  Project: DIATOMS, Database drIven Application Testing tOol ModuleS
 *
 *--------------------------------------------------------------------*/

package reminder;

/**
 *  Exception to indicate that there was a problem with the data 
 *  and the Reminder application.
 *
 *  @author Gregory M. Kapfhammer 2/22/2005
 */

public class ReminderDataException extends Exception
{

    public ReminderDataException()
    {

	super();

    }

    public ReminderDataException(String message)
    {

	super(message);

    }

}
