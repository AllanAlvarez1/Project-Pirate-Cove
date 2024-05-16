package levels;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Crabby;
import main.Game;
import objects.GameContainer;
import objects.Potion;
import utilz.HelpMethods;
import utilz.LoadSave;
import static utilz.HelpMethods.GetLevelData;
import static utilz.HelpMethods.GetCrabs;
import static utilz.HelpMethods.GetPlayerSpawn;


public class Level {
	private int[][] levelData;
	private BufferedImage img;
	private ArrayList <Crabby> crabs;
//	private ArrayList<Potion> potions;
//	private ArrayList<GameContainer> containers;

	private int levelTilesWide;
	private int maxTilesOffset;
	private int maxLevelOffsetX;
	private Point playerSpawn;
	
	public Level(BufferedImage img) {
		this.img = img;
		createLevelData();
		createEnemies();
//		createPotions();
//		createContainers();
		calcLevelOffset();
		calcPlayerSpawn();
	}
//	
//	private void createContainers() {
//		containers = HelpMethods.GetContainer(img);
//	}
//
//	private void createPotions() {
//		potions = HelpMethods.GetPotions(img);
//	}

	private void calcPlayerSpawn() {
		playerSpawn = GetPlayerSpawn(img);
	}

	private void calcLevelOffset() {
		levelTilesWide = img.getWidth();
		maxTilesOffset = levelTilesWide - Game.TILES_IN_WIDTH;
		maxLevelOffsetX = Game.TILES_SIZE * maxTilesOffset;
	}

	private void createEnemies() {
		crabs = GetCrabs(img);
	}

	private void createLevelData() {
		levelData = GetLevelData(img);
	}

	public int getSpriteIndex(int x, int y) {
		return levelData[y][x];
	}
	
	public int[][] getLevelData(){
		return levelData;
	}
	
	public int getLevelOffset() {
		return maxLevelOffsetX;
	}
	public ArrayList<Crabby> getCrabs(){
		return crabs;
	}
	
	public Point getPlayerSpawn() {
		return playerSpawn;
	}
	
//	public ArrayList<Potion> getPotions(){
//		return potions;
//	}
//	public ArrayList<GameContainer> getContainers(){
//		return containers;
//	}

}
