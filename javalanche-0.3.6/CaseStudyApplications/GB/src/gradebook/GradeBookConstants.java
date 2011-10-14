/*---------------------------------------------------------------------
 *  File: $Id: GradeBookConstants.java,v 1.12 2005/03/08 15:25:17 gkapfham Exp $   
 *  Version:  $Revision: 1.12 $
 *
 *  Project: DIATOMS, Database drIven Application Testing tOol ModuleS
 *
 *--------------------------------------------------------------------*/

package gradebook;

/**
 *  Constants that are used by other GradeBook classes.
 *
 *  @author Gregory M. Kapfhammer 2/22/2005
 */

public abstract class GradeBookConstants
{

    /** Extra constants used when creating tables */
    public static final int INITIAL_CURVE = 0;
    public static final int HOMEWORK_VALUE = 10;    
    public static final int FINAL_PROJECT_TOTAL_POINTS = 100;

    /** Names of all the tables in the database */
    public static final String MASTER = "Master";
    public static final String STUDENT = "Student";
    public static final String EXAMMASTER = "ExamMaster";
    public static final String EXAMSCORES = "ExamScores";
    public static final String LABMASTER = "LabMaster";
    public static final String HOMEWORKMASTER = "HomeworkMaster";
    public static final String LABSCORES = "LabScores";
    public static final String HOMEWORKSCORES = "HomeworkScores";
    public static final String FINALPROJECTSCORES = 
	"FinalProjectScores";

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

    /** Specific fields within the different tables */
    
    /** Student table */
    public static final String STUDENT_ID = "ID";
    public static final String STUDENT_LASTNAME = "LastName";

    /** ExamMaster table */
    public static final String EXAMMASTER_EXAMID = "ExamId";
    public static final String EXAMMASTER_NAME = "Name";
    public static final String EXAMMASTER_TOTALPOINTS = "TotalPoints";
    public static final String EXAMMASTER_CURVE = "Curve";
    
    /** ExamScores table */
    public static final String EXAMSCORES_EXAMID = "ExamId";
    public static final String EXAMSCORES_STUDENTID = "StudentId";
    public static final String EXAMSCORES_EARNEDPOINTS = "EarnedPoints";

    /** LabScores table */
    public static final String LABSCORES_LABID = "LabId";
    public static final String LABSCORES_STUDENTID = "StudentId";
    public static final String LABSCORES_EARNEDPOINTS = "EarnedPoints";

    /** LabMaster table */
    public static final String LABMASTER_LABID = "LabId";
    public static final String LABMASTER_NAME = "Name";
    public static final String LABMASTER_TOTALPOINTS = "TotalPoints";

    /** HomeworkScores table */
    public static final String HOMEWORKSCORES_HOMEWORKID = "HomeworkId";
    public static final String HOMEWORKSCORES_STUDENTID = "StudentId";
    public static final String HOMEWORKSCORES_EARNEDPOINTS = "EarnedPoints";

    /** Master table */
    public static final String MASTER_PARTICIPATION = "Participation";
    public static final String MASTER_EXAMONE = "ExamOne";
    public static final String MASTER_EXAMTWO = "ExamTwo";
    public static final String MASTER_EXAMTHREE = "ExamThree";
    public static final String MASTER_LABORATORY = "Laboratory";
    public static final String MASTER_HOMEWORK = "Homework";
    public static final String MASTER_FINALPROJECT = "FinalProject";
    
    /** FinalProjectScores table */
    public static final String FINALPROJECTSCORES_STUDENTID = "StudentId";
    public static final String FINALPROJECTSCORES_EARNEDPOINTS = 
	"EarnedPoints";

}
