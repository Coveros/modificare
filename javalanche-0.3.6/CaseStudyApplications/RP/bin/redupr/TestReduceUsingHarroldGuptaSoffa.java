/*---------------------------------------------------------------------
 *  File: $Id: TestHittingSet.java,v 1.26 2007/07/02 13:36:17 smitha Exp $   
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
 *  Test suite for the HarroldGuptaSoffa class.
 *
 *  @author Adam M. Smith 7/01/2007
 */

public class TestReduceUsingHarroldGuptaSoffa extends TestCase
{
	SetCover cover;
	LinkedHashSet covered;
	ReduceUsingHarroldGuptaSoffa hgs;

	 /*
     *  Required constructor.
     *  
     *  @author Adam M. Smith 7/02/2007
     */
    public TestReduceUsingHarroldGuptaSoffa(String name)
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
	 	hgs = new ReduceUsingHarroldGuptaSoffa();
	 }
	
	
	// THERE ARE THREE VERSIONS OF EACH TEST.  ONE FOR EACH VALUE OF NUMBER OF LOOKS AHEAD
	// The lookahead values are separated by ****
	
	//****************************************************************************************
	
	// This method tests looksahead=0
	
	public void testReduceUsingTallamGuptaExampleLA0() {
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
		
		hgs.setSetCover(cover);
		hgs.setLooksAhead(0);
		hgs.reduceUsingHGS("coverage");
		covered = hgs.getCoverPickSets();

		assertTrue(covered.contains(test1));
		assertTrue(covered.contains(test2));
		assertTrue(covered.contains(test3));

		Iterator answerIterator = covered.iterator();

	//	System.out.println("*****\nCovering Set for reduceUsingTallumGuptaExample:\n");
		
	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
		
	//	System.out.println("*****\n\n");

	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test1");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test3");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test2");
	}	


// This method tests lookahead = 1

public void testReduceUsingTallamGuptaExampleLA1() {
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
		
		hgs.setSetCover(cover);
		hgs.setLooksAhead(1);
		hgs.reduceUsingHGS("coverage");
		covered = hgs.getCoverPickSets();

		assertTrue(covered.contains(test1));
		assertTrue(covered.contains(test2));
		assertTrue(covered.contains(test3));

		Iterator answerIterator = covered.iterator();

	//	System.out.println("*****\nCovering Set for reduceUsingTallumGuptaExample:\n");
		
	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
		
	//	System.out.println("*****\n\n");

	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test1");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test3");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test2");
	}	
	
	
	//looksahead=2
	
	public void testReduceUsingTallamGuptaExampleLA2() {
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
		
		hgs.setSetCover(cover);
		hgs.setLooksAhead(2);
		hgs.reduceUsingHGS("coverage");
		covered = hgs.getCoverPickSets();

		assertTrue(covered.contains(test1));
		assertTrue(covered.contains(test2));
		assertTrue(covered.contains(test3));

		Iterator answerIterator = covered.iterator();

	//	System.out.println("*****\nCovering Set for reduceUsingTallumGuptaExample:\n");
		
	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
		
	//	System.out.println("*****\n\n");

	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test1");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test3");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test2");
	}	


//****************************************************************************************

	public void testReduceUsingHGSExampleLA0() {
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
	
		hgs.setSetCover(cover);
		hgs.setLooksAhead(0);
		hgs.reduceUsingHGS("coverage");
		covered = hgs.getCoverPickSets();
		
		Iterator answerIterator = covered.iterator();
	
	//	System.out.println("*****\nCovering Set for reduceUsingHGS:\n");
	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
	//	System.out.println("*****\n\n");
	
	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test4");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test5");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test0");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test2");
	
	} 
	
		public void testReduceUsingHGSExampleLA1() {
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
	
		hgs.setSetCover(cover);
		hgs.setLooksAhead(1);
		hgs.reduceUsingHGS("coverage");
		covered = hgs.getCoverPickSets();
		
		Iterator answerIterator = covered.iterator();
	
	//	System.out.println("*****\nCovering Set for reduceUsingHGS:\n");
	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
	//	System.out.println("*****\n\n");
	
	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test4");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test5");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test0");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test2");
	
	}
	
		public void testReduceUsingHGSExampleLA2() {
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
	
		hgs.setSetCover(cover);
		hgs.setLooksAhead(2);
		hgs.reduceUsingHGS("coverage");
		covered = hgs.getCoverPickSets();
		
		Iterator answerIterator = covered.iterator();
	
	//	System.out.println("*****\nCovering Set for reduceUsingHGS:\n");
	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
	//	System.out.println("*****\n\n");
	
	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test4");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test5");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test3");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test0");
	
	}

//****************************************************************************************

	public void testReduceUsingWalcottExampleLA0() {
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
		
		hgs.setSetCover(cover);

		hgs.setLooksAhead(0);

		hgs.reduceUsingHGS("coverage");
		covered = hgs.getCoverPickSets();
		
		Iterator answerIterator = covered.iterator();

	//	System.out.println("*****\nSet of Covering Tests For Walcott Reduction Example:\n");

	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
	//	System.out.println("*****\n\n");
	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test3");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test0");
	
	}
	
	public void testReduceUsingWalcottExampleLA1() {
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
		
		hgs.setSetCover(cover);

		hgs.setLooksAhead(1);

		hgs.reduceUsingHGS("coverage");
		covered = hgs.getCoverPickSets();
		
		Iterator answerIterator = covered.iterator();

	//	System.out.println("*****\nSet of Covering Tests For Walcott Reduction Example:\n");

	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
	//	System.out.println("*****\n\n");
	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test3");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test0");
	}

public void testReduceUsingWalcottExampleLA2() {
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
		
		hgs.setSetCover(cover);

		hgs.setLooksAhead(0);

		hgs.reduceUsingHGS("coverage");
		covered = hgs.getCoverPickSets();
		
		Iterator answerIterator = covered.iterator();

	//	System.out.println("*****\nSet of Covering Tests For Walcott Reduction Example:\n");

	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
	//	System.out.println("*****\n\n");
	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test3");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test0");
	
	}

//****************************************************************************************

	public void testReduceUsingAdamSmithFirstExampleLA0() {
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
		
		hgs.setSetCover(cover);
		hgs.setLooksAhead(0);
		hgs.reduceUsingHGS("coverage");
		covered = hgs.getCoverPickSets();

		Iterator answerIterator = covered.iterator();

	//	System.out.println("*****\nSet of Covering Tests for reduceUsingAdamSmithFirstExample:\n");

	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
		
	//	System.out.println("*****\n\n");
		
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test0");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test4");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test1");
	
	}
	
	public void testReduceUsingAdamSmithFirstExampleLA1() {
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
		
		hgs.setSetCover(cover);
		hgs.setLooksAhead(1);
		hgs.reduceUsingHGS("coverage");
		covered = hgs.getCoverPickSets();

		Iterator answerIterator = covered.iterator();

		//	System.out.println("*****\nSet of Covering Tests for reduceUsingAdamSmithFirstExample:\n");

	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
		
	//	System.out.println("*****\n\n");
		
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test0");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test4");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test1");
	
	}
	
	public void testReduceUsingAdamSmithFirstExampleLA2() {
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
		
		hgs.setSetCover(cover);
		hgs.setLooksAhead(2);
		hgs.reduceUsingHGS("coverage");
		covered = hgs.getCoverPickSets();

		Iterator answerIterator = covered.iterator();

	//	System.out.println("*****\nSet of Covering Tests for reduceUsingAdamSmithFirstExample:\n");

	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
		
	//	System.out.println("*****\n\n");
		
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test0");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test4");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test1");
	
}
	//****************************************************************************************


	// Begin prioritization section

	public void testPrioritizeUsingAdamSmithFirstExampleLA0() {
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
		
		hgs.setSetCover(cover);
		hgs.setLooksAhead(0);
		hgs.prioritizeUsingHGS("coverage");
		
		covered = hgs.getPrioritizedSets();

		Iterator answerIterator = covered.iterator();

	//	System.out.println("*****\nSet of Prioritized Tests for prioritizeUsingAdamSmithFirstExample:\n");

	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
		
	//	System.out.println("*****\n\n");
	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test0");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test4");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test1");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test5");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test2");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test3");	

	}


	public void testPrioritizeUsingAdamSmithFirstExampleLA1() {
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
		
		hgs.setSetCover(cover);
		hgs.setLooksAhead(1);
		hgs.prioritizeUsingHGS("coverage");
		
		covered = hgs.getPrioritizedSets();

		Iterator answerIterator = covered.iterator();

	//	System.out.println("*****\nSet of Prioritized Tests for prioritizeUsingAdamSmithFirstExample:\n");

	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
		
	//	System.out.println("*****\n\n");
	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test0");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test4");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test1");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test5");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test2");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test3");
		
	}

	public void testPrioritizeUsingAdamSmithFirstExampleLA2() {
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
		
		hgs.setSetCover(cover);
		hgs.setLooksAhead(2);
		hgs.prioritizeUsingHGS("coverage");
		
		covered = hgs.getPrioritizedSets();

		Iterator answerIterator = covered.iterator();

	//	System.out.println("*****\nSet of Prioritized Tests for prioritizeUsingAdamSmithFirstExample:\n");

	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
		
	//	System.out.println("*****\n\n");
	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test0");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test4");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test1");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test5");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test2");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test3");

	}

//****************************************************************************************


public void testPrioritizeUsingWalcottExampleLA0() {
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
		
		hgs.setSetCover(cover);

		hgs.setLooksAhead(0);
		
		hgs.prioritizeUsingHGS("coverage");
		covered = hgs.getPrioritizedSets();

		Iterator answerIterator = covered.iterator();

	//	System.out.println("*****\nSet of Prioritized Tests for prioritizeUsingWalcottExample:\n");
		
	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
	
	//	System.out.println("*****\n\n");

	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test3");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test0");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test5");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test2");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test4");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test1");

	}

public void testPrioritizeUsingWalcottExampleLA1() {
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
		
		hgs.setSetCover(cover);

		hgs.setLooksAhead(1);
		
		hgs.prioritizeUsingHGS("coverage");
		covered = hgs.getPrioritizedSets();

		Iterator answerIterator = covered.iterator();

	//	System.out.println("*****\nSet of Prioritized Tests for prioritizeUsingWalcottExample:\n");
		
	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
	
	//	System.out.println("*****\n\n");

	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test3");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test0");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test5");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test2");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test4");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test1");

	}
	
	public void testPrioritizeUsingWalcottExampleLA2() {
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
		
		hgs.setSetCover(cover);

		hgs.setLooksAhead(2);
		
		hgs.prioritizeUsingHGS("coverage");
		covered = hgs.getPrioritizedSets();

		Iterator answerIterator = covered.iterator();

	//	System.out.println("*****\nSet of Prioritized Tests for prioritizeUsingWalcottExample:\n");
		
	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
	
	//	System.out.println("*****\n\n");

	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test3");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test0");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test5");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test2");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test4");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test1");

	}

//****************************************************************************************

	public void testPrioritizeUsingTallamGuptaExampleLA0() {
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
		
		hgs.setSetCover(cover);
		hgs.setLooksAhead(0);
		hgs.prioritizeUsingHGS("coverage");
		covered = hgs.getPrioritizedSets();

		Iterator answerIterator = covered.iterator();

	//	System.out.println("*****\nPrioritized Set for prioritizeUsingTallumGuptaExample:\n");
		
	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
		
	//	System.out.println("*****\n\n");
	
	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test1");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test3");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test2");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test0");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test4");
	
	}	
	
		public void testPrioritizeUsingTallamGuptaExampleLA1() {
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
		
		hgs.setSetCover(cover);
		hgs.setLooksAhead(1);
		hgs.prioritizeUsingHGS("coverage");
		covered = hgs.getPrioritizedSets();

		Iterator answerIterator = covered.iterator();

	//	System.out.println("*****\nPrioritized Set for prioritizeUsingTallumGuptaExample:\n");
		
	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
		
	//	System.out.println("*****\n\n");
	
	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test1");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test3");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test2");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test0");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test4");
	
	}	
		public void testPrioritizeUsingTallamGuptaExampleLA2() {
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
		
		hgs.setSetCover(cover);
		hgs.setLooksAhead(2);
		hgs.prioritizeUsingHGS("coverage");
		covered = hgs.getPrioritizedSets();

		Iterator answerIterator = covered.iterator();

	//	System.out.println("*****\nPrioritized Set for prioritizeUsingTallumGuptaExample:\n");
		
	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
		
	//	System.out.println("*****\n\n");
	
	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test1");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test3");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test2");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test0");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test4");
	
	}	

//****************************************************************************************

	public void testPrioritizeUsingHGSExampleLA0() {
		assertEquals(0, cover.getTestSubsets().size());
		assertEquals(0, cover.getRequirementSubsetUniverse().size());
		
		SingleTest test0 = new SingleTest("test0",0);
		SingleTest test1 = new SingleTest("test1",1);
		SingleTest test2 = new SingleTest("test2",2);
		SingleTest test3 = new SingleTest("test3",3);
		SingleTest test4 = new SingleTest("test4",4);
		SingleTest test5 = new SingleTest("test5",5);		SingleTest test6 = new SingleTest("test6",6);

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
	
		hgs.setSetCover(cover);
		hgs.setLooksAhead(0);
		hgs.prioritizeUsingHGS("coverage");
		covered = hgs.getPrioritizedSets();
		
		Iterator answerIterator = covered.iterator();
	//	System.out.println("*****\nPrioritized Set for PrioritizeUsingHGSExample:\n");
	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
	//	System.out.println("*****\n\n");
	
	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test4");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test5");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test0");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test2");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test1");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test3");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test6");
	
	}
	
		public void testPrioritizeUsingHGSExampleLA1() {
		assertEquals(0, cover.getTestSubsets().size());
		assertEquals(0, cover.getRequirementSubsetUniverse().size());
		
		SingleTest test0 = new SingleTest("test0",0);
		SingleTest test1 = new SingleTest("test1",1);
		SingleTest test2 = new SingleTest("test2",2);
		SingleTest test3 = new SingleTest("test3",3);
		SingleTest test4 = new SingleTest("test4",4);
		SingleTest test5 = new SingleTest("test5",5);		SingleTest test6 = new SingleTest("test6",6);

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
	
		hgs.setSetCover(cover);
		hgs.setLooksAhead(1);
		hgs.prioritizeUsingHGS("coverage");
		covered = hgs.getPrioritizedSets();
		
		Iterator answerIterator = covered.iterator();
	//	System.out.println("*****\nPrioritized Set for PrioritizeUsingHGSExample:\n");
	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
	//	System.out.println("*****\n\n");
	
	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test4");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test5");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test0");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test2");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test1");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test3");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test6");
	
	}
	
		public void testPrioritizeUsingHGSExampleLA2() {
		assertEquals(0, cover.getTestSubsets().size());
		assertEquals(0, cover.getRequirementSubsetUniverse().size());
		
		SingleTest test0 = new SingleTest("test0",0);
		SingleTest test1 = new SingleTest("test1",1);
		SingleTest test2 = new SingleTest("test2",2);
		SingleTest test3 = new SingleTest("test3",3);
		SingleTest test4 = new SingleTest("test4",4);
		SingleTest test5 = new SingleTest("test5",5);		SingleTest test6 = new SingleTest("test6",6);

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
	
		hgs.setSetCover(cover);
		hgs.setLooksAhead(2);
		hgs.prioritizeUsingHGS("coverage");
		covered = hgs.getPrioritizedSets();
		
		Iterator answerIterator = covered.iterator();
	//	System.out.println("*****\nPrioritized Set for PrioritizeUsingHGSExample:\n");
	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
	//	System.out.println("*****\n\n");
	
	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test4");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test5");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test3");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test0");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test2");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test1");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test6");
	
	}
	
/**********************************************************************************************************************
***********************************************************************************************************************

						BEGIN THE TIME METRIC TESTS
					***********************************************************************************************************************
**********************************************************************************************************************/

/*public void testReduceUsingTallamGuptaExampleTimeLA0() {
		System.out.println(System.getProperty("user.dir"));
		hgs.setSetCover(SetCover.constructSetCoverFromBinary("../raise/data/raise/reduce/setCovers/TallamGuptaCoverage.dat","../raise/data/raise/reduce/setCovers/TallamGuptaTime.dat"));
		hgs.setLooksAhead(0);
		hgs.reduceUsingHGS("time");
		covered = hgs.getCoverPickSets();

		Iterator answerIterator = covered.iterator();

	//	System.out.println("*****\nCovering Set for reduceUsingTallumGuptaExample:\n");
		
	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
		
	//	System.out.println("*****\n\n");

	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"SingleTest1");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"SingleTest3");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"SingleTest2");
	}	

public void testReduceUsingHGSExampleTimeLA0() {
		hgs.setSetCover(SetCover.constructSetCoverFromBinary("../raise/data/raise/reduce/setCovers/HGSCoverage.dat","../raise/data/raise/reduce/setCovers/HGSTime.dat"));
		hgs.setLooksAhead(0);
		hgs.reduceUsingHGS("time");
		covered = hgs.getCoverPickSets();

		Iterator answerIterator = covered.iterator();

		//System.out.println("*****\nCovering Set for reduceUsingTallumGuptaExample:\n");
		
	//	while ( answerIterator.hasNext()){
	
		//	SingleTest currentTest = (SingleTest) answerIterator.next();

		//	System.out.println(currentTest.toString());
	//	}
		
	//	System.out.println("*****\n\n");

	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"SingleTest4");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"SingleTest2");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"SingleTest3");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"SingleTest5");
	}		
	
	public void testPrioritizeUsingHGSExampleTimeLA0() {
		hgs.setSetCover(SetCover.constructSetCoverFromBinary("../raise/data/raise/reduce/setCovers/HGSCoverage.dat","../raise/data/raise/reduce/setCoverse/HGSTime.dat"));
		hgs.setLooksAhead(0);
		hgs.prioritizeUsingHGS("time");
		covered = hgs.getPrioritizedSets();

		Iterator answerIterator = covered.iterator();

	//		System.out.println("*****\nCovering Set for prioritizeUsingHGSExample:\n");
		
	//		while (answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//		}
		
	//		System.out.println("*****\n\n");
	
		cover = SetCover.constructSetCoverFromBinary("../raise/data/raise/reduce/setCovers/HGSCoverage.dat","../raise/data/raise/reduce/setCovers/HGSTime.dat");
	
		assertTrue(cover.coversRequirementSubsetUniverse(covered));

		assertEquals( ( (SingleTest) answerIterator.next()).getName(),"SingleTest4");
		assertEquals( ( (SingleTest) answerIterator.next()).getName(),"SingleTest2");	
		assertEquals( ( (SingleTest) answerIterator.next()).getName(),"SingleTest3");	
		assertEquals( ( (SingleTest) answerIterator.next()).getName(),"SingleTest5");
		assertEquals( ( (SingleTest) answerIterator.next()).getName(),"SingleTest0");
		assertEquals( ( (SingleTest) answerIterator.next()).getName(),"SingleTest6");	
		assertEquals( ( (SingleTest) answerIterator.next()).getName(),"SingleTest1");
	}				
	
public void testReduceUsingHugeExampleRatioLA0() {
		hgs.setSetCover(SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rlll1","/home/adam/Kanonizo/data/time-rlll1"));
		hgs.setLooksAhead(0);
		hgs.reduceUsingHGS("ratio");
		covered = hgs.getCoverPickSets();

		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rlll1","/home/adam/Kanonizo/data/time-rlll1");
		
		assertTrue(cover.coversRequirementSubsetUniverse(covered));
		
		Iterator answerIterator = covered.iterator();

		System.out.println("*****\nCovering Set for reduceUsingHugeExample:\n");
		
		while ( answerIterator.hasNext()){
	
			SingleTest currentTest = (SingleTest) answerIterator.next();

			System.out.println(currentTest.toString());
		}
		
		System.out.println("*****\n\n");
		

	}	*/	
}
