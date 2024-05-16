package ui;



import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static utilz.Constants.VolumeButtons.*;
import utilz.LoadSave;

public class VolumeButton extends PauseButtons {

	//class for the volume bar and slider
	
	private BufferedImage[] imgs;
	private BufferedImage slider;
	private int index = 0;
	private boolean mouseOver, mousePressed;
	private int buttonX, minX, maxX;
	
	
	public VolumeButton(int x, int y, int width, int height) {
		super(x + width / 2, y, VOLUME_WIDTH, VOLUME_HEIGHT);
		bounds.x -= VOLUME_WIDTH / 2;
		buttonX = x + width / 2;
		this.x = x;
		this.width = width;
		minX = x + VOLUME_WIDTH / 2;
		maxX = x + width - VOLUME_WIDTH / 2;
		loadImgs();
	}
	
	//loads the bar and the slider while setting their height and width
	private void loadImgs() {
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.VOLUME_BUTTONS);
		
		imgs = new BufferedImage[3];
		for(int i = 0; i < imgs.length; i++)
			imgs[i] = temp.getSubimage(i * VOLUME_DEFAULT_WIDTH, 0, VOLUME_DEFAULT_WIDTH, VOLUME_DEFAULT_HEIGHT);
		
		slider = temp.getSubimage(3 * VOLUME_DEFAULT_WIDTH, 0, SLIDER_DEFAULT_WIDTH, VOLUME_DEFAULT_HEIGHT);
		
	}
	
	//draw the bar and the slider
	public void draw(Graphics pen) {
		pen.drawImage(slider, x, y, width, height, null);
		pen.drawImage(imgs[index], buttonX - VOLUME_WIDTH / 2, y, VOLUME_WIDTH, height,null);
	}
	
	//change the button over the slider but make sure it does not go out of bounds
	public void changeX(int x) {
		if(x < minX)
			buttonX = minX;
		else if(x > maxX)
			buttonX = maxX;
		else
			buttonX = x;
		
		bounds.x = buttonX / 2;
	}
	
	//update the bar and slider to go through their sprite indexes if hovered over or pressed
	public void update() {
		index = 0;
		if(mouseOver)
			index = 1;
		if(mousePressed)
			index = 2;
	}
	
	public void resetBools() {
		mouseOver = false;
		mousePressed = false;
	}

	public boolean isMouseOver() {
		return mouseOver;
	}

	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}

	public boolean isMousePressed() {
		return mousePressed;
	}

	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}

}
