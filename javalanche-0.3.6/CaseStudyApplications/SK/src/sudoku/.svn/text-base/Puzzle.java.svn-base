package src.sudoku;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Puzzle {
    List<Box> solvedBoxes = new ArrayList<Box>();

    private int dimension;

    private Box[][] puzzle;

    public Puzzle(String initialState) {
        dimension = ((int) Math.sqrt(SudokuUtils.length(initialState)));
        validateInputString(initialState);

        solvedBoxes = parseSolvedBoxesFromInputString(initialState);
        puzzle = initializePuzzle();
        setSolvedBoxesInPuzzle(solvedBoxes);
    }

    public Puzzle(Puzzle anotherPuzzle, Box seededValue) {
        dimension = anotherPuzzle.dimension;
        solvedBoxes.add(seededValue);
        puzzle = initializePuzzle();
        copyPossibleValues(anotherPuzzle);
        puzzle[seededValue.row()][seededValue.column()] = new Box(dimension, seededValue.row(), seededValue.column(),
                seededValue.getSolvedValue());
    }

    public boolean solve() {
        return solve(this);
    }

    /**
     * first eliminate as many possibilities as possible by propagating
     * constraints, then try searching.
     * 
     * @param originalPuzzle
     *            contains the solution to the puzzle at the end
     * @return true if this solves the puzzle; false otherwise
     */
    public boolean solve(Puzzle originalPuzzle) {
        try {
            if (propagateConstraints(originalPuzzle))
                return true;
        } catch (DuplicateBoxesWithSameSolutionException e) {
            return false;
        }

        return search(originalPuzzle);
    }

    /**
     * start by removing all solved values as possibilities from peers. next
     * find any boxes where a possible value does not match any possible values
     * in at least one of its units, thus solving such boxes; remove these too
     * from peers, and so on until this process is exhausted.
     * 
     * @param originalPuzzle
     *            contains the solution to the puzzle at the end
     * @return true if this solves the puzzle; false otherwise
     * @throws DuplicateBoxesWithSameSolutionException
     *             if the same solution shows up in two boxes
     */
    public boolean propagateConstraints(Puzzle originalPuzzle) throws DuplicateBoxesWithSameSolutionException {
        do {
            removeSolvedValuesFromPeers();
            if (isSolved()) {
                // puzzle has been solved. copy solution into original
                originalPuzzle.copyPossibleValues(this);
                return true;
            }

            setSolvedBoxesInPuzzle(getUniquelySpecifiedBoxes());

            checkForDuplicates();
        } while (solvedBoxes.size() > 0);
        return false;
    }

    /**
     * find the unsolved box with the smallest number of possible values. assume
     * one of those values is correct and try solving the puzzle. if this
     * doesn't work, try the next possible value, etc. until the puzzle is
     * solved or all possible values are exhausted (in which case the puzzle has
     * no single solution)
     * 
     * @param originalPuzzle
     *            contains the solution to the puzzle at the end
     * @return true if this solves the puzzle; false otherwise
     */
    public boolean search(Puzzle originalPuzzle) {
        Box firstUnsolvedBox = min(getUnsolvedBoxes());
        for (int possibleValue : firstUnsolvedBox.getPossibleValues()) {
            Box seededValue = new Box(dimension(), firstUnsolvedBox.row(), firstUnsolvedBox.column(), possibleValue);
            Puzzle copyOfPuzzle = new Puzzle(this, seededValue);
            if (copyOfPuzzle.solve(originalPuzzle))
                return true;
        }
        return false;
    }

    public void checkForDuplicates() throws DuplicateBoxesWithSameSolutionException {
        for (int row = 0; row < dimension(); row++)
            for (int column = 0; column < dimension(); column++)
                if (getBox(row, column).anyPeerHasDuplicateSolvedValue(this))
                    throw new DuplicateBoxesWithSameSolutionException("reached contradiction");
    }

    public void removeSolvedValuesFromPeers() throws DuplicateBoxesWithSameSolutionException {
        while (solvedBoxes.size() > 0) {
            Box solvedBox = solvedBoxes.get(0);
            solvedBoxes.addAll(solvedBox.removeSolvedValuesFromPeers(this));
            solvedBoxes.remove(0);
        }
    }

    public List<Box> getUniquelySpecifiedBoxes() {
        Set<Box> uniquelySpecifiedBoxes = new HashSet<Box>();
        for (Box unsolvedBox : getUnsolvedBoxes()) {
            int matchFound = 0;
            List<Integer> possibleValues = unsolvedBox.getPossibleValues();

            for (Integer possibleValue : possibleValues)
                for (List<Box> peers : unsolvedBox.getPeersPerUnit(this)) {
                    if (!doesPossibleValueAppearInAnyPeers(possibleValue, peers)) {
                        Box solvedBox = new Box(dimension(), unsolvedBox.row(), unsolvedBox.column(), possibleValue);
                        uniquelySpecifiedBoxes.add(solvedBox);
                    }
                }
        }
        return new ArrayList<Box>(uniquelySpecifiedBoxes);
    }

    public List<Box> getUnsolvedBoxes() {
        List<Box> unsolvedBoxes = new ArrayList<Box>();
        for (int currentRow = 0; currentRow < dimension; currentRow++) {
            for (int currentColumn = 0; currentColumn < dimension; currentColumn++) {
                Box candidate = getBox(currentRow, currentColumn);
                if (!candidate.isSolved())
                    unsolvedBoxes.add(candidate);
            }
        }
        return unsolvedBoxes;
    }

    public int dimension() {
        return dimension;
    }

    public List<Box> getSolvedBoxes() {
        return solvedBoxes;
    }

    public int getSolvedValue(int row, int column) {
        for (Box box : solvedBoxes) {
            if (box.row() == row && box.column() == column)
                return box.getSolvedValue();
        }

        throw new RuntimeException("box: " + row + ", " + column + " has not been solved");
    }

    public Integer[] getPossibleValues(int row, int column) {
        List<Integer> possibleValues = getBox(row, column).getPossibleValues();
        return possibleValues.toArray(new Integer[possibleValues.size()]);
    }

    public boolean isSolved() {
        for (int currentRow = 0; currentRow < dimension; currentRow++) {
            for (int currentColumn = 0; currentColumn < dimension; currentColumn++) {
                Box candidate = getBox(currentRow, currentColumn);
                if (!candidate.isSolved())
                    return false;
            }
        }
        return true;
    }

    public List<Box> getPeers(int row, int column) {
        return getBox(row, column).getPeers(this);
    }

    public List<Box> getPeersInSameRow(int row, int column) {
        return getBox(row, column).getPeersInSameRow(this);
    }

    public List<Box> getPeersInSameColumn(int row, int column) {
        return getBox(row, column).getPeersInSameColumn(this);
    }

    public List<Box> getPeersInSameSubSquare(int row, int column) {
        return getBox(row, column).getPeersInSameSubSquare(this);
    }

    public Box getBox(int currentrow, int currentcolumn) {
        return puzzle[currentrow][currentcolumn];
    }

    public String solutionAsSingleString() {
        String solution = "";
        for (int row = 0; row < dimension; row++)
            for (int column = 0; column < dimension; column++)
                solution += (puzzle[row][column].getSolvedValue());
        return solution;
    }

    private Box min(List<Box> unsolvedBoxes) {
        Box boxWithMinimumPossibleValues = null;
        for (Box box : unsolvedBoxes) {
            if (boxWithMinimumPossibleValues == null
                    || box.numberOfPossibleValues() < boxWithMinimumPossibleValues.numberOfPossibleValues())
                boxWithMinimumPossibleValues = box;
        }

        return boxWithMinimumPossibleValues;
    }

    private boolean doesPossibleValueAppearInAnyPeers(Integer possibleValue, List<Box> peers) {
        for (Box peer : peers) {
            if (peer.getPossibleValues().contains(possibleValue)) {
                return true;
            }
        }

        return false;
    }

    private void validateInputString(String initialState) {
        if (SudokuUtils.isEmpty(initialState))
            throw new RuntimeException("empty input");

        if (dimension * dimension != SudokuUtils.length(initialState))
            throw new RuntimeException("invalid input length: " + SudokuUtils.length(initialState));
    }

    private Box[][] initializePuzzle() {
        Box[][] puzzle = new Box[dimension][dimension];
        for (int row = 0; row < dimension; row++)
            for (int column = 0; column < dimension; column++)
                puzzle[row][column] = new Box(dimension, row, column);
        return puzzle;
    }

    private List<Box> parseSolvedBoxesFromInputString(String initialState) {
        List<Box> solvedBoxes = new ArrayList<Box>();
        int row = 0;
        int column = 0;
        for (int i = 0; i < initialState.length(); i++) {
            if (Character.isDigit(initialState.charAt(i)) && initialState.charAt(i) != '0') {
                int currentValue = Character.getNumericValue(initialState.charAt(i));
                Box box = new Box(dimension, row, column);
                box.setValue(currentValue);
                solvedBoxes.add(box);
            }
            column = ++column % dimension;
            if (column == 0)
                row++;
        }

        return solvedBoxes;
    }

    private void setSolvedBoxesInPuzzle(List<Box> solvedBoxes) {
        this.solvedBoxes = solvedBoxes;
        for (Box solvedBox : solvedBoxes)
            puzzle[solvedBox.row()][solvedBox.column()] = solvedBox;
    }

    private void copyPossibleValues(Puzzle anotherPuzzle) {
        for (int row = 0; row < dimension; row++) {
            for (int column = 0; column < dimension; column++) {
                Box box = getBox(row, column);
                box.copyPossibleValues(anotherPuzzle.getBox(row, column).getPossibleValues());
            }
        }
    }
}