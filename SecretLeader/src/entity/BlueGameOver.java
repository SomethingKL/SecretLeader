package entity;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JLabel;

import framework.TCPClient;
import ui.SLPanel;

/**
 * Responsible for the ending game screen for the blue team.
 */
public class BlueGameOver{
	/**reads relevant game information*/
	private TCPClient client = new TCPClient();
	/**box around the list*/
	private Rectangle box;
	/**final width of the frame*/
	private static final int WIDTH  = 1400;
	/**final height of the frame*/
	private static final int HEIGHT = 825;
	/**image for this part*/
	private Image image;
	/**stores the complete player role string for game over*/
	private String complete = "";
	/**stores read in from roles.txt*/
	private String[] roles;
	
	/**
	 * 
	 * @param point, spot on the board
	 * @param image, the image to go on the screen
	 * @throws IOException, if the image is not found
	 */
	public BlueGameOver(Point point, Image image) throws IOException{
		box = new Rectangle(point.x, point.y,WIDTH, HEIGHT);
		this.image = image.getScaledInstance(WIDTH, HEIGHT, 0);
		}
	
	/** Drawing the contents of the blue game over screen
	 * @param g2d, the graphic
	 * @param panel, the panel of the game
	 */
	public void draw(Graphics2D g2d, SLPanel panel){
		
		// I put a delay in for the game over screen; because why not!?
		/*
		try{
			Thread.sleep(5 *1000); // *5s* = 1s * 5= 1s = 1000 ms 
		}catch(Exception e){
			e.printStackTrace();
		}*/
		g2d.drawImage(this.image, box.x, box.y, panel);
		roles = client.readFile("data/Roles.txt");
		int blueY = 412;
		int redY = 412;
		g2d.setColor(Color.WHITE);
		for(int i = 0; i < roles.length; i++){
			if(i%2 == 0){		
				g2d.drawString(roles[i], 250, blueY);
				blueY += 30;
			}
			if(i%2 == 1){
				g2d.drawString(roles[i], 650, redY);
				redY += 30;
			}
		}	
	}
}