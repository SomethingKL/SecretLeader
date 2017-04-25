package entity;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import framework.TCPClient;
import ui.SLPanel;

/**
 * @author Max
 * This runs all of the policy selecting for the president and chancellor.
 */
public class PolicySelection {
	/**box around the list*/
	private Rectangle box;
	//the box around the first card
	private Rectangle card1Box;
	//the box around the second card
	private Rectangle card2Box;
	//the box around the third card
	private Rectangle card3Box;
	/**final width of the frame*/
	private static final int WIDTH  = 100;
	/**final height of the frame*/
	private static final int HEIGHT = 150;
	//where the image is at
	private TCPClient client = new TCPClient();
	//The first card being displayed
	private PolicyCard card1;
	//the second card being displayed
	private PolicyCard card2;
	//the third card being displayed
	private PolicyCard card3;
	//the first card of the chancellors view
	private PolicyCard card4;
	//the second card of the chancellors view
	private PolicyCard card5;
	//the username of the player
	private String playerID;
	//string for the policies
	private String cat;
	//whether the next screen should be chosen
	private boolean nextState = false;
	//the number of cards needed
	private int cardAmount = 3;
	//random integer to decide if a card is red or blue
	private int randInt = 0;
	/** the number of players in the game */
	private int playerInt;

	/**
	 * @param point, spot on the board
	 * @param userName, the name of the current user
	 * @throws IOException, if a file cannot be found
	 */
	public PolicySelection(Point point,String userName) throws IOException{
		box = new Rectangle(point.x, point.y,WIDTH*4, HEIGHT);
		
		//setting up the file
		client.openToWrite("data/leaveStarting.txt");
		client.writeToFile("#Which screen to be on; 1 for the presidents screen; 2 for the chancellors screen");
		client.writeToFile("1");
		client.close();
		//sets the ID of the player
		playerID = userName;
		//need to make these randomized		
		card1 = new PolicyCard(new Point(760, 620),"Blue");
		card2 = new PolicyCard(new Point(900, 620),"Red");
		card3 = new PolicyCard(new Point(1040,620),"Blue");
		//image change: card1.getNewImage("Red");
		resetCards();

		card1Box = new Rectangle(760,620,WIDTH,HEIGHT);
		card2Box = new Rectangle(900,620,WIDTH,HEIGHT);
		card3Box = new Rectangle(1040,620,WIDTH,HEIGHT);
		
		playerInt = client.readFile("data/Players.txt").length;
	}
	
	/**{@literal}
	 * @throws IOException 
	 * Will only put the cards down if the player is the chancellor or the president
	 */
	public void draw(Graphics2D g2d, SLPanel panel) throws IOException{
		String[] turnInfo = client.readFile("data/Turn.txt");
		String[] setPiece = client.readFile("data/leaveStarting.txt");
		while(setPiece.length < 1 && turnInfo.length< 3){
			setPiece = client.readFile("data/leaveStarting.txt");
			turnInfo = client.readFile("data/Turn.txt");
		}
	

		//the first stage of the game, where the president picks his cards
		if(setPiece[0].contains(new String("1")) && turnInfo[0].equals(playerID)){
			card1.draw(g2d,panel, card1.getCardKept());
			card2.draw(g2d, panel, card2.getCardKept());
			card3.draw(g2d, panel,card3.getCardKept());
			//set up the fonts for the graphic

		}
		
		//the second stage of the game, where the chancellor picks his cards
		else if(setPiece[0].contains(new String("2")) && turnInfo[1].equals(playerID)){
			//pick up the information from the file for each of the card
			setPiece = client.readFile("data/VotingScreen.txt");
			card4 = new PolicyCard(new Point(760, 620),setPiece[0]);
			card5 = new PolicyCard(new Point(900, 620),setPiece[1]);
			card4.draw(g2d, panel);
			card5.draw(g2d, panel);
		}
	}
	
	/**
	 * @param e, the mouse clicking the button
	 * @return true or false, whether something happened or not
	 * @throws IOException 
	 */
	public void click(MouseEvent e, SLPanel.GameState state) {
		String[] setPiece = client.readFile("data/leaveStarting.txt");
		String[] roles = client.readFile("data/Turn.txt");
		
		//this is the border of things that can be clicked
		//has to be in the right gamestate, right point and in the right part of the game
		if(box.contains(e.getPoint()) && state == SLPanel.GameState.POLICY && setPiece[0].equals(new String("1"))
				&& roles[0].equals(playerID)){
			//if each button has been clicked
			if(card1Box.contains(e.getPoint())){
				card1.flipKept();
			}
			//if card2 has a click
			else if(card2Box.contains(e.getPoint())){
				card2.flipKept();
			}
			//if card3 has a click
			else if(card3Box.contains(e.getPoint())){
				card3.flipKept();
			}
			
			//resets the board if two are selected
			if(resetBoard()){
				//go to the next screen
				client.openToWrite("data/leaveStarting.txt");
				client.writeToFile("#Which screen to be on; 1 for the presidents screen; 2 for the chancellors screen");
				client.writeToFile("2");
				client.close();
				
				//to pass the policy cards to the next screen
				client.openToWrite("data/VotingScreen.txt");
				client.writeToFile("#The voting policies to be passed");
				client.writeToFile(cat);
				client.close();
				
				//resets the opagueness of the card
				card1.setKept(false);
				card2.setKept(false);
				card3.setKept(false);
				//card4.setKept(false);
				//card5.setKept(false);
				
			}
		}
		
		//the chancellor picks the policy
		else if(box.contains(e.getPoint()) && state == SLPanel.GameState.POLICY && setPiece[0].equals(new String("2"))
				&& roles[1].equals(playerID)){
			//getting the board information from the file
			String[] boardInfo = client.readFile("data/Board.txt");
			int blue = Integer.parseInt(boardInfo[0]);
			int red = Integer.parseInt(boardInfo[1]);
			//the first card is selected
			if(card1Box.contains(e.getPoint())){

				if(card4.getType().equals(new String("Red"))){
					red += 1;
				}
				else if (card4.getType().equals(new String("Blue"))){
					blue +=1;
				}
			}
			
			//the second card
			else if(card2Box.contains(e.getPoint())){
				//the new card to go on the screen
				if(card5.getType().equals(new String("Red"))){
					red += 1;
					
				}
				else if (card5.getType().equals(new String("Blue"))){
					blue +=1;
				}
			}
			//anything is wrong; so throw error
			else{
				assert(false);
			}
			
			//update to the board file!
			final int newBlue = blue;
			final int newRed = red;
		
			//cuts out the third box area!
			if(card1Box.contains(e.getPoint()) || card2Box.contains(e.getPoint())){
				new Thread(() -> setCardStates(newBlue,newRed)).start();
				
				//resets the cards for the next policy selection
				try{
					resetCards();
				}
				catch(Exception ee){
					ee.printStackTrace();
				}
				//need to reset the file to appear on the presidents screen after the vote
				client.openToWrite("data/leaveStarting.txt");
				client.writeToFile("#Which screen to be on; 1 for the presidents screen; 2 for the chancellors screen");
				client.writeToFile("1");
				client.close();
				
				//resetting the selection of the cards
				card1.setKept(false);
				card2.setKept(false);
				card3.setKept(false);
				card4.setKept(false);
				card5.setKept(false);
				
				roles = client.readFile("data/Turn.txt");
				client.openToWrite("data/Turn.txt");
				client.writeToFile("#number of Blue victories");
				client.writeToFile(roles[0]);
				client.writeToFile("#number of Red victories");
				client.writeToFile("&&&&&&&&&&*");
				client.close();
				///////////////////client.openToWrite(name);
				
				nextState = true;
				setNewPositions();
			}
		}
	}
	


	/**Sets the number of blue and red cards in a file. Which updates the board
	 * @param blue, the number of blue cards
	 * @param red, the number of red cards
	 */
	private void setCardStates(int blue, int red) {
		//set the board
		client.openToWrite("data/Board.txt");
		client.writeToFile("#number of Blue victories");
		client.writeToFile(String.valueOf(blue));
		client.writeToFile("#number of Red victories");
		client.writeToFile(String.valueOf(red));
		client.close();
		
		//check if there are 5 blue policies and change state
		if(blue == 5){
			client.openToWrite("data/state.txt");
			client.writeToFile("BLUEGAMEOVER");
			client.close();
		}
		else if(red == 6){
			client.openToWrite("data/state.txt");
			client.writeToFile("REDGAMEOVER");
			client.close();
		}
		
		//set the file to move on to the next stage of the game
		else{
			client.openToWrite("data/state.txt");
			client.writeToFile("SELECTION");
			client.close();
		}
		return;
	}

	/**
	 * @return true or false, whether two cards have been selected or not
	 */
	private boolean resetBoard(){
		if(card1.getCardKept() && card2.getCardKept()){
			cat = card1.getType() + "\n" +  card2.getType();
			return true;
		}
		else if(card1.getCardKept() && card3.getCardKept()){
			cat = card1.getType() + "\n" + card3.getType();
			return true;
		}
		else if(card2.getCardKept() && card3.getCardKept()){
			cat = card2.getType() + "\n" + card3.getType();
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * @return true or false, whether the next state of the game should happen
	 */
	public boolean isNextState(){
		return nextState;
	}
	
	/**
	 * Will reset the cards for the president
	 * @throws IOException, if the picture doesn't open
	 */
	public void resetCards() throws IOException{
		Random cardRandomizer = new Random();
		
		//mapping to assign card values in a loop
		Map<String,PolicyCard> cardMap = new HashMap<String,PolicyCard>();
		(cardMap).put("card1", card1);
		(cardMap).put("card2", card2);
		(cardMap).put("card3", card3);
		//for loop that will assign blue or red to each card in the hash map
		for(int i = 1; i < cardAmount+1; i++){
			randInt = cardRandomizer.nextInt(18);
			String cardName = "card" + i;
			PolicyCard card = cardMap.get(cardName);
			if(randInt < 6){
				card.getNewImage("Blue");
			}
			if(randInt > 5){
				card.getNewImage("Red");
			}
		}
	}
	
	/**
	 * Sets the positions of the new president
	 */
	public void setNewPositions() {
		//gets the roles of the game currently
		String[] roles = client.readFile("data/Turn.txt");
		String[] players = client.readFile("data/Players.txt");
		while(players.length < playerInt){
			players = client.readFile("data/Players.txt");
		}
		
		int spot = 0;
		for(int i = 0; i < players.length;i++){
			if(roles[0].equals(players[i])){
				spot = i;
			}
		}
		//sets the new spot for the president
		if(spot == players.length-1){
			spot = 0;
		}
		else{
			spot +=1;
		}
		client.openToWrite("data/Turn.txt");
		client.writeToFile("#name of the President");
		client.writeToFile(players[spot]);
		client.writeToFile("#name of the Chancellor");
		client.writeToFile(roles[1]);
		client.close();
	}
	
}
