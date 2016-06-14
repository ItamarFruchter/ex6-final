package oop.ex6.fileprocessing;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * this class process the file containing the code 
 */
public class FileProcessor {	
		
	private FileProcessor() {}
	
	/**
	 * @param file - the file containing the code
	 * @return an array containing all the lines of the code
	 * @throws MyIOException - when there is a problem accessing the file
	 */
	public static String[] readFile(File file) throws IOException{
		LinkedList<String> lines = new LinkedList<String>();
		BufferedReader br;
		br = new BufferedReader(new FileReader(file));
		String str = br.readLine();
		while (str != null){
			lines.add(str);
		}
		br.close();
		return lines.toArray(new String[lines.size()]);
	}
}
