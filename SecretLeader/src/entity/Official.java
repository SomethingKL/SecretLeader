
package entity;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**This is our secret leader official card.
 * It represents whether the player is president or chancellor.
 */
public class Official extends Entity{
	/**final width of the frame*/
	private static final int WIDTH  = 550;
	/**final height of the frame*/
	private static final int HEIGHT = 160;
	
	/**
	 * @param point, spot on the board
	 * @param name, name of the file
	 * @throws IOException, file does not exist
	 */
	public Official(Point point, String name) throws IOException{
		super(point, new Dimension(WIDTH, HEIGHT), ImageIO.read(new File("data/"+name)));
	}
}
