/**This is our secret leader panel (hence SL before panel).
 * We can use this to keep track of the game state.
 * Basically when to do what eg. when to open the voting frame.
 */
package ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JOptionPane;

import entity.BlueGameOver;
import entity.RedGameOver;
import framework.*;

public class SLPanel extends SLCanvas{
	/**States**
	 * JOINING: The starting portion of the game
	 * PLAYING: ???
	 * VOTING: When the people are voting for the chancellor
	 * WAITING: When the game needs to sit at a rest for a period of time
	 * POLICY: When the president and chancellor are selecting the policies
	 * SELCTION: The stage where the President is selecting a chancellor
	 * BLUEGAMEOVER: When the blue team has won the game
	 * REDGAMEOVER: When the red team has won the game 
	 * */
	public static enum GameState{JOINING, PLAYING, VOTING, WAITING, POLICY,SELECTION, BLUEGAMEOVER, REDGAMEOVER}
	private static GameState state;
	/**Game implementation*/
	private Controller control;
	/**The main menu of the game */
	private MainMenu menu;
	/** file reader */
	private TCPClient client = new TCPClient();
	/** the userName of the player */
	private String userName;
	
	public SLPanel(){
		super();
		client.openToWrite("data/state.txt");
		client.writeToFile("JOINING");
		client.close();

		menu = new MainMenu(this);
		state = GameState.JOINING;
	}
	@Override
	/**{@literal} called from repaint()
	 */
	public void canvasDraw(Graphics2D g2d) {
		
		// maybe if First state then controller paint x
		// and if Second state then controller paint y
		try{ 
			if(state == GameState.WAITING){
				this.setBackground(new Color(255,255,255));
				control.draw(g2d, this,state);
			}
			
			else if(state == GameState.SELECTION){
				this.setBackground(new Color(255,255,255));
				control.draw(g2d, this,state);
			}

			//the menuning screen of the game
			else if(state == GameState.JOINING){
				menu.setTextArea();
				menu.waitingScreen();
				menu.draw(g2d,this);
				if(menu.getNextScreen() == true){
					this.setBackground(new Color(255,255,255));
					userName = menu.getUserName();
					removeAll();
					//setRoles();
					//state = GameState.POLICY;
					control = new Controller(userName);
					//getRole(userName);
						
				}
			}
			//voting if the chancellor will be elected or not
			else if(state == GameState.VOTING){
				this.setBackground(new Color(255,255,255));
				control.draw(g2d, this,state);
				if(control.hasVoted() == true){
					state = GameState.WAITING;
					control.setHasVoted(false);
					//need to make the game decide whether the vote passed or not right here
				}
			}
			
			//getting the policy to be set
			else if(state == GameState.POLICY){
				control.draw(g2d, this, state);
				if(control.hasVoted()){
					control.setHasVoted(false);
				}
			}
			//if the blue team has won
			else if(state == GameState.BLUEGAMEOVER){
					control.draw(g2d, this, state);
			}
			
			//if the red team has won
			else if(state == GameState.REDGAMEOVER){
				control.draw(g2d, this, state);
			}
			
			//should never get here
			else{
				assert(false);
			}
			
			//States when the game is going to be different from user to user, when the vote passes.
			if(state != GameState.WAITING || client.readFile("data/state.txt")[0].equals(new String("POLICY"))){
				setState();
			}
			else if(state == GameState.POLICY && client.readFile("data/state.txt")[0].equals(new String("WAITING"))){
				setState();
			}
			//when the vote has been rejected, the state goes here
			else if(state == GameState.WAITING && client.readFile("data/state.txt")[0].equals(new String("SELECTION"))){
				setState();
			}			
		//for IOStream file reading errors
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Tells the game what stage it should be in according to the next file "data/state.txt"
	 */
	private void setState() {
		String[] stateInfo = client.readFile("data/state.txt");
		while(stateInfo.length == 0){
			stateInfo = client.readFile("data/state.txt");
		}
		client.close();
		if(stateInfo[0].equals("JOINING")){
			state = GameState.JOINING;
		}
		else if(stateInfo[0].equals("PLAYING")){
			state = GameState.PLAYING;
		}
		else if(stateInfo[0].equals("POLICY")){
			state = GameState.POLICY;
		}
		else if(stateInfo[0].equals("VOTING")){
			state = GameState.VOTING;
		}
		else if(stateInfo[0].equals("BLUEGAMEOVER")){
			state = GameState.BLUEGAMEOVER;
		}
		else if(stateInfo[0].equals("REDGAMEOVER")){
			state = GameState.REDGAMEOVER;
		}
		else if(stateInfo[0].equals("WAITING")){
			state = GameState.WAITING;
		}
		else if(stateInfo[0].equals("SELECTION")){
			state = GameState.SELECTION;
		}
		
	}
	
	
	@Override
	/**{@literal} called when mouse is clicked
	 */
	public void mouseReleasedFramework(MouseEvent e){
		//menu.click(e);
		
		//at the menuning of the game this won't be up. 
		//So, once the game has been started this clicking can be on.
		//Otherwise, a ton of error will be thrown.
		if(state != GameState.JOINING){
			control.click(e);
		}
	}
}