/*---------------------------------------------------------------------
 *  File: $Id: TestGradeBookCreatorWithFullDataSet.java,v 1.2 2005/03/01 02:59:40 gkapfham Exp $   
 *  Version:  $Revision: 1.2 $
 *
 *  Project: DIATOMS, Database drIven Application Testing tOol ModuleS
 *
 *--------------------------------------------------------------------*/

package gradebook;

import org.dbunit.DatabaseTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.xml.FlatXmlDataSet;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;

import java.io.FileInputStream;

/**
 *  Test case for the GradeBookCreator class.
 *
 *  @author Gregory M. Kapfhammer 2/19/2005
 */

public class TestGradeBookCreatorWithFullDataSet extends DatabaseTestCase
{

    /** This is the connection that will be passed to the other
	GradeBookCreator methods when we are testing them */
    private Connection testConnection;

    /** Constants required during testing */
    private static final String URL = 
	"jdbc:hsqldb:hsql://localhost/GradeBookDBTestOnly";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    /** Description of the test database */
    private DatabaseDescription testDatabaseDescription;

    /**
     *  Required default constructor.
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    public TestGradeBookCreatorWithFullDataSet(String name)
    {

	super(name);

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
     *  Can the Master table be created without errors?
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    public void testCreateMasterTable()
    {

	try
	    {

		GradeBookCreator.
		    createMasterTable(testDatabaseDescription);

		// it should contain the master table
		assertTrue(GradeBookCreator.
			   containsMasterTable(testDatabaseDescription));
		
		// remove the master table so other test runs can 
		// run independently
		GradeBookCreator.
		    dropMasterTable(testDatabaseDescription);

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("testCreateMasterTable Produced Exception");
		
	    }

    }

    /**
     *  Should not be able to DROP if table does not exist.
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    public void testDropMasterNotPossible()
    {

	try
	    {

		GradeBookCreator.dropMasterTable(testDatabaseDescription);
		fail("Was able to DROP when table not there");

	    }

	catch(SQLException e)
	    {

		// test case passes

	    }

    }

    /**
     *  Should be able to create Student table.
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    public void testCreateStudentTable()
    {

	try
	    {

		GradeBookCreator.
		    createStudentTable(testDatabaseDescription);

		// it should contain the student table
		assertTrue(GradeBookCreator.
			   containsStudentTable(testDatabaseDescription));
		
		// remove the master table so other test runs can 
		// run independently
		GradeBookCreator.
		    dropStudentTable(testDatabaseDescription);

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("testCreateStudentTable Produced Exception");
		
	    }

    }

    /**
     *  Should not be able to DROP if table does not exist.
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    public void testDropStudentNotPossible()
    {

	try
	    {

		GradeBookCreator.dropStudentTable(testDatabaseDescription);
		fail("Was able to DROP when table not there");

	    }

	catch(SQLException e)
	    {

		// test case passes

	    }

    }

    /**
     *  Should be able to create ExamMaster table.
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    public void testCreateExamMasterTable()
    {

	try
	    {

		GradeBookCreator.
		    createExamMasterTable(testDatabaseDescription);

		// it should contain the Exammaster table
		assertTrue(GradeBookCreator.
			   containsExamMasterTable(testDatabaseDescription));
		
		// remove the master table so other test runs can 
		// run independently
		GradeBookCreator.
		    dropExamMasterTable(testDatabaseDescription);

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("testCreateExamMasterTable Produced Exception");
		
	    }

    }

    /**
     *  Should not be able to DROP if table does not exist.
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    public void testDropExamMasterNotPossible()
    {

	try
	    {

		GradeBookCreator.dropExamMasterTable(testDatabaseDescription);
		fail("Was able to DROP when table not there");

	    }

	catch(SQLException e)
	    {

		// test case passes

	    }

    }

    /**
     *  Should be able to create ExamScores table.
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    public void testCreateExamScoresTable()
    {

	try
	    {

		GradeBookCreator.
		    createExamScoresTable(testDatabaseDescription);

		// it should contain the Exammaster table
		assertTrue(GradeBookCreator.
			   containsExamScoresTable(testDatabaseDescription));
		
		// remove the master table so other test runs can 
		// run independently
		GradeBookCreator.
		    dropExamScoresTable(testDatabaseDescription);

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("testCreateExamScoresTable Produced Exception");
		
	    }

    }

    /**
     *  Should not be able to DROP if table does not exist.
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    public void testDropExamScoresNotPossible()
    {

	try
	    {

		GradeBookCreator.dropExamScoresTable(testDatabaseDescription);
		fail("Was able to DROP when table not there");

	    }

	catch(SQLException e)
	    {

		// test case passes

	    }

    }

    /**
     *  Should be able to create LabMaster table.
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    public void testCreateLabMasterTable()
    {

	try
	    {

		GradeBookCreator.
		    createLabMasterTable(testDatabaseDescription);

		// it should contain the Exammaster table
		assertTrue(GradeBookCreator.
			   containsLabMasterTable(testDatabaseDescription));
		
		// remove the master table so other test runs can 
		// run independently
		GradeBookCreator.
		    dropLabMasterTable(testDatabaseDescription);

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("testCreateLabMasterTable Produced Exception");
		
	    }

    }

    /**
     *  Should not be able to DROP if table does not exist.
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    public void testDropLabMasterNotPossible()
    {

	try
	    {

		GradeBookCreator.dropLabMasterTable(testDatabaseDescription);
		fail("Was able to DROP when table not there");

	    }

	catch(SQLException e)
	    {

		// test case passes

	    }

    }

    /**
     *  Should be able to create LabScores table.
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    public void testCreateLabScoresTable()
    {

	try
	    {

		GradeBookCreator.
		    createLabScoresTable(testDatabaseDescription);

		// it should contain the Exammaster table
		assertTrue(GradeBookCreator.
			   containsLabScoresTable(testDatabaseDescription));
		
		// remove the master table so other test runs can 
		// run independently
		GradeBookCreator.
		    dropLabScoresTable(testDatabaseDescription);

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("testCreateLabScoresTable Produced Exception");
		
	    }

    }

    /**
     *  Should not be able to DROP if table does not exist.
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    public void testDropLabScoresNotPossible()
    {

	try
	    {

		GradeBookCreator.dropLabScoresTable(testDatabaseDescription);
		fail("Was able to DROP when table not there");

	    }

	catch(SQLException e)
	    {

		// test case passes

	    }

    }

    /**
     *  Should be able to create HomeworkMaster table.
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    public void testCreateHomeworkMasterTable()
    {

	try
	    {

		GradeBookCreator.
		    createHomeworkMasterTable(testDatabaseDescription);

		// it should contain the Exammaster table
		assertTrue(GradeBookCreator.
			   containsHomeworkMasterTable(testDatabaseDescription));
		
		// remove the master table so other test runs can 
		// run independently
		GradeBookCreator.
		    dropHomeworkMasterTable(testDatabaseDescription);

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("testHomeworkMasterTable Produced Exception");
		
	    }

    }

    /**
     *  Should not be able to DROP if table does not exist.
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    public void testDropHomeworkMasterNotPossible()
    {

	try
	    {

		GradeBookCreator.
		    dropHomeworkMasterTable(testDatabaseDescription);
		fail("Was able to DROP when table not there");

	    }

	catch(SQLException e)
	    {

		// test case passes

	    }

    }

    /**
     *  Should be able to create LabScores table.
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    public void testCreateHomeworkScoresTable()
    {

	try
	    {

		GradeBookCreator.
		    createHomeworkScoresTable(testDatabaseDescription);

		// it should contain the Exammaster table
		assertTrue(GradeBookCreator.
			   containsHomeworkScoresTable(testDatabaseDescription));
		
		// remove the master table so other test runs can 
		// run independently
		GradeBookCreator.
		    dropHomeworkScoresTable(testDatabaseDescription);

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("testCreateHomeworkScoresTable Produced Exception");
		
	    }

    }

    /**
     *  Should not be able to DROP if table does not exist.
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    public void testDropHomeworkScoresNotPossible()
    {

	try
	    {

		GradeBookCreator.
		    dropHomeworkScoresTable(testDatabaseDescription);
		fail("Was able to DROP when table not there");

	    }

	catch(SQLException e)
	    {

		// test case passes

	    }

    }

    /**
     *  Should be able to create FinalProjectScores table.
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    public void testCreateFinalProjectScoresTable()
    {

	try
	    {

		GradeBookCreator.
		    createFinalProjectScoresTable(testDatabaseDescription);

		// it should contain the Exammaster table
		assertTrue(GradeBookCreator.
			   containsFinalProjectScoresTable(testDatabaseDescription));
		
		// remove the master table so other test runs can 
		// run independently
		GradeBookCreator.
		    dropFinalProjectScoresTable(testDatabaseDescription);

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("testCreateFinalProjectScoresTable Produced Exception");
		
	    }

    }

    /**
     *  Should not be able to DROP if table does not exist.
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    public void testDropFinalProjectScoresNotPossible()
    {

	try
	    {

		GradeBookCreator.dropFinalProjectScoresTable(testDatabaseDescription);
		fail("Was able to DROP when table not there");

	    }

	catch(SQLException e)
	    {

		// test case passes

	    }

    }

}
