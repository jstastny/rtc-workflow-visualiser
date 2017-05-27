package com.stastnarodina.workflowVisualiser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.DOMReader;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Parser {
	
	
	
	public Parser() {}
	
	public Set<Workflow> parse(InputStream is) throws ParserConfigurationException, SAXException, IOException, DocumentException {
		Set<Workflow> workflows = new HashSet<>();
		  
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(false); // never forget this!
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		//Let SAX figure the correct format: See http://stackoverflow.com/questions/3482494/howto-let-the-sax-parser-determine-the-encoding-from-the-xml-declaration
		Reader isr = new InputStreamReader(is);
		InputSource iSour = new InputSource();
		iSour.setCharacterStream(isr);
		
		org.w3c.dom.Document doc = builder.parse(iSour);
		
		DOMReader domReader = new DOMReader();
		
		Document document = domReader.read(doc);
		
//		 SAXReader saxReader = new SAXReader();
//		 saxReader.setFeature("http://xml.org/sax/features/namespaces", false);
//		 Document document = saxReader.read(is);
//		
		
		List<?> nodes = document.selectNodes("//workflowDefinition");
		
		for(Object node: nodes) {
			Node element = (Node) node;
			Workflow workflow = new Workflow();
			workflow.setId(element.valueOf("@id"));
			Node workflowNode = element.selectSingleNode("./workflow");
			workflow.setDescription(workflowNode.valueOf("@description"));
			workflow.setName(workflowNode.valueOf("@name"));
			
			// Fetch resolutions
			for(Object resObj : workflowNode.selectNodes("resolution")) {
				Node resNode = (Node) resObj; 
				Resolution resolution = new Resolution();
				resolution.setId(resNode.valueOf("@id"));
				resolution.setName(resNode.valueOf("@name"));
				workflow.getResolutions().add(resolution);
			}
			
			// Fetch actions
			for(Object actionObj : workflowNode.selectNodes("action")) {
				Node actionNode = (Node) actionObj;
				Action action = new Action();
				action.setId(actionNode.valueOf("@id"));
				action.setName(actionNode.valueOf("@name"));
				// Target state is fetched later -- after the states are loaded
				for(Object resObj : actionNode.selectNodes("resolution")) {
					Node resNode = (Node) resObj;
					action.getResolutions().add(workflow.getResolutionById(resNode.valueOf("@id")));
				}
				workflow.getActions().add(action);
				
			}
			
			// Fetch states
			for(Object stateObj : workflowNode.selectNodes("state")) {
				Node stateNode = (Node) stateObj;
				State state = new State();
				state.setId(stateNode.valueOf("@id"));
				state.setGroup(Group.getByName(stateNode.valueOf("@group")));
				state.setShowResolution(Boolean.parseBoolean(stateNode.valueOf("@showResolution")));
				state.setName(stateNode.valueOf("@name"));
				for(Object actionObj : stateNode.selectNodes("action")) {
					Node actionNode = (Node) actionObj;
					Action action = workflow.getActionById(actionNode.valueOf("@id"));
					state.getActions().add(action);
				}
				workflow.getStates().add(state);
			}
			
			// Go trough actions again and add target states
			for(Object actionObj : workflowNode.selectNodes("action")) {
				Node actionNode = (Node) actionObj;
				Action action = workflow.getActionById(actionNode.valueOf("@id"));
				action.setTargetState(workflow.getStateById(actionNode.valueOf("@state")));
			}
			
			
			workflow.setStartAction(workflow.getActionById(workflowNode.valueOf("@startActionId")));
			workflow.setResolveAction(workflow.getActionById(workflowNode.valueOf("@resolveActionId")));
			workflow.setReopenAction(workflow.getActionById(workflowNode.valueOf("@reopenActionId")));	
			
			
			
			
			 
			workflows.add(workflow);
			
		}
		
		
		
//		Element root = doc.getDocumentElement();
//		
//		List nodes = XPath.selectNodes(root, "//target");
//		
//		for(Object node: nodes) {
//			System.out.println(node);
//		}
		
		
//        
//			
//		
//		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//		factory.setNamespaceAware(false); // never forget this!
//		DocumentBuilder builder = factory.newDocumentBuilder();
//		Document doc = builder.parse(is);
//		
//		XPathFactory xpathFactory = XPathFactory.newInstance();
//		XPath xpath = xpathFactory.newXPath();
//		//XPathExpression expr = xpath.compile("//book[author='Neal Stephenson']/title/text()");
//		XPathExpression expr = xpath.compile("/process-specification/project-configuration/data/configuration-data/workflowDefinition");
//		
//		Object result = expr.evaluate(doc, XPathConstants.NODESET);
//	    NodeList nodes = (NodeList) result;
//	    
//	    for (int i = 0; i < nodes.getLength(); i++) {
//	    	Workflow workflow = new Workflow();
//	    	Node workflowDefinitionNode = nodes.item(i);
//	    	workflow.setId(workflowDefinitionNode.getAttributes().getNamedItem("id").getTextContent());
//	    	
//	    	
//	    	Node workflowNode = workflowDefinitionNode.getChildNodes().item(0);
//	    	
//	    	System.out.println(nodes.item(i).getAttributes().getNamedItem("description").getTextContent());
//	        //System.out.println(nodes.item(i).getNodeValue()); 
//	    }
//		
		
		
		
		
		
		return workflows;
		
	}

}
