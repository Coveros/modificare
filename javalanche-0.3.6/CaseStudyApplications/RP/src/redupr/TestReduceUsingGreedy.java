
/*---------------------------------------------------------------------
 *  File: $Id: TestReduceUsingGreedy.java,v 1.26 2007/08/01 smitha Exp $   
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

public class TestReduceUsingGreedy extends TestCase
{
	SetCover cover;
	LinkedHashSet covered;
	ReduceUsingGreedy g;

	 /*
     *  Required constructor.
     *  
     *  @author Adam M. Smith 7/02/2007
     */
    public TestReduceUsingGreedy(String name)
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
	 	g = new ReduceUsingGreedy();
	 }
	 
	 
	/*public void testGreedyPrioritization()
	{
		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rmmm5","/home/adam/Kanonizo/data/time-rmmm5");
		g.setSetCover(cover);
		g.prioritizeUsingGreedy("ratio");
		covered = g.getPrioritizedSets();
		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rmmm5","/home/adam/Kanonizo/data/time-rmmm5");
		assertTrue(cover.coversRequirementSubsetUniverse(covered));
		assertTrue(cover.getTestSubsets().size() == covered.size());
		
		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rmmm5","/home/adam/Kanonizo/data/time-rmmm5");
		g.setSetCover(cover);
		g.prioritizeUsingGreedy("coverage");
		covered = g.getPrioritizedSets();
		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rmmm5","/home/adam/Kanonizo/data/time-rmmm5");
		assertTrue(cover.coversRequirementSubsetUniverse(covered));
		assertTrue(cover.getTestSubsets().size() == covered.size());
		
		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rmmm5","/home/adam/Kanonizo/data/time-rmmm5");
		g.setSetCover(cover);
		g.prioritizeUsingGreedy("time");
		covered = g.getPrioritizedSets();
		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rmmm5","/home/adam/Kanonizo/data/time-rmmm5");
		assertTrue(cover.coversRequirementSubsetUniverse(covered));
		assertTrue(cover.getTestSubsets().size() == covered.size());
		
		//Iterator answerIterator = covered.iterator();

//		System.out.println("*****\nCovering Set for reduceUsingHugeExample:\n");
		
	//	while ( answerIterator.hasNext()){
	
		//	SingleTest currentTest = (SingleTest) answerIterator.next();

			//System.out.println(currentTest.toString());
		//}
		
		//System.out.println("*****\n\n");
	}
		
	public void testGreedyReduction()
	{
		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rmmm5","/home/adam/Kanonizo/data/time-rmmm5");
		g.setSetCover(cover);
		g.reduceUsingGreedy("ratio");
		covered = g.getCoverPickSets();
		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rmmm5","/home/adam/Kanonizo/data/time-rmmm5");
		assertTrue(cover.coversRequirementSubsetUniverse(covered));
		
		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rmmm5","/home/adam/Kanonizo/data/time-rmmm5");
		g.setSetCover(cover);
		g.reduceUsingGreedy("time");
		covered = g.getCoverPickSets();
		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rmmm5","/home/adam/Kanonizo/data/time-rmmm5");
		assertTrue(cover.coversRequirementSubsetUniverse(covered));
		
		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rmmm5","/home/adam/Kanonizo/data/time-rmmm5");
		g.setSetCover(cover);
		g.reduceUsingGreedy("coverage");
		covered = g.getCoverPickSets();
		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rmmm5","/home/adam/Kanonizo/data/time-rmmm5");
		assertTrue(cover.coversRequirementSubsetUniverse(covered));
				
		//Iterator answerIterator = covered.iterator();

//		System.out.println("*****\nCovering Set for reduceUsingHugeExample:\n");
		
	//	while ( answerIterator.hasNext()){
	
		//	SingleTest currentTest = (SingleTest) answerIterator.next();

			//System.out.println(currentTest.toString());
		//}
		
		//System.out.println("*****\n\n");
	}
		
		
		
		
	/*
	 * The following tests are for the greedy reduction/prioritization methods in 
	 * SetCover that have been commented out.
	 *
	public void testSameReductionValuesAsKapfhammerImplementation()
	{	
		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rsss5","/home/adam/Kanonizo/data/time-rsss5");
		g.setSetCover(cover);
		g.reduceUsingGreedy("ratio");
		covered = g.getCoverPickSets();
		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rsss5","/home/adam/Kanonizo/data/time-rsss5");
		Set coverSet = cover.reduceUsingGreedy("ratio");
		//assertTrue(covered.contains(coverSet) && coverSet.contains(covered) );
		
		/* cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rmmm5","/home/adam/Kanonizo/data/time-rmmm5");
		g.setSetCover(cover);
		g.reduceUsingGreedy("time");
		covered = g.getCoverPickSets();
		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rmmm5","/home/adam/Kanonizo/data/time-rmmm5");
		assertTrue(cover.coversRequirementSubsetUniverse(covered));
		
		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rmmm5","/home/adam/Kanonizo/data/time-rmmm5");
		g.setSetCover(cover);
		g.reduceUsingGreedy("coverage");
		covered = g.getCoverPickSets();
		cover = SetCover.constructSetCoverFromBinary("/home/adam/Kanonizo/data/matrix-rmmm5","/home/adam/Kanonizo/data/time-rmmm5");
		assertTrue(cover.coversRequirementSubsetUniverse(covered));
	
	
	/////////////////////////////////////////////////////////////////////////////////////
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
		
	
		Iterator answerIterator = coverSet.iterator();
		System.out.println("*****\nKapfhammers Covering Set:\n");
		while ( answerIterator.hasNext())
		{
			SingleTest currentTest = (SingleTest) answerIterator.next();
			System.out.println(currentTest.toString());
		}
		System.out.println("*****\n\n");
		
		answerIterator = covered.iterator();
		System.out.println("*****\n My Covering Set:\n");
		while ( answerIterator.hasNext())
		{
			SingleTest currentTest = (SingleTest) answerIterator.next();
			System.out.println(currentTest.toString());
		}
		System.out.println("*****\n\n");
	
	}
		*/
}
