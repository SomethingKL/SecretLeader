package framework;

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
	
	
	public MainMenu(SLPanel slPanelIn){
		slPanel = slPanelIn;
		slPanel.setLayout(null);
		setTextArea();
		repaint = true;
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
	
	/**
	 * If the button is clicked then the next screen is drawn up; for waiting.
	 */
	private class submitButtonAction implements ActionListener{
		public synchronized void  actionPerformed(ActionEvent event){
			//this will put the game to the waiting screen
				String tmpString = field.getText();
				if(tmpString != null && !tmpString.isEmpty()){
					System.out.println("bam");
					userName = tmpString;
					//make the person put a name!
					slPanel.removeAll();
					//got to make a new screen for the user; the waiting screen.
					slPanel.revalidate();
					slPanel.repaint();
				}
				
			
			
		}
	}
	
}
