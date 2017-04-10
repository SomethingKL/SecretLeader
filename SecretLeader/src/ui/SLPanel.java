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
	private static enum GameState{JOINING, PLAYING, WAITING, GAMEOVER}
	private static GameState state;
	
	/**Used for implementing the game
	 */
	private Controller control;
	private MainMenu begin;
	
	public SLPanel(){
		super();
		state = GameState.PLAYING;
		try{
			begin = new MainMenu(this);
			this.removeAll();
			//state = GameState.JOINING;
			control = new Controller();
		
			
		
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	@Override
	/**{@literal} called from repaint()
	 */
	public void canvasDraw(Graphics2D g2d) {
		// maybe if First state then controller paint x
		// and if Second state then controller paint y
		if(state != GameState.JOINING){
			try{ 
				this.setBackground(new Color(255,255,255));
				control.draw(g2d, this);
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		else if(state == GameState.JOINING){
			//maybe a try and catch block
			begin.setTextArea();
			
			
		}
	}
	@Override
	/**{@literal} called when mouse is clicked
	 */
	public void mouseReleasedFramework(MouseEvent e){
		System.out.println("clicked!");
	}
}