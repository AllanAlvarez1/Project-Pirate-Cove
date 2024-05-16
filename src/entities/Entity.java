package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import main.Game;

public abstract class Entity { //a class that you cannot create an object of
	
	//this class is used by the Player class
	
	protected float x,y;
	protected int width,height;
	protected Rectangle2D.Float hitbox;
	protected int animationTick, animationIndex;
	protected int state;
	protected float airSpeed;
	protected boolean inAir = false;
	protected int maxHealth = 100;
	protected int currentHealth = maxHealth;
	protected Rectangle2D.Float attackBox;
	protected float walkSpeed = 1.0f * Game.SCALE;

	
	public Entity(float x, float y, int width, int height) {
		this.x=x;
		this.y=y;
		this.width = width;
		this.height = height;
		
	}
	
	//draws the hitbox for the entity
	protected void drawHitbox(Graphics pen, int xLevelOffset) {
		//for debugging the hitbox
		pen.setColor(Color.GREEN);
		pen.drawRect((int)hitbox.x - xLevelOffset, (int)hitbox.y, (int)hitbox.width, (int)hitbox.height);
	}
	
	//draws the attackbox for the entity
	protected void drawAttackBox(Graphics pen, int xLevelOffset) {
		pen.setColor(Color.RED);
		pen.drawRect((int)(attackBox.x - xLevelOffset),(int) attackBox.y, (int)attackBox.width, (int)attackBox.height);
	}
	//initializes the hitbox based on the scale of the game
	protected void initHitbox(int width, int height) {
		hitbox = new Rectangle2D.Float(x, y ,(int) (width * Game.SCALE),(int) (height * Game.SCALE));
	}
	
	public Rectangle2D.Float getHitBox() {
		return hitbox;
	}
	public int getEnemyState() {
		return state;
	}
	public int getAnimationIndex() {
		return animationIndex;
	}
}
