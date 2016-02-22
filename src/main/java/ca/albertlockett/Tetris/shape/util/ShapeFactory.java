package ca.albertlockett.Tetris.shape.util;

import java.util.Random;

import ca.albertlockett.Tetris.shapes.Block;
import ca.albertlockett.Tetris.shapes.J;
import ca.albertlockett.Tetris.shapes.L;
import ca.albertlockett.Tetris.shapes.Line;
import ca.albertlockett.Tetris.shapes.S;
import ca.albertlockett.Tetris.shapes.Shape;
import ca.albertlockett.Tetris.shapes.T;
import ca.albertlockett.Tetris.shapes.Z;

public class ShapeFactory {

	public Shape getRandomShape() {
		Random random = new Random();

		return new Line();
		
		/*
		int shape = random.nextInt(7);
		switch(shape) {
			case 0:
				return new Block();
			case 1:
				return new S();
			case 2:
				return new Z();
			case 3:
				return new L();
			case 4:
				return new J();
			case 5:
				return new T();
			default:
				return new Line();
		}		
		*/
	}
	
}
