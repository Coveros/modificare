package TransactionAgent;




/**
 *  Describes a Database.
 *
 *  @author Gregory M. Kapfhammer 2/19/2005
 */

public class DatabaseDescription
{

    /** The URl for the database */
    private String databaseUrl;

    /** The URL alias for the database */
    private String databaseUserName;

    /** The Path for the database */
    private String databasePassword;

    public DatabaseDescription(String url, String name, String pass)
    {

	databaseUrl = url;
	databaseUserName = name;
	databasePassword = pass;

    }

    public String getUrl()
    {

	return databaseUrl;

    }

    public String getUserName()
    {

	return databaseUserName;

    }

    public String getPassword()
    {

	return databasePassword;

    }

}
