package entity;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class VictoryCard extends Entity{
	/**final width of the frame*/
	private static final int WIDTH  = 130;
	/**final height of the frame*/
	private static final int HEIGHT = 180;
	
	public VictoryCard(Point point, String name) throws IOException{
		super(point, new Dimension(WIDTH, HEIGHT), ImageIO.read(new File("data/"+name)));
	}
}