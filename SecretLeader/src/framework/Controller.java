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
import entity.PlayerCard;
import entity.PlayerList;
import ui.SLPanel;

public class Controller{
	private TCPClient client = new TCPClient();
	private PlayerCard role;
	private PlayerList players;

	public Controller() throws IOException{
		role = new PlayerCard(new Point(0,0), "BlueCard.jpg");
		players = new PlayerList(new Point(5,305));
	}
	public void draw(Graphics2D g2d, SLPanel slPanel) throws IOException{
		//get the current scores
		String[] scores = client.readFile("data/Board.txt");
		int numBlueVictories = Integer.parseInt(scores[0]);
		int numRedVictories = Integer.parseInt(scores[1]);
		
		//display the blue board
		Board blueBoard = new Board(new Point(200,0), "Blue.jpg", numBlueVictories);
		blueBoard.draw(g2d, slPanel);
		//display the red board
		Board redBoard = new Board(new Point(200,300), "Red.jpg", numRedVictories);
		redBoard.draw(g2d, slPanel);
		
		//display the player card
		role.draw(g2d, slPanel);
		//display the playerList
		players.draw(g2d, slPanel);
	}
}