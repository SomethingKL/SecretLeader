/**Anything that has an image and is drawn is an entity
 */
package entity;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import ui.SLPanel;

public class Entity {
	/**box around the entity*/
	private Rectangle box;
	/**image for this part*/
	private Image image;
	
	/**final width of the frame*/
	private static final int WIDTH  = 540;
	/**final height of the frame*/
	private static final int HEIGHT = 610;
	
	
	public Entity(Point location, Dimension size, Image image){
		
		box = new Rectangle(location.x, location.y, size.width, size.height);
		this.image = image.getScaledInstance(size.width, size.height, 0);
	}
	
	/**{@literal} draws the image on the graphics panel
	 */
	public void draw(Graphics2D g2d, SLPanel panel){
		g2d.drawImage(this.image, box.x, box.y, panel);
	}
	
	/**@return x gets location of the top left corner*/
	public int getX(){
		return box.x;
	}
	/**@return y gets location of the top left corner*/
	public int getY(){
		return box.y;
	}
	
	/**
	 * Replaces the image inside of the box
	 * @param image
	 */
	public void resetImage(Image image){
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		this.image = image.getScaledInstance(width,height,0);
	}
	
}