package myfeed;

import javax.swing.*;
import java.awt.image.*;
import java.awt.Dimension;
import java.awt.Graphics;


public class JDisplay extends JComponent{
	private BufferedImage buffer;

	public final int HEIGHT;
	public final int WIDTH;

	public JDisplay(){
		WIDTH = 800;
		HEIGHT = 600;
		buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}
	public JDisplay(int width, int height){
		HEIGHT = height;
		WIDTH = height;
		buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(buffer, 0, 0, WIDTH, HEIGHT, null);
	}
	public void clearImage(){
		for(int x = 0;x<WIDTH;x++){
			for (int y = 0; y<HEIGHT;y++) buffer.setRGB(x,y,0x00000000);
		}
	}
	public void drawPixel(int x, int y, int rgbColor){
		buffer.setRGB(x, y, rgbColor);
	}
	public BufferedImage getImage(){
		return buffer;
	}
}