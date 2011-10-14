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
import java.io.FileReader;
import java.io.IOException;
import java.security.Permission;
import java.util.StringTokenizer;
import javancss.*;

/**
 * 
 * @author Joshua J. Geiger
 * @author Gavilan T. Steinman
 * 
 */
public class Main {
	public static String output;
	/**
	 * 
	 * This method coordinates the static analysis of a Java application using
	 * JavaNCSS and TestCaseAndOracleCounter. First, it creates a new Javancss
	 * object that saves all output to temporary file "temp.dat" in the current
	 * directory upon construction. <br />
	 * <br />
	 * Next, we parse the contents of "temp.dat" looking for the Average
	 * Function CCN, Program NCSS, and Number of JavaDocs per method. <br />
	 * <br />
	 * Next we utilize the TestCaseAndOracleCounter method to find the number of
	 * test cases and oracle execution contained in the application. <br />
	 * <br />
	 * Finally we summerize the data and print it to standard out in the
	 * following format:<br />
	 * <i>&gt; name numTestCases numOracles avgCyc avgSizeSource numJavaDocs</i>
	 * 
	 * @param args,
	 *            where args[0] = specified name, args[1] root.
	 */
	public static void main(String args[]) {

		String name = args[0];
		String sourceRoot = args[1];
		String numTestCases = "";
		String numOracles = "";
		String avgCyc = "";
		String avgSizeSource = "";
		String numJavaDocs = "";
		output = "";

		try {
			String fileName = "temp.dat";

			String commands[] = { "-recursive", "-package", "-function",
					sourceRoot, "-out", fileName };
			String S_RCS_HEADER = "$Header: /home/clemens/src/java/javancss/src/javancss/RCS/Main.java,v 28.49 2006/10/06 11:46:24 clemens Exp clemens $";
			Javancss pJavancss = new Javancss(commands, S_RCS_HEADER);

			int countJavadocs = 0;
			File filePointer = new File(fileName);
			FileReader file = new FileReader(filePointer);
			BufferedReader fileBuffer = new BufferedReader(file);
			String line = fileBuffer.readLine();

			while (line != null) {
				if (line.indexOf("Average Function CCN:") != -1) {
					avgCyc = (line.substring(22)).trim();
				} else if (line.indexOf("Program NCSS:") != -1) {
					avgSizeSource = (line.substring(14)).trim();
				} else if (line.indexOf("Javadocs") != -1) {
					if (countJavadocs == 0) {
						countJavadocs++;
					} else {
						line = fileBuffer.readLine();
						line = fileBuffer.readLine();
						line = fileBuffer.readLine();
						line = fileBuffer.readLine();
						line = fileBuffer.readLine();

						StringTokenizer tok = new StringTokenizer(line);
						tok.nextToken();
						numJavaDocs = tok.nextToken();
					}
				}

				line = fileBuffer.readLine();
			}
			OracleAndTestCaseCounter t = new OracleAndTestCaseCounter(
					sourceRoot);
			numTestCases = t.getNumTest();
			numOracles = t.getNumOracles();
			
			//todo: Check to verify that this is working
			avgSizeSource = avgSizeSource.replaceAll(",", "");
			output = name + "\t" + numTestCases + "\t" + numOracles
							+ "\t" + avgCyc + "\t" + avgSizeSource + "\t"
							+ numJavaDocs;
			System.out.println(output);
		} catch (IOException e) {
			System.out.println("Error running system command." + e);
		}

	}
}
