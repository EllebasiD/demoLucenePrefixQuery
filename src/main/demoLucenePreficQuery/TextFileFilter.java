package main.demoLucenePreficQuery;

import java.io.File;
import java.io.FileFilter;
/**
 * This class filters the type of documents accepted, here only documents with ".txt" extension are chosen.
 * 
 * @author Isabelle Deligniere
 */
public class TextFileFilter implements FileFilter {
	/**
     * This method tests whether or not the specified abstract pathname should be included in a pathname list.
     * 
     * @param pathname	the pathname of the file.
     * @return true 	if and only if pathname should be included.
     */
	@Override
	public boolean accept(File pathname) {
		return pathname.getName().toLowerCase().endsWith(".txt");
	}

}
