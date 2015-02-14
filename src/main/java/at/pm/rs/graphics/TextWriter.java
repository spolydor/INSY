package at.pm.rs.graphics;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import at.pm.rs.connection.SetOfData;

/**
 * This class creates a Writer object that prints text to a file. It generates
 * from the given SetOfData the fitting relations model.
 * 
 * @author Patrick Malik
 * @version 20150114
 */
public class TextWriter extends FileWriter {

	/**
	 * prints the given SetOfData as RM to a file.
	 * 
	 * @see Writer#print(rs.at.pm.connection.SetOfData)
	 * @param data
	 *            the SetOfData to be printed.
	 */
	@Override
	public void print(SetOfData data) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(this.getOutputDir() + "/RM.html", "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO logger error
			writer.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Logger error
			writer.close();
		}
		writer.print(model(data));
		writer.close();
	}

	/**
	 * Creates html-code in a String out of the {@link SetOfData} given as
	 * parameter.
	 * 
	 * @param data
	 *            the given {@link SetOfData}
	 * @return a html representation of the RM created from the
	 *         {@link SetOfData}
	 */
	private String model(SetOfData data) {
	}
	
	
	private String tableBegin(String tableName){
		return tableName+"(";
	}
	
	private String tableEnd(){
		return ")";
	}
	
}
