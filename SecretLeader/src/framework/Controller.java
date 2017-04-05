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

import entity.*;
import ui.SLPanel;

public class Controller{
	/**reads relevant game information*/
	private TCPClient client = new TCPClient();
	/**holds the player's team*/
	private PlayerCard role;
	/**holds the list of players in the game*/
	private PlayerList players;
	/**holds the player's game name*/
	private String playerID;
	/**holds the blue team information*/
	private Board blueBoard;
	/**holds the red team information*/
	private Board redBoard;

	public Controller() throws IOException{
		role = new PlayerCard(new Point(0,0), "BlueCard.jpg");
		players = new PlayerList(new Point(5,305));
		playerID = "Ross";
		blueBoard = new Board(new Point(200,0), "Blue.jpg");
		redBoard = new Board(new Point(200,300), "Red.jpg");
	}
	/**{@literal}draws all the current game images
	 */
	public void draw(Graphics2D g2d, SLPanel slPanel) throws IOException{
		updateBoard(g2d, slPanel);
		
		//display the player card
		role.draw(g2d, slPanel);
		//display the playerList
		players.draw(g2d, slPanel);
		
		displayOfficialPosition(g2d, slPanel);
	}
	/**{@literal}draws the board as it currently is
	 */
	private void updateBoard(Graphics2D g2d, SLPanel slPanel) throws IOException{
		//get the current scores
		String[] scores = client.readFile("data/Board.txt");
		int numBlueVictories = Integer.parseInt(scores[0]);
		int numRedVictories = Integer.parseInt(scores[1]);
		
		//display the blue board
		blueBoard.blue(numBlueVictories);;
		blueBoard.draw(g2d, slPanel);
		//display the red board
		redBoard.red(numRedVictories);;
		redBoard.draw(g2d, slPanel);
	}
	/**{@literal}draws the chancellor or president card if you are either
	 */
	private void displayOfficialPosition(Graphics2D g2d, SLPanel slPanel) throws IOException{
		//get the current turn information
		String[] turnInfo = client.readFile("data/Turn.txt");
		Official position;
		//check if you are the president
		if(turnInfo[0].compareTo(playerID)==0){
			position = new Official(new Point(0,600), "President.jpg");
			position.draw(g2d, slPanel);
		}
		//check if you are the chancellor
		else if(turnInfo[1].compareTo(playerID)==0){
			position = new Official(new Point(0,600), "Chancellor.jpg");
			position.draw(g2d, slPanel);
		}
	}
}