package myfeed;

import myfeed.NewsLog;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

import java.net.URL;
import java.net.MalformedURLException;
import java.io.InputStream;
import java.io.IOException;



public class NewsReader{

	private final String HEADER = "DEFAULT HEADER";
	private URL url;
	
	NewsLog[] news;

	private void readNews() throws MalformedURLException, IOException, XMLStreamException{
		int count = 5;
		boolean lookingForData = false;


		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		InputStream in;
		XMLEventReader eventReader;
		in = url.openStream();
		eventReader= inputFactory.createXMLEventReader(in);
		
		
		while ((eventReader.hasNext()) && (count>0)){
			XMLEvent event = eventReader.nextEvent();
			if (event.isStartElement()) {
				String localPart = event.asStartElement().getName().getLocalPart();
				//System.out.println("\\" + localPart);
				
				
				switch(localPart){
					
					case "item":
						if (!lookingForData) lookingForData = true;	
						event = eventReader.nextEvent();
						break;
					case "title":
						if (lookingForData){
							event = eventReader.nextEvent();
							news[5-count] = new NewsLog();
							if(event instanceof Characters) news[5-count].setHeader(event.asCharacters().getData());

							//System.out.println(header);
							
						}
						break;
					case "link":
						if (lookingForData){
							lookingForData = false;
							event = eventReader.nextEvent();
							news[5-count--].setLink(new URL(event.asCharacters().getData()));
							//System.out.println(event.asCharacters().getData());
							
							break;
						}
				}
			}
		 else {continue;}
		}

	}

	public NewsLog[] getNews(){
		try{readNews();}
		catch(Exception e){}
		return news;
	}
	public NewsReader(){
		try{url = new URL("https://meduza.io/rss2/all");}
		//try{url = new URL("https://www.rt.com/rss/russia/");}
		catch(MalformedURLException e){}
		news = new NewsLog[5];
	}

}