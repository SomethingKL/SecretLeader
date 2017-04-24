package entity;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**Either a blue or red policy. This runs the logic of the policy card and displays it
 */
public class PolicyCard extends Entity{
	/**final width of the frame*/
	private static final int WIDTH  = 100;
	/**final height of the frame*/
	private static final int HEIGHT = 150;
	//whether to keep the card or not
	private boolean cardKept = false;
	//the type of the card; BluePolicy or RedPolicy
	private String type;
	
	/**
	 * @param point, spot on the board
	 * @param name, name of the file
	 * @throws IOException, file does not exist
	 */
	public PolicyCard(Point point,String name) throws IOException{
		super(point, new Dimension(WIDTH, HEIGHT), ImageIO.read(new File("data/"+name+"Policy.jpg")));
		type = name;
	}
	
	/**
	 * @return cardKept, whether the card has been kept
	 */
	public boolean getCardKept(){
		return cardKept;
	}
	
	/**
	 * @return type, blue or red policy card
	 */
	public String getType(){
		return type;
	}
	
	/**
	 * Flip the value of the policy card selected
	 */
	public void flipKept(){
		if(cardKept == true){
			cardKept = false;
		}
		else{
			cardKept = true;
		}
		return;
	}
	
	/** Resets the card value
	 * @param side, whether to keep the card or not.
	 */
	public void setKept(boolean side){
		cardKept = false;
	}
	
	
	/**Sets a new picture for the policycard
	 * 
	 * @param type, the card; Red or Blue
	 * @throws IOException, if the image does not exist
	 */
	public void getNewImage(String type) throws IOException{
		this.resetImage(ImageIO.read(new File("data/"+type+"Policy.jpg")));
		this.type = type;
	}

	/**Checks to see if the player is the secret leader or not
	 * @param secret, the player list with their roles
	 * @param playerID, the currrent player ID
	 * @return true if the player is the secret leader
	 */
	public boolean checkLeader(String[] secret, String playerID) {
		for(int k = 0; k < secret.length;k++){
			if(secret[k].equals(playerID + " Secret Leader")){
				return true;
			}
		}
		return false;
	}
}
