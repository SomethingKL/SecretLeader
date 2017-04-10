package ui;


import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import ui.SLPanel;
import javax.swing.*;


public class MainMenuPanel extends JPanel{
	
	//Secret Leader Starting picture
	//
	//where the user puts their input
	private JTextField userName;
	
	private JButton submit;
	
	public MainMenuPanel(){
		initializePanel();
	}
	
	public void initializePanel(){
		submit = new JButton("Submit");
		add(submit);
	}
}
