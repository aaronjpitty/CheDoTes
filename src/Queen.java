
public class Queen extends Piece{
	public Queen(Boolean colour, int xp, int yp){
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
		identity = "Queen";
	}
	//generic Queen movement, diagonal or straight, as far as can go
	public static String[] move(int x1, int y1){
		String [] possibleInd = new String[48];
		for(int i = 0; i < 8; i++){			
			String possx = Integer.toString(i);
			String possy = Integer.toString(i);
			String s1 = possx + "," + Integer.toString(y1);
			String s2 = Integer.toString(x1) + "," + possy;
			possibleInd[i] = s1;
			possibleInd[i+8] = s2;			
		}
		for(int i = 0; i < 8; i++){
			String possx1 = Integer.toString(x1 + i);
			String possx2 = Integer.toString(x1 - i);
			String possy1 = Integer.toString(y1 + i);
			String possy2 = Integer.toString(y1 - i);
			possibleInd[i+16] = possx1 + "," + possy1;
			possibleInd[i+24] = possx1 + "," + possy2;
			possibleInd[i+32] = possx2 + "," + possy1;
			possibleInd[i+40] = possx2 + "," + possy2;
			
		}
		return possibleInd;
	} 
}
