
public class Pawn extends Piece{
	//Boolean isMoved; //need to keep track of movement to know if it can perform firstMove()
	public Pawn(Boolean colour, int xp, int yp){
		isWhite = colour; //how to keep track of colour
		xpos = xp;
		ypos = yp;
		if(isWhite == true){
			isActive = true; //if it's white then it starts as active and useable on the first turn
		}
		else{
			isActive = false; // if it's black then it goes second and cannot be used first turn, hence it is not active
		}
		isRemoved = false; //keeping track of whether the piece has been removed from play
		identity = "Pawn"; 
		isMoved = false; //starts as not having moved
	}

	//generic movement of one space
	public static String[] move(int x1, int y1){
		String defx = Integer.toString(x1);
		String defy = Integer.toString(y1);
		String [] possibleInd = new String[6];
		if((y1+1) < 8){
			possibleInd[0] = defx + "," + Integer.toString(y1 + 1);
		}
		else{
			possibleInd[0] = "10,10";
		}
		if((y1-1) >= 0){
			possibleInd[1] = defx + "," + Integer.toString(y1 - 1);
		}
		else{
			possibleInd[1] = "10,10";
		}
		String [] possibleCap = capture(x1,y1);
		possibleInd[2] = possibleCap[0];
		possibleInd[3] = possibleCap[1];
		possibleInd[4] = possibleCap[2];
		possibleInd[5] = possibleCap[3];
		
		return possibleInd;
	}
	//firstMove allows pawn on it's first movement to jump forward two spaces
	public static String[] firstMove(int x1, int y1){
		String defx = Integer.toString(x1);
		String defy = Integer.toString(y1);
		String [] possibleInd = new String[8];
		if((y1+2) < 8){
			possibleInd[0] = defx + "," + Integer.toString(y1 + 2);
			possibleInd[1] = defx + "," + Integer.toString(y1 + 1);
		}
		else if((y1+1) < 8){
			possibleInd[0] = "10,10";
			possibleInd[1] = defx + "," + Integer.toString(y1 + 1);
		}
		else{
			possibleInd[0] = "10,10";
			possibleInd[1] = "10,10";
		}
		if((y1-2) >= 0){
			possibleInd[2] = defx + "," + Integer.toString(y1 - 2);
			possibleInd[3] = defx + "," + Integer.toString(y1 - 1);
		}
		else if((y1-1) >= 0){
			possibleInd[2] = "10,10";
			possibleInd[3] = defx + "," + Integer.toString(y1 - 1);
		}
		else{
			possibleInd[2] = "10,10";
			possibleInd[3] = "10,10";
		}
		
		String [] possibleCap = capture(x1,y1);
		possibleInd[4] = possibleCap[0];
		possibleInd[5] = possibleCap[1];
		possibleInd[6] = possibleCap[2];
		possibleInd[7] = possibleCap[3];
		
		return possibleInd;
	}
	//pawns can only capture a piece by moving diagonally onto it, therefore this needs a separate method
	public static String[] capture(int x1, int y1){
		String [] possibleCap = new String[4];
		if((x1+1) < 8){
			possibleCap[0] = Integer.toString(x1+1) + "," + Integer.toString(y1+1);
			possibleCap[1] = Integer.toString(x1+1) + "," + Integer.toString(y1-1);
		}
		else{
			possibleCap[0] = "10,10";
			possibleCap[1] = "10,10";
		}
		if((x1-1) >= 0){
			possibleCap[2] = Integer.toString(x1-1) + "," + Integer.toString(y1+1);
			possibleCap[3] = Integer.toString(x1-1) + "," + Integer.toString(y1-1);
		}
		else{
			possibleCap[2] = "10,10";
			possibleCap[3] = "10,10";
		}
		
		return possibleCap;
	}

}
