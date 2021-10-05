package MyFeed;

import javax.swing.*;
import java.awt.Image;
import java.awt.BorderLayout;
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

	private static MyFeedExplorer explorer;
	static JFrame frame;


	//private WeatherLog forecast;



	public static void main(String args[]) 
	{
		explorer = new MyFeedExplorer();
		
		explorer.startApp();

	}

	public void startApp(){
		Thread newsThread = new Thread(this.newsReader);
		Thread weatherThread = new Thread(this.weatherReader);
		newsThread.start();
		weatherThread.start();
		try{
			newsThread.join();
			weatherThread.join();
		} catch(InterruptedException e){e.printStackTrace();} 

		this.createAndShowGUI();
	}


	private void createAndShowGUI(){
		frame = new JFrame("MyFeed");
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(BLACK);


		JPanel newsPanel = new JPanel();
		newsPanel.setOpaque(true);
		newsPanel.setBackground(BLACK);
		newsPanel.setSize(780, 350);

		JPanel weatherPanel = new JPanel();
		weatherPanel.setOpaque(true);
		weatherPanel.setBackground(BLACK);
		weatherPanel.setSize(780, 260);

		JLabel newsHead = new JLabel("Новости");
			
		newsHead.setForeground(WHITE);
		newsHead.setHorizontalAlignment(SwingConstants.CENTER);
		newsHead.setPreferredSize(new Dimension(120, 30));
		newsHead.setVisible(true);

		JLabel weatherHead = new JLabel("Погода Москва (Ночь, Утро, День, Вечер)");
		weatherHead.setForeground(WHITE);
		weatherHead.setHorizontalAlignment(SwingConstants.LEFT);
		weatherHead.setPreferredSize(new Dimension(750,30));
		weatherHead.setVisible(true);
		

		newsPanel.add(newsHead);
		weatherPanel.add(weatherHead);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(true);
		buttonPanel.setBackground(BLACK);
		buttonPanel.setSize(400, 60);

		JLabel restartButton = new JLabel("Обновить");
		restartButton.setOpaque(true);
		restartButton.setHorizontalAlignment(SwingConstants.CENTER);
		
		restartButton.setBackground(GRAY);
		restartButton.setForeground(CYAN);
		restartButton.addMouseListener(new MouseBtn());
		restartButton.setPreferredSize(new Dimension(200, 40));
		restartButton.setVisible(true);
		
        buttonPanel.add(restartButton);

		for(int i = 0; i<5;i++){
			
			logs[i].setOpaque(true);
			logs[i].setHorizontalAlignment(SwingConstants.CENTER);
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
		buttonPanel.setVisible(true);
		



		frame.getContentPane().add(newsPanel);
		frame.getContentPane().add(weatherPanel);
		frame.getContentPane().add(buttonPanel);

		newsPanel.setLocation(10,10);
		weatherPanel.setLocation(10, 355);
		buttonPanel.setLocation(200, 620);


		
		
		frame.pack();
		frame.setLocation(100,100);
		frame.setSize(800,710);
		

		Image logo = new ImageIcon(MyFeedExplorer.class.getResource("rss.png")).getImage();
		frame.setIconImage(logo);


		frame.setVisible(true);
		
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
	private class MouseBtn extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e){
			frame.dispose();
			explorer.startApp();
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
