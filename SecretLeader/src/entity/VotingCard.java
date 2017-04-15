package entity;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class VotingCard extends Entity{
	/**final width of the frame*/
	private static final int WIDTH  = 240;
	/**final height of the frame*/
	private static final int HEIGHT = 145;
	//where the image is at
	private Rectangle box;
	
	public VotingCard(Point point, String name) throws IOException{
		super(point, new Dimension(WIDTH, HEIGHT), ImageIO.read(new File("data/"+name)));
		box = new Rectangle(point.x, point.y, WIDTH, HEIGHT);
	}
	
	public void click(MouseEvent e) {
		//this is the border of things that can be clicked
		if(box.contains(e.getPoint())){
			System.out.println("Voting Card!");
		}
	}
}
