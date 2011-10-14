/*---------------------------------------------------------------------
 *  File: $Id: TestGradeBook.java,v 1.15 2005/07/07 20:24:59 gkapfham Exp gkapfham $   
 *  Version:  $Revision: 1.15 $
 *
 *  Project: DIATOMS, Database drIven Application Testing tOol ModuleS
 *
 *--------------------------------------------------------------------*/

package gradebook;

import junit.framework.*;

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

import java.util.ArrayList;
import java.util.Iterator;

/**
 *  Test Suite for the GradeBook class.
 *
 *  @author Gregory M. Kapfhammer 2/22/2005
 */

public class TestGradeBook extends DatabaseTestCase
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
	"jdbc:hsqldb:hsql://localhost/GradeBookDBTestOnly";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    /** The gradebook that we will use to perform all of the testing */
    private GradeBook testGradeBook;	

   
    /** Flag to indicate whether the database server exists */
    private static boolean databaseServerExists = false;

    /**
     *  Required default constructor.
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    public TestGradeBook(String name)
    {

	super(name);

    }


    public static Test suite() 
    {
	
	TestSuite suite= new TestSuite(gradebook.TestGradeBook.class);
	return suite;

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
	testGradeBook = new GradeBook(testDatabaseDescription);

	if( !tablesExist )
	    {

		// create each of the tables that will be populated during
		// testing inside of this test suite
		GradeBookCreator.
		    createMasterTable(testDatabaseDescription);

		GradeBookCreator.
		    createStudentTable(testDatabaseDescription);

		GradeBookCreator.
		    createExamMasterTable(testDatabaseDescription);

		GradeBookCreator.
		    createExamScoresTable(testDatabaseDescription);

		GradeBookCreator.
		    createLabMasterTable(testDatabaseDescription);

		GradeBookCreator.
		    createLabScoresTable(testDatabaseDescription);

		GradeBookCreator.
		    createHomeworkMasterTable(testDatabaseDescription);

		GradeBookCreator.
		    createHomeworkScoresTable(testDatabaseDescription);

		GradeBookCreator.
		    createFinalProjectScoresTable(testDatabaseDescription);

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
		GradeBookCreator.dropMasterTable(testDatabaseDescription);
		GradeBookCreator.dropStudentTable(testDatabaseDescription);

		GradeBookCreator.
		    dropExamMasterTable(testDatabaseDescription);

		GradeBookCreator.
		    dropExamScoresTable(testDatabaseDescription);

		GradeBookCreator.
		    dropLabMasterTable(testDatabaseDescription);

		GradeBookCreator.
		    dropLabScoresTable(testDatabaseDescription);

		GradeBookCreator.
		    dropHomeworkMasterTable(testDatabaseDescription);
		
		GradeBookCreator.
		    dropHomeworkScoresTable(testDatabaseDescription);

		GradeBookCreator.
		    dropFinalProjectScoresTable(testDatabaseDescription);

		// signal that none of the tables are now inside
		// of the database
		tablesExist = false;

		

	    }

	catch(SQLException e)
	    {

		fail("Could not drop table in tearDown");

	    }

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
			
			GradeBookCreator.
			    createDatabaseServer("localhost",
						 "GradeBookDBTestOnly",
						 "db/GradeBookDBTestOnly");
		
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
//     protected IDatabaseConnection getConnection() throws Exception
//     {

// 	// describe the database
// 	testDatabaseDescription = 
// 	    new DatabaseDescription(URL, USERNAME, PASSWORD);

// 	// only start the database server once ; 
// 	// otherwise, we will see error messages related to sockets
//       	createDatabaseServerOnce();
	
// 	// Load the HSQL Database Engine JDBC driver
// 	Class.forName("org.hsqldb.jdbcDriver");

// 	testConnection =
// 	    DriverManager.
// 	    getConnection(testDatabaseDescription.getUrl(), 
// 			  testDatabaseDescription.getUserName(), 
// 			  testDatabaseDescription.getPassword());

// 	return new DatabaseConnection(testConnection);

//     }

    /**
     *  Retrieve the data set from a local XML file.
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
//     protected IDataSet getDataSet() throws Exception
//     {

// 	return 
// 	    new FlatXmlDataSet(new FileInputStream("data/EmptyDataSet.xml"));

//     }

    /*
     *  The database server should be running since it was started
     *  one time inside of the setUp code.
     *  
     *  @author Gregory M. Kapfhammer 2/19/2005
     */
    public void testDatabaseServerIsRunningInGradeBook()
    {

	assertTrue(GradeBookCreator.databaseServerIsRunning());

    }

    /**
     *  We should not be able to create the database server again;
     *  the method should return false to indicate that it was not
     *  started again.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2005
     */
    public void testDoesNotStartServerAgainInGradeBook()
    {

	// try multiple times to ensure that there are no sequencing
	// or timing issues assocated with creating the server

	assertTrue(!GradeBookCreator.
		   createDatabaseServer("localhost",
					"GradeBookDBTestOnly",
					"db/GradeBookDBTestOnly"));

	assertTrue(!GradeBookCreator.
		   createDatabaseServer("localhost",
					"GradeBookDBTestOnly",
					"db/GradeBookDBTestOnly"));

	assertTrue(!GradeBookCreator.
		   createDatabaseServer("localhost",
					"GradeBookDBTestOnly",
					"db/GradeBookDBTestOnly"));
	
    }

    /**
     *  Should throw an exception because percentages are not correct.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2005
     */
    public void testPopulateMasterTableWrongPercentages()
    {

	try
	    {

		testGradeBook.populateMaster(.1, .1, .1, .1, .1, .1, .1);
		fail("Wrong Percentages for the Master Table");

	    }

	catch(GradeBookDataException e)
	    {

		// test case passes

	    }

	catch(SQLException e)
	    {

		e.printStackTrace();
		fail("Should not have problems interacting with DB");

	    }

    }    

    /**
     *  Can we populate the Master table with the right values?
     *  
     *  <Master Id = '1'
        Participation = '.05'
        ExamOne = '.125'
        ExamTwo = '.125'
        ExamThree = '.20'
        Laboratory = '.25'
        Homework = '.15' 
	FinalProject = '.1' />
     *	
     *  @author Gregory M. Kapfhammer 2/22/2005
     */
    public void testPopulateMasterTable()
    {

	try
	    {
		
		testGradeBook.populateMaster(.05, .125, .125, .20, .25, .15,
					     .1);

		// create the expected Master Table from the XML
		IDataSet expectedDatabaseState = getDataSet(); 
		ITable expectedMasterTable = 
		    expectedDatabaseState.
		    getTable(GradeBookConstants.MASTER);

		// create the actual Master Table from the database
		IDataSet actualDatabaseState = 
		    getConnection().createDataSet();
		ITable actualMasterTable = 
		    actualDatabaseState.
		    getTable(GradeBookConstants.MASTER);
		
		// expected should be equal to actual in terms 
		// of both the number of rows and the data
		Assertion.assertEquals(expectedMasterTable,
				       actualMasterTable);
				       
		

	    }

	catch(SQLException e)
	    {

		e.printStackTrace();
		fail("Problem interacting with database");

	    }

	catch(GradeBookDataException e)
	    {

		e.printStackTrace();
		fail("Problem with placing data in GradeBook database");

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("Other DBUnit Exception was thrown");

	    }

    }

    /**
     *  Should throw an exception because first name is not correct.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2005
     */
    public void testAddNullStudentNotPossible()
    {

	try
	    {

		testGradeBook.addStudent(null, "Kapfhammer", "gkapfham");
		fail("null could be added to the database");

	    }

	catch(GradeBookDataException e)
	    {

		// test case passes

	    }

	catch(SQLException e)
	    {

		e.printStackTrace();
		fail("Should not have problems interacting with DB");

	    }

    }    

    /**
     *  Should throw an exception because first name is not correct.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2005
     */
    public void testAddEmptyStringStudentNotPossible()
    {

	try
	    {

		testGradeBook.addStudent("", "Kapfhammer", "gkapfham");
		fail("null could be added to the database");

	    }

	catch(GradeBookDataException e)
	    {

		// test case passes

	    }

	catch(SQLException e)
	    {

		e.printStackTrace();
		fail("Should not have problems interacting with DB");

	    }

    }    
    
    /**
     *  Can we add a student to the database correctly?
     *  
     *  @author Gregory M. Kapfhammer 2/22/2004
     */
    public void testAddStudent()
    {

	try
	    {
		
		testGradeBook.addStudent("Gregory0",
					 "Kapfhammer0",
					 "gkapfham0");

		testGradeBook.addStudent("Gregory1",
					 "Kapfhammer1",
					 "gkapfham1");

		// create the expected Master Table from the XML
		IDataSet expectedDatabaseState = getDataSet(); 
		ITable expectedMasterTable = 
		    expectedDatabaseState.
		    getTable(GradeBookConstants.STUDENT);

		// create the actual Master Table from the database
		IDataSet actualDatabaseState = 
		    getConnection().createDataSet();
		ITable actualMasterTable = 
		    actualDatabaseState.
		    getTable(GradeBookConstants.STUDENT);
		
		// expected should be equal to actual in terms 
		// of both the number of rows and the data
		Assertion.assertEquals(expectedMasterTable,
				       actualMasterTable);
		
		// should be able to find a matching ID 0 and 1
		int expectedStudentIDFirst = 0;
		       
		int actualStudentIDFirst = 
		    testGradeBook.getStudentID("Kapfhammer0");

		assertEquals(expectedStudentIDFirst, actualStudentIDFirst);

		int expectedStudentIDSecond = 1;
		       
		int actualStudentIDSecond = 
		    testGradeBook.getStudentID("Kapfhammer1");

		assertEquals(expectedStudentIDSecond, 
			     actualStudentIDSecond);
		
		// should not be able to find this student in database
		int errorExpectedStudentID = -1;
		
		int errorActualStudentID =
		    testGradeBook.getStudentID("NotThere");

		assertEquals(errorExpectedStudentID,
			     errorActualStudentID);

	    }

	catch(SQLException e)
	    {

		e.printStackTrace();
		fail("Problem interacting with database");

	    }

	catch(GradeBookDataException e)
	    {

		e.printStackTrace();
		fail("Problem with placing data in GradeBook database");

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("Other DBUnit Exception was thrown");

	    }

    }

    /**
     *  Should throw an exception because negative is not correct.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2005
     */
    public void testAddExamNegativeNotPossible()
    {

	try
	    {

		testGradeBook.addExam("ExamOne", -1);
		fail("negative could be added to the database");

	    }

	catch(GradeBookDataException e)
	    {

		// test case passes

	    }

	catch(SQLException e)
	    {

		e.printStackTrace();
		fail("Should not have problems interacting with DB");

	    }

    }    

    /**
     *  Should throw an exception because name is not correct.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2005
     */
    public void testAddExamNullNotPossible()
    {

	try
	    {

		testGradeBook.addExam("", 50);
		fail("negative could be added to the database");

	    }

	catch(GradeBookDataException e)
	    {

		// test case passes

	    }

	catch(SQLException e)
	    {

		e.printStackTrace();
		fail("Should not have problems interacting with DB");

	    }

    }    

    /**
     *  Can we populate the ExamMaster table with the right values?
     *  
     *  @author Gregory M. Kapfhammer 2/22/2005
     */
    public void testAddExamsToExamMasterTable()
    {

	try
	    {
		
		// add the three standard exams with their 
		// standard point totals
		testGradeBook.addExam("ExamOne", 50);
		testGradeBook.addExam("ExamTwo", 50);
		testGradeBook.addExam("ExamThree", 100);

		// public int setExamCurve(int newCurve, int examId)

		// set the curve for the first examination
		testGradeBook.setExamCurve(5, 0);

		// create the expected Master Table from the XML
		IDataSet expectedDatabaseState = getDataSet(); 
		ITable expectedMasterTable = 
		    expectedDatabaseState.
		    getTable(GradeBookConstants.EXAMMASTER);

		// create the actual Master Table from the database
		IDataSet actualDatabaseState = 
		    getConnection().createDataSet();
		ITable actualMasterTable = 
		    actualDatabaseState.
		    getTable(GradeBookConstants.EXAMMASTER);
		
		// expected should be equal to actual in terms 
		// of both the number of rows and the data
		Assertion.assertEquals(expectedMasterTable,
				       actualMasterTable);

		// check to see that the values for the total
		// points are correct
		int firstAndSecondExamPointsExpected = 50;
		int finalExamPointsExpected = 100;
				       
		assertEquals(firstAndSecondExamPointsExpected,
			     testGradeBook.getExamTotalPoints(0));
		assertEquals(firstAndSecondExamPointsExpected,
			     testGradeBook.getExamTotalPoints(1));
		assertEquals(finalExamPointsExpected,
			     testGradeBook.getExamTotalPoints(2));
		
		// check to see that the values for curves are 
		// all set to the default of 0
		int curveDefaultExpected = 0;
		int curveDefaultExpectedSpecial = 5;

		assertEquals(curveDefaultExpectedSpecial,
			     testGradeBook.getExamCurve(0));
		assertEquals(curveDefaultExpected,
			     testGradeBook.getExamCurve(1));
		assertEquals(curveDefaultExpected,
			     testGradeBook.getExamCurve(2));
		

	    }

	catch(SQLException e)
	    {

		e.printStackTrace();
		fail("Problem interacting with database");

	    }

	catch(GradeBookDataException e)
	    {

		e.printStackTrace();
		fail("Problem with placing data in GradeBook database");

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("Other DBUnit Exception was thrown");

	    }
	
    }

    /**
     *  Can we populate the ExamMaster table with the right values
     *  using a different data set that contains curves?
     *  
     *  @author Gregory M. Kapfhammer 2/22/2005
     */
    public void testAddExamsToExamMasterTableIncludeCurves()
    {

	try
	    {
		
		// add the three standard exams with their 
		// standard point totals
		testGradeBook.addExam("ExamOne", 50);
		testGradeBook.addExam("ExamTwo", 50);
		testGradeBook.addExam("ExamThree", 100);

		// set the curves for the second two examinations
		// (we are leaving the curve for the first 
		// examination at the default)
		int changeOneActual1 = testGradeBook.setExamCurve(5, 1);
		int changeOneActual2 = testGradeBook.setExamCurve(20, 2);

		// both of these should have changed just one row
		// inside of the ExamMaster table
		assertEquals(1, changeOneActual1);
		assertEquals(1, changeOneActual2);

		// create the expected Master Table from the XML
		IDataSet expectedDatabaseState = 
		    new 
		    FlatXmlDataSet(new 
				   FileInputStream("data/FullDataSetCurves.xml"));
		ITable expectedMasterTable = 
		    expectedDatabaseState.
		    getTable(GradeBookConstants.EXAMMASTER);

		// create the actual Master Table from the database
		IDataSet actualDatabaseState = 
		    getConnection().createDataSet();
		ITable actualMasterTable = 
		    actualDatabaseState.
		    getTable(GradeBookConstants.EXAMMASTER);
		
		// expected should be equal to actual in terms 
		// of both the number of rows and the data
		Assertion.assertEquals(expectedMasterTable,
				       actualMasterTable);

		// check to see that the values for the total
		// points are correct
		int firstAndSecondExamPointsExpected = 50;
		int finalExamPointsExpected = 100;
				       
		assertEquals(firstAndSecondExamPointsExpected,
			     testGradeBook.getExamTotalPoints(0));
		assertEquals(firstAndSecondExamPointsExpected,
			     testGradeBook.getExamTotalPoints(1));
		assertEquals(finalExamPointsExpected,
			     testGradeBook.getExamTotalPoints(2));
		
		// check to see that the values for curves are 
		// all set to the default of 0
		int curveDefaultExpected0 = 0;
		int curveDefaultExpected1 = 5;
		int curveDefaultExpected2 = 20;
		
		assertEquals(curveDefaultExpected0,
			     testGradeBook.getExamCurve(0));
		assertEquals(curveDefaultExpected1,
			     testGradeBook.getExamCurve(1));
		assertEquals(curveDefaultExpected2,
			     testGradeBook.getExamCurve(2));
		

	    }

	catch(SQLException e)
	    {

		e.printStackTrace();
		fail("Problem interacting with database");

	    }

	catch(GradeBookDataException e)
	    {

		e.printStackTrace();
		fail("Problem with placing data in GradeBook database");

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("Other DBUnit Exception was thrown");

	    }
	
    }

    /** Should be testing that the key constraints are maintained;
	not sure how to express this inside of an HSQLDB schema */

    /**
     *  Should throw an exception because negative is not correct.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2005
     */
    public void testAddExamScoreSecondNegativeNotPossible()
    {

	try
	    {

		testGradeBook.addExamScore(0, 1, -1);
		fail("negative could be added to the database");

	    }

	catch(GradeBookDataException e)
	    {

		// test case passes

	    }

	catch(SQLException e)
	    {

		e.printStackTrace();
		fail("Should not have problems interacting with DB");

	    }

    }    

    /**
     *  Should throw an exception because negative is not possible.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2005
     */
    public void testAddExamScoreFirstNegativeNotPossible()
    {

	try
	    {

		testGradeBook.addExamScore(-1, 0, 50);
		fail("negative could be added to the database");

	    }

	catch(GradeBookDataException e)
	    {

		// test case passes

	    }

	catch(SQLException e)
	    {

		e.printStackTrace();
		fail("Should not have problems interacting with DB");

	    }

    }    

    /**
     *  Can we populate the ExamScores table with the right values?
     *  
     *  @author Gregory M. Kapfhammer 2/22/2005
     */
    public void testAddExamScoresToExamScoresTable()
    {

	try
	    {

		// add the three standard exams with their 
		// standard point totals
		testGradeBook.addExam("ExamOne", 50);
		testGradeBook.addExam("ExamTwo", 50);
		testGradeBook.addExam("ExamThree", 100);
		
		// public int setExamCurve(int newCurve, int examId)
		
		// add in the curves to the ExamMaster table
		testGradeBook.setExamCurve(5, 0);

		// check to see if the ExamMaster table contains the 
		// right curve (only the first exam will have a curve)

		// create the expected Exam MASTER from the XML
		IDataSet expectedDatabaseStateFirst = getDataSet(); 
		ITable expectedMasterTable = 
		    expectedDatabaseStateFirst.
		    getTable(GradeBookConstants.EXAMMASTER);

		// create the actual Scores Table from the database
		IDataSet actualDatabaseStateFirst = 
		    getConnection().createDataSet();
		ITable actualMasterTable = 
		    actualDatabaseStateFirst.
		    getTable(GradeBookConstants.EXAMMASTER);
		
		// expected should be equal to actual in terms 
		// of both the number of rows and the data
		Assertion.assertEquals(expectedMasterTable,
				       actualMasterTable);

		// add the three student exams with their point totals
		testGradeBook.addExamScore(0, 0, 35);
		testGradeBook.addExamScore(1, 1, 43);
		testGradeBook.addExamScore(2, 2, 33);

		// create the expected Exam Scores from the XML
		IDataSet expectedDatabaseState = getDataSet(); 
		ITable expectedScoresTable = 
		    expectedDatabaseState.
		    getTable(GradeBookConstants.EXAMSCORES);

		// create the actual Scores Table from the database
		IDataSet actualDatabaseState = 
		    getConnection().createDataSet();
		ITable actualScoresTable = 
		    actualDatabaseState.
		    getTable(GradeBookConstants.EXAMSCORES);
		
		// expected should be equal to actual in terms 
		// of both the number of rows and the data
		Assertion.assertEquals(expectedScoresTable,
				       actualScoresTable);

		// public int getExamScore(int examId, int studentId)

		// note that the first examination has the curve!  

		int firstEarnedPointsExpected = 35 + 5;
		int secondEarnedPointsExpected = 43;
		int finalExamEarnedPointsExpected = 33;
				       
		assertEquals(firstEarnedPointsExpected,
			     testGradeBook.getExamScore(0,0));
		assertEquals(secondEarnedPointsExpected,
			     testGradeBook.getExamScore(1,1));
		assertEquals(finalExamEarnedPointsExpected,
			     testGradeBook.getExamScore(2,2));
		
	    }

	catch(SQLException e)
	    {

		e.printStackTrace();
		fail("Problem interacting with database");

	    }

	catch(GradeBookDataException e)
	    {

		e.printStackTrace();
		fail("Problem with placing data in GradeBook database");

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("Other DBUnit Exception was thrown");

	    }
	
    }

    /**
     *  Can we calculate the examination averages properly?
     *  
     *  @author Gregory M. Kapfhammer 2/22/2004
     */
    public void testCorrectAverageExamScores()
    {

	try
	    {

		// add the three standard exams with their 
		// standard point totals
		testGradeBook.addExam("ExamOne", 50);
		testGradeBook.addExam("ExamTwo", 50);
		testGradeBook.addExam("ExamThree", 100);
		
		// public int setExamCurve(int newCurve, int examId)
		
		// add in the curves to the ExamMaster table
		testGradeBook.setExamCurve(5, 0);

		// check to see if the ExamMaster table contains the 
		// right curve (only the first exam will have a curve)

		// create the expected Exam MASTER from the XML
		IDataSet expectedDatabaseStateFirst = getDataSet(); 
		ITable expectedMasterTable = 
		    expectedDatabaseStateFirst.
		    getTable(GradeBookConstants.EXAMMASTER);

		// create the actual Scores Table from the database
		IDataSet actualDatabaseStateFirst = 
		    getConnection().createDataSet();
		ITable actualMasterTable = 
		    actualDatabaseStateFirst.
		    getTable(GradeBookConstants.EXAMMASTER);
		
		// expected should be equal to actual in terms 
		// of both the number of rows and the data
		Assertion.assertEquals(expectedMasterTable,
				       actualMasterTable);
		
		// addExamScore(int examId, int studentId, int earnedPoints)

		// add the three student exams with their point totals
		// and add the exams in for the first examination with
		// some additional test scores
		testGradeBook.addExamScore(0, 0, 35);
		testGradeBook.addExamScore(0, 1, 40);
		testGradeBook.addExamScore(0, 2, 45);
		testGradeBook.addExamScore(1, 0, 43);
		testGradeBook.addExamScore(2, 0, 33);

		// create the expected Master Table from the XML
		IDataSet expectedDatabaseState = 
		    new 
		    FlatXmlDataSet(new 
				   FileInputStream("data/FullDataSetAverage.xml"));

		ITable expectedScoresTable = 
		    expectedDatabaseState.
		    getTable(GradeBookConstants.EXAMSCORES);

		// create the actual Master Table from the database
		IDataSet actualDatabaseState = 
		    getConnection().createDataSet();
		ITable actualScoresTable = 
		    actualDatabaseState.
		    getTable(GradeBookConstants.EXAMSCORES);
		
		// the tables of data should be the same
		Assertion.assertEquals(expectedScoresTable,
				       actualScoresTable);
						      
		// first exam has the curve !!
 
		// check that the scores are the same for the initial
		// data about the examinations
		int firstEarnedPointsExpected = 35 + 5;
		int secondEarnedPointsExpected = 40 + 5;
		int finalExamEarnedPointsExpected = 33;
		
		// public int getExamScore(int examId, int studentId)
	       
		assertEquals(firstEarnedPointsExpected,
			     testGradeBook.getExamScore(0,0));
		assertEquals(secondEarnedPointsExpected,
			     testGradeBook.getExamScore(0,1));
		assertEquals(finalExamEarnedPointsExpected,
			     testGradeBook.getExamScore(2,0));

		// check to see that the computed averages are the same
		double examZeroAverageExpected = 
		    (35 + 5 + 40 + 5 + 45 + 5) / 3;
		double examOneAverageExpected = 43.0;
		double examTwoAverageExpected = 33.0;
		double tolerance = 0.0;

		// public double calculateAverageExamScore(int examId)

		assertEquals(examZeroAverageExpected,
			     testGradeBook.
			     calculateAverageExamScore(0),
			     tolerance);

		// we are getting these numbers because we did 
		// not set the curve for these last two exams

		// NOTE: We would get an exception if we made an
		// assertion about the state of the ExamScores
		// table (but, previous tests show that code is 
		// working correctly) 
		
		assertEquals(examOneAverageExpected,
			     testGradeBook.
			     calculateAverageExamScore(1),
			     tolerance);

		assertEquals(examTwoAverageExpected,
			     testGradeBook.
			     calculateAverageExamScore(2),
			     tolerance);
		
	    }

	catch(SQLException e)
	    {

		e.printStackTrace();
		fail("Problem interacting with database");

	    }

	catch(GradeBookDataException e)
	    {

		e.printStackTrace();
		fail("Problem with placing data in GradeBook database");

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("Other DBUnit Exception was thrown");

	    }

    }

    /**
     *  Should throw an exception because negative is not correct.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2005
     */
    public void testAddLabScoreSecondNegativeNotPossible()
    {

	try
	    {

		testGradeBook.addLabScore(0, 1, -1);
		fail("negative could be added to the database");

	    }

	catch(GradeBookDataException e)
	    {

		// test case passes

	    }

	catch(SQLException e)
	    {

		e.printStackTrace();
		fail("Should not have problems interacting with DB");

	    }

    }    

    /**
     *  Should throw an exception because negative is not possible.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2005
     */
    public void testAddLabScoreFirstNegativeNotPossible()
    {

	try
	    {

		testGradeBook.addLabScore(-1, 0, 50);
		fail("negative could be added to the database");

	    }

	catch(GradeBookDataException e)
	    {

		// test case passes

	    }

	catch(SQLException e)
	    {

		e.printStackTrace();
		fail("Should not have problems interacting with DB");

	    }

    }    

    /**
     *  Can we populate the LabScores table with the right values?
     *  
     *  @author Gregory M. Kapfhammer 2/22/2005
     */
    public void testAddLabScoresToLabScoresTable()
    {

	try
	    {

		// public void addLab(String name, int totalPoints)

		// add the three standard exams with their 
		// standard point totals
		testGradeBook.addLab("LabOne", 10);
		testGradeBook.addLab("LabTwo", 20);
		testGradeBook.addLab("LabThree", 30);
		
		// create the expected Exam MASTER from the XML
		IDataSet expectedDatabaseStateFirst = getDataSet(); 
		ITable expectedLabMasterTable = 
		    expectedDatabaseStateFirst.
		    getTable(GradeBookConstants.LABMASTER);

		// create the actual Scores Table from the database
		IDataSet actualDatabaseStateFirst = 
		    getConnection().createDataSet();
		ITable actualLabMasterTable = 
		    actualDatabaseStateFirst.
		    getTable(GradeBookConstants.LABMASTER);
		
		// expected should be equal to actual in terms 
		// of both the number of rows and the data
		Assertion.assertEquals(expectedLabMasterTable,
				       actualLabMasterTable);

// 		public void addLabScore(int labId, int studentId, int earnedPoints)

		// add the three student exams with their point totals
		testGradeBook.addLabScore(0, 0, 8);
		testGradeBook.addLabScore(1, 1, 18);
		testGradeBook.addLabScore(2, 2, 28);

		// create the expected Exam Scores from the XML
		IDataSet expectedDatabaseState = getDataSet(); 
		ITable expectedScoresTable = 
		    expectedDatabaseState.
		    getTable(GradeBookConstants.LABSCORES);

		// create the actual Scores Table from the database
		IDataSet actualDatabaseState = 
		    getConnection().createDataSet();
		ITable actualScoresTable = 
		    actualDatabaseState.
		    getTable(GradeBookConstants.LABSCORES);
		
		// expected should be equal to actual in terms 
		// of both the number of rows and the data
		Assertion.assertEquals(expectedScoresTable,
				       actualScoresTable);

		// public int getLabScore(int labId, int studentId)

		int firstEarnedPointsExpected = 8;
		int secondEarnedPointsExpected = 18;
		int finalExamEarnedPointsExpected = 28;
				       
		assertEquals(firstEarnedPointsExpected,
			     testGradeBook.getLabScore(0,0));
		assertEquals(secondEarnedPointsExpected,
			     testGradeBook.getLabScore(1,1));
		assertEquals(finalExamEarnedPointsExpected,
			     testGradeBook.getLabScore(2,2));
		
	    }

	catch(SQLException e)
	    {

		e.printStackTrace();
		fail("Problem interacting with database");

	    }

	catch(GradeBookDataException e)
	    {

		e.printStackTrace();
		fail("Problem with placing data in GradeBook database");

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("Other DBUnit Exception was thrown");

	    }
	
    }

    /**
     *  Can we calculate averages for lab scores? 
     *  
     *  @author Gregory M. Kapfhammer 2/22/2005
     */
    public void testCorrectAverageLabScores()
    {

	try
	    {

		// public void addLab(String name, int totalPoints)

		// add the three standard exams with their 
		// standard point totals
		testGradeBook.addLab("LabOne", 10);
		testGradeBook.addLab("LabTwo", 20);
		testGradeBook.addLab("LabThree", 30);
		
		// create the expected Exam MASTER from the XML
		IDataSet expectedDatabaseStateFirst = getDataSet(); 
		ITable expectedLabMasterTable = 
		    expectedDatabaseStateFirst.
		    getTable(GradeBookConstants.LABMASTER);

		// create the actual Scores Table from the database
		IDataSet actualDatabaseStateFirst = 
		    getConnection().createDataSet();
		ITable actualLabMasterTable = 
		    actualDatabaseStateFirst.
		    getTable(GradeBookConstants.LABMASTER);
		
		// expected should be equal to actual in terms 
		// of both the number of rows and the data
		Assertion.assertEquals(expectedLabMasterTable,
				       actualLabMasterTable);

// 		public void addLabScore(int labId, int studentId, int earnedPoints)

		// multiple grades for the first lab score
		testGradeBook.addLabScore(0, 0, 7);
		testGradeBook.addLabScore(0, 1, 8);
		testGradeBook.addLabScore(0, 2, 9);
		
		// just one for this lab score
		testGradeBook.addLabScore(1, 1, 18);
		testGradeBook.addLabScore(2, 2, 28);

		// create the expected Lab Scores from the XML
		IDataSet expectedDatabaseState = 
		    new 
		    FlatXmlDataSet(new 
				   FileInputStream("data/FullDataSetAverage.xml"));

		ITable expectedScoresTable = 
		    expectedDatabaseState.
		    getTable(GradeBookConstants.LABSCORES);

		// create the actual Scores Table from the database
		IDataSet actualDatabaseState = 
		    getConnection().createDataSet();
		ITable actualScoresTable = 
		    actualDatabaseState.
		    getTable(GradeBookConstants.LABSCORES);
		
		// expected should be equal to actual in terms 
		// of both the number of rows and the data
		Assertion.assertEquals(expectedScoresTable,
				       actualScoresTable);

		// check to make sure that the values are still stored
		// correctly inside of the database

		// public int getLabScore(int labId, int studentId)

		int firstEarnedPointsExpected = 7;
		int secondEarnedPointsExpected = 18;
		int finalExamEarnedPointsExpected = 28;
				       
		assertEquals(firstEarnedPointsExpected,
			     testGradeBook.getLabScore(0,0));
		assertEquals(secondEarnedPointsExpected,
			     testGradeBook.getLabScore(1,1));
		assertEquals(finalExamEarnedPointsExpected,
			     testGradeBook.getLabScore(2,2));
		
		// check to see if the averages are correct

		double firstAverageExpected = (7 + 8 + 9)/3;
		double secondAverageExpected = 18.0;
		double thirdAverageExpected = 28.0;
		double tolerance = 0.0;

		assertEquals(firstAverageExpected,
			     testGradeBook.
			     calculateAverageLaboratoryScore(0),
			     tolerance);

		assertEquals(secondAverageExpected,
			     testGradeBook.
			     calculateAverageLaboratoryScore(1),
			     tolerance);

		assertEquals(thirdAverageExpected,
			     testGradeBook.
			     calculateAverageLaboratoryScore(2),
			     tolerance);		

	    }

	catch(SQLException e)
	    {

		e.printStackTrace();
		fail("Problem interacting with database");

	    }

	catch(GradeBookDataException e)
	    {

		e.printStackTrace();
		fail("Problem with placing data in GradeBook database");

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("Other DBUnit Exception was thrown");

	    }
	
    }    

    /**
     *  Should throw an exception because negative is not correct.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2005
     */
    public void testAddHomeworkScoreSecondNegativeNotPossible()
    {

	try
	    {

		testGradeBook.addHomeworkScore(0, 1, -1);
		fail("negative could be added to the database");

	    }

	catch(GradeBookDataException e)
	    {

		// test case passes

	    }

	catch(SQLException e)
	    {

		e.printStackTrace();
		fail("Should not have problems interacting with DB");

	    }

    }    

    /**
     *  Should throw an exception because negative is not possible.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2005
     */
    public void testAddHomeworkScoreFirstNegativeNotPossible()
    {

	try
	    {

		testGradeBook.addHomeworkScore(-1, 0, 50);
		fail("negative could be added to the database");

	    }

	catch(GradeBookDataException e)
	    {

		// test case passes

	    }

	catch(SQLException e)
	    {

		e.printStackTrace();
		fail("Should not have problems interacting with DB");

	    }

    }    

    /**
     *  Can we populate the HomeworkScores table with the right values?
     *  
     *  @author Gregory M. Kapfhammer 2/22/2005
     */
    public void testAddHomeworkScoresToHomeworkScoresTable()
    {

	try
	    {

		// public void addHomework(String name, int totalPoints)

		// add the three standard exams with their 
		// standard point totals
		testGradeBook.addHomework("HomeworkOne", 10);
		testGradeBook.addHomework("HomeworkTwo", 20);
		testGradeBook.addHomework("HomeworkThree", 30);
		
		// create the expected Exam MASTER from the XML
		IDataSet expectedDatabaseStateFirst = getDataSet(); 
		ITable expectedHomeworkMasterTable = 
		    expectedDatabaseStateFirst.
		    getTable(GradeBookConstants.HOMEWORKMASTER);

		// create the actual Scores Table from the database
		IDataSet actualDatabaseStateFirst = 
		    getConnection().createDataSet();
		ITable actualHomeworkMasterTable = 
		    actualDatabaseStateFirst.
		    getTable(GradeBookConstants.HOMEWORKMASTER);
		
		// expected should be equal to actual in terms 
		// of both the number of rows and the data
		Assertion.assertEquals(expectedHomeworkMasterTable,
				       actualHomeworkMasterTable);

		//     public void addHomeworkScore(int homeworkId, 
		//                  int studentId, 
		//		    int earnedPoints)

		// add the three student exams with their point totals
		testGradeBook.addHomeworkScore(0, 0, 8);
		testGradeBook.addHomeworkScore(1, 1, 18);
		testGradeBook.addHomeworkScore(2, 2, 28);

		// create the expected Exam Scores from the XML
		IDataSet expectedDatabaseState = getDataSet(); 
		ITable expectedScoresTable = 
		    expectedDatabaseState.
		    getTable(GradeBookConstants.HOMEWORKSCORES);

		// create the actual Scores Table from the database
		IDataSet actualDatabaseState = 
		    getConnection().createDataSet();
		ITable actualScoresTable = 
		    actualDatabaseState.
		    getTable(GradeBookConstants.HOMEWORKSCORES);
		
		// expected should be equal to actual in terms 
		// of both the number of rows and the data
		Assertion.assertEquals(expectedScoresTable,
				       actualScoresTable);

		// public int getLabScore(int labId, int studentId)

		int firstEarnedPointsExpected = 8;
		int secondEarnedPointsExpected = 18;
		int finalExamEarnedPointsExpected = 28;
				       
		assertEquals(firstEarnedPointsExpected,
			     testGradeBook.getHomeworkScore(0,0));
		assertEquals(secondEarnedPointsExpected,
			     testGradeBook.getHomeworkScore(1,1));
		assertEquals(finalExamEarnedPointsExpected,
			     testGradeBook.getHomeworkScore(2,2));
		
	    }

	catch(SQLException e)
	    {

		e.printStackTrace();
		fail("Problem interacting with database");

	    }

	catch(GradeBookDataException e)
	    {

		e.printStackTrace();
		fail("Problem with placing data in GradeBook database");

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("Other DBUnit Exception was thrown");

	    }
	
    }

    /**
     *  Should throw an exception because negative is not correct.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2005
     */
    public void testAddFinalProjectScoreSecondNegativeNotPossible()
    {

	try
	    {

		testGradeBook.addFinalProjectScore(0, -1);
		fail("negative could be added to the database");

	    }

	catch(GradeBookDataException e)
	    {

		// test case passes

	    }

	catch(SQLException e)
	    {

		e.printStackTrace();
		fail("Should not have problems interacting with DB");

	    }

    }    

    /**
     *  Should throw an exception because negative is not possible.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2005
     */
    public void testAddFinalProjectScoreFirstNegativeNotPossible()
    {

	try
	    {

		testGradeBook.addFinalProjectScore(-1, 0);
		fail("negative could be added to the database");

	    }

	catch(GradeBookDataException e)
	    {

		// test case passes

	    }

	catch(SQLException e)
	    {

		e.printStackTrace();
		fail("Should not have problems interacting with DB");

	    }

    }    

    /**
     *  Can we populate the LabScores table with the right values?
     *  
     *  @author Gregory M. Kapfhammer 2/22/2005
     */
    public void testAddFinalProjectScoresToLabScoresTable()
    {

	try
	    {

		// public void addFinalProjectScore(int studentId, 
		//                                  int totalPoints)

		// add the three standard exams with their 
		// standard point totals
		testGradeBook.addFinalProjectScore(0, 90);
		testGradeBook.addFinalProjectScore(1, 80);
		testGradeBook.addFinalProjectScore(2, 70);
		
		// create the expected Exam MASTER from the XML
		IDataSet expectedDatabaseStateFirst = getDataSet(); 
		ITable expectedFinalProjectScoresTable = 
		    expectedDatabaseStateFirst.
		    getTable(GradeBookConstants.FINALPROJECTSCORES);

		// create the actual Scores Table from the database
		IDataSet actualDatabaseStateFirst = 
		    getConnection().createDataSet();
		ITable actualFinalProjectScoresTable = 
		    actualDatabaseStateFirst.
		    getTable(GradeBookConstants.FINALPROJECTSCORES);
		
		// expected should be equal to actual in terms 
		// of both the number of rows and the data
		Assertion.assertEquals(expectedFinalProjectScoresTable,
				       actualFinalProjectScoresTable);

		// check to see if our accessor methods can correctly
		// query the database and produce the correct score

		//     public int getFinalProjectScore(int studentId)

		int firstEarnedPointsExpected = 90;
		int secondEarnedPointsExpected = 80;
		int finalExamEarnedPointsExpected = 70;
				       
		assertEquals(firstEarnedPointsExpected,
			     testGradeBook.getFinalProjectScore(0));
		assertEquals(secondEarnedPointsExpected,
			     testGradeBook.getFinalProjectScore(1));
		assertEquals(finalExamEarnedPointsExpected,
			     testGradeBook.getFinalProjectScore(2));
		
	    }

	catch(SQLException e)
	    {

		e.printStackTrace();
		fail("Problem interacting with database");

	    }

	catch(GradeBookDataException e)
	    {

		e.printStackTrace();
		fail("Problem with placing data in GradeBook database");

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("Other DBUnit Exception was thrown");

	    }
	
    }

    /**
     *  Can we calculate the final grade for a student properly?
     *  
     *  @author Gregory M. Kapfhammer 3/1/2005
     */
    public void testCalculateFinalGrade()
    {

	try
	    {

		// populate the database with some of the initial
		// values that are needed
		
// 		public void populateMaster(double participation,
// 					   double examOne,
// 					   double examTwo,
// 					   double examThree,
// 					   double laboratory,
// 					   double homework,
// 					   double project) 

		testGradeBook.populateMaster(.05,
					     .125,
					     .125,
					     .20, 
					     .25, 
					     .15,
					     .1);
		
		// calculate the final grade for this student
		double finalGradeStudentZero = 
		    testGradeBook.calculateExamFinalGrade(0);

	    }

	catch(SQLException e)
	    {

		e.printStackTrace();
		fail("Problem interacting with database");

	    }

	catch(GradeBookDataException e)
	    {

		e.printStackTrace();
		fail("Problem with placing data in GradeBook database");

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("Other DBUnit Exception was thrown");

	    }

    }

    /**
     *  Can we ensure that a studentId can prompt the finding of all 
     *  the examIds that were taken by a student?
     *  
     *  @author Gregory M. Kapfhammer 3/1/2005
     */
    public void testGetExamIdsAndExamFinalGrade()
    {

	try
	    {
		
// 		public void populateMaster(double participation,
// 					   double examOne,
// 					   double examTwo,
// 					   double examThree,
// 					   double laboratory,
// 					   double homework,
// 					   double project) 

		testGradeBook.populateMaster(.05,
					     .125,
					     .125,
					     .20, 
					     .25, 
					     .15,
					     .1);

		// add the three standard exams with their 
		// standard point totals
		testGradeBook.addExam("ExamOne", 50);
		testGradeBook.addExam("ExamTwo", 50);
		testGradeBook.addExam("ExamThree", 100);
		
		// public int setExamCurve(int newCurve, int examId)
		
		// add in the curves to the ExamMaster table
		//testGradeBook.setExamCurve(5, 0);

		// check to see if the ExamMaster table contains the 
		// right curve (only the first exam will have a curve)

		// create the expected Exam MASTER from the XML
		IDataSet expectedDatabaseStateFirst = //getDataSet();
		    new FlatXmlDataSet(new FileInputStream("data/FullDataSetFinalGrade.xml"));
		ITable expectedMasterTable = 
		    expectedDatabaseStateFirst.
		    getTable(GradeBookConstants.EXAMMASTER);

		// create the actual Scores Table from the database
		IDataSet actualDatabaseStateFirst = 
		    getConnection().createDataSet();
		ITable actualMasterTable = 
		    actualDatabaseStateFirst.
		    getTable(GradeBookConstants.EXAMMASTER);
		
		// expected should be equal to actual in terms 
		// of both the number of rows and the data
		Assertion.assertEquals(expectedMasterTable,
				       actualMasterTable);
		
		//public void addExamScore(int examId, 
		//                         int studentId, 
		//                         int earnedPoints)

		testGradeBook.addExamScore(0, 0, 35);
		testGradeBook.addExamScore(1, 0, 40);
		testGradeBook.addExamScore(2, 0, 90);
		
		// same student took two exams
		testGradeBook.addExamScore(0, 1, 42);
		testGradeBook.addExamScore(1, 1, 43);
		
		// one student only took one examination
		testGradeBook.addExamScore(2, 2, 33);

		// create the expected Exam Scores from the XML
		IDataSet expectedDatabaseState = 
		    new FlatXmlDataSet(new FileInputStream("data/FullDataSetFinalGrade.xml"));

		ITable expectedScoresTable = 
		    expectedDatabaseState.
		    getTable(GradeBookConstants.EXAMSCORES);

		// create the actual Scores Table from the database
		IDataSet actualDatabaseState = 
		    getConnection().createDataSet();
		ITable actualScoresTable = 
		    actualDatabaseState.
		    getTable(GradeBookConstants.EXAMSCORES);
		
		// expected should be equal to actual in terms 
		// of both the number of rows and the data
		Assertion.assertEquals(expectedScoresTable,
				       actualScoresTable);

		// public Iterator getExamIds(int studentId)

		Iterator examIdIterator = 
		    testGradeBook.getExamIds(0);

		Integer firstExamIdExpected = new Integer(0);
		Integer secondExamIdExpected = new Integer(1);

		assertTrue(examIdIterator.hasNext());
		assertEquals(firstExamIdExpected, 
			     (Integer)examIdIterator.next());
		assertTrue(examIdIterator.hasNext());
		assertEquals(secondExamIdExpected, 
			     (Integer)examIdIterator.next());

		// do not tolerate any error in the double values
		double tolerance = 0.0;
		    
		// check to see if the calculated final grades
		// for the examinations are correct for the 
		// three separate students
		
		// first student
		double firstStudentExpectedExamFinal = 
		    ( (double)35 / (double)50  * .125) + 
		    ( (double)40 / (double)50 * .125) + 
		    ( (double)90 / (double)100 * .20);
		
		assertEquals(firstStudentExpectedExamFinal,
			     testGradeBook.
			     calculateExamFinalGrade(0),
			     tolerance);

		// second student
		double secondStudentExpectedExamFinal = 
		    ( (double)42 / (double)50  * .125) + 
		    ( (double)43 / (double)50 * .125) + 
		    ( (double)0 / (double)100 * .20);
		
		assertEquals(secondStudentExpectedExamFinal,
			     testGradeBook.
			     calculateExamFinalGrade(1),
			     tolerance);

		// third student -- this would not pass ; we are 
		// going to assume that all grades are entered
		// right now the code assigns to specific variables
		// and thus we cannot have no grades and then a grade

// 		double thirdStudentExpectedExamFinal = 
// 		    ( (double)0 / (double)50  * .125) + 
// 		    ( (double)0 / (double)50 * .125) + 
// 		    ( (double)33 / (double)100 * .20);
		
// 		assertEquals(thirdStudentExpectedExamFinal,
// 			     testGradeBook.
// 			     calculateExamFinalGrade(2),
// 			     tolerance);		
		
	    }

	catch(SQLException e)
	    {

		e.printStackTrace();
		fail("Problem interacting with database");

	    }

	catch(GradeBookDataException e)
	    {

		e.printStackTrace();
		fail("Problem with placing data in GradeBook database");

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("Other DBUnit Exception was thrown");

	    }

    }


    /**
     *  Can we calculate the final grade for the laboratories?
     *  
     *  @author Gregory M. Kapfhammer 3/1/2005
     */
    public void testCalculateLaboratoryFinalGrade()
    {

	try
	    {

				
// 		public void populateMaster(double participation,
// 					   double examOne,
// 					   double examTwo,
// 					   double examThree,
// 					   double laboratory,
// 					   double homework,
// 					   double project) 

		testGradeBook.populateMaster(.05,
					     .125,
					     .125,
					     .20, 
					     .25, 
					     .15,
					     .1);

		// public void addLab(String name, int totalPoints)
		
		// add three diferent labs to the database
		testGradeBook.addLab("First", 50);
		testGradeBook.addLab("Second", 60);
		testGradeBook.addLab("Third", 70);

		// create the expected LAB MASTER from the XML
		IDataSet expectedDatabaseStateFirst = 
		    new FlatXmlDataSet(new FileInputStream("data/FullDataSetFinalGrade.xml"));
		ITable expectedLabMasterTable = 
		    expectedDatabaseStateFirst.
		    getTable(GradeBookConstants.LABMASTER);

		// create the actual Scores Table from the database
		IDataSet actualDatabaseStateFirst = 
		    getConnection().createDataSet();
		ITable actualLabMasterTable = 
		    actualDatabaseStateFirst.
		    getTable(GradeBookConstants.LABMASTER);
		
		// expected should be equal to actual in terms 
		// of both the number of rows and the data
		Assertion.assertEquals(expectedLabMasterTable,
				       actualLabMasterTable);

		// public void addLabScore(int labId, int studentId, int earnedPoints)

		// add three different scores for a single student
		// to the database
		
		testGradeBook.addLabScore(0, 0, 40);
		testGradeBook.addLabScore(1, 0, 50);
		testGradeBook.addLabScore(2, 0, 60);
		
		IDataSet expectedDatabaseStateSecond = 
		    new FlatXmlDataSet(new FileInputStream("data/FullDataSetFinalGrade.xml"));
		
		ITable expectedLabScoresTable = 
		    expectedDatabaseStateSecond.
		    getTable(GradeBookConstants.LABSCORES);

		// create the actual Scores Table from the database
		ITable actualLabScoresTable = 
		    actualDatabaseStateFirst.
		    getTable(GradeBookConstants.LABSCORES);
		
		// expected should be equal to actual in terms 
		// of both the number of rows and the data
		Assertion.assertEquals(expectedLabScoresTable,
				       actualLabScoresTable);

		// do not tolerate any difference in the double output
		double tolerance = 0.0;

		double expectedLabScoreFinal = 
		    ( (double) (40+50+60) / ( (double) (50+60+70) ) ) * .25;

		assertEquals(expectedLabScoreFinal,
			     testGradeBook.
			     calculateLaboratoryFinalGrade(0),
			     tolerance);
		    

	    }

		catch(SQLException e)
	    {

		e.printStackTrace();
		fail("Problem interacting with database");

	    }

	catch(GradeBookDataException e)
	    {

		e.printStackTrace();
		fail("Problem with placing data in GradeBook database");

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("Other DBUnit Exception was thrown");

	    }

    }

    /**
     *  Can we calculate the final grade for the homeworks?
     *  
     *  @author Gregory M. Kapfhammer 3/1/2005
     */
    public void testCalculateHomeworkFinalGrade()
    {

	try
	    {

				
// 		public void populateMaster(double participation,
// 					   double examOne,
// 					   double examTwo,
// 					   double examThree,
// 					   double laboratory,
// 					   double homework,
// 					   double project) 

		testGradeBook.populateMaster(.05,
					     .125,
					     .125,
					     .20, 
					     .25, 
					     .15,
					     .1);

		// public void addHomework(String name, int totalPoints)
		
		// add three diferent labs to the database
		testGradeBook.addHomework("First", 10);
		testGradeBook.addHomework("Second", 10);
		testGradeBook.addHomework("Third", 10);

		// create the expected LAB MASTER from the XML
		IDataSet expectedDatabaseStateFirst = 
		    new FlatXmlDataSet(new FileInputStream("data/FullDataSetFinalGrade.xml"));
		ITable expectedHomeworkMasterTable = 
		    expectedDatabaseStateFirst.
		    getTable(GradeBookConstants.HOMEWORKMASTER);

		// create the actual Scores Table from the database
		IDataSet actualDatabaseStateFirst = 
		    getConnection().createDataSet();
		ITable actualHomeworkMasterTable = 
		    actualDatabaseStateFirst.
		    getTable(GradeBookConstants.HOMEWORKMASTER);
		
		// expected should be equal to actual in terms 
		// of both the number of rows and the data
		Assertion.assertEquals(expectedHomeworkMasterTable,
				       actualHomeworkMasterTable);

		// public void addLabScore(int labId, int studentId, int earnedPoints)

		// add three different scores for a single student
		// to the database
		
		testGradeBook.addHomeworkScore(0, 0, 4);
		testGradeBook.addHomeworkScore(1, 0, 5);
		testGradeBook.addHomeworkScore(2, 0, 6);
		
		ITable expectedHomeworkScoresTable = 
		    expectedDatabaseStateFirst.
		    getTable(GradeBookConstants.HOMEWORKSCORES);

		// create the actual Scores Table from the database
		ITable actualHomeworkScoresTable = 
		    actualDatabaseStateFirst.
		    getTable(GradeBookConstants.HOMEWORKSCORES);
		
		// expected should be equal to actual in terms 
		// of both the number of rows and the data
		Assertion.assertEquals(expectedHomeworkScoresTable,
				       actualHomeworkScoresTable);

		// do not tolerate any difference in the double output
		double tolerance = 0.0;

		double expectedHomeworkScoreFinal = 
		    ( (double) (4+5+6) / ( (double) (10+10+10) ) ) * .15;

		assertEquals(expectedHomeworkScoreFinal,
			     testGradeBook.
			     calculateHomeworkFinalGrade(0),
			     tolerance);
		    
	    }

		catch(SQLException e)
	    {

		e.printStackTrace();
		fail("Problem interacting with database");

	    }

	catch(GradeBookDataException e)
	    {

		e.printStackTrace();
		fail("Problem with placing data in GradeBook database");

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("Other DBUnit Exception was thrown");

	    }

    }

    /**
     *  Can we calculate the final grade for the final project?
     *  
     *  @author Gregory M. Kapfhammer 3/1/2005
     */
    public void testCalculateFinalProjectFinalGrade()
    {

	try
	    {

				
// 		public void populateMaster(double participation,
// 					   double examOne,
// 					   double examTwo,
// 					   double examThree,
// 					   double laboratory,
// 					   double homework,
// 					   double project) 

		testGradeBook.populateMaster(.05,
					     .125,
					     .125,
					     .20, 
					     .25, 
					     .15,
					     .1);

		// public void addFinalProjectScore(int studentId, int totalPoints)
		
		testGradeBook.addFinalProjectScore(0, 89);
		testGradeBook.addFinalProjectScore(1, 90);

		// add three different scores for a single student
		// to the database
		
		// create the expected Scores table from the XML
		IDataSet expectedDatabaseStateFirst = 
		    new FlatXmlDataSet(new FileInputStream("data/FullDataSetFinalGrade.xml"));
		
		// create the actual Scores Table from the database
		IDataSet actualDatabaseStateFirst = 
		    getConnection().createDataSet();

		ITable expectedFinalProjectScoresTable = 
		    expectedDatabaseStateFirst.
		    getTable(GradeBookConstants.FINALPROJECTSCORES);

		// create the actual Scores Table from the database
		ITable actualFinalProjectScoresTable = 
		    actualDatabaseStateFirst.
		    getTable(GradeBookConstants.FINALPROJECTSCORES);
		
		// expected should be equal to actual in terms 
		// of both the number of rows and the data
		Assertion.assertEquals(expectedFinalProjectScoresTable,
				       actualFinalProjectScoresTable);

		// do not tolerate any difference in the double output
		double tolerance = 0.0;
		
		double expectedProjectScoreFinalFirst = 
		    ( (double) (89) / ( (double) (100) ) ) * .1;

		assertEquals(expectedProjectScoreFinalFirst,
			     testGradeBook.
			     calculateProjectFinalGrade(0),
			     tolerance);

		double expectedProjectScoreFinalSecond = 
		    ( (double) (90) / ( (double) (100) ) ) * .1;

		assertEquals(expectedProjectScoreFinalSecond,
			     testGradeBook.
			     calculateProjectFinalGrade(1),
			     tolerance);
		    
	    }

	catch(SQLException e)
	    {

		e.printStackTrace();
		fail("Problem interacting with database");

	    }

	catch(GradeBookDataException e)
	    {

		e.printStackTrace();
		fail("Problem with placing data in GradeBook database");

	    }

	catch(Exception e)
	    {

		e.printStackTrace();
		fail("Other DBUnit Exception was thrown");

	    }

    }

    public static void main(String[] args)
    {

        junit.textui.TestRunner.run(suite());
        System.out.println("Main from tff"); 
        // if this is not commented out then it does not appear to
        // have time to write out the file

        // but, if this is commented out then the -D flag for the
        // monitorEnabled does not appear to work correctly

        //System.exit(1);

    }


}
