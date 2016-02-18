package ca.albertlockett.Tetris;

import java.awt.RenderingHints.Key;
import java.io.IOException;
import java.util.List;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import ca.albertlockett.Tetris.shape.util.CollisionException;
import ca.albertlockett.Tetris.shape.util.ShapeDrawer;
import ca.albertlockett.Tetris.shape.util.ShapeFactory;
import ca.albertlockett.Tetris.shapes.Shape;
import ca.albertlockett.Tetris.thread.BrickDropper;
import ca.albertlockett.Tetris.thread.KeyInputThread;

public class GameScreen {

	
	public static void main(String[] args) throws IOException, 
			InterruptedException, CollisionException {
		
		// create game screen
		Terminal terminal = new DefaultTerminalFactory().createTerminal();
		Screen screen = new TerminalScreen(terminal);
	    screen.startScreen();
	    TerminalSize gameSize = screen.getTerminalSize();
	    
	    // initialize game state variables
	    boolean gameOver = false;
	    int fpsDelay = 1000 / 60;  // 60 FPS
	    int brickDropDelay = 1000; // 1s at level 1
	    
	    // offset for shape
	    int xOffset = gameSize.getColumns() / 2;
	    int yOffset = 0;
	    
	    // initialize shape utilities
	    ShapeFactory shapeFactory = new ShapeFactory();
	    ShapeDrawer drawer = new ShapeDrawer();
	    Shape shape = shapeFactory.getRandomShape();
	    
	    // draw shape to start game
	    drawer.drawShape(shape, screen, xOffset, yOffset);
	    
	    // start brick move treads
	    BrickDropper brickDropTimer = new BrickDropper();
	    brickDropTimer.setDelay(brickDropDelay);
	    brickDropTimer.start();
	    
	    // start key input thread
	    KeyInputThread keyInput = new KeyInputThread((TerminalScreen) screen);
	    keyInput.start();
	    
	    while(!gameOver) {
	    	Thread.sleep(fpsDelay);
	    	
	    	boolean nextShape = false;
	    	
	    	// check if there's been some keyboard input
	    	List<KeyStroke> keyStrokes = keyInput.getKeyStrokes();
	    	for(KeyStroke key : keyStrokes) {
	    		
	    		// drop
	    		if(new Character(' ').equals(key.getCharacter())) {
	    			drawer.undrawShape(shape, screen, xOffset, yOffset);
	    			while(drawer.canDropShape(screen, shape, xOffset, yOffset)){
	    				yOffset++;
	    			}
	    			drawer.drawShape(shape, screen, xOffset, yOffset);
	    			nextShape = true;
	    		}
	    		
	    		// down
	    		if(key.getKeyType().equals(KeyType.ArrowDown)) {
	    			drawer.undrawShape(shape, screen, xOffset, yOffset);
	    			if(drawer.canDropShape(screen, shape, xOffset, yOffset)) {
	    				yOffset++;
	    			}
	    			drawer.drawShape(shape, screen, xOffset, yOffset);
	    			continue;
	    		}
	    		
	    		// left
	    		if(key.getKeyType().equals(KeyType.ArrowLeft)) {
	    			drawer.undrawShape(shape, screen, xOffset, yOffset);
	    			if(drawer.canGoLeft(screen, shape, xOffset, yOffset)) {
	    				xOffset--;
	    			}
	    			drawer.drawShape(shape, screen, xOffset, yOffset);
	    			continue;
	    		}
	    		
	    		// right
	    		if(key.getKeyType().equals(KeyType.ArrowRight)) {
	    			drawer.undrawShape(shape, screen, xOffset, yOffset);
	    			if(drawer.canGoRight(screen, shape, xOffset, yOffset)) {
	    				xOffset++;
	    			}
	    			drawer.drawShape(shape, screen, xOffset, yOffset);
	    			continue;
	    		}
	    		
	    		// rotate left
	    		if(new Character('z').equals(key.getCharacter())) {
	    			drawer.undrawShape(shape, screen, xOffset, yOffset);
	    			if(drawer.canRotateLeft(screen, shape, xOffset, yOffset)) {
	    				shape.rotateLeft();
	    			}
	    			drawer.drawShape(shape, screen, xOffset, yOffset);
	    			continue;
	    		}
	    		
	    		// rotate right
	    		if(new Character('x').equals(key.getCharacter())) {
	    			drawer.undrawShape(shape, screen, xOffset, yOffset);
	    			if(drawer.canRotateRight(screen, shape, xOffset, yOffset)) {
	    				shape.rotateRight();
	    			}
	    			drawer.drawShape(shape, screen, xOffset, yOffset);
	    			continue;
	    		}
	    	}
	    	
	    	if(brickDropTimer.getDropBrick()) {
	    		drawer.undrawShape(shape, screen, xOffset, yOffset);
	    		if(drawer.canDropShape(screen, shape, xOffset, yOffset)){
	    			yOffset++;
	    		} else {
	    			nextShape = true;
	    		}
	    		drawer.drawShape(shape, screen, xOffset, yOffset);
	    	}
	    	
	    	// if time for next shape
	    	if(nextShape) {
	    		xOffset = gameSize.getColumns() / 2;
	    	    yOffset = 0;
	    	    shape = shapeFactory.getRandomShape();
	    	}
	    }
	    
	}
	
}
