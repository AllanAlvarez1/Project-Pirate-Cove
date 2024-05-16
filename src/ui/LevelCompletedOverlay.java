package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;
import static utilz.Constants.URMButtons.*;

public class LevelCompletedOverlay {

	private Playing playing;
	private UrmButton menu, next;
	private BufferedImage img;
	private int backgroundX, backgroundY, backgroundWidth, backgroundHeight;
	
	public LevelCompletedOverlay(Playing playing) {
		this.playing = playing;
		initImg();
		initButtons();
		
	}

	//Level Completed buttons are initialized here
	private void initButtons() {
		int menuX = (int)(330 * Game.SCALE);
		int nextX = (int) (445 * Game.SCALE);
		int y = (int)(195 * Game.SCALE);
		next = new UrmButton(nextX, y, URM_SIZE, URM_SIZE, 0);
		menu = new UrmButton(menuX, y, URM_SIZE, URM_SIZE, 2);
	}

	//image for level completed is gotten here as well as the size of it
	private void initImg() {
		img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_COMPLETED);
		backgroundWidth = (int) (img.getWidth() * Game.SCALE);
		backgroundHeight = (int) (img.getHeight() * Game.SCALE);
		backgroundX = Game.GAME_WIDTH / 2 - backgroundWidth / 2;
		backgroundY = (int) (75 * Game.SCALE);

	}
	//update the next level and menu button methods
	public void update() {
		next.update();
		menu.update();
	}
	//draw the background for the level completed and the next level and menu buttons
	public void draw(Graphics pen) {
		pen.drawImage(img, backgroundX, backgroundY, backgroundWidth, backgroundHeight, null);
		next.draw(pen);
		menu.draw(pen);
	}
	
	private boolean isIn(UrmButton button, MouseEvent e) {
		return button.getBounds().contains(e.getX(),e.getY());
		
	}
	
	public void mouseMoved(MouseEvent e) {
		next.setMouseOver(false);
		menu.setMouseOver(false);
		
		if(isIn(menu, e))
			menu.setMouseOver(true);
		else if(isIn(next, e))
			next.setMouseOver(true);
	}
	
	//if you press menu the gamestate is set to menu and if you pick next level then the next level is loaded from playing
	public void mouseReleased(MouseEvent e) {
		
		if(isIn(menu, e)) {
			if(menu.isMousePressed())
				playing.resetAll();
				Gamestate.state = Gamestate.MENU;
		}else if(isIn(next, e))
			if(next.isMousePressed());
				playing.loadNextLevel();
				
				menu.resetBools();
				next.resetBools();
	}
	
	public void mousePressed(MouseEvent e) {
		if(isIn(menu, e))
			menu.setMousePressed(true);
		else if(isIn(next, e))
			next.setMousePressed(true);
		
	
	}
}
