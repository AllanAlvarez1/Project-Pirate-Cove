package inputs;

import java.awt.event.KeyEvent;

import java.awt.event.KeyListener;

import gamestates.Gamestate;
import main.Game;
import main.GamePanel;
import static utilz.Constants.Directions.*;
public class KeyboardInputs implements KeyListener {
	private GamePanel gamePanel;

	
	public KeyboardInputs(GamePanel gamePanel) { //connects to the game panel class
		this.gamePanel = gamePanel;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(Gamestate.state) {
		case MENU:
			gamePanel.getGame().getMenu().keyPressed(e);
			break;
		case PLAYING:
			gamePanel.getGame().getPlaying().keyPressed(e);
			break;
		default:
			break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(Gamestate.state) {
		case MENU:
			gamePanel.getGame().getMenu().keyReleased(e);
			break;
		case PLAYING:
			gamePanel.getGame().getPlaying().keyReleased(e);
			break;
		default:
			break;
		}
			
		}	
		
			
		
	

	@Override
	public void keyTyped(KeyEvent e) {}

}
