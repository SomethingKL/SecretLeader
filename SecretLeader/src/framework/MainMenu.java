package framework;

import java.awt.Color;
import java.awt.Dimension;
/**This runs the beginning frame
 * There is currently a warning at line ?. The program doesn't like to read in a null string from a file. 
 * It has something to do with the action submitActionListener
 * 
 */
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultStyledDocument;

import ui.SLPanel;

//import javax.awt.DocumentSizeFilter;
public class MainMenu extends JPanel {
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
	private ColorButton submit;
	//the name of the user for the game
	private String userName = "User";
	//where the screen should be at
	private boolean waitingScreen = false;
	//whether the game can go to the next state or not
	private boolean nextScreen = false;
	
	public MainMenu(SLPanel slPanelIn){
		//set the files
		initGameFiles();

		slPanel = slPanelIn;
		slPanel.setLayout(null);
		setTextArea();
		repaint = true;
	}
	
	/**
	 * Resets all of the files for the game
	 */
	public void initGameFiles(){
		int blue = 4;
		int red = 4;
		
		//the starting screen information
		client.readFile("data/Board.txt");
		client.openToWrite("data/leaveStarting.txt");
		client.writeToFile("#Whether all screens should go to the nextScreen");
		client.writeToFile("false");
		client.close();
		
		//board information reset
		client.openToWrite("data/Board.txt");
		client.writeToFile("#number of Blue victories");
		client.writeToFile(String.valueOf(blue));
		client.writeToFile("#number of Red victories");
		client.writeToFile(String.valueOf(red));
		client.close();
		
		//information in the box being reset
		client.openToWrite("data/displayInfo.txt");
		client.writeToFile("0");
		client.close();
		
		//the roles of the players being reset
		client.openToWrite("data/Roles.txt");
		client.writeToFile("");
		client.close();
		
		//chancellor proposed chancellor being reset
		client.openToWrite("data/ProposedChancellor.txt");
		client.writeToFile("None");
		client.close();
		/*
		client.openToWrite("data/Voting.txt");
		client.close();*/
		//client.openToWrite("data/VotingFile.txt");
		//client.close();
		
		///will have a ton more stuff for the initial game files here
		
	}
	
	/**
	 * 
	 * @param g2d, the graphic that is being drawn
	 * @param slPanel, the panel that is changing, from the level above
	 */
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
		submit = new ColorButton("Submit");
		
	
		//need to set the bounds of the button to add it.
		field.setBounds(500,300,300,50);
		field.setPreferredSize(new Dimension(300,50));
		Font font = new Font("Copperplate Gothic Bold", Font.ITALIC,25);
		field.setFont(font);
		field.setBackground(new Color(100, 100, 100));
			// Get KeyStroke for enter key
		KeyStroke enterKey = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0);
		KeyStroke tabKey = KeyStroke.getKeyStroke(KeyEvent.VK_TAB,0);
			// Override enter for a pane
		String actionKey = "none";
		InputMap map = field.getInputMap();
		map.put(enterKey, actionKey);
		map.put(tabKey, actionKey);
		
		//userNameLabel settings
		userNameLabel.setFont(font);
		userNameLabel.setPreferredSize(new Dimension(400,50));
		userNameLabel.setBounds(470,250,400,50);
		userNameLabel.setForeground(new Color(200,20,20));
		
		//submit button settings
		submit.setFont(font);
		submit.setPreferredSize(new Dimension(400,50));
		submit.setBounds(450,400,400,50);
		submit.addActionListener(new submitButtonAction());
		submit.setBackground(new Color(200,20,20));
		submit.setHoverBackgroundColor(Color.GRAY);
		submit.setPressedBackgroundColor(Color.GRAY);
		//submit.addMouseListener(new colorChange(submit,new Color(0,0,0)));

		
		//adding the pieces to the panel
		slPanel.add(submit);
		slPanel.add(userNameLabel);
		slPanel.add(field);
	}
	
	/**
	 * The screen where the users wait or go to the game!
	 */
	public void waitingScreen(){
		
		//will only print the screen when the user has enter a name
		if(waitingScreen == false){
			return;
		}
		
		//whether the game should end the waiting section and continue on to the board
		String[] goToNextScreen = client.readFile("data/leaveStarting.txt");
		while(goToNextScreen.length < 1){
			goToNextScreen = client.readFile("data/leaveStarting.txt");
		}
		
		if(goToNextScreen[0].equals("true")){
			nextScreen = true;
		}
		String [] scores = client.readFile("data/Players.txt");
		
		//settings for the waitingLabel
		JLabel waitingLabel = new JLabel("Waiting for All Users to Join...");
		Font font = new Font("Copperplate Gothic Bold", Font.ITALIC,25);
		waitingLabel.setFont(font);
		waitingLabel.setPreferredSize(new Dimension(600,50));
		waitingLabel.setBounds(425,250,600,50);
		waitingLabel.setForeground(new Color(200,20,20));
		
		//once the game has enough players to play
		if(scores.length >= 4){
			ColorButton startGame = new ColorButton("Click here to start!");
			startGame.setFont(font);
			startGame.setPreferredSize(new Dimension(400,50));
			startGame.setBounds(450,400,400,50);
			startGame.addActionListener(new nextScreenButton());
			startGame.setBackground(new Color(200,20,20));
	
			slPanel.add(startGame);
		}
		
		//adding everything to the panel
		slPanel.add(waitingLabel);
		slPanel.revalidate();
		slPanel.repaint();
	}
	
	/**Setting the roles for all of the players
	 * @param userName, the name of the player
	 * @return Red or Blue, to set the player card
	 */
	public String getRole(String userName){
		setRoles();
		int length = client.getLength("data/Roles.txt");
		String[] scores = new String[length];
		scores = client.readFile("data/Roles.txt");
		client.close();
		String[] players = client.readFile("data/Players.txt");
		String tmpS= scores[0];
		for(int i = 0; i <length; i++){
			if(players[i].equals(userName)){
				System.out.println(scores[i]);
				tmpS = scores[i];
				String [] tmpA = tmpS.split(" ");
				tmpS = tmpA[1];	
				//if the person is the leader then they need the role card to be a red team member.
				if(tmpS.equals(new String("Secret"))){
					tmpS = "Red";
				}
			}	
		}
		return tmpS;
	}
	
	/**This action listener sends the whole game into the next stage of the game.
	 *If one person selects the submit button then all screens will change.
	 */
	private class nextScreenButton implements ActionListener{
		public void  actionPerformed(ActionEvent event){
			String tmp = getRole(userName);
			client.openToWrite("data/leaveStarting.txt");
			client.writeToFile("#Whether all screens should go to the nextScreen");
			client.writeToFile("true");
			client.close();
			
			//the next state of the game
			client.openToWrite("data/state.txt");
			client.writeToFile("SELECTION");
			client.close();
		}
	}
	
	/**
	 * If the button is clicked then the next screen is drawn up; for waiting.
	 */
	private class submitButtonAction implements ActionListener{
	
		public void  actionPerformed(ActionEvent event){
			//this will put the game to the waiting screen
			String tmpString = field.getText();
			
	
			//to make sure all of the inputs are correct.
			if(tmpString != null && !tmpString.isEmpty()){ //&& isRepeat(tmpString) == false will go here when the real game is ran
				userName = tmpString;
				//make the person put a name!
				slPanel.removeAll();
				//makes the new screen appear
				waitingScreen = true;
				
				//outputs the user information to a file
				String[] scores = client.readFile("data/Players.txt");
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
				//activates the waiting for all users to join screen.
				waitingScreen();
			}
		}
	}
	
	/**
	 * 
	 * @param name, the string entered by the user
	 * @return true if the name has been used and false if it has not been used
	 */
	public boolean isRepeat(String name){
		String[] checkPlayers = client.readFile("data/Players.txt");
		for(int k = 0; k < checkPlayers.length; k++){
			if(checkPlayers[0].equals(name)){
				//dialog box?
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Sets the roles of the players in the file
	 */
	public void setRoles(){
		TCPClient getFile = new TCPClient();
		String[] scores = new String[getFile.getLength("data/Players.txt")];
		int length = getFile.getLength("data/Players.txt");
		scores = getFile.readFile("data/Players.txt");
		getFile.openToWrite("data/Roles.txt");
		getFile.writeToFile("#Player Names");
		for(int i =0; i < length; i++){
			String fileString = scores[i];
			if(i == 1){
				fileString += " Secret Leader";
				getFile.writeToFile(fileString);
			}
			else if(i%2 == 0){
				fileString += " Blue Team";
				getFile.writeToFile(fileString);
			}
			else if(i % 2 == 1){
				fileString += " Red Team";
				getFile.writeToFile(fileString);
			}
			else{
				assert(false);
				System.out.println("Should never get here");
			}
		}
		getFile.close();
	}

	/**
	 * @return the userName of the user
	 */
	public String getUserName() {
		return userName;
	}
}
