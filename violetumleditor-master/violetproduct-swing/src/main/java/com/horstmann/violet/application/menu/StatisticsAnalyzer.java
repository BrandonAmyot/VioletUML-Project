package com.horstmann.violet.application.menu;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import com.horstmann.violet.framework.file.IGraphFile;
import com.horstmann.violet.product.diagram.abstracts.IGraph;
import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.node.INode;

public class StatisticsAnalyzer {
	public static IGraphFile graphFile;
	private IGraph graph = graphFile.getGraph();
	private String HtmlFile;
	private int numOfActivationBars;
	private int numOfSynchronousCalls;
	private int numOfASynchronousCalls;
	private int numOfReturnEdges;
	private List<LifelineNode> lifelineNodesList;

	private class LifelineNode {
		String name;
		int numOfChildren;
		
		public LifelineNode(String name) {
			this.name = name;
			numOfChildren = 0;
		}
		
		public void setNumOfChildren(int number) {
			numOfChildren = number;
		}
		
		public int getNumOfChildren() {
			return numOfChildren;
		}
		
		public String getName() {
			return name;
		}

		@Override
		public String toString() {
			return "\"" + this.name + "\"";
		}
	}
	
	public StatisticsAnalyzer(String HtmlFile) {
		this.HtmlFile = HtmlFile;
		numOfActivationBars = 0;
		numOfSynchronousCalls = 0;
		numOfASynchronousCalls = 0;
		numOfReturnEdges = 0;
		lifelineNodesList = new ArrayList<LifelineNode>();
		
		
		analyze();
	}

	private void analyze() {
		Collection<INode> Graphnodes = this.graph.getAllNodes();
		Collection<IEdge> edges = graph.getAllEdges();
	
		String html = stringifyHtml();
		
		for (INode node : Graphnodes) {
			if(node.getClass().getSimpleName().equals("LifelineNode")) {
				String id = node.getId().toString();
				String name = getName(html, id);
				
				LifelineNode classNode = new LifelineNode(name);
				
				int numOfChildren = node.getChildren().size();			
				classNode.setNumOfChildren(numOfChildren);
				
				lifelineNodesList.add(classNode);
			}
			else if(node.getClass().getSimpleName().equals("ActivationBarNode")) {
				numOfActivationBars++;
			}
		}
		
		for (IEdge edge : edges) {
			if(edge.getClass().getSimpleName().equals("SynchronousCallEdge")) { 
				numOfSynchronousCalls++;				
			}
			if(edge.getClass().getSimpleName().equals("AsynchronousCallEdge")) { 
				numOfASynchronousCalls++;				
			}
			if(edge.getClass().getSimpleName().equals("ReturnEdge")) { 
				numOfReturnEdges++;				
			}
		}
		
	}

	public void writeStats(String filename) {

		try {
			PrintWriter writer = new PrintWriter(new FileOutputStream(filename));
			System.out.println("Writing statistics...");
			
			// Write number of lifelines
			writer.println("Number of Lifelines: " + lifelineNodesList.size());
			
			// Write lifeline name and number of children
			for (LifelineNode classNode : lifelineNodesList) {
				writer.println("\"" + classNode.getName() + "\"" + ": " + classNode.getNumOfChildren() + " children");
			}
			
			// Write number of relationships
			writer.println("\nNumber of relationships:\nActivation Bars: " + this.numOfActivationBars + "\nSynchronous Calls: " + this.numOfSynchronousCalls 
						+ "\nASynchronous Calls: " + this.numOfASynchronousCalls + "\nReturn Edges: " + this.numOfReturnEdges);
			

			writer.close();
			System.out.println("Success! The statistic file is located at '" + filename + "'");
		} catch (FileNotFoundException e) {
			System.err.println("Could not write to " + filename + ":");
			System.err.println(e.getMessage());
		}
	}

	private String stringifyHtml() {
		String editedHTML = "";
		FileInputStream fstream;
		try {
			fstream = new FileInputStream(HtmlFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

			String strLine;

			while ((strLine = br.readLine()) != null) {
				if (!strLine.contains("SCRIPT")) {
					editedHTML += strLine + "\n";
				}
			}

			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return editedHTML;
	}
	
	private String getName (String html, String id) {
		Document doc = Jsoup.parse(html);
		
		Element classNodeElement = doc.getElementsByAttributeValueEnding("value", id).first().parent();
		String name = classNodeElement.getElementsByTag("name").first().text();
		
		return name;
	}
	
}
