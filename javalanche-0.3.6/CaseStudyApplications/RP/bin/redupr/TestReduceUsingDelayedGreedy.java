/*---------------------------------------------------------------------
 *  File: $Id: TestReduceUsingDelayedGreedy.java,v 1.26 2007/07/02 13:36:17 smitha Exp $   
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
 */

public class TestReduceUsingDelayedGreedy extends TestCase
{
	SetCover cover;
	LinkedHashSet covered;
	ReduceUsingDelayedGreedy dg;

	 /*
     *  Required constructor.
     *  
     *  @author Adam M. Smith 7/02/2007
     */
    public TestReduceUsingDelayedGreedy(String name)
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
	 	dg = new ReduceUsingDelayedGreedy();
	 }
	 

	/**
	 * This method tests the use of objects and references.  It basically served as proof
	 * of some of the things regarding java objects that I wasn't sure about.  
	 *
	 * Changed to use assert Dec
	 */
	 
	 public void testSomeSetCoverStuffTallamGuptaExample() {
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
	 
	 
	 	LinkedHashSet ct = STS4.getRequirementSubsetSet();
	 	
	 	Iterator r = ct.iterator();
	 	
	 	RequirementSubset rs = (RequirementSubset) r.next();
	 		 	
	 	Iterator t4cri = rs.getCoveringTests().iterator();
	 		 		 	
	 	//System.out.println("Here are the covering tests from the RS in the STS before removal");
	 	
	 	//while (t4cri.hasNext()) {
	 		//System.out.println(t4cri.next());
	 	//}
		
		assertEquals( ((SingleTest) t4cri.next()).getName(),"test2");	 	
		assertEquals( ((SingleTest) t4cri.next()).getName(),"test4");

	 	Iterator r4i = req4.getCoveringTests().iterator();

	 	//System.out.println("Here are the covering tests directly from the RS before removal");
	 	
	 	// while (r4i.hasNext()){
	 	//		System.out.println(r4i.next());
	 	// }	 
	 	
	 	assertEquals( ((SingleTest) r4i.next()).getName(),"test2");	 	
		assertEquals( ((SingleTest) r4i.next()).getName(),"test4");

	 	Iterator newit = cover.getTestSubsets().iterator();
	 	SingleTestSubset thisSTS = null;
	 	

		//The point of most of the rest of this code is to remove test2 
		// SingleTests from the cover and then see if they were removed in 
		// other places

		// This searches for the test "test4" and sets the SingleTestSubet that 
		// contains it to thisSTS.  It seems like there should be a better way to 
		// do this... maybe not.  Oh yeah, there is no get for LinkedHashSet. So... 
		// nope. 

	 	while (newit.hasNext()) {
	 		thisSTS = (SingleTestSubset) newit.next();
	 		if (thisSTS.getTest().getName().equals("test4"))
				break;
	 	}
	 	
		//assertFalse(thisSTS.getTest().getName().equals("test4"));
	 	
	 	Iterator newnewit = thisSTS.getRequirementSubsetSet().iterator();
	 	
	 	RequirementSubset thisreq = null;
	 	
	 	while (newnewit.hasNext()){
	 		thisreq = (RequirementSubset) newnewit.next();
	 		
	 		if (thisreq.getName().equals("req4"));
	 			break;
	 	}
	 	
	 	//remove
	 	thisreq.removeCoveringTest(test4);
	 	 
	  	ct = STS4.getRequirementSubsetSet();
	 	
	 	r = ct.iterator();
	 	
	 	rs = (RequirementSubset) r.next();
	 	
	 	t4cri = rs.getCoveringTests().iterator();
	 		 	
	 //	System.out.println("Here are the covering tests from the RS in the STS after removal");
	 	
	 //	while (t4cri.hasNext()) {
	 	//	System.out.println(t4cri.next());
	 	//}

		assertEquals(((SingleTest) t4cri.next()).getName(),"test2");
	 	
	 	r4i = req4.getCoveringTests().iterator();
	 //	System.out.println("Here are the covering tests directly from the RS after removal");
	 	
	 //	while (r4i.hasNext()){
	 //		System.out.println(r4i.next());
	 //	}
	 
	 assertEquals(((SingleTest) r4i.next()).getName(),"test2");
	 }
	 
	
	public void testReduceUsingTallamGuptaExample() {
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
		
		dg.setSetCover(cover);
		
		dg.reduceUsingDG("coverage");
		covered = dg.getCoverPickSets();

		Iterator answerIterator = covered.iterator();

	//	System.out.println("*****\nCovering Set for reduceUsingTallumGuptaExample:\n");
	//	
	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
		
	//	System.out.println("*****\n\n");

	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test1");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test2");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test3");
	}	



	public void testReduceUsingHGSExample() {
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
	
		dg.setSetCover(cover);
		
		dg.reduceUsingDG("coverage");
		covered = dg.getCoverPickSets();
		
		Iterator answerIterator = covered.iterator();

	//	System.out.println("*****\nCovering Set for reduceUsingHGS:\n");
	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
	//	System.out.println("*****\n\n");
	
		
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test4");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test2");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test0");

	} 



	public void testReduceUsingWalcottExample() {
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
		
		dg.setSetCover(cover);
		
		dg.reduceUsingDG("coverage");
		covered = dg.getCoverPickSets();
		
		Iterator answerIterator = covered.iterator();

	//	System.out.println("*****\nSet of Covering Tests For Walcott Reduction Example:\n");

	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
	//	System.out.println("*****\n\n");

	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test0");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test3");
}

	public void testReduceUsingAdamSmithFirstExample() {
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
		
		dg.setSetCover(cover);
		dg.reduceUsingDG("coverage");
		covered = dg.getCoverPickSets();

		Iterator answerIterator = covered.iterator();

	//	System.out.println("*****\nSet of Covering Tests for reduceUsingAdamSmithFirstExample:\n");

	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//}
   //  	System.out.println("*****\n\n");	
		assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test0");	
		assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test1");
		assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test4");	
	
	}
	

	public void testPrioritizeUsingAdamSmithFirstExample() {
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
		
		dg.setSetCover(cover);
		dg.prioritizeUsingDG("coverage");
		covered = dg.getPrioritizedSets();

		Iterator answerIterator = covered.iterator();

	//	System.out.println("*****\nSet of Prioritized Tests for prioritizeUsingAdamSmithFirstExample:\n");

	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
		
	//	System.out.println("*****\n\n");

	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test0");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test1");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test4");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test5");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test2");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test3");
	}

public void testPrioritizeUsingWalcottExample() {
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
		
		dg.setSetCover(cover);
		dg.prioritizeUsingDG("coverage");
		covered = dg.getPrioritizedSets();

		Iterator answerIterator = covered.iterator();

	//	System.out.println("*****\nSet of Prioritized Tests for prioritizeUsingWalcottExample:\n");
		
	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
	
	//	System.out.println("*****\n\n");
	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test0");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test3");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test2");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test5");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test4");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test1");

	}

	public void testPrioritizeUsingTallamGuptaExample() {
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
		
		dg.setSetCover(cover);
		dg.prioritizeUsingDG("coverage");
		covered = dg.getPrioritizedSets();

		Iterator answerIterator = covered.iterator();

	//	System.out.println("*****\nPrioritized Set for prioritizeUsingTallumGuptaExample:\n");
		
	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
		
	//	System.out.println("*****\n\n");

	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test1");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test2");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test3");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test0");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test4");

	}	



	public void testPrioritizeUsingHGSExample() {
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
	
		dg.setSetCover(cover);
		dg.prioritizeUsingDG("coverage");
		covered = dg.getPrioritizedSets();
		
		Iterator answerIterator = covered.iterator();
	//	System.out.println("*****\nPrioritized Set for PrioritizeUsingHGSExample:\n");
	//	while ( answerIterator.hasNext()){
	
	//		SingleTest currentTest = (SingleTest) answerIterator.next();

	//		System.out.println(currentTest.toString());
	//	}
	//	System.out.println("*****\n\n");
	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test4");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test2");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test0");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test1");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test5");	
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test3");
	assertEquals( ( (SingleTest) answerIterator.next()).getName(),"test6");
	}
/*******************************************************************************************************************
																			Time Tests
*******************************************************************************************************************
*/

/*public void testReduceUsingTallamGuptaExampleTime()
	{
		// this example never is without a test or requirement eliminiation 
		// so the answer will be the same as coverage
		dg.setSetCover(SetCover.constructSetCoverFromBinary("data/diatoms/reduce/synthetic_test_data/testMatrixTallamGuptaExample","data/diatoms/reduce/synthetic_test_data/testTimeTallamGuptaExample"));
			
		dg.reduceUsingDG("time");
		covered = dg.getCoverPickSets();
	
		Iterator answerIterator = covered.iterator();

		//System.out.println("*****\nCovering Set for reduceUsingTallumGuptaExample:\n");
			
		//	while ( answerIterator.hasNext()){
		
		//		SingleTest currentTest = (SingleTest) answerIterator.next();
	
		//		System.out.println(currentTest.toString());
		//	}
			
		//	System.out.println("*****\n\n");

		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("SingleTest1"));
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("SingleTest2"));
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("SingleTest3"));
	}
	
	public void testReduceUsingHGSExampleTime() 
	{
	
		dg.setSetCover(SetCover.constructSetCoverFromBinary("data/diatoms/reduce/synthetic_test_data/testMatrixHGSExample","data/diatoms/reduce/synthetic_test_data/testTimeHGSExample"));
		
		dg.reduceUsingDG("time");
			covered = dg.getCoverPickSets();
	
			Iterator answerIterator = covered.iterator();
/*
		System.out.println("*****\nCovering Set for reduceUsingTallumGuptaExample:\n");
			
			while ( answerIterator.hasNext()){
		
				SingleTest currentTest = (SingleTest) answerIterator.next();
	
				System.out.println(currentTest.toString());
			}
			
			System.out.println("*****\n\n");
*//*
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("SingleTest4"));
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("SingleTest2"));
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("SingleTest5"));
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("SingleTest3"));
	}
	
public void testPrioritizeUsingHGSExampleTime() 
	{
	
		dg.setSetCover(SetCover.constructSetCoverFromBinary("data/diatoms/reduce/synthetic_test_data/testMatrixHGSExample","data/diatoms/reduce/synthetic_test_data/testTimeHGSExample"));
		
		dg.prioritizeUsingDG("time");
			covered = dg.getPrioritizedSets();
	
			Iterator answerIterator = covered.iterator();

			//System.out.println("*****\nCovering Set for reduceUsingTallumGuptaExample:\n");
			
		//	while ( answerIterator.hasNext()){
		
		//		SingleTest currentTest = (SingleTest) answerIterator.next();
	
		//		System.out.println(currentTest.toString());
		//	}
			
		//	System.out.println("*****\n\n");

		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("SingleTest4"));
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("SingleTest2"));
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("SingleTest5"));
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("SingleTest3"));
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("SingleTest0"));
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("SingleTest6"));
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("SingleTest1"));
	}
public void testPrioritizeUsingTallamGuptaExampleTime()
	{
		// this example never is without a test or requirement eliminiation 
		// so the answer will be the same as coverage
		dg.setSetCover(SetCover.constructSetCoverFromBinary("data/diatoms/reduce/synthetic_test_data/testMatrixTallamGuptaExample","data/diatoms/reduce/synthetic_test_data/testTimeTallamGuptaExample"));
			
		dg.prioritizeUsingDG("time");
		covered = dg.getPrioritizedSets();
	
		Iterator answerIterator = covered.iterator();
/*
		System.out.println("*****\nCovering Set for prioritizeUsingTallumGuptaExample:\n");
			
			while ( answerIterator.hasNext()){
		
				SingleTest currentTest = (SingleTest) answerIterator.next();
	
				System.out.println(currentTest.toString());
			}
			
			System.out.println("*****\n\n");
*//*
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("SingleTest1"));
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("SingleTest2"));
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("SingleTest3"));
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("SingleTest0"));
		assertTrue( ((SingleTest) answerIterator.next()).getName().equals("SingleTest4"));		
	}*/

	public void testLinkedHashSetSize0()
	{
		LinkedHashSet test = new LinkedHashSet();
		assertTrue(test.size()==0);
	}

	/*public void testNoNullsMatrixrlll5() 
	{
		dg.setSetCover(SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rlll5","/home/adam/Kanonizo/data/time-rlll5"));
		
		Iterator testSubsetsIterator = dg.getSetCover().getTestSubsets().iterator();
		
		while (testSubsetsIterator.hasNext())
		{
			assertTrue(!((SingleTestSubset)testSubsetsIterator.next()).equals(null));
		}
		
	}*/

	

// 	public void testReduceUsingDGHugeExampleCoverage() 
// 	{
// 		dg.setSetCover(SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rlll5","/home/adam/Kanonizo/data/time-rlll5"));

// 		dg.reduceUsingDG("coverage");
		
// 		covered = dg.getCoverPickSets();

// 		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rlll5","/home/adam/Kanonizo/data/time-rlll5");
		
// 		assertTrue(cover.coversRequirementSubsetUniverse(covered));
		
// 		//Iterator answerIterator = covered.iterator();

// 	//	System.out.println("*****\nCovering Set for reduceUsingHugeExample:\n");
		
// 		//while ( answerIterator.hasNext()){
	
// 	//		SingleTest currentTest = (SingleTest) answerIterator.next();

// 		//	System.out.println(currentTest.toString());
// 		//}
		
// 		//System.out.println("*****\n\n");
// 	}
	
// 	public void testReduceUsingDGHugeExampleTime() 
// 	{
// 		dg.setSetCover(SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rlll5","/home/adam/Kanonizo/data/time-rlll5"));

// 		dg.reduceUsingDG("time");
		
// 		covered = dg.getCoverPickSets();

// 		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rlll5","/home/adam/Kanonizo/data/time-rlll5");
		
// 		assertTrue(cover.coversRequirementSubsetUniverse(covered));
		
// 		//Iterator answerIterator = covered.iterator();

// 	//	System.out.println("*****\nCovering Set for reduceUsingHugeExample:\n");
		
// 		//while ( answerIterator.hasNext()){
	
// 	//		SingleTest currentTest = (SingleTest) answerIterator.next();

// 		//	System.out.println(currentTest.toString());
// 		//}
		
// 		//System.out.println("*****\n\n");
// 	}
// 	public void testReduceUsingDGHugeExampleRatio() 
// 	{
// 		dg.setSetCover(SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rlll5","/home/adam/Kanonizo/data/time-rlll5"));

// 		dg.reduceUsingDG("ratio");
		
// 		covered = dg.getCoverPickSets();

// 		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rlll5","/home/adam/Kanonizo/data/time-rlll5");
		
// 		assertTrue(cover.coversRequirementSubsetUniverse(covered));
		
// 		//Iterator answerIterator = covered.iterator();

// 	//	System.out.println("*****\nCovering Set for reduceUsingHugeExample:\n");
		
// 		//while ( answerIterator.hasNext()){
	
// 	//		SingleTest currentTest = (SingleTest) answerIterator.next();

// 		//	System.out.println(currentTest.toString());
// 		//}
		
// 		//System.out.println("*****\n\n");
// 	}
	
	
	public void testIsSubsetActualSubset()
	{
		LinkedHashSet superSet = new LinkedHashSet();
		LinkedHashSet subSet = new LinkedHashSet();
		
		RequirementSubset req0 = new RequirementSubset("req0",0);
		RequirementSubset req1 = new RequirementSubset("req1",1);
		RequirementSubset req2 = new RequirementSubset("req2",2);
		RequirementSubset req3 = new RequirementSubset("req3",3);
		RequirementSubset req4 = new RequirementSubset("req4",4);
		RequirementSubset req5 = new RequirementSubset("req5",5);
		RequirementSubset req6 = new RequirementSubset("req6",6);
		RequirementSubset req7 = new RequirementSubset("req7",7);
		
		superSet.add(req0);
		superSet.add(req1);
		superSet.add(req2);
		superSet.add(req3);
		superSet.add(req4);
		superSet.add(req5);
		superSet.add(req6);
		superSet.add(req7);
		
		subSet.add(req0);
		subSet.add(req1);
		subSet.add(req2);
		subSet.add(req3);
		subSet.add(req4);
		subSet.add(req5);
		subSet.add(req6);
		
		assertTrue(ReduceUsingDelayedGreedy.isSubset(superSet,subSet));
	}
	
		public void testIsSubsetEqualSets()
	{
		LinkedHashSet superSet = new LinkedHashSet();
		LinkedHashSet subSet = new LinkedHashSet();
		
		RequirementSubset req0 = new RequirementSubset("req0",0);
		RequirementSubset req1 = new RequirementSubset("req1",1);
		RequirementSubset req2 = new RequirementSubset("req2",2);
		RequirementSubset req3 = new RequirementSubset("req3",3);
		RequirementSubset req4 = new RequirementSubset("req4",4);
		RequirementSubset req5 = new RequirementSubset("req5",5);
		RequirementSubset req6 = new RequirementSubset("req6",6);
		RequirementSubset req7 = new RequirementSubset("req7",7);
		
		superSet.add(req0);
		superSet.add(req1);
		superSet.add(req2);
		superSet.add(req3);
		superSet.add(req4);
		superSet.add(req5);
		superSet.add(req6);
		superSet.add(req7);
		
		subSet.add(req0);
		subSet.add(req1);
		subSet.add(req2);
		subSet.add(req3);
		subSet.add(req4);
		subSet.add(req5);
		subSet.add(req6);
		subSet.add(req7);
		
		assertTrue(ReduceUsingDelayedGreedy.isSubset(superSet,subSet));
	}
	
	public void testIsSubsetSubsetBigger()
	{
		LinkedHashSet superSet = new LinkedHashSet();
		LinkedHashSet subSet = new LinkedHashSet();
		
		RequirementSubset req0 = new RequirementSubset("req0",0);
		RequirementSubset req1 = new RequirementSubset("req1",1);
		RequirementSubset req2 = new RequirementSubset("req2",2);
		RequirementSubset req3 = new RequirementSubset("req3",3);
		RequirementSubset req4 = new RequirementSubset("req4",4);
		RequirementSubset req5 = new RequirementSubset("req5",5);
		RequirementSubset req6 = new RequirementSubset("req6",6);
		RequirementSubset req7 = new RequirementSubset("req7",7);
		
		superSet.add(req0);
		superSet.add(req1);
		superSet.add(req2);
		superSet.add(req3);
		superSet.add(req4);
		superSet.add(req5);
		superSet.add(req6);
		
		subSet.add(req0);
		subSet.add(req1);
		subSet.add(req2);
		subSet.add(req3);
		subSet.add(req4);
		subSet.add(req5);
		subSet.add(req6);
		subSet.add(req7);
		
		assertFalse(ReduceUsingDelayedGreedy.isSubset(superSet,subSet));
	}

	public void testIsSubsetNotASubset()
	{
		LinkedHashSet superSet = new LinkedHashSet();
		LinkedHashSet subSet = new LinkedHashSet();
		
		RequirementSubset req0 = new RequirementSubset("req0",0);
		RequirementSubset req1 = new RequirementSubset("req1",1);
		RequirementSubset req2 = new RequirementSubset("req2",2);
		RequirementSubset req3 = new RequirementSubset("req3",3);
		RequirementSubset req4 = new RequirementSubset("req4",4);
		RequirementSubset req5 = new RequirementSubset("req5",5);
		RequirementSubset req6 = new RequirementSubset("req6",6);
		RequirementSubset req7 = new RequirementSubset("req7",7);
		RequirementSubset req8 = new RequirementSubset("req8",8);
		
		superSet.add(req0);
		superSet.add(req7);
		superSet.add(req2);
		superSet.add(req3);
		superSet.add(req4);
		superSet.add(req5);
		superSet.add(req6);
		
		subSet.add(req0);
		subSet.add(req1);
		subSet.add(req2);
		subSet.add(req3);
		subSet.add(req4);
		subSet.add(req5);
		subSet.add(req6);
		
		assertFalse(ReduceUsingDelayedGreedy.isSubset(superSet,subSet));
	}
	
}

