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
import java.io.File;
import java.util.ArrayList;

/**
 * 
 * @author Joshua J. Geiger
 * @author Gavilan T. Steinman
 * 
 */
public class FileList {
	/**
	 * Directory from whence the search originates.
	 */
	private String root;

	/**
	 * List of files obtained from recursing through the directory specified
	 * durring construction.
	 */
	private ArrayList<String> list;

	/**
	 * The main method constructs a FileList and prints the string
	 * representation.
	 * 
	 * @param args,
	 *            where args[0] = root, args[1] = extention filter
	 */
	public static void main(String[] args) {
		FileList list = new FileList(args[0], args[1]);
		System.out.println(list);
	}

	/**
	 * The constructor creates the list from the specified root and extension by
	 * calling 'getList'.
	 * 
	 * @param root
	 * @param extension
	 */
	public FileList(String root, String extension) {
		this.root = root;
		list = this.getList(root, extension);
	}

	/**
	 * Creates the list from the specified root and extension.
	 * 
	 * @param root
	 * @param extension
	 * @return list
	 */
	private ArrayList getList(String root, String extension) {
		ArrayList<String> finalList = new ArrayList<String>();
		File file = new File(root);
		String[] fileList = file.list();
		for (int i = 0; i < fileList.length; i++) {
			String fileString = root + "/" + fileList[i];
			File dir = new File(fileString);

			boolean isDir = dir.isDirectory();
			if (isDir) {
				finalList.addAll(this.getList(fileString + "", extension));
			} else {
				if ((fileString.substring((fileString.length() - (extension
						.length())), fileString.length())).equals(extension)) {
					finalList.add(fileString);
				}
			}
		}
		return finalList;
	}

	/**
	 * @return list
	 */
	public ArrayList getList() {
		return list;
	}

	/**
	 * @return String representation.
	 */
	public String toString() {
		return list.toString();
	}
}
