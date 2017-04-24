/**This is our secret leader player list.
 * it reads from a file containing the names of players in the game.
 * and then draws those names on screen
 */
package entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.IOException;

import framework.TCPClient;
import ui.SLPanel;

public class PlayerList {
	/**box around the list*/
	private Rectangle[] box;
	/**list of names in the game*/
	private String[] players;
	/**top left corner for the player list*/
	private Point point;
	/**final width of the frame*/
	private static final int WIDTH  = 190;
	/**final height of the frame*/
	private static final int HEIGHT = 290;
	/**used to get the list of players*/
	private TCPClient client = new TCPClient();
	/** the color values of the players */
	private int[] colorPlayer;
	/** the username of the player */
	private String userName;
	
	public PlayerList(Point start, String role) throws IOException{
		 this.point = start;
		 //get the current players
		 players = client.readFile("data/Players.txt");
		 box = new Rectangle[players.length];
		 colorPlayer = new int[players.length];
		 
		 for(int k=0;k<players.length&&k<10;k++){
			 int y = point.y+k*HEIGHT/10;
			 Rectangle player = new Rectangle(point.x, y, WIDTH, HEIGHT/10);
			 box[k] = player;
			 colorPlayer[k] = 0;
		 }
		 if(role.compareTo("Red")==0)
			 for(int k=3;k<colorPlayer.length;k++)
				 if(k%2==1)
					 colorPlayer[k] = 2;
		 for(int k=0;k<players.length&&k<10;k++){
			 int i = rn(0,players.length-1);
			 String temp = players[k];
			 players[k] = players[i];
			 players[i] = temp;
			 int h = colorPlayer[k];
			 colorPlayer[k] = colorPlayer[i];
			 colorPlayer[i] = h;
		 }
	}
	//Pre: takes a minimun and maximum value inclusively
	//Post: returns a random number within that range
	int rn(int min, int max){    
	   return (int)(Math.random()*(max-min+1))+min;
	}
	/**{@literal}creates the list of players playing
	 */
	public void draw(Graphics2D g2d, SLPanel panel){
		Font font = new Font("Copperplate Gothic Bold", Font.ITALIC, HEIGHT/10);
		g2d.setFont(font);
		g2d.setStroke(new BasicStroke(2));
		
		for(int k=0;k<players.length&&k<10;k++){
			int y = point.y+k*HEIGHT/10;
			
			g2d.setColor(Color.black);
			g2d.draw(box[k]);
			if(colorPlayer[k] == 1){
				g2d.setColor(Color.GREEN);
			}else if(colorPlayer[k] == 2){
				g2d.setColor(Color.RED);
			}else{ 
				g2d.setColor(Color.black);
			}
			
			g2d.drawString(players[k], point.x+2, y+HEIGHT/11);
			g2d.setColor(Color.black);
		}
	}
	
	/**
	 * Sets the username of the player
	 * @param user, the user of the game
	 */
	public void setUserName(String user){
		userName = user;
	}
	/**
	 * Gets the length of the player list
	 */
	public int getPlayerslength(){
		return players.length;
	}
	/**
	 * @param e, the mouse click
	 * @param state, the current state of the game
	 */
	public void click(MouseEvent e, SLPanel.GameState state) {
		String[] roles = client.readFile("data/Turn.txt");
		
		for(int k=0;k<players.length&&k<10;k++){
			colorPlayer[k] = 0;
			if(box[k].contains(e.getPoint()) && roles[0].equals(userName)){
				client.openToWrite("data/ProposedChancellor.txt");
				client.writeToFile(players[k]);
				
				//fixes the colors of the players
				if(colorPlayer[k] != 1){
					colorPlayer[k] = 1;
				}
			}
		}
	}
}