package ca.albertlockett.Tetris;

import java.io.IOException;

import ca.albertlockett.Tetris.shape.util.CollisionException;

public class Tetris {

	public static void main(String[] args) throws IOException, 
			InterruptedException, CollisionException {
		
		GameScreen gameScreen = new GameScreen();
		gameScreen.run();

	}

}
