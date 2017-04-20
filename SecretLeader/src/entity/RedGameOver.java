package entity;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import ui.SLPanel;

public class RedGameOver extends Entity{
	/**final width of the frame*/
	private static final int WIDTH  = 1180;
	/**final height of the frame*/
	private static final int HEIGHT = 825;
	
	public RedGameOver(Point point, String name) throws IOException{
			super(point, new Dimension(WIDTH, HEIGHT), ImageIO.read(new File("data/"+name)));
	}
	
	public void draw(Graphics2D g2d, SLPanel panel){
		super.draw(g2d, panel);
	}
}