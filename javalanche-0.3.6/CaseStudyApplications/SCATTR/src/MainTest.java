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

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * These test cases test the Main class.
 * 
 * 
 * @author Joshua Geiger
 * @author Gavilan Steinman
 *
 */
public class MainTest {

	/**
	 * This test case tests the main method of the Main class.
	 *
	 */
	@Test
	public void testMain() {
		Main m = new Main();
		String [] args = {"Example","FilesForTestCases/sampleProgram/"};
		m.main(args);
		assertEquals(m.output, "Example	5	7	1.00	15.00	0.00");
	}

}
