
package entity;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import ui.SLPanel;

/**This is our secret leader board.
 * It holds all the board information for one team.
 */
public class Board extends Entity{
	/**final width of the frame*/
	private static final int WIDTH  = 950;
	/**final height of the frame*/
	private static final int HEIGHT = 300;
	/**an array holding the VictoryCards*/
	private VictoryCard[] cards;
	
	/**
	 * @param point, spot on the board
	 * @param name, the  type of cards being displayed
	 * @throws IOException, if the image doesn't exist
	 */
	public Board(Point point, String name) throws IOException{
		super(point, new Dimension(WIDTH, HEIGHT), ImageIO.read(new File("data/"+name)));
	}
	
	/**@param num is the number of blue policies on the board
	 * @throws IOException
	 */
	public void blue(int num) throws IOException{
		int number;
		if(num >= 5)number = 5;
		else number = num;
		cards = new VictoryCard[number];
		for(int k=0;k<number;k++){
			int x = this.getX()+135+k*137;
			int y = this.getY()+60;
			VictoryCard vc = new VictoryCard(new Point(x,y),"BluePolicy.jpg");
			cards[k] = vc;
		}
	}
	
	/**@param num is the number of red policies on the board
	 * @throws IOException
	 */
	public void red(int num) throws IOException{
		int number;
		if(num >= 6)number = 6;
		else number = num;
		cards = new VictoryCard[number];
		for(int k=0;k<number;k++){
			int x = this.getX()+64+k*138;
			int y = this.getY()+61;
			VictoryCard vc = new VictoryCard(new Point(x,y),"RedPolicy.jpg");
			cards[k] = vc;
		}
	}
	
	@Override
	/**{@literal} draws the board on the graphics panel
	 * and the cards that are on the board
	 */
	public void draw(Graphics2D g2d, SLPanel panel){
		super.draw(g2d, panel);
		for(VictoryCard vc: cards)
			vc.draw(g2d, panel);
	}
}