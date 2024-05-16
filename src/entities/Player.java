package entities;

import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import gamestates.Playing;
import main.Game;
import utilz.LoadSave;
import static utilz.HelpMethods.*;
public class Player extends Entity{
	
	//the class that holds the player character with data such as their health bar, images, and updates

	private BufferedImage[][] animation;
	private int Tick, Index = 15; //120 frames divided by the amount of animations 
	
	private boolean moving = false, attacking = false;
	private boolean left,  right,  jump;
	private int[][] levelData;
	private float xDrawOffset = 21 * Game.SCALE;
	private float yDrawOffset = 4 * Game.SCALE;
	//jumping and gravity
	
	
	private float jumpSpeed = -2.25f * Game.SCALE;
	private float fallSpeedAfterCollision = 0.5f *Game.SCALE; //if the player collides with a tile from below then they go down faster
	
	//status bar
	private BufferedImage statusBarImg;
	

	private int statusBarWidth = (int) (193 * Game.SCALE);
	private int statusBarHeight = (int) (58 * Game.SCALE);
	private int statusBarX = (int) (10 * Game.SCALE);
	private int statusBarY = (int) (10 * Game.SCALE);
	
	//health bar
	private int healthBarWidth = (int) (150 * Game.SCALE);
	private int healthBarHeight = (int) (4 * Game.SCALE);
	private int healthBarXStart = (int) (34 * Game.SCALE);
	private int healthBarYStart = (int) (14 * Game.SCALE);
	
	
	private int healthWidth = healthBarWidth;
	
	//Attack hitbox
	//these two values are used to flip the attack hitbox of the player when they turn
	private int flipX = 0;
	private int flipW = 1;
	private boolean attackChecked;
	Playing playing;
	
public Player(float x, float y, int width, int height, Playing playing) {
		super(x, y, width, height);
		this.playing = playing;
		this.state = IDLE; //player is by default in an idle state
		this.maxHealth = 100; //the player has 100 points of health at max health
		this.currentHealth = maxHealth; //player starts at max health
		this.walkSpeed = Game.SCALE * 1.0f; //player walk speed
		loadAnimations();
		initHitbox( 20 , 27);
		initAttackBox();
	}

//this method sets the player hitbox wherever the spawn is located
public void setSpawn(Point spawn) {
	this.x = spawn.x;
	this.y = spawn.y;
	hitbox.x = x;
	hitbox.y = y;
}
	
//initialization of the attack box 
private void initAttackBox() {
	attackBox = new Rectangle2D.Float(x, y, (int)(20 * Game.SCALE), (int)(20 * Game.SCALE));
}

//updates the player
public void update() {
	updateHealthBar();

	if(currentHealth <= 0) {
		playing.setGameOver(true);
		return;
	}
		updateAttackBox();
		updatePosition();
		if(attacking)
			checkAttack();
		//updateHitbox();
		updateAnimationTick();
		setAnimation();
		
		
		
	}
	//check if the player hit the enemy
private void checkAttack() {
	if(attackChecked || Index != 1)
		return;
	attackChecked = true;
	playing.checkEnemyHit(attackBox);
	
}
//updates the attack box to be in front of the player hitbox adjusting to the game scale
private void updateAttackBox() {
	if(right) {
		attackBox.x = hitbox.x + hitbox.width + (int)(Game.SCALE * 10);
	}else if(left) {
		attackBox.x = hitbox.x - hitbox.width - (int)(Game.SCALE * 10);

	}
	attackBox.y = hitbox.y + (Game.SCALE * 10);
}

//updates the current health bar so if there is a loss of health then the width goes down ie. the red bar inside of the health tube
private void updateHealthBar() {
	healthWidth = (int) ((currentHealth /(float) maxHealth) * healthBarWidth);
}

//renders the player's anaimtion and state as well as their hitbox
public void render(Graphics pen, int levelOffset) {
		pen.drawImage(animation[state][Index], 
				(int)(hitbox.x - xDrawOffset) - levelOffset + flipX, 
				(int)(hitbox.y - yDrawOffset) , 
				width * flipW,height, null);
		//drawHitbox(pen, levelOffset);
		//drawAttackBox(pen, levelOffset);
		drawUI(pen);
	}
	

//draws the ui for the player, specifically the health bar
private void drawUI(Graphics pen) {
	pen.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
	pen.setColor(Color.RED); //color of the health bar 
	pen.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
}

private void updateAnimationTick() {
			Tick++; //the time each animation lasts for
			if(Tick >= ANI_SPEED) {
				Tick = 0;
				Index++; //go to the next position in the sheet
				if(Index >=  GetSpriteAmount(state)) { //once it reaches the end of the sprite sheet then go back to the first sprite
					Index = 0;
					attacking = false;
					attackChecked = false;
				}
			}
			
		}
	
private void setAnimation() { //set the player animation based on movement
			int startAni = state;
	
			if(moving) 
				state = RUNNING;
			 else 
				state = IDLE;
			if(inAir) {
				if(airSpeed < 0)
					state = JUMP;
				else
					state = FALLING;
			}
			
			if(attacking) {
				state = ATTACK;
				if(startAni != ATTACK) {
					Index = 1;
					Tick = 0;
					return;
				}
			}
			if(startAni != state)
				resetAniTick();
			
		}
		
private void resetAniTick() { //reset animation tick
	Tick = 0;
	Index = 0;
}

private void updatePosition() { //move the character
		moving = false;
		
		if(jump)
			jump();
	
		if(!inAir)
			if((!left && !right) || (right && left))
				return;
		
		float xSpeed = 0;
		
			if(left) {
				xSpeed -= walkSpeed;
				flipX = width;
				flipW = -1;
			}
			if(right) {
				xSpeed += walkSpeed;
				flipX = 0;
				flipW = 1;
			}
			if(!inAir) {
				if(!IsEntityOnFloor(hitbox, levelData)) 
					inAir = true;
				
			}
			
			if(inAir) {
				if(CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, levelData)){
				hitbox.y += airSpeed;
				airSpeed += GRAVITY;
				updateXPos(xSpeed);
				}else 
				{
				hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
				if(airSpeed > 0)
					resetInAir();
				else
					airSpeed = fallSpeedAfterCollision;
				updateXPos(xSpeed);
			}
		}else 
			updateXPos(xSpeed);
		
		moving = true;
			}
			
//if the player is in the air then return it as true
private void jump() {
	if(inAir)
		return;
	inAir = true;
	airSpeed = jumpSpeed;
}

//once the player reaches the ground then reset the inAir method
private void resetInAir() {
	inAir = false;
	airSpeed = 0;
}

private void updateXPos(float xSpeed) {
	if (CanMoveHere(hitbox.x + xSpeed, hitbox.y , hitbox.width, hitbox.height, levelData)) {
			hitbox.x += xSpeed;
		} else {
			hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
		}
}

//makes sure the player health cannot be below 0 or above the maximum health possible
public void changeHealth(int value) {
	currentHealth += value;
	
	if(currentHealth <= 0) {
		currentHealth = 0; //player is dead
		
	}else if(currentHealth >= maxHealth )
		currentHealth = maxHealth;
}



private void loadAnimations() { //set the arrays for animations and load them
		
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
		
		animation = new BufferedImage[7][8];//covers the player spritesheet, 7 rows 8 columns
		
		for(int j = 0; j< animation.length;j++) //searches vertically
			for(int i = 0; i < animation[j].length; i++) //searches horizontally
				animation[j][i] = img.getSubimage(i*64, j * 40, 64, 40); //get the sprites in the first row and render them at a good size
		
		statusBarImg = LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);
	
		}
	//loads the level data and if the player is in the air then set that to be true so they can fall
public void loadLvlData(int[][] levelData) {
	this.levelData = levelData;
	if(!IsEntityOnFloor(hitbox, levelData))
		inAir = true;
}


public boolean isLeft() {
	return left;
}

public void setLeft(boolean left) {
	this.left = left;
}



public boolean isRight() {
	return right;
}

public void setRight(boolean right) {
	this.right = right;
}




public void resetDirBooleans() { //reset direction when the screen is focused again

	right = false;
	left = false;
}

public void setAttacking(boolean attacking) {
this.attacking = attacking;
}

public void setJump(boolean jump) {
	this.jump = jump;
}
//a hard reset for the player and the direction values so health is back to full, state is back to idle, 
//and the hitbox is where it originally was before
public void resetAll() {
	resetDirBooleans();
	inAir = false;
	attacking = false;
	moving = false;
	state = IDLE;
	currentHealth = maxHealth;
	
	hitbox.x = x;
	hitbox.y = y;
	
	if(!IsEntityOnFloor(hitbox, levelData))
		inAir = true;
}
}








