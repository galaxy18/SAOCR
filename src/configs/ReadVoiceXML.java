package configs;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import objects.VoiceInfo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ReadVoiceXML implements Params{
	DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
	
	private Hashtable<String, String> ht_params;
	private Hashtable<String, VoiceInfo> ht_ID2battle;
	private Hashtable<String, VoiceInfo> ht_ID2common;
	private Hashtable<String, VoiceInfo> ht_ID2normal;
	private Hashtable<String, VoiceInfo> ht_ID2other;
	
//	private Vector<String> battleActionID = new Vector<String>();
//	private Vector<String> commonActionID = new Vector<String>();
	private String name = "kirito";
	
	private static ReadVoiceXML instance;
	
	public ReadVoiceXML(String arg_name){
		name = arg_name;
		ht_params = new Hashtable<String, String>();
		ht_ID2battle = new Hashtable<String, VoiceInfo>();
		ht_ID2common = new Hashtable<String, VoiceInfo>();
		ht_ID2normal = new Hashtable<String, VoiceInfo>();
		ht_ID2other = new Hashtable<String, VoiceInfo>();
		instance = this;
	}
	
	public Document parse(String filePath) { 
		Document document = null; 
		try { 
			//DOM parser instance 
			DocumentBuilder builder = builderFactory.newDocumentBuilder(); 
			//parse an XML file into a DOM tree 
			File xmlFile = new File(filePath);
			if (xmlFile.exists())
				document = builder.parse(new File(filePath));
			else
				return null;
		} catch (ParserConfigurationException e) { 
			e.printStackTrace();  
		} catch (SAXException e) { 
			e.printStackTrace(); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		} 
		return document; 
	} 

	public void readFile() { 
//		DOMParser parser = new DOMParser(); 
		Document document = parse(voice_root + name + voice_conf); 
		if (document == null)
			return;
		//get root element 
		Element rootElement = document.getDocumentElement(); 
		//traverse child elements 
		NodeList nodes = rootElement.getChildNodes(); 
		
		readParams(nodes, null);
	}
	
	private void readParams(NodeList nodes, String name){
		for (int j = 0; j < nodes.getLength(); j++){
			Node node = nodes.item(j);
			if (node.getNodeType() == Node.CDATA_SECTION_NODE){
				ht_params.put(name, node.getNodeValue());
//				System.out.println("Name " + name + " value " + node.getNodeValue());
			}
			else if (node.hasChildNodes()){
				NodeList elements = node.getChildNodes();
				if (node.getNodeName().equals(Params.LIST_BATTLE_Action)){
					readInfo(elements, LIST_BATTLE_Action, null, null);
				}
				else if (node.getNodeName().equals(Params.LIST_COMMON_Action)){
					readInfo(elements, LIST_COMMON_Action, null, null);
				}
				else if (node.getNodeName().equals(Params.LIST_NORMAL_Action)){
					readInfo(elements, LIST_NORMAL_Action, null, null);
				}
				else if (node.getNodeName().equals(Params.LIST_OTHER)){
					readInfo(elements, LIST_OTHER, null, null);
				}
				else{
					readParams(elements, node.getNodeName());
				}
			}
			else if (node.getNodeType() != Node.TEXT_NODE){
				ht_params.put(node.getNodeName(), "");
			}
		}
	}
	
	private void readInfo(NodeList nodes, String folder, VoiceInfo infoObj, String name){
		for (int j = 0; j < nodes.getLength(); j++){
			Node node = nodes.item(j);
			if (node.getNodeType() == Node.CDATA_SECTION_NODE){
				if (infoObj != null && name != null){
					infoObj.setValue(name, node.getNodeValue());
					if (folder.equals(LIST_BATTLE_Action)){
						if (name.equals(Params.FLAG_BATTLE_ACTION_ID)){
							ht_ID2battle.put(node.getNodeValue(), infoObj);
//							battleActionID.add(node.getNodeValue());
						}
					}
					else if (folder.equals(LIST_COMMON_Action)){
						if (name.equals(Params.FLAG_COMMON_ACTION_ID)){
							ht_ID2common.put(node.getNodeValue(), infoObj);
//							commonActionID.add(node.getNodeValue());
						}
					}
					else if (folder.equals(LIST_NORMAL_Action)){
						if (name.equals(Params.FLAG_NORMAL_ACTION_ID)){
							ht_ID2normal.put(node.getNodeValue(), infoObj);
						}
					}
					else{
						if (name.equals(Params.FLAG_OTHER_ID)){
							ht_ID2other.put(node.getNodeValue(), infoObj);
						}
					}
				}
//				System.out.println("value " + node.getNodeValue());
			}
			else if (node.hasChildNodes()){
//				System.out.println("Name "+ node.getNodeName());
				NodeList elements = node.getChildNodes();
				if (node.getNodeName().equals(INFO_BATTLE_Action) ||
						node.getNodeName().equals(INFO_COMMON_Action) ||
						node.getNodeName().equals(INFO_NORMAL_Action) ||
						node.getNodeName().equals(INFO_OTHER)
						){
					VoiceInfo newinfoobj = new VoiceInfo();
					readInfo(elements, folder, newinfoobj, node.getNodeName());
				}
				else{
					readInfo(elements, folder, infoObj, node.getNodeName());
				}
			}
			else if (node.getNodeType() != Node.TEXT_NODE){
				if (infoObj != null){
					infoObj.setValue(node.getNodeName(), "");
				}
//				System.out.println("Emptynode "+ node.getNodeName());
			}
			else{
//				System.out.println("Emptynode "+ node.getNodeName());
			}
		}
	}
	
//	public InfoObject getCardByID(String ID){
//		return ht_ID2card.get(ID);
//	}
	public VoiceInfo getBattleActionByID(String ID){
		return ht_ID2battle.get(ID);
	}
	public VoiceInfo getCommonActionByID(String ID){
		return ht_ID2common.get(ID);
	}
	public VoiceInfo getNormalActionByID(String ID){
		if (ht_ID2normal != null)
			return ht_ID2normal.get(ID);
		return null;
	}
	public VoiceInfo getOtherActionByID(String ID){
		if (ht_ID2other != null)
			return ht_ID2other.get(ID);
		return null;
	}
	public Hashtable<String, String> getParams(){
		return ht_params;
	}
	public String getParamByID(String ID){
		return ht_params.get(ID);
	}
	public static ReadVoiceXML getInstance(){
		return instance;
	}
}
