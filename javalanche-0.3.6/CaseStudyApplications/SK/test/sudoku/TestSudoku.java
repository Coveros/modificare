package test.sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import junit.framework.*;

import src.sudoku.Box;
import src.sudoku.DuplicateBoxesWithSameSolutionException;
import src.sudoku.Puzzle;

public class TestSudoku extends TestCase {

    public static TestSuite suite() 
    {
    	/*
    	TestSuite suite = new TestSuite();
        suite.addTestSuite(TestSudoku.class);
        return suite;
        */
    	
        ArrayList<TestSuite> suiteList = new ArrayList<TestSuite>();
        
    	TestSuite finalSuite = new TestSuite();
    	
    	suiteList.add(new TestSuite(TestSudoku.class));
    	
    	for(TestSuite suite : suiteList)
    	{
    		for(int i = 0; i < suite.countTestCases();i++)
    			finalSuite.addTest(suite.testAt(i));
    	}
    	
        return finalSuite;
    }	

//     public static void main(String[] args) {
//         new TestSudoku().testSolveWithDepthFirstSearch();
//     }

      public static void main(String[] args) 
      {
    	  TestSuite suite = suite();
      	
      	if(args.length == 0)
      	{
      		for(int i = 0; i < suite.countTestCases();i++)
      		{
      			System.out.println(suite.testAt(i));
      			junit.textui.TestRunner.run(suite.testAt(i));
      	
      		}
      	}
      	else
      	{
      		for(int i = 0; i < args.length;i++)
      		{
      			System.out.println(suite.testAt(Integer.parseInt(args[i])));
      			junit.textui.TestRunner.run(suite.testAt(Integer.parseInt(args[i])));
      		}	
      	}
      	
      	System.out.println("Total Test Cases: "+ suite.countTestCases());    
     }

    public void testInitialize_Null() {
        String initialState = null;

        try {
            Puzzle solver = new Puzzle(initialState);
            fail("should fail");
        } catch (RuntimeException e) {
            assertEquals("empty input", e.getMessage());
        }
    }

    public void testInitialize_Empty() {
        String initialState = "";

        try {
            Puzzle puzzle = new Puzzle(initialState);
            fail("should fail");
        } catch (RuntimeException e) {
            assertEquals("empty input", e.getMessage());
        }
    }

    public void testInitialize_NotSquare() {
        String initialState = "......";

        try {
            Puzzle puzzle = new Puzzle(initialState);
            fail("should fail");
        } catch (RuntimeException e) {
            assertEquals("invalid input length: 6", e.getMessage());
        }
    }

    public void testInitialize_NoSeededValues() {
        String initialState = "....";

        Puzzle puzzle = new Puzzle(initialState);
        assertEquals(2, puzzle.dimension());
        assertEquals(0, puzzle.getSolvedBoxes().size());
    }

    public void testInitialize_NoSeededValues_Zeros() {
        String initialState = "0000";

        Puzzle puzzle = new Puzzle(initialState);
        assertEquals(2, puzzle.dimension());
        assertEquals(0, puzzle.getSolvedBoxes().size());
    }

    public void testInitialize_NoSeededValues_Dashes() {
        String initialState = "----";

        Puzzle puzzle = new Puzzle(initialState);
        assertEquals(2, puzzle.dimension());
        assertEquals(0, puzzle.getSolvedBoxes().size());
    }

    public void testSolvedValues() {
        String initialState = "1234";

        Puzzle puzzle = new Puzzle(initialState);
        assertEquals(2, puzzle.dimension());
        assertEquals(4, puzzle.getSolvedBoxes().size());
        assertEquals(1, puzzle.getSolvedValue(0, 0));
        assertEquals(2, puzzle.getSolvedValue(0, 1));
        assertEquals(3, puzzle.getSolvedValue(1, 0));
        assertEquals(4, puzzle.getSolvedValue(1, 1));
    }

    public void testSolvedValues_SomeValuesMissing() {
        String initialState = ".2.4";

        Puzzle puzzle = new Puzzle(initialState);
        assertEquals(2, puzzle.dimension());
        assertEquals(2, puzzle.getSolvedBoxes().size());
        assertEquals(2, puzzle.getSolvedValue(0, 1));
        assertEquals(4, puzzle.getSolvedValue(1, 1));
    }

    public void testSolvedValue_Error() {
        String initialState = ".2.4";

        Puzzle puzzle = new Puzzle(initialState);

        try {
            puzzle.getSolvedValue(0, 0);
            fail("should fail");
        } catch (RuntimeException e) {
            assertEquals("box: 0, 0 has not been solved", e.getMessage());
        }
    }

    public void testPossibleValues_Tiny() {
        Puzzle puzzle = new Puzzle("....");

        assertPossibleValues(new int[] { 1, 2 }, puzzle.getPossibleValues(0, 0));
        assertPossibleValues(new int[] { 1, 2 }, puzzle.getPossibleValues(0, 1));
        assertPossibleValues(new int[] { 1, 2 }, puzzle.getPossibleValues(1, 0));
        assertPossibleValues(new int[] { 1, 2 }, puzzle.getPossibleValues(1, 1));
    }

    public void testPossibleValues_InitiateBoxState() {
        Puzzle puzzle = new Puzzle(get81BlankBoxes());

        assertPossibleValues(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 }, puzzle.getPossibleValues(5, 6));
    }

    public void testGetPeers_Unit1() {
        Puzzle puzzle = new Puzzle(get81BlankBoxes());

        List<Box> peers = puzzle.getPeers(0, 0);

        assertEquals(20, peers.size());
        assertPeer(peers, 0, 1);
        assertPeer(peers, 0, 2);
        assertPeer(peers, 0, 3);
        assertPeer(peers, 0, 4);
        assertPeer(peers, 0, 5);
        assertPeer(peers, 0, 6);
        assertPeer(peers, 0, 7);
        assertPeer(peers, 0, 8);

        assertPeer(peers, 1, 0);
        assertPeer(peers, 2, 0);
        assertPeer(peers, 3, 0);
        assertPeer(peers, 4, 0);
        assertPeer(peers, 5, 0);
        assertPeer(peers, 6, 0);
        assertPeer(peers, 7, 0);
        assertPeer(peers, 8, 0);

        assertPeer(peers, 1, 1);
        assertPeer(peers, 1, 2);
        assertPeer(peers, 2, 1);
        assertPeer(peers, 2, 2);
    }

    public void testGetPeers_Unit6() {
        Puzzle puzzle = new Puzzle(get81BlankBoxes());

        List<Box> peers = puzzle.getPeers(7, 6);

        assertEquals(20, peers.size());
        assertPeer(peers, 7, 0);
        assertPeer(peers, 7, 1);
        assertPeer(peers, 7, 2);
        assertPeer(peers, 7, 3);
        assertPeer(peers, 7, 4);
        assertPeer(peers, 7, 5);
        assertPeer(peers, 7, 7);
        assertPeer(peers, 7, 8);

        assertPeer(peers, 0, 6);
        assertPeer(peers, 1, 6);
        assertPeer(peers, 2, 6);
        assertPeer(peers, 3, 6);
        assertPeer(peers, 4, 6);
        assertPeer(peers, 5, 6);
        assertPeer(peers, 6, 6);
        assertPeer(peers, 8, 6);

        assertPeer(peers, 6, 7);
        assertPeer(peers, 6, 8);
        assertPeer(peers, 8, 7);
        assertPeer(peers, 8, 8);
    }

    public void testGetPeersInSameRow() {
        Puzzle puzzle = new Puzzle(get81BlankBoxes());

        List<Box> peers = puzzle.getPeersInSameRow(3, 3);

        assertEquals(8, peers.size());
        assertPeer(peers, 3, 0);
        assertPeer(peers, 3, 1);
        assertPeer(peers, 3, 2);
        assertPeer(peers, 3, 4);
        assertPeer(peers, 3, 5);
        assertPeer(peers, 3, 6);
        assertPeer(peers, 3, 7);
        assertPeer(peers, 3, 8);
    }

    public void testGetPeersInSameColumn() {
        Puzzle puzzle = new Puzzle(get81BlankBoxes());

        List<Box> peers = puzzle.getPeersInSameColumn(3, 4);

        assertEquals(8, peers.size());
        assertPeer(peers, 0, 4);
        assertPeer(peers, 1, 4);
        assertPeer(peers, 2, 4);
        assertPeer(peers, 4, 4);
        assertPeer(peers, 5, 4);
        assertPeer(peers, 6, 4);
        assertPeer(peers, 7, 4);
        assertPeer(peers, 8, 4);
    }

    public void testGetPeersInSameSubSquare() {
        Puzzle puzzle = new Puzzle(get81BlankBoxes());

        List<Box> peers = puzzle.getPeersInSameSubSquare(3, 4);

        assertEquals(8, peers.size());
        assertPeer(peers, 3, 3);
        assertPeer(peers, 3, 5);
        assertPeer(peers, 4, 3);
        assertPeer(peers, 4, 4);
        assertPeer(peers, 4, 5);
        assertPeer(peers, 5, 3);
        assertPeer(peers, 5, 4);
        assertPeer(peers, 5, 5);
    }

    private void assertPeer(List<Box> peers, int rowIndex, int columnIndex) {
        for (Box box : peers)
            if (box.row() == rowIndex && box.column() == columnIndex)
                return;

        fail("unable to find peer at " + rowIndex + ", " + columnIndex);
    }

    public void testFindBoxesConstrainedToOnePossibleValue_Row() {
        Puzzle puzzle = new Puzzle(get81BlankBoxes());

        puzzle.getBox(2, 0).copyPossibleValues(Arrays.asList(new Integer[] { 1 }));
        puzzle.getBox(2, 1).copyPossibleValues(Arrays.asList(new Integer[] { 2 }));
        puzzle.getBox(2, 3).copyPossibleValues(Arrays.asList(new Integer[] { 3 }));
        puzzle.getBox(2, 4).copyPossibleValues(Arrays.asList(new Integer[] { 4 }));
        puzzle.getBox(2, 5).copyPossibleValues(Arrays.asList(new Integer[] { 5 }));
        puzzle.getBox(2, 6).copyPossibleValues(Arrays.asList(new Integer[] { 6 }));
        puzzle.getBox(2, 7).copyPossibleValues(Arrays.asList(new Integer[] { 7 }));
        puzzle.getBox(2, 8).copyPossibleValues(Arrays.asList(new Integer[] { 8 }));

        List<Box> uniquelySpecifiedBoxes = puzzle.getUniquelySpecifiedBoxes();

        assertEquals(1, uniquelySpecifiedBoxes.size());
        assertSolved(uniquelySpecifiedBoxes.get(0), 2, 2, 9);
    }

    public void testFindBoxesConstrainedToOnePossibleValue_Column() {
        Puzzle puzzle = new Puzzle(get81BlankBoxes());

        puzzle.getBox(0, 2).copyPossibleValues(Arrays.asList(new Integer[] { 1 }));
        puzzle.getBox(1, 2).copyPossibleValues(Arrays.asList(new Integer[] { 2 }));
        puzzle.getBox(3, 2).copyPossibleValues(Arrays.asList(new Integer[] { 3 }));
        puzzle.getBox(4, 2).copyPossibleValues(Arrays.asList(new Integer[] { 4 }));
        puzzle.getBox(5, 2).copyPossibleValues(Arrays.asList(new Integer[] { 5 }));
        puzzle.getBox(6, 2).copyPossibleValues(Arrays.asList(new Integer[] { 6 }));
        puzzle.getBox(7, 2).copyPossibleValues(Arrays.asList(new Integer[] { 7 }));
        puzzle.getBox(8, 2).copyPossibleValues(Arrays.asList(new Integer[] { 8 }));

        List<Box> uniquelySpecifiedBoxes = puzzle.getUniquelySpecifiedBoxes();

        assertEquals(1, uniquelySpecifiedBoxes.size());
        assertSolved(uniquelySpecifiedBoxes.get(0), 2, 2, 9);
    }

    public void testFindBoxesConstrainedToOnePossibleValue_SubSquare() {
        Puzzle puzzle = new Puzzle(get81BlankBoxes());

        puzzle.getBox(0, 0).copyPossibleValues(Arrays.asList(new Integer[] { 1 }));
        puzzle.getBox(0, 1).copyPossibleValues(Arrays.asList(new Integer[] { 2 }));
        puzzle.getBox(0, 2).copyPossibleValues(Arrays.asList(new Integer[] { 3 }));

        puzzle.getBox(1, 0).copyPossibleValues(Arrays.asList(new Integer[] { 4 }));
        puzzle.getBox(1, 1).copyPossibleValues(Arrays.asList(new Integer[] { 5 }));
        puzzle.getBox(1, 2).copyPossibleValues(Arrays.asList(new Integer[] { 6 }));

        puzzle.getBox(2, 0).copyPossibleValues(Arrays.asList(new Integer[] { 7 }));
        puzzle.getBox(2, 1).copyPossibleValues(Arrays.asList(new Integer[] { 8 }));

        List<Box> uniquelySpecifiedBoxes = puzzle.getUniquelySpecifiedBoxes();

        assertEquals(1, uniquelySpecifiedBoxes.size());
        assertSolved(uniquelySpecifiedBoxes.get(0), 2, 2, 9);
    }

    public void testFindBoxesConstrainedToOnePossibleValue_NoUnitsHave9AsPossibleValue() {
        Puzzle puzzle = new Puzzle(get81BlankBoxes());

        setPossibleValues(puzzle, new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8 });
        puzzle.getBox(2, 2).copyPossibleValues(Arrays.asList(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 }));

        List<Box> uniquelySpecifiedBoxes = puzzle.getUniquelySpecifiedBoxes();

        assertEquals(1, uniquelySpecifiedBoxes.size());
        assertSolved(uniquelySpecifiedBoxes.get(0), 2, 2, 9);
    }

    private void setPossibleValues(Puzzle puzzle, Integer[] possibleValues) {
        for (int rowIndex = 0; rowIndex < puzzle.dimension(); rowIndex++)
            for (int columnIndex = 0; columnIndex < puzzle.dimension(); columnIndex++)
                puzzle.getBox(rowIndex, columnIndex).copyPossibleValues(Arrays.asList(possibleValues));
    }

    private void assertSolved(Box box, int rowIndex, int columnIndex, int value) {
        assertEquals(rowIndex, box.row());
        assertEquals(columnIndex, box.column());
        assertTrue("should be solved", box.isSolved());
        assertEquals(value, box.getPossibleValues().get(0).intValue());
    }

    private String get81BlankBoxes() {
        return ".................................................................................";
    }

    public void testRemoveSolvedValuesFromPeers() throws DuplicateBoxesWithSameSolutionException {
        String row1 = ".1.......";
        String row2 = ".........";
        String row3 = ".........";
        String row4 = ".........";
        String row5 = ".........";
        String row6 = ".........";
        String row7 = ".........";
        String row8 = ".........";
        String row9 = ".........";
        Puzzle puzzle = new Puzzle(row1 + row2 + row3 + row4 + row5 + row6 + row7 + row8 + row9);

        puzzle.removeSolvedValuesFromPeers();

        assertPossibleValues(new int[] { 1 }, puzzle.getPossibleValues(0, 1));

        assertPossibleValues(new int[] { 2, 3, 4, 5, 6, 7, 8, 9 }, puzzle.getPossibleValues(0, 0));
        assertPossibleValues(new int[] { 2, 3, 4, 5, 6, 7, 8, 9 }, puzzle.getPossibleValues(0, 2));
        assertPossibleValues(new int[] { 2, 3, 4, 5, 6, 7, 8, 9 }, puzzle.getPossibleValues(1, 0));
        assertPossibleValues(new int[] { 2, 3, 4, 5, 6, 7, 8, 9 }, puzzle.getPossibleValues(1, 2));
        assertPossibleValues(new int[] { 2, 3, 4, 5, 6, 7, 8, 9 }, puzzle.getPossibleValues(2, 0));
        assertPossibleValues(new int[] { 2, 3, 4, 5, 6, 7, 8, 9 }, puzzle.getPossibleValues(2, 2));

        assertPossibleValues(new int[] { 2, 3, 4, 5, 6, 7, 8, 9 }, puzzle.getPossibleValues(0, 3));
        assertPossibleValues(new int[] { 2, 3, 4, 5, 6, 7, 8, 9 }, puzzle.getPossibleValues(0, 4));
        assertPossibleValues(new int[] { 2, 3, 4, 5, 6, 7, 8, 9 }, puzzle.getPossibleValues(0, 5));
        assertPossibleValues(new int[] { 2, 3, 4, 5, 6, 7, 8, 9 }, puzzle.getPossibleValues(0, 6));
        assertPossibleValues(new int[] { 2, 3, 4, 5, 6, 7, 8, 9 }, puzzle.getPossibleValues(0, 7));
        assertPossibleValues(new int[] { 2, 3, 4, 5, 6, 7, 8, 9 }, puzzle.getPossibleValues(0, 8));

        assertPossibleValues(new int[] { 2, 3, 4, 5, 6, 7, 8, 9 }, puzzle.getPossibleValues(1, 1));
        assertPossibleValues(new int[] { 2, 3, 4, 5, 6, 7, 8, 9 }, puzzle.getPossibleValues(2, 1));
        assertPossibleValues(new int[] { 2, 3, 4, 5, 6, 7, 8, 9 }, puzzle.getPossibleValues(3, 1));
        assertPossibleValues(new int[] { 2, 3, 4, 5, 6, 7, 8, 9 }, puzzle.getPossibleValues(4, 1));
        assertPossibleValues(new int[] { 2, 3, 4, 5, 6, 7, 8, 9 }, puzzle.getPossibleValues(5, 1));
        assertPossibleValues(new int[] { 2, 3, 4, 5, 6, 7, 8, 9 }, puzzle.getPossibleValues(6, 1));
        assertPossibleValues(new int[] { 2, 3, 4, 5, 6, 7, 8, 9 }, puzzle.getPossibleValues(7, 1));
        assertPossibleValues(new int[] { 2, 3, 4, 5, 6, 7, 8, 9 }, puzzle.getPossibleValues(8, 1));

        assertPossibleValues(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 }, puzzle.getPossibleValues(1, 3));
    }

    public void testRemoveSolvedValuesFromPeers_NewSolvedValue() throws DuplicateBoxesWithSameSolutionException {
        String row1 = ".1.......";
        String row2 = ".2.......";
        String row3 = ".3.......";
        String row4 = ".4.......";
        String row5 = ".5.......";
        String row6 = ".6.......";
        String row7 = ".7.......";
        String row8 = ".........";
        String row9 = ".9.......";
        Puzzle puzzle = new Puzzle(row1 + row2 + row3 + row4 + row5 + row6 + row7 + row8 + row9);

        puzzle.removeSolvedValuesFromPeers();

        assertPossibleValues(new int[] { 8 }, puzzle.getPossibleValues(7, 1));

        assertPossibleValues(new int[] { 1, 2, 3, 4, 5, 6 }, puzzle.getPossibleValues(6, 0));
        assertPossibleValues(new int[] { 1, 2, 3, 4, 5, 6 }, puzzle.getPossibleValues(6, 2));
        assertPossibleValues(new int[] { 1, 2, 3, 4, 5, 6 }, puzzle.getPossibleValues(7, 0));
        assertPossibleValues(new int[] { 1, 2, 3, 4, 5, 6 }, puzzle.getPossibleValues(7, 2));
        assertPossibleValues(new int[] { 1, 2, 3, 4, 5, 6 }, puzzle.getPossibleValues(8, 0));
        assertPossibleValues(new int[] { 1, 2, 3, 4, 5, 6 }, puzzle.getPossibleValues(8, 2));

        assertPossibleValues(new int[] { 1, 2, 3, 4, 5, 6, 7, 9 }, puzzle.getPossibleValues(7, 3));
        assertPossibleValues(new int[] { 1, 2, 3, 4, 5, 6, 7, 9 }, puzzle.getPossibleValues(7, 4));
        assertPossibleValues(new int[] { 1, 2, 3, 4, 5, 6, 7, 9 }, puzzle.getPossibleValues(7, 5));
        assertPossibleValues(new int[] { 1, 2, 3, 4, 5, 6, 7, 9 }, puzzle.getPossibleValues(7, 6));
        assertPossibleValues(new int[] { 1, 2, 3, 4, 5, 6, 7, 9 }, puzzle.getPossibleValues(7, 7));
        assertPossibleValues(new int[] { 1, 2, 3, 4, 5, 6, 7, 9 }, puzzle.getPossibleValues(7, 8));

        assertPossibleValues(new int[] { 1 }, puzzle.getPossibleValues(0, 1));
        assertPossibleValues(new int[] { 2 }, puzzle.getPossibleValues(1, 1));
        assertPossibleValues(new int[] { 3 }, puzzle.getPossibleValues(2, 1));
        assertPossibleValues(new int[] { 4 }, puzzle.getPossibleValues(3, 1));
        assertPossibleValues(new int[] { 5 }, puzzle.getPossibleValues(4, 1));
        assertPossibleValues(new int[] { 6 }, puzzle.getPossibleValues(5, 1));

    }

    private void assertPossibleValues(int[] expectedPossibleValues, Integer[] actualPossibleValues) {
        assertEquals("same number of possible values", expectedPossibleValues.length, actualPossibleValues.length);
        for (int i = 0; i < expectedPossibleValues.length; i++)
            assertEquals("possible value at index " + i, expectedPossibleValues[i], actualPossibleValues[i].intValue());
    }

    public void testRemoveSolvedValuesFromPeers_ReachContradiction() {
        String row1 = ".3.....3.";
        String row2 = ".........";
        String row3 = ".........";
        String row4 = ".........";
        String row5 = ".........";
        String row6 = ".........";
        String row7 = ".........";
        String row8 = ".........";
        String row9 = ".........";
        Puzzle puzzle = new Puzzle(row1 + row2 + row3 + row4 + row5 + row6 + row7 + row8 + row9);

        try {
            puzzle.removeSolvedValuesFromPeers();
            fail("should fail");
        } catch (DuplicateBoxesWithSameSolutionException e) {
            // good
        }
    }

    public void testGetUnsolvedBoxes() {
        String initialState = ".2.4";

        Puzzle puzzle = new Puzzle(initialState);

        List<Box> unsolvedBoxes = puzzle.getUnsolvedBoxes();

        assertEquals(2, unsolvedBoxes.size());
        assertEquals(0, unsolvedBoxes.get(0).row());
        assertEquals(0, unsolvedBoxes.get(0).column());
        assertEquals(1, unsolvedBoxes.get(1).row());
        assertEquals(0, unsolvedBoxes.get(1).column());
    }

    public void testSolveByPropagatingKnownValues() throws DuplicateBoxesWithSameSolutionException {
        String row1 = "..3.2.6..";
        String row2 = "9..3.5..1";
        String row3 = "..18.64..";
        String row4 = "..81.29..";
        String row5 = "7.......8";
        String row6 = "..67.82..";
        String row7 = "..26.95..";
        String row8 = "8..2.3..9";
        String row9 = "..5.1.3..";

        Puzzle puzzle = new Puzzle(row1 + row2 + row3 + row4 + row5 + row6 + row7 + row8 + row9);

        puzzle.removeSolvedValuesFromPeers();

        assertTrue(puzzle.isSolved());
        assertEquals("483921657" + "967345821" + "251876493" + "548132976" + "729564138" + "136798245" + "372689514"
                + "814253769" + "695417382", puzzle.solutionAsSingleString());
    }

//     public void testPropagateConstraints_InsufficientToSolvePuzzle() throws DuplicateBoxesWithSameSolutionException {
//         String row1 = "4.....8.5";
//         String row2 = ".3.......";
//         String row3 = "...7.....";
//         String row4 = ".2.....6.";
//         String row5 = "....8.4..";
//         String row6 = "....1....";
//         String row7 = "...6.3.7.";
//         String row8 = "5..2.....";
//         String row9 = "1.4......";

//         Puzzle puzzle = new Puzzle(row1 + row2 + row3 + row4 + row5 + row6 + row7 + row8 + row9);

//         boolean solved = puzzle.propagateConstraints(puzzle);
//         assertFalse(solved);

//         assertRow(puzzle, 0, new String[] { "4", "1679", "12679", "139", "2369", "269", "8", "1239", "5" });
//         assertRow(puzzle, 1, new String[] { "26789", "3", "1256789", "14589", "24569", "245689", "12679", "1249",
//                 "124679" });
//         assertRow(puzzle, 2, new String[] { "2689", "15689", "125689", "7", "234569", "245689", "12369", "12349",
//                 "123469" });
//         assertRow(puzzle, 3, new String[] { "3789", "2", "15789", "3459", "34579", "4579", "13579", "6", "13789" });
//         assertRow(puzzle, 4, new String[] { "3679", "15679", "15679", "359", "8", "25679", "4", "12359", "12379" });
//         assertRow(puzzle, 5, new String[] { "36789", "4", "56789", "359", "1", "25679", "23579", "23589", "23789" });
//         assertRow(puzzle, 6, new String[] { "289", "89", "289", "6", "459", "3", "1259", "7", "12489" });
//         assertRow(puzzle, 7, new String[] { "5", "6789", "3", "2", "479", "1", "69", "489", "4689" });
//         assertRow(puzzle, 8, new String[] { "1", "6789", "4", "589", "579", "5789", "23569", "23589", "23689" });
//     }

    private void assertRow(Puzzle puzzle, int rowIndex, String[] possibleValues) {
        for (int columnIndex = 0; columnIndex < possibleValues.length; columnIndex++)
            assertPossibleValues(possibleValues[columnIndex], puzzle.getBox(rowIndex, columnIndex));
    }

    private void assertPossibleValues(String expectedPossibleValues, Box box) {
        assertEquals("same number of possible values", expectedPossibleValues.length(), box.numberOfPossibleValues());

        String actualPossibleValues = "";
        List<Integer> possibleValues = box.getPossibleValues();
        for (int actualPossibleValue : possibleValues)
            actualPossibleValues += actualPossibleValue;
        assertEquals("possible values", expectedPossibleValues, actualPossibleValues);
    }

//     public void testSolveWithDepthFirstSearch() {
//         String row1 = "4.....8.5";
//         String row2 = ".3.......";
//         String row3 = "...7.....";
//         String row4 = ".2.....6.";
//         String row5 = "....8.4..";
//         String row6 = "....1....";
//         String row7 = "...6.3.7.";
//         String row8 = "5..2.....";
//         String row9 = "1.4......";

//         Puzzle puzzle = new Puzzle(row1 + row2 + row3 + row4 + row5 + row6 + row7 + row8 + row9);

//         boolean solved = puzzle.solve();
//         assertTrue("should be solved", solved);
//         assertEquals("417369825" + "632158947" + "958724316" + "825437169" + "791586432" + "346912758" + "289643571"
//                 + "573291684" + "164875293", puzzle.solutionAsSingleString());
//     }
}
