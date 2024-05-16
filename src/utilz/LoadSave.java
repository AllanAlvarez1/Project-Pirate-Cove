package utilz;

import java.awt.Color;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entities.Crabby;
import main.Game;
import static utilz.Constants.EnemyConstants.CRABBY;;
public class LoadSave { //this class holds the images for the game and can be accessed to bring them to the screen
						//and it gets sprite and level data 
	
	//these values hold the images from the resource folder. They can be used to access the images.
	public static final String PLAYER_ATLAS = "player_sprites.png";
	public static final String LEVEL_ATLAS = "outside_sprites.png";
	public static final String MENU_BUTTONS = "button_atlas.png";
	public static final String MENU_BACKGROUND = "menu_background.png";
	public static final String MENU_BACKGROUND_IMG = "background.png";
	public static final String PLAYING_BACKGROUND_IMG = "playing_bg_img.png";
	public static final String BIG_CLOUDS_IMG = "big_clouds.png";
	public static final String SMALL_CLOUDS_IMG = "small_clouds.png";
	public static final String PAUSE_BACKGROUND = "pause_menu.png";
	public static final String SOUND_BUTTONS = "sound_button.png";
	public static final String URM_BUTTONS = "urm_buttons.png";
	public static final String VOLUME_BUTTONS = "volume_buttons.png";
	public static final String CRABBY_SPRITE = "crabby_sprite.png";
	public static final String STATUS_BAR = "health_power_bar.png";
	public static final String LEVEL_COMPLETED = "completed_sprite.png";
	public static final String POTIONS_ATLAS = "potions_sprites.png";
	public static final String CONTAINER_ATLAS = "objects_sprites.png";

	
	//this method gets the sprites and images by inputting the values from above
	
	public static BufferedImage GetSpriteAtlas(String fileName) {
		BufferedImage img = null;
		InputStream is = LoadSave.class.getResourceAsStream("/" + fileName); 
		try {
		img = ImageIO.read(is);
		
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close(); //closes the input stream
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		return img;
}
	
	//this method searches through the lvls folder in resources
	//and sorts them in ascending numerical order.
	//It then goes through them one by one and returns the image
	//so that it can be used to show the level
	public static BufferedImage[] GetAllLevels() {
		URL url = LoadSave.class.getResource("/lvls");
		File file = null;
		try {
			file = new File(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		File[] files = file.listFiles();
		File[] filesSorted = new File[files.length];
		
		for(int i = 0; i < filesSorted.length; i++)
			for(int j = 0; j < files.length; j++) {
				if(files[j].getName().equals((i + 1) + ".png"))
					filesSorted[i] = files[j];
			}
		
		BufferedImage[] imgs = new BufferedImage[filesSorted.length];
		
		for(int i = 0; i < imgs.length; i++)
			try {
				imgs[i] = ImageIO.read(filesSorted[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		return imgs;
	}
	
	
	
	


}