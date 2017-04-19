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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import ui.SLPanel;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

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
	private JButton submit;
	//the name of the user for the game
	private String userName = "User";
	//where the screen should be at
	private boolean waitingScreen = false;
	//whether the game can go to the next state or not
	private boolean nextScreen = false;
	
	public MainMenu(SLPanel slPanelIn){
		//Have to set the file to false to begin with; otherwise everyone will go to the wrong screen to start
		//set the file
		//set the files
		initGameFiles();
		
		
		
		client.readFile("data/Board.txt");
		System.out.println(client.getLength("data/Board.txt"));
		
		client.openToWrite("data/leaveStarting.txt");
		client.writeToFile("#Whether all screens should go to the nextScreen");
		client.writeToFile("false");
		client.close();
		
		slPanel = slPanelIn;
		slPanel.setLayout(null);
		setTextArea();
		repaint = true;
	}
	
	/**
	 * Resets all of the files for the game
	 */
	public void initGameFiles(){
		int blue = 0;
		int red = 0;
		client.openToWrite("data/Board.txt");
		client.writeToFile("#number of Blue victories");
		client.writeToFile(String.valueOf(blue));
		//client.writeToFile(Integer.toString(blue));
		client.writeToFile("#number of Red victories");
		client.writeToFile(String.valueOf(red));
		client.close();
		
		client.openToWrite("data/Roles.txt");
		client.writeToFile("");
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
		submit = new JButton("Submit");
		
	
		//need to set the bounds of the button to add it.
		field.setBounds(450,300,300,50);
		field.setPreferredSize(new Dimension(300,50));
		Font font = new Font("Copperplate Gothic Bold", Font.ITALIC,25);
		field.setFont(font);
		field.setBackground(new Color(100, 100, 100));
		
		//userNameLabel settings
		userNameLabel.setFont(font);
		userNameLabel.setPreferredSize(new Dimension(400,50));
		userNameLabel.setBounds(410,250,400,50);
		userNameLabel.setForeground(new Color(200,20,20));
		
		//submit button settings
		submit.setFont(font);
		submit.setPreferredSize(new Dimension(400,50));
		submit.setBounds(400,400,400,50);
		submit.addActionListener(new submitButtonAction());
		submit.setBackground(new Color(200,20,20));
		//submit.addMouseListener(new colorChange(submit,new Color(0,0,0)));

		
		//adding the pieces to the panel
		slPanel.add(submit);
		slPanel.add(userNameLabel);
		slPanel.add(field);
	}
	
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
		
		//the amount of users that are able to go into the game
		//**NOte: if over 12 the game will break
		String[] scores = new String[client.getLength("data/Players.txt")];
		try{
			scores = client.readFile("data/Players.txt");
		}
		catch(Exception e){
			System.out.println("THe file was not read in correctly");
		}
		
		//System.out.println(scores.length);
		JLabel waitingLabel = new JLabel("Waiting for All Users to Join...");
		
		//settings for the waitingLabel
		Font font = new Font("Copperplate Gothic Bold", Font.ITALIC,25);
		waitingLabel.setFont(font);
		waitingLabel.setPreferredSize(new Dimension(600,50));
		waitingLabel.setBounds(375,250,600,50);
		waitingLabel.setForeground(new Color(200,20,20));
		
		
		if (scores == null || scores.length == 0){
			//just wanted to make sure the scores didn't go haywire
		}
		else if(scores.length >= 4){
			
			JButton startGame = new JButton("Click here to start!");
			startGame.setFont(font);
			startGame.setPreferredSize(new Dimension(400,50));
			startGame.setBounds(400,400,400,50);
			startGame.addActionListener(new nextScreenButton());
			startGame.setBackground(new Color(200,20,20));
			//startGame.addActionListener(event -> nextScreen = true);
			
			slPanel.add(startGame);
			//submit.addActionListener(new submitButtonAction());
		}
		
		slPanel.add(waitingLabel);
		slPanel.revalidate();
		slPanel.repaint();
	}
	/**
	 * 
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
				
				 if(tmpS.equals(new String("Red"))){
					
					String redString = "";
					for(int j = 1; j < length; j+=2){
						System.out.println(scores[j]);
						redString += scores[j];
						redString+= "\n ";
					}
					JOptionPane.showMessageDialog(this,"You are on the Red Team! Your teammates are: \n" + redString,"Role Message", JOptionPane.PLAIN_MESSAGE);
				 }
						
				else if(tmpS.equals(new String("Secret"))){
					//JOptionPane.showMessageDialog(null, "You are the Secret Leader for the red team! Shhh!");
					tmpS = "Red";
					JOptionPane.showMessageDialog(this,"You are the Secret Leader for the red team! Shhh!","Role Message", JOptionPane.PLAIN_MESSAGE);
				}
			}	

		}
		return tmpS;
	}
	private class nextScreenButton implements ActionListener{
		public void  actionPerformed(ActionEvent event){
			String tmp = getRole(userName);
			client.openToWrite("data/leaveStarting.txt");
			client.writeToFile("#Whether all screens should go to the nextScreen");
			client.writeToFile("true");
			client.close();
			
			client.openToWrite("data/state.txt");
			client.writeToFile("VOTING");
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
			
			//if there are '\n's the game will break. So, I take them out.
			tmpString = tmpString.replace('\n',' ');
			tmpString = tmpString.replaceAll("\\s", "");
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

	public void click(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
