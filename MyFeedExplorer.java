import javax.swing.*;
import java.awt.Image;
import java.awt.Color;
import java.awt.event.*;
import java.awt.Desktop;
import java.net.URI;

import java.net.URL;
import java.net.MalformedURLException;


import java.net.*;
import java.io.*;


import myfeed.*;
import java.awt.Dimension;

public class MyFeedExplorer{

	private final int SIDE = 640;
	private NewsReader newsReader;
	private WeatherReader weatherReader;

	private boolean isConnected = false;
	

	private JLabel[] logs;
	private JLabel[] wlogs;
	private NewsLog[] news;
	private WeatherLog forecast;



	public static void main(String args[]) 
	{
		MyFeedExplorer explorer = new MyFeedExplorer();
		
		explorer.getNews();
		explorer.getWeather();
		explorer.createAndShowGUI();

	}

	private void createAndShowGUI(){
		
		
		JFrame frame = new JFrame("MyFeed");
		frame.getContentPane().setLayout(null);
	


		JPanel newsPanel = new JPanel();
		newsPanel.setSize(780, 350);

		JPanel weatherPanel = new JPanel();
		weatherPanel.setSize(780, 300);

		JLabel newsHead = new JLabel("News Feed");
		newsHead.setHorizontalAlignment(SwingConstants.CENTER);
		newsHead.setPreferredSize(new Dimension(120, 30));
		newsHead.setVisible(true);

		JLabel weatherHead = new JLabel("Moscow Weather (Night, Morning, Day, Evening)");
		weatherHead.setHorizontalAlignment(SwingConstants.LEFT);
		weatherHead.setPreferredSize(new Dimension(750,30));
		weatherHead.setVisible(true);
		

		newsPanel.add(newsHead);
		weatherPanel.add(weatherHead);

		//logs = new JLabel[5];
		for(int i = 0; i<5;i++){
			//logs[i] = new JLabel("...");

			
			logs[i].setOpaque(true);
			logs[i].setBackground(Color.lightGray);
			
			if(isConnected) logs[i].addMouseListener(new Mouse(news[i].getLink()));

			logs[i].setPreferredSize(new Dimension(780, 50));
			logs[i].setVisible(true);

			newsPanel.add(logs[i]);
		}

		for(int i = 0; i<4;i++){
			wlogs[i].setOpaque(true);
			wlogs[i].setBackground(Color.lightGray);

			wlogs[i].setPreferredSize(new Dimension(700, 50));
			wlogs[i].setVisible(true);	
			weatherPanel.add(wlogs[i]);		
		}

		newsPanel.setVisible(true);
		weatherPanel.setVisible(true);
		



		frame.getContentPane().add(newsPanel);
		frame.getContentPane().add(weatherPanel);
		newsPanel.setLocation(10,10);
		weatherPanel.setLocation(10, 355);
		
		frame.pack();
		frame.setLocation(100,100);
		frame.setSize(800,700);
		
		frame.setVisible(true);


		Image logo = new ImageIcon("rss.png").getImage();
		frame.setIconImage(logo);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
	}

	private void getNews(){
		try{
		news = newsReader.getNews();
		for(int i =0; i<5;i++){
			logs[i] = new JLabel(news[i].getHeader());
			//links[i] = news[i].getURI();
			}
		isConnected = true;
		}
		catch(Exception e){

			for(int i =0; i<5;i++){
			logs[i] = new JLabel("<html>No Connection.<br>Please try again</html>");
		}	
		}
	}
	private void getWeather(){
		try{
		forecast = weatherReader.getWeather();
		
		//System.out.println("\n");
		//for(int i = 0; i<4;i++)System.out.println(forecast.getWeather()[i]);

		for(int i = 0; i<4;i++){
			wlogs[i] = new JLabel(forecast.getWeather()[i]);
		}
		}
		catch (Exception e){
			for(int i = 0; i<4;i++){
			wlogs[i] = new JLabel(forecast.getWeather()[i]);
			}
		}
	}

	private MyFeedExplorer(){
		newsReader = new NewsReader();
		weatherReader = new WeatherReader();
		forecast = new WeatherLog();
		logs = new JLabel[5];
		wlogs = new JLabel[4];
		news = new NewsLog[5];
		getNews();
		getWeather();

	}


	private class Mouse extends MouseAdapter{

		private URI uri;

		public Mouse(String url){
			try{
			this.uri = new URI(url);
		} catch(URISyntaxException error){
			System.out.println(error.toString());
		}
		}
		@Override
		public void mouseClicked(MouseEvent e){
			try{Desktop.getDesktop().browse(uri);}
			catch(IOException error){}
		}
		@Override
   	 	public void mouseEntered(MouseEvent e) {
        	e.getComponent().setBackground(new Color(255, 128, 0, 200));
    	}
    	@Override
   	 	public void mouseExited(MouseEvent e) {
        	e.getComponent().setBackground(Color.lightGray);
    	}
	}

}

