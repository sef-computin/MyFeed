package myfeed;
import java.net.URL;
import java.net.*;

public class WeatherLog{

	String[] forecast;

	public String[] getWeather(){
		return forecast;
	}

	public void setWeather(String[] forecast){
		for(int i = 0; i<4;i++){
			this.forecast[i] = forecast[i];
		}
	}
	
	public WeatherLog(){
		forecast = new String[4];
	}

}