package src.sudoku;

import java.util.List;

public class SudokuUtils {

    public static int length(String str) {
        return str == null ? 0 : str.length();
    }

    public static boolean isEmpty(String val) {
        return val == null || val.equals("");
    }

    public static int length(List list) {
        return list == null ? 0 : list.size();
    }

}
