package src.sudoku;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Box {
    private List<Integer> possibleValues = new ArrayList<Integer>();

    private int row;

    private int column;

    public Box(int puzzleDimension, int row, int column) {
        setRowAndColumn(row, column);
        for (int i = 0; i < puzzleDimension; i++)
            possibleValues.add(i + 1);

    }

    public Box(int dimension, int row, int column, int possibleValue) {
        setRowAndColumn(row, column);
        possibleValues.add(possibleValue);
    }

    public int numberOfPossibleValues() {
        return possibleValues.size();
    }

    public int row() {
        return row;
    }

    public int column() {
        return column;
    }

    public void setValue(int currentValue) {
        possibleValues = new ArrayList<Integer>();
        possibleValues.add(currentValue);
    }

    public void removePossibleValue(int valueToBeRemoved) {
        for (int i = 0; i < possibleValues.size(); i++)
            if (possibleValues.get(i).intValue() == valueToBeRemoved)
                possibleValues.remove(i);
    }

    public int getSolvedValue() {
        return possibleValues.get(0);
    }

    public boolean isSolved() {
        return SudokuUtils.length(possibleValues) == 1;
    }

    public List<Integer> getPossibleValues() {
        return possibleValues;
    }

    public void copyPossibleValues(List<Integer> possibleValues) {
        this.possibleValues = new ArrayList<Integer>();
        for (Integer possibleValue : possibleValues)
            this.possibleValues.add(possibleValue);
    }

    public List<Box> getPeers(Puzzle puzzle) {
        ArrayList<Box> peers = new ArrayList<Box>();
        addUnique(peers, getPeersInSameRow(puzzle));
        addUnique(peers, getPeersInSameColumn(puzzle));
        addUnique(peers, getPeersInSameSubSquare(puzzle));
        return peers;
    }

    public List<List<Box>> getPeersPerUnit(Puzzle puzzle) {
        List<List<Box>> peersPerUnit = new ArrayList<List<Box>>();
        peersPerUnit.add(getPeersInSameRow(puzzle));
        peersPerUnit.add(getPeersInSameColumn(puzzle));
        peersPerUnit.add(getPeersInSameSubSquare(puzzle));
        return peersPerUnit;
    }

    public List<Box> getPeersInSameRow(Puzzle puzzle) {
        ArrayList<Box> boxes = new ArrayList<Box>();
        for (int currentcolumn = 0; currentcolumn < puzzle.dimension(); currentcolumn++)
            boxes.add(puzzle.getBox(row(), currentcolumn));
        boxes.remove(this);
        return boxes;
    }

    public List<Box> getPeersInSameColumn(Puzzle puzzle) {
        ArrayList<Box> boxes = new ArrayList<Box>();
        for (int currentrow = 0; currentrow < puzzle.dimension(); currentrow++)
            boxes.add(puzzle.getBox(currentrow, column()));
        boxes.remove(this);
        return boxes;
    }

    public List<Box> getPeersInSameSubSquare(Puzzle puzzle) {
        List<Box> boxes = new ArrayList<Box>();
        int subSquares = (int) Math.sqrt(puzzle.dimension());

        int startingsSubSquareRow = (row() / subSquares) * subSquares;
        int startingSubSquareColumn = (column() / subSquares) * subSquares;

        int endingSubSquarerow = startingsSubSquareRow + subSquares;
        int endingSubSquarecolumn = startingSubSquareColumn + subSquares;

        for (int row = startingsSubSquareRow; row < endingSubSquarerow; row++)
            for (int column = startingSubSquareColumn; column < endingSubSquarecolumn; column++)
                boxes.add(puzzle.getBox(row, column));
        boxes.remove(this);
        return boxes;
    }

    public List<Box> removeSolvedValuesFromPeers(Puzzle puzzle) throws DuplicateBoxesWithSameSolutionException {
        List<Box> newSolvedBoxes = new ArrayList<Box>();
        List<Box> peers = getUnsolvedPeers(puzzle);
        for (Box peer : peers) {
            peer.removePossibleValue(getSolvedValue());

            if (peer.isSolved())
                newSolvedBoxes.add(peer);
        }

        puzzle.checkForDuplicates();

        return newSolvedBoxes;
    }

    public List<Box> getUnsolvedPeers(Puzzle puzzle) {
        List<Box> peers = getPeers(puzzle);
        for (Iterator<Box> iter = peers.iterator(); iter.hasNext();) {
            Box peer = iter.next();
            if (peer.isSolved())
                iter.remove();
        }
        return peers;
    }

    public boolean anyPeerHasDuplicateSolvedValue(Puzzle puzzle) {
        List<Box> peers = getPeers(puzzle);
        for (Box peer : peers) {
            if (peer.hasSameSolvedValue(this)) {
                return true;
            }
        }
        return false;
    }

    public boolean equals(Object o) {
        Box anotherBox = (Box) o;
        return row == anotherBox.row && column == anotherBox.column;
    }

    public int hashCode() {
        return ("" + row + "/" + column).hashCode();
    }

    public String toString() {
        String asString = "" + row + ":" + column + "[";
        for (int possibleValue : possibleValues) {
            asString += possibleValue;
        }

        asString += "]";

        return asString;
    }

    private boolean hasSameSolvedValue(Box box) {
        if (!isSolved() || !box.isSolved())
            return false;

        return getSolvedValue() == box.getSolvedValue();
    }

    private void setRowAndColumn(int row, int column) {
        this.row = row;
        this.column = column;
    }

    private void addUnique(ArrayList<Box> peers, List<Box> peersInSameRow) {
        for (Box peer : peersInSameRow)
            if (!peers.contains(peer))
                peers.add(peer);
    }
}
