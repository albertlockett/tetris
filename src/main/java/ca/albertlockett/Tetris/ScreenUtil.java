package ca.albertlockett.Tetris;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;

public class ScreenUtil {

	// TODO: Define these globally so they're not hard coded in 2 places
	private static TextColor bgColor1 = new TextColor.RGB(0, 0, 0);
	private static TextColor bgColor2 = new TextColor.RGB(50, 50, 50);
	
	public static boolean isBlockAtLocation(Screen screen, int x, int y) {
		
		// check back character
		TextCharacter backChar = screen.getBackCharacter(x, y);
		if(backChar != null) {
			TextColor fgBackColor = backChar.getForegroundColor();
			TextColor bgBackColor = backChar.getBackgroundColor();
			
			if(!fgBackColor.equals(bgColor1) && !fgBackColor.equals(bgColor2)) {
				return true;
			}
			if(!bgBackColor.equals(bgColor1) && !bgBackColor.equals(bgColor2)) {
				return true;
			}
		}
		
		// check front character
		TextCharacter frontChar = screen.getFrontCharacter(x, y);
		if(frontChar != null) {
			TextColor fgFrontColor = frontChar.getForegroundColor();
			TextColor bgFrontColor = frontChar.getBackgroundColor();
			
			if(!fgFrontColor.equals(bgColor1) && !fgFrontColor.equals(bgColor2)) {
				return true;
			}
			
			if(!bgFrontColor.equals(bgColor1) && !bgFrontColor.equals(bgColor2)) {
				return true;
			}
		}
		
		return false;
	}
	
	
	public static boolean isRowComplete(Screen screen, int row) {
		TerminalSize screenSize = screen.getTerminalSize();
		boolean rowComplete = true;
		for(int col = 0; col < screenSize.getColumns(); col++) {
			if(!ScreenUtil.isBlockAtLocation(screen, row, col)) {
				rowComplete = false;
			}
		}
		return rowComplete;
	}
	
}
