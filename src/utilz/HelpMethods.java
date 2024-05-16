package utilz;

import static utilz.Constants.EnemyConstants.CRABBY;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Crabby;
import main.Game;
import objects.GameContainer;
import objects.Potion;
//import static utilz.Constants.ObjectConstants.*;
public class HelpMethods {
	//this class is extremely useful for storing methods that will be used often

	//this method checks each direction to make sure that the entity is able to move.
	//Checks for if there is a free space anywhere and if there is then they can move into that tile on the level.
	public static boolean CanMoveHere(float x, float y, float width, float height, int[][] levelData) {
		
		if (!IsSolid(x, y, levelData))
			if (!IsSolid(x + width, y + height, levelData))
				if (!IsSolid(x + width, y, levelData))
					if (!IsSolid(x, y + height, levelData))
						return true;
		return false;
	}
	
	
		//this method checks each tile in a level to determine if they are solid
	private static boolean IsSolid(float x, float y, int[][] levelData) {
		int maxWidth = levelData[0].length * Game.TILES_SIZE; //gets the width of the level
		if(x<0 || x >= maxWidth) //if x is outside of the left and right border of the level
			return true;
		if(y < 0 || y >= Game.GAME_HEIGHT) //if y is above or below their respective borders
			return true;
		
		float xIndex = x / Game.TILES_SIZE; //gets the specific tile on the x plane
		float yIndex = y / Game.TILES_SIZE; //gets the specific tile on the y plane
		
		return isTileSolid((int) xIndex, (int) yIndex, levelData); //return the results
		
		
	}
	
	// there are 48 tiles and number 11 is transparent so this checks to make sure //it is within the range of tiles
	public static boolean isTileSolid(int xTile, int yTile, int[][] levelData) {
		
int value = levelData[yTile][xTile];
		
		if(value >= 48 || value < 0 || value != 11) //this makes sure the tiles that either don't exist or are transparent are registered as not solid
													
			return true;
		return false;
		
	}
	
	public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
		int currentTile = (int)(hitbox.x / Game.TILES_SIZE); //the current tile the player is on
		if( xSpeed > 0) {
			//right
			int tileXPos = currentTile * Game.TILES_SIZE; //pixel value for current tile
			int xOffset = (int)(Game.TILES_SIZE - hitbox.width); //size of the player compared to the player
			return tileXPos + xOffset - 1;
		}else {
			//left
			return currentTile * Game.TILES_SIZE;
		}
	}
	
	public static float GetEntityYPosUnderRoofOrAboveFloor( Rectangle2D.Float hitbox, float airSpeed) {
		int currentTile = (int)(hitbox.y / Game.TILES_SIZE); //the current tile the player is on
		if(airSpeed > 0) {
			//Falling, touching floor
			int tileYPos = currentTile * Game.TILES_SIZE; //pixel value for current tile
			int yOffset = (int)(Game.TILES_SIZE - hitbox.height); //size of the player compared to the tile
			return tileYPos + yOffset - 1;
		} else {
			//Jumping
			return currentTile * Game.TILES_SIZE;
		}
	}
	
	//Check the pixel below the bottom left and bottom right corners of the entity
	public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int [][] levelData) {
		if(!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, levelData)) 
			if(!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, levelData))
				return false;
		return true;
	}
	
	//checks if a tile is solid then declares it a floor if it is, otherwise it is in the air
	public static boolean isFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] levelData) {
		if( xSpeed > 0)
			return IsSolid(hitbox.x + hitbox.width + xSpeed, hitbox.y + hitbox.height + 1, levelData);
		else
			return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, levelData);
	}
	
	//not all tiles are walkable such as the background so this makes sure those are not in the physical space
	public static boolean IsAllTileWalkable(int xStart, int xEnd, int y, int [][] levelData) {
		for(int i = 0; i < xEnd - xStart; i++) {
			if(isTileSolid(xStart + i , y, levelData))
				return false;
			if(!isTileSolid(xStart + i, y + i , levelData))
				return false;
		}
		return true;
	}
	
	//this method determines if the enemy can see the player on a walkable tile on their right or left side
	public static boolean IsSightClear(int [][] levelData, Rectangle2D.Float firstHitbox, Rectangle2D.Float 
			secondHitBox, int yTile) {
		int firstXTile = (int) (firstHitbox.x / Game.TILES_SIZE);
		int secondXTile = (int) (secondHitBox.x / Game.TILES_SIZE);
		
		if(firstXTile > secondXTile) 
			IsAllTileWalkable(secondXTile, firstXTile, yTile, levelData);
		 else 
			IsAllTileWalkable(firstXTile, secondXTile, yTile, levelData);

		return true;
	}
	
	//this method gets the level data and is extremely important for making a level using the color red
	//the color red ranging from values of 0 to 47 can be used to make the level using an image manipulator.
	//the value of green and blue do not matter so if the red value is set to 11 then that tile is transparent.
	public static int[][] GetLevelData(BufferedImage img){
		int[][] levelData = new int[img.getHeight()] [img.getWidth()];
		for(int j = 0; j < img.getHeight(); j++)
			for(int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getRed();
				if(value >= 48)
					value = 0;
				levelData[j][i] = value;
			}
		return levelData;
	}
	
	//like the get level data method, this method uses the color green to place the Crab enemy.
	//if the green is set to 0 then that is where a crab will be placed
public static ArrayList<Crabby> GetCrabs(BufferedImage img){
		ArrayList<Crabby> list = new ArrayList<>();
		
		for(int j = 0; j < img.getHeight(); j++)
			for(int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if(value == CRABBY)
					list.add(new Crabby(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
			}
		return list;
	}

//like the previous two methods, this one uses the color green to place the player
//but only if the value of green is set to 100. This will become the new spawn point for the player
public static Point GetPlayerSpawn(BufferedImage img) {
	for(int j = 0; j < img.getHeight(); j++)
		for(int i = 0; i < img.getWidth(); i++) {
			Color color = new Color(img.getRGB(i, j));
			int value = color.getGreen();
			if(value == 100)
				return new Point(i * Game.TILES_SIZE, j * Game.TILES_SIZE);
		}
	return new Point(1 * Game.TILES_SIZE, 1 * Game.TILES_SIZE);
}


//these methods will be included in the future when work is continued after the semester ends


//public static ArrayList<Potion> GetPotions(BufferedImage img){
//	ArrayList<Potion> list = new ArrayList<>();
//	
//	for(int j = 0; j < img.getHeight(); j++)
//		for(int i = 0; i < img.getWidth(); i++) {
//			Color color = new Color(img.getRGB(i, j));
//			int value = color.getBlue();
//			if(value == RED_POTION || value == BLUE_POTION)
//				list.add(new Potion(i * Game.TILES_SIZE, j * Game.TILES_SIZE, value));
//		
//
//				
//		}
//	return list;
//}
//
//public static ArrayList<GameContainer> GetContainer(BufferedImage img){
//	ArrayList<GameContainer> list = new ArrayList<>();
//	
//	for(int j = 0; j < img.getHeight(); j++)
//		for(int i = 0; i < img.getWidth(); i++) {
//			Color color = new Color(img.getRGB(i, j));
//			int value = color.getBlue();
//			if(value == BOX || value == BARREL)
//				list.add(new GameContainer(i * Game.TILES_SIZE, j * Game.TILES_SIZE, value));
//		
//
//				
//		}
//	return list;
//}

}
