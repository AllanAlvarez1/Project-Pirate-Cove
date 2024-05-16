package entities;
import static utilz.HelpMethods.*;
import static utilz.Constants.*;

import java.awt.geom.Rectangle2D;

import static utilz.Constants.Directions.*;
import main.Game;

import static utilz.Constants.EnemyConstants.*;
public abstract class Enemy extends Entity {
	
	protected int enemyType;
	protected boolean firstUpdate = true;
	protected int walkDirection = LEFT;
	protected int tileY;
	protected float attackDistance = Game.TILES_SIZE;
	protected boolean active = true;
	protected boolean attackChecked;
	
	public Enemy(float x, float y, int width, int height, int enemyType) {
		super(x, y, width, height);
		this.enemyType = enemyType;
		maxHealth = GetMaxHealth(enemyType);
		currentHealth = maxHealth;
		walkSpeed = Game.SCALE * 0.35f;
	}
	
	//updates the animation to go through the whole index and when it does switch back to 0
	protected void updateAnimationTick() {
		animationTick++;
		if(animationTick >= ANI_SPEED) {
			animationTick = 0;
			animationIndex++;
			if(animationIndex >= GetSpriteAmount(enemyType, state)) {
				animationIndex = 0;
				//after an attack or being hit the enemy goes back to being idle
				switch(state) {
				case ATTACK, HIT -> state = IDLE;
				//the enemy is longer considered active or alive
				case DEAD -> active = false;
				}
			
			}
		}
	}
	
	//if the enemy is not on the floor then it is in the air
	protected void firstUpdateCheck(int [][] levelData) {
		if(!IsEntityOnFloor(hitbox, levelData)) 
			inAir = true;
		firstUpdate = false;
	}
	//if the enemy is in the then they will be pulled down by gravity until they are on a walkable tile
	protected void updateInAir(int[][] levelData) {
		if(inAir) {
			if(CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, levelData)) {
				hitbox.y += airSpeed;
				airSpeed += GRAVITY;
			} else {
				inAir = false;
				hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
				tileY = (int)(hitbox.y / Game.TILES_SIZE);
			}
		}
	}
	
	//this method makes it so the enemy can move left until it reaches a spot where it cannot keep moving or 
	//where there is a drop, then it will move right and vice versa
	protected void move(int [][] levelData) {
			float xSpeed = 0;
			
			if(walkDirection == LEFT)
				xSpeed = -walkSpeed;
			else
				xSpeed = walkSpeed;
			
			if(CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData))
				if(isFloor(hitbox, xSpeed, levelData)) {
					hitbox.x += xSpeed;
					return;
				}
			changeWalkDirection();
		}
	
	
	//this method holds the enemy state and resets the animation index and tick back to 0 to cycle through it again
	protected void newState(int enemyState) {
		this.state = enemyState;
		animationTick = 0;
		animationIndex = 0;
	}
	
	//this method checks if the enemy can see the player and if there is a tile obstructing the view of the player
	protected boolean canSeePlayer(int [][] levelData, Player player) {
		int playerTileY = (int)(player.getHitBox().y / Game.TILES_SIZE);
		if( playerTileY == tileY)
			if(isPlayerInRange(player)) {
				if(IsSightClear(levelData, hitbox, player.hitbox, tileY))
					return true;
			}
		return false;
	}
	
	//if the player hitbox is to the right of the enemy hitbox then turn right and vice versa if the player hitbox is to the left
	protected void turnTowardsPlayer(Player player) {
		if(player.hitbox.x > hitbox.x)
			walkDirection = RIGHT;
		else
			walkDirection = LEFT;
	}
	
	//this method resets the enemy back to their starting position, starting health, being active, and back to being idle
	public void resetEnemy() {
		hitbox .x = x;
		hitbox.y = y;
		firstUpdate = true;
		currentHealth = maxHealth;
		newState(IDLE);
		active = true;
		airSpeed = 0;
	}
	
	//checks if the enemy attackbox overlaps the player hitbox and if so, subtract from the player's health based on the enemy damage
	//stored in the constants class.
	protected void checkEnemyHit(Rectangle2D.Float attackBox ,Player player) {
		if(attackBox.intersects(player.hitbox)) {
			player.changeHealth(-GetEnemyDamage(enemyType));
		}
		attackChecked = true;
	}
	
	//checks if the player is close enough to be attacked compared to the enemy hitbox
	protected boolean isPlayerCloseForAttack(Player player) {
		int absoluteValue = (int) Math.abs(player.hitbox.x - hitbox.x);
		return absoluteValue <= attackDistance;
	}
	
	//updates the health of enemy and if it reaches 0 then it is considered dead,
	// otherwise it is in the hit state
	public void hurt(int amount) {
		currentHealth -= amount;
		if(currentHealth <= 0)
			newState(DEAD);
		else
			newState(HIT);
	}
	
	//checks if the player is in range of the enemy by their hitboxes
	protected boolean isPlayerInRange(Player player) {
		int absoluteValue = (int) Math.abs(player.hitbox.x - hitbox.x);
		return absoluteValue <= attackDistance * 5;
	}

	public void changeWalkDirection() {
		if(walkDirection == LEFT)
			walkDirection = RIGHT;
		else
			walkDirection = LEFT;
	}


	
	
	
	public boolean isActive() {
		return active;
	}

}
