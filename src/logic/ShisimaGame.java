package logic;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import logic.Piece;
import gui.PieceGui;

public class ShisimaGame {
	private List<Piece> pieces = new ArrayList<Piece>();
	
	public ShisimaGame(){

		createAndAddPiece(Piece.TYPE_PLAYER_1, 0, 0);
		createAndAddPiece(Piece.TYPE_PLAYER_1, 0, 1);
		createAndAddPiece(Piece.TYPE_PLAYER_1, 0, 2);
		
		createAndAddPiece(Piece.TYPE_PLAYER_2, 2, 0);
		createAndAddPiece(Piece.TYPE_PLAYER_2, 2, 1);
		createAndAddPiece(Piece.TYPE_PLAYER_2, 2, 2);
		
	}
	
	/**
	 * create a game piece
	 * 
	 * @param color color constant
	 * @param type type constant
	 * @param x x position of upper left corner
	 * @param y y position of upper left corner
	 */
	private void createAndAddPiece(int player, int x, int y) {
		Piece piece = new Piece(player, x, y);
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
		
	}
	
	/**
	 * returns the first piece at the specified location that is not marked
	 * as 'captured'.
	 * @param row one of Piece.ROW_..
	 * @param column one of Piece.COLUMN_..
	 * @return the first not captured piece at the specified location
	 */
	private Piece getPieceAtLocation(int row, int column) {
		return null;
	}
	
	/**
	 * @return the internal list of pieces
	 */
	public List<Piece> getPieces() {
		return this.pieces;
	}

}
