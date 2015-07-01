import java.util.ArrayList;


public class King extends Piece {
	//Boolean isChecked; //could implement to alert player
	//Boolean checkmate; //could implement to end game
	//Boolean isMoved; //needs to be known for castleMove()
	
	public King(Boolean colour, int xp, int yp){
		xpos = xp;
		ypos = yp;
		//isChecked = false;
		//checkmate = false;
		isMoved = false;
		isWhite = colour; //keep track of colour
		if(isWhite == true){
			isActive = true; //if it's white then it starts as active and useable on the first turn
		}
		else{
			isActive = false; // if it's black then it goes second and cannot be used first turn, hence it is not active
		}
		isRemoved = false; //keeping track of whether the piece has been removed from play - this should end the game
		identity = "King";
		
	}
	//generic move for King, can only move one space in any direction
	public static String[] move(int x1, int y1){
		String defx = Integer.toString(x1);
		String defy = Integer.toString(y1);
		String [] possibleInd = new String[13];
		if((y1+1) < 8){
			possibleInd[0] = defx + "," + Integer.toString(y1+1);
		}
		else{
			possibleInd[0] = "10,10";
		}
		if(y1-1 >= 0){
			possibleInd[1] = defx + "," + Integer.toString(y1-1);
		}
		else{
			possibleInd[1] = "10,10";
		}
		if((x1+1) < 8){
			possibleInd[2] = Integer.toString(x1+1) + "," + defy;
		}
		else{
			possibleInd[2] = "10,10";
		}
		if(x1-1 >= 0){
			possibleInd[3] = Integer.toString(x1-1) + "," + defy;
		}
		else{
			possibleInd[3] = "10,10";
		}
		
		
		String[] castleMoves = castleMove(x1,y1);
		for(int i = 2; i < 7; i++){
			possibleInd[i+2] = castleMoves[i];
		}
		
	
		if((x1+1) < 8 && (y1+1) < 8){
			possibleInd[9] = Integer.toString(x1+1) + "," + Integer.toString(y1+1);
		}
		else{
			possibleInd[9] = "10,10";
		}
		if((x1+1) < 8 && (y1-1) >= 0){
			possibleInd[10] = Integer.toString(x1+1) + "," + Integer.toString(y1-1);
		}
		else{
			possibleInd[10] = "10,10";
		}
		if((x1-1) >= 0 && (y1+1) < 8){
			possibleInd[11] = Integer.toString(x1-1) + "," + Integer.toString(y1+1);
		}
		else{
			possibleInd[11] = "10,10";
		}
		if((x1-1) >= 0 && (y1-1) >= 0){
			possibleInd[12] = Integer.toString(x1-1) + "," + Integer.toString(y1-1);
		}
		else{
			possibleInd[12] = "10,10";
		}		
		return possibleInd;		
	}
	//special castleMove method, needs to be the first move of both King and Rook
	//King moves two spaces sideways then Rook moves to opposite side of King
	public static String[] castleMove(int x1, int y1){
		String [] castleInd = new String[7];
		String y = Integer.toString(y1);
		for(int i = 2; i < 7; i++){
			if(i != x1){
				castleInd[i] = Integer.toString(i) + "," + y;
			}
			else{
				castleInd[i] = "10,10";
			}
		}
		return castleInd;
	}
}
