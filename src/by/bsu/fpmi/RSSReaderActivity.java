package by.bsu.fpmi;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.MultiTapKeyListener;
import android.widget.EditText;

public class RSSReaderActivity extends Activity {
	static final String TITLE = "title";
	static final String DESCRIPTION = "description";
	static final String CHANNEL = "channel";
	static final String LANGUAGE = "language";
	static final String COPYRIGHT = "copyright";
	static final String LINK = "link";
	static final String AUTHOR = "author";
	static final String ITEM = "item";
	static final String PUB_DATE = "pubDate";
	static final String GUID = "guid";

//	final URL url;
	static final String FILENAME = "download.xml";
	File inputXml;
	
	public RSSReaderActivity(String feedUrl) {
		/*try {
			this.url = new URL(feedUrl);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}*/
		
	}

	@SuppressWarnings("null")
	public String readFeed() {
		Feed feed = new Feed("", "", "", "", "", "");
		try {
			inputXml = new File(FILENAME);

			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder;
				documentBuilder = builderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(inputXml);
			NodeList nodeList = document.getElementsByTagName(ITEM);
			
			for (int i = 0; i < nodeList.getLength(); ++i) {
				Element item = (Element) nodeList.item(i);
				
				FeedMessage feedMessage = new FeedMessage();
				Element titleElement = (Element) item.getElementsByTagName(TITLE).item(0);
				feedMessage.setTitle(titleElement.getFirstChild().getNodeValue());
				
				Element descrElement = (Element) item.getElementsByTagName(DESCRIPTION).item(0);
				feedMessage.setDescription(descrElement.getFirstChild().getNodeValue());
				
				Element linkElement = (Element) item.getElementsByTagName(LINK).item(0);
				feedMessage.setLink(linkElement.getFirstChild().getNodeValue());
				
				feed.getMessages().add(feedMessage);
			}
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return feed.toString();

	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        EditText rss = (EditText) findViewById(R.id.rss);
        rss.setText(readFeed());
    }

	/*private InputStream read() {
		try {
			return url.openStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}*/
}