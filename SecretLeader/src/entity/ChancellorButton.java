package entity;


import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import framework.TCPClient;
import ui.SLPanel;
import ui.SLPanel.GameState;

/**
 * Button for the selection of the chancellor
 */
public class ChancellorButton extends Entity{
	
	/**final width of the frame*/
	private static final int WIDTH  = 250;
	/**final height of the frame*/
	private static final int HEIGHT = 125;
	/**reads relevant game information*/
	private TCPClient client = new TCPClient();
	/**the userName of the player **/
	private String userName;
	
	/**
	 * @param point on the board
	 * @param name, the user name of the player
	 * @param userNameIn, 
	 * @throws IOException, if the image is not there
	 */
	public ChancellorButton(Point point,String name,String userNameIn) throws IOException{
		super(point, new Dimension(WIDTH, HEIGHT), ImageIO.read(new File("data/SelectChancellor.jpg")));
		userName = userNameIn;
	}

	/**Changes the state of the game once the chancellor has been successfully set
	 *@param e, the mouse event
	 * @param state, the current state of the game
	 */
	public void click(MouseEvent e,SLPanel.GameState state) {
		String[] roles = client.readFile("data/Turn.txt");
		//this is the border of things that can be clicked
		if(box.contains(e.getPoint()) && state == GameState.SELECTION && userName.equals(roles[0])){
			String[] chan = client.readFile("data/ProposedChancellor.txt");
			//the president cannot be elected as chancellor.
			if(userName.equals(chan[0]) == false && chan[0].equals(new String("None")) == false){
				client.openToWrite("data/state.txt");
				client.writeToFile("VOTING");
				client.close();
				client.openToWrite("data/displayInfo.txt");
				client.writeToFile("3");
				client.close();
			}
		}
	}
}
