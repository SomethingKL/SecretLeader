package entity;
/**This is our secret leader player list.
 * it reads from a file containing the names of players in the game.
 * and then draws those names on screen
 */

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.IOException;

import framework.TCPClient;
import ui.SLPanel;


public class ResultsBox {

	/**box around the list*/
	private Rectangle box;
	/**final width of the frame*/
	private static final int WIDTH  = 240;
	/**final height of the frame*/
	private static final int HEIGHT = 150;
	/**used to get the list of players*/
	private TCPClient client = new TCPClient();
	
	private final String header = "Status Area";
	
	public ResultsBox(Point point) throws IOException{
		box = new Rectangle(point.x, point.y, WIDTH, HEIGHT);
	}
	/**{@literal}creates the list of players playing
	 */
	public void draw(Graphics2D g2d, SLPanel panel){
		
		String[] info = client.readFile("data/displayInfo.txt");
		while(info.length < 1){
			info = client.readFile("data/displayInfo.txt");
		}
		String string1st = "";
		String string2nd = "";
		if(info[0].equals(new String("0"))){
			//display nothing
		}
		else if(info[0].equals(new String("1"))){
			string1st= "Vote PASSED";
			string2nd = "";
		}
		else if(info[0].equals(new String("2"))){
			string1st= "Vote NOT PASSED";
			string2nd = "";
		}
		else if(info[0].equals(new String("3"))){
			String[] chan = client.readFile("data/ProposedChancellor.txt");
			string1st = "Chancellor Nomination";
			string2nd = chan[0];
		}
		Font font = new Font("Copperplate Gothic Bold", Font.ITALIC, 18);
		g2d.setFont(font);
		g2d.setStroke(new BasicStroke(2));
		//g2d.draw(box);
		g2d.draw(box);
		g2d.drawString(header, box.x + WIDTH/4, 40);
		g2d.drawString(string1st, box.x,box.y+HEIGHT/2);
		g2d.drawString(string2nd, box.x,box.y+HEIGHT/2+20);
	}
	public void click(MouseEvent e, SLPanel.GameState state) {
		//this is the border of things that can be clicked
		if(box.contains(e.getPoint())){
		}
	}
}