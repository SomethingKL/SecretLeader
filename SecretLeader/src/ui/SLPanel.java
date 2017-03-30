/**This is our secret leader panel (hence SL before panel).
 * We can use this to keep track of the game state.
 * Basically when to do what eg. when to open the voting frame.
 */
package ui;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class SLPanel extends SLCanvas{
	/**Utility*/
	private final long secInNanosecond = 1000000000L;
	private final long milisecInNanosec = 1000000L;
	
	/**Used for managing FPS*/
	private final int GAME_FPS = 30;
	private final long GAME_UPDATE_PERIOD = secInNanosecond/GAME_FPS;
	
	/**State
	 * these can change when we decide what the different states are*/
	private static enum GameState{FIRST, SECOND, THIRD}
	private static GameState state;
	
	public SLPanel(){
		super();
		state = GameState.FIRST;
		
		/**refreshes the game in a new thread
		 */
		Thread gameThread = new Thread(){
			@Override
			public void run() {
				loop();
			}
		};
		gameThread.start();
	}
	
	/**MAIN GAME LOOP
	 */
	private void loop() {
		//Used for calculating wait time during the FPS
		long beginTime, timeTaken, timeLeft;
		
		while(true){
			beginTime = System.nanoTime();
			
			//Render
			repaint();
			
			timeTaken = System.nanoTime() - beginTime;
            timeLeft = (GAME_UPDATE_PERIOD - timeTaken) / milisecInNanosec; // In milliseconds
            if(timeLeft < 10) 
                timeLeft = 10;
            try{
            	Thread.sleep(timeLeft);
            } catch (InterruptedException ex){}
		}
	}

	@Override
	/**{@literal} called from repaint()
	 */
	public void canvasDraw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		// maybe if First state then controller paint x
		// and if Second state then controller paint y
	}

	@Override
	/**{@literal} called when mouse is clicked
	 */
	public void mouseReleasedFramework(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}