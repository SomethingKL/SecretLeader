/**This is our secret leader frame (hence SL before frame).
 * We can use this as the main user interface.
 */
package ui;

import javax.swing.JFrame;

public class SLFrame extends JFrame{
	/**final width of the frame*/
	private static final int WIDTH  = 1180;
	/**final height of the frame*/
	private static final int HEIGHT = 825;

	public SLFrame() {
		initWindow();
	}
	
	/**{@literal} Initializes game window
	 */
	private void initWindow() {
		this.setContentPane(new SLPanel());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("SecretLeader");
		//this.setLocationRelativeTo(null);
		this.setSize(WIDTH, HEIGHT);
		this.setVisible(true);
		this.setResizable(true);
	}
}