package com.txl.plugin.xmlutils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AndroidDimenXMLParser {
	private static final String TAG  = "AndroidDimenXMLParser";
	public static Map<String,String> readDimensXML(String path)
	{
		HashMap<String, String> map=new HashMap<String, String>();
		Document AndroidManifestDoc=null;
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance(); 
		DocumentBuilder builder=null;
		try
		{ 
			builder=factory.newDocumentBuilder(); 
			AndroidManifestDoc=builder.parse(new File(path));
			NodeList dimenNode=AndroidManifestDoc.getElementsByTagName("dimen");
			for(int i=0;i<dimenNode.getLength();i++)
			{
				Node node=dimenNode.item(i);
				Node androidNameNode=node.getAttributes().getNamedItem("name");

				String key=androidNameNode.getNodeValue();
				String value=node.getFirstChild().getNodeValue();
				System.out.println(TAG+": key :"+key +"value : "+value+" node "+node.getFirstChild().getNodeValue());
				map.put(key, value); 
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return map;
	}

	public static void saveXML(String path,Document doc)
	{
		try 
		{
			Source source = new DOMSource(doc);  
			ByteArrayOutputStream bos = new ByteArrayOutputStream();    
			StreamResult result = new StreamResult(bos);  
			TransformerFactory factory = TransformerFactory.newInstance();  
			Transformer transformer = factory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");  
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");  
			transformer.transform(source, result);  
			File file=FileUtil.createFile(path);
			FileWriter fileWriter=new FileWriter(file);
			String value=bos.toString("UTF-8");
			fileWriter.write(value);
			fileWriter.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}


	public static void saveMap2XML(Map<String,String> map,String path,float designWidth,float targetWidth,float scale) {
//		float scale = targetWidth/designWidth;
		Iterator<Entry<String, String>> iterator=map.entrySet().iterator();
		StringBuilder builder=new StringBuilder();
		builder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\r<resources>\r");
		try {
			while(iterator.hasNext())
			{
				Entry<String, String> entryA=iterator.next();
				String key=entryA.getKey();
				String value=entryA.getValue();
				String end = value.substring(value.length()-2);
				String start = value.substring(0,value.length()-2);
				value = (Float.parseFloat(start) * scale)+end;
				builder.append("		<dimen name=\""+key+"\">"+value+"</dimen>\r");
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		builder.append("</resources>");
		try 
		{
			FileWriter fileWriter=new FileWriter(path);
			fileWriter.write(builder.toString());
			fileWriter.flush();
			fileWriter.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}


	}
}
