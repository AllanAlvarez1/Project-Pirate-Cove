package main;

import java.awt.Color;
import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;
import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.Directions.*;
public class GamePanel extends JPanel{ //allows you to draw on the screen
	
	private MouseInputs mouseInputs; //used to connect to the mouse inputs class
	private Game game;
	public GamePanel(Game game) {
		this.game = game;
		mouseInputs = new MouseInputs(this); //connects to the mouseinputs class
		setPanelSize();
		addKeyListener(new KeyboardInputs(this)); //add a key listener to the KeyBoardInputs class
		addMouseListener(new MouseInputs(this)); //add a mouse listener to the MouseInputs class
		addMouseMotionListener(mouseInputs); //add a mouse motion listener to MouseInputs
	}
	
	private void setPanelSize() { //set the window panel size to a fixed size
		Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
		setPreferredSize(size); //size is 1664 by 896
		System.out.println("size : " + GAME_WIDTH + " : "+ GAME_HEIGHT);
	}
	
public void updateGame() {
	
}


	
public void paintComponent(Graphics pen) {//the paintbrush
		super.paintComponent(pen); 
		game.render(pen);
	}

public Game getGame() {
	return game;
}
}
