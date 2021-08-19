package MyFeed;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

import java.net.URL;
import java.net.MalformedURLException;
import java.io.InputStream;
import java.io.IOException;

import javax.swing.JLabel;



public class NewsReader implements Runnable{

	private MyFeedExplorer explorer;

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
						}
						break;
					case "link":
						if (lookingForData){
							lookingForData = false;
							event = eventReader.nextEvent();
							news[5-count--].setLink(new URL(event.asCharacters().getData()));
							break;
						}
				}
			}
		 else {continue;}
		}

	}

	public NewsLog[] getNews() throws Exception{
		readNews();
		
		return news;
	}

	public void run(){
		boolean connect = false;
		try{
			news = getNews();
			connect = true;
		}
		catch(Exception e){
			connect = false;
		}

		//System.out.println("Новости собраны");
		
		synchronized(explorer){
			if (connect){
				for(int i=0; i<5;i++){
					explorer.logs[i] = new JLabel(news[i].getHeader());
				}
				explorer.isConnected = true;
				explorer.news = news;
			}
			else{
				for(int i =0; i<5;i++){
				explorer.logs[i] = new JLabel("<html>No Connection.<br>Please try again</html>");
				explorer.isConnected = false;
			}	
			}
		}
		//System.out.println("NewsReader закончен");
	}

	public NewsReader(MyFeedExplorer explorer){
		try{url = new URL("https://meduza.io/rss2/all");}
		catch(MalformedURLException e){}
		this.explorer = explorer;
		news = new NewsLog[5];
		//System.out.println("NewsReader создался");
	}

}
