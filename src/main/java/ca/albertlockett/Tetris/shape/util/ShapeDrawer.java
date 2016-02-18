package ca.albertlockett.Tetris.shape.util;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;

import ca.albertlockett.Tetris.shapes.Shape;
import ca.albertlockett.Tetris.shapes.SquareCoordinate;

public class ShapeDrawer {

	public boolean canDrawAtPosition(Screen screen, Shape shape, int xOffset, 
			int yOffset) {
		
		TerminalSize termSize = screen.getTerminalSize();
		System.out.println(termSize);
		for(SquareCoordinate coord : shape.getCoordinates()) {
			
			// check if out of bounds
			System.out.println(coord.x + xOffset);
			if(coord.x + xOffset >= termSize.getColumns()) {
				return false;
			}
			
			System.out.println(coord.y + yOffset);
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
	
	
	public void drawShape(Shape shape, Screen screen, int xOffset, int yOffset)
			throws CollisionException {
		for(SquareCoordinate coord : shape.getCoordinates()) {
			TextColor color = shape.getColor();
			TextCharacter block = new TextCharacter(' ', color, color,
					SGR.FRAKTUR);
			screen.setCharacter(coord.x + xOffset, coord.y + yOffset, block);
		}
	}
	
	public void undrawShape(Shape shape, Screen screen, int xOffset, 
			int yOffset) throws CollisionException {
		for(SquareCoordinate coord : shape.getCoordinates()) {
			TextCharacter block = new TextCharacter(' ', TextColor.ANSI.DEFAULT, 
					TextColor.ANSI.DEFAULT, SGR.FRAKTUR);
			screen.setCharacter(coord.x + xOffset, coord.y + yOffset, block);
		}
	}
	
	
	
}
