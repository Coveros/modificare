/*
## This file is part of SCATTR
##
## SCATTR is free software:  you can redistrubite it and/or modify it under the 
## terms of the GNU General Public License as published by the Free Software 
## Foundation, either version 3 of the License, or any later version.
##
## SCATTR is distributed in the hope that it will be useful, but WITHOUT ANY 
## WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
## FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more 
## details.
##
## You should have received a copy of the GNU General Public License along with 
## Foobar.  If not, see <http://www.gnu.org/licenses/>.
##
## Copyright 2007
*/

//10-4-07

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * 
 * @author Joshua J. Geiger
 * @author Gavilan T. Steinman
 * 
 */
public class OracleAndTestCaseCounter {

	/**
	 * Directory from whence the search originates.
	 */
	String root;

	/**
	 * List of files to be searched. Obtained through the FileList class.
	 */
	ArrayList<String> list;

	/**
	 * Increments with each test found.
	 */
	int testCounter;

	/**
	 * Increments with each Oracle executions found.
	 */
	int testOracle;

	/**
	 * Main method. Executes the constructor and passes it the root and calls
	 * toString on the constructed object.
	 * 
	 * @param args,
	 *            where args[0] = root.
	 */
	public static void main(String args[]) {
		OracleAndTestCaseCounter t = new OracleAndTestCaseCounter(args[0]);
		System.out.println(t);
	}

	/**
	 * The constructor generates a list of files from the specified root and
	 * calls the 'countTestCasesAndOraclesInFile' method on each file,
	 * incrementing the values of 'testCounter' and 'testOracle' respectively.
	 * 
	 * @param root
	 */
	public OracleAndTestCaseCounter(String root) {
		this.root = root;
		FileList files = new FileList(root, ".java");
		this.list = files.getList();
		this.testCounter = 0;
		this.testOracle = 0;
		for (int i = 0; i < list.size(); i++) {
			this.countTestCasesAndOraclesInFile(list.get(i));
		}
	}

	/**
	 * Counts test cases and oracles in a file by parsing for "public void test"
	 * and "assertEquals", "assertTrue", "assertNull", "assertNotNull",
	 * "assertSame", "assertNotSame" respectively.
	 * 
	 * @param fileName
	 */
	private void countTestCasesAndOraclesInFile(String fileName) {
		try {
			// System.out.println(fileName);
			File filePointer = new File(fileName);
			FileReader file = new FileReader(filePointer);

			BufferedReader fileBuffer = new BufferedReader(file);
			String line = fileBuffer.readLine();
			while (line != null) {
				if (line.indexOf("public void test") != -1) {
					this.testCounter++;
				}

				// TODO: make a note of what asserts we are covering and check
				// to see if there are other oracle executions
				if (line.indexOf("assertEquals") != -1) {
					this.testOracle++;
				} else if (line.indexOf("assertTrue") != -1) {
					this.testOracle++;
				} else if (line.indexOf("assertFalse") != -1) {
					this.testOracle++;
				} else if (line.indexOf("assertNull") != -1) {
					this.testOracle++;
				} else if (line.indexOf("assertNotNull") != -1) {
					this.testOracle++;
				} else if (line.indexOf("assertSame") != -1) {
					this.testOracle++;
				} else if (line.indexOf("assertNotSame") != -1) {
					this.testOracle++;
				}

				line = fileBuffer.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + fileName);
		} catch (IOException e) {
			System.out.println("IOException found.");
		}
	}

	/**
	 * @return number of tests
	 */
	public String getNumTest() {
		return "" + this.testCounter;
	}

	/**
	 * @return number of oracles
	 */
	public String getNumOracles() {
		return "" + this.testOracle;
	}

	/**
	 * @return string representation
	 */
	public String toString() {
		String r = "";
		r += "Number of test cases: " + this.testCounter + "\n";
		r += "Number of oracles: " + this.testOracle + "\n";
		return r;
	}
}
