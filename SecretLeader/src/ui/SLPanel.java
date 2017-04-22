/**This is our secret leader panel (hence SL before panel).
 * We can use this to keep track of the game state.
 * Basically when to do what eg. when to open the voting frame.
 */
package ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.io.IOException;

<<<<<<< HEAD
import framework.Controller;
import framework.MainMenu;
=======
import javax.swing.JOptionPane;

import entity.BlueGameOver;
import entity.RedGameOver;
import framework.*;
>>>>>>> dc12f0767a5d30662018e77624708b5e35376de0

public class SLPanel extends SLCanvas{
	/**State
	 * PLAYING for when it's your turn and WAITING for when it's not your turn*/
<<<<<<< HEAD
	public static enum GameState{JOINING, APOINTMENT, VOTING, SELECTION, VICTORY, GAMEOVER}
=======
	public static enum GameState{JOINING, PLAYING, VOTING, WAITING, POLICY, BLUEGAMEOVER, REDGAMEOVER}
>>>>>>> dc12f0767a5d30662018e77624708b5e35376de0
	private static GameState state;
	/**Used for implementing the game
	 */
	private Controller control;
<<<<<<< HEAD
	private MainMenu menu;
=======
	private MainMenu begin;
	private TCPClient client = new TCPClient();
	//the userName of the player
>>>>>>> dc12f0767a5d30662018e77624708b5e35376de0
	private String userName;
	private BlueGameOver BlueGameOver;
	private RedGameOver RedGameOver;
	
	public SLPanel(){
		super();
<<<<<<< HEAD
		//try{
			menu = new MainMenu(this);
			state = GameState.JOINING;
		/*}catch(IOException e){
			e.printStackTrace();
		}*/
=======
		client.openToWrite("data/state.txt");
		client.writeToFile("JOINING");
		client.close();

		begin = new MainMenu(this);
		state = GameState.JOINING;
>>>>>>> dc12f0767a5d30662018e77624708b5e35376de0
	}
	@Override
	/**{@literal} called from repaint()
	 */
	public void canvasDraw(Graphics2D g2d) {
<<<<<<< HEAD
		if(state == GameState.JOINING){
=======
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
>>>>>>> dc12f0767a5d30662018e77624708b5e35376de0
			try{
				
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
//					//getRole(userName);
					
				}
<<<<<<< HEAD
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		else if(state == GameState.PLAYING){
			try{ 
				this.setBackground(new Color(255,255,255));
				control.draw(g2d, this,state);
			}catch(Exception e){
=======
			} catch(IOException e){
>>>>>>> dc12f0767a5d30662018e77624708b5e35376de0
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
		//if the blue team has won
		else if(state == GameState.BLUEGAMEOVER){
			//Brewer, do your thing here
			try{
				control.draw(g2d, this, state);
				System.out.println("blue win");
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		//if the red team has won
		else if(state == GameState.REDGAMEOVER){
			//Brewer, do your thing here
			try{
				control.draw(g2d, this, state);
				System.out.println("red win");
			}
			catch(Exception e){
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
		else if(stateInfo[0].equals("BLUEGAMEOVER")){
			state = GameState.BLUEGAMEOVER;
		}
		else if(stateInfo[0].equals("REDGAMEOVER")){
			state = GameState.REDGAMEOVER;
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
		String[] players = client.readFile("data/Players.txt");
		String tmpS= scores[0];
		for(int i = 0; i <length; i++){
			if(players[i].equals(userName)){
				System.out.println(scores[i]);
				tmpS = scores[i];
				String [] tmpA = tmpS.split(" ");
				tmpS = tmpA[1];
				
				 if(tmpS.equals(new String("Red"))){
					
					String redString = "";
					for(int j = 1; j < length; j+=2){
						System.out.println(scores[j]);
						redString += scores[j];
						redString+= "\n ";
					}
					JOptionPane.showMessageDialog(this,"You are on the Red Team! Your teammates are: \n" + redString,"Role Message", JOptionPane.PLAIN_MESSAGE);
				 }
						
				else if(tmpS.equals(new String("Secret"))){
					//JOptionPane.showMessageDialog(null, "You are the Secret Leader for the red team! Shhh!");
					tmpS = "Red";
					JOptionPane.showMessageDialog(this,"You are the Secret Leader for the red team! Shhh!","Role Message", JOptionPane.PLAIN_MESSAGE);
				}
			}	

		}
		return tmpS;
	}
	//need to find a way to display the dialog boxes on here 
	//if:
		//who the proposed chancellor is
		//whether the vote passed or not

}