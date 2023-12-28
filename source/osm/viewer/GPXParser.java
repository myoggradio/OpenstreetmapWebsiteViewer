package osm.viewer;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.Parameter;
import core.Protokol;

public class GPXParser 
{
	private boolean istok = false;
	private Document doc = null;
	private String filename = null;
	public void parse(File dir) throws Exception 
	{
		Protokol.write("GPXParser:parse:start:" + dir.getAbsolutePath());
		Protokol.write("GPXParser:parse:unten links:" + Parameter.latul + ":" + Parameter.lonul);
		Protokol.write("GPXParser:parse:oben rechts:" + Parameter.lator + ":" + Parameter.lonor);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		File[] fileList = dir.listFiles();
		for (int i=0;i<fileList.length;i++)
		{
			File file = fileList[i];
			if (file != null)
			{				if (file.isFile())
				{
					istok = false;
					filename = file.getAbsolutePath();
					try
					{
						doc = db.parse(filename);
						NodeList nl = doc.getChildNodes();
						parseNodeList(nl);
					}
					catch (Exception e)
					{
						Protokol.write("GPXParser:parse:Exception:");
						Protokol.write(e.toString());
					}
					if (istok) Protokol.write(filename);
				}
			}
		}
		Protokol.write("GPXParser:parse:ende:" + dir.getAbsolutePath());
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
