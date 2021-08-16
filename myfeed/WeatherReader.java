package myfeed;

import myfeed.WeatherLog;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

import java.net.URL;
import java.net.MalformedURLException;
import java.io.InputStream;
import java.io.IOException;



public class WeatherReader{

	private URL url;

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
				//System.out.println(localPart);			
				switch(localPart){
					case "item":
						event = eventReader.nextEvent();
						break;
					case "description":
						if (lookingForData){
							event = eventReader.nextEvent();
							if(event instanceof Characters){ 
								//System.out.println(event.asCharacters().getData());
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

		//System.out.println("\n");
		//for(int i = 0; i<4;i++)System.out.println(forecast.getWeather()[i]);
	}

	public WeatherLog getWeather(){
		try{readWeather();}
		catch(Exception e){}
		return forecast;
	}

	public WeatherReader(){
		try{
			url = new URL("http://informer.gismeteo.ru/rss/27347.xml");
		}
		catch(MalformedURLException e){}
		forecast = new WeatherLog();
	}

}