
/*---------------------------------------------------------------------
 *  File: $Id: TestReduceUsingKWay.java,v 1.26 2007/08/01 smitha Exp $   
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
 *  Test suite for the ReduceUsingDelayedGreedy class.
 *
 *  @author Adam M. Smith 7/01/2007
 *
 *  	Altered by author on December 4 to use assert statements.
 */

public class TestReduceUsingKWay extends TestCase
{
	SetCover cover;
	LinkedHashSet covered;
	ReduceUsingKWay kw;

	 /*
     *  Required constructor.
     *  
     *  @author Adam M. Smith 7/02/2007
     */
    public TestReduceUsingKWay(String name)
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
	 	kw = new ReduceUsingKWay();
	 }
	 
	 
	public void testReduceUsingTallamGuptaExampleCoverage() 
	{
		// make singletest
		SingleTest test0 = new SingleTest("test0",0);
		SingleTest test1 = new SingleTest("test1",1);
		SingleTest test2 = new SingleTest("test2",2);
		SingleTest test3 = new SingleTest("test3",3);
		SingleTest test4 = new SingleTest("test4",4);

		// make requirementsubsets
		RequirementSubset req0 = new RequirementSubset("req0",0);
		RequirementSubset req1 = new RequirementSubset("req1",1);
		RequirementSubset req2 = new RequirementSubset("req2",2);
		RequirementSubset req3 = new RequirementSubset("req3",3);
		RequirementSubset req4 = new RequirementSubset("req4",4);
		RequirementSubset req5 = new RequirementSubset("req5",5);

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
		
		kw.setSetCover(cover);
		
		kw.reduceUsingKW("coverage");
		covered = kw.getCoverPickSets();

		Iterator answerIterator = covered.iterator();

	//	System.out.println("*****\nCovering Set for reduceUsingTallumGuptaExample:\n");
		
	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
		
	//	System.out.println("*****\n\n");

	assertTrue( ((SingleTest) answerIterator.next()).getName().equals("test0"));
	assertTrue( ((SingleTest) answerIterator.next()).getName().equals("test1"));
	assertTrue( ((SingleTest) answerIterator.next()).getName().equals("test2"));
	assertTrue( ((SingleTest) answerIterator.next()).getName().equals("test3"));
	}	



	public void testReduceUsingHGSExampleCoverage() {
		assertEquals(0, cover.getTestSubsets().size());
		assertEquals(0, cover.getRequirementSubsetUniverse().size());
		
		SingleTest test0 = new SingleTest("test0",0);
		SingleTest test1 = new SingleTest("test1",1);
		SingleTest test2 = new SingleTest("test2",2);
		SingleTest test3 = new SingleTest("test3",3);
		SingleTest test4 = new SingleTest("test4",4);
		SingleTest test5 = new SingleTest("test5",5);
		SingleTest test6 = new SingleTest("test6",6);

		RequirementSubset req0 = new RequirementSubset("req0",0);
		RequirementSubset req1 = new RequirementSubset("req1",1);
		RequirementSubset req2 = new RequirementSubset("req2",2);
		RequirementSubset req3 = new RequirementSubset("req3",3);
		RequirementSubset req4 = new RequirementSubset("req4",4);
		RequirementSubset req5 = new RequirementSubset("req5",5);
		RequirementSubset req6 = new RequirementSubset("req6",6);
		RequirementSubset req7 = new RequirementSubset("req7",7);
	
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
	
		kw.setSetCover(cover);
		
		kw.reduceUsingKW("coverage");
		covered = kw.getCoverPickSets();

		Iterator answerIterator = covered.iterator();
	//	System.out.println("*****\nCovering Set for reduceUsingHGS:\n");
	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
	//	System.out.println("*****\n\n");
	
	assertTrue( ((SingleTest) answerIterator.next()).getName().equals("test0"));
	assertTrue( ((SingleTest) answerIterator.next()).getName().equals("test2"));
	assertTrue( ((SingleTest) answerIterator.next()).getName().equals("test1"));
	assertTrue( ((SingleTest) answerIterator.next()).getName().equals("test4"));
	} 



	public void testReduceUsingWalcottExampleCoverage() {
		assertEquals(0, cover.getTestSubsets().size());
		assertEquals(0, cover.getRequirementSubsetUniverse().size());
	
		SingleTest test0 = new SingleTest("test0",0);
		SingleTest test1 = new SingleTest("test1",1);
		SingleTest test2 = new SingleTest("test2",2);
		SingleTest test3 = new SingleTest("test3",3);
		SingleTest test4 = new SingleTest("test4",4);
		SingleTest test5 = new SingleTest("test5",5);

		RequirementSubset req0 = new RequirementSubset("req0",0);
		RequirementSubset req1 = new RequirementSubset("req1",1);
		RequirementSubset req2 = new RequirementSubset("req2",2);
		RequirementSubset req3 = new RequirementSubset("req3",3);
		RequirementSubset req4 = new RequirementSubset("req4",4);
		RequirementSubset req5 = new RequirementSubset("req5",5);
		RequirementSubset req6 = new RequirementSubset("req6",6);
		RequirementSubset req7 = new RequirementSubset("req7",7);
	
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
		
		kw.setSetCover(cover);
		
		kw.reduceUsingKW("coverage");
		covered = kw.getCoverPickSets();

		
		Iterator answerIterator = covered.iterator();

	//	System.out.println("*****\nSet of Covering Tests For Walcott Reduction Example:\n");

	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
	//	System.out.println("*****\n\n");
	
	assertTrue( ((SingleTest) answerIterator.next()).getName().equals("test0"));
	assertTrue( ((SingleTest) answerIterator.next()).getName().equals("test3"));

	}

	public void testReduceUsingAdamSmithFirstExampleCoverage() {
		assertEquals(0, cover.getTestSubsets().size());
		assertEquals(0, cover.getRequirementSubsetUniverse().size());

		SingleTest test0 = new SingleTest("test0",0);
		SingleTest test1 = new SingleTest("test1",1);
		SingleTest test2 = new SingleTest("test2",2);
		SingleTest test3 = new SingleTest("test3",3);
		SingleTest test4 = new SingleTest("test4",4);
		SingleTest test5 = new SingleTest("test5",5);

		RequirementSubset req0 = new RequirementSubset("req0",0);
		RequirementSubset req1 = new RequirementSubset("req1",1);
		RequirementSubset req2 = new RequirementSubset("req2",2);
		RequirementSubset req3 = new RequirementSubset("req3",3);
		RequirementSubset req4 = new RequirementSubset("req4",4);
		RequirementSubset req5 = new RequirementSubset("req5",5);
		RequirementSubset req6 = new RequirementSubset("req6",6);
		RequirementSubset req7 = new RequirementSubset("req7",7);
	
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
		
		kw.setSetCover(cover);
		
		kw.reduceUsingKW("coverage");
		covered = kw.getCoverPickSets();

		Iterator answerIterator = covered.iterator();

	//	System.out.println("*****\nSet of Covering Tests for reduceUsingAdamSmithFirstExample:\n");

	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
		
	//	System.out.println("*****\n\n");
	
	assertTrue( ((SingleTest) answerIterator.next()).getName().equals("test1"));
	assertTrue( ((SingleTest) answerIterator.next()).getName().equals("test4"));
	assertTrue( ((SingleTest) answerIterator.next()).getName().equals("test0"));
	assertTrue( ((SingleTest) answerIterator.next()).getName().equals("test2"));
	}
	

	public void testPrioritizeUsingAdamSmithFirstExampleCoverage() {
		assertEquals(0, cover.getTestSubsets().size());
		assertEquals(0, cover.getRequirementSubsetUniverse().size());

		SingleTest test0 = new SingleTest("test0",0);
		SingleTest test1 = new SingleTest("test1",1);
		SingleTest test2 = new SingleTest("test2",2);
		SingleTest test3 = new SingleTest("test3",3);
		SingleTest test4 = new SingleTest("test4",4);
		SingleTest test5 = new SingleTest("test5",5);

		RequirementSubset req0 = new RequirementSubset("req0",0);
		RequirementSubset req1 = new RequirementSubset("req1",1);
		RequirementSubset req2 = new RequirementSubset("req2",2);
		RequirementSubset req3 = new RequirementSubset("req3",3);
		RequirementSubset req4 = new RequirementSubset("req4",4);
		RequirementSubset req5 = new RequirementSubset("req5",5);
		RequirementSubset req6 = new RequirementSubset("req6",6);
		RequirementSubset req7 = new RequirementSubset("req7",7);
	
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
		
		kw.setSetCover(cover);
		kw.prioritizeUsingKW("coverage");
		covered = kw.getPrioritizedSets();

		Iterator answerIterator = covered.iterator();

	//	System.out.println("*****\nSet of Prioritized Tests for prioritizeUsingAdamSmithFirstExample:\n");

	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
		
	//	System.out.println("*****\n\n");

	assertTrue( ((SingleTest) answerIterator.next()).getName().equals("test1"));
	assertTrue( ((SingleTest) answerIterator.next()).getName().equals("test4"));
	assertTrue( ((SingleTest) answerIterator.next()).getName().equals("test0"));
	assertTrue( ((SingleTest) answerIterator.next()).getName().equals("test2"));
	assertTrue( ((SingleTest) answerIterator.next()).getName().equals("test3"));
	assertTrue( ((SingleTest) answerIterator.next()).getName().equals("test5"));

	}

public void testPrioritizeUsingWalcottExampleCoverage() {
		assertEquals(0, cover.getTestSubsets().size());
		assertEquals(0, cover.getRequirementSubsetUniverse().size());

		SingleTest test0 = new SingleTest("test0",0);
		SingleTest test1 = new SingleTest("test1",1);
		SingleTest test2 = new SingleTest("test2",2);
		SingleTest test3 = new SingleTest("test3",3);
		SingleTest test4 = new SingleTest("test4",4);
		SingleTest test5 = new SingleTest("test5",5);

		RequirementSubset req0 = new RequirementSubset("req0",0);
		RequirementSubset req1 = new RequirementSubset("req1",1);
		RequirementSubset req2 = new RequirementSubset("req2",2);
		RequirementSubset req3 = new RequirementSubset("req3",3);
		RequirementSubset req4 = new RequirementSubset("req4",4);
		RequirementSubset req5 = new RequirementSubset("req5",5);
		RequirementSubset req6 = new RequirementSubset("req6",6);
		RequirementSubset req7 = new RequirementSubset("req7",7);
	
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
		
		kw.setSetCover(cover);
		kw.prioritizeUsingKW("coverage");
		covered = kw.getPrioritizedSets();


		Iterator answerIterator = covered.iterator();

	//	System.out.println("*****\nSet of Prioritized Tests for prioritizeUsingWalcottExample:\n");
		
	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
	
	//	System.out.println("*****\n\n");

		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("test0"));
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("test3"));
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("test2"));
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("test4"));
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("test1"));
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("test5"));

	}

	public void testPrioritizeUsingTallamGuptaExampleCoverage() {
		assertEquals(0, cover.getTestSubsets().size());
		assertEquals(0, cover.getRequirementSubsetUniverse().size());

		// make singletest
		SingleTest test0 = new SingleTest("test0",0);
		SingleTest test1 = new SingleTest("test1",1);
		SingleTest test2 = new SingleTest("test2",2);
		SingleTest test3 = new SingleTest("test3",3);
		SingleTest test4 = new SingleTest("test4",4);

		// make requirementsubsets
		RequirementSubset req0 = new RequirementSubset("req0",0);
		RequirementSubset req1 = new RequirementSubset("req1",1);
		RequirementSubset req2 = new RequirementSubset("req2",2);
		RequirementSubset req3 = new RequirementSubset("req3",3);
		RequirementSubset req4 = new RequirementSubset("req4",4);
		RequirementSubset req5 = new RequirementSubset("req5",5);

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
		
		kw.setSetCover(cover);
		kw.prioritizeUsingKW("coverage");
		covered = kw.getPrioritizedSets();

		Iterator answerIterator = covered.iterator();

		//System.out.println("*****\nPrioritized Set for prioritizeUsingTallumGuptaExample:\n");
		
	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
		
	//	System.out.println("*****\n\n");


	assertTrue( ((SingleTest) answerIterator.next()).getName().equals("test0"));
	assertTrue( ((SingleTest) answerIterator.next()).getName().equals("test1"));
	assertTrue( ((SingleTest) answerIterator.next()).getName().equals("test2"));
	assertTrue( ((SingleTest) answerIterator.next()).getName().equals("test3"));
	assertTrue( ((SingleTest) answerIterator.next()).getName().equals("test4"));

	}	

	public void testPrioritizeUsingHGSExampleCoverage() {
		assertEquals(0, cover.getTestSubsets().size());
		assertEquals(0, cover.getRequirementSubsetUniverse().size());
		
		SingleTest test0 = new SingleTest("test0",0);
		SingleTest test1 = new SingleTest("test1",1);
		SingleTest test2 = new SingleTest("test2",2);
		SingleTest test3 = new SingleTest("test3",3);
		SingleTest test4 = new SingleTest("test4",4);
		SingleTest test5 = new SingleTest("test5",5);
		SingleTest test6 = new SingleTest("test6",6);

		RequirementSubset req0 = new RequirementSubset("req0",0);
		RequirementSubset req1 = new RequirementSubset("req1",1);
		RequirementSubset req2 = new RequirementSubset("req2",2);
		RequirementSubset req3 = new RequirementSubset("req3",3);
		RequirementSubset req4 = new RequirementSubset("req4",4);
		RequirementSubset req5 = new RequirementSubset("req5",5);
		RequirementSubset req6 = new RequirementSubset("req6",6);
		RequirementSubset req7 = new RequirementSubset("req7",7);
	
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
	
		kw.setSetCover(cover);
		kw.prioritizeUsingKW("coverage");
		covered = kw.getPrioritizedSets();
		
		Iterator answerIterator = covered.iterator();
	//	System.out.println("*****\nPrioritized Set for PrioritizeUsingHGSExample:\n");
	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
	//	System.out.println("*****\n\n");
	
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("test0"));
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("test2"));
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("test1"));
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("test4"));
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("test3"));
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("test5"));
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("test6"));
	}
/////////////////////////////////////END COVERAGE TESTS///////////////////////////////////////////////////
	/*public void testReduceUsingTallamGuptaExampleRatio()
		{
	
			kw.setSetCover(SetCover.constructSetCoverFromBinary("data/diatoms/reduce/synthetic_test_data/testMatrixTallamGuptaExample","data/diatoms/reduce/synthetic_test_data/testTimeTallamGuptaExample"));
			
			kw.reduceUsingKW("ratio");
			covered = kw.getCoverPickSets();
	
			Iterator answerIterator = covered.iterator();

			//System.out.println("*****\nCovering Set for reduceUsingTallumGuptaExample:\n");
			
		//	while ( answerIterator.hasNext()){
		
		//		SingleTest currentTest = (SingleTest) answerIterator.next();
	
		//		System.out.println(currentTest.toString());
		//	}
			
		//	System.out.println("*****\n\n");

		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("SingleTest1"));
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("SingleTest4"));
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("SingleTest2"));
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("SingleTest3"));
	}
	
	public void testReduceUsingHGSExampleRatio() 
	{
	
		kw.setSetCover(SetCover.constructSetCoverFromBinary("data/diatoms/reduce/synthetic_test_data/testMatrixHGSExample","data/diatoms/reduce/synthetic_test_data/testTimeHGSExample"));
		
		kw.reduceUsingKW("ratio");
			covered = kw.getCoverPickSets();
	
			Iterator answerIterator = covered.iterator();

			//System.out.println("*****\nCovering Set for reduceUsingTallumGuptaExample:\n");
			
		//	while ( answerIterator.hasNext()){
		
		//		SingleTest currentTest = (SingleTest) answerIterator.next();
	
		//		System.out.println(currentTest.toString());
		//	}
			
		//	System.out.println("*****\n\n");

		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("SingleTest2"));
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("SingleTest5"));
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("SingleTest3"));
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("SingleTest4"));
	}

/////////////////////////////////////////////END OF RATIO TESTS////////////////////////////////////////////////
	public void testReduceUsingTallamGuptaExampleTime()
	{
	
		kw.setSetCover(SetCover.constructSetCoverFromBinary("data/diatoms/reduce/synthetic_test_data/testMatrixTallamGuptaExample","data/diatoms/reduce/synthetic_test_data/testTimeTallamGuptaExample"));
			
		kw.reduceUsingKW("time");
		covered = kw.getCoverPickSets();
	
		Iterator answerIterator = covered.iterator();

		//System.out.println("*****\nCovering Set for reduceUsingTallumGuptaExample:\n");
			
		//	while ( answerIterator.hasNext()){
		
		//		SingleTest currentTest = (SingleTest) answerIterator.next();
	
		//		System.out.println(currentTest.toString());
		//	}
			
		//	System.out.println("*****\n\n");

		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("SingleTest2"));
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("SingleTest4"));
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("SingleTest0"));
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("SingleTest1"));
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("SingleTest3"));
	}
	
public void testReduceUsingKWayHugeExampleRatio() {
		kw.setSetCover(SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rlls1","/home/adam/Kanonizo/data/time-rlls1"));

		kw.reduceUsingKW("ratio");
		
		covered = kw.getCoverPickSets();

		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rlls1","/home/adam/Kanonizo/data/time-rlls1");
		
		assertTrue(cover.coversRequirementSubsetUniverse(covered));
		
		/*Iterator answerIterator = covered.iterator();

		System.out.println("*****\nCovering Set for reduceUsingHugeExample:\n");
		
		while ( answerIterator.hasNext()){
	
			SingleTest currentTest = (SingleTest) answerIterator.next();

			System.out.println(currentTest.toString());
		}
		
		System.out.println("*****\n\n");
*/
	//}	
	
		
}
