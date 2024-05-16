package entities;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;
import static utilz.Constants.EnemyConstants.*;

public class EnemyManager {
	
	//this class manages the enemies by updating, drawing them, and loading images
	
private Playing playing;
private BufferedImage[][] crabbyArray;
private ArrayList<Crabby> crabbies = new ArrayList<>();

	public EnemyManager(Playing playing) {
		this.playing = playing;
		loadEnemyImgs();
		
	}
	
	//loads the crabbies array list into the level
	public void loadEnemies(Level level) {
		crabbies = level.getCrabs();
	}

	//updates the enemy array list such as the crabs. If any crabs are active then update the level and if not
	//then the level is completed
	public void update(int [][] levelData, Player player) {
		boolean isAnyActive = false;
		for(Crabby c : crabbies)
			if(c.isActive()) {
				c.update(levelData, player);
				isAnyActive = true;
			}
		if(!isAnyActive)
			playing.setLevelCompleted(true);
	}
	
	
	//draw the enemy
	public void draw(Graphics pen, int xLevelOffset) {
		drawCrabs(pen, xLevelOffset);
	}
	
	//draws the hitbox for the crabs and draws the crabs themselves while setting their state and what index of their animation they are in
	private void drawCrabs(Graphics pen, int xLevelOffset) {
		for(Crabby c : crabbies) {
			if(c.isActive())
				pen.drawImage(crabbyArray[c.getEnemyState()][c.getAnimationIndex()],
					(int)c.getHitBox().x - xLevelOffset - CRABBY_DRAWOFFSET_X + c.flipX(), 
					(int)c.getHitBox().y - CRABBY_DRAWOFFSET_Y,
					CRABBY_WIDTH * c.flipW(), CRABBY_HEIGHT, null);
		//c.drawAttackBox(pen, xLevelOffset);
		//c.drawHitbox(pen, xLevelOffset);
		}
	}
	
	//check if the player attackbox intersected the enemy hitbox then damage the enemy's health
	public void checkEnemyAttacked(Rectangle2D.Float attackBox) {
		for(Crabby c: crabbies)
			if(c.isActive())
				if(attackBox.intersects(c.getHitBox())) {
					c.hurt(10);
					return;
			}
	}

	//loads the images of the enemy such as crab
	private void loadEnemyImgs() {
		crabbyArray = new BufferedImage[5][9]; //5 rows 9 columns of images
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.CRABBY_SPRITE);
		for(int j = 0; j < crabbyArray.length; j++)
			for(int i = 0; i < crabbyArray[j].length; i++)
				crabbyArray[j][i] = temp.getSubimage(i * CRABBY_WIDTH_DEFAULT , j * CRABBY_HEIGHT_DEFAULT, CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFAULT);
	}

	//resets the enemies and their states
	public void resetAllEnemies() {
		for(Crabby c: crabbies)
			c.resetEnemy();
	}
}
