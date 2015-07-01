import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Chess extends JFrame {
	private final short GAME_WIDTH = 700; //keep these all unchangeable
	private final short GAME_HEIGHT = 700;
	private final short MESSAGE_AREA_HEIGHT = 100;
	private final byte BOARD_SIZE = 8;
	
	private Canvas canvas;
	
	private Boolean pickUp = false; //is a piece currently picked up?
	private String currentPiece; //name of current piece
	private Color currentColour; //colour of current piece
	private Boolean currentWhite;
	private Boolean currentBlack;
	private int currentx;
	private int currenty;
	private Square[] squareList = new Square[64]; //make list of pieces and squares
	private Pawn[] pawnList = new Pawn[16];
	private Bishop[] bishopList = new Bishop[4];
	private King[] kingList = new King[2];
	private Knight[] knightList = new Knight[4];
	private Queen[] queenList = new Queen[2];
	private Rook[] rookList = new Rook[4];
	private Piece[] promotedPieces = new Piece[16];
	private int promotedCount = 0;
	private Piece cItem;
	private Boolean isWhiteActive = true;
	private JTextArea gameMessages = new JTextArea();
	private JTextArea playerInfo = new JTextArea();
	private Piece[][] currentPieces = {pawnList, bishopList, kingList, knightList, queenList, rookList, promotedPieces};
	private Piece[] removedWhite = new Piece[24]; //for removed pieces
	private Piece[] removedBlack = new Piece[24];
	private byte whiteRemovedCount;
	private byte blackRemovedCount;
	public static Boolean enPassant = false;
	public static Boolean enPassanted = false;
	public static Boolean up = false;
	private Boolean firstMove = false;
	public static int enPassantx = 10;
	public static int enPassanty = 10;
	private int oldx = 10;
	private int oldy = 10;
	public static Boolean blackCastling = true;
	public static Boolean whiteCastling = true;
	
	public Chess() { //constructor
		setTitle("Chess");
		setLayout(new BorderLayout());
		setResizable(false); //no resizing because it makes the buttons look horrible
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
		add(canvas, BorderLayout.CENTER); //this is going to have a JPanel to take up the main area with the buttons
		
		//JTextArea gameMessages = new JTextArea();
		gameMessages.setEditable(false);
		gameMessages.setBackground(canvas.getBackground());
		JScrollPane gameMessageScroll = new JScrollPane(gameMessages);
		gameMessageScroll.setBorder(new TitledBorder(new EtchedBorder(), "Game messages"));
		gameMessageScroll.setPreferredSize(new Dimension(GAME_WIDTH, MESSAGE_AREA_HEIGHT));
		add(gameMessageScroll, BorderLayout.PAGE_END); //this will show up at the bottom and alert the player of the moves made
		
		//JTextArea player1Info = new JTextArea();
		playerInfo.setEditable(false);
		playerInfo.setBackground(canvas.getBackground());
		JScrollPane playerInfoScroll = new JScrollPane(playerInfo);
		playerInfoScroll.setBorder(new TitledBorder(new EtchedBorder(), "Player info"));
		playerInfoScroll.setPreferredSize(new Dimension(GAME_WIDTH, MESSAGE_AREA_HEIGHT));
		add(playerInfoScroll, BorderLayout.PAGE_START); //this appears at the top of the page and will show player names, maybe captured pieces?
		
		Boolean check = true;
		for(Piece space : promotedPieces){
			space = new Pawn(check, 10, 10);
			if(check == true){
				check = false;
			}
			else if(check == false){
				check = true;
			}
		}
		
		JPanel board = new JPanel(new GridLayout(8,8)); //grid layout of buttons for board
		board.setPreferredSize(new Dimension(GAME_WIDTH,GAME_HEIGHT));
		add(board, BorderLayout.CENTER);
				
		Boolean colourButton = true; //this is used to alternate colours for board squares
		int count = 0;
		for(int i = 0; i < BOARD_SIZE; i++){
			for(int j = 0; j < BOARD_SIZE; j++){
				String pieceName = "";
				Square squarePiece = new Square(i,j); //create new square for each board square
				squarePiece.setText("");
				squarePiece.addActionListener(new SquaresListener()); //each square has an actionlistener for if it is clicked
				squareList[count] = squarePiece;
				if (j == 1 || j == 6){ //these are the columns that pawns populate at the start
					if(j == 1){ //create all pawns for white
						squarePiece.isOccupiedWhite = true;
					
							Pawn wPawn1 = new Pawn(true, 0, j);		
							pawnList[0] = wPawn1;
						
							Pawn wPawn2 = new Pawn(true, 1, j);	
							pawnList[1] = wPawn2;
						
							Pawn wPawn3 = new Pawn(true, 2, j);	
							pawnList[2] = wPawn3;
						
							Pawn wPawn4 = new Pawn(true, 3, j);	
							pawnList[3] = wPawn4;
						
							Pawn wPawn5 = new Pawn(true, 4, j);	
							pawnList[4] = wPawn5;
					
							Pawn wPawn6 = new Pawn(true, 5, j);	
							pawnList[5] = wPawn6;
						
							Pawn wPawn7 = new Pawn(true, 6, j);	
							pawnList[6] = wPawn7;
						
							Pawn wPawn8 = new Pawn(true, 7, j);	
							pawnList[7] = wPawn8;
						
					}
					if(j == 6){ //create all pawns for black
						squarePiece.isOccupiedBlack = true;						
							
							Pawn bPawn1 = new Pawn(false, 0, j);	
							pawnList[8] = bPawn1;	
						
							Pawn bPawn2 = new Pawn(false, 1, j);	
							pawnList[9] = bPawn2;
						
							Pawn bPawn3 = new Pawn(false, 2, j);	
							pawnList[10] = bPawn3;
						
							Pawn bPawn4 = new Pawn(false, 3, j);	
							pawnList[11] = bPawn4;
						
							Pawn bPawn5 = new Pawn(false, 4, j);	
							pawnList[12] = bPawn5;
						
							Pawn bPawn6 = new Pawn(false, 5, j);	
							pawnList[13] = bPawn6;
					
							Pawn bPawn7 = new Pawn(false, 6, j);	
							pawnList[14] = bPawn7;
						
							Pawn bPawn8 = new Pawn(false, 7, j);	
							pawnList[15] = bPawn8;
						
					}
					squarePiece.setText("Pawn"); //set piece name
				}				
				if ((j == 0 || j == 7) && (i == 0 || i == 7)){
					if (j == 0 && i == 0){
						squarePiece.isOccupiedWhite = true;
						Rook wRook1 = new Rook(true, i, j); //create white rooks
						rookList[0] = wRook1;
					}
					if (j == 7 && i == 0){
						squarePiece.isOccupiedBlack = true;
						Rook wRook2 = new Rook(true, i, j); //create white rooks
						rookList[1] = wRook2;
					}
					if (j == 0 && i == 7){
						squarePiece.isOccupiedWhite = true;
						Rook bRook1 = new Rook(false, i, j); //create black rooks
						rookList[2] = bRook1;
					}
					if (j == 7 && i == 7){
						squarePiece.isOccupiedBlack = true;
						Rook bRook2 = new Rook(false, i, j); //create black rooks
						rookList[3] = bRook2;
					}
					squarePiece.setText("Rook"); //set piece name
				}
				if ((j == 0 || j == 7) && (i == 1 || i == 6)){
					if (j == 0 && i == 1){
						squarePiece.isOccupiedWhite = true;
						Knight wKnight1 = new Knight(true, i, j); //create white Knight
						knightList[0] = wKnight1;
						}
					if (j == 7 && i == 1){
						squarePiece.isOccupiedBlack = true;
						Knight wKnight2 = new Knight(true, i, j); //create white Knight
						knightList[1] = wKnight2;
					}
					if (j == 0 && i == 6){
						squarePiece.isOccupiedWhite = true;
						Knight bKnight1 = new Knight(false, i, j); //create black Knight
						knightList[2] = bKnight1;
					}
					if (j == 7 && i == 6){
						squarePiece.isOccupiedBlack = true;
						Knight bKnight2 = new Knight(false, i, j); //create black Knight
						knightList[3] = bKnight2;
					}
					squarePiece.setText("Knight"); //set piece name
				}
				if ((j == 0 || j == 7) && (i == 2 || i == 5)){
					if (j == 0 && i == 2){
						squarePiece.isOccupiedWhite = true;
						Bishop wBishop1 = new Bishop(true, i, j); //create white Bishop
						bishopList[0] = wBishop1;
					}
					if (j == 7 && i == 2){
						squarePiece.isOccupiedBlack = true;
						Bishop wBishop2 = new Bishop(true, i, j); //create white Bishop
						bishopList[1] = wBishop2;
					}
					if (j == 0 && i == 5){
						squarePiece.isOccupiedWhite = true;
						Bishop bBishop1 = new Bishop(false, i, j); //create black Bishop
						bishopList[2] = bBishop1;
					}
					if (j == 7 && i == 5){
						squarePiece.isOccupiedBlack = true;
						Bishop bBishop2 = new Bishop(false, i, j); //create black Bishop
						bishopList[3] = bBishop2;
					}
					squarePiece.setText("Bishop"); //set piece name
				}
				if ((j == 0 || j == 7) && i == 4){
					if (j == 0){
						squarePiece.isOccupiedWhite = true;
						King wKing = new King(true, i, j); //create white king
						kingList[0] = wKing;
					}
					if (j == 7){
						squarePiece.isOccupiedBlack = true;
						King bKing = new King(false, i, j); //create black king
						kingList[1] = bKing;
					}
					squarePiece.setText("King"); //set piece name
				}
				if ((j == 0 || j == 7) && i == 3){
					if (j == 0){
						squarePiece.isOccupiedWhite = true;
						Queen wQueen = new Queen(true, i, j); //create white queen
						queenList[0] = wQueen;
					}
					if (j == 7){
						squarePiece.isOccupiedBlack = true;
						Queen bQueen = new Queen(false, i, j); //create black queen
						queenList[1] = bQueen;
					}
					squarePiece.setText("Queen"); //set piece name
				}
				if(j == 1 || j == 0){
					squarePiece.setForeground(Color.BLUE); //give one side blue colour
				}
				if(j == 7 || j == 6){
					squarePiece.setForeground(Color.RED); //give other side red colour
				
				}
				//create checkered board
				if (colourButton == true){
					squarePiece.setBackground(Color.BLACK); 
					squarePiece.colour = Color.BLACK;
					squarePiece.setOpaque(true);
					colourButton = false;
				}
				else {
					squarePiece.setBackground(Color.WHITE);
					squarePiece.colour = Color.white;
					squarePiece.setOpaque(true);
					colourButton = true;
				}
			
				
				board.add(squarePiece); //add square to board
				count++;
			}
			//following switches colours for checkered board
			if (colourButton == true){				
				colourButton = false;
			}
			else {
				colourButton = true;
			}
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //close when clicking x button
		//display board
		pack();
		setVisible(true);
	}
	
	class SquaresListener implements ActionListener {
		public void actionPerformed (ActionEvent event){ //if button is clicked, do following
			Square currentSquare = ((Square) event.getSource());
			Square oldSquare = currentSquare;
			if (pickUp == false){ //if there is a piece not already picked up
				ArrayList<Square> availMoves = new ArrayList<Square>(Arrays.asList(squareList));
				ArrayList<Square> illegalCheckMoves = new ArrayList<Square>();
				ArrayList<Square[]> potentialCheckMoves = new ArrayList<Square[]>();
				currentPiece = currentSquare.getText(); //get current piece name so we know what is picked up				
				currentColour = currentSquare.getForeground(); //get piece colour
				currentWhite = currentSquare.isOccupiedWhite;
				currentBlack = currentSquare.isOccupiedBlack;
				if((isWhiteActive == true && currentWhite == true) || (isWhiteActive == false && currentBlack == true)){ //check for active 'player'
					currentx = currentSquare.x;
					currenty = currentSquare.y;
					oldx = currentx;
					oldy = currenty;							
					int cx = currentSquare.x;
					int cy = currentSquare.y;
					int b = 0;
					if(currentPiece == ""){		//if square is empty do nothing as you cannot pick anything up			
					}
					else{						
						switch(currentPiece){							
						case "Pawn":
							String[] pawnAvailInds = new String[4];
							pawnAvailInds[0] = "10,10";
							pawnAvailInds[1] = "10,10";
							pawnAvailInds[2] = "10,10";
							pawnAvailInds[3] = "10,10"; 
							int c = 0;
							for(Pawn currentPawn : pawnList){
								if(cx == currentPawn.xpos && cy == currentPawn.ypos){ //is this the pawn we've picked up?
									cItem = currentPawn;
									if(currentPawn.isMoved == false){	//if pawn has not been moved before						
										pawnAvailInds = Pawn.firstMove(cx, cy); //use firstmove method so that pawn can move two spaces
										if(currentPawn.isWhite == true){ //remove ability to go backwards
											pawnAvailInds[2] = "10,10";
											pawnAvailInds[3] = "10,10";
											pawnAvailInds[5] = "10,10";
											pawnAvailInds[7] = "10,10";
										}
										else if(currentPawn.isWhite == false){
											pawnAvailInds[0] = "10,10";
											pawnAvailInds[1] = "10,10";
											pawnAvailInds[4] = "10,10";
											pawnAvailInds[6] = "10,10";//pawns can't go backwards
										}
										currentPawn.isMoved = true; //pawn has now been moved once so isMoved is true, firstMove() can no longer be used for this pawn
										firstMove = true; //used for enPassant
									}								
									else{
										pawnAvailInds = Pawn.move(cx, cy); //if pawn has moved before can only use move() method
										if(currentPawn.isWhite == true){ //remove ability to go backwards
											pawnAvailInds[1] = "10,10";											
											pawnAvailInds[5] = "10,10";
											pawnAvailInds[3] = "10,10";											
										}
										else if(currentPawn.isWhite == false){
											pawnAvailInds[0] = "10,10";											
											pawnAvailInds[4] = "10,10";
											pawnAvailInds[2] = "10,10";//pawns can't go backwards										
										}
									}
								}
							}
							for(String item : pawnAvailInds){
								if(item == "10,10"){
									c++; //see how many invalid indices there are
								}
							}						
							Square[] pawnAvailSquares = new Square[pawnAvailInds.length - c]; //create available squares array with length relative to valid moves
							for(String item : pawnAvailInds){
								if(item == null || item == "10,10" || pawnAvailSquares.length < 0){ //if item is invalid or there are no items
								}
								else{
									String[] parts = item.split(",");
									int part1 = Integer.parseInt(parts[0]);
									int part2 = Integer.parseInt(parts[1]); //get separate indices
									
									for(Square square : squareList){
										if (square.x == part1 && square.y == part2){ //if this square is the active one
											pawnAvailSquares[b] = square; //add square to available squares list
											b++; //update square count
										}
									}									
								}
							}
							//calculate legal moves from available squares
							availMoves = (currentSquare.determineIfAvailable(pawnAvailSquares, currentPiece, currentWhite, currentBlack, currentx, currenty));
							
							break;
						case "Rook":
							for(Rook currentRook : rookList){
								if(cx == currentRook.xpos && cy == currentRook.ypos){
									cItem = currentRook; //find current rook
								}
							}
							String[] rookAvailInds = Rook.move(cx, cy);								
							Square[] rookAvailSquares = new Square[rookAvailInds.length];							
							for(String item : rookAvailInds){
								if(item == null){									
								}
								else{
									String[] parts = item.split(",");
									int part1 = Integer.parseInt(parts[0]);
									int part2 = Integer.parseInt(parts[1]);									
									for(Square square : squareList){
										if (square.x == part1 && square.y == part2){
											rookAvailSquares[b] = square;
											b++;
										}
									}											
								}
							}						
							availMoves = (currentSquare.determineIfAvailable(rookAvailSquares, currentPiece, currentWhite, currentBlack, currentx, currenty));
							if(availMoves.size() > 0){
								for(Rook currentRook : rookList){
									if(cx == currentRook.xpos && cy == currentRook.ypos){
										currentRook.isMoved = true;
									}
								}
							}
							if(whiteCastling == true || blackCastling == true){
								byte wRookCastleCount = 0;
								byte bRookCastleCount = 0;
								for(Rook rook : rookList){
									if(rook.isWhite == true && rook.isMoved == true){
										wRookCastleCount++;
									}
									if(rook.isWhite == false && rook.isMoved == true){
										bRookCastleCount = 0;
									}
								}
								if(wRookCastleCount == 2){
									whiteCastling = false;
								}
								if(bRookCastleCount == 2){
									blackCastling = false;
								}
							}
							
							
							break;
						case "Queen":							
							for(Queen currentQueen : queenList){
								if(cx == currentQueen.xpos && cy == currentQueen.ypos){
									cItem = currentQueen;
								}
							}							
							String[] queenAvailInds = Queen.move(cx, cy);
							Square[] queenAvailSquares = new Square[queenAvailInds.length];							
							for(String item : queenAvailInds){
								if(item == null){									
								}
								else{
									String[] parts = item.split(",");
									int part1 = Integer.parseInt(parts[0]);
									int part2 = Integer.parseInt(parts[1]);									
									for(Square square : squareList){
										if (square.x == part1 && square.y == part2){
											queenAvailSquares[b] = square;
											b++;
										}
									}											
								}
							}
							availMoves = (currentSquare.determineIfAvailable(queenAvailSquares, currentPiece, currentWhite, currentBlack, currentx, currenty));
							
							break;
						case "Bishop":							
							for(Bishop currentBishop : bishopList){
								if(cx == currentBishop.xpos && cy == currentBishop.ypos){
									cItem = currentBishop;
								}
							}
							c = 0;
							String[] bishopAvailInds = Bishop.move(cx, cy);							
							for(String item : bishopAvailInds){
								if(item == "10,10"){
									c++;
								}
							}							
							Square[] bishopAvailSquares = new Square[bishopAvailInds.length - c];											
							for(String item : bishopAvailInds){
								if(item == null){									
								}
								else{
									String[] parts = item.split(",");
									int part1 = Integer.parseInt(parts[0]);
									int part2 = Integer.parseInt(parts[1]);									
									for(Square square : squareList){
										if (square.x == part1 && square.y == part2){
											bishopAvailSquares[b] = square;
											b++;
										}
									}											
								}
							}
							availMoves = (currentSquare.determineIfAvailable(bishopAvailSquares, currentPiece, currentWhite, currentBlack, currentx, currenty));
							break;
						case "Knight":							
							for(Knight currentKnight : knightList){
								if(cx == currentKnight.xpos && cy == currentKnight.ypos){
									cItem = currentKnight;
								}
							}							
							c = 0;
							String[] knightAvailInds = Knight.move(cx, cy);
							for(String item : knightAvailInds){
								if(item == "10,10"){
									c++;
								}
							}							
							Square[] knightAvailSquares = new Square[knightAvailInds.length - c];							
							for(String item : knightAvailInds){
								if(item == null || item == "10,10"){
								}
								else{
									String[] parts = item.split(",");
									int part1 = Integer.parseInt(parts[0]);
									int part2 = Integer.parseInt(parts[1]);									
									for(Square square : squareList){
											if (square.x == part1 && square.y == part2){
												knightAvailSquares[b] = square;
												b++;														
										}										
									}
								}
							}							
							availMoves = (currentSquare.determineIfAvailable(knightAvailSquares, currentPiece, currentWhite, currentBlack, currentx, currenty));
							
							break;
						case "King":							
							for(King currentKing : kingList){
								if(cx == currentKing.xpos && cy == currentKing.ypos){
									cItem = currentKing;
								}
							}				
							c = 0;						
							String[] kingAvailInds = King.move(cx, cy);		
							for(String item : kingAvailInds){
								if(item == "10,10"){
									c++;
								}
							}							
							Square[] kingAvailSquares = new Square[kingAvailInds.length - c];							
							for(String item : kingAvailInds){
								if(item == null || item == "10,10"){
								}
								else{
									String[] parts = item.split(",");
									int part1 = Integer.parseInt(parts[0]);
									int part2 = Integer.parseInt(parts[1]);									
									for(Square square : squareList){
											if (square.x == part1 && square.y == part2){
												kingAvailSquares[b] = square;
												b++;																				
										}										
									}
								}
							}							
							availMoves = (currentSquare.determineIfAvailable(kingAvailSquares, currentPiece, currentWhite, currentBlack, currentx, currenty));
							if(availMoves.size() > 0){
								for(King currentKing : kingList){
									if(cx == currentKing.xpos && cy == currentKing.ypos){
										currentKing.isMoved = true;
									}
								}							
								//code to check all other available moves against king so cannot move into check
								/*String[] possibleCheckers = {};
								for(Piece[] type : currentPieces){
									for(Piece checker : type){
										if(checker == null){
											
										}
										else{
											switch(checker.identity){
												case "Pawn":
													possibleCheckers = Pawn.move(cx, cy);
													break;
												case "Rook":
													possibleCheckers = Rook.move(cx, cy);
													break;
												case "Queen":
													possibleCheckers = Queen.move(cx, cy);
													break;
												case "Bishop":
													possibleCheckers = Bishop.move(cx, cy);
													break;
												case "King":
													possibleCheckers = King.move(cx, cy);
													break;
												case "Knight":
													possibleCheckers = Knight.move(cx, cy);
													break;
											}
										}
										c = 0;	
										for(String item : possibleCheckers){
											if(item == "10,10"){
												c++;
											}
										}		
										b = 0;
										Square[] potentialCheckSquares = new Square[possibleCheckers.length - c];							
										for(String item : possibleCheckers){
											if(item == null || item == "10,10"){
											}
											else{
												String[] parts = item.split(",");
												int part1 = Integer.parseInt(parts[0]);
												int part2 = Integer.parseInt(parts[1]);									
												for(Square square : squareList){
													if(square == null){
														
													}
													else{
														if((currentWhite == true && square.isOccupiedBlack) ||(currentBlack == true && square.isOccupiedWhite)){
															if (square.x == part1 && square.y == part2){
																potentialCheckSquares[b] = square;
																b++;																				
															}	
														}
													}
												}
											}
										}
										potentialCheckMoves.add(potentialCheckSquares);
									}
								}
								
								for(Square[] sqList : potentialCheckMoves){
									if(sqList == null){
										
									}
									else{
										for(Square square : sqList){	
											if(square == null){
												
											}
											else{
												for(Square kingMove : availMoves){	
													if((square.x == kingMove.x) && (square.y == kingMove.y)){
														illegalCheckMoves.add(square);
													}
												}
											}
										}
									}
								}
								System.out.println(illegalCheckMoves.size());*/
							}							
							break;							
						}												
						for(Square square : availMoves){
							if(square == null){								
							}
							else{
								square.setBackground(Color.YELLOW); //set square background to yellow for all legal moves								
							}
						}
						for(Square square : illegalCheckMoves){
							if(square == null){								
							}
							else{
								square.setBackground(Color.RED); //set square background to yellow for all legal moves								
							}
						}
						if(availMoves.size() == 0){ //if there are no legal moves for this piece do nothing but inform player
							gameMessages.append("There are no valid moves for this piece" + "\n"); 
						}
						else{ //if there are legal moves available, 'pick up' piece
							currentSquare.setText(""); //set the square that has had the piece removed from to have no text
							pickUp = true; //pickUp is now true as a piece has been picked up
							currentSquare.isOccupiedWhite = false;
							currentSquare.isOccupiedBlack = false; //square is no longer occupied
						}									
					}
				}
				
				else{					
					gameMessages.append("It is not your turn" + "\n"); // inform the player if they try to pick up an inactive piece					
				}
			}				
			else if(pickUp == true){ //if piece is already picked up
				enPassant = false; //when putting down a new piece we reset enPassant to false as it can only be true for one turn
				currentSquare = ((Square) event.getSource());
				if(currentSquare.getBackground() == Color.YELLOW){ //we only want to put pieces down on legal squares
					int cx = currentSquare.x;
					int cy = currentSquare.y;	
					if(currentPiece == "King"){
						if((((cx == oldx + 2) || (cx == oldx - 2)) && ((blackCastling == true && currentBlack == true) || (whiteCastling == true && currentWhite == true)))){
							for(Square square : squareList){
								if(cx == oldx + 2){
									if(square.x == 7 && square.y == cy){
										square.setText("");
										square.isOccupiedWhite = false;
										square.isOccupiedBlack = false;
										for(Rook castleRook : rookList){
											if(castleRook.xpos == square.x && castleRook.ypos == cy){
												castleRook.xpos = cx - 1;
											}
										}
									}
									if(square.x == cx - 1 && square.y == cy){
										square.setText("Rook");
										square.isOccupiedWhite = currentWhite;
										square.isOccupiedBlack = currentBlack;
									}
								}
								
								if(cx == oldx - 2){
									if(square.x == 0 && square.y == cy){
										square.setText("");
										square.isOccupiedWhite = false;
										square.isOccupiedBlack = false;
										for(Rook castleRook : rookList){
											if(castleRook.xpos == square.x && castleRook.ypos == cy){
												castleRook.xpos = cx + 1;
											}
										}
									}
									if(square.x == cx + 1 && square.y == cy){
										square.setText("Rook");
										square.isOccupiedWhite = currentWhite;
										square.isOccupiedBlack = currentBlack;
									}
								}
							}		
						}
						if(Chess.blackCastling == true || Chess.whiteCastling == true){
							for(King king : kingList){
								if(king.isMoved == true){
									if(king.isWhite == true){
										Chess.whiteCastling = false;
										
									}
									if(king.isWhite == false){
										Chess.blackCastling = false;
									}
								}
							}
						}
					}
					
					if(enPassanted == true){ //has something been 'enpassanted'?
						enPassanted = false; //reset to false
						if(cx != oldx){							 
							for(Pawn item : pawnList){
								if(item.xpos == enPassantx && item.ypos == enPassanty){ //find piece that has been enpassanted
									item.isRemoved = true;
									item.xpos = 10;
									item.ypos = 10;
									if(item.isWhite == false){
										playerInfo.append("Removed black " + item.identity + "\n");
										removedBlack[blackRemovedCount] = item;
										blackRemovedCount++;
									}
									if(item.isWhite == true){
										playerInfo.append("Removed white " + item.identity + "\n");
										removedWhite[whiteRemovedCount] = item;
										whiteRemovedCount++;
									}
								}
							}						
							for(Square square : squareList){
								if(square.x == enPassantx && square.y == enPassanty){
									square.isOccupiedBlack = false;
									square.isOccupiedWhite = false;
									square.setText(""); //remove text from square that had the piece removed
								}
							}
						}
					}
					//if a piece is being taken
					if((currentWhite == true && currentSquare.isOccupiedBlack == true) || (currentBlack == true && currentSquare.isOccupiedWhite == true)){
						for(int i = 0; i < 6; i++){
							Piece[] currentArray = currentPieces[i]; 
							for(Piece item : currentArray){						
								if(item.xpos == cx && item.ypos == cy){
									if(item.identity == "King"){
										gameMessages.append("Game over! The king is taken!");
										for(Square thing : squareList){
											thing.setEnabled(false); //if king is taken, game is over and all buttons are disabled to prevent continuing
										}
									}
									else{//if game is not over because king is taken, continue and remove pieces				
										item.isRemoved = true;
										item.xpos = 10;
										item.ypos = 10;
										if(currentSquare.isOccupiedBlack == true){
											playerInfo.append("Removed black " + item.identity + "\n");
											removedBlack[blackRemovedCount] = item;
											currentSquare.isOccupiedBlack = false;
											blackRemovedCount++;
										}
										if(currentSquare.isOccupiedWhite == true){
											playerInfo.append("Removed white " + item.identity + "\n");
											removedWhite[whiteRemovedCount] = item;
											currentSquare.isOccupiedWhite = false;
											whiteRemovedCount++;
										}
									}
								}
							}
						}
					}
					if(currentPiece == "Pawn" && (cy == 0 || cy == 7)){ //if pawn has reached either end of the board
						Boolean needPromote = true; //since the pawn has reached this point it needs a promotion
						Boolean whitePawn = true; //used later to give new piece the correct colour
						for(Pawn pawn : pawnList){
							if(pawn.xpos == oldx && pawn.ypos == oldy){
								if(pawn.isWhite == true){
									whitePawn = true;
								}
								else{
									whitePawn = false;
								}
								pawn.isRemoved = true;
								pawn.xpos = 10;
								pawn.ypos = 10;
								if(pawn.isWhite == false){
									playerInfo.append("Replacing black pawn... " + "\n");
									removedBlack[blackRemovedCount] = pawn;
									blackRemovedCount++;
								}
								if(pawn.isWhite == true){
									playerInfo.append("Replacing white pawn... " + "\n");
									removedWhite[whiteRemovedCount] = pawn;
									whiteRemovedCount++;
								}
								currentSquare.setText("");
								break;
							}
						}
						while(needPromote == true){ //keep looping until valid input is given by the user
							String type = JOptionPane.showInputDialog(canvas, "What piece do you want to promote to?", null); //create pop up to ask user what they want to promote to
							type = type.substring(0,1).toUpperCase() + type.substring(1).toLowerCase(); //change input so that the program can recognise it
							if(type.equals("Queen") || type.equals("Rook") || type.equals("Bishop") || type.equals("Knight")){	 //is the input valid?
								if(type.equals("Queen")){
									promotedPieces[promotedCount] = new Queen(whitePawn, cx, cy);	
								}
								if(type.equals("Rook")){
									promotedPieces[promotedCount] = new Rook(whitePawn, cx, cy);
								}
								if(type.equals("Bishop")){
									promotedPieces[promotedCount] = new Bishop(whitePawn, cx, cy);
								}
								if(type.equals("Knight")){
									promotedPieces[promotedCount] = new Knight(whitePawn, cx, cy);
								}
								promotedCount++; //add new pieces to promotedPieces then increase count
								currentPiece = type; //currentPiece now set to new piece type
								needPromote = false; //promotion over
							}
							else{
								gameMessages.append("That is not a valid type" + "\n"); //let player know if they enter an invalid type
							}
						}					
					}
					if(firstMove == true && ((cy == (oldy+2)) || (cy == (oldy-2)))){ //if first move used to move two spaces
						firstMove = false; //reset firstMove
						if(currentPiece == "Pawn"){
							for(Pawn cPawn : pawnList){
								//check for enPassant
								if((cPawn.xpos == cx+1 || cPawn.xpos == cx-1) && cPawn.ypos == cy){
									//check if pawns are opposite colours so they can be captured
									if((currentWhite == true && cPawn.isWhite == false) || (currentBlack == true && cPawn.isWhite == true)){
										enPassant = true;
										enPassantx = cx;
										enPassanty = cy;
										if(cPawn.xpos == cx+1){ //need to know which direction pawn is in comparison to capturing pawn
											up = true;
										}
										else if(cPawn.xpos == cx-1){
											up = false;
										}
									}									
								}
							}
						}
					}
					currentSquare.setText(currentPiece); //set square that has been clicked to have the text of the piece
					currentSquare.setForeground(currentColour); //set square that has been clicked to have the correct colour
					currentSquare.isOccupiedWhite = currentWhite;
					currentSquare.isOccupiedBlack = currentBlack;
					pickUp = false; //pickUp is now false as the piece has been put down
					cItem.xpos = cx;
					cItem.ypos = cy;
					if(isWhiteActive == true){
						isWhiteActive = false;
					}
					else{
						isWhiteActive = true; //change active player
					}
					
					for(Square square : squareList){
						square.setBackground(square.colour); //reset square colours so yellow doesn't remain					
					}
				}
				else{
					gameMessages.append("That is not a valid move" + "\n"); //let player know if it is not a valid move
				}				
			}			
		}
	}
	
	/*class BoardMouseListener implements MouseListener{ //do we need a mouselistener to see where mouse is? Could hover over squares?
		
	}*/

	public static void main(String[] args) {
		Chess chessboard = new Chess(); //create new instance of chess

	}

}
