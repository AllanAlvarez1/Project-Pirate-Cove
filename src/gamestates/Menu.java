package gamestates;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import ui.MenuButton;
import utilz.LoadSave;

public class Menu extends State implements Statemethods {

	//this class is all about the MENU gamestate
	
	private MenuButton[] buttons = new MenuButton[3]; //3 menu buttons to be held in the array
	private BufferedImage backgroundImg, backgroundImagePirate;
	private int menuX, menuY, menuWidth, menuHeight;
	
	public Menu(Game game) {
		super(game);
		loadButtons();
		loadBackground();
		backgroundImagePirate = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG); //holds the menu background image
	}

	//loads the background for the menu
	private void loadBackground() {
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
		menuWidth = (int)(backgroundImg.getWidth() * Game.SCALE);
		menuHeight = (int)(backgroundImg.getHeight() * Game.SCALE);
		menuX = Game.GAME_WIDTH / 2 - menuWidth /2;
		menuY = (int) (45 * Game.SCALE);
	}
	//loads the buttons for the menu based on the spritesheet they are and sets them to a specific gamestate
	private void loadButtons() {
		buttons[0] = new MenuButton(Game.GAME_WIDTH / 2, (int)(150 *Game.SCALE) ,0 , Gamestate.PLAYING);
		buttons[1] = new MenuButton(Game.GAME_WIDTH / 2, (int)(220 *Game.SCALE) ,1 , Gamestate.OPTIONS);
		buttons[2] = new MenuButton(Game.GAME_WIDTH / 2, (int)(290 *Game.SCALE) ,2 , Gamestate.QUIT);

	}

	//updates the menu buttons
	@Override
	public void update() {
		for(MenuButton mb : buttons)
			mb.update();
	}

	//draws the background image, menu image, and menu buttons
	@Override
	public void draw(Graphics pen) {
		pen.drawImage(backgroundImagePirate, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		
		pen.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);
		
		for(MenuButton mb : buttons)
			mb.draw(pen);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for(MenuButton mb : buttons) {
			if(isIn(e, mb)) {
				mb.setMousePressed(true);
				
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for(MenuButton mb : buttons) {
			if(isIn(e, mb)) {
				if(mb.isMousePressed())
					mb.applyGamestate();
			break;
		}
		
		}
		resetButtons();
	}

	private void resetButtons() {
		for(MenuButton mb : buttons) {
		mb.resetBools();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for(MenuButton mb : buttons) {
			mb.setMouseOver(false);
		}
		
		for(MenuButton mb : buttons) 
			if(isIn(e, mb)) {
				mb.setMouseOver(true);
				break;
		}
	}

	//pressing ENTER starts the game from the menu
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			Gamestate.state = Gamestate.PLAYING;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}
