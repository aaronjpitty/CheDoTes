import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JButton;


public class Square extends JButton {
	Boolean isOccupiedWhite; //keep track of whether there is a piece on this square
	Boolean isOccupiedBlack;
	Boolean isAvailable; //can another piece move on to this space?
	Color colour;
	int x; //could use this to alert players of what moves have just been made
	int y;
	
	public Square(int x1, int y1){
		isAvailable = false; //this will be updated when an item is picked up and will become true if that piece can move on to this square
		isOccupiedWhite = false; //this is updated if a piece is moved onto the square. Some changed in chess constructor
		isOccupiedBlack = false;
		colour = Color.black;
		x = x1;
		y = y1;
	}
	public ArrayList<Square> determineIfAvailable(Square[] squares, String pieceName, Boolean white, Boolean black, int x, int y){
		ArrayList<Square> maybeSquares = new ArrayList<Square>();
		ArrayList<Square> allowedSquares = new ArrayList<Square>();
		int[][] diagArray = new int[48][2];
		for(int[] item : diagArray){
			item[0] = 10;
			item[1] = 10;
		}
 		int blockNo = 0;
		for(Square piece : squares){
			if(piece == null || (piece.x == x && piece.y == y)){				
			}
			else{
				piece.isAvailable = true; //set all given squares to available to start with
			}			
		}
		for (Square pieceIndex : squares){	
			if(pieceIndex == null){
			}
			else{						
				if(pieceIndex.x > 7 || pieceIndex.y > 7 || pieceIndex.x < 0 || pieceIndex.y < 0){
					//determined this Square is unavailable already so do nothing
					pieceIndex.isAvailable = false;
				}
				else{	
					int checkx =  pieceIndex.x;
					int checky =  pieceIndex.y;
					if(pieceIndex.x == x && pieceIndex.y == y){ //if this is the same piece
					}
					else{
						if(pieceIndex.isAvailable == true){
							if((pieceIndex.isOccupiedWhite == true && white == true) || (pieceIndex.isOccupiedBlack == true && black == true)){
								pieceIndex.isAvailable = false; //determined this is a 'blocker' and can't be taken as it is the same colour
								diagArray[blockNo][0] = checkx; //add blocker indices to array
								diagArray[blockNo][1] = checky;
								blockNo++; //increase blocker number
							}
							else if((pieceIndex.isOccupiedWhite == true && black == true) || (pieceIndex.isOccupiedBlack == true && white == true)){
								if(pieceName == "Pawn"){
									pieceIndex.isAvailable = false; //pawn cannot take forwards, others not affected as this can be taken
								}
								else{
									pieceIndex.isAvailable = true; 
								}
								diagArray[blockNo][0] = checkx;
								diagArray[blockNo][1] = checky;
								blockNo++;								
							}
						}
					}				
					if(pieceName == "King"){
						if(checkx >= x+2 || checkx <= x-2){							
							if((black == true && Chess.blackCastling == true) || (white == true && Chess.whiteCastling == true)){
								pieceIndex.isAvailable = true;
							}
							else{
								pieceIndex.isAvailable = false;
							}
						}
					}
					if(pieceName == "Pawn"){
						if((checkx == x+1 && checky == y+1) || (checkx == x-1 && checky == y+1) || (checkx == x-1 && checky == y-1) || (checkx == x+1 && checky == y-1)){
							if((pieceIndex.isOccupiedWhite == true && black == true) || (pieceIndex.isOccupiedBlack == true && white == true)){
								//allow capture
								pieceIndex.isAvailable = true;
							}
							else{
								pieceIndex.isAvailable = false;
							}								
							Boolean pass = Chess.enPassant;
							if(pass == true){ //check if this pawn can perform enPassant									
								if((white == true && ((checkx == x+1 && checky == y+1) && Chess.up == false) || ((checkx == x-1 && checky == y+1) && Chess.up == true)) || (black == true && ((checkx == x+1 && checky == y-1) && Chess.up == false) || ((checkx == x-1 && checky == y-1) && Chess.up == true))){									
									pieceIndex.isAvailable = true;		
									Chess.enPassanted = true;
									Chess.enPassantx = checkx;
									if(white == true){
										Chess.enPassanty = checky - 1;
									}
									if(black == true){
										Chess.enPassanty = checky + 1;
									}
								}																												
							}							
						}
					}															
					if(pieceIndex.isAvailable == true){
						maybeSquares.add(pieceIndex);					
					}
				}
			}			
		}		
		for(Square checkSquare : maybeSquares){
			int checkx = checkSquare.x;
			int checky = checkSquare.y;		
					
			for(int[] item : diagArray){
				int index1 = item[0];
				int index2 = item[1];
				if(item[0] != 10 && item[1] != 10){
					for(int i = 0; i < 7; i++){
						if(index1 > x && index2 > y){
							if(checkx == x+i && checky == y+i && checkx > index1 && checky > index2){
								checkSquare.isAvailable = false;
							}
						}
						if(index1 > x && index2 < y){
							if(checkx == x+i && checky == y-i && checkx > index1 && checky < index2){
								checkSquare.isAvailable = false;
							}
						}
						if(index1 < x && index2 > y){
							if(checkx == x-i && checky == y+i && checkx < index1 && checky > index2){
								checkSquare.isAvailable = false;
							}
						}
						if(index1 < x && index2 < y){
							if(checkx == x-i && checky == y-i && checkx < index1 && checky < index2){
								checkSquare.isAvailable = false;
							}								
						}									
					}
					if(index1 == x && index2 > y){
						if((checkx == x) && (checky > index2)){									
							checkSquare.isAvailable = false;
						}								
					}
					else if(index2 == y && index1 > x){
						if(checky == y && (checkx > index1)){
							checkSquare.isAvailable = false;
						}
					}
					else if(index1 == x && index2 < y){
						if((checkx == x) && (checky < index2)){									
							checkSquare.isAvailable = false;
						}								
					}
					else if(index2 == y && index1 < x){
						if(checky == y && (checkx < index1)){
							checkSquare.isAvailable = false;
						}
					}
					
				}
			}
			
			if(checkSquare.isAvailable == true){
				allowedSquares.add(checkSquare);			
			}
		}		
		return allowedSquares;			
	}
	
}
