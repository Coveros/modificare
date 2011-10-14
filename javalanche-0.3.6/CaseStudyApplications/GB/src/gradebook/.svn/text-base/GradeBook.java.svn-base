/*---------------------------------------------------------------------
 *  File: $Id: GradeBook.java,v 1.17 2005/07/07 20:24:49 gkapfham Exp $   
 *  Version:  $Revision: 1.17 $
 *
 *  Project: DIATOMS, Database drIven Application Testing tOol ModuleS
 *
 *--------------------------------------------------------------------*/

package gradebook;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Iterator;

import java.io.FileInputStream;

/**
 *  Main functionality of the GradeBook.
 *
 *  @author Gregory M. Kapfhammer 2/19/2005
 */

public class GradeBook
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
    public GradeBook(DatabaseDescription describe)
    {

	databaseConnection = GradeBookCreator.
	    createDatabaseConnection(describe);

    }

    /**
     *  Place the correct value inside of the Master table for all
     *  of the percentages for the different grades.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2005
     */
    public void populateMaster(double participation,
			       double examOne,
			       double examTwo,
			       double examThree,
			       double laboratory,
			       double homework,
			       double project) 
	throws GradeBookDataException, SQLException
    {

	// the percentages do not add up to one and we should not
	// place this inside of the database
	if( (participation + examOne + examTwo + examThree +
	     laboratory + homework + project) != 1.0 )
	    {

		throw new GradeBookDataException("Master table data wrong");

	    }

	// the data is correct in terms of percentages and we should go
	// ahead and place this into the database
	Statement insertStatement = databaseConnection.createStatement();
	
	// execute an INSERT INTO statement
	insertStatement.executeUpdate(GradeBookConstants.INSERT + " " +
				      GradeBookConstants.INTO + " " + 
				      GradeBookConstants.MASTER + " " +
				      GradeBookConstants.VALUES + " " + 
				      "(" + GradeBookConstants.NULL + ", " + 
				      participation + ", " +
				      examOne + ", " +
				      examTwo + ", " +
				      examThree + ", " +
				      laboratory + ", " +
				      homework + "," +  
				      project + ")");

    }

    /**
     *  Add a new student to the Student table.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2004
     */
    public void addStudent(String firstName,
			   String lastName,
			   String email)
	throws GradeBookDataException, SQLException
    {

	// the percentages do not add up to one and we should not 
	// place this inside of the database 
	if( firstName == null || firstName == "" || 
	    lastName == null  || lastName == "" ||
	    email == null     || email == "" )
	    {

		throw new GradeBookDataException("Student data wrong");

	    }

	// the data is correct in terms of names and we should go
	// ahead and place this into the database
	Statement insertStatement = databaseConnection.createStatement();
	
	// execute an INSERT INTO statement
	insertStatement.executeUpdate(GradeBookConstants.INSERT + " " +
				      GradeBookConstants.INTO + " " + 
				      GradeBookConstants.STUDENT + " " +
				      GradeBookConstants.VALUES + " " + 
				      "(" + GradeBookConstants.NULL + ", '" + 
				      firstName + "', '" +
				      lastName + "', '" +
				      email + "')");

    }

    /**
     *  Retrieve the ID of a student that is already stored inside 
     *  of the database.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2004
     */
    public int getStudentID(String lastName)
	throws GradeBookDataException, SQLException
    {

	// error code indicates that we could not find an ID 
	// that matched correctly
	int studentID = -1;

	// cannot retrieve information about a student with
	// an underspecified lastName
	if( lastName == "" || lastName == null )
	    {

		throw new GradeBookDataException("Underspecified student");

	    }
	
	// the data is correct in terms of last name and we should go
	// ahead and place this into the database
	Statement selectStatement = databaseConnection.createStatement();

	ResultSet studentIdResultSet = 
	    selectStatement.
	    executeQuery(GradeBookConstants.SELECT + " " +
			 GradeBookConstants.STUDENT_ID + " " +
			 GradeBookConstants.FROM + " " + 
			 GradeBookConstants.STUDENT + " " + 
			 GradeBookConstants.WHERE + " " + 
			 GradeBookConstants.STUDENT_LASTNAME + " = '" +
			 lastName + "'");
					 
	// move the cursor in the ResultSet forward and then 
	// return the first one (there should be only one matching ID;
	// otherwise, we would just return the **last** matching ID
	while( studentIdResultSet.next() )
	    {

		studentID = studentIdResultSet.
		    getInt(GradeBookConstants.STUDENT_ID);

	    }

	return studentID;

    }

    /**
     *  Returns a formatted string of all students and their IDs.
     *  
     *  @author Gregory M. Kapfhammer 3/7/2005
     */
    public String getStudents()
	throws GradeBookDataException, SQLException
    {

	StringBuffer finalStudents = new StringBuffer();

	int currentStudentId = 0;
	String currentStudentLastName = "";

	// the data is correct in terms of last name and we should go
	// ahead and place this into the database
	Statement selectStatement = databaseConnection.createStatement();

	ResultSet studentResultSet = 
	    selectStatement.
	    executeQuery(GradeBookConstants.SELECT + " " +
			 GradeBookConstants.STUDENT_ID + ", " +
			 GradeBookConstants.STUDENT_LASTNAME + " " +  
			 GradeBookConstants.FROM + " " + 
			 GradeBookConstants.STUDENT); 
					 
	// move the cursor in the ResultSet forward and then 
	// return the first one (there should be only one matching ID;
	// otherwise, we would just return the **last** matching ID
	while( studentResultSet.next() )
	    {

		currentStudentId = studentResultSet.
		    getInt(GradeBookConstants.STUDENT_ID);

		currentStudentLastName = studentResultSet.
		    getString(GradeBookConstants.STUDENT_LASTNAME);

		finalStudents.append(currentStudentId + " " + 
				     currentStudentLastName + "\n");
		
	    }

	return finalStudents.toString();

    }

    /**
     *  Returns a formatted string of all exams and their IDs.
     *  
     *  @author Gregory M. Kapfhammer 3/7/2005
     */
    public String getExams()
	throws GradeBookDataException, SQLException
    {

	StringBuffer finalExams = new StringBuffer();

	int currentExamId = 0;
	int currentExamTotalPoints = 0;
	int currentExamCurve = 0;

	String currentExamName = "";

	// the data is correct in terms of last name and we should go
	// ahead and place this into the database
	Statement selectStatement = databaseConnection.createStatement();

	ResultSet examResultSet = 
	    selectStatement.
	    executeQuery(GradeBookConstants.SELECT + " " +
			 GradeBookConstants.EXAMMASTER_EXAMID + ", " +
			 GradeBookConstants.EXAMMASTER_NAME + ", " +
			 GradeBookConstants.EXAMMASTER_TOTALPOINTS + ", " +  
			 GradeBookConstants.EXAMMASTER_CURVE + " " +  
			 GradeBookConstants.FROM + " " + 
			 GradeBookConstants.EXAMMASTER); 
					 
	// move the cursor in the ResultSet forward and then 
	// return the first one (there should be only one matching ID;
	// otherwise, we would just return the **last** matching ID
	while( examResultSet.next() )
	    {

		currentExamId = examResultSet.
		    getInt(GradeBookConstants.EXAMMASTER_EXAMID);
		
		currentExamTotalPoints = examResultSet.
		    getInt(GradeBookConstants.EXAMMASTER_TOTALPOINTS);

		currentExamCurve = examResultSet.
		    getInt(GradeBookConstants.EXAMMASTER_CURVE);

		currentExamName = examResultSet.
		    getString(GradeBookConstants.EXAMMASTER_NAME);

		finalExams.append(currentExamId + " " +
				  currentExamName + " " + 
				  currentExamTotalPoints +  " " +
				  currentExamCurve + "\n");
		
	    }

	return finalExams.toString();

    }

    /**
     *  Returns a formatted string of all labs and their information.
     *  
     *  @author Gregory M. Kapfhammer 3/7/2005
     */
    public String getLaboratories()
	throws GradeBookDataException, SQLException
    {

	StringBuffer finalLabs = new StringBuffer();

	int currentLabId = 0;
	int currentLabTotalPoints = 0;
	String currentLabName = "";

	// the data is correct in terms of last name and we should go
	// ahead and place this into the database
	Statement selectStatement = databaseConnection.createStatement();

	ResultSet labResultSet = 
	    selectStatement.
	    executeQuery(GradeBookConstants.SELECT + " " +
			 GradeBookConstants.LABMASTER_LABID + ", " +
			 GradeBookConstants.LABMASTER_NAME + ", " +
			 GradeBookConstants.LABMASTER_TOTALPOINTS + " " +  
			 GradeBookConstants.FROM + " " + 
			 GradeBookConstants.LABMASTER); 
					 
	// move the cursor in the ResultSet forward and then 
	// return the first one (there should be only one matching ID;
	// otherwise, we would just return the **last** matching ID
	while( labResultSet.next() )
	    {

		currentLabId = labResultSet.
		    getInt(GradeBookConstants.LABMASTER_LABID);
		
		currentLabTotalPoints = labResultSet.
		    getInt(GradeBookConstants.LABMASTER_TOTALPOINTS);

		currentLabName = labResultSet.
		    getString(GradeBookConstants.LABMASTER_NAME);

		finalLabs.append(currentLabId + " " +
				 currentLabName + " " + 
				 currentLabTotalPoints +  "\n"); 
						
	    }

	return finalLabs.toString();

    }

    /**
     *  Places an examination inside of the ExamMaster table.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2004
     */
    public void addExam(String name, int totalPoints)
	throws GradeBookDataException, SQLException
    {

	// the percentages do not add up to one and we should not 
	// place this inside of the database 
	if( name == "" || name == null || totalPoints < 0 ) 
	    {

		throw new GradeBookDataException("ExamMaster data wrong");

	    }

	// the data is correct in terms of names and we should go
	// ahead and place this into the database
	Statement insertStatement = databaseConnection.createStatement();
	
	// assume that the curve is 0 by default and then allow it 
	// to be adjusted after the fact (need to manually inspect
	// the average in order to do this properly)

	// execute an INSERT INTO statement
	insertStatement.executeUpdate(GradeBookConstants.INSERT + " " +
				      GradeBookConstants.INTO + " " + 
				      GradeBookConstants.EXAMMASTER + " " +
				      GradeBookConstants.VALUES + " " + 
				      "(" + GradeBookConstants.NULL + ", '" + 
				      name + "', '" +
				      totalPoints + "', '" +
				      GradeBookConstants.INITIAL_CURVE + 
				      "')");

    }

    /**
     *  Places an examination inside of the LabMaster table.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2004
     */
    public void addLab(String name, int totalPoints)
	throws GradeBookDataException, SQLException
    {

	// the percentages do not add up to one and we should not 
	// place this inside of the database 
	if( name == "" || name == null || totalPoints < 0 ) 
	    {

		throw new GradeBookDataException("LabMaster data wrong");

	    }

	// the data is correct in terms of names and we should go
	// ahead and place this into the database
	Statement insertStatement = databaseConnection.createStatement();
	
	// execute an INSERT INTO statement
	insertStatement.executeUpdate(GradeBookConstants.INSERT + " " +
				      GradeBookConstants.INTO + " " + 
				      GradeBookConstants.LABMASTER + " " +
				      GradeBookConstants.VALUES + " " + 
				      "(" + GradeBookConstants.NULL + ", '" + 
				      name + "', '" +
				      totalPoints + "'" +
				      ")");

    }

    /**
     *  Places an examination inside of the HomeworkMaster table.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2004
     */
    public void addHomework(String name, int totalPoints)
	throws GradeBookDataException, SQLException
    {

	// the percentages do not add up to one and we should not 
	// place this inside of the database 
	if( name == "" || name == null || totalPoints < 0 ) 
	    {

		throw new GradeBookDataException("HomeworkMaster data wrong");

	    }

	// the data is correct in terms of names and we should go
	// ahead and place this into the database
	Statement insertStatement = databaseConnection.createStatement();
	
	// execute an INSERT INTO statement
	insertStatement.executeUpdate(GradeBookConstants.INSERT + " " +
				      GradeBookConstants.INTO + " " + 
				      GradeBookConstants.HOMEWORKMASTER + " " +
				      GradeBookConstants.VALUES + " " + 
				      "(" + GradeBookConstants.NULL + ", '" + 
				      name + "', '" +
				      totalPoints + "'" +
				      ")");

    }

    /**
     *  Places an examination inside of the ExamMaster table.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2004
     */
    public void addFinalProjectScore(int studentId, int totalPoints)
	throws GradeBookDataException, SQLException
    {

	// the percentages do not add up to one and we should not 
	// place this inside of the database 
	if( studentId < 0 || totalPoints < 0 ) 
	    {

		throw new GradeBookDataException("FinalProjectScores wrong");

	    }

	// the data is correct in terms of names and we should go
	// ahead and place this into the database
	Statement insertStatement = databaseConnection.createStatement();
	
	// execute an INSERT INTO statement
	insertStatement.executeUpdate(GradeBookConstants.INSERT + " " +
				      GradeBookConstants.INTO + " " + 
				      GradeBookConstants.FINALPROJECTSCORES 
				      + " " +
				      GradeBookConstants.VALUES + " " + 
				      "(" + studentId + ", '" + 
				      totalPoints + "')");

    }

    // QUESTION: Where do we store the total points for a final project?
    // ANSWER: This could potentially be a constant in the code.

    /**
     *  Retrieve the earned points of a final project.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2004
     */
    public int getFinalProjectScore(int studentId)
	throws GradeBookDataException, SQLException
    {

	// error code indicates that we could not find an ID 
	// that matched correctly
	int earnedPoints = -1;

	// cannot retrieve information about a student with
	// an underspecified lastName
	if( studentId < 0 )
	    {

		throw new GradeBookDataException("Wrong StudentId");

	    }
	
	// the data is correct in terms of last name and we should go
	// ahead and place this into the database
	Statement selectStatement = databaseConnection.createStatement();

	ResultSet finalProjectScoresResultSet = 
	    selectStatement.
	    executeQuery(GradeBookConstants.SELECT + " " +
			 GradeBookConstants.FINALPROJECTSCORES_EARNEDPOINTS 
			 + " " +
			 GradeBookConstants.FROM + " " + 
			 GradeBookConstants.FINALPROJECTSCORES + " " + 
			 GradeBookConstants.WHERE + " " + 
			 GradeBookConstants.FINALPROJECTSCORES_STUDENTID 
			 + " = '" +
			 studentId + "'");
					 
	// move the cursor in the ResultSet forward and then 
	// return the first one (there should be only one matching ID;
	// otherwise, we would just return the **last** matching ID
	while( finalProjectScoresResultSet.next() )
	    {

		earnedPoints = finalProjectScoresResultSet.
		    getInt(GradeBookConstants.
			   FINALPROJECTSCORES_EARNEDPOINTS);

	    }

	return earnedPoints;

    }

    /**
     *  Retrieve the total points of an examination.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2004
     */
    public int getExamTotalPoints(int examId)
	throws GradeBookDataException, SQLException
    {

	// error code indicates that we could not find an ID 
	// that matched correctly
	int totalPoints = -1;

	// cannot retrieve information about a student with
	// an underspecified lastName
	if( examId < 0 )
	    {

		throw new GradeBookDataException("Wrong ExamId");

	    }
	
	// the data is correct in terms of last name and we should go
	// ahead and place this into the database
	Statement selectStatement = databaseConnection.createStatement();

	ResultSet examMasterResultSet = 
	    selectStatement.
	    executeQuery(GradeBookConstants.SELECT + " " +
			 GradeBookConstants.EXAMMASTER_TOTALPOINTS + " " +
			 GradeBookConstants.FROM + " " + 
			 GradeBookConstants.EXAMMASTER + " " + 
			 GradeBookConstants.WHERE + " " + 
			 GradeBookConstants.EXAMMASTER_EXAMID + " = '" +
			  examId + "'");
					 
	// move the cursor in the ResultSet forward and then 
	// return the first one (there should be only one matching ID;
	// otherwise, we would just return the **last** matching ID
	while( examMasterResultSet.next() )
	    {

		totalPoints = examMasterResultSet.
		    getInt(GradeBookConstants.EXAMMASTER_TOTALPOINTS);

	    }

	return totalPoints;

    }

    /**
     *  Set the curve for a specific examination.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2004
     */
    public int setExamCurve(int newCurve, int examId)
	throws GradeBookDataException, SQLException
    {

	// error code indicates that we could not find an ID 
	// that matched correctly
	int curve = -1;

	// cannot retrieve information about a student with
	// an underspecified lastName
	if( examId < 0 )
	    {

		throw new GradeBookDataException("Wrong ExamId");

	    }

	// the data is correct in terms of last name and we should go
	// ahead and place this into the database
	Statement updateStatement = databaseConnection.createStatement();

	// the rowCount indicates how many rows were modified by the 
	// SQL UPDATE statement; it should always be just one row
	int rowCount = updateStatement.
	    executeUpdate(GradeBookConstants.UPDATE + " " +
			  GradeBookConstants.EXAMMASTER + " " +
			  GradeBookConstants.SET + " " + 
			  GradeBookConstants.EXAMMASTER_CURVE + " = " +
			  newCurve + " " + 
			  GradeBookConstants.WHERE + " " + 
			  GradeBookConstants.EXAMMASTER_EXAMID + " = '" +
			  examId + "'");
					 
	return rowCount;	

    }

    /**
     *  Retrieve the curve of an examination.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2004
     */
    public int getExamCurve(int examId)
	throws GradeBookDataException, SQLException
    {

	// error code indicates that we could not find an ID 
	// that matched correctly
	int curve = -1;

	// cannot retrieve information about a student with
	// an underspecified lastName
	if( examId < 0 )
	    {

		throw new GradeBookDataException("Wrong ExamId");

	    }
	
		// the data is correct in terms of last name and we should go
	// ahead and place this into the database
	Statement selectStatement = databaseConnection.createStatement();

	ResultSet examMasterResultSet = 
	    selectStatement.
	    executeQuery(GradeBookConstants.SELECT + " " +
			 GradeBookConstants.EXAMMASTER_CURVE + " " +
			 GradeBookConstants.FROM + " " + 
			 GradeBookConstants.EXAMMASTER + " " + 
			 GradeBookConstants.WHERE + " " + 
			 GradeBookConstants.EXAMMASTER_EXAMID + " = '" +
			  examId + "'");
					 
	// move the cursor in the ResultSet forward and then 
	// return the first one (there should be only one matching ID;
	// otherwise, we would just return the **last** matching ID
	while( examMasterResultSet.next() )
	    {

		curve = examMasterResultSet.
		    getInt(GradeBookConstants.EXAMMASTER_CURVE);

	    }

	return curve;

    }

    /**
     *  Places an examination inside of the ExamScores table.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2004
     */
    public void addExamScore(int examId, int studentId, int earnedPoints)
	throws GradeBookDataException, SQLException
    {

	// the percentages do not add up to one and we should not 
	// place this inside of the database 
	if( examId < 0 || studentId < 0 || earnedPoints < 0 ) 
	    {

		throw new GradeBookDataException("ExamScore data wrong");

	    }

	// the data is correct in terms of names and we should go
	// ahead and place this into the database
	Statement insertStatement = databaseConnection.createStatement();
	
	// execute an INSERT INTO statement
	insertStatement.executeUpdate(GradeBookConstants.INSERT + " " +
				      GradeBookConstants.INTO + " " + 
				      GradeBookConstants.EXAMSCORES + " " +
				      GradeBookConstants.VALUES + " " + 
				      "('" + examId + "', '" + 
				      studentId + "', '" +
				      earnedPoints + 
				      "')");

    }

    /**
     *  Places an examination inside of the LabScores table.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2004
     */
    public void addLabScore(int labId, int studentId, int earnedPoints)
	throws GradeBookDataException, SQLException
    {

	// error checking for a valid LabScore addition
	if( labId < 0 || studentId < 0 || earnedPoints < 0 ) 
	    {

		throw new GradeBookDataException("LabScore data wrong");

	    }

	// the data is correct in terms of names and we should go
	// ahead and place this into the database
	Statement insertStatement = databaseConnection.createStatement();
	
	// execute an INSERT INTO statement
	insertStatement.executeUpdate(GradeBookConstants.INSERT + " " +
				      GradeBookConstants.INTO + " " + 
				      GradeBookConstants.LABSCORES + " " +
				      GradeBookConstants.VALUES + " " + 
				      "('" + labId + "', '" + 
				      studentId + "', '" +
				      earnedPoints + 
				      "')");

    }

    /**
     *  Places an examination inside of the HomeworkScores table.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2004
     */
    public void addHomeworkScore(int homeworkId, int studentId, 
				 int earnedPoints)
	throws GradeBookDataException, SQLException
    {

	// error checking for a valid LabScore addition
	if( homeworkId < 0 || studentId < 0 || earnedPoints < 0 ) 
	    {

		throw new GradeBookDataException("LabScore data wrong");

	    }

	// the data is correct in terms of names and we should go
	// ahead and place this into the database
	Statement insertStatement = databaseConnection.createStatement();
	
	// execute an INSERT INTO statement
	insertStatement.executeUpdate(GradeBookConstants.INSERT + " " +
				      GradeBookConstants.INTO + " " + 
				      GradeBookConstants.HOMEWORKSCORES + " " +
				      GradeBookConstants.VALUES + " " + 
				      "('" + homeworkId + "', '" + 
				      studentId + "', '" +
				      earnedPoints + 
				      "')");

    }

    /**
     *  Retrieves all of the ExamIds for a given StudentId (i.e., 
     *  we need to know which examinations a student actually took).
     *  
     *  @author Gregory M. Kapfhammer 3/1/2005
     */
    public Iterator getExamIds(int studentId)
	throws GradeBookDataException, SQLException
    {

	// store all of the ExamIds as Integers inside of this
	// ArrayList and then return the extracted iterator
	ArrayList examIdList = new ArrayList();

	// cannot retrieve information about a student with
	// an underspecified studentId
	if( studentId < 0 )
	    {

		throw new GradeBookDataException("Wrong ExamId");

	    }
	
	// the data is correct and we should go ahead and query database
	Statement selectStatement = databaseConnection.createStatement();

	ResultSet examIdsResultSet = 
	    selectStatement.
	    executeQuery(GradeBookConstants.SELECT + " " +
			 GradeBookConstants.EXAMSCORES_EXAMID + " " +
			 GradeBookConstants.FROM + " " + 
			 GradeBookConstants.EXAMSCORES + " " + 
			 GradeBookConstants.WHERE + " " + 
			 GradeBookConstants.EXAMSCORES_STUDENTID + " = '" +
			  studentId + "'");
					 
	// actually go through all of the rows in the result set ;
	// normally, there will be three separate rows that we need 
	// to extract the ExamId from and then place inside of the 
	// ArrayList for storage
	while( examIdsResultSet.next() )
	    {

		// extract the ExamId from result set
		int currentExamId = examIdsResultSet.
		    getInt(GradeBookConstants.EXAMSCORES_EXAMID);

		// create a reference wrapper so that we can place
		// this inside of the ArrayList
		Integer currentExamIdRef = 
		    new Integer(currentExamId);
		examIdList.add(currentExamIdRef);

	    }

	return examIdList.iterator();

    }

    
    /**
     *  Retrieve the score for a specific student's specific examination.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2004
     */
    public int getExamScore(int examId, int studentId)
	throws GradeBookDataException, SQLException
    {

	// error code indicates that we could not find an ID 
	// that matched correctly
	int earnedPoints = -1;

	// the curve points are assumed to be zero unless stated
	// otherwise (stored this way in the database as well)
	int curvePoints = 0;

	// cannot retrieve information about a student with
	// an underspecified lastName
	if( studentId < 0 || examId < 0 )
	    {

		throw new GradeBookDataException("Underspecified Exam Score");

	    }
	
	// the data is correct in terms of last name and we should go
	// ahead and place this into the database
	Statement selectStatement = databaseConnection.createStatement();

	ResultSet examScoresResultSet = 
	    selectStatement.
	    executeQuery(GradeBookConstants.SELECT + " " +
			 GradeBookConstants.EXAMSCORES_EARNEDPOINTS + " " +
			 GradeBookConstants.FROM + " " + 
			 GradeBookConstants.EXAMSCORES + " " + 
			 GradeBookConstants.WHERE + " " + 
			 GradeBookConstants.EXAMSCORES_EXAMID + " = '" +
			  examId + "' " + 
			 GradeBookConstants.AND + " " +
			 GradeBookConstants.EXAMSCORES_STUDENTID + " = '" +
			  studentId + "'");

	// extract the curve for the specific examination in question
	// (reuse the same select statement)
	ResultSet examCurveResultSet = 
	    selectStatement.
	    executeQuery(GradeBookConstants.SELECT + " " + 
			 GradeBookConstants.EXAMMASTER_CURVE + " " +
			 GradeBookConstants.FROM + " " +
			 GradeBookConstants.EXAMMASTER + " " +
			 GradeBookConstants.WHERE + " " + 
			 GradeBookConstants.EXAMMASTER_EXAMID + " = '" +
			 examId + "'");

	// find the curve in the result set first so that we can
	// add it to the student's earned points
	while( examCurveResultSet.next() )
	    {

		curvePoints = examCurveResultSet.
		    getInt(GradeBookConstants.EXAMMASTER_CURVE);

	    }
					 
	// move the cursor in the ResultSet forward and then 
	// return the first one (there should be only one matching ID;
	// otherwise, we would just return the **last** matching ID
	while( examScoresResultSet.next() )
	    {

		earnedPoints = examScoresResultSet.
		    getInt(GradeBookConstants.EXAMSCORES_EARNEDPOINTS);

	    }
	
	System.out.println("****Curve points: " + curvePoints);

	// return how many points the student earned and
	// the curve that was set for the examination 
	return (earnedPoints + curvePoints);

    }

    /**
     *  Retrieve the score for a specific student's specific laboratory.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2004
     */
    public int getLabScore(int labId, int studentId)
	throws GradeBookDataException, SQLException
    {

	// error code indicates that we could not find an ID 
	// that matched correctly
	int earnedPoints = -1;

	// cannot retrieve information about a student with
	// an underspecified lastName
	if( studentId < 0 || labId < 0 )
	    {

		throw new GradeBookDataException("Underspecified Lab Score");

	    }
	
	// the data is correct in terms of last name and we should go
	// ahead and place this into the database
	Statement selectStatement = databaseConnection.createStatement();

	ResultSet labScoresResultSet = 
	    selectStatement.
	    executeQuery(GradeBookConstants.SELECT + " " +
			 GradeBookConstants.LABSCORES_EARNEDPOINTS + " " +
			 GradeBookConstants.FROM + " " + 
			 GradeBookConstants.LABSCORES + " " + 
			 GradeBookConstants.WHERE + " " + 
			 GradeBookConstants.LABSCORES_LABID + " = '" +
			  labId + "' " + 
			 GradeBookConstants.AND + " " +
			 GradeBookConstants.LABSCORES_STUDENTID + " = '" +
			  studentId + "'");

	// move the cursor in the ResultSet forward and then 
	// return the first one (there should be only one matching ID;
	// otherwise, we would just return the **last** matching ID
	while( labScoresResultSet.next() )
	    {

		earnedPoints = labScoresResultSet.
		    getInt(GradeBookConstants.LABSCORES_EARNEDPOINTS);

	    }
	
	// return how many points the student earned and
	// the curve that was set for the examination 
	return (earnedPoints);

    }

    /**
     *  Retrieve the score for a specific student's specific homework.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2004
     */
    public int getHomeworkScore(int homeworkId, int studentId)
	throws GradeBookDataException, SQLException
    {

	// error code indicates that we could not find an ID 
	// that matched correctly
	int earnedPoints = -1;

	// cannot retrieve information about a student with
	// an underspecified lastName
	if( studentId < 0 || homeworkId < 0 )
	    {

		throw 
		    new GradeBookDataException("Underspecified homework Score");

	    }
	
	// the data is correct in terms of last name and we should go
	// ahead and place this into the database
	Statement selectStatement = databaseConnection.createStatement();

	ResultSet homeworkScoresResultSet = 
	    selectStatement.
	    executeQuery(GradeBookConstants.SELECT + " " +
			 GradeBookConstants.HOMEWORKSCORES_EARNEDPOINTS + " " +
			 GradeBookConstants.FROM + " " + 
			 GradeBookConstants.HOMEWORKSCORES + " " + 
			 GradeBookConstants.WHERE + " " + 
			 GradeBookConstants.HOMEWORKSCORES_HOMEWORKID + 
			 " = '" +
			  homeworkId + "' " + 
			 GradeBookConstants.AND + " " +
			 GradeBookConstants.HOMEWORKSCORES_STUDENTID + " = '" +
			  studentId + "'");

	// move the cursor in the ResultSet forward and then 
	// return the first one (there should be only one matching ID;
	// otherwise, we would just return the **last** matching ID
	while( homeworkScoresResultSet.next() )
	    {

		earnedPoints = homeworkScoresResultSet.
		    getInt(GradeBookConstants.HOMEWORKSCORES_EARNEDPOINTS);

	    }
	
	// return how many points the student earned and
	// the curve that was set for the examination 
	return (earnedPoints);

    }

    /**
     *  Calculates the average grade for a examination.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2004
     */
    public double calculateAverageExamScore(int examId)
	throws GradeBookDataException, SQLException
    {

	// keep track of the total number of points, the total 
	// number of exams, and then the average for all exams
	int totalPoints = 0;
	int totalExams = 0;
	double examAverage = -1.0;

	// the curve points are assumed to be zero unless stated
	// otherwise (stored this way in the database as well)
	int curvePoints = 0;

	// cannot retrieve information about an examination
	// that is not inside of the database
	if( examId < 0 )
	    {

		throw new GradeBookDataException("Underspecified Exam Score");

	    }
	
	// create a select statement that will allow us to extract the
	// curve from the ExamMaster table
	Statement selectStatement = databaseConnection.createStatement();

	// extract the curve for the specific examination in question
	// (reuse the same select statement)
	ResultSet examCurveResultSet = 
	    selectStatement.
	    executeQuery(GradeBookConstants.SELECT + " " + 
			 GradeBookConstants.EXAMMASTER_CURVE + " " +
			 GradeBookConstants.FROM + " " +
			 GradeBookConstants.EXAMMASTER + " " +
			 GradeBookConstants.WHERE + " " + 
			 GradeBookConstants.EXAMMASTER_EXAMID + " = '" +
			 examId + "'");

	// find the curve in the result set first so that we can
	// add it to the student's earned points
	while( examCurveResultSet.next() )
	    {

		curvePoints = examCurveResultSet.
		    getInt(GradeBookConstants.EXAMMASTER_CURVE);

	    }

	// the data is correct in terms of last name and we should go
	// ahead and place this into the database
	//selectStatement = databaseConnection.createStatement();

	ResultSet examScoresResultSet = 
	    selectStatement.
	    executeQuery(GradeBookConstants.SELECT + " " +
			 GradeBookConstants.EXAMSCORES_EARNEDPOINTS + " " +
			 GradeBookConstants.FROM + " " + 
			 GradeBookConstants.EXAMSCORES + " " + 
			 GradeBookConstants.WHERE + " " + 
			 GradeBookConstants.EXAMSCORES_EXAMID + " = '" +
			  examId + "'");
		
	// NOTE: The curve must be added onto the grade for each of
	// the examinations (we have previously calculated the curve)
			 
	// move the cursor in the ResultSet forward and then 
	// return the first one (there should be only one matching ID;
	// otherwise, we would just return the **last** matching ID
	while( examScoresResultSet.next() )
	    {

		int earnedPoints = examScoresResultSet.
		    getInt(GradeBookConstants.EXAMSCORES_EARNEDPOINTS);

		totalPoints = totalPoints + earnedPoints + curvePoints;
		totalExams++;

	    }

	// compute the average and return it to the client (this
	// value includes the curve)
	return ((double)totalPoints/totalExams);

    }

    /**
     *  Calculates the average grade for a laboratory assignment.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2004
     */
    public double calculateAverageLaboratoryScore(int labId)
	throws GradeBookDataException, SQLException
    {

	// keep track of the total number of points, the total 
	// number of exams, and then the average for all exams
	int totalPoints = 0;
	int totalLabs = 0;
	double labAverage = -1.0;

	// cannot retrieve information about an examination
	// that is not inside of the database
	if( labId < 0 )
	    {

		throw new GradeBookDataException("Underspecified Lab Score");

	    }
	
	// the data is correct in terms of last name and we should go
	// ahead and place this into the database
	Statement selectStatement = databaseConnection.createStatement();

	ResultSet labScoresResultSet = 
	    selectStatement.
	    executeQuery(GradeBookConstants.SELECT + " " +
			 GradeBookConstants.LABSCORES_EARNEDPOINTS + " " +
			 GradeBookConstants.FROM + " " + 
			 GradeBookConstants.LABSCORES + " " + 
			 GradeBookConstants.WHERE + " " + 
			 GradeBookConstants.LABSCORES_LABID + " = '" +
			 labId + "'");
		
	// return the first one (there should be only one matching ID;
	// otherwise, we would just return the **last** matching ID
	while( labScoresResultSet.next() )
	    {

		int earnedPoints = labScoresResultSet.
		    getInt(GradeBookConstants.LABSCORES_EARNEDPOINTS);

		totalPoints = totalPoints + earnedPoints;
		totalLabs++;

	    }

	// compute the average and return it to the client 
	return ((double)totalPoints/totalLabs);

    }

    /**
     *  Calculate the final grade for a student's examinations.
     *  
     *  @author Gregory M. Kapfhammer 2/22/2005
     */
    public double calculateExamFinalGrade(int studentId)
	throws GradeBookDataException, SQLException
    {

	// the default grade is a 0.0 if a calculation fails.
	double examFinalGrade = 0.0;

	// all of the percentages from the Master table
	double examOne = 0.0;
	double examTwo = 0.0;
	double examThree = 0.0;

	// all of the totalPoints for each of the categories
	// need to have a separate variable for each due to the 
	// fact that you can assign different percentages to 
	// the different examination

	// examinations : total and earned points and final grade
	int examOneTotalPoints = 0;
	int examTwoTotalPoints = 0;
	int examThreeTotalPoints = 0;

	int examOneEarnedPoints = 0;
	int examTwoEarnedPoints = 0;
	int examThreeEarnedPoints = 0;

	double examOneFinal = 0.0;
	double examTwoFinal = 0.0;
	double examThreeFinal = 0.0;
		
	// check to make sure that we have a valid student ID
	if( studentId < 0 )
	    {

		throw new GradeBookDataException("Incorrect Student Id");

	    }

	// it's a valid student and thus we can go ahead and query
	// for the percentages from the master table
	Statement selectStatement = databaseConnection.createStatement();

	// extract all the percentages that we need in order 
	// to be able to calculate the final grade
	
	// note that there is **NO** WHERE clause for this select
	// statement since we simply need all of the percentages and
	// there is only supposed to be one row inside of this table
	// (this invariant is not currently enforced in the code)
	ResultSet masterResultSet = 
	    selectStatement.
	    executeQuery(GradeBookConstants.SELECT + " " +
			 GradeBookConstants.MASTER_PARTICIPATION + ", " +
			 GradeBookConstants.MASTER_EXAMONE + ", " +
			 GradeBookConstants.MASTER_EXAMTWO + ", " +
			 GradeBookConstants.MASTER_EXAMTHREE + " " +
			 GradeBookConstants.FROM + " " + 
			 GradeBookConstants.MASTER + " "); 
	
	// extract all of the percentages from the masterResultSet
	// all of the values can be extracted during the first run
	// through the while loop (should be only one row)
	while( masterResultSet.next() )
	    {

		examOne = masterResultSet.
		    getDouble(GradeBookConstants.
			      MASTER_EXAMONE);

		examTwo = masterResultSet.
		    getDouble(GradeBookConstants.
			      MASTER_EXAMTWO);

		examThree = masterResultSet.
		    getDouble(GradeBookConstants.
			      MASTER_EXAMTHREE);

	    }

	// find the examinations that the student actually took
	Iterator examIdsIterator = 
	    getExamIds(studentId);

	// counter so that we can know which test we are looking at
	int whichId = 0;

	// go through all of the specific ExamIds and (1) extract the
	// total points from the ExamMaster table, (2) extract the 
	// earned points from the ExamScores table
	while( examIdsIterator.hasNext() )
	    {

		Integer currentExamIdRef = 
		    (Integer)examIdsIterator.next();
		int currentExamId = currentExamIdRef.intValue();

		// calculate grade for exam one
		//if( whichId == 0 )
		if( currentExamId == whichId && whichId == 0)
		{

			examOneTotalPoints = 
			    getExamTotalPoints(currentExamId);

			examOneEarnedPoints = 
			    getExamScore(currentExamId,
					 studentId);

			if( examOneTotalPoints != 0 )
			    {

				examOneFinal = 
				    (double) examOneEarnedPoints / 
				    (double) examOneTotalPoints;

			    }

		    }

		// calculate grade for exam two
		else if( currentExamId == whichId && whichId == 1 )
		    {
			
			examTwoTotalPoints = 
			    getExamTotalPoints(currentExamId);

			examTwoEarnedPoints = 
			    getExamScore(currentExamId,
					 studentId);

			if( examTwoTotalPoints != 0 )
			    {

				examTwoFinal = 
				    (double) examTwoEarnedPoints / 
				    (double) examTwoTotalPoints;

			    }

		    }

		// calculate grade for exam three
		else if( currentExamId == whichId && whichId == 2 )
		    {

			System.out.println("currentExamId: " + 
					   currentExamId);

			System.out.println("whichId: " + whichId);
			
			examThreeTotalPoints = 
			    getExamTotalPoints(currentExamId);

			examThreeEarnedPoints = 
			    getExamScore(currentExamId,
					 studentId);

			System.out.println("examThreeTotalPoints: " + 
					   examThreeTotalPoints);

			System.out.println("examThreeEarnedPoints: " + 
					   examThreeEarnedPoints);

			if( examThreeTotalPoints != 0 )
			    {

				examThreeFinal = 
				    (double) examThreeEarnedPoints / 
				    (double) examThreeTotalPoints;

			    }

		    }

		// go to the next examination Id
		whichId++;

	    }

	System.out.println("ExamOneFinal: " + examOneFinal);
	System.out.println("ExamTwoFinal: " + examTwoFinal);
	System.out.println("ExamThreeFinal: " + examThreeFinal);
	
	System.out.println("ExamOne: " + examOne);
	System.out.println("ExamTwo: " + examTwo);
	System.out.println("ExamThree: " + examThree);

	// calculate the final exam grade
	examFinalGrade = examOneFinal*examOne +	
	    examTwoFinal*examTwo + examThreeFinal*examThree;
	return examFinalGrade;

    }

    /**
     *  Calculates the final grade for the laboratories.  Calculates the 
     *  grade for ALL of the laboratories that are currently inside of
     *  the LabMaster table (not just the ones for which a student has 
     *  a grade entered).
     *  
     *  @author Gregory M. Kapfhammer 3/1/2005
     */
    public double calculateLaboratoryFinalGrade(int studentId)
	throws GradeBookDataException, SQLException
    {

	// the grade for the laboratory is easier because there
	// is just one percentage that we need to extract from
	// the Master table
	double labPercentage = 0.0;
	double labFinalGrade = 0.0;
	int labTotalPoints = 0;
	int labEarnedPoints = 0;

	// check to make sure that we have a valid student ID
	if( studentId < 0 )
	    {

		throw new GradeBookDataException("Incorrect Student Id");

	    }

	// it's a valid student and thus we can go ahead and query
	// for the percentages from the master table
	Statement selectStatement = databaseConnection.createStatement();

	// extract all the percentages that we need in order 
	// to be able to calculate the final grade
	
	// note that there is **NO** WHERE clause for this select
	// statement since we simply need all of the percentages and
	// there is only supposed to be one row inside of this table
	// (this invariant is not currently enforced in the code)
	ResultSet masterResultSet = 
	    selectStatement.
	    executeQuery(GradeBookConstants.SELECT + " " +
			 GradeBookConstants.MASTER_LABORATORY + " " +
			 GradeBookConstants.FROM + " " + 
			 GradeBookConstants.MASTER); 
	
	// extract all of the percentages from the masterResultSet
	// all of the values can be extracted during the first run
	// through the while loop (should be only one row)
	while( masterResultSet.next() )
	    {

		labPercentage = masterResultSet.
		    getDouble(GradeBookConstants.
			      MASTER_LABORATORY);

	    }


	// find out the total number of total points for all labs

	ResultSet labTotalPointsResultSet = 
	    selectStatement.
	    executeQuery(GradeBookConstants.SELECT + " " +
			 GradeBookConstants.LABMASTER_TOTALPOINTS + " " + 
			 GradeBookConstants.FROM + " " +
			 GradeBookConstants.LABMASTER);

	// extract all of the total points from the result set 
	// and keep adding them to our runing total
	while( labTotalPointsResultSet.next() )
	    {

		labTotalPoints += labTotalPointsResultSet.
		    getInt(GradeBookConstants.LABMASTER_TOTALPOINTS);

	    }

	// find out the earned number of points for all labs for 
	// the specified student

	ResultSet labEarnedPointsResultSet = 
	    selectStatement.
	    executeQuery(GradeBookConstants.SELECT + " " +
			 GradeBookConstants.LABSCORES_EARNEDPOINTS + " " +
			 GradeBookConstants.FROM + " " +
			 GradeBookConstants.LABSCORES + " " + 
			 GradeBookConstants.WHERE + " " +
			 GradeBookConstants.LABSCORES_STUDENTID + " = " + 
			 studentId);

	// extract all of the total points from the result set 
	// and keep adding them to our runing total
	while( labEarnedPointsResultSet.next() )
	    {

		labEarnedPoints += labEarnedPointsResultSet.
		    getInt(GradeBookConstants.LABSCORES_EARNEDPOINTS);

	    }

// 	System.out.println("LabEarnedPoints: " + labEarnedPoints);
// 	System.out.println("LabTotalPoints: " + labTotalPoints);
// 	System.out.println("LabPercentage: " + labPercentage);

	// calculate the final laboratory grade
	labFinalGrade = ( (double)labEarnedPoints / (double)labTotalPoints ) *
	    labPercentage;	    

	System.out.println("LabFinalGrade: " + labFinalGrade);

	return labFinalGrade;

    }    

    /**
     *  Calculates the final grade for the homework.
     *  
     *  @author Gregory M. Kapfhammer 3/1/2005
     */
    public double calculateHomeworkFinalGrade(int studentId)
	throws GradeBookDataException, SQLException
    {

	// the grade for the homework is easier because there
	// is just one percentage that we need to extract from
	// the Master table
	double hwPercentage = 0.0;
	double hwFinalGrade = 0.0;
	int hwTotalPoints = 0;
	int hwEarnedPoints = 0;

	// check to make sure that we have a valid student ID
	if( studentId < 0 )
	    {

		throw new GradeBookDataException("Incorrect Student Id");

	    }

	// it's a valid student and thus we can go ahead and query
	// for the percentages from the master table
	Statement selectStatement = databaseConnection.createStatement();

	// extract all the percentages that we need in order 
	// to be able to calculate the final grade
	
	// note that there is **NO** WHERE clause for this select
	// statement since we simply need all of the percentages and
	// there is only supposed to be one row inside of this table
	// (this invariant is not currently enforced in the code)
	ResultSet masterResultSet = 
	    selectStatement.
	    executeQuery(GradeBookConstants.SELECT + " " +
			 GradeBookConstants.MASTER_HOMEWORK + " " +
			 GradeBookConstants.FROM + " " + 
			 GradeBookConstants.MASTER); 
	
	// extract all of the percentages from the masterResultSet
	// all of the values can be extracted during the first run
	// through the while loop (should be only one row)
	while( masterResultSet.next() )
	    {

		hwPercentage = masterResultSet.
		    getDouble(GradeBookConstants.
			      MASTER_HOMEWORK);

	    }


	// find out the earned number of points for all labs for 
	// the specified student

	ResultSet hwEarnedPointsResultSet = 
	    selectStatement.
	    executeQuery(GradeBookConstants.SELECT + " " +
			 GradeBookConstants.HOMEWORKSCORES_EARNEDPOINTS + " " +
			 GradeBookConstants.FROM + " " +
			 GradeBookConstants.HOMEWORKSCORES + " " + 
			 GradeBookConstants.WHERE + " " +
			 GradeBookConstants.HOMEWORKSCORES_STUDENTID + " = " + 
			 studentId);

	// keep track of how many homework assignments the student
	// actually answered and then use this to calculate their
	// total points for the homework assignments
	int homeworksAnswered = 0;

	// extract all of the total points from the result set 
	// and keep adding them to our runing total
	while( hwEarnedPointsResultSet.next() )
	    {

		hwEarnedPoints += hwEarnedPointsResultSet.
		    getInt(GradeBookConstants.HOMEWORKSCORES_EARNEDPOINTS);

		homeworksAnswered++;

	    }

	// calculate the total homework points
	hwTotalPoints = homeworksAnswered *
	    GradeBookConstants.HOMEWORK_VALUE;

	System.out.println("HwEarnedPoints: " + hwEarnedPoints);
	System.out.println("HwTotalPoints: " + hwTotalPoints);
	System.out.println("HwPercentage: " + hwPercentage);

	// calculate the final laboratory grade
	hwFinalGrade = ( (double)hwEarnedPoints / (double)hwTotalPoints ) *
	    hwPercentage;	    

	System.out.println("HwFinalGrade: " + hwFinalGrade);

	return hwFinalGrade;

    }    

    /**
     *  Calculates the final grade for a student's final project.
     *  
     *  @author Gregory M. Kapfhammer 3/7/2005
     */
    public double calculateProjectFinalGrade(int studentId)
	throws GradeBookDataException, SQLException
    {

	// the final grade for the final project
	double projectFinalGrade = 0.0;

	// the percentage assigned to the final project
	double projectPercentage = 0.0;

	// the total and earned number of points for the final project
	int projectTotalPoints = 0;
	int projectEarnedPoints = 0;

	// check to make sure that we have a valid student ID
	if( studentId < 0 )
	    {

		throw new GradeBookDataException("Incorrect Student Id");

	    }

	// it's a valid student and thus we can go ahead and query
	// for the percentages from the master table
	Statement selectStatement = databaseConnection.createStatement();

	// extract the percentage from the Master table
	
	// note that there is **NO** WHERE clause for this select
	// statement since we simply need all of the percentages and
	// there is only supposed to be one row inside of this table
	// (this invariant is not currently enforced in the code)
	ResultSet masterResultSet = 
	    selectStatement.
	    executeQuery(GradeBookConstants.SELECT + " " +
			 GradeBookConstants.MASTER_FINALPROJECT + " " +
			 GradeBookConstants.FROM + " " + 
			 GradeBookConstants.MASTER); 
	
	// extract all of the percentages from the masterResultSet
	// all of the values can be extracted during the first run
	// through the while loop (should be only one row)
	while( masterResultSet.next() )
	    {

		projectPercentage = masterResultSet.
		    getDouble(GradeBookConstants.
			      MASTER_FINALPROJECT);

	    }

	// extract the total points that were assigned to the final
	// project (this is currently just stored as a constant)
	projectTotalPoints = GradeBookConstants.FINAL_PROJECT_TOTAL_POINTS;
	
	// extract the number of points that this student earned on 
	// this assignment

	ResultSet projectEarnedPointsResultSet = 
	    selectStatement.
	    executeQuery(GradeBookConstants.SELECT + " " +
			 GradeBookConstants.FINALPROJECTSCORES_EARNEDPOINTS 
			 + " " +
			 GradeBookConstants.FROM + " " +
			 GradeBookConstants.FINALPROJECTSCORES + " " + 
			 GradeBookConstants.WHERE + " " +
			 GradeBookConstants.FINALPROJECTSCORES_STUDENTID 
			 + " = " + 
			 studentId);

	// extract the total points from the result set 
	// and keep adding them to our runing total
	while( projectEarnedPointsResultSet.next() )
	    {

		projectEarnedPoints = projectEarnedPointsResultSet.
		    getInt(GradeBookConstants.
			   FINALPROJECTSCORES_EARNEDPOINTS);

	    }

	// calculate the final project final grade (include the calculation
	// to modify by the percentage that is assigned to the project)
	projectFinalGrade = ( (double)projectEarnedPoints / 
			      (double)projectTotalPoints ) * 
	    projectPercentage;

	return projectFinalGrade;

    }

    /**
     *  Calculate the final grade for a student based upon all of the 
     *  previous grade calculation methods.
     *  
     *  @author Gregory M. Kapfhammer 3/7/2005
     */
    public double calculateFinalGrade(int studentId)
	throws GradeBookDataException, SQLException
    {

	double finalGrade = 0.0;

	double finalExamGrade = calculateExamFinalGrade(studentId);
	double finalLabGrade = calculateLaboratoryFinalGrade(studentId);
	double finalHomeworkGrade = calculateHomeworkFinalGrade(studentId);
	double finalProjectGrade = calculateProjectFinalGrade(studentId);

	finalGrade = finalExamGrade + finalLabGrade + finalHomeworkGrade + 
	    finalProjectGrade;

	return finalGrade;

    }


    // this was the code **before** we split the finalGrade calculations
    // into several separate methods

// 	ResultSet masterResultSet = 
// 	    selectStatement.
// 	    executeQuery(GradeBookConstants.SELECT + " " +
// 			 GradeBookConstants.MASTER_PARTICIPATION + ", " +
// 			 GradeBookConstants.MASTER_EXAMONE + ", " +
// 			 GradeBookConstants.MASTER_EXAMTWO + ", " +
// 			 GradeBookConstants.MASTER_EXAMTHREE + ", " +
// 			 GradeBookConstants.MASTER_LABORATORY + ", " +
// 			 GradeBookConstants.MASTER_HOMEWORK + ", " +
// 			 GradeBookConstants.MASTER_FINALPROJECT + " " +
// 			 GradeBookConstants.FROM + " " + 
// 			 GradeBookConstants.MASTER + " "); 
	
// 	while( masterResultSet.next() )
// 	    {

// 		participation = masterResultSet.
// 		    getDouble(GradeBookConstants.
// 			      MASTER_PARTICIPATION);

// 		examOne = masterResultSet.
// 		    getDouble(GradeBookConstants.
// 			      MASTER_EXAMONE);

// 		examTwo = masterResultSet.
// 		    getDouble(GradeBookConstants.
// 			      MASTER_EXAMTWO);

// 		examThree = masterResultSet.
// 		    getDouble(GradeBookConstants.
// 			      MASTER_EXAMTHREE);

// 		laboratory = masterResultSet.
// 		    getDouble(GradeBookConstants.
// 			      MASTER_LABORATORY);

// 		homework = masterResultSet.
// 		    getDouble(GradeBookConstants.
// 			      MASTER_HOMEWORK);

// 		finalProject = masterResultSet.
// 		    getDouble(GradeBookConstants.
// 			      MASTER_FINALPROJECT);
		
// 	    }

}
