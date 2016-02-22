package ca.albertlockett.Tetris.shape.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;

import ca.albertlockett.Tetris.shapes.Shape;
import ca.albertlockett.Tetris.shapes.SquareCoordinate;

public class ShapeDrawer {

	private TextColor bgColor1 = new TextColor.RGB(0, 0, 0);
	private TextColor bgColor2 = new TextColor.RGB(50, 50, 50);
	
	// drawing validation methods ============================================
	
	public boolean canDrawAtPosition(Screen screen, Shape shape, int xOffset, 
			int yOffset) {
		
		TerminalSize termSize = screen.getTerminalSize();
		for(SquareCoordinate coord : shape.getCoordinates()) {
			
			int xCoord = 2 * xOffset + 2 * coord.x;
			int yCoord = yOffset + coord.y;
			
			// check if out of bounds
			if(xCoord < 0 || xCoord >= termSize.getColumns()) {
				System.err.println("xCoord:" + xCoord + " out of bounds");
				return false;
			}
			
			if(xCoord < 0 || xCoord + 1 >= termSize.getColumns()) {
				System.err.println("xCoord + 1:" + (xCoord + 1) + " out of bounds");
				return false;
			}
			
			if(coord.y + yOffset >= termSize.getRows()) {
				System.err.println("yCoord out of bounds");
				return false;
			}
			
			// check if there's already a block there
			List<TextCharacter> textChars = new ArrayList<>();
			textChars.add(screen.getFrontCharacter(xCoord, yCoord));
			textChars.add(screen.getBackCharacter(xCoord, yCoord));
			textChars.add(screen.getFrontCharacter(xCoord + 1, yCoord));
			textChars.add(screen.getBackCharacter(xCoord + 1, yCoord));
			
			for(TextCharacter textChar : textChars) {
				
				if(textChar == null) {
					System.err.println("null text char");
					continue;
				}
				
				
				TextColor fgColor = textChar.getForegroundColor();
				TextColor bgColor = textChar.getBackgroundColor();
				
				// check foreground color
				if(!fgColor.equals(bgColor1) && !fgColor.equals(bgColor2)) {
					System.err.println("fg Block collision detected");
					return false;
				}
				
				// check background color
				if( !bgColor.equals(bgColor1) && !bgColor.equals(bgColor2)) {
					System.err.println("bg Block collision detected");
					return false;
				}
			}
		}
		
		return true;
	}
	
	// some movement helper methods    ---------------------------------------
	
	public boolean canDropShape(Screen screen, Shape shape, int xOffset, 
			int yOffset) {
		return this.canDrawAtPosition(screen, shape, xOffset, yOffset + 1);
	}
	
	public boolean canGoRight(Screen screen, Shape shape, int xOffset, 
			int yOffset) {
		return this.canDrawAtPosition(screen, shape, xOffset + 1, yOffset);
	}
	
	public boolean canGoLeft(Screen screen, Shape shape, int xOffset, 
			int yOffset) {
		return this.canDrawAtPosition(screen, shape, xOffset - 1, yOffset);
	}
	
	public boolean canRotateRight(Screen screen, Shape shape, int xOffset, 
			int yOffset) {
		shape.rotateRight();
		boolean canRotate = canDrawAtPosition(screen, shape, xOffset, yOffset);
		shape.rotateLeft();
		return canRotate;
	}
	
	public boolean canRotateLeft(Screen screen, Shape shape, int xOffset, 
			int yOffset) {
		shape.rotateLeft();
		boolean canRotate = canDrawAtPosition(screen, shape, xOffset, yOffset);
		shape.rotateRight();
		return canRotate;
	}
	
	
	// drawing methods =======================================================
	
	public void drawShape(Shape shape, Screen screen, int xOffset, int yOffset)
			throws CollisionException, IOException {
		
		// check if can draw here
		if(!this.canDrawAtPosition(screen, shape, xOffset, yOffset)) {
			//throw new CollisionException();
			System.err.println("cannot draw shape");
			return;
		}
		
		// draw shape
		for(SquareCoordinate coord : shape.getCoordinates()) {
			int xCoord = 2 * coord.x + 2 * xOffset;
			int yCoord = coord.y + yOffset;
			TextColor color = shape.getColor();
			TextCharacter block = new TextCharacter(' ', color, color,
					SGR.FRAKTUR);
			screen.setCharacter(xCoord, yCoord, block);
			screen.setCharacter(xCoord+ 1, yCoord, block);
		}
		
		// refresh screen to show
		screen.refresh();
	}
	
	public void undrawShape(Shape shape, Screen screen, int xOffset, 
			int yOffset) throws CollisionException, IOException {
		
		// undraw shape
		for(SquareCoordinate coord : shape.getCoordinates()) {
			int xCoord = 2 * coord.x + 2 * xOffset;
			int yCoord = coord.y + yOffset;
			
			TextCharacter block = null;
			if((((coord.x + xOffset) % 2) ^ ((coord.y + yOffset) % 2)) == 0) {
				block = new TextCharacter(' ', 
						bgColor1,
						bgColor1,
						SGR.FRAKTUR);
			} else {
				block = new TextCharacter(' ', 
						bgColor2,
						bgColor2,
						SGR.FRAKTUR);
			}
			screen.setCharacter(xCoord, yCoord, block);
			screen.setCharacter(xCoord+ 1, yCoord, block);
		}
		
		// refresh to show undrawn shapw
		screen.refresh();
	}
	
	public void fillBackground(Screen screen) throws IOException {
		TerminalSize gameSize = screen.getTerminalSize();
		for(int row = 0; row < gameSize.getRows(); row++) {
			for(int col = 0; col < gameSize.getColumns() / 2; col++) {
				
				TextCharacter block = null;
				if(((row % 2) ^ (col % 2)) == 0) {
					block = new TextCharacter(' ', 
							bgColor1,
							bgColor1,
							SGR.FRAKTUR);
				} else {
					block = new TextCharacter(' ', 
							bgColor2,
							bgColor2,
							SGR.FRAKTUR);
				}
				
				screen.setCharacter(col * 2, row, block);
				screen.setCharacter(col * 2 + 1, row, block);
			}
			screen.refresh();
		}
		
		
	}
	
}
