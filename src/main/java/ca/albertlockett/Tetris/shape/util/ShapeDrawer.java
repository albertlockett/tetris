package ca.albertlockett.Tetris.shape.util;

import java.io.IOException;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;

import ca.albertlockett.Tetris.shapes.Shape;
import ca.albertlockett.Tetris.shapes.SquareCoordinate;

public class ShapeDrawer {

	// drawing validation methods ============================================
	
	public boolean canDrawAtPosition(Screen screen, Shape shape, int xOffset, 
			int yOffset) {
		
		TerminalSize termSize = screen.getTerminalSize();
		for(SquareCoordinate coord : shape.getCoordinates()) {
			
			// check if out of bounds
			if(coord.x + xOffset >= termSize.getColumns()) {
				return false;
			}
			
			if(coord.y + yOffset >= termSize.getRows()) {
				return false;
			}
			
			// check if there's already a block there
			TextCharacter frontChar = screen
					.getFrontCharacter(coord.x + xOffset, coord.y + yOffset);
			if(!frontChar.getBackgroundColor().equals(TextColor.ANSI.DEFAULT) ||
			   !frontChar.getForegroundColor().equals(TextColor.ANSI.DEFAULT)) {
				
				// if there is a block there make sure it's not this same shape
				
				System.out.println("frontchar invalid");
				System.out.println(frontChar);
				return false;
			}
			
			TextCharacter backChar = screen
					.getBackCharacter(coord.x + xOffset, coord.y + yOffset);
			if(!backChar.getBackgroundColor().equals(TextColor.ANSI.DEFAULT) || 
			   !backChar.getBackgroundColor().equals(TextColor.ANSI.DEFAULT)) {
				
				System.out.println("backchar invalid");
				return false;
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
		// TODO:
		return true;
	}
	
	public boolean canRotateLeft(Screen screen, Shape shape, int xOffset, 
			int yOffset) {
		// TODO:
		return true;
	}
	
	
	// drawing methods =======================================================
	
	public void drawShape(Shape shape, Screen screen, int xOffset, int yOffset)
			throws CollisionException, IOException {
		
		// check if can draw here
		if(!this.canDrawAtPosition(screen, shape, xOffset, yOffset)) {
			throw new CollisionException();
		}
		
		// draw shape
		for(SquareCoordinate coord : shape.getCoordinates()) {
			TextColor color = shape.getColor();
			TextCharacter block = new TextCharacter(' ', color, color,
					SGR.FRAKTUR);
			screen.setCharacter(coord.x + xOffset, coord.y + yOffset, block);
		}
		
		// refresh screen to show
		screen.refresh();
	}
	
	public void undrawShape(Shape shape, Screen screen, int xOffset, 
			int yOffset) throws CollisionException, IOException {

		// undraw shape
		for(SquareCoordinate coord : shape.getCoordinates()) {
			TextCharacter block = new TextCharacter(' ', TextColor.ANSI.DEFAULT, 
					TextColor.ANSI.DEFAULT, SGR.FRAKTUR);
			screen.setCharacter(coord.x + xOffset, coord.y + yOffset, block);
		}
		
		// refresh to show undrawn shapw
		screen.refresh();
	}
	
	
	
}
