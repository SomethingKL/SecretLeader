
package entity;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import framework.TCPClient;

/**This is the card for each player role. Blue team and red team
 * It shows the players current role in the game.
 */
public class PlayerCard extends Entity{
	/**final width of the frame*/
	private static final int WIDTH  = 200;
	/**final height of the frame*/
	private static final int HEIGHT = 300;
	
	/**
	 * @param point, spot on the board
	 * @param name, name of the file
	 * @throws IOException, file does not exist
	 */
	public PlayerCard(Point point, String name) throws IOException{
		super(point, new Dimension(WIDTH, HEIGHT), ImageIO.read(new File("data/"+name)));
	}
}