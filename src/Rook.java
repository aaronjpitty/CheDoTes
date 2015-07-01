
public class Rook extends Piece{
	Boolean isMoved; //need to keep track of if the rook has moved and can therefore perform castleMove
	public Rook(Boolean colour, int xp, int yp){
		xpos = xp;
		ypos = yp;
		isWhite = colour; //keep track of colour
		if(isWhite == true){
			isActive = true; //if it's white then it starts as active and useable on the first turn
		}
		else{
			isActive = false; // if it's black then it goes second and cannot be used first turn, hence it is not active
		}
		isRemoved = false; //keeping track of whether the piece has been removed from play
		identity = "Rook";
		isMoved = false; //starts as not having moved
	}
	//generic movement for Rook, any straight line as far as can go
	public static String[] move(int x1, int y1){
		String defx = Integer.toString(x1);
		String defy = Integer.toString(y1);
		String [] possibleInd = new String[16];
		for(int i = 0; i < 8; i++){			
			String possx = Integer.toString(i);
			String possy = Integer.toString(i);
			String s1 = possx + "," + defy;
			String s2 = defx + "," + possy;
			possibleInd[i] = s1;
			possibleInd[i+8] = s2;			
		}
		
		/*String[] castleMoves = castleMove(x1,y1);
		for(int i = 0; i < 8; i++){
			possibleInd[i+16] = castleMoves[i];
		}*/
		
		return possibleInd;
	}
	//special castleMove method, needs to be the first move of both King and Rook
	//King moves two spaces sideways then Rook moves to opposite side of King
	/*public static String[] castleMove(int x1, int y1){
		String [] castleInd = new String[6];
		String y = Integer.toString(y1);
		for(int i = 1; i < 6; i++){
			if(i != x1){
				castleInd[i] = Integer.toString(i) + "," + y;
			}
			else{
				castleInd[i] = "10,10";
			}
		}
		return castleInd;
	}*/
}
