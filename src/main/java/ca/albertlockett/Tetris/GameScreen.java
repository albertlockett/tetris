package ca.albertlockett.Tetris;

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

	private int fpsDelay = 1000 / 60;
	private int brickDropDelay = 1000;
	private Screen screen;
	private ShapeFactory shapeFactory;
	private ShapeDrawer drawer;
	private Shape shape;
	private int xOffset;
	private int yOffset;
	
	public GameScreen() throws IOException {
		// create game screen
		Terminal terminal = new DefaultTerminalFactory().createTerminal();
		screen = new TerminalScreen(terminal);
		
		// initialize shape utilities
		shapeFactory = new ShapeFactory();
		drawer = new ShapeDrawer();
	    shape = shapeFactory.getRandomShape();
	    
	}
	
	
	public void run() throws IOException, 
			InterruptedException, CollisionException {
		
		// start game screen
		screen.startScreen();
		
		TerminalSize gameSize = screen.getTerminalSize();
		xOffset = gameSize.getColumns() / 4;
	    yOffset = 0;
	    
	    // initialize game state variables
	    boolean gameOver = false;
	    
	    
	    // draw background and draw shape to start game
	    drawer.fillBackground(screen);
	    drawer.drawShape(shape, screen, xOffset, yOffset);
	    
	    // start brick move treads
	    BrickDropper brickDropTimer = new BrickDropper();
	    brickDropTimer.setDelay(brickDropDelay);
	    brickDropTimer.start();
	    
	    // start key input thread
	    KeyInputThread keyInput = new KeyInputThread((TerminalScreen) screen);
	    keyInput.start();
	    
	    
	    System.out.println("starting game: xO=" + xOffset + " yO=" + yOffset);
	    
	    // run game loop
	    while(!gameOver) {
	    	Thread.sleep(fpsDelay); // delay
	    	
	    	boolean nextShape = false;
	    	boolean shapeMoved = false;
	    	
	    	// check if there's been some keyboard input
	    	List<KeyStroke> keyStrokes = keyInput.getKeyStrokes();
	    	for(KeyStroke key : keyStrokes) {
				drawer.undrawShape(shape, screen, xOffset, yOffset);
	    		processKeyInput(key);
	    		System.out.println("move: xO=" + xOffset + " yO=" + yOffset);
	    		if(!drawer.canDropShape(screen, shape, xOffset, yOffset)) {
	    			nextShape = true;
	    		}
	    		drawer.drawShape(shape, screen, xOffset, yOffset);
	    		shapeMoved = true;
	    	}
	    	
	    	// check if time to drop brick
	    	if(brickDropTimer.getDropBrick()) {
	    		System.out.println("drop: xO=" + xOffset + " yO=" + yOffset);
	    		drawer.undrawShape(shape, screen, xOffset, yOffset);
	    		if(drawer.canDropShape(screen, shape, xOffset, yOffset)){
	    			yOffset++;
	    		} else {
	    			nextShape = true;
	    		}
	    		drawer.drawShape(shape, screen, xOffset, yOffset);
	    		shapeMoved = true;
	    	}
	    	
	    	// if ready for next shape
	    	if(nextShape) {
	    		xOffset = gameSize.getColumns() / 4;
	    	    yOffset = 0;
	    	    shape = shapeFactory.getRandomShape();
	    	}
	    	
	    	if(shapeMoved) {
	    		processCompletedRows();
	    	}
	    }
	    
	}
	
	
	private void processKeyInput(KeyStroke key) throws CollisionException,
			IOException {
		// drop
		if(new Character(' ').equals(key.getCharacter())) {
			while(drawer.canDropShape(screen, shape, xOffset, yOffset)){
				yOffset++;
			}
			
		}
		
		// down
		if(key.getKeyType().equals(KeyType.ArrowDown)) {
			if(drawer.canDropShape(screen, shape, xOffset, yOffset)) {
				yOffset++;
			}
			return;
		}
		
		// left
		if(key.getKeyType().equals(KeyType.ArrowLeft)) {
			if(drawer.canGoLeft(screen, shape, xOffset, yOffset)) {
				xOffset--;
			}
			return;
		}
		
		// right
		if(key.getKeyType().equals(KeyType.ArrowRight)) {
			if(drawer.canGoRight(screen, shape, xOffset, yOffset)) {
				xOffset++;
			}
			return;
		}
		
		// rotate left
		if(new Character('z').equals(key.getCharacter())) {
			if(drawer.canRotateLeft(screen, shape, xOffset, yOffset)) {
				shape.rotateLeft();
			}
			return;
		}
		
		// rotate right
		if(new Character('x').equals(key.getCharacter())) {
			if(drawer.canRotateRight(screen, shape, xOffset, yOffset)) {
				shape.rotateRight();
			}
			return;
		}
	}
	
	
	public void processCompletedRows() {
		TerminalSize gameSize = screen.getTerminalSize();
		for(int row = 0; row < gameSize.getRows(); row++) {
			if(ScreenUtil.isRowComplete(screen, row)) {
				
				System.out.println("found completed row");
				drawer.removeRow(screen, row);
			}
		}
	}
	
}
