package at.pm.rs.graphics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import at.pm.rs.connection.SetOfData;
import at.pm.rs.connection.TableData;
import at.pm.rs.graphics.dot.Node;

/**
 * This class creates a Writer object that prints the given
 * {@link rs.at.pm.connection.SetOfData} modelled to a entity-relationship-model
 * to a file with graphviz.
 * 
 * @author Patrick Malik
 * @version 20150114
 *
 */
public class GraphWriter extends FileWriter {

	// @Override
	public void print(TableData[] data) {
		PrintWriter writer = null;
		File output = new File(this.getOutputDir() + "/ER.dot");
		System.out.println(output.getAbsolutePath());
		try {
			writer = new PrintWriter(output, "UTF-8");
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

	@Override
	public String model(TableData[] datasets) {
		String content = "";
		// For shaping them as entities or attributes
		ArrayList<String> tables = new ArrayList<>();
		// ArrayList<String> attributes = new ArrayList<>();
		// The relations between the entities or attributes get mapped in this
		// map
		HashMap<String, String> rel = new HashMap<>();
		HashMap<String, ArrayList<Node>> duplicates = new HashMap<>();

		content += "graph ER {\n";

		int i = 0;

		for (TableData data : datasets) {
			String curTableName = data.getTableName();
			tables.add(curTableName);
			// ArrayList<String> values = new ArrayList<>();

			for (SetOfData cur : data.getSetOfData()) {
				// if(attributes.contains(cur.getName())){
				// String rn = cur.getName()+1;
				// attributes.add(rn);
				// rel.put(rn, curTableName);
				// duplicates.put(cur.getName(), );
				// i++;
				// }else{
				// attributes.add(cur.getName());
				// rel.put(cur.getName(), curTableName);
				// }

				if (!duplicates.containsKey(cur.getName()))
					duplicates.put(cur.getName(), new ArrayList<>());

				duplicates.get(cur.getName()).add(new Node(cur.getName() + i, cur));
				rel.put(cur.getName() + i, curTableName);

				i++;

			}

		}

		content += "node [shape=box];";

		for (String s : tables)
			content += s + ";";

		content += "\nnode [shape=ellipse];";

		for (Entry<String, ArrayList<Node>> entry : duplicates.entrySet()) {
			ArrayList<Node> values = entry.getValue();

			String doub = "{node [label=\"" + entry.getKey() + "\" decorate=\"true\"] ";

			for (Node val : values) {
				doub += val.getName();

				if (val.getParent().getIsPk())
					doub += " [label=<<u>" + entry.getKey() + "</u>>]";

				doub += ";";
			}

			doub += "}";
			content += doub + "\n";
		}

		// for (String s : attributes){
		// content += s + ";";
		// }

		System.out.println(rel.toString());

		for (Map.Entry<String, String> entry : rel.entrySet()) {
			content += "\n" + entry.getKey() + " -- " + entry.getValue();
		}

		content += "\n}";
		System.out.println(content);
		return content;
	}
}
