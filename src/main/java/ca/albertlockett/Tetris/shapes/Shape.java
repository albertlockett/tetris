package ca.albertlockett.Tetris.shapes;

import java.util.List;

import com.googlecode.lanterna.TextColor;

public abstract class Shape {

	protected ShapeDirection direction;
	
	public Shape() {
		this.direction = ShapeDirection.UP;
	}
	
	public void rotateRight() {
		switch(this.direction) {
			case UP:
				this.direction = ShapeDirection.RIGHT;
				break;
			case RIGHT:
				this.direction = ShapeDirection.DOWN;
				break;
			case DOWN:
				this.direction = ShapeDirection.LEFT;
				break;
			case LEFT:
				this.direction = ShapeDirection.UP;
				break;
		}
	}
	
	public void rotateLeft() {
		switch(this.direction) {
			case UP:
				this.direction = ShapeDirection.LEFT;
				break;
			case LEFT:
				this.direction = ShapeDirection.DOWN;
				break;
			case DOWN:
				this.direction = ShapeDirection.RIGHT;
				break;
			case RIGHT:
				this.direction = ShapeDirection.UP;
				break;
		}
	}
	
	public abstract List<SquareCoordinate> getCoordinates();
	
	public abstract TextColor getColor();
	
}
