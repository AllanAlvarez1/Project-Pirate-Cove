package main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

public class GameWindow extends JFrame{ //creates the window for the game

	public GameWindow(GamePanel gamePanel) {
		JFrame frame = new JFrame();
		
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//when the window is closed the execution is stopped
		frame.add(gamePanel);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null); //sets window to be created at the center of the screen
		frame.setVisible(true); //sets the window to be visible
		frame.addWindowFocusListener(new WindowFocusListener() {

			@Override
			public void windowGainedFocus(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowLostFocus(WindowEvent e) {
				gamePanel.getGame().windowFocusLost();
			}
			
		});
	
	
	
	
	
	
	}
}
