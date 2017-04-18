/**This is our secret leader panel (hence SL before panel).
 * We can use this to keep track of the game state.
 * Basically when to do what eg. when to open the voting frame.
 */
package ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.io.IOException;

import framework.*;

public class SLPanel extends SLCanvas{
	/**State
	 * PLAYING for when it's your turn and WAITING for when it's not your turn*/
	public static enum GameState{JOINING, PLAYING, VOTING, WAITING,POLICY, GAMEOVER}
	private static GameState state;
	/**Used for implementing the game
	 */
	private Controller control;
	private MainMenu begin;
	private TCPClient client = new TCPClient();
	//the userName of the player
	private String userName;
	private int presidentSpot = 3;
	
	public SLPanel(){
		super();
		client.openToWrite("data/state.txt");
		client.writeToFile("JOINING");
		client.close();
		//try{
		
			this.removeAll();
			begin = new MainMenu(this);
			state = GameState.JOINING;
			
		//}
			/*
		
		}catch(IOException e){
			e.printStackTrace();
		}*/
	}
	@Override
	/**{@literal} called from repaint()
	 */
	public void canvasDraw(Graphics2D g2d) {
		// maybe if First state then controller paint x
		// and if Second state then controller paint y

		if(state == GameState.WAITING){
			try{ 
				this.setBackground(new Color(255,255,255));
				control.draw(g2d, this,state);
				//if the game gets here while in the policy stage then the new chancellor needs to get selected
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		//the beginning screen of the game
		else if(state == GameState.JOINING){
			try{
				//maybe a try and catch block
				begin.setTextArea();
				begin.waitingScreen();
				begin.draw(g2d,this);
				if(begin.getNextScreen() == true){
					this.setBackground(new Color(255,255,255));
					userName = begin.getUserName();
					removeAll();
					//setRoles();
					state = GameState.POLICY;
					control = new Controller(userName);
				}
			} catch(IOException e){
				e.printStackTrace();
			}
		}
		
		//voting if the chancellor will be elected or not
		else if(state == GameState.VOTING){
			try{ 
				this.setBackground(new Color(255,255,255));
				control.draw(g2d, this,state);
				if(control.hasVoted() == true){
					state = GameState.WAITING;
					control.setHasVoted(false);
					//need to make the game decide whether the vote passed or not right here
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		//getting the policy to be set
		else if(state == GameState.POLICY){
			try{ 
				control.draw(g2d, this, state);
				if(control.hasVoted()){
					//if the policy has been set then the president needs to be moved up one.
					control.setHasVoted(false);
					//state = GameState.PLAYING;
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		//need to wait for all people to vote
		//this is here because there is a time or two in the game where 
		//the game needs to stay in waiting for a few players but remain in
		//a different state for the other.
		String [] tmp = client.readFile("data/state.txt");
		if(state != GameState.WAITING || client.readFile("data/state.txt")[0].equals(new String("POLICY"))){
			setState();
		}
		else if(state == GameState.POLICY && client.readFile("data/state.txt")[0].equals(new String("WAITING"))){
			setState();
		}
	}
	
	/**
	 * Tells the game what stage it should be in
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
		else if(stateInfo[0].equals("GAMEOVER")){
			state = GameState.GAMEOVER;
		}
		else if(stateInfo[0].equals("WAITING")){
			state = GameState.WAITING;
		}
		
	}
	
	
	@Override
	/**{@literal} called when mouse is clicked
	 */
	public void mouseReleasedFramework(MouseEvent e){
		begin.click(e);
		
		//at the beginning of the game this won't be up. 
		//So, once the game has been started this clicking can be on.
		//Otherwise, a ton of error will be thrown.
		if(state != GameState.JOINING){
			control.click(e);
		}
	}

}