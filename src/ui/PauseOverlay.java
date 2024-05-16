package ui;

import java.awt.Graphics;
import static utilz.Constants.URMButtons.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;
import static utilz.Constants.UI.PauseButtons.*;
import static utilz.Constants.VolumeButtons.*;
public class PauseOverlay {
	
	//this class handles the pause overlay which is pretty much what you see in the PAUSED gamestate
	
	private Playing playing;
	private BufferedImage backgroundImg;
	private int bgX, bgY, bgW, bgH;
	private SoundButtons musicButton, sfxButton;
	private UrmButton menuB, replayB, unpauseB;
	private VolumeButton volumeButton;
	
	public PauseOverlay( Playing playing) {
		this.playing = playing;
		loadBackground();
		createSoundButtons();
		createUrmButtons();
		createVolumeButton();
	}
	
	//creates the bar and slider in the pause menu
	private void createVolumeButton() {
		int vX = (int)(309 * Game.SCALE);
		int vY = (int) (278 * Game.SCALE);
		volumeButton = new VolumeButton(vX, vY, SLIDER_WIDTH, VOLUME_HEIGHT);
	}
	
	//creates the menu button, replay button, and unpause button in the menu as well as their y positions
	private void createUrmButtons() {
		int menuX = (int)(313 * Game.SCALE);
		int replayX = (int)(387 * Game.SCALE);
		int unpauseX = (int)(462 * Game.SCALE);
		int by = (int)(325*Game.SCALE);
		
		menuB = new UrmButton(menuX, by, URM_SIZE, URM_SIZE, 2);
		replayB = new UrmButton(replayX, by, URM_SIZE, URM_SIZE, 1);
		unpauseB = new UrmButton(unpauseX, by, URM_SIZE, URM_SIZE, 0);

	}
	
	//creates the sound and music buttons on the pause screen
	private void createSoundButtons() {
		int soundX = (int)(450 * Game.SCALE);
		int musicY = (int)(140*Game.SCALE);
		int sfxY = (int)(186 * Game.SCALE);
		musicButton = new SoundButtons(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
		sfxButton = new SoundButtons(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);

		
		
	}
	//loads the pause background
	private void loadBackground() {
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
		bgW = (int)(backgroundImg.getWidth() * Game.SCALE);
		bgH = (int)(backgroundImg.getHeight() * Game.SCALE);
		bgX = Game.GAME_WIDTH / 2 - bgW / 2;
		bgY = (int)(25 * Game.SCALE);

	}
	
	//updates the pause screen buttons
	public void update() {
		//sound buttons
		musicButton.update();
		sfxButton.update();
		//UrmButtons
		menuB.update();
		replayB.update();
		unpauseB.update();
		
		volumeButton.update();
	}
	
	//draws the pause screen's background and buttons
	public void draw(Graphics pen) {
		//Background
		pen.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);
		
		//Sound buttons
		musicButton.draw(pen);
		sfxButton.draw(pen);
		
		//UrmButtons
		menuB.draw(pen);
		replayB.draw(pen);
		unpauseB.draw(pen);
		
		volumeButton.draw(pen);
	}
	
	//mouse events for each button are below
	
	
	public void mousePressed(MouseEvent e) {
	
		if(isIn(e, musicButton))
			musicButton.setMousePressed(true);
		else if(isIn(e,sfxButton))
			sfxButton.setMousePressed(true);
		else if(isIn(e,menuB))
			menuB.setMousePressed(true);
		else if(isIn(e,replayB))
			replayB.setMousePressed(true);
		else if(isIn(e,unpauseB))
			unpauseB.setMousePressed(true);
		else if(isIn(e,volumeButton))
			volumeButton.setMousePressed(true);
	}

	
	
	public void mouseMoved(MouseEvent e) {
		
		musicButton.setMouseOver(false);
		sfxButton.setMouseOver(false);
		menuB.setMouseOver(false);
		replayB.setMouseOver(false);
		unpauseB.setMouseOver(false);
		volumeButton.setMouseOver(false);

		if(isIn(e, musicButton))
			musicButton.setMouseOver(true);
		else if(isIn(e,sfxButton))
			sfxButton.setMouseOver(true);
		else if(isIn(e,menuB))
			menuB.setMouseOver(true);
		else if(isIn(e,replayB))
			replayB.setMouseOver(true);
		else if(isIn(e,unpauseB))
			unpauseB.setMouseOver(true);
		else if(isIn(e,volumeButton))
			volumeButton.setMouseOver(true);
	}
	
	
	public void mouseReleased(MouseEvent e) {
		if(isIn(e, musicButton)) {
			if(musicButton.isMousePressed()) {
				musicButton.setMuted(!musicButton.isMuted());
			}
		}
		else if(isIn(e,sfxButton)) {
			if(sfxButton.isMousePressed()) 
				sfxButton.setMuted(!sfxButton.isMuted());
			}
		else if(isIn(e,menuB)) {
			if(menuB.isMousePressed()) {
				Gamestate.state = Gamestate.MENU;
				playing.unpauseGame();
				}
		}else if(isIn(e,replayB)) {
				if(replayB.isMousePressed()) {
					playing.resetAll();
					playing.unpauseGame();
				}
		}else if(isIn(e,unpauseB))
					if(unpauseB.isMousePressed()) {
						playing.unpauseGame();
					}
		musicButton.resetBools();
		sfxButton.resetBools();
		menuB.resetBools();
		replayB.resetBools();
		unpauseB.resetBools();
		volumeButton.resetBools();
	}
	
	
public void mouseDragged(MouseEvent e) {
		if(volumeButton.isMousePressed()) {
			volumeButton.changeX(e.getX());
		}
		
		
	}
private boolean isIn(MouseEvent e, PauseButtons b) {
	return(b.getBounds().contains(e.getX(),e.getY()));
		
}

}
