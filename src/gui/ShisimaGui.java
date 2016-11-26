package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import logic.ShisimaGame;
import logic.Piece;


public class ShisimaGui extends JPanel
{
	private static final long serialVersionUID = 3114147670071466558L;
	private Image imgBackground;
	private ShisimaGame shisimaGame;
	
	private static final int PIECES_START_X = 50;
	private static final int PIECES_START_Y = 50;

	private List<PieceGui> piecesGui = new ArrayList<PieceGui>();
	
	public ShisimaGui(){
		//background
		URL urlBackgroundImg = getClass().getResource("/shisima/img/board.png");
		this.imgBackground = new ImageIcon(urlBackgroundImg).getImage();
		
		// create chess game
		this.shisimaGame = new ShisimaGame();
		
		//wrap game pieces into their graphical representation
		for (Piece piece : this.shisimaGame.getPieces()) {
			createAndAddGuiPiece(piece);
		}
		
		// add mouse listeners to enable drag and drop
		//
		PiecesDragAndDropListener listener = new PiecesDragAndDropListener(this.piecesGui, this);
		this.addMouseListener(listener);
		this.addMouseMotionListener(listener);
		
	}
		
	/**
	 * create a game piece
	 * 
	 * @param color color constant
	 * @param type type constant
	 * @param x x position of upper left corner
	 * @param y y position of upper left corner
	 */
	private void createAndAddGuiPiece(Piece piece) {
		Image img = this.getImageForPiece(piece.getType());
		PieceGui pieceGui = new PieceGui(img, piece);
		this.piecesGui.add(pieceGui);
	}

	
	/**
	 * load image for given color and type. This method translates the color and
	 * type information into a filename and loads that particular file.
	 * 
	 * @param color color constant
	 * @param type type constant
	 * @return image
	 */
	private Image getImageForPiece(int type) {
		String filename = "";

		switch (type) {
			case Piece.TYPE_PLAYER_1:
				filename += "player1";
				break;
			case Piece.TYPE_PLAYER_2:
				filename += "player2";
				break;
		}
		filename += ".png";

		URL urlPieceImg = getClass().getResource("/shisima/img/" + filename);
		return new ImageIcon(urlPieceImg).getImage();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(this.imgBackground, 0, 0, null);
		for (PieceGui piece : this.piecesGui) {
			g.drawImage(piece.getImage(), piece.getX(), piece.getY(), null);
		}
	}
	
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(this.imgBackground.getWidth(null), this.imgBackground.getHeight(null));
    }
    
	/**
	 * switches between the different game states 
	 */
	/*public void changeGameState() {
		this.chessGame.changeGameState();
		this.lblGameState.setText(this.getGameStateAsText());
	}*/

	/**
	 * @return current game state
	 */
/*	public int getGameState() {
		return this.chessGame.getGameState();
	}
	
	
	
	*/
	/**
	 * convert logical position into x,y coordinate
	 * @param column
	 * @param row
	 * @return coordinate for position
	 */
	public static Coordinates convertToCoordinate(int column, int row){
		Coordinates c = null; 
		switch( column ){
		case Piece.COLUMN_1:
			switch( row ){
			case Piece.ROW_1:
				c = new Coordinates(175,25);
				break;
			case Piece.ROW_2:
				c = new Coordinates(25,175);
				break;
			case Piece.ROW_3:
				c = new Coordinates(69,281);
				break;
			}
			break;
		case Piece.COLUMN_2:
			switch( row ){
			case Piece.ROW_1:
				c = new Coordinates(69,69);
				break;
			case Piece.ROW_2:
				c = new Coordinates(175,175);
				break;
			case Piece.ROW_3:
				c = new Coordinates(175,325);
				break;
			}
			break;
		case Piece.COLUMN_3:
			switch( row ){
			case Piece.ROW_1:
				c = new Coordinates(281,69);
				break;
			case Piece.ROW_2:
				c = new Coordinates(325,175);
				break;
			case Piece.ROW_3:
				c = new Coordinates(281,281);
				break;
			}
			break;
		}
		
		return new Coordinates(PIECES_START_X + c.x, PIECES_START_Y + c.y);
	}
	
	/**
	 * convert x,y coordinate into logical column
	 * @param c
	 * @return logical column for coordinate
	 */
	public static int convertCoordinatesToColumn(int x, int y){
		return 0;
		
	}
	
	/**
	 * convert x,y coordinate into logical row
	 * @param c
	 * @return logical row for coordinate
	 */
	public static int convertCoodinatesToRow(int x, int y){
		return 0;
	}

	/**
	 * change location of given piece, if the location is valid.
	 * If the location is not valid, move the piece back to its original
	 * position.
	 * @param dragPiece
	 * @param x
	 * @param y
	 */
	public void setNewPieceLocation(PieceGui dragPiece, int x, int y) {
		int targetRow = ShisimaGui.convertCoodinatesToRow(x,y);
		int targetColumn = ShisimaGui.convertCoordinatesToColumn(x,y);
		
		if( targetRow < Piece.ROW_1 || targetRow > Piece.ROW_3 || targetColumn < Piece.COLUMN_1 || targetColumn > Piece.COLUMN_3){
			// reset piece position if move is not valid
			dragPiece.resetToUnderlyingPiecePosition();
		
		}else{
			//change model and update gui piece afterwards
			System.out.println("moving piece to "+targetRow+"/"+targetColumn);
			this.shisimaGame.movePiece(
					dragPiece.getPiece().getRow(), dragPiece.getPiece().getColumn()
					, targetRow, targetColumn);
			dragPiece.resetToUnderlyingPiecePosition();
		}
	}

}
