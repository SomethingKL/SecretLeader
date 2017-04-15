/**This is our secret leader player list.
 * it reads from a file containing the names of players in the game.
 * and then draws those names on screen
 */
package entity;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.IOException;

import framework.TCPClient;
import ui.SLPanel;

public class PlayerList {
	/**box around the list*/
	private Rectangle box;
	/**final width of the frame*/
	private static final int WIDTH  = 190;
	/**final height of the frame*/
	private static final int HEIGHT = 290;
	/**used to get the list of players*/
	private TCPClient client = new TCPClient();
	
	public PlayerList(Point point) throws IOException{
		box = new Rectangle(point.x, point.y, WIDTH, HEIGHT);
	}
	/**{@literal}creates the list of players playing
	 */
	public void draw(Graphics2D g2d, SLPanel panel){
		Font font = new Font("Copperplate Gothic Bold", Font.ITALIC, HEIGHT/10);
		g2d.setFont(font);
		g2d.setStroke(new BasicStroke(2));
		g2d.draw(box);
		//get the current players
		String[] players = client.readFile("data/Players.txt");
		for(int k=0;k<players.length&&k<10;k++){
			int y = box.y+k*HEIGHT/10;
			Rectangle player = new Rectangle(box.x, y, WIDTH, HEIGHT/10);
			g2d.draw(player);
			g2d.drawString(players[k], box.x+2, y+HEIGHT/11);
		}
	}
	public void click(MouseEvent e) {
		//this is the border of things that can be clicked
		if(box.contains(e.getPoint())){
		
		}
	}
}