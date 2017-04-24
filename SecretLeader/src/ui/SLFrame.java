
package ui;

import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import framework.TCPClient;

/**This is our secret leader frame (hence SL before frame).
 * We can use this as the main user interface.
 */
public class SLFrame extends JFrame{
	/**final width of the frame*/
	private static final int WIDTH  = 1400;
	/**final height of the frame*/
	private static final int HEIGHT = 825;
	
	/**
	 * Initializes the games window and initial files
	 */
	public SLFrame() {
		initWindow();
		
		//deletes files on closing of the frame
	    addWindowListener(new WindowAdapter(){
	        public void windowClosing(WindowEvent e) {
        		TCPClient client = new TCPClient();
        		String[] players = client.readFile("data/Players.txt");
        		for(int k = 0; k < players.length;k++){
        			File file = new File("data/Voting/Vote" + players[k]+".txt");
        			file.delete();
        		}
        		//resets the players file
	        	client.openToWrite("data/Players.txt");
	        	client.close();
	        }
	      });
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
		this.setResizable(false);

		Image icon = new ImageIcon("data/GameIcon.JPG").getImage();
		this.setIconImage(icon);
	}
	
}