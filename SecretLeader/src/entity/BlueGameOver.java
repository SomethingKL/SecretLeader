package entity;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import ui.SLPanel;

public class BlueGameOver{
	/**box around the list*/
	private Rectangle box;
	/**final width of the frame*/
	private static final int WIDTH  = 1180;
	/**final height of the frame*/
	private static final int HEIGHT = 825;
	/**image for this part*/
	private Image image;
	
	public BlueGameOver(Point point, Image image) throws IOException{
		box = new Rectangle(point.x, point.y,WIDTH, HEIGHT);
		this.image = image.getScaledInstance(WIDTH, HEIGHT, 0);
		}
	public void draw(Graphics2D g2d, SLPanel panel){
		g2d.drawImage(this.image, box.x, box.y, panel);
	}
}