package entity;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.Color.*;
import javax.imageio.ImageIO;

import framework.TCPClient;
import ui.SLPanel;

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
	//whether the next screen should be choosen
	private boolean nextState = false;
	
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
		//////////////how to change the image
		card1.getNewImage("Red");
		
		card1Box = new Rectangle(760,620,WIDTH,HEIGHT);
		card2Box = new Rectangle(900,620,WIDTH,HEIGHT);
		card3Box = new Rectangle(1040,620,WIDTH,HEIGHT);
	}
	
	/**{@literal}
	 * @throws IOException 
	 * Will only put the cards down if the player is the chancellor or the president
	 */
	public void draw(Graphics2D g2d, SLPanel panel) throws IOException{
		String[] turnInfo = client.readFile("data/Turn.txt");
		String[] setPiece = client.readFile("data/leaveStarting.txt");
		client.close();
		
		//the first stage of the game, where the president picks his cards
		if(setPiece[0].contains(new String("1")) && turnInfo[0].equals(playerID)){
			card1.draw(g2d,panel);
			card2.draw(g2d, panel);
			card3.draw(g2d, panel);
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
	 */
	public void click(MouseEvent e, SLPanel.GameState state) {
		String[] setPiece = client.readFile("data/leaveStarting.txt");
		//this is the border of things that can be clicked
		//has to be in the right gamestate, right point and in the right part of the game
		if(box.contains(e.getPoint()) && state == SLPanel.GameState.POLICY && setPiece[0].equals(new String("1"))){
			//if each button has been clicked
			if(card1Box.contains(e.getPoint())){
				if(card1.getCardKept()){
					card1.flipKept();
					//need to figure out how to change the color of a rectangle
					//need to look like a highlight
				}
				else{
					card1.flipKept();
				}
			}
			
			else if(card2Box.contains(e.getPoint())){
				if(card2.getCardKept()){
					card2.flipKept();
				}
				else{
					card2.flipKept();
				}
			}
			else if(card3Box.contains(e.getPoint())){
				if(card3.getCardKept()){
					card3.flipKept();
				}
				else{
					card3.flipKept();
				}
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
				
			}
		}
		
		else if(box.contains(e.getPoint()) && state == SLPanel.GameState.POLICY && setPiece[0].equals(new String("2"))){
			//getting the board information from the file
			String[] boardInfo = client.readFile("data/Board.txt");
			int blue = Integer.parseInt(boardInfo[0]);
			int red = Integer.parseInt(boardInfo[1]);
			if(card1Box.contains(e.getPoint())){
				

				//the first card
				if(card4.getType().equals(new String("Red"))){
					System.out.println("card 1 red");
					red += 1;
				}
				else if (card4.getType().equals(new String("Blue"))){
					blue +=1;
					System.out.println("card 1 blue");
				}
			}
			//the second card
			else if(card2Box.contains(e.getPoint())){
				//the new card to go on the screen
				if(card5.getType().equals(new String("Red"))){
					System.out.println("card 2 red");
					red += 1;
					
				}
				else if (card5.getType().equals(new String("Blue"))){
					System.out.println("card 2 blue");
					blue +=1;
				}
			}
			else{
				assert(false);
			}
			
			//update to the board file!
			final int newBlue = blue;
			final int newRed = red;
			new Thread(() -> doWork(newBlue,newRed)).start();
			
			/*
			try {
			    Thread.sleep(10000);                 //1000 milliseconds is one second.
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}*/
			//have to get the next screen to come up everytime
			nextState = true;
		}
	}
	
	/**Sets the number of blue and red cards in a file
	 * 
	 * @param blue, the number of blue cards
	 * @param red, the number of red cards
	 */
	private void doWork(int blue, int red) {
		client.openToWrite("data/Board.txt");
		client.writeToFile("#number of Blue victories");
		client.writeToFile(String.valueOf(blue));
		client.writeToFile("#number of Red victories");
		client.writeToFile(String.valueOf(red));
		client.close();
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
	 * 
	 * @return true or false, whether the next state of the game should happen
	 */
	public boolean isNextState(){
		return nextState;
	}
}
