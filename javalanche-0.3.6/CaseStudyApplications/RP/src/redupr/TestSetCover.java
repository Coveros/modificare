/*---------------------------------------------------------------------
 *  File: TestCover.java, 2008/01/22 13:36:17 smitha   
 *  Version:  $Revision: 1.26 $
 *
 *  Project: DIATOMS, Database drIven Application Testing tOol ModuleS
 *
 *--------------------------------------------------------------------*/

package redupr;

import java.util.Set;
import java.util.LinkedHashSet;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Vector;

import java.io.PrintWriter;

import junit.framework.TestSuite;
import junit.framework.TestCase;

//import diatoms.profiler.Profiler;

/**
 *  Test suite for the SetCover class.
 *
 *  @author Adam M. Smith 1/22/2008
 */

public class TestSetCover extends TestCase
{
	SetCover cover;
	SetCover cover2;
	LinkedHashSet covered;

	 /*
     *  Required constructor.
     *  
     *  @author Adam M. Smith 7/02/2007
     */
    public TestSetCover(String name)
    {
    	super(name);
	 }

   /**
     *  Construct the fresh instance of the SetCover.
     *  
     *  @author Adam M. Smith 7/02/2007
     */
    public void setUp()
    {
  		cover = new SetCover();
  		covered = new LinkedHashSet();
  		cover2 = new SetCover();
	 }
	 
	 public void testProperNumberOfRequirementsAndTests()
	 {
	 	cover = SetCover.constructSetCoverFromBinary("data/diatoms/reduce/synthetic_test_data/testMatrixAdamExample","data/diatoms/reduce/synthetic_test_data/testTimeAdamExample");
	 	
	 	assertEquals(8,(cover.getRequirementSubsetUniverse()).size());
	 	assertEquals(6,(cover.getTestSubsets()).size());
	 }


	 public void testIdenticalConstructionAdamExample()
	 {
	 
	  	SingleTest test0 = new SingleTest("SingleTest0",0,4.0);
		SingleTest test1 = new SingleTest("SingleTest1",1,1.0);
		SingleTest test2 = new SingleTest("SingleTest2",2,5.0);
		SingleTest test3 = new SingleTest("SingleTest3",3,2.0);
		SingleTest test4 = new SingleTest("SingleTest4",4,3.0);
		SingleTest test5 = new SingleTest("SingleTest5",5,2.0);

		RequirementSubset req0 = new RequirementSubset("RequirementSubset0",0);
		RequirementSubset req1 = new RequirementSubset("RequirementSubset1",1);
		RequirementSubset req2 = new RequirementSubset("RequirementSubset2",2);
		RequirementSubset req3 = new RequirementSubset("RequirementSubset3",3);
		RequirementSubset req4 = new RequirementSubset("RequirementSubset4",4);
		RequirementSubset req5 = new RequirementSubset("RequirementSubset5",5);
		RequirementSubset req6 = new RequirementSubset("RequirementSubset6",6);
		RequirementSubset req7 = new RequirementSubset("RequirementSubset7",7);
	
		SingleTestSubset STS0 = new SingleTestSubset(test0);
		SingleTestSubset STS1 = new SingleTestSubset(test1);
		SingleTestSubset STS2 = new SingleTestSubset(test2);
		SingleTestSubset STS3 = new SingleTestSubset(test3);
		SingleTestSubset STS4 = new SingleTestSubset(test4);
		SingleTestSubset STS5 = new SingleTestSubset(test5);

		// add SingleTests to requirementSubsets
		req0.addCoveringTest(test0);		
		req0.addCoveringTest(test1);		
		req0.addCoveringTest(test2);		
		req0.addCoveringTest(test5);		
		req1.addCoveringTest(test1);		
		req1.addCoveringTest(test2);		
		req1.addCoveringTest(test3);		
		req1.addCoveringTest(test5);		
		req2.addCoveringTest(test0);		
		req3.addCoveringTest(test4);		
		req3.addCoveringTest(test5);		
		req4.addCoveringTest(test1);		
		req4.addCoveringTest(test2);		
		req5.addCoveringTest(test1);		
		req5.addCoveringTest(test3);		
		req6.addCoveringTest(test4);		
		req6.addCoveringTest(test5);		
		req7.addCoveringTest(test4);		
						
		// add requirementsubsets to STSs
		STS0.addRequirementSubset(req0);
		STS0.addRequirementSubset(req2);
		STS1.addRequirementSubset(req0);
		STS1.addRequirementSubset(req1);
		STS1.addRequirementSubset(req4);
		STS1.addRequirementSubset(req5);
		STS2.addRequirementSubset(req0);
		STS2.addRequirementSubset(req1);
		STS2.addRequirementSubset(req4);
		STS3.addRequirementSubset(req1);
		STS3.addRequirementSubset(req5);
		STS4.addRequirementSubset(req3);
		STS4.addRequirementSubset(req6);
		STS4.addRequirementSubset(req7);
		STS5.addRequirementSubset(req0);
		STS5.addRequirementSubset(req1);
		STS5.addRequirementSubset(req3);
		STS5.addRequirementSubset(req6);
		
		// add requirementSubsets to the cover
		cover2.addRequirementSubset(req0);
		cover2.addRequirementSubset(req1);
		cover2.addRequirementSubset(req2);
		cover2.addRequirementSubset(req3);
		cover2.addRequirementSubset(req4);
		cover2.addRequirementSubset(req5);
		cover2.addRequirementSubset(req6);
		cover2.addRequirementSubset(req7);
	
	
		// add SingleTestSubset to cover
		cover2.addSingleTestSubset(STS0);
		cover2.addSingleTestSubset(STS1);
		cover2.addSingleTestSubset(STS2);
		cover2.addSingleTestSubset(STS3);
		cover2.addSingleTestSubset(STS4);
		cover2.addSingleTestSubset(STS5);
	 
	 
		cover = SetCover.constructSetCoverFromBinary("data/diatoms/reduce/synthetic_test_data/testMatrixAdamExample","data/diatoms/reduce/synthetic_test_data/testTimeAdamExample");
	 
	 	assertEquals(cover.toString(),cover2.toString());
	}	 
	
	public void testIdenticalConstructionWalcottExample()
	{
	
		SingleTest test0 = new SingleTest("SingleTest0",0,9.0);
		SingleTest test1 = new SingleTest("SingleTest1",1,1.0);
		SingleTest test2 = new SingleTest("SingleTest2",2,3.0);
		SingleTest test3 = new SingleTest("SingleTest3",3,4.0);
		SingleTest test4 = new SingleTest("SingleTest4",4,4.0);
		SingleTest test5 = new SingleTest("SingleTest5",5,4.0);

		RequirementSubset req0 = new RequirementSubset("RequirementSubset0",0);
		RequirementSubset req1 = new RequirementSubset("RequirementSubset1",1);
		RequirementSubset req2 = new RequirementSubset("RequirementSubset2",2);
		RequirementSubset req3 = new RequirementSubset("RequirementSubset3",3);
		RequirementSubset req4 = new RequirementSubset("RequirementSubset4",4);
		RequirementSubset req5 = new RequirementSubset("RequirementSubset5",5);
		RequirementSubset req6 = new RequirementSubset("RequirementSubset6",6);
		RequirementSubset req7 = new RequirementSubset("RequirementSubset7",7);
	
		SingleTestSubset STS0 = new SingleTestSubset(test0);
		SingleTestSubset STS1 = new SingleTestSubset(test1);
		SingleTestSubset STS2 = new SingleTestSubset(test2);
		SingleTestSubset STS3 = new SingleTestSubset(test3);
		SingleTestSubset STS4 = new SingleTestSubset(test4);
		SingleTestSubset STS5 = new SingleTestSubset(test5);

		
		// add SingleTests to requirementSubsets
		req0.addCoveringTest(test0);		
		req0.addCoveringTest(test1);		
		req0.addCoveringTest(test2);		
		req1.addCoveringTest(test0);		
		req1.addCoveringTest(test3);		
		req1.addCoveringTest(test5);		
		req2.addCoveringTest(test3);		
		req3.addCoveringTest(test0);		
		req3.addCoveringTest(test4);		
		req3.addCoveringTest(test5);		
		req4.addCoveringTest(test0);		
		req4.addCoveringTest(test2);		
		req5.addCoveringTest(test0);		
		req5.addCoveringTest(test4);		
		req5.addCoveringTest(test5);		
		req6.addCoveringTest(test0);		
		req6.addCoveringTest(test3);		
		req7.addCoveringTest(test0);		
		req7.addCoveringTest(test4);		
		
		
		// add requirementsubsets to STSs
		STS0.addRequirementSubset(req0);
		STS0.addRequirementSubset(req1);
		STS0.addRequirementSubset(req3);
		STS0.addRequirementSubset(req4);
		STS0.addRequirementSubset(req5);
		STS0.addRequirementSubset(req6);
		STS0.addRequirementSubset(req7);
		STS1.addRequirementSubset(req0);
		STS2.addRequirementSubset(req0);
		STS2.addRequirementSubset(req4);
		STS3.addRequirementSubset(req1);
		STS3.addRequirementSubset(req2);
		STS3.addRequirementSubset(req6);
		STS4.addRequirementSubset(req3);
		STS4.addRequirementSubset(req5);
		STS4.addRequirementSubset(req7);
		STS5.addRequirementSubset(req1);
		STS5.addRequirementSubset(req3);
		STS5.addRequirementSubset(req5);
		
		// add requirementSubsets to the cover
		cover.addRequirementSubset(req0);
		cover.addRequirementSubset(req1);
		cover.addRequirementSubset(req2);
		cover.addRequirementSubset(req3);
		cover.addRequirementSubset(req4);
		cover.addRequirementSubset(req5);
		cover.addRequirementSubset(req6);
		cover.addRequirementSubset(req7);
	
	
		// add SingleTestSubset to cover
		cover.addSingleTestSubset(STS0);
		cover.addSingleTestSubset(STS1);
		cover.addSingleTestSubset(STS2);
		cover.addSingleTestSubset(STS3);
		cover.addSingleTestSubset(STS4);
		cover.addSingleTestSubset(STS5);
	
		cover2 = SetCover.constructSetCoverFromBinary("data/diatoms/reduce/synthetic_test_data/testMatrixWalcottExample","data/diatoms/reduce/synthetic_test_data/testTimeWalcottExample");
		
		assertEquals(cover.toString(),cover2.toString());
	}
	
	public void testIdenticalConstructionTallamGuptaExample()
	{
	
	// make singletest
		SingleTest test0 = new SingleTest("SingleTest0",0,7.0);
		SingleTest test1 = new SingleTest("SingleTest1",1,4.0);
		SingleTest test2 = new SingleTest("SingleTest2",2,3.0);
		SingleTest test3 = new SingleTest("SingleTest3",3,9.0);
		SingleTest test4 = new SingleTest("SingleTest4",4,1.0);

		// make requirementsubsets
		RequirementSubset req0 = new RequirementSubset("RequirementSubset0",0);
		RequirementSubset req1 = new RequirementSubset("RequirementSubset1",1);
		RequirementSubset req2 = new RequirementSubset("RequirementSubset2",2);
		RequirementSubset req3 = new RequirementSubset("RequirementSubset3",3);
		RequirementSubset req4 = new RequirementSubset("RequirementSubset4",4);
		RequirementSubset req5 = new RequirementSubset("RequirementSubset5",5);

		// make singletestsubset with singletests
		SingleTestSubset STS0 = new SingleTestSubset(test0);
		SingleTestSubset STS1 = new SingleTestSubset(test1);
		SingleTestSubset STS2 = new SingleTestSubset(test2);
		SingleTestSubset STS3 = new SingleTestSubset(test3);
		SingleTestSubset STS4 = new SingleTestSubset(test4);

		// add SingleTests to requirementSubsets
		req0.addCoveringTest(test0);		
		req0.addCoveringTest(test1); 
		req1.addCoveringTest(test0);
		req1.addCoveringTest(test2);
		req2.addCoveringTest(test0);
		req2.addCoveringTest(test3);
		req3.addCoveringTest(test1);
		req4.addCoveringTest(test2);
		req4.addCoveringTest(test4);
		req5.addCoveringTest(test3);
	
		// add requirementsubsets to STSs
		STS0.addRequirementSubset(req0);
		STS0.addRequirementSubset(req1);
		STS0.addRequirementSubset(req2);
		STS1.addRequirementSubset(req0);
		STS1.addRequirementSubset(req3);
		STS2.addRequirementSubset(req1);
		STS2.addRequirementSubset(req4);
		STS3.addRequirementSubset(req2);
		STS3.addRequirementSubset(req5);
		STS4.addRequirementSubset(req4);

		// add requirementSubsets to the cover
		cover.addRequirementSubset(req0);
		cover.addRequirementSubset(req1);
		cover.addRequirementSubset(req2);
		cover.addRequirementSubset(req3);
		cover.addRequirementSubset(req4);
		cover.addRequirementSubset(req5);

		// add SingleTestSubset to cover
		
		cover.addSingleTestSubset(STS0);
		cover.addSingleTestSubset(STS1);
		cover.addSingleTestSubset(STS2);
		cover.addSingleTestSubset(STS3);
		cover.addSingleTestSubset(STS4);
	
		cover2 = SetCover.constructSetCoverFromBinary("data/diatoms/reduce/synthetic_test_data/testMatrixTallamGuptaExample","data/diatoms/reduce/synthetic_test_data/testTimeTallamGuptaExample");
		
		assertEquals(cover.toString(),cover2.toString());
	}
	
	public void testIdenticalConstructionHGSExample()
	{
		SingleTest test0 = new SingleTest("SingleTest0",0,9.0);
		SingleTest test1 = new SingleTest("SingleTest1",1,3.0);
		SingleTest test2 = new SingleTest("SingleTest2",2,3.0);
		SingleTest test3 = new SingleTest("SingleTest3",3,4.0);
		SingleTest test4 = new SingleTest("SingleTest4",4,5.0);
		SingleTest test5 = new SingleTest("SingleTest5",5,3.0);
		SingleTest test6 = new SingleTest("SingleTest6",6,6.0);

		RequirementSubset req0 = new RequirementSubset("RequirementSubset0",0);
		RequirementSubset req1 = new RequirementSubset("RequirementSubset1",1);
		RequirementSubset req2 = new RequirementSubset("RequirementSubset2",2);
		RequirementSubset req3 = new RequirementSubset("RequirementSubset3",3);
		RequirementSubset req4 = new RequirementSubset("RequirementSubset4",4);
		RequirementSubset req5 = new RequirementSubset("RequirementSubset5",5);
		RequirementSubset req6 = new RequirementSubset("RequirementSubset6",6);
		RequirementSubset req7 = new RequirementSubset("RequirementSubset7",7);
	
		SingleTestSubset STS0 = new SingleTestSubset(test0);
		SingleTestSubset STS1 = new SingleTestSubset(test1);
		SingleTestSubset STS2 = new SingleTestSubset(test2);
		SingleTestSubset STS3 = new SingleTestSubset(test3);
		SingleTestSubset STS4 = new SingleTestSubset(test4);
		SingleTestSubset STS5 = new SingleTestSubset(test5);
		SingleTestSubset STS6 = new SingleTestSubset(test6);
		
		// add SingleTests to requirementSubsets
		req0.addCoveringTest(test0);		
		req0.addCoveringTest(test4);		
		req1.addCoveringTest(test4);		
		req2.addCoveringTest(test0);		
		req2.addCoveringTest(test1);		
		req2.addCoveringTest(test2);		
		req3.addCoveringTest(test2);		
		req3.addCoveringTest(test5);		
		req4.addCoveringTest(test0);		
		req4.addCoveringTest(test3);		
		req5.addCoveringTest(test0);		
		req5.addCoveringTest(test5);		
		req6.addCoveringTest(test2);		
		req6.addCoveringTest(test3);		
		req6.addCoveringTest(test6);		
		req7.addCoveringTest(test1);		
		req7.addCoveringTest(test2);		
		req7.addCoveringTest(test3);		
		req7.addCoveringTest(test6);		
		
		
		// add requirementsubsets to STSs
		STS0.addRequirementSubset(req0);
		STS0.addRequirementSubset(req2);
		STS0.addRequirementSubset(req4);
		STS0.addRequirementSubset(req5);
		STS1.addRequirementSubset(req2);
		STS1.addRequirementSubset(req7);
		STS2.addRequirementSubset(req2);
		STS2.addRequirementSubset(req3);
		STS2.addRequirementSubset(req6);
		STS2.addRequirementSubset(req7);
		STS3.addRequirementSubset(req4);
		STS3.addRequirementSubset(req6);
		STS3.addRequirementSubset(req7);
		STS4.addRequirementSubset(req0);
		STS4.addRequirementSubset(req1);
		STS5.addRequirementSubset(req3);
		STS5.addRequirementSubset(req5);
		STS6.addRequirementSubset(req6);
		STS6.addRequirementSubset(req7);
		
		// add requirementSubsets to the cover
		cover.addRequirementSubset(req0);
		cover.addRequirementSubset(req1);
		cover.addRequirementSubset(req2);
		cover.addRequirementSubset(req3);
		cover.addRequirementSubset(req4);
		cover.addRequirementSubset(req5);
		cover.addRequirementSubset(req6);
		cover.addRequirementSubset(req7);
	
	
		// add SingleTestSubset to cover
		cover.addSingleTestSubset(STS0);
		cover.addSingleTestSubset(STS1);
		cover.addSingleTestSubset(STS2);
		cover.addSingleTestSubset(STS3);
		cover.addSingleTestSubset(STS4);
		cover.addSingleTestSubset(STS5);
		cover.addSingleTestSubset(STS6);
	
		cover2 = SetCover.constructSetCoverFromBinary("data/diatoms/reduce/synthetic_test_data/testMatrixHGSExample","data/diatoms/reduce/synthetic_test_data/testTimeHGSExample");
		
		assertEquals(cover.toString(),cover2.toString());
	}
	
	public void testNoFile()
	{
		try
		{
			cover = SetCover.constructSetCoverFromBinary("","");
		}
		catch (Exception e){}
		
	}
	
	
	/*
	 * The following tests are for the greedy reduction/prioritization methods in 
	 * SetCover that have been commented out.
	 *
	
	public void testGreedyReduction()
	{
		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rmmm5","/home/adam/Kanonizo/data/time-rmmm5");
		
		Set coverSet = cover.reduceUsingGreedy("ratio");
		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rmmm5","/home/adam/Kanonizo/data/time-rmmm5");
		assertTrue(cover.coversRequirementSubsetUniverse((LinkedHashSet) coverSet));
		
		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rmmm5","/home/adam/Kanonizo/data/time-rmmm5");
		
		coverSet = cover.reduceUsingGreedy("coverage");
		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rmmm5","/home/adam/Kanonizo/data/time-rmmm5");
		assertTrue(cover.coversRequirementSubsetUniverse((LinkedHashSet) coverSet));
		
		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rmmm5","/home/adam/Kanonizo/data/time-rmmm5");
		
		coverSet = cover.reduceUsingGreedy("time");
		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rmmm5","/home/adam/Kanonizo/data/time-rmmm5");
		assertTrue(cover.coversRequirementSubsetUniverse((LinkedHashSet) coverSet));
		
		//Iterator answerIterator = coverSet.iterator();

//		System.out.println("*****\nCovering Set for reduceUsingHugeExample:\n");
		
	//	while ( answerIterator.hasNext()){
	
		//	SingleTest currentTest = (SingleTest) answerIterator.next();

			//System.out.println(currentTest.toString());
		//}
		
		//System.out.println("*****\n\n");
	}
	
	public void testGreedyPrioritization()
	{
		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rmmm5","/home/adam/Kanonizo/data/time-rmmm5");
		Set coverSet = cover.prioritizeUsingGreedy("ratio");
		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rmmm5","/home/adam/Kanonizo/data/time-rmmm5");
		assertTrue(cover.coversRequirementSubsetUniverse((LinkedHashSet) coverSet));
		assertTrue(cover.getTestSubsets().size() == coverSet.size());
		
		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rmmm5","/home/adam/Kanonizo/data/time-rmmm5");
		coverSet = cover.prioritizeUsingGreedy("coverage");
		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rmmm5","/home/adam/Kanonizo/data/time-rmmm5");
		assertTrue(cover.coversRequirementSubsetUniverse((LinkedHashSet) coverSet));
		assertTrue(cover.getTestSubsets().size() == coverSet.size());
		
		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rmmm5","/home/adam/Kanonizo/data/time-rmmm5");
		coverSet = cover.prioritizeUsingGreedy("time");
		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rmmm5","/home/adam/Kanonizo/data/time-rmmm5");
		assertTrue(cover.coversRequirementSubsetUniverse((LinkedHashSet) coverSet));
		assertTrue(cover.getTestSubsets().size() == coverSet.size());
		//Iterator answerIterator = coverSet.iterator();

//		System.out.println("*****\nCovering Set for reduceUsingHugeExample:\n");
		
	//	while ( answerIterator.hasNext()){
	
		//	SingleTest currentTest = (SingleTest) answerIterator.next();

			//System.out.println(currentTest.toString());
		//}
		
		//System.out.println("*****\n\n");
	}
	
	public void testForPurchasePricesOnCSSS1()
	{
		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-csss1","/home/adam/Kanonizo/data/time-csss1");
		Set coverSet = cover.prioritizeUsingGreedy("coverage");
		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-csss1","/home/adam/Kanonizo/data/time-csss1");
		assertTrue(cover.coversRequirementSubsetUniverse((LinkedHashSet) coverSet));
		
		// Print the Results
		Iterator answerIterator = coverSet.iterator();

		System.out.println("*****\nPrioritize Set for testForPurchasePricesOnCSSS1:\n");
		
		while ( answerIterator.hasNext()){
	
			SingleTest currentTest = (SingleTest) answerIterator.next();

			System.out.println(currentTest.toString());
		}
		
		System.out.println("*****\n\n");
		
	}
	
		public void testForPurchasePricesOnCSSS2()
	{
		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-csss2","/home/adam/Kanonizo/data/time-csss2");
		Set coverSet = cover.prioritizeUsingGreedy("coverage");
		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-csss2","/home/adam/Kanonizo/data/time-csss2");
		assertTrue(cover.coversRequirementSubsetUniverse((LinkedHashSet) coverSet));
		
		// Print the Results
		Iterator answerIterator = coverSet.iterator();

		System.out.println("*****\nPrioritize Set for testForPurchasePricesOnCSSS1:\n");
		
		while ( answerIterator.hasNext()){
	
			SingleTest currentTest = (SingleTest) answerIterator.next();

			System.out.println(currentTest.toString());
		}
		
		System.out.println("*****\n\n");
		
	}
	
	public void testMetricsCSSS1()
	{
	
		String metric = "coverage";
		
		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-csss1","/home/adam/Kanonizo/data/time-csss1");
		Set coverSet = cover.reduceUsingGreedy(metric);
		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-csss1","/home/adam/Kanonizo/data/time-csss1");
		Set prioritizeSet = cover.prioritizeUsingGreedy(metric);

		assertTrue(cover.coversRequirementSubsetUniverse((LinkedHashSet) coverSet));
		
		// Print the Results
		Iterator answerIterator = coverSet.iterator();

		System.out.println("*****\nCover Set for CSSS1 using "+metric+":\n");
		
		while ( answerIterator.hasNext()){
	
			SingleTest currentTest = (SingleTest) answerIterator.next();

			System.out.println(currentTest.toString());
		}
		
		System.out.println("*****\n\n");
		
		// Print the Results
		answerIterator = prioritizeSet.iterator();

		System.out.println("*****\nPrioritize Set for CSSS1 using "+metric+":\n");
		
		while ( answerIterator.hasNext()){
	
			SingleTest currentTest = (SingleTest) answerIterator.next();

			System.out.println(currentTest.toString());
		}
		
		System.out.println("*****\n\n");
	}
	*/
}
