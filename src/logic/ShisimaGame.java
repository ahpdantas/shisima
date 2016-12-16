package logic;

import java.util.ArrayList;
import java.util.List;

import logic.Piece;
import net.RemoteGameService;
import rmi.GameMethodsInterface;
import rmi.PlayerInstance;
import utils.Coordinates;

public class ShisimaGame  {
	private int gameState = GAME_NOT_STARTED;
	private int player = PLAYER_1;
	private int gamesPlayed = 0;
	private int wins = 0;
	private int winner = UNKNOWN;
	private RemoteGameService gameService;
	
	public static final int GAME_NOT_STARTED = 0;
	public static final int GAME_STATE_PLAYER_1 = 1;
	public static final int GAME_STATE_PLAYER_2 = 2;
	
	public static final int UNKNOWN = 0;
	public static final int PLAYER_1 = 1;
	public static final int PLAYER_2 = 2;
	public static final int NO_WINNER = 3;
	
	private List<Piece> pieces = new ArrayList<Piece>();
	
	public ShisimaGame(RemoteGameService gameService){

		this.gameService = gameService;
		
		if( this.gameService.getPlayer().getType() == PlayerInstance.Type.PLAYER_1 ){
			this.player = PLAYER_1;
			System.out.println("I'm player1");
			
		} else if( this.gameService.getPlayer().getType() == PlayerInstance.Type.PLAYER_2 ){
			this.player = PLAYER_2;
			this.gameState = GAME_STATE_PLAYER_1;
			this.gameService.startGame();
		}
		
		CreatePieces();
	}
	
	private void CreatePieces(){
		createAndAddPiece(Piece.TYPE_PLAYER_1, Piece.PIECE_1, Piece.ROW_1, Piece.COLUMN_1);
		createAndAddPiece(Piece.TYPE_PLAYER_1, Piece.PIECE_2, Piece.ROW_1, Piece.COLUMN_2);
		createAndAddPiece(Piece.TYPE_PLAYER_1, Piece.PIECE_3, Piece.ROW_1, Piece.COLUMN_3);
		
		createAndAddPiece(Piece.TYPE_PLAYER_2, Piece.PIECE_1, Piece.ROW_3, Piece.COLUMN_1);
		createAndAddPiece(Piece.TYPE_PLAYER_2, Piece.PIECE_2, Piece.ROW_3, Piece.COLUMN_2);
		createAndAddPiece(Piece.TYPE_PLAYER_2, Piece.PIECE_3, Piece.ROW_3, Piece.COLUMN_3);
	}
	
	public void restart(){
		
    	if( this.player == ShisimaGame.PLAYER_1 ){
    		this.player = ShisimaGame.PLAYER_2;
    	}else if( this.player == ShisimaGame.PLAYER_2 ){
    		this.player = ShisimaGame.PLAYER_1;
    	}
    	
    	this.gameState = GAME_STATE_PLAYER_1;
    	this.winner = UNKNOWN;
    	
    	pieces.clear();
    	CreatePieces();
	}
	
	/**
	 * create a game piece
	 * 
	 * @param player
	 * @param row on of Pieces.ROW_..
	 * @param column on of Pieces.COLUMN_..
	 */
	private void createAndAddPiece(int player, int id, int row, int column) {
		Piece piece = new Piece(player, id, row, column);
		this.pieces.add(piece);
	}
	
	/**
	 * Move piece to the specified location. If the target location is occupied
	 * by an opponent piece, that piece is marked as 'captured'
	 * @param sourceRow the source row (Piece.ROW_..) of the piece to move
	 * @param sourceColumn the source column (Piece.COLUMN_..) of the piece to move
	 * @param targetRow the target row (Piece.ROW_..)
	 * @param targetColumn the target column (Piece.COLUMN_..)
	 */
	public boolean movePiece(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
		Piece piece = getPieceAtLocation(sourceRow, sourceColumn);
		Piece targetPiece = getPieceAtLocation(targetRow, targetColumn);
		
		if( targetPiece == null ){
			if( ((targetRow == sourceRow )&&( Math.abs(targetColumn- sourceColumn) <= 1 ))||
					((targetColumn == sourceColumn )&&( Math.abs(targetRow- sourceRow) <= 1 ))||
					( targetColumn == Piece.COLUMN_2)&&(targetRow == Piece.ROW_2)||
					( sourceColumn == Piece.COLUMN_2)&&(sourceRow == Piece.ROW_2)){
				piece.setRowColumn(targetRow, targetColumn);
				this.changeGameState();
				this.sendGameStatus(piece.getId(), targetRow, targetColumn);
				return true;
			}
		}
		return false;
	}
	
	public Boolean tieCondition(List<Coordinates> coordinates){
		int[] numberOfOcurrences;
		
		numberOfOcurrences = new int[9];
		
		for( Coordinates c: coordinates ){
			switch( c.x ){
			case Piece.ROW_1:
				switch(c.y){
				case Piece.COLUMN_1:
					numberOfOcurrences[0]++;
					break;
				case Piece.COLUMN_2:
					numberOfOcurrences[1]++;
					break;
				case Piece.COLUMN_3:
					numberOfOcurrences[2]++;
					break;
				}
				break;
			case Piece.ROW_2:
				switch(c.y){
				case Piece.COLUMN_1:
					numberOfOcurrences[3]++;
					break;
				case Piece.COLUMN_2:
					numberOfOcurrences[4]++;
					break;
				case Piece.COLUMN_3:
					numberOfOcurrences[5]++;
					break;
				}
				break;
			case Piece.ROW_3:
				switch(c.y){
				case Piece.COLUMN_1:
					numberOfOcurrences[6]++;
					break;
				case Piece.COLUMN_2:
					numberOfOcurrences[7]++;
					break;
				case Piece.COLUMN_3:
					numberOfOcurrences[8]++;
					break;
				}
				break;
			}
		}
		
		for( int c : numberOfOcurrences ){
			if( c >= 3 ){
				this.setWinner(NO_WINNER);
				return true;
			}
		}
		
		return false;
	}
	
	public void detectWinner(){
		Piece piece = getPieceAtLocation(Piece.ROW_2, Piece.COLUMN_2);
		Piece piece2;
		Piece piece3;
		
		for (Piece p : this.pieces) {
			if( tieCondition(p.getMoves()) ){
				return;
			}
		}
		
		if( piece != null ){
			piece2 = getPieceAtLocation(Piece.ROW_1, Piece.COLUMN_1 );
			if( piece2 != null && piece2.getType() == piece.getType() ){
				piece3 = getPieceAtLocation(Piece.ROW_3, Piece.COLUMN_3 );
				if( piece3 != null && piece3.getType() == piece2.getType() ){
					System.out.println("Player "+piece3.getType() +"Wins");
					this.setWinner(piece3.getType());
					return;
				}
			}
			
			piece2 = getPieceAtLocation(Piece.ROW_1, Piece.COLUMN_2 );
			if( piece2 != null && piece2.getType() == piece.getType() ){
				piece3 = getPieceAtLocation(Piece.ROW_3, Piece.COLUMN_2 );
				if( piece3 != null && piece3.getType() == piece2.getType() ){
					System.out.println("Player "+piece3.getType() +"Wins");
					this.setWinner(piece3.getType());
					return;
				}
			}
			
			piece2 = getPieceAtLocation(Piece.ROW_2, Piece.COLUMN_1 );
			if( piece2 != null && piece2.getType() == piece.getType() ){
				piece3 = getPieceAtLocation(Piece.ROW_2, Piece.COLUMN_3 );
				if( piece3 != null && piece3.getType() == piece2.getType() ){
					System.out.println("Player "+piece3.getType() +"Wins");
					this.setWinner(piece3.getType());
					return;
				}
			}
			
			piece2 = getPieceAtLocation(Piece.ROW_3, Piece.COLUMN_1 );
			if( piece2 != null && piece2.getType() == piece.getType() ){
				piece3 = getPieceAtLocation(Piece.ROW_1, Piece.COLUMN_3 );
				if( piece3 != null && piece3.getType() == piece2.getType() ){
					System.out.println("Player "+piece3.getType() +"Wins");
					this.setWinner(piece3.getType());
					return;
				}
			}
		}
	}
	
	
	/**
	 * returns the first piece at the specified location that is not marked
	 * as 'captured'.
	 * @param row one of Piece.ROW_..
	 * @param column one of Piece.COLUMN_..
	 * @return the first not captured piece at the specified location
	 */
	private Piece getPieceAtLocation(int row, int column) {
		for (Piece piece : this.pieces) {
			if( piece.getRow() == row
					&& piece.getColumn() == column ){
				return piece;
			}
		}
		return null;
	}
	
	/**
	 * @return the internal list of pieces
	 */
	public List<Piece> getPieces() {
		return this.pieces;
	}
	

	/**
	 * @return current game state (one of ChessGame.GAME_STATE_..)
	 */
	public int getGameState() {
		return this.gameState;
	}
	
	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}
	
	/**
	 * switches the game state from ChessGame.GAME_STATE_PLAYER_1 to
	 * ChessGame.GAME_STATE_PLAYER_2 and vice versa.
	 */
	public void changeGameState() {
		
		switch (this.gameState) {
			case GAME_NOT_STARTED:
				this.gameState = GAME_STATE_PLAYER_1;
			break;
			case GAME_STATE_PLAYER_1:
				this.gameState = GAME_STATE_PLAYER_2;
				break;
			case GAME_STATE_PLAYER_2:
				this.gameState = GAME_STATE_PLAYER_1;
				break;
			default:
				throw new IllegalStateException("unknown game state:" + this.gameState);
		}
		System.out.println("Changing GameState:"+ this.gameState);
		
		this.detectWinner();
	}
	
	public void sendGameStatus(int pieceId, int row, int column){
		try{
			this.gameService.movePiece(pieceId, row, column);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public int getWinner() {
		return winner;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}

	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public void setGamesPlayed(int gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

}
