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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import entity.*;
import ui.SLPanel;
import ui.SLPanel.GameState;

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
	// all of the different game states
	private SLPanel.GameState state;
	//the yes card image to be displayed on the screen
	private Votecard yes;
	//the no card image to be displayed on the screen
	private Votecard no;
	//whether the player has voted or not
	private boolean nextScreen;
	
	private PolicySelection PS;

	//this builds all of the possible pieces on the board.
	public Controller(String userNameIn) throws IOException{
		firstPresident();
		String tmpRole = getRole(userNameIn);
		playerID = userNameIn;
		role = new PlayerCard(new Point(0,0), tmpRole + "Card.jpg");
		players = new PlayerList(new Point(5,305));
		blueBoard = new Board(new Point(200,0), "Blue.jpg");
		redBoard = new Board(new Point(200,300), "Red.jpg");
		yes = new Votecard(new Point(650,610),ImageIO.read(new File("data/YesCard.PNG")),"yes");
		no = new Votecard(new Point(905,610),ImageIO.read(new File("data/NoCard.PNG")),"no");
		PS = new PolicySelection(new Point(750,620),playerID);
		nextScreen = false;
		firstPresident();
	}
	/**{@literal}draws all the current game images
	 */
	public void draw(Graphics2D g2d, SLPanel slPanel,SLPanel.GameState state) throws IOException{
		updateBoard(g2d, slPanel,state);
		
		updateSelction(g2d,slPanel,state);
		
		//display the player card
		role.draw(g2d, slPanel);
		//display the playerList
		players.draw(g2d, slPanel);
		//yes.draw(g2d,slPanel);
		displayOfficialPosition(g2d, slPanel);
	}
	private void updateSelction(Graphics2D g2d, SLPanel slPanel, GameState state2) throws IOException {
		state = state2;
		if(state2 == state.VOTING){
			yes.draw(g2d, slPanel);
			no.draw(g2d, slPanel);
			if(yes.isNextState() || no.isNextState()){
				nextScreen = true;
			}
			
			
		}
		else if(state2 == state.POLICY){
			//only display for the president then only for the chancellor
			PS.draw(g2d, slPanel);
			nextScreen = PS.isNextState();
		}
	}
	
	/**{@literal}draws the board as it currently is
	 */
	//want to put the current state of the game right. Ye and ne cards
	private void updateBoard(Graphics2D g2d, SLPanel slPanel,SLPanel.GameState state) throws IOException{
		//get the current scores
		
		//this gets the current scores
		client.close();
		String[] scores = client.readFile("data/Board.txt");
		while(scores.length <2)
		{
			scores = client.readFile("data/Board.txt");
		}
		//the while loop is needed up above so that the file has time to update itself. Otherwise, the numbers won't be read correctly.
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
		else{
			position = new Official(new Point(0,600), "Blank.jpg");
			position.draw(g2d, slPanel);
		}
	}
	
	/**
	 * The click
	 * @param e, the event itself
	 */
	public void click(MouseEvent e) {
		boolean yesBool = yes.click(e,state);
		boolean noBool = no.click(e, state);
		if(yesBool || noBool){
			nextScreen = true;
			int players = client.getLength("data/Players.txt");
			int votes = client.getLength("data/VotingFile.txt");
			System.out.println(players +" " + votes);
			if(votes == players){
				System.out.println("Does it get here");
				client.openToWrite("data/state.txt");
				client.writeToFile("POLICY");
				client.close();
			}
		}
		players.click(e,state);
		PS.click(e,state);
	}
	
	/**
	 * @return votes, whether the player has voted or not
	 */
	public boolean hasVoted(){
		return nextScreen;
	}
	
	public void setHasVoted(boolean voted){
		nextScreen = voted;
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
		client.close();
		String tmpS= scores[0];
		for(int i = 0; i <length; i++){
			if(scores[i].startsWith(userName)){
				tmpS = scores[i];
				String [] tmpA = tmpS.split(" ");
				tmpS = tmpA[1];
				
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
		client.close();
		String[] scores = new String[length];
		scores = client.readFile("data/Players.txt");
		
		//String firstPres = scores[3];
		String firstPres = "a";
		client.openToWrite("data/Turn.txt");
		client.writeToFile("#name of the President");
		client.writeToFile(firstPres);
		client.writeToFile("#name of the Chancellor");
		//no chancellor to start the game so random characters are selected
		client.writeToFile("b");
		client.close();
		
	}
	
}