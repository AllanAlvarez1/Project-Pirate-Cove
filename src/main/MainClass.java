package main;

public class MainClass {

	//This is the Project of Allan Alvarez for Video Game Programming Class.
	//The name of the game is Pirate's Cove and the goal is to eliminate all of the 
	//enemies on the map to progress to the next level.
	//The goal is to not just make a game but to understand how each piece connects to 
	//each other so that it is a true learning experience.
	//KaarinGaming has my thanks for helping me understand the process
	//of coding a video game in java. Through this project I was able to learn
	//how to organize my code and use eclipse at a level that I deem professional
	//I learned about packages and how to get classes from one package to transfer data to another.
	//I especially had fun learning about how to get the images from the resource folder and be able to
	//to make levels and put the enemies and player in the level using colors. This method sped up level
	//production and let me experiment with how many enemies there should be on a map. Coding the AI
	//for the enemies was a fun learning experience as it can be boiled down to moving in the direction of the
	//player's hitbox once they are in hitbox range and sight range. When I first started this project I had no idea
	//how I would make the player attack but it was as simple as making an attack hitbox be in front of the player
	//and making it so when the animation starts and the hitbox is colliding with an enemy, then that is an attack.
	//This class has taught me so much about how to make a video game with Java and I feel that my skills have truly improved
	//over the course of the semester. This was a fun learning experience and I will continue to work on this game even after
	//I am done with it. 
	
	public static void main(String[] args) {
	
		new Game(); //accesses the constructor in game
	}

}
