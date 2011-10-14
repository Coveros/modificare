 //	Code for the Delayed Greedy test suite reduction algorithm
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


public class ReduceUsingDelayedGreedy  {	
	
	// Create a SetCover object
	private SetCover cover;
	
	// The reduced suite
	LinkedHashSet coverPickSets;
	
	LinkedHashSet prioritizedSets;


	// 
	// 	Default constructor
	// 
	//    @author Adam M. Smith 07/13/2007
	

	public ReduceUsingDelayedGreedy() {
		cover = new SetCover();
		coverPickSets = new LinkedHashSet();
	}


   // 
	//	This constructor takes a Setcover object as a parameter
	//
	// @author Adam M. Smith 07/13/2007
	//
	public ReduceUsingDelayedGreedy(SetCover passedCover) {
		cover = passedCover;
		coverPickSets = new LinkedHashSet();
	}

   
   
   // Set the SetCover object
	//
	// @author Adam M. Smith 07/13/2007
  	public void setSetCover(SetCover inCover) 
  	{
		cover = inCover;
	}
	
	// Get the SetCover object
	//
	// @author Adam M. Smith 02/07/2008
   public SetCover getSetCover()
   {
   	return cover;
   }
	
   // get the coverPickSets 
	//
	// @author Adam M. Smith 07/13/2007
   public LinkedHashSet getCoverPickSets() 
   {
		return coverPickSets;
	}

	
   // get the prioritizedSets 
	//
	// @author Adam M. Smith 07/13/2007
    
   public LinkedHashSet getPrioritizedSets() 
   {
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
	//  This method removes every instance of the passed test from the 
	//  RequirementSubset Universe
	//-------------------------------------------------------------------
	public void removeTestsFromRSU(LinkedHashSet testsToRemove){
		Iterator universeIterator = cover.getRequirementSubsetUniverse().iterator();
		
		while (universeIterator.hasNext()) {
			RequirementSubset currentReq = (RequirementSubset) universeIterator.next();
			Iterator testIt = testsToRemove.iterator();
			
			while (testIt.hasNext()) {
				SingleTest test = ((SingleTestSubset) testIt.next()).getTest();
				
				if (currentReq.getCoveringTests().contains(test)){
					currentReq.removeCoveringTest(test);
				}
			}
		}
	}
	
	
	//--------------------------------------------------------------------
	//  This method removes every instance of the passed RequirementSubset
	//  from the singleTestSubsetSet
	//-------------------------------------------------------------------
	public void removeRequirementsFromSTSS(LinkedHashSet reqsToRemove){
		Iterator setIterator = cover.getTestSubsets().iterator();
		
		while (setIterator.hasNext()) {
			SingleTestSubset currentSTS = (SingleTestSubset) setIterator.next();
			Iterator reqIt = reqsToRemove.iterator();
		
			while (reqIt.hasNext()) {
				RequirementSubset req = ((RequirementSubset) reqIt.next());
			
				if (currentSTS.getRequirementSubsetSet().contains(req))
					currentSTS.removeRequirementSubset(req);
		
			}
		}
	}
		
	//----------------------------------------------------------------------
	// This method takes a SingleTest object and returns the corresponding
	// SingleTestSubject object
	//----------------------------------------------------------------------
	public SingleTestSubset getSingleTestSubsetFromSingleTest(SingleTest test) {
		// Need to find the SingleTestSubset that corresponds to the 
		// SingleTest object. Extract an iterator for the testSubsets
		Iterator testSubsetsIterator = cover.getTestSubsets().iterator();
	
		// While there are more TestSubsets...
		while (testSubsetsIterator.hasNext()) {
			// Get the current SingleTestSubset
			SingleTestSubset currentSingleTestSubset = 
				(SingleTestSubset) testSubsetsIterator.next();
							
			// Get the SingleTest object that corresponds to the 
			// currentSingleTestSubset object
			SingleTest currentSingleTestFromTS = currentSingleTestSubset.getTest();
							

			// If the current SingleTest from the testSubset iterator
			// has the same name as the current SingleTest from the 
			// requirementSubset iterator, then we have a match. 	
			if ((currentSingleTestFromTS.getName()).equals(test.getName()))
				return currentSingleTestSubset;	
			}
		
		return null;	
	}

	
	/**
	 * The containsAll() method does not work for large sets so this method
	 * implements a new way.
	 *
	 * @author Adam M. Smith
	 */
	 
	public static boolean isSubset(LinkedHashSet possibleSuperset, LinkedHashSet possibleSubset)
	{
		if(possibleSuperset.size() < possibleSubset.size())
		{
			return false;
		}
						
		//get an iterator over the requirements covered by testToCompare
		Iterator possibleSubsetIterator = possibleSubset.iterator();
						
		while(possibleSubsetIterator.hasNext())
		{
			if(!possibleSuperset.contains((Object)possibleSubsetIterator.next()))
			{
				return false;
			}	
		}
		
		return true;
	}
	
	//---------------------------------------------------------------------
	// This method performs the DG reduction algorithm
	//---------------------------------------------------------------------
	public void reduceUsingDG(String metric) 
	{
		coverPickSets = new LinkedHashSet();
		boolean makeGreedy = false;

		LinkedHashSet testsToCheck = new LinkedHashSet();
		LinkedHashSet reqsToCheck = new LinkedHashSet();

		testsToCheck.addAll(cover.getTestSubsets());
		reqsToCheck.addAll(cover.getRequirementSubsetUniverse());				
	
		// Repeat the loop until all of the requirements are covered.
		// i.e. There are no more RequirementSubsets in the
		// requirementSubsetUniverse.
	 
		while (!cover.getRequirementSubsetUniverse().isEmpty()) {
			makeGreedy = false;

			// repeat this loop as long as a greedy choice is not made and the 
			// requirements are not all covered.
			while (!cover.getRequirementSubsetUniverse().isEmpty() && !makeGreedy) {
				makeGreedy = true;

			//Make the object implications on the testsToCheck list
				
				LinkedHashSet toRemove = new LinkedHashSet();
				
				// While there are tests left to check
				while (!testsToCheck.isEmpty()) 
				{			

					// Extract an iterator over the tests
					Iterator testsToCheckIterator = testsToCheck.iterator();
								
					// Get the current test
					SingleTestSubset currentTest = (SingleTestSubset) testsToCheckIterator.next();
				
					// In this loop, compare the currentTest to the remaining
					while (testsToCheckIterator.hasNext()) 
					{
						SingleTestSubset testToCompare = (SingleTestSubset) testsToCheckIterator.next();

						// If the currentTest is a subset of the testToCompare
						
						if ( isSubset(testToCompare.getRequirementSubsetSet(),currentTest.getRequirementSubsetSet())) 
						{	
														
							// Extract an iterator over the requirements that are covered by the currentTest
							Iterator currentCoveredReqsIterator = (currentTest.getRequirementSubsetSet()).iterator();
								
							// For each covered requirement
							while (currentCoveredReqsIterator.hasNext())
							{
								RequirementSubset	currentCoveredReq = (RequirementSubset) currentCoveredReqsIterator.next();

								// if it is not already in the list of requirements to check: add it.
								if (!reqsToCheck.contains(currentCoveredReq)) {
									reqsToCheck.add(currentCoveredReq);
								}
							}							


							// remove the test from the testsToCheck
							toRemove.add(currentTest);		
							makeGreedy = false;
							break;
							
						}
				
						// If the testToCompare is a subset of the CurrentTest
						if (isSubset(currentTest.getRequirementSubsetSet(),testToCompare.getRequirementSubsetSet()) )
						{								
							// Extract an iterator over the requirements that are covered by the currentTest
							Iterator compareCoveredReqsIterator = (testToCompare.getRequirementSubsetSet()).iterator();
								
							// For each covered requirement
							while (compareCoveredReqsIterator.hasNext())
							{
								RequirementSubset compareCoveredReq = (RequirementSubset) compareCoveredReqsIterator.next();

								// if it is not already in the list of requirements to check: add it.
								if (!reqsToCheck.contains(compareCoveredReq)) {
									reqsToCheck.add(compareCoveredReq);
								}
							}			
						
							toRemove.add(testToCompare);
							makeGreedy = false;
						}
					}
						
				// Only remove the tests that were subsets from the cover
				if (!toRemove.isEmpty())	{
					cover.removeSingleTestSubsets(toRemove);
					removeTestsFromRSU(toRemove);
				}
				// then remove those tests from the check list + the currenttest.
	
				if (!toRemove.contains(currentTest)){
					toRemove.add(currentTest);
				}
				
				testsToCheck.removeAll(toRemove);								
				toRemove.clear();					
			
				} // closes object implication
					
				//Make the attribute implications on the testsToCheck list
			
				// While there are reqs left to check
				while (!reqsToCheck.isEmpty()){			

					// Extract an iterator over the reqs
					Iterator reqsToCheckIterator = reqsToCheck.iterator();
					
					// Get the current req
					RequirementSubset currentReq = (RequirementSubset) reqsToCheckIterator.next();
				
					// In this loop, compare the currentreq to the remaining
					while (reqsToCheckIterator.hasNext()){
						RequirementSubset reqToCompare = (RequirementSubset) reqsToCheckIterator.next();

						// If the currentreq is a superset of the reqToCompare
						if (currentReq.getCoveringTests().containsAll(reqToCompare.getCoveringTests() ) && !(reqToCompare.getCoveringTests().containsAll(currentReq.getCoveringTests()) ) ) {	
							
							// Extract an iterator over the tests that cover the current requirement
							Iterator currentCoveringTestsIterator = (currentReq.getCoveringTests()).iterator();
								
							// For each covered requirement
							while (currentCoveringTestsIterator.hasNext()){
							SingleTest	currentCoveringTest = (SingleTest) currentCoveringTestsIterator.next();

							SingleTestSubset currentSTS = getSingleTestSubsetFromSingleTest(currentCoveringTest);

								// if it is not already in the list of tests to check: add it.
								if (!testsToCheck.contains(currentSTS) && !(currentSTS==null)) {
									testsToCheck.add(currentSTS);
								}
							}							

							// remove the req from the reqsToCheck
							toRemove.add(currentReq);
							makeGreedy = false;
							break;
							
						}
				
						// If the reqToCompare is a superset of the Currentreq
						if (reqToCompare.getCoveringTests().containsAll(currentReq.getCoveringTests() ) && !(currentReq.getCoveringTests().containsAll(reqToCompare.getCoveringTests()) ) ) 
						{	
							
						// Extract an iterator over the tests that cover the currentreq
						Iterator compareCoveringTestsIterator = (reqToCompare.getCoveringTests()).iterator();
								
						// For each covered requirement
						while (compareCoveringTestsIterator.hasNext()){
							SingleTest currentCoveringTest = (SingleTest) compareCoveringTestsIterator.next();
				
							SingleTestSubset currentSTS = getSingleTestSubsetFromSingleTest(currentCoveringTest);

							// if it is not already in the list of tests to check: add it.
							if (!testsToCheck.contains(currentSTS) && !(currentSTS==null)) {
								testsToCheck.add(currentSTS);
							}
						}			
						
						toRemove.add(reqToCompare);
						makeGreedy = false;
					}
				}
					
				//Only remove the requirements from the cover that were supersets
				if (!toRemove.isEmpty()){
					cover.removeRequirementSubsets(toRemove);
					removeRequirementsFromSTSS(toRemove);
				}
				if (!toRemove.contains(currentReq)) {
					toRemove.add(currentReq);
				}
					/////REQS ARE ALL BEING REMOVED
				reqsToCheck.removeAll(toRemove);
				
				toRemove.clear();
						
			} // closes attribute implication
				
				// Make owner reduction

				//Set to hold STSs to remove during the owner reduction
				LinkedHashSet ownerReductions = new LinkedHashSet();
				
				// Extract an iterator over the requirementSubsetUniverse
				Iterator reqIterator = cover.getRequirementSubsetUniverse().iterator();

				// While there are more requirements to look at
				while (reqIterator.hasNext()) {
					
					//Get the current RequirementSubset 
					RequirementSubset currentRequirementSubset = (RequirementSubset) reqIterator.next();
				
					// If the currentRequirementSubset is only covered by one test
					if (currentRequirementSubset.getCoveringTests().size() == 1) {
						makeGreedy = false;
											
						// Extract an iterator for the single covering test
						Iterator coveringTests = currentRequirementSubset.getCoveringTests().iterator();

						// Get the RequirementSubset's coveringTest
						SingleTest coveringTest = (SingleTest) coveringTests.next();

						// Get the SingleTestSubset corresponding to the SingleTest
						SingleTestSubset coveringTestSubset = getSingleTestSubsetFromSingleTest(coveringTest);

						if (testsToCheck.contains(coveringTestSubset)){
							testsToCheck.remove(coveringTestSubset);
						}
						
						ownerReductions.add(coveringTestSubset);
						if (!coverPickSets.contains(coveringTestSubset.getTest())) {
							coverPickSets.add(coveringTestSubset.getTest());
						}
						
					}


				}


				//Extract an iterator over the ownerReduction list
				
				Iterator ownerReductionIterator = ownerReductions.iterator();
				LinkedHashSet reqsToRemove = new LinkedHashSet();

				while (ownerReductionIterator.hasNext()) {

					SingleTestSubset ownedTest = (SingleTestSubset) ownerReductionIterator.next();
				
					// Extract an iterator over all of the RequirementSubsets covered by that SingleTestSubset
					Iterator coveredReqs = ownedTest.getRequirementSubsetSet().iterator();
	
					// While there are more RequirementSubsets covered by the current coveringTestSubset
					while (coveredReqs.hasNext()) {
	
						// Get the current RequirementSubset
						RequirementSubset currentRS = (RequirementSubset) coveredReqs.next();
						
						// Add the RequirementSubset to the reqsToRemove list
						reqsToRemove.add(currentRS);
						
						 // Extract an iterator over the tests that will be effected by the removal of
                   // this requirement
                   Iterator currentRSIterator = currentRS.getCoveringTests().iterator();


                   // While there are more effected tests
                   while (currentRSIterator.hasNext()) {

						   // Get the current singleTest
         	         SingleTest STToCheck = (SingleTest) currentRSIterator.next();
							
							if (!STToCheck.equals(ownedTest.getTest())){
							
              	      	// Get the corresponding SingleTestSubset
                   		SingleTestSubset STSToCheck = getSingleTestSubsetFromSingleTest(STToCheck);

	                   	// If the SingleTestSubset is not already included in the testsToCheck list
   	                	if (!testsToCheck.contains(STSToCheck)  && !(STSToCheck==null)) {
      	               	// Add it
         	               testsToCheck.add(STSToCheck);
            	         }
							}
					   }						
					}
				}
				
				
													
				//remove
				cover.removeRequirementSubsets(reqsToRemove);
				removeRequirementsFromSTSS(reqsToRemove);
				
				cover.removeSingleTestSubsets(ownerReductions);	
				
				} // closes the && while
								
			// Make a greedy choice
			if (!cover.getRequirementSubsetUniverse().isEmpty())
			{
				Iterator RSI = cover.getTestSubsets().iterator();
				SingleTestSubset bestSoFar = (SingleTestSubset) RSI.next();
	
				while (RSI.hasNext()) {
				
					SingleTestSubset competitor = (SingleTestSubset) RSI.next();
					
					if(metric.equals("coverage"))
					{
						if (competitor.getRequirementSubsetSet().size() > bestSoFar.getRequirementSubsetSet().size()) 
						{
							bestSoFar = competitor;
						}				
					}
					
					if(metric.equals("time"))
					{
						//System.out.println("\nCompetitor: \n\t" + competitor +"\nBestSoFar: \n\t"+bestSoFar);
					
						if (competitor.getTest().getCost() < bestSoFar.getTest().getCost())
						{
							//System.out.println("*Found Replacement: "+competitor); 
							bestSoFar = competitor;
						}
					}
					
					if(metric.equals("ratio"))
					{
						if (((competitor.getRequirementSubsetSet().size())/competitor.getTest().getCost()) > ((bestSoFar.getRequirementSubsetSet().size())/bestSoFar.getTest().getCost()) )
						{
							bestSoFar = competitor;
						}
					}
				}
				
				// LinkedHashSet to hold the requirements to remove
				LinkedHashSet requirementsToRemove = new LinkedHashSet();
	
				// Extract an iterator over all of the RequirementSubsets covered by that SingleTestSubset
				Iterator coveredReqs = bestSoFar.getRequirementSubsetSet().iterator();
	
				// While there are more RequirementSubsets covered by the current coveringTestSubset
				while (coveredReqs.hasNext()) {
	
					// Get the current RequirementSubset
					RequirementSubset currentRS = (RequirementSubset) coveredReqs.next();
							
					// Add the RequirementSubset to the requirements to remove list
					requirementsToRemove.add(currentRS);
							
					// Extract an iterator over the tests that will be effected by the removal of
					// this requirement
					Iterator currentRSIterator	= currentRS.getCoveringTests().iterator();
	
	
					// While there are more effected tests
					while (currentRSIterator.hasNext()) {
										
						// Get the current SingleTestSubset		
						SingleTestSubset STSToCheck = getSingleTestSubsetFromSingleTest((SingleTest) currentRSIterator.next());
						
						// If the SingleTestSubset is not already included in the testsToCheck list
						
						if	(!(STSToCheck==null) && !testsToCheck.contains(STSToCheck)) {
							// Add it
							testsToCheck.add(STSToCheck);
						}															
					}						
				}

				//remove
				
				if (!testsToCheck.contains(bestSoFar)){
					testsToCheck.remove(bestSoFar);
				}
				
				LinkedHashSet outTest = new LinkedHashSet();
				
				outTest.add(bestSoFar);
								
				removeTestsFromRSU(outTest);
				
				cover.removeRequirementSubsets(requirementsToRemove);
				cover.removeSingleTestSubset(bestSoFar);
				
				coverPickSets.add(bestSoFar.getTest());
							
			} //closes greedy if
		} // closes the outer while  
	} // closes the method

	public void prioritizeUsingDG(String metric) {
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
			reduceUsingDG(metric);
			
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
