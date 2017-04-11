package framework;

/**This runs the beginning frame
 * There is currently a warning at line ?. The program doesn't like to read in a null string from a file. 
 * It has something to do with the action submitActionListener
 * 
 */
import java.awt.Font;
import java.awt.Component.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import ui.SLPanel;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

//import javax.awt.DocumentSizeFilter;
public class MainMenu {
	/**reads relevant game information*/
	private TCPClient client = new TCPClient();
	//I do not want this to repaint another button everytime
	private boolean repaint= false;
	private SLPanel slPanel;
	//label above the text pane
	private JLabel userNameLabel;
	//where the user puts their input
	private JTextPane field;
	//the button that sends the user to the next screen
	private JButton submit;
	//the name of the user for the game
	private String userName = "User";
	//where the screen should be at
	private boolean waitingScreen = false;
	//whether the game can go to the next state or not
	private boolean nextScreen = false;
	
	public MainMenu(SLPanel slPanelIn){
		slPanel = slPanelIn;
		slPanel.setLayout(null);
		setTextArea();
		repaint = true;
	}
	
	public void draw(Graphics2D g2d, SLPanel slPanel){
		slPanel.revalidate();
		slPanel.repaint();
		
	}
	
	/**This decides whether or not to move onto the next part of the game.
	 * @return nextScreen, whether the game should go to the next screen or not.
	 */
	public boolean getNextScreen(){
		return nextScreen;
	}
		
		//String[] scores = client.readFile("data/Board.txt");
	
	/**
	 * This sets the panel up for the initial part of the game.
	 */
	public void setTextArea(){
		if(repaint == true){
			return;
		}
		
		//this sets the character limit for the username
		DefaultStyledDocument fieldDoc = new DefaultStyledDocument();
		fieldDoc.setDocumentFilter(new DocumentSizeFilter(10));
  
		slPanel.setBackground(new Color(37,41,42));
		
		//initializing my attributes
		field = new JTextPane(fieldDoc);
		userNameLabel = new JLabel("Please enter a username:");
		submit = new JButton("Submit");
		
		//need to set the bounds of the button to add it.
		field.setBounds(200,200,300,50);
		field.setPreferredSize(new Dimension(300,50));
		Font font = new Font("Copperplate Gothic Bold", Font.ITALIC,25);
		field.setFont(font);
		field.setBackground(new Color(100, 100, 100));
		
		//userNameLabel settings
		userNameLabel.setFont(font);
		userNameLabel.setPreferredSize(new Dimension(400,50));
		userNameLabel.setBounds(150,150,400,50);
		userNameLabel.setForeground(new Color(200,20,20));
		
		//submit button settings
		submit.setFont(font);
		submit.setPreferredSize(new Dimension(400,50));
		submit.setBounds(150,400,400,50);
		submit.addActionListener(new submitButtonAction());

		
		//adding the pieces to the panel
		slPanel.add(submit);
		slPanel.add(userNameLabel);
		slPanel.add(field);
	}
	
	public void waitingScreen(){
		
		if(waitingScreen == false){
			return;
		}
		
		String[] scores = client.readFile("data/Players.txt");
		//System.out.println(scores.length);
		JLabel waitingLabel = new JLabel("Waiting for All Users to Join...");
		
		//settings for the waitingLabel
		Font font = new Font("Copperplate Gothic Bold", Font.ITALIC,25);
		waitingLabel.setFont(font);
		waitingLabel.setPreferredSize(new Dimension(600,50));
		waitingLabel.setBounds(150,150,600,50);
		waitingLabel.setForeground(new Color(200,20,20));
		
		
		if (scores == null || scores.length == 0){
			
		}
		else if(scores.length >= 4){
			
			JButton startGame = new JButton("Click here to start!");
			startGame.setFont(font);
			startGame.setPreferredSize(new Dimension(400,50));
			startGame.setBounds(150,400,400,50);
			
			startGame.addActionListener(event -> nextScreen = true);
			
			slPanel.add(startGame);
			//submit.addActionListener(new submitButtonAction());
			
		}
		
		slPanel.add(waitingLabel);
		slPanel.revalidate();
		slPanel.repaint();
	}
	
	/**
	 * If the button is clicked then the next screen is drawn up; for waiting.
	 */
	private class submitButtonAction implements ActionListener{
		public synchronized void  actionPerformed(ActionEvent event){
			//this will put the game to the waiting screen
				String tmpString = field.getText();
				if(tmpString != null && !tmpString.isEmpty()){
					userName = tmpString;
					//make the person put a name!
					slPanel.removeAll();
					//makes the new screen appear
					waitingScreen = true;
					
					//outputs the user information to a file
					String[] scores = client.readFile("data/Players.txt");
					//scores.toString();
					
					if(scores == null || scores.length == 0){
						client.openToWrite("data/Players.txt");
						client.writeToFile("#Player Names");
						client.writeToFile(userName);
								
					}
					else{
						client.openToWrite("data/Players.txt");
						client.writeToFile("#Player Names");
						for(int i = 0; i < scores.length; i++){
							client.writeToFile(scores[i]);
						}
						client.writeToFile(userName);
					}
					client.close();
					waitingScreen();
				}
				
			
			
		}
	}
	
}
