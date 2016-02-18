package ca.albertlockett.Tetris.shapes;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.lanterna.TextColor;

public class Block extends Shape {

	private static final TextColor COLOR = new TextColor.RGB(255, 255, 102);
	
	public List<SquareCoordinate> getCoordinates() {
		List<SquareCoordinate> coordinates = new ArrayList<>();
		coordinates.add(new SquareCoordinate(0, 0));
		coordinates.add(new SquareCoordinate(0, 1));
		coordinates.add(new SquareCoordinate(1, 0));
		coordinates.add(new SquareCoordinate(1, 1));
		return coordinates;
	}

	@Override
	public TextColor getColor() {
		return COLOR;
	}

}
