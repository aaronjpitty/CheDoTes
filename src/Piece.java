
public abstract class Piece {
	Boolean isWhite; //keep track of piece colour
	Boolean isActive; //whose turn is it and what can be used
	Boolean isRemoved; //has the piece been captured
	Boolean isMoved;
	String identity; //check what piece it is
	int xpos;
	int ypos;
	
	public static String[] move(int x1, int x2){
		String [] possibleInd = {};
		return possibleInd;
	}
		
	
}
