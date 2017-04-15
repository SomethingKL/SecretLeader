/**This is our secret leader controller.
 * SLPanel decides when to do a task, like read from a central file,
 * 		but this class performs the actual work.
 * Basically this class knows what to do when its told to do work.
 * The main point of this is to reduce the work of our panel.
 */
package framework;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JOptionPane;

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

	public Controller(String userNameIn) throws IOException{
		firstPresident();
		String tmpRole = getRole(userNameIn);
		playerID = userNameIn;
		role = new PlayerCard(new Point(0,0), tmpRole + "Card.jpg");
		players = new PlayerList(new Point(5,305));
		blueBoard = new Board(new Point(200,0), "Blue.jpg");
		redBoard = new Board(new Point(200,300), "Red.jpg");
		firstPresident();
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
		if(turnInfo[0].equals(playerID)){
			position = new Official(new Point(0,600), "President.jpg");
			position.draw(g2d, slPanel);
		}
		//check if you are the chancellor

		else if(turnInfo[1].equals(playerID)){
			position = new Official(new Point(0,600), "Chancellor.jpg");
			position.draw(g2d, slPanel);
		}
	}
	
	public void click(MouseEvent e) {
		players.click(e);
	}
	
	/**
	 * 
	 * @param userName, the name of the player
	 * @return Red or Blue, to set the player card
	 */
	public String getRole(String userName){
		int length = client.getLength("data/Roles.txt");
		String[] scores = new String[length];
		scores = client.readFile("data/Roles.txt");
		String tmpS= scores[0];
		for(int i = 0; i <length; i++){
			if(scores[i].startsWith(userName)){
				tmpS = scores[i];
				String [] tmpA = tmpS.split(" ");
				tmpS = tmpA[1];
				System.out.println(tmpS);
				
				//display that this character is the secret leader.
				if(tmpS.equals("Secret")){
					//JOptionPane.showMessageDialog(null, "You are the Secret Leader for the red team! Shhh!");
					tmpS = "Red";
				}
				
				//display who the red teams are and who the secret leader is to the red team
				//red team is odd numbers (starting from 0), secret leader is always #1
				else if(tmpS.equals("Red")){
					/*
					String redString = "";
					redString +="is the Secret Leader";
					for(int j = 1; j < length; j++){
						
					}*/
					//JOptionPane.showMessageDialog(null, "You are on the Red Team! Your teammates are");
				}
			}
		}
		return tmpS;
	}
	
	/**
	 * Sets up the first president in the "Turn.txt" file. Does not set chancellor
	 */
	public void firstPresident(){
		int length = client.getLength("data/Players.txt");
		String[] scores = new String[length];
		scores = client.readFile("data/Players.txt");
		
		String firstPres = scores[3];
		client.openToWrite("data/Turn.txt");
		client.writeToFile("#name of the President");
		client.writeToFile(firstPres);
		client.writeToFile("#name of the Chancellor");
		//no chancellor to start the game so random characters are selected
		client.writeToFile("&&&&&&&&&&$");
		client.close();
		
	}
	
}