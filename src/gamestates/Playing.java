package gamestates;

import java.awt.Color;
import static utilz.Constants.Environments.*;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.Game;
import objects.ObjectManager;
import ui.GameOverOverlay;
import ui.LevelCompletedOverlay;
import ui.PauseOverlay;
import utilz.LoadSave;

public class Playing extends State implements Statemethods {
	
	//The PLAYING gamestate which is a big part of the whole operation 
	
	private Player player;
	private LevelManager levelManager;
	private EnemyManager enemyManager;
	private GameOverOverlay gameOverOverlay;
	private LevelCompletedOverlay levelCompletedOverlay;
	private ObjectManager objectManager;

	private boolean paused = false; // the game is not set to paused by default
	private PauseOverlay pauseOverlay;
	private int xLevelOffset; // the distance of the level on the x plane
	private int leftBorder = (int) (0.4 * Game.GAME_WIDTH); // change the left and right to determine when the screen moves
	private int rightBorder = (int) (0.6 * Game.GAME_WIDTH);
	private int maxLevelOffsetX;

	private BufferedImage backgroundImg, bigCloud, smallCloud;
	private int[] smallCloudsPosition;
	private Random random = new Random();

	private boolean gameOver;
	private boolean levelCompleted;

	public Playing(Game game) {
		super(game);
		initClasses();
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BACKGROUND_IMG);
		bigCloud = LoadSave.GetSpriteAtlas(LoadSave.BIG_CLOUDS_IMG);
		smallCloud = LoadSave.GetSpriteAtlas(LoadSave.SMALL_CLOUDS_IMG);
		smallCloudsPosition = new int[8];
		for (int i = 0; i < smallCloudsPosition.length; i++)
			smallCloudsPosition[i] = (int) (90 * Game.SCALE) + random.nextInt((int) (100 * Game.SCALE));

		calcLevelOffset();
		loadStartLevel();
	}

	//reset all values then load the next level from the level manager and set the new spawn in the new level
	public void loadNextLevel() {
		resetAll();
		levelManager.loadNextLevel();
		player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
	}

	//at the start of the level, load the enemies
	private void loadStartLevel() {
		enemyManager.loadEnemies(levelManager.getCurrentLevel());
//		objectManager.loadObjects(levelManager.getCurrentLevel());
	}
	
	//the distance of the level
	private void calcLevelOffset() {
		maxLevelOffsetX = levelManager.getCurrentLevel().getLevelOffset();
	}

	private void initClasses() {
		levelManager = new LevelManager(game);
		enemyManager = new EnemyManager(this);
//		objectManager = new ObjectManager(this);
		player = new Player(200, 200, (int) (64 * Game.SCALE), (int) (40 * Game.SCALE), this); //player size scaled to the game
		player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
		player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
		pauseOverlay = new PauseOverlay(this);
		gameOverOverlay = new GameOverOverlay(this);
		levelCompletedOverlay = new LevelCompletedOverlay(this);
	}

	public void unpauseGame() {
		paused = false;
	}

	public Player getPlayer() {
		return player;
	}

	public void windowFocusLost() {
		player.resetDirBooleans();
	}

	//updates the playing gamestate
	@Override
	public void update() {
		//if the game is paused then update the pause screen class and if the level is completed then update the level completed class
		//and if there is a game over then update the player and the level as well as the enemies
		if (paused) {
			pauseOverlay.update();
		} else if (levelCompleted) {
			levelCompletedOverlay.update();
		} else if (!gameOver) {
			levelManager.update();
//			objectManager.update();
			player.update();
			enemyManager.update(levelManager.getCurrentLevel().getLevelData(), player);
			checkClosetoBorder();
		}
	}

	private void checkClosetoBorder() { // checks if the player hitbox has moved past either the right or left border
		int playerX = (int) player.getHitBox().x; // player position
		int difference = playerX - xLevelOffset;

		if (difference > rightBorder) //checks right border and updates accordingly
			xLevelOffset += difference - rightBorder;
		else if (difference < leftBorder) //checks left border and updates accordingly
			xLevelOffset += difference - leftBorder;

		if (xLevelOffset > maxLevelOffsetX)
			xLevelOffset = maxLevelOffsetX;
		else if (xLevelOffset < 0)
			xLevelOffset = 0;

	}

	public void mouseDragged(MouseEvent e) {
		if (!gameOver)
			if (paused)
				pauseOverlay.mouseDragged(e);
	}

	//draw the playing state
	@Override
	public void draw(Graphics pen) {
		pen.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);

		drawClouds(pen);

		levelManager.draw(pen, xLevelOffset);
		player.render(pen, xLevelOffset);
		enemyManager.draw(pen, xLevelOffset);
//		objectManager.draw(pen, xLevelOffset);
		if (paused) {
			pen.setColor(Color.BLACK);
			pen.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
			pauseOverlay.draw(pen); 
		} else if (gameOver)
			gameOverOverlay.draw(pen);
		else if (levelCompleted)
			levelCompletedOverlay.draw(pen);
	}

	//draws the clouds in the playing state 
	private void drawClouds(Graphics pen) {
		for (int i = 0; i < 3; i++)
			pen.drawImage(bigCloud, i * BIGCLOUD_WIDTH - (int) (xLevelOffset * 0.3), (int) (204 * Game.SCALE),
					BIGCLOUD_WIDTH, BIGCLOUD_HEIGHT, null);

		for (int i = 0; i < smallCloudsPosition.length; i++)
			pen.drawImage(smallCloud, SMALLCLOUD_WIDTH * 4 * i - (int) (xLevelOffset * 0.7), smallCloudsPosition[i],
					SMALLCLOUD_WIDTH, SMALLCLOUD_HEIGHT, null);
	}

	//resets the important values in the playing state such as resetting the enemies, the player, and if the level is completed or not
	public void resetAll() {
		gameOver = false;
		paused = false;
		levelCompleted = false;
		player.resetAll();
		enemyManager.resetAllEnemies();
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	//check if the enemy is attacked
	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		enemyManager.checkEnemyAttacked(attackBox);
	}

	//Left Click sets the player to attack in the playing state
	@Override
	public void mouseClicked(MouseEvent e) {
		if (!gameOver)
			if (e.getButton() == MouseEvent.BUTTON1) {
				player.setAttacking(true);
				;
			}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (!gameOver) {
			if (paused)
				pauseOverlay.mousePressed(e);
			else if (levelCompleted)
				levelCompletedOverlay.mousePressed(e);

		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (!gameOver) {
			if (paused)
				pauseOverlay.mouseReleased(e);
			else if (levelCompleted)
				levelCompletedOverlay.mouseReleased(e);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (!gameOver) {
			if (paused)
				pauseOverlay.mouseMoved(e);
			else if (levelCompleted)
				levelCompletedOverlay.mouseMoved(e);
		}
	}

	//sets A and D to left and right and sets SPACE to jump, as well as ESCAPE to pause.
	@Override
	public void keyPressed(KeyEvent e) {
		if (gameOver)
			gameOverOverlay.keyPressed(e);
		else
			switch (e.getKeyCode()) {

			case KeyEvent.VK_A:
				player.setLeft(true);
				break;

			case KeyEvent.VK_D:
				player.setRight(true);
				break;
			case KeyEvent.VK_SPACE:
				player.setJump(true);
				break;
			case KeyEvent.VK_ESCAPE: // pauses the game and unpauses if paused
				paused = !paused;
			}
	}

	//stops moving the player and stops them from jumping if space is not held anymore
	@Override
	public void keyReleased(KeyEvent e) {
		if (!gameOver)
			switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				player.setLeft(false);
				break;
			case KeyEvent.VK_D:
				player.setRight(false);
				break;
			case KeyEvent.VK_SPACE:
				player.setJump(false);
				break;
			}
	}

	public EnemyManager getEnemyManager() {
		return enemyManager;
	}

	public void setMaxLevelOffset(int levelOffset) {
		this.maxLevelOffsetX = levelOffset;
	}

	public void setLevelCompleted(boolean levelCompleted) {
		this.levelCompleted = levelCompleted;
	}

	public ObjectManager getObjectManager() {
		return objectManager;
	}
}
