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


public class WeatherReader implements Runnable{

	private URL url;

	private MyFeedExplorer explorer;

	WeatherLog forecast;

	private void readWeather() throws MalformedURLException, IOException, XMLStreamException{
		int count = 4;
		boolean lookingForData = false;
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		InputStream in;
		XMLEventReader eventReader;


		in = url.openStream();
		eventReader= inputFactory.createXMLEventReader(in);
		String[] data = new String[4];
		while ((eventReader.hasNext()) && (count>0)){
			XMLEvent event = eventReader.nextEvent();
			if (event.isStartElement()) {
				String localPart = event.asStartElement().getName().getLocalPart();	
				switch(localPart){
					case "item":
						event = eventReader.nextEvent();
						break;
					case "description":
						if (lookingForData){
							event = eventReader.nextEvent();
							if(event instanceof Characters){
								data[4-count--] = new String(event.asCharacters().getData());
							}
						}
						else{ lookingForData = true;}
						break;
				}
			}
		 else {continue;}
		}
		forecast.setWeather(data);
	}

	public WeatherLog getWeather() throws Exception{
		readWeather();
		return forecast;
	}

	@Override
	public void run(){
		boolean connect = false;
		try{
			forecast = getWeather();
			connect = true;
		} catch(Exception e){
			connect = false;
		}

		//System.out.println("Погода собрана");

		synchronized(explorer){
			if(connect){
				for(int i = 0; i<4;i++){
					explorer.wlogs[i] = new JLabel(forecast.getWeather()[i]);
				}
			}
			else{
				for(int i = 0; i<4;i++){
					explorer.wlogs[i] = new JLabel("~~~no weZa 4 u~~~");
				}
			}
		}
		//System.out.println("WeatherReader закончен");
	}

	public WeatherReader(MyFeedExplorer explorer){
		try{
			url = new URL("http://informer.gismeteo.ru/rss/27347.xml");
		}
		catch(MalformedURLException e){}
		this.explorer = explorer;
		forecast = new WeatherLog();



		//System.out.println("WeatherReader создался");
	}

}
