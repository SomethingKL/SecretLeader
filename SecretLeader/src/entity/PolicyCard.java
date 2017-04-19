package entity;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PolicyCard extends Entity{
	/**final width of the frame*/
	private static final int WIDTH  = 100;
	/**final height of the frame*/
	private static final int HEIGHT = 150;
	//whether to keep the card or not
	private boolean cardKept = false;
	//the type of the card; BluePolicy or RedPolicy
	private String type;
	
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
	
	public void flipKept(){
		if(cardKept == true){
			cardKept = false;
		}
		else{
			cardKept = true;
		}
		return;
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
}
