package objects;

import static utilz.Constants.ANI_SPEED;

//import static utilz.Constants.ObjectConstants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;

public class GameObject {
	
	protected int x, y, objectType;
	protected Rectangle2D.Float hitbox;
	protected boolean Animation, active = true;
	protected int animationTick, animationIndex;
	protected int xDrawOffset, yDrawOffset;
	
	public GameObject(int x, int y, int objectType) {
		this.x = x;
		this.y = y;
		this.objectType = objectType;
	}
	
//	protected void initHitbox(int width, int height) {
//		hitbox = new Rectangle2D.Float(x, y ,(int) (width * Game.SCALE),(int) (height * Game.SCALE));
//	}
//	
//	public void drawHitbox(Graphics pen, int xLevelOffset) {
//		//for debugging the hitbox
//		pen.setColor(Color.GREEN);
//		pen.drawRect((int)hitbox.x - xLevelOffset, (int)hitbox.y, (int)hitbox.width, (int)hitbox.height);
//	}
//	
//	protected void updateAnimationTick() {
//		animationTick++;
//		if(animationTick >= ANI_SPEED) {
//			animationTick = 0;
//			animationIndex++;
//			if(animationIndex >= GetSpriteAmount(objectType)) {
//				animationIndex = 0;
//				if(objectType == BARREL || objectType == BOX ) {
//					Animation = false;
//					active = false;
//				}
//				
//			}
//		}
//	}
//	
//	public void reset() {
//		animationIndex = 0;
//		animationTick = 0;
//		active = true;
//		if(objectType == BARREL || objectType == BOX ) {
//			Animation = false;
//		}else {
//			Animation = true;
//
//		}
//
//	}
//
//	public int getObjectType() {
//		return objectType;
//	}
//
//	
//
//	public Rectangle2D.Float getHitbox() {
//		return hitbox;
//	}
//
//	
//
//	public boolean isActive() {
//		return active;
//	}
//
//
//
//	public int getxDrawOffset() {
//		return xDrawOffset;
//	}
//
//	
//
//	public int getyDrawOffset() {
//		return yDrawOffset;
//	}
//	
//	public int getAnimationIndex() {
//		return animationIndex;
//	}
//	public void setActive(boolean active) {
//		this.active = active;
//	}
//	

}
