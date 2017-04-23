package entity;
/**
 * This is both the voting card for yes and no. 
 * Has an action listener
 */
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.IOException;

import framework.TCPClient;
import ui.SLPanel;
import ui.SLPanel.GameState;

public class Votecard {
	
	/**box around the list*/
	private Rectangle box;
	/**final width of the frame*/
	private static final int WIDTH  = 240;
	/**final height of the frame*/
	private static final int HEIGHT = 145;
	//where the image is at
	private TCPClient client = new TCPClient();
	/**image for this part*/
	private Image image;
	//what type of card is this
	private String type;
	//whether to move on from the last state or not
	private boolean nextState = false;
	
	public Votecard(Point point,Image image,String typeIn) throws IOException{
		box = new Rectangle(point.x, point.y,WIDTH, HEIGHT);
		this.image = image.getScaledInstance(WIDTH, HEIGHT, 0);
		type = typeIn;
		//box = new Rectangle(point.x, point.y, WIDTH, HEIGHT);
	}
	/**{@literal}creates the list of players playing
	 */
	public void draw(Graphics2D g2d, SLPanel panel){
		g2d.drawImage(this.image, box.x, box.y, panel);
	}
	
	/**
	 * 
	 * @param e, the mouse clicking the button
	 * @return true or false, whether something happened or not
	 */
	public boolean click(MouseEvent e,SLPanel.GameState state) {
		//this is the border of things that can be clicked
		if(box.contains(e.getPoint()) && state == GameState.VOTING){
			int length = client.getLength("data/VotingFile.txt");
			String[] votes = client.readFile("data/VotingFile.txt");
			
			client.openToWrite("data/VotingFile.txt");
			for(int i = 0;i < length; i++){
				client.writeToFile(votes[i]);
			}
			client.writeToFile(type);
			client.close();
			
			//may be an issue here; for reading and writing to the voting file at the same time!
			//may also be an issue with the mainmenu later on
			
			nextState = true;
			return true;
		}
		return false;
	}
	
	/**
	 * @return true or false
	 * true if the vote has been made, false otherwise
	 */
	public boolean isNextState() {
		return nextState;
	}
	
	/**If more yes's then it passes. If more no's then it doesn't pass.
	 * If there's a tie then the vote doesn't pass
	 * @return true or false, true if the vote passed false otherwise.
	 */
	public boolean votePass(){
		String[] votes = client.readFile("data/VotingFile.txt");
		int yes = 0;
		int no = 0;
		for(int i = 0; i < votes.length; i++){
			if(votes[i].equals(new String("yes"))){
				yes +=1;
			}
			else if(votes[i].equals(new String("no"))){
				no +=1;
			}
		}
		//doesn't pass in a tie
		if(yes > no){
			return true;
		}
		return false;
	}
	
	
	public void setNextState(boolean set){
		nextState = set;
	}
		
}
