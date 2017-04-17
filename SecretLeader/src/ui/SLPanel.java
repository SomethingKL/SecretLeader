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
	private String userName;
	
	public SLPanel(){
		super();
		state = GameState.PLAYING;
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
		if(state == GameState.PLAYING){
			try{ 
				this.setBackground(new Color(255,255,255));
				control.draw(g2d, this,state);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		else if(state == GameState.JOINING){
			try{
				//maybe a try and catch block
				begin.setTextArea();
				begin.waitingScreen();
				begin.draw(g2d,this);
				if(begin.getNextScreen() == true){
					userName = begin.getUserName();
					removeAll();
					setRoles();
					state = GameState.VOTING;
					control = new Controller(userName);
				}
			} catch(IOException e){
			e.printStackTrace();
			}
		}
		else if(state == GameState.VOTING){
			try{ 
				this.setBackground(new Color(255,255,255));
				control.draw(g2d, this,state);
				if(control.hasVoted() == true){
					System.out.println("Change state?");
					state = GameState.POLICY;
					control.setHasVoted(false);
					//need to make the game decide whether the vote passed or not right here
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		else if(state == GameState.POLICY){
			try{ 
				control.draw(g2d, this, state);
				if(control.hasVoted()){
					state = GameState.PLAYING;
					control.setHasVoted(false);
					//state = GameState.PLAYING;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	@Override
	/**{@literal} called when mouse is clicked
	 */
	public void mouseReleasedFramework(MouseEvent e){
		System.out.println("clicked!");
		begin.click(e);
		control.click(e);
	}
	
	/**
	 * Sets the roles of the players in the file
	 */
	public void setRoles(){
		TCPClient getFile = new TCPClient();
		String[] scores = new String[getFile.getLength("data/Players.txt")];
		int length = getFile.getLength("data/Players.txt");
		scores = getFile.readFile("data/Players.txt");
		getFile.openToWrite("data/Roles.txt");
		getFile.writeToFile("#Player Names");
		for(int i =0; i < length; i++){
			String fileString = scores[i];
			if(i == 1){
				fileString += " Secret Leader";
				getFile.writeToFile(fileString);
			}
			else if(i%2 == 0){
				fileString += " Blue Team";
				getFile.writeToFile(fileString);
			}
			else if(i % 2 == 1){
				fileString += " Red Team";
				getFile.writeToFile(fileString);
			}
			else{
				System.out.println("Should never get here");
			}
		}
		getFile.close();
	}
}