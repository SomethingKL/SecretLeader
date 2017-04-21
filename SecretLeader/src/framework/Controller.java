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
	/**sets to draw blue game over screen*/
	private BlueGameOver BlueGameOver;
	/**sets to draw red game over screen*/
	private RedGameOver RedGameOver;
	
	private PolicySelection PS;

	//this builds all of the possible pieces on the board.
	public Controller(String userNameIn) throws IOException{
		firstPresident();
		//String tmpRole = getRole(userNameIn);
		String tmpRole = getRole(userNameIn);
		playerID = userNameIn;
		role = new PlayerCard(new Point(0,0), tmpRole + "Card.jpg");
		players = new PlayerList(new Point(5,305));
		blueBoard = new Board(new Point(200,0), "Blue.jpg");
		redBoard = new Board(new Point(200,300), "Red.jpg");
		yes = new Votecard(new Point(650,610),ImageIO.read(new File("data/YesCard.PNG")),"yes");
		no = new Votecard(new Point(905,610),ImageIO.read(new File("data/NoCard.PNG")),"no");
		PS = new PolicySelection(new Point(750,620),playerID);
		BlueGameOver = new BlueGameOver(new Point(0,0),ImageIO.read(new File("data/GameOverBackground.png")));
		RedGameOver = new RedGameOver(new Point(0,0),ImageIO.read(new File("data/GameOverBackground.png")));
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
		if(state == GameState.BLUEGAMEOVER){
			BlueGameOver.draw(g2d, slPanel);
		}
		if(state == GameState.BLUEGAMEOVER){
			RedGameOver.draw(g2d, slPanel);
		}
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
		//need the loop to ensure that the file has updated
		String[] turnInfo = client.readFile("data/Turn.txt");
		while(turnInfo.length < 2){
			turnInfo = client.readFile("data/Turn.txt");
		}
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
			
			//makes sure that all of the players have voted
			if(votes == players){
				//if the vote has not passed, this goes to the waiting screen until the 
				//president selects another chancellor
				if(PS.getNoCount() >= players/2){
					
					//need to display that the vote has not passed here somehow
					client.openToWrite("data/state.txt");
					client.writeToFile("WAITING");
					client.close();
				}
				
				//if the vote has passed, this goes to the policy selection screen
				else{
					//need to display that the vote has passed somehow
					//need to set the new chancellor, at this point!
					//so, the file that holds the chancellor being voted on would open and
					
					//get new chancellor here from a file
					String tmpChan = new String("c");
					
					//write the chancellor to the file
					String[] full=	client.readFile("data/Turn.txt");
					client.openToWrite("data/Turn.txt");
					client.writeToFile("#name of the President");
					client.writeToFile(full[0]);
					client.writeToFile("#name of the Chancellor");
					client.writeToFile(tmpChan);
					client.close();
					
					//sets the file for the mode
					System.out.println("Vote has passed!");
					client.openToWrite("data/state.txt");
					client.writeToFile("POLICY");
					client.close();

				}
				
			//this will reset the voting file after the vote has been completed.
			/**
			 * client.openToWrite("data/VotingFile.txt");
			 * client.close();
			 */
				
			}
			//if all players haven't voted yet
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
		client.writeToFile("&&&&&&&&&&&&&&&&&&$");
		client.writeToFile("b");
		client.close();
	}
	
	public String getRole(String userName){
		String[] players = client.readFile("data/Players.txt");
		String [] scores = client.readFile("data/Roles.txt");
		while(players.length != scores.length){
			scores = client.readFile("data/Roles.txt");
		}
		client.close();
		String tmpS= scores[0];
		for(int i = 0; i <scores.length; i++){
			if(players[i].equals(userName)){
				System.out.println(scores[i]);
				tmpS = scores[i];
				String [] tmpA = tmpS.split(" ");
				tmpS = tmpA[1];
						
				if(tmpS.equals(new String("Secret"))){
					tmpS = "Red";
					
				}
			}	

		}
		return tmpS;
	}
	
}