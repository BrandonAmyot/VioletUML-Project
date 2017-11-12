package com.horstmann.violet.framework.file;

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
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import com.horstmann.violet.product.diagram.abstracts.IGraph;
import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.node.INode;

public class StatisticsAnalyzer {
	private IGraph graph;
	private String HtmlFile;
	private int numOfDependency;
	private int numOfAssociation;
	private int numOfAggregation;
	private int numOfComposition;
	private List<ClassNodes> classNodes;

	private class ClassNodes {
		String id;
		String name;
		int numOfCBO;
		int numOfMethods;
		
		public ClassNodes(String id, String name) {
			this.id = id;
			this.name = name;
			numOfCBO = 0;
			numOfMethods = 0;
		}
		
		public void setCBO(int cbo) {
			numOfCBO = cbo;
		}
		
		public int getCBO() {
			return numOfCBO;
		}
		
		public void setNumOfMethods(int num) {
			numOfMethods = num;
		}
		
		public int getNumOfMethods() {
			return numOfMethods;
		}
		
		public String getId() {
			return id;
		}
		
		public String getName() {
			return name;
		}

		@Override
		public String toString() {
			return "\"" + this.name + "\"";
		}
	}
	
	public StatisticsAnalyzer(IGraph graph, String HtmlFile) {
		this.graph = graph;
		this.HtmlFile = HtmlFile;
		numOfDependency = 0;
		numOfAssociation = 0;
		numOfAggregation = 0;
		numOfComposition = 0;
		classNodes = new ArrayList<ClassNodes>();
		
		
		analyze();
	}

	private void analyze() {
		Collection<INode> Graphnodes = this.graph.getAllNodes();
		Collection<IEdge> edges = graph.getAllEdges();
	
		String html = stringifyHtml();
		
		for (INode node : Graphnodes) {
			if(node.getClass().getSimpleName().equals("ClassNode")) {
				String id = node.getId().toString();
				String name = getName(html, id);
				int numOfMethods = getNumOfMethods(html, id);
				
				
				ClassNodes classNode = new ClassNodes(id, name);
				classNode.setNumOfMethods(numOfMethods);
				
				classNodes.add(classNode);
			}
		}
		
		for (IEdge edge : edges) {
			if(edge.getClass().getSimpleName().equals("DependencyEdge")) numOfDependency++;
			if(edge.getClass().getSimpleName().equals("AssociationEdge")) numOfAssociation++;
			if(edge.getClass().getSimpleName().equals("AggregationEdge")) numOfAggregation++;
			if(edge.getClass().getSimpleName().equals("CompositionEdge")) numOfComposition++;
		}
		
	}

	public void writeStats(String filename) {

		try {
			PrintWriter pw = new PrintWriter(new FileOutputStream(filename));
			System.out.println("Writing statistics...");
			
			// Write # of classes on first line
			pw.println(classNodes.size());
			
			// Write # of relationships
			pw.println(this.numOfDependency + " " + this.numOfAssociation + " " + this.numOfAggregation + " " + this.numOfComposition);
			
			// Write name + # of methods + CBO for each class
			for (ClassNodes classNode : classNodes) {
				pw.println("\"" + classNode.getName() + "\" " + classNode.getNumOfMethods() + " " + classNode.getCBO());
			}

			pw.close();
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
	
	private int getNumOfMethods (String html, String id) {
		Document doc = Jsoup.parse(html);
		
		Element classNodeElement = doc.getElementsByAttributeValueEnding("value", id).first().parent();
		String methodsString = classNodeElement.getElementsByTag("methods").first().text();
		String[] methods = methodsString.split("\\s+");
		
		int numOfMethods = 0;
		for (String method : methods) {
				if(!method.isEmpty()) numOfMethods++;
		}
		
		return numOfMethods;
	}
}
