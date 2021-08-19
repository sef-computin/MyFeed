package MyFeed;

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



import java.awt.Dimension;

public class MyFeedExplorer{
	final Color BLACK = new Color(15, 15, 15);
	final Color CYAN = new Color(0,173,238);
	final Color GRAY = new Color(30,30,30);
	final Color WHITE = new Color(250,250,250);

	private final int SIDE = 640;
	private NewsReader newsReader;
	private WeatherReader weatherReader;

	public boolean isConnected = false;
	

	public JLabel[] logs;
	public JLabel[] wlogs;
	public NewsLog[] news;
	//private WeatherLog forecast;



	public static void main(String args[]) 
	{
		MyFeedExplorer explorer = new MyFeedExplorer();
		
		Thread newsThread = new Thread(explorer.newsReader);
		Thread weatherThread = new Thread(explorer.weatherReader);
		newsThread.start();
		weatherThread.start();
		try{
			newsThread.join();
			weatherThread.join();
		} catch(InterruptedException e){e.printStackTrace();} 

		explorer.createAndShowGUI();

	}


	private void createAndShowGUI(){
		JFrame frame = new JFrame("MyFeed");
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(BLACK);


		JPanel newsPanel = new JPanel();
		newsPanel.setOpaque(true);
		newsPanel.setBackground(BLACK);
		newsPanel.setSize(780, 350);

		JPanel weatherPanel = new JPanel();
		weatherPanel.setOpaque(true);
		weatherPanel.setBackground(BLACK);
		weatherPanel.setSize(780, 300);

		JLabel newsHead = new JLabel("News Feed");
			
		newsHead.setForeground(WHITE);
		newsHead.setHorizontalAlignment(SwingConstants.CENTER);
		newsHead.setPreferredSize(new Dimension(120, 30));
		newsHead.setVisible(true);

		JLabel weatherHead = new JLabel("Moscow Weather (Night, Morning, Day, Evening)");
		weatherHead.setForeground(WHITE);
		weatherHead.setHorizontalAlignment(SwingConstants.LEFT);
		weatherHead.setPreferredSize(new Dimension(750,30));
		weatherHead.setVisible(true);
		

		newsPanel.add(newsHead);
		weatherPanel.add(weatherHead);

		//logs = new JLabel[5];
		for(int i = 0; i<5;i++){
			//logs[i] = new JLabel("...");

			
			logs[i].setOpaque(true);
			logs[i].setBackground(GRAY);
			logs[i].setForeground(CYAN);
			
			if(isConnected) logs[i].addMouseListener(new Mouse(news[i].getLink()));

			logs[i].setPreferredSize(new Dimension(780, 50));
			logs[i].setVisible(true);

			newsPanel.add(logs[i]);
		}

		for(int i = 0; i<4;i++){
			wlogs[i].setOpaque(true);
			wlogs[i].setBackground(GRAY);
			wlogs[i].setForeground(WHITE);

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


		Image logo = new ImageIcon(MyFeedExplorer.class.getResource("rss.png")).getImage();
		frame.setIconImage(logo);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
	}

	private MyFeedExplorer(){
		newsReader = new NewsReader(this);
		weatherReader = new WeatherReader(this);
		//forecast = new WeatherLog();
		logs = new JLabel[5];
		wlogs = new JLabel[4];
		news = new NewsLog[5];		
	}

	//private class NewsThread implements Runnable{
	//	newsReader = new NewsReader();
	//}
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
        	e.getComponent().setBackground(CYAN);
        	e.getComponent().setForeground(BLACK);
    	}
    	@Override
   	 	public void mouseExited(MouseEvent e) {
        	e.getComponent().setBackground(GRAY);
        	e.getComponent().setForeground(CYAN);
    	}
	}

}

