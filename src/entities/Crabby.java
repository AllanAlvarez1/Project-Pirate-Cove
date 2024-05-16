package entities;
import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.*;
import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.CanMoveHere;
import static utilz.HelpMethods.GetEntityYPosUnderRoofOrAboveFloor;
import static utilz.HelpMethods.IsEntityOnFloor;
import static utilz.HelpMethods.isFloor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;
public class Crabby extends Enemy {
	
	//this class is all about the crab enemy
	
	private int attackBoxOffsetX;

	public Crabby(float x, float y) {
		super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
		initHitbox(22 ,19 ); //22 pixels wide 19 pixels high
		initAttackBox();
	}
	
	//this method is for the attackbox of the crab. It is quite wide considering the arms stretch out
	private void initAttackBox() { 
		attackBox = new Rectangle2D.Float(x, y, (int)(82 * Game.SCALE),(int)(19 * Game.SCALE));
		attackBoxOffsetX = (int)(Game.SCALE * 30);
	}

	//this is the method for the ai of the crab.
	//if the crab sees the player it will turn to it and get close enough to attack
	// and then attack the player. If there is no player detected then it will just move around from left to right
	private void updateBehavior(int [][] levelData, Player player) {
		if(firstUpdate) 
			firstUpdateCheck(levelData);
		
		
		if(inAir) 
			updateInAir(levelData);
		
			 else {
			switch(state) {
			case IDLE:
				newState(RUNNING);
				break;
			case RUNNING:
				
				if(canSeePlayer(levelData, player)) {
					turnTowardsPlayer(player);
				if(isPlayerCloseForAttack(player))
					newState(ATTACK);
				}
				move(levelData);
				break;
			case ATTACK:
				
				if(animationIndex == 0)
					attackChecked = false;
				//once it reaches the final attacking sprite then check if the crab hit the player
				if(animationIndex == 3 && !attackChecked)
					checkEnemyHit(attackBox, player);
				break;
			case HIT:
				break;
			}
	}
}
	
	
	//this method method flips the crab so its sprites are mirrored
	public int flipX() {
		if( walkDirection == RIGHT) {
			return width;
		}else {
			return 0;
		}
	}
	
	//this method method flips the crab so its sprites are mirrored
	public int flipW() {
		if(walkDirection == RIGHT) {
			return -1;
		} else {
			return 1;
		}
	}
	//the update for the crab
	public void update(int [][] levelData, Player player) {
		updateBehavior(levelData, player);
		updateAnimationTick();
		updateAttackBox();
	}
//updates the attackbox to be next to the crab
	private void updateAttackBox() {
		attackBox.x = hitbox.x - attackBoxOffsetX;
		attackBox.y = hitbox.y;
	}
	
	
	
	
	

}
