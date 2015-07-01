
public class Knight extends Piece{
	public Knight(Boolean colour, int xp, int yp){
		xpos = xp;
		ypos = yp;
		isMoved = false;
		isWhite = colour; //keep track of colour
		if(isWhite == true){
			isActive = true; //if it's white then it starts as active and useable on the first turn
		}
		else{
			isActive = false; // if it's black then it goes second and cannot be used first turn, hence it is not active
		}
		isRemoved = false; //keeping track of whether the piece has been removed from play
		identity = "Knight";
	}
	//generic move for knight, only 'L' shaped plays, 2 spaces in one direction then 1 in another (just not backwards)
	public static String[] move(int x1, int y1){
		String [] possibleInd = new String[8];
		if(((x1 + 2) < 8) && (y1 + 1) < 8){
			possibleInd[0] = Integer.toString(x1 + 2) + "," + Integer.toString(y1 + 1);
		}
		else{
			possibleInd[0] = "10,10";
		}
		if(((x1 + 2) < 8) && (y1 - 1) >= 0){
			possibleInd[1] = Integer.toString(x1 + 2) + "," + Integer.toString(y1 - 1);
		}
		else{
			possibleInd[1] = "10,10";
		}if(((x1 - 2) >= 0) && (y1 + 1) < 8){
			possibleInd[2] = Integer.toString(x1 - 2) + "," + Integer.toString(y1 + 1);
		}
		else{
			possibleInd[2] = "10,10";
		}if(((x1 - 2) >= 0) && (y1 - 1) >= 0){
			possibleInd[3] = Integer.toString(x1 - 2) + "," + Integer.toString(y1 - 1);
		}
		else{
			possibleInd[3] = "10,10";
		}if(((x1 + 1) < 8) && (y1 + 2) < 8){
			possibleInd[4] = Integer.toString(x1 + 1) + "," + Integer.toString(y1 + 2);
		}
		else{
			possibleInd[4] = "10,10";
		}if(((x1 - 1) >= 0) && (y1 + 2) < 8){
			possibleInd[5] = Integer.toString(x1 - 1) + "," + Integer.toString(y1 + 2);
		}
		else{
			possibleInd[5] = "10,10";
		}if(((x1 + 1) < 8) && (y1 - 2) >= 0){
			possibleInd[6] = Integer.toString(x1 + 1) + "," + Integer.toString(y1 - 2);
		}
		else{
			possibleInd[6] = "10,10";
		}if(((x1 - 1) >= 0) && (y1 - 2) >= 0){
			possibleInd[7] = Integer.toString(x1 - 1) + "," + Integer.toString(y1 - 2);
		}
		else{
			possibleInd[7] = "10,10";
		}
		
		return possibleInd;		
	}	
}
