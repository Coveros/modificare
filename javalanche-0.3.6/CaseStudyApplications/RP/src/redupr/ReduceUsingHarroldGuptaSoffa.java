/* 
 *	Code for the Harrold, Gupta, Soffa test suite reduction algorithm
 *
 *	@author: Adam M. Smith	July 2, 2007
 */


package redupr;

//import diatoms.monitor.*;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Iterator;
import java.util.*;

public class ReduceUsingHarroldGuptaSoffa  {	
	// Create a SetCover object
	private SetCover cover;
	
	// The reduced suite
	LinkedHashSet coverPickSets;
	
	LinkedHashSet prioritizedSets;

	// Initialize the cardinality  
	private int cardinality;

	// The number of looks ahead for tie resolution.
	private int numberOfLooksAhead;

	/* 
	 *	Default constructor
	 *
	 * @author Adam M. Smith 07/13/2007
	 */

	public ReduceUsingHarroldGuptaSoffa() {
		cover = new SetCover();
		coverPickSets = new LinkedHashSet();
		cardinality = 0;
		numberOfLooksAhead = 1;
	}


   /* 
	 *	This constructor takes a Setcover object as a parameter
	 *
	 * @author Adam M. Smith 07/13/2007
	 */
	public ReduceUsingHarroldGuptaSoffa(SetCover passedCover) {
		cover = passedCover;
		coverPickSets = new LinkedHashSet();
		cardinality = 0;
		numberOfLooksAhead = 1;
	}

   /* 
	 *	This constructor takes a Setcover object and a number of
	 * times to look ahead as parameters.
	 *
	 * @author Adam M. Smith 07/13/2007
	 */
	public ReduceUsingHarroldGuptaSoffa(SetCover passedCover, int looksAhead) {
		cover = passedCover;
		coverPickSets = new LinkedHashSet();
		cardinality = 0;
		numberOfLooksAhead = looksAhead;
	}

   /*
    * Set the number of times to look ahead.
	 *
	 * @author Adam M. Smith 07/13/2007
    *
   */

   public void setLooksAhead(int looks) {
			numberOfLooksAhead = looks;
	}

	/*
    * Set the SetCover object
	 *
	 * @author Adam M. Smith 07/13/2007
    *
   */

	public void setSetCover(SetCover inCover) {
	
		cover = inCover;

	}
   
	/*
    * get the coverPickSets 
	 *
	 * @author Adam M. Smith 07/13/2007
    *
   */
	public LinkedHashSet getCoverPickSets() {
		return coverPickSets;
	}

	/*
    * get the prioritizedSets 
	 *
	 * @author Adam M. Smith 07/13/2007
    *
   */
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
	
	
	/*----------------------------------------------------------------------
	 * This method adds a SingleTest to the reduced suite, removes that test
	 * from the SingleTestSubset list, and removes all RequirementSubsets 
	 * that it covers from the requirementSubsetUniverse.
	 *
	 * Alters RequirementSubsetUniverse, testSubsets, 
	 * requirementsOfCardinality.
	 *
	 * @param test:
	 * @param listOfReqs:
	 *  
	 *--------------------------------------------------------------------*/
   
	private void addTestToReducedSuite(SingleTestSubset test, LinkedHashSet listOfReqs){
		coverPickSets.add(test.getTest());
		
		Iterator requirementSubsetToRemoveIterator = 
			test.getRequirementSubsetSet().iterator();
		
		// For each covered requirement
		while (requirementSubsetToRemoveIterator.hasNext()) {
			RequirementSubset currentRequirementSubsetToRemove =
				(RequirementSubset) requirementSubsetToRemoveIterator.next();

			// Remove the requirement from the universe if it hasn't been done already.
			// It will be gone if another test has
			// covered it.
			if(cover.getRequirementSubsetUniverse().contains(currentRequirementSubsetToRemove)){
				cover.removeRequirementSubset(currentRequirementSubsetToRemove);
			}
			
			// Remove the requirement from the requirementsOfCardinality list 
			// if it hasn't been done already.  It will be gone if another test has
			// covered it.
			if (listOfReqs.contains(currentRequirementSubsetToRemove)){
				listOfReqs.remove(currentRequirementSubsetToRemove);
			}
		}
			
		// Remove the SingleTestSubset
		cover.removeSingleTestSubset(test);
	}

	/*----------------------------------------------------------------------
	* This method returns the integer value of the number of requirements 
 	* in the requirementsOfCardinality list that a particuler 
	* SingleTestSubset covers.
	*---------------------------------------------------------------------*/ 
	private int getNumberOfCurrentCardinalityCovers(SingleTestSubset ts,
																  LinkedHashSet requirementsOfCardinalityList){
		int cardinalityCovers = 0;
		Iterator coveredRequirementsIterator = 
			ts.getRequirementSubsetSet().iterator();

		while (coveredRequirementsIterator.hasNext()){
			if (requirementsOfCardinalityList.contains(coveredRequirementsIterator.next()))
				cardinalityCovers++;
		}
		return cardinalityCovers;
	}


	/*----------------------------------------------------------------------
	* This method takes a SingleTest object and returns the corresponding
	* SingleTestSubject object
	*--------------------------------------------------------------------*/
	private SingleTestSubset getSingleTestSubsetFromSingleTest(SingleTest test) {
		// Extract an iterator for the testSubsets
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

	/*-------------------------------------------------------------------
	 * This method builds a list of requirementSubsets that are of
	 * the current cardinality.  
	 *-----------------------------------------------------------------*/ 

	private LinkedHashSet buildCardinalityList(int card){
		LinkedHashSet cardList = new LinkedHashSet();
		int cardToUse = card;
	
		Iterator requirementSubsetUniverseIterator = 
			(cover.getRequirementSubsetUniverse()).iterator();
		
		// For every requirementSubset
	   while (requirementSubsetUniverseIterator.hasNext()) {
	     
		   // Get the current requirementSubset
			RequirementSubset currentRequirementSubset = 
				(RequirementSubset) requirementSubsetUniverseIterator.next();
	   
			// If its covering test set is of the cardinality that 
			// we are looking for...
			if (currentRequirementSubset.getCoveringTests().size() ==
		   	cardToUse) {
				
				cardList.add(currentRequirementSubset);
			}
		}
		return cardList;
	}


	/*----------------------------------------------------------------------
	 *  This method finds the test with the most coverage, shortest time, or largest coverage per cost 
	 *  ratio of the given list of requirements and returns it.  
	 *  It recursively solves ties.
	 *
	 * @param testList:
	 * @param requirementList:
	 * @param cardToCheck:
	 * @param looksLeft:
	 *--------------------------------------------------------------------*/
	private SingleTestSubset getBestSingleTestSubset(LinkedHashSet testList,
																	LinkedHashSet requirementList,
																	int cardToCheck, int looksLeft, String metric) 
	{

		SingleTest bestSoFar = null;
		int bestSoFarCardinalityCovers;
		int competitorCardinalityCovers;
		SingleTestSubset bestSoFarSingleTestSubset = null;
		LinkedHashSet tieList = new LinkedHashSet();
				
		// Extract an iterator over the covering tests
		Iterator coveringTestsIterator =	testList.iterator();
	
		// Set the first one as the best choice so far.  In this way
		// if there is only one, it will be selected.  
		
		bestSoFar = (SingleTest) coveringTestsIterator.next();
		
		// Get the SingleTestSubset for the bestSoFar SingleTest
		// Note: This is a wasted operation for single cardinality
		// requirementSubsets but it will be less of loss to put it
		// here than to re-update it unneccesarily in every
		// iteration of the next loop.
		bestSoFarSingleTestSubset = 
			getSingleTestSubsetFromSingleTest(bestSoFar);

	   	// It will never be the case that this method will return null because we have
			// rebuilt the the requirementSubsetsSet to contain only requirementSubsets
			// that are covered by a remaining test. 
		
		if (looksLeft < 0) {
			return bestSoFarSingleTestSubset;
		}

							
		// While there are still more covering tests to be examined.
		while (coveringTestsIterator.hasNext())
		{
			// Get the current SingleTestSubset
			SingleTestSubset currentSingleTestSubsetFromRS =
				getSingleTestSubsetFromSingleTest((SingleTest)coveringTestsIterator.next());
				
			if(metric.equals("time"))
			{
					
				// Uses time as the greedy choice factor
				if ( currentSingleTestSubsetFromRS.getTest().getCost() < bestSoFarSingleTestSubset.getTest().getCost()) 
				{
					bestSoFarSingleTestSubset = currentSingleTestSubsetFromRS;
					tieList.clear();				
				} 									
						
				// If it is a tie
				else if (currentSingleTestSubsetFromRS.getTest().getCost() == bestSoFarSingleTestSubset.getTest().getCost())
				{
					if (!tieList.contains(bestSoFar)) 
						tieList.add(bestSoFar);	
			
					tieList.add(currentSingleTestSubsetFromRS.getTest());
				}
			}
			
			else if(metric.equals("ratio"))
			{
			
				double bestSoFarCost = bestSoFarSingleTestSubset.getTest().getCost();
				double competitorCost = currentSingleTestSubsetFromRS.getTest().getCost();
				
				// Get the number of current cardinality requirements that
				// the bestSoFar covers.
				bestSoFarCardinalityCovers = 
					getNumberOfCurrentCardinalityCovers(bestSoFarSingleTestSubset,
																	requirementList);	
			
				// Get the covers
				competitorCardinalityCovers =
					getNumberOfCurrentCardinalityCovers(currentSingleTestSubsetFromRS,
																	requirementList);
			
				
				double bestSoFarRatio = bestSoFarCardinalityCovers/bestSoFarCost;
				double competitorRatio = competitorCardinalityCovers/competitorCost;
				
				// Uses coverage as the greedy choice factor
				if ( competitorRatio > bestSoFarRatio) 
				{
					bestSoFarSingleTestSubset = currentSingleTestSubsetFromRS;
					bestSoFarRatio = competitorRatio;
					tieList.clear();				
				} 									
						
				// If it is a tie
				else if (competitorRatio == bestSoFarRatio)
				{
					if (!tieList.contains(bestSoFar)) 
						tieList.add(bestSoFar);	
			
					tieList.add(currentSingleTestSubsetFromRS.getTest());
				}
			}
			
			else if(metric.equals("coverage"))
			{
			
				// Get the number of current cardinality requirements that
				// the bestSoFar covers.
				// Note:  Same as above.
				bestSoFarCardinalityCovers = 
					getNumberOfCurrentCardinalityCovers(bestSoFarSingleTestSubset,
																	requirementList);	
				// Now we need to check to see if it covers more current 
				// cardinality RequirementSubsets than the bestSoFar 
				// SingleTest object 
				competitorCardinalityCovers =
					getNumberOfCurrentCardinalityCovers(currentSingleTestSubsetFromRS,
																	requirementList);
			
			
				// Uses coverage as the greedy choice factor
				if ( competitorCardinalityCovers > bestSoFarCardinalityCovers) 
				{
					bestSoFarSingleTestSubset = currentSingleTestSubsetFromRS;
					bestSoFarCardinalityCovers = competitorCardinalityCovers;
					tieList.clear();				
				} 									
						
				// If it is a tie
				else if (competitorCardinalityCovers == bestSoFarCardinalityCovers)
				{
					if (!tieList.contains(bestSoFar)) 
						tieList.add(bestSoFar);	
			
					tieList.add(currentSingleTestSubsetFromRS.getTest());
				}
			}
		}	
			if (tieList.size() != 0 && !metric.equals("time")) {
				return getBestSingleTestSubset(tieList,
														(buildCardinalityList(cardToCheck+1)),
														(cardToCheck+1),
														(looksLeft-1),metric); 					
			}

			return bestSoFarSingleTestSubset;
	}

	/*---------------------------------------------------------------------
	 * This method performs the HGS reduction algorithm
	 *-------------------------------------------------------------------*/
	public void reduceUsingHGS(String metric) {
	
	// Check to see if the metric value is legit.
		if (!metric.equals("coverage") && !metric.equals("time") && !metric.equals("ratio"))
		{
			System.out.println("\nInvalid metric type.\nProgram terminated");  
			System.exit(0);
		}
		
		// I have no idea what this was used for.  It apparently is not necessary now.
		// int[] deathArray = new int[1];
		
		// The list of covering tests
		coverPickSets = new LinkedHashSet();
		
		// This is the list that holds all of the current cardinality 
		// RequirementSubsets.
		LinkedHashSet requirementsOfCardinality = new LinkedHashSet();		
		
		// Repeat the loop until all of the requirements are covered.
		// i.e. There are no more RequirementSubsets in the
		// requirementSubsetUniverse.
	 
	   while (!cover.getRequirementSubsetUniverse().isEmpty()) {
			// Update the cardinality
	      cardinality++;
			
			// Populate the list of requirements that are of the current cardinality.
			requirementsOfCardinality = buildCardinalityList(cardinality);
		
			// While the requirements of cardinality are not all covered
			while (!requirementsOfCardinality.isEmpty()){
			
				// Extract an iterator of the the requirements of cardinality.
				Iterator requirementsOfCardinalityIterator = 
					requirementsOfCardinality.iterator();
			
				// Get the next requirement of cardinality.
				RequirementSubset currentRequirementSubset = 
					(RequirementSubset) requirementsOfCardinalityIterator.next();

				// Get the list of tests that cover the current requirement 
				LinkedHashSet coveringTests = currentRequirementSubset.getCoveringTests();

				addTestToReducedSuite(getBestSingleTestSubset(coveringTests,
																				 requirementsOfCardinality,
																				 cardinality, 
																				 numberOfLooksAhead,metric),
																				 requirementsOfCardinality);
			}
		} 
	}  // Closes HGS algorithm


	public void prioritizeUsingHGS(String metric) {
		//This is the set that will be added to the prioritized cover
		prioritizedSets = new LinkedHashSet();
		LinkedHashSet remainingTests;			
		    
		LinkedHashSet requirementsBeforeReduction = new LinkedHashSet(); 

		requirementsBeforeReduction.addAll((LinkedHashSet) cover.getRequirementSubsetUniverse());

		// keep track of how many times the reduction algorithm is completed.
			int i = 0;		 	
		
		// While there are still tests left
		while (!cover.getTestSubsets().isEmpty()) {
			remainingTests = new LinkedHashSet();			
			
			// Reset the cardinality each time.
			cardinality = 0;
		
			// Reduce the current SetCover
			this.reduceUsingHGS(metric);

			// Update the number of times the reduction algorithm has been run.
			i++;	
			
			// Reset the requirementSubsetUniverse
			this.cover.addRequirementSubsets(requirementsBeforeReduction);
			
			// Add the reduction results to the prioritizedSets list
			this.prioritizedSets.addAll(coverPickSets);		

			// Extract an iterator over the testSubsets
			Iterator testSubsetsIterator = this.cover.getTestSubsets().iterator();

			// This loop constructs the list of remainingTests
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

				currentCTS.retainAll(remainingTests);

				if (currentCTS.size() == 0) {
					requirementsToRemove.add(currentRS);
				}
			}
			
			cover.removeRequirementSubsets(requirementsToRemove);
		}
	}
} // Closes class	
