package objects;

import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
//import static utilz.Constants.ObjectConstants.*;
import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;

public class ObjectManager {
//	private Playing playing;
//	private BufferedImage[][] potionImgs, containerImgs;
//	private ArrayList<Potion> potions;
//	private ArrayList<GameContainer> containers;
//
//	public ObjectManager(Playing playing) {
//
//		this.playing = playing;
//		loadImgs();
//	}
//
//	private void loadImgs() {
//		BufferedImage potionSprite = LoadSave.GetSpriteAtlas(LoadSave.POTIONS_ATLAS);
//		potionImgs = new BufferedImage[2][7];
//
//		for (int j = 0; j < potionImgs.length; j++)
//			for (int i = 0; i < potionImgs[j].length; i++)
//				potionImgs[j][i] = potionSprite.getSubimage(12 * i, 16 * j, 12, 16);
//
//		BufferedImage containerSprite = LoadSave.GetSpriteAtlas(LoadSave.CONTAINER_ATLAS);
//		containerImgs = new BufferedImage[2][8];
//
//		for (int j = 0; j < containerImgs.length; j++)
//			for (int i = 0; i < containerImgs[j].length; i++)
//				containerImgs[j][i] = containerSprite.getSubimage(40 * i, 30 * j, 40, 30);
//	}
//	
////	public void loadObjects(Level newLevel) {
////		potions = newLevel.getPotions();
////		containers = newLevel.getContainers();
////	}
//
//	public void update() {
//		for (Potion p : potions)
//			if (p.isActive())
//				p.update();
//
//		for (GameContainer gc : containers)
//			if (gc.isActive())
//				gc.update();
//
//	}
//
//	public void draw(Graphics pen, int xLevelOffset) {
//		drawPotions(pen, xLevelOffset);
//		drawContainers(pen, xLevelOffset);
//	}
//
//	private void drawContainers(Graphics pen, int xLevelOffset) {
//		for (GameContainer gc : containers)
//			if (gc.isActive()) {
//				int type = 0;
//				if (gc.objectType == BARREL)
//					type = 1;
//
//				pen.drawImage(containerImgs[type][gc.getAnimationIndex()],
//						(int) (gc.getHitbox().x - gc.getxDrawOffset() - xLevelOffset),
//						(int) (gc.getHitbox().y - gc.getyDrawOffset()), CONTAINER_WIDTH, CONTAINER_HEIGHT, null);
//			}
//	}
//
//	private void drawPotions(Graphics pen, int xLevelOffset) {
//		for (Potion p : potions)
//			if (p.isActive()) {
//				int type = 0; //blue potion
//				if(p.getObjectType() == RED_POTION)
//					type = 1;
//				
//
//				pen.drawImage(containerImgs[type][p.getAnimationIndex()],
//						(int) (p.getHitbox().x - p.getxDrawOffset() - xLevelOffset),
//						(int) (p.getHitbox().y - p.getyDrawOffset()), POTION_WIDTH, POTION_HEIGHT, null);
//		
//			}
//	}

}
