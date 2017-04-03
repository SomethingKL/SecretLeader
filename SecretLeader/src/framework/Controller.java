/**This is our secret leader controller.
 * SLPanel decides when to do a task, like read from a central file,
 * 		but this class performs the actual work.
 * Basically this class knows what to do when its told to do work.
 * The main point of this is to reduce the work of our panel.
 */
package framework;

import java.awt.Graphics2D;
import java.awt.Point;
import java.io.IOException;

import entity.Board;
import ui.SLPanel;

public class Controller{
	TCPClient client = new TCPClient();

	public Controller(){
		
	}
	public void draw(Graphics2D g2d, SLPanel slPanel) throws IOException{
		String[] scores = client.readFile("data/Board.txt");
		int blue = Integer.parseInt(scores[0]);
		int red = Integer.parseInt(scores[1]);
		Board blueBoard = new Board(new Point(0,0), "Blue.jpg", blue);
		blueBoard.draw(g2d, slPanel);
		Board redBoard = new Board(new Point(0,300), "Red.jpg", red);
		redBoard.draw(g2d, slPanel);
	}
}