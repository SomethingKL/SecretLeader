
package entity;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import ui.SLPanel;

/**Anything that has an image and is drawn is an entity. 
 * Most classes inside of the package extend this class
 */
public class Entity{
	/**box around the entity*/
	protected Rectangle box;
	/**image for this part*/
	private Image image;
	/**final width of the frame*/
	private static final int WIDTH  = 540;
	/**final height of the frame*/
	private static final int HEIGHT = 610;
	/**the size of the image**/
	private Dimension dim;
	
	/**
	 * @param location,the spot on the board
	 * @param size, the size of the picture
	 * @param image, the image to go on the screen
	 */
	public Entity(Point location, Dimension size, Image image){
		dim = size;
		box = new Rectangle(location.x, location.y, size.width, size.height);
		this.image = image.getScaledInstance(size.width, size.height, 0);
	}
	
	/**{@literal} draws the image on the graphics panel
	 */
	public void draw(Graphics2D g2d, SLPanel panel){
		g2d.drawImage(this.image, box.x, box.y, panel);
	}
	
	/**
	 * Draws the image on the graphics panel with opacity optional
	 * @param g2d
	 * @param panel
	 * @param isOpaque
	 */
	public void draw(Graphics2D g2d, SLPanel panel, boolean isOpaque){
		float opacity;
		if(isOpaque){
			opacity = 0.5f;
		} else {
			opacity = 1f;
		}
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
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
		this.image = image.getScaledInstance(dim.width,dim.height,0);
	}
	
}