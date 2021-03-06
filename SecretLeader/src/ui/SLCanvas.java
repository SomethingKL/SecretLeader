
package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

/**This is our secret leader canvas (hence SL before canvas).
 * The Canvas is an abstraction of both the JPanel and the MouseListener.
 * The main point of this is to reduce the work of our panel.
 */
public abstract class SLCanvas extends JPanel implements MouseListener{

	/**
	 * Does the upper level drawing of the game
	 */
	public SLCanvas() {
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		this.setBackground(Color.white);
		this.addMouseListener(this);
	}

	/**============== DRAWING =============
	 */

	/**Implemented in the SLPanel
	 * @param g2d
	 */
	public abstract void canvasDraw(Graphics2D g2d);

	@Override
	/**{@literal} Draws everything
	 * called from within JPanel
	 */
	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);
		this.canvasDraw(g2d);
	}

	/**============== MOUSE ===============
	 */
	@Override
	public void mouseEntered(MouseEvent e){
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseExited(MouseEvent e){
		// TODO Auto-generated method stub
	}
	@Override
	public void mousePressed(MouseEvent e){
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseReleased(MouseEvent e){
		// TODO Auto-generated method stub
	}
	/**{@literal} When the mouse is clicked 
	 * it calls the sub class method as a separate thread
	 */
	@Override
	public void mouseClicked(MouseEvent e){
		Thread click = new Thread(){
			@Override
			public void run() {
				mouseReleasedFramework(e);
			}
		};
		click.start();
	}
	
	/**{@literal} Implemented in SLPanel
	 * @param e holds the information attached to the event
	 */
	public abstract void mouseReleasedFramework(MouseEvent e);
}