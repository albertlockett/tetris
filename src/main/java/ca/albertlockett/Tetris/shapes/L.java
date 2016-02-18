package ca.albertlockett.Tetris.shapes;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.lanterna.TextColor;

public class L extends Shape {

	private static final TextColor COLOR = new TextColor.RGB(0, 0, 255);
	
	public List<SquareCoordinate> getCoordinates() {
		List<SquareCoordinate> coordinates = new ArrayList<>();
		switch(this.direction) {
			case UP:
				coordinates.add(new SquareCoordinate(0, 0));
				coordinates.add(new SquareCoordinate(0, 1));
				coordinates.add(new SquareCoordinate(0, 2));
				coordinates.add(new SquareCoordinate(1, 2));
				break;
			case RIGHT:
				coordinates.add(new SquareCoordinate(0, 0));
				coordinates.add(new SquareCoordinate(1, 0));
				coordinates.add(new SquareCoordinate(2, 0));
				coordinates.add(new SquareCoordinate(1, 0));
				break;
			case DOWN:
				coordinates.add(new SquareCoordinate(0, 0));
				coordinates.add(new SquareCoordinate(1, 0));
				coordinates.add(new SquareCoordinate(1, 1));
				coordinates.add(new SquareCoordinate(1, 2));
				break;
			case LEFT:
				coordinates.add(new SquareCoordinate(2, 0));
				coordinates.add(new SquareCoordinate(2, 1));
				coordinates.add(new SquareCoordinate(1, 1));
				coordinates.add(new SquareCoordinate(0, 1));
				break;
		}
		return coordinates;
	}

	public TextColor getColor() {
		return COLOR;
	}

}
