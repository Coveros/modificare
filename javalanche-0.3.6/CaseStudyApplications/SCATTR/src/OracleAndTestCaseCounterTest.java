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
 * These test cases test the OracleAndTestCaseCounter class.
 * 
 * 
 * @author Joshua Geiger
 * @author Gavilan Steinman
 *
 */
public class OracleAndTestCaseCounterTest {

	/**
	 * This test case tests the testGetNumTest method of the 
	 *  OracleAndTestCaseCounterTest class.
	 *
	 */
	@Test
	public void testGetNumTest() {
		OracleAndTestCaseCounter o = new OracleAndTestCaseCounter(
				"FilesForTestCases/sampleProgram/");
		assertEquals(o.getNumTest(),"5");
	}

	/**
	 * This test case tests the testGetNumOracles method of the 
	 *  OracleAndTestCaseCounterTest class.
	 *
	 */
	@Test
	public void testGetNumOracles() {
		OracleAndTestCaseCounter o = new OracleAndTestCaseCounter(
				"FilesForTestCases/sampleProgram/");
		assertEquals(o.getNumOracles(),"7");
	}
	
}
