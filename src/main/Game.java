package main;

import java.awt.Graphics; 

import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.Playing;
import utilz.LoadSave;




//where everything concerning the game is called
public class Game  implements Runnable{
	//a constructor is the head method of the class
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread; //gameloop thread
	private final int FPS_SET = 120; //Frames per Second goal
	private final int UPS_SET = 200; //Update per second goal
	
	private Playing playing;
	private Menu menu;

	public final static int TILES_DEFAULT_SIZE = 32; //the tiles have a default size of 32 pixels that will be scaled
	public final static float SCALE = 2f;  //determines how large the game is and is used for pretty much most size calcs
	public final static int TILES_IN_WIDTH = 26; //the levels are a minimum of 26 tiles wide
	public final static int TILES_IN_HEIGHT = 14; //the levels are required to be 14 tiles high
	public final static int TILES_SIZE = (int)(TILES_DEFAULT_SIZE * SCALE); //tiles are fitted to the size of the game/screen
	public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH; //game width is fitted to the tiles size
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT; //game height is fitted to the tiles size
	public Game() {
		initClasses();
		
		gamePanel = new GamePanel(this); //accesses the gamepanel class
		gameWindow = new GameWindow(gamePanel); //accesses the gamewindow class
		gamePanel.setFocusable(true);
		gamePanel.requestFocus();
		
		startGameLoop();
		
	}
	
	
	private void initClasses() {
		menu = new Menu(this);
		playing = new Playing(this);
	}


	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start(); 
	}
	
	public void update() { //this method makes it so the playing state does not continue while in menu and vice versa
		switch(Gamestate.state) {
		case MENU: //only the correct gamestate will update 
			menu.update();
			break;
		case PLAYING:
			playing.update();
			break;
		case OPTIONS: //options and quit stop the program for now 
		case QUIT:
		default:
			System.exit(0);
			break;
		
		}

	}
	public void render(Graphics pen) { //this method makes it so only one gamestate is being rendered at a time.
		switch(Gamestate.state) { 
		case MENU:
			menu.draw(pen);
			break;
		case PLAYING:
			playing.draw(pen);
			break;
		default:
			break;
		
		}
	}
	
	@Override
	public void run() { //the game loop thread
		
		double timePerFrame = 1000000000.0 / FPS_SET; //time per frame in nanoseconds
		double timePerUpdate = 1000000000.0 / UPS_SET; //update per second in nanoseconds
		
		long previousTime = System.nanoTime(); //previous nanotime recorded by computer
		
		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis(); //checks the last time in milliseconds and becomes that value
		double deltaU = 0; //change in updates
		double deltaF = 0; //change in frames
		
		while(true) {
			
			
			long currentTime = System.nanoTime(); //the current time in nanoseconds
			
			deltaU += (currentTime - previousTime) / timePerUpdate; //change in time per update
			deltaF += (currentTime - previousTime) / timePerFrame; //change in time per frame
			previousTime = currentTime; //previous time now becomes the new current time
			
			if(deltaU >= 1) { //when there is a change in update time, advance the update and go to the next frame
				update();
				updates++;
				deltaU--;
			}
			
			if(deltaF >= 1) { //when there is a change in frame time, then repaint the screen and advance in frames
				gamePanel.repaint(); //repaint what happens in gamepanel
				frames++; //update frame count	
				deltaF--; //last checked nanotime is now current nanotime
			}
			

			
			if(System.currentTimeMillis() - lastCheck >= 1000) {//check how many frames per second
				 lastCheck = System.currentTimeMillis();
				 System.out.println("FPS: " + frames + " | UPS: " + updates ); //tells us how the game is running
				 
				 frames = 0; //resets the frames back to 0
				 updates = 0; //resets the update back to 0
			 }
			
			
			
			
		}
		
	}
	public void windowFocusLost() {
		if(Gamestate.state == Gamestate.PLAYING) {
			playing.getPlayer().resetDirBooleans();
		}
	}
	public Menu getMenu() {
		return menu;
	}
	
	public Playing getPlaying() {
		return playing;
	}
}
