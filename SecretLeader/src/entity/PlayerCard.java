package entity;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PlayerCard extends Entity{
	/**final width of the frame*/
	private static final int WIDTH  = 200;
	/**final height of the frame*/
	private static final int HEIGHT = 300;
	
	public PlayerCard(Point point, String name) throws IOException{
		super(point, new Dimension(WIDTH, HEIGHT), ImageIO.read(new File("data/"+name)));
	}
}