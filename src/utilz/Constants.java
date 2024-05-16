package utilz;

import main.Game;

public class Constants {
	
	//This class holds values for various things that are set and used across many other classes
	
	public static final float GRAVITY = 0.04f * Game.SCALE;
	public static final int ANI_SPEED = 25; //animation speed. higher value makes it go slower. Lower value goes faster.
	
	public static class UI{
		//holds the size for the buttons and adjusts them according to the size of the game
		public static class Buttons{
			public static final int B_WIDTH_DEFAULT = 140;
			public static final int B_HEIGHT_DEFAULT = 56;
			public static final int B_WIDTH = (int)(B_WIDTH_DEFAULT * Game.SCALE);
			public static final int B_HEIGHT = (int)(B_HEIGHT_DEFAULT * Game.SCALE);
		}
		
		public static class PauseButtons{
			public static final int SOUND_SIZE_DEFAULT = 42;
			public static final int SOUND_SIZE = (int)(SOUND_SIZE_DEFAULT * Game.SCALE);
		}
	}
	//these objects will be added at a later date
	
//	public static class ObjectConstants{
//		public static final int RED_POTION = 0;
//		public static final int BLUE_POTION = 1;
//		public static final int BARREL = 2;
//		public static final int BOX = 3;
//		
//		public static final int RED_POTION_VALUE = 15;
//		public static final int BLUE_POTION_VALUE = 10;
//		
//		public static final int CONTAINER_WIDTH_DEFAULT = 40;
//		public static final int CONTAINER_HEIGHT_DEFAULT = 30; 
//		public static final int CONTAINER_WIDTH = (int) (Game.SCALE * CONTAINER_WIDTH_DEFAULT);
//		public static final int CONTAINER_HEIGHT = (int) (Game.SCALE * CONTAINER_HEIGHT_DEFAULT);
//		
//		public static final int POTION_WIDTH_DEFAULT = 12;
//		public static final int POTION_HEIGHT_DEFAULT = 16;
//		public static final int POTION_WIDTH = (int)(Game.SCALE * POTION_WIDTH_DEFAULT);
//		public static final int POTION_HEIGHT = (int)(Game.SCALE * POTION_HEIGHT_DEFAULT);
//
//		public static int GetSpriteAmount(int object_type) {
//			switch(object_type) {
//			case RED_POTION, BLUE_POTION:
//				return 7;
//			case BARREL, BOX:
//				return 8;
//			}
//			return 1;
//		}
//		
//	}
	
	//values for directions 
	public static class Directions{
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}
	
	//values for the background such as the clouds
	public static class Environments{
		public static final int BIGCLOUD_DEFAULT_WIDTH = 448;
		public static final int BIGCLOUD_DEFAULT_HEIGHT = 101;
		public static final int SMALLCLOUD_DEFAULT_WIDTH = 74;
		public static final int SMALLCLOUD_DEFAULT_HEIGHT = 24;


		
		public static final int BIGCLOUD_WIDTH = (int) (BIGCLOUD_DEFAULT_WIDTH * Game.SCALE);
		public static final int BIGCLOUD_HEIGHT = (int) (BIGCLOUD_DEFAULT_HEIGHT * Game.SCALE);
		
		public static final int SMALLCLOUD_WIDTH = (int) (SMALLCLOUD_DEFAULT_WIDTH * Game.SCALE);
		public static final int SMALLCLOUD_HEIGHT = (int) (SMALLCLOUD_DEFAULT_HEIGHT * Game.SCALE);


	}
	
	public static class URMButtons{
		public static final int URM_DEFAULT_SIZE = 56;
		public static final int URM_SIZE = (int)(URM_DEFAULT_SIZE * Game.SCALE);
	}
	
	//values for the volume bar and slider
	public static class VolumeButtons{
		public static final int VOLUME_DEFAULT_WIDTH = 28;
		public static final int VOLUME_DEFAULT_HEIGHT = 44;
		public static final int SLIDER_DEFAULT_WIDTH = 215;
		
		public static final int VOLUME_WIDTH = (int)(VOLUME_DEFAULT_WIDTH * Game.SCALE);
		public static final int VOLUME_HEIGHT = (int)(VOLUME_DEFAULT_HEIGHT * Game.SCALE);
		public static final int SLIDER_WIDTH = (int)(SLIDER_DEFAULT_WIDTH * Game.SCALE);
	}
	
	//values for the enemies such as the crab
	public static class EnemyConstants{
		
		public static final int CRABBY = 0;
		
		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int ATTACK = 2;
		public static final int HIT = 3;
		public static final int DEAD = 4;
		
		public static final int CRABBY_WIDTH_DEFAULT = 72;
		public static final int CRABBY_HEIGHT_DEFAULT = 32;

		public static final int CRABBY_WIDTH = (int)(CRABBY_WIDTH_DEFAULT * Game.SCALE);
		public static final int CRABBY_HEIGHT = (int)(CRABBY_HEIGHT_DEFAULT * Game.SCALE);
		
		public static final int CRABBY_DRAWOFFSET_X = (int)(26*Game.SCALE);
		public static final int CRABBY_DRAWOFFSET_Y = (int)(9*Game.SCALE);

		//sets the value of the sprites according to how many there are per enemy state 
		//so there are 9 sprites for the idle state.
		public static int GetSpriteAmount(int enemy_type, int enemy_state) {
			
			switch(enemy_type) {
			case CRABBY:
				switch(enemy_state) {
				case IDLE:
					return 9;
				case RUNNING:
					return 6;
				case ATTACK:
					return 7;
				case HIT:
					return 4;
				case DEAD:
					return 5;
				}
			}
			return 0;
		}
		
		//sets the health of each enemy such as the crab
		public static int GetMaxHealth(int enemy_type) {
			switch(enemy_type) {
			case CRABBY:
				return 10;
				default:
				return 1;
			}
		}
		//sets the damage of each enemy such as the crab
		public static int GetEnemyDamage(int enemy_type) {
			switch(enemy_type) {
			case CRABBY:
				return 15;
				default:
				return 0;
			}
		}
		
	}
	//these values are for which rows each of the character state is in on the sprite sheet
	public static class PlayerConstants{
		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int JUMP = 2;
		public static final int FALLING = 3;
		public static final int ATTACK = 4;
		public static final int HIT = 5;
		public static final int DEAD = 6;
		
		
		//these values are for how many sprites are in each action of the player so 8 sprites for dying and 5 for idling.
		public static int GetSpriteAmount(int player_action) {
			switch(player_action) {
			case DEAD:
				return 8;
			case RUNNING:
				return 6;
			case IDLE:
				return 5;
			case HIT:
				return 4;
			case JUMP:
			case ATTACK:
				return 3;
			case FALLING:
				default:
					return 1;
						
			}
		}
	}
}
