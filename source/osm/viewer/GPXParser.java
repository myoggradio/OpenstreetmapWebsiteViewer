package osm.viewer;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.Parameter;

public class GPXParser 
{
	private boolean istok = false;
	private Document doc = null;
	private String filename = null;
	public void parse(File dir) throws Exception 
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		File[] fileList = dir.listFiles();
		for (int i=0;i<fileList.length;i++)
		{
			File file = fileList[i];
			if (file.isFile())
			{
				istok = false;
				filename = file.getAbsolutePath();
				doc = db.parse(filename);
				NodeList nl = doc.getChildNodes();
				parseNodeList(nl);
				if (istok) System.out.println(filename);
			}
		}
	}
	public void parseNodeList(NodeList nl)
	{
		String lat = "0.0";
		String lon = "0.0";
		for (int i=0;i<nl.getLength();i++)
		{
			Node node = nl.item(i);
			NamedNodeMap nnm = node.getAttributes();
			if (nnm != null)
			{
				for (int j=0;j<nnm.getLength();j++)
				{
					Node node2 = nnm.item(j);
					if (node2.getNodeName().equals("lat"))
					{
						lat = node2.getNodeValue();
					}
					if (node2.getNodeName().equals("lon"))
					{
						lon = node2.getNodeValue();
						double dlat = Double.parseDouble(lat);
						double dlon = Double.parseDouble(lon);
						if (Parameter.latul < dlat && dlat < Parameter.lator)
						{
							if (Parameter.lonul < dlon && dlon < Parameter.lonor)
							{
								istok = true;
							}
						}
					}
				}
			}
			NodeList nl2 = node.getChildNodes();
			if (nl2 != null) parseNodeList(nl2);
		}
	}
}
