 //	Code for the K-Way test suite reduction algorithm
 //
 //	@author: Adam M. Smith	July 2, 2007
 //


package redupr;

//import diatoms.monitor.*;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Iterator;
import java.util.*;

import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class ReduceUsingKWay {

// Create a SetCover object
	private SetCover cover;
	
	// The reduced suite
	LinkedHashSet coverPickSets;
	
	LinkedHashSet prioritizedSets;


	// 
	// 	Default constructor
	// 
	//    @author Adam M. Smith 07/13/2007
	

	public ReduceUsingKWay() {
		cover = new SetCover();
		coverPickSets = new LinkedHashSet();
	}


   // 
	//	This constructor takes a Setcover object as a parameter
	//
	// @author Adam M. Smith 07/13/2007
	//
	public ReduceUsingKWay(SetCover passedCover) {
		cover = passedCover;
		coverPickSets = new LinkedHashSet();
	}

   
   //
   // Set the SetCover object
	//
	// @author Adam M. Smith 07/13/2007
   

	public void setSetCover(SetCover inCover) {
	
		cover = inCover;

	}
   
	//
   // get the coverPickSets 
	//
	// @author Adam M. Smith 07/13/2007
   
  	public LinkedHashSet getCoverPickSets() {
		return coverPickSets;
	}

	//
   // get the prioritizedSets 
	//
	// @author Adam M. Smith 07/13/2007
    
   public LinkedHashSet getPrioritizedSets() {
		return prioritizedSets;
	}
		
	public String getCoveringTestSetString(String separator)
	{
		String output="";
		Iterator coverPickSetsIterator = this.coverPickSets.iterator();
		
		if (coverPickSetsIterator.hasNext())
		{
			int index = (((SingleTest) coverPickSetsIterator.next()).getIndex()+1);
			output = (output+index);
		}
		
		while (coverPickSetsIterator.hasNext())	
		{
			int index = (((SingleTest) coverPickSetsIterator.next()).getIndex()+1);
			output = (output+separator+index );
		}
		
		return output;
	
	}
		
	public String getPrioritizedSetString(String separator)
	{	
		String output="";
		Iterator prioritizedSetsIterator = this.prioritizedSets.iterator();
		
		if (prioritizedSetsIterator.hasNext())
		{
			int index = (((SingleTest) prioritizedSetsIterator.next()).getIndex()+1);
			output = (output+index);
		}
		
		while (prioritizedSetsIterator.hasNext())	
		{
			int index = (((SingleTest) prioritizedSetsIterator.next()).getIndex()+1);
			output = (output +separator+index);
		}
		
		return output;
	}
		
	//--------------------------------------------------------------------
	//  This method removes every instance of the passed RequirementSubset
	//  from the singleTestSubsetSet
	//-------------------------------------------------------------------
	private void removeRequirementFromSTSS(RequirementSubset req){
		Iterator setIterator = cover.getTestSubsets().iterator();
		
		while (setIterator.hasNext()) {
			SingleTestSubset currentSTS = (SingleTestSubset) setIterator.next();
				if (currentSTS.getRequirementSubsetSet().contains(req))
					currentSTS.removeRequirementSubset(req);
		
		}
		
	}
		
	//--------------------------------------------------------------------
	//  This method adds a test to the coverPicksSet, removes that STS from 
	//  the cover, and removes the test from the reqs that are covered by it
	//-------------------------------------------------------------------	
		
	private void selectTest(SingleTestSubset test){
		// add the SingleTest to the coverPickSets
		coverPickSets.add(test.getTest());
		
		// remove the SingleTestSubset from the SetCover object
		cover.removeSingleTestSubset(test);
		
		// extract an iterator over the RequirementSubset that the SingleTestSubset covers
		Iterator reqIt = test.getRequirementSubsetSet().iterator();
		
		// While there are more RequirementSubsets
		while (reqIt.hasNext()){
			//Get the current RequirementSubset
			RequirementSubset currentReq = (RequirementSubset) reqIt.next();
			
			//Remove it from the SingleTestSubsets
			removeRequirementFromSTSS(currentReq);
			
			// Remove it from the cover.
			cover.removeRequirementSubset(currentReq);
				
		}
	
	
	}	
				
	private void printList(LinkedHashSet list){
		Iterator it = list.iterator();
		
		while (it.hasNext()){
			System.out.println(it.next());
		}
	}
	
	//---------------------------------------------------------------------
	// This method performs the KW reduction algorithm
	//---------------------------------------------------------------------
	public void reduceUsingKW(String metric) 
	{
	
		// Check to see if the metric value is legit.
		if (!metric.equals("coverage") && !metric.equals("time") && !metric.equals("ratio"))
		{
			System.out.println("\nInvalid metric type.\nProgram terminated");  
			System.exit(0);
		}
	
		coverPickSets = new LinkedHashSet();
		LinkedHashSet testsToCheck = new LinkedHashSet();

		SingleTestSubset test1 = null;
		SingleTestSubset test2 = null;
		
		Pair bestPairSoFar = null;
		Pair competitorPair = null;
		Pair bestPairOverall = null;
	
		// While the requirements are not all covered
		while (!cover.getRequirementSubsetUniverse().isEmpty()){
			
			// rebuild the testsToCheck list with all of the remaining SingleTestSubsets
			testsToCheck.clear();		
			testsToCheck.addAll(cover.getTestSubsets());
						
			//In this loop I need to find the best overall pair
			
			while (!testsToCheck.isEmpty()) {
				//Extract an iterator over testsToCheck
				Iterator STSIterator = testsToCheck.iterator();
				
				if (testsToCheck.size() == 1) {
					if (cover.getTestSubsets().size() == 1) {
						selectTest( (SingleTestSubset) STSIterator.next());
					}
					break;
				}
				
				// Make the first pair
				test1 = (SingleTestSubset) STSIterator.next();
				test2 = (SingleTestSubset) STSIterator.next();
				
				bestPairSoFar = new Pair(test1, test2);
			
				while (STSIterator.hasNext())
				{
					// get the new second test for the competitor pair
					test2 = (SingleTestSubset) STSIterator.next();
				
					// make the competitor pair
					competitorPair = new Pair(test1, test2);
				
					//compare the competitor pair
			
					if(metric.equals("coverage"))
					{		
						if (competitorPair.getNumReqsCovered() > bestPairSoFar.getNumReqsCovered()){
							bestPairSoFar = competitorPair;
						}
					}
				
					if(metric.equals("time"))
					{		
						if (competitorPair.getTime() < bestPairSoFar.getTime())
						{
							bestPairSoFar = competitorPair;
						}
					}
					if(metric.equals("ratio"))
					{	
						//System.out.println("competitor: " + competitorPair+"\nBest So Far: "+bestPairSoFar); 
					
						if (competitorPair.getRatio() > bestPairSoFar.getRatio()){
							bestPairSoFar = competitorPair;
						}
					}
				}
				if (bestPairOverall == null) {
					bestPairOverall = bestPairSoFar;
				}					
			
				else {
					
					if(metric.equals("coverage"))
					{
						if (bestPairSoFar.getNumReqsCovered() > bestPairOverall.getNumReqsCovered())
						{ 
							bestPairOverall = bestPairSoFar;
						}
					}
					
					if(metric.equals("time"))
					{
						if (bestPairSoFar.getTime() < bestPairOverall.getTime())
						{
							bestPairOverall = bestPairSoFar;
						}
					}
					
					if(metric.equals("ratio"))
					{

						if (bestPairSoFar.getRatio() > bestPairOverall.getRatio())
						{
							bestPairOverall = bestPairSoFar;
						}
					}
				
				}
				
			testsToCheck.remove(test1);
			} //Closes testsToCheck empty loop
			
			if (!cover.getTestSubsets().isEmpty()){
				// After you have the best Pair, select the tests for coverPickSets
				selectTest(bestPairOverall.getTest1());
				selectTest(bestPairOverall.getTest2());
			
				bestPairOverall = null;
			}
		} // Closes outermost loop
	
	} // Closes method

	public void prioritizeUsingKW(String metric) 
	{
		// Check to see if the metric value is legit.
		if (!metric.equals("coverage") && !metric.equals("time") && !metric.equals("ratio"))
		{
			System.out.println("\nInvalid metric type.\nProgram terminated");  
			System.exit(0);
		}
	
		//This is the set that will be added to the prioritized cover
		prioritizedSets = new LinkedHashSet();
		LinkedHashSet remainingTests;			
		
		FastByteArrayOutputStream pristeneCover = cover.createFastByteArrayOutputStream();
				
		// keep track of how many times the reduction algorithm is completed.
			int i = 0;		 	
		
		// While there are still tests left
		while (!cover.getTestSubsets().isEmpty()) {
			remainingTests = new LinkedHashSet();			
			
			// Reduce the current SetCover
			reduceUsingKW(metric);
			
			// Update the number of times the reduction algorithm has been run.
			i++;	
			
			// Add the reduction results to the prioritizedSets list
			prioritizedSets.addAll(coverPickSets);	
			coverPickSets.clear();							
			
			try {		
			
				// Retrieve an input stream from the byte array and read
            // a copy of the object back in.
            ObjectInputStream in =
                new ObjectInputStream(pristeneCover.getInputStream());
            cover = (SetCover) in.readObject();
								
			}
			
			catch(IOException e) {
            e.printStackTrace();
        }
			
			catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
         }
         
        	LinkedHashSet badTests = new LinkedHashSet();
			
			//Remove the ones that are already covered.
			Iterator cpsIt = prioritizedSets.iterator();
			
			while (cpsIt.hasNext()){
				String currentName = ((SingleTest) cpsIt.next()).getName();
				
				Iterator goodTestsIterator = cover.getTestSubsets().iterator();
				
				while (goodTestsIterator.hasNext()){
					SingleTestSubset currentGoodTest = (SingleTestSubset) goodTestsIterator.next();
				
					if (currentName.equals(currentGoodTest.getTest().getName())){
						badTests.add(currentGoodTest);
					}
				}
			}
			
			cover.removeSingleTestSubsets(badTests);
			
			if (!cover.getTestSubsets().isEmpty()) {
				
				// Reset the requirementSubsetUniverse
			
				// Extract an iterator over the testSubsets
				Iterator testSubsetsIterator = cover.getTestSubsets().iterator();

				// This loop constructs the list of remaining SingleTests
				while (testSubsetsIterator.hasNext()){
					// add the test to the remainingTests list
					remainingTests.add(((SingleTestSubset) testSubsetsIterator.next()).getTest());
				}
				
				//Extract an iterator over the requirementSubsetUniverse
				Iterator requirementSubsetIterator = cover.getRequirementSubsetUniverse().iterator();
				
				LinkedHashSet requirementsToRemove = new LinkedHashSet();
	
				// This loop removes the covering tests from each requirement that are no longer 
				// in the remainingTests list.  If this removes all of the tests for a RS, then
				// the RS is added to the list to be removed.
				
				while (requirementSubsetIterator.hasNext()) {
					// Get the current RequirementSubset
					RequirementSubset currentRS = (RequirementSubset) requirementSubsetIterator.next();
					LinkedHashSet currentCTS = currentRS.getCoveringTests();
	
					Iterator ctsIterator = currentCTS.iterator();
					LinkedHashSet stToRemove = new LinkedHashSet();
					while (ctsIterator.hasNext()) {
						Iterator remainingTestsIterator = remainingTests.iterator();
						SingleTest currentST = (SingleTest) ctsIterator.next();
						boolean found = false;
												
						while (remainingTestsIterator.hasNext()){
							SingleTest currentRT = (SingleTest) remainingTestsIterator.next();
													
							if (currentRT.getName().equals(currentST.getName())){
								found = true;
							}
						}
						
						if (!found){
							stToRemove.add(currentST);
						}
					}
					
					currentCTS.removeAll(stToRemove);
					
					if (currentCTS.size() == 0) {
						requirementsToRemove.add(currentRS);
					}
				}
								
				cover.removeRequirementSubsets(requirementsToRemove);
			}
		}
	}
} // Closes class	
