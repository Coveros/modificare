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

import java.util.ArrayList;

import org.junit.Test;

/**
 * These test cases test the FileList class.
 * 
 * 
 * @author Joshua Geiger
 * @author Gavilan Steinman
 *
 */
public class FileListTest {

	/**
	 * This test case tests the testFileList method of the 
	 *  FileList class.
	 *
	 */
	@Test
	public void testFileList() {
		FileList f = new FileList("FilesForTestCases/sampleDir","");
		Object [] actualList = (f.getList().toArray());
		String [] expectedList = {"FilesForTestCases/sampleDir/c", 
				"FilesForTestCases/sampleDir/sub1/c", 
				"FilesForTestCases/sampleDir/sub1/a", 
				"FilesForTestCases/sampleDir/sub1/b", 
				"FilesForTestCases/sampleDir/a", 
				"FilesForTestCases/sampleDir/e", 
				"FilesForTestCases/sampleDir/d", 
				"FilesForTestCases/sampleDir/b"};
		assertEquals(actualList.length, expectedList.length);
		for(int i=0; i<actualList.length;i++){
			assertTrue(actualList[i].equals((String)expectedList[i]));
			assertFalse(actualList[i].equals(""));
		}
	}

	/**
	 * This test case tests the testToString method of the 
	 *  FileList class.
	 *
	 */
	@Test
	public void testToString() {
		FileList f = new FileList("FilesForTestCases/sampleDir","");
		assertEquals(f.toString(), "[FilesForTestCases/sampleDir/c, " +
				"FilesForTestCases/sampleDir/sub1/c, " +
				"FilesForTestCases/sampleDir/sub1/a, " +
				"FilesForTestCases/sampleDir/sub1/b, " +
				"FilesForTestCases/sampleDir/a, " +
				"FilesForTestCases/sampleDir/e, " +
				"FilesForTestCases/sampleDir/d, " +
				"FilesForTestCases/sampleDir/b]");
	}

}
