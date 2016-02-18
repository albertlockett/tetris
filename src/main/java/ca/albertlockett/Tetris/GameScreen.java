package ca.albertlockett.Tetris;

import java.io.IOException;

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

public class GameScreen {

	
	public static void main(String[] args) throws IOException, 
			InterruptedException, CollisionException {
		
		Terminal terminal = new DefaultTerminalFactory().createTerminal();
		Screen screen = new TerminalScreen(terminal);
	    screen.startScreen();
	
	    ShapeFactory shapeFactory = new ShapeFactory();
	    ShapeDrawer drawer = new ShapeDrawer();
	    
	    int xOffset = 5;
	    int yOffset = 5;
	    
	    Shape shape = shapeFactory.getRandomShape();
	    drawer.drawShape(shape, screen, xOffset, yOffset);
	    screen.refresh();
	    
	    while(true) {
	    	KeyStroke key = screen.pollInput();
	    	if(key != null) {
	    		
	    		if(key.getKeyType().equals(KeyType.ArrowLeft)) {
	    			drawer.undrawShape(shape, screen, xOffset, yOffset);
    				screen.refresh();
	    			if(drawer.canDrawAtPosition(screen, shape, xOffset - 1,
	    					yOffset)) {
	    				xOffset--;
	    			}
	    			drawer.drawShape(shape, screen, xOffset, yOffset);
    				screen.refresh();
	    		} else if(key.getKeyType().equals(KeyType.ArrowRight)) {
	    			drawer.undrawShape(shape, screen, xOffset, yOffset);
    				screen.refresh();
	    			if(drawer.canDrawAtPosition(screen, shape, xOffset + 1, 
	    					yOffset)) {
	    				xOffset++;
	    			}
    				drawer.drawShape(shape, screen, xOffset, yOffset);
    				screen.refresh();
	    		} else if(key.getKeyType().equals(KeyType.ArrowDown)) {
    				drawer.undrawShape(shape, screen, xOffset, yOffset);
    				screen.refresh();
	    			if(drawer.canDrawAtPosition(screen, shape, xOffset, 
	    					yOffset + 1)) {
	    				yOffset++;
	    			}
    				drawer.drawShape(shape, screen, xOffset, yOffset);
    				screen.refresh();
	    		} else if(key.getKeyType().equals(KeyType.ArrowUp)) {
    				drawer.undrawShape(shape, screen, xOffset, yOffset);
    				screen.refresh();
	    			if(drawer.canDrawAtPosition(screen, shape, xOffset, 
	    					yOffset - 1)) {
	    				yOffset--;
	    			}
    				drawer.drawShape(shape, screen, xOffset, yOffset);
    				screen.refresh();
	    		} else if(key.getCharacter().equals('z')) {
	    			drawer.undrawShape(shape, screen, xOffset, yOffset);
    				screen.refresh();
    				shape.rotateLeft();
    				drawer.drawShape(shape, screen, xOffset, yOffset);
    				screen.refresh();
	    		} else if(key.getCharacter().equals('x')) {
	    			drawer.undrawShape(shape, screen, xOffset, yOffset);
    				screen.refresh();
    				shape.rotateRight();
    				drawer.drawShape(shape, screen, xOffset, yOffset);
    				screen.refresh();
	    		} else if(key.getKeyType().equals(KeyType.Enter)) {
	    			drawer.undrawShape(shape, screen, xOffset, yOffset);
	    			xOffset = 5;
    			    yOffset = 5;    
    			    shape = shapeFactory.getRandomShape();
    			    drawer.drawShape(shape, screen, xOffset, yOffset);
    			    screen.refresh();
	    		}
	    		
	    		System.out.println(key);
	    	}
	    	Thread.sleep(20);
	    }
	    
	    
	    
	    /* == this works OK
	    while(true) {
	    	
	    	Shape shape = shapeFactory.getRandomShape();
	    	
		    int xOffset = 10;
		    int yOffset = 1;
		    int delay = 200;
		    
		    while (drawer.canDrawAtPosition(screen, shape, xOffset, yOffset)) {

		    	drawer.drawShape(shape, screen, xOffset, yOffset);
			    screen.refresh();
			    
			    // wait
			    Thread.sleep(delay);
			    
			    // undraw
			    drawer.undrawShape(shape, screen, xOffset, yOffset);
			    screen.refresh();
			    
			    if(!drawer.canDrawAtPosition(screen,shape,xOffset,yOffset+1)) {
			    	drawer.drawShape(shape, screen, xOffset, yOffset);
			    }
				    
			    // increment
			    yOffset++;
		    
			    
		    }
	    }
	    */
	    
	    
	}
	
}
