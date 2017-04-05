/**This is our secret leader official card.
 * It represents whether the player is president or chancellor.
 */
package entity;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Official extends Entity{
	/**final width of the frame*/
	private static final int WIDTH  = 570;
	/**final height of the frame*/
	private static final int HEIGHT = 160;
	
	public Official(Point point, String name) throws IOException{
		super(point, new Dimension(WIDTH, HEIGHT), ImageIO.read(new File("data/"+name)));
	}
}
