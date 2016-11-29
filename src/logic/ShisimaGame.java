package logic;

import java.util.ArrayList;
import java.util.List;
import logic.Piece;

public class ShisimaGame {
	private int gameState = GAME_STATE_PLAYER_1;
	public static final int GAME_STATE_PLAYER_1 = 0;
	public static final int GAME_STATE_PLAYER_2 = 1;
	
	private List<Piece> pieces = new ArrayList<Piece>();
	
	public ShisimaGame(){

		createAndAddPiece(Piece.TYPE_PLAYER_1, Piece.ROW_1, Piece.COLUMN_1);
		createAndAddPiece(Piece.TYPE_PLAYER_1, Piece.ROW_1, Piece.COLUMN_2);
		createAndAddPiece(Piece.TYPE_PLAYER_1, Piece.ROW_1, Piece.COLUMN_3);
		
		createAndAddPiece(Piece.TYPE_PLAYER_2, Piece.ROW_3, Piece.COLUMN_1);
		createAndAddPiece(Piece.TYPE_PLAYER_2, Piece.ROW_3, Piece.COLUMN_2);
		createAndAddPiece(Piece.TYPE_PLAYER_2, Piece.ROW_3, Piece.COLUMN_3);
		
	}
	
	/**
	 * create a game piece
	 * 
	 * @param player
	 * @param row on of Pieces.ROW_..
	 * @param column on of Pieces.COLUMN_..
	 */
	private void createAndAddPiece(int player, int row, int column) {
		Piece piece = new Piece(player, row, column);
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
	public void movePiece(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
		Piece piece = getPieceAtLocation(sourceRow, sourceColumn);
		Piece targetPiece = getPieceAtLocation(targetRow, targetColumn);
		
		if( targetPiece == null ){
			if( ((targetRow == sourceRow )&&( Math.abs(targetColumn- sourceColumn) <= 1 ))||
					((targetColumn == sourceColumn )&&( Math.abs(targetRow- sourceRow) <= 1 ))||
					( targetColumn == Piece.COLUMN_2)&&(targetRow == Piece.ROW_2)||
					( sourceColumn == Piece.COLUMN_2)&&(sourceRow == Piece.ROW_2)){
				piece.setRow(targetRow);
				piece.setColumn(targetColumn);
				this.changeGameState();
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
	
	/**
	 * switches the game state from ChessGame.GAME_STATE_PLAYER_1 to
	 * ChessGame.GAME_STATE_PLAYER_2 and vice versa.
	 */
	public void changeGameState() {
		switch (this.gameState) {
			case GAME_STATE_PLAYER_1:
				this.gameState = GAME_STATE_PLAYER_2;
				break;
			case GAME_STATE_PLAYER_2:
				this.gameState = GAME_STATE_PLAYER_1;
				break;
			default:
				throw new IllegalStateException("unknown game state:" + this.gameState);
		}
	}

}
