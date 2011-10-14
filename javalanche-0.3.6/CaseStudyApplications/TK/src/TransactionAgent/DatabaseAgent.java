/*---------------------------------------------------------------------
 *  File: $Id: DatabaseAgent.java,v 1.1 2002/03/20 18:13:17 gkapfham Exp $   
 *  Version:  $Revision: 1.1 $
 *
 *  Project: Reverification Prototype Testbed
 *
 *--------------------------------------------------------------------*/

package TransactionAgent;

import java.sql.*;

/**
 *  This interface is implemented by all of the different types of 
 *  DatabaseAgents that could be produced.  The interface defines the 
 *  static set of methods that all TransactionAgent.DatabaseAgents must
 *  implement.  A class that implements this interface will be used to 
 *  connect to a database and perform specific types of operations for 
 *  Bank services.
 *
 *  @author Gregory M. Kapfhammer 4/12/2000
 */

public interface DatabaseAgent 
{

  public void setBankDSN(String bank_dsn) throws SQLException, 
  ClassNotFoundException;
  
  public String getBankDSN();

  public boolean verifyCard(String user_name, int card_num, int pin_num) 
    throws SQLException;
  
  public int accountExists(String account_type, String rank, 
			   int card_number) throws SQLException; 
			  
  public double getAccountBalance(int uid) throws SQLException;

  public boolean accountWithdraw(int uid, double amount) 
    throws SQLException;

  public boolean accountDeposit(int uid, double amount) 
    throws SQLException;

   public boolean accountTransfer(int source_uid, int dest_uid, double amount) 
     throws SQLException;
	    
  public int lockAccount(int card_number) throws SQLException;

  public int isLocked(int card_number) throws SQLException;

}


