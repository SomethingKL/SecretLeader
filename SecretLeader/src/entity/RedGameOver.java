package entity;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import framework.TCPClient;
import ui.SLPanel;

public class RedGameOver{
	/**reads relevant game information*/
	private TCPClient client = new TCPClient();
	/**box around the list*/
	private Rectangle box;
	/**final width of the frame*/
	private static final int WIDTH  = 1180;
	/**final height of the frame*/
	private static final int HEIGHT = 825;
	/**image for this part*/
	private Image image;
	/**stores the complete player role string for game over*/
	private String complete = "";
	/**stores read in from roles.txt*/
	private String[] roles;
	
	public RedGameOver(Point point, Image image) throws IOException{
		box = new Rectangle(point.x, point.y,WIDTH, HEIGHT);
		this.image = image.getScaledInstance(WIDTH, HEIGHT, 0);
		}
	public void draw(Graphics2D g2d, SLPanel panel){


		g2d.drawImage(this.image, box.x, box.y, panel);

		roles = client.readFile("data/Roles.txt");
		int blueY = 440;
		int redY = 440;
		for(int i = 0; i < roles.length; i++){
			if(i%2 == 0){
				g2d.setColor(Color.BLUE);
				g2d.drawString(roles[i], 250, blueY);
				blueY += 30;
			}
			if(i%2 == 1){
				g2d.setColor(Color.RED);
				g2d.drawString(roles[i], 650, redY);
				redY += 30;
			}
		}
	}
}