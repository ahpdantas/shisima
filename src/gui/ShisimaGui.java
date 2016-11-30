package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logic.ShisimaGame;
import net.NetworkService;
import net.ReceiverListener;
import logic.Piece;


public class ShisimaGui extends JPanel implements ReceiverListener
{
	private static final long serialVersionUID = 3114147670071466558L;
	private Image imgBackground;
	private ShisimaGame shisimaGame;
	
	private static final int PIECES_START_X = 50;
	private static final int PIECES_START_Y = 50;

	private List<PieceGui> piecesGui = new ArrayList<PieceGui>();
	
	public ShisimaGui(NetworkService network){
		//background
		URL urlBackgroundImg = getClass().getResource("/shisima/img/board.png");
		network.addReceiverListener(this);
		this.imgBackground = new ImageIcon(urlBackgroundImg).getImage();
		
		// create chess game
		this.shisimaGame = new ShisimaGame(network);
		
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
	public void changeGameState() {
		this.shisimaGame.changeGameState();
	}

	/**
	 * @return current game state
	 */
	public int getGameState() {
		return this.shisimaGame.getGameState();
	}
	
	public int getPlayer(){
		return this.shisimaGame.getPlayer();
	}
	
	
	/**
	 * convert logical position into x,y coordinate
	 * @param row
	 * @param column
	 * @return coordinate for position
	 */
	public static Coordinates convertToCoordinate(int row, int column){
		Coordinates c = null; 
		
		System.out.println("row: "+ row + "column:" + column);
		
		switch( column ){
		case Piece.COLUMN_1:
			switch( row ){
			case Piece.ROW_1:
				c = new Coordinates(69,69);
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
				c = new Coordinates(175,25);
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
	public int convertCoordinatesToColumn(int x, int y){
		int PieceSize = 60;		
		
		if( x > 25 && x < 25 + PieceSize ){
			return 1;
		}
		else if ( x > 69 && x < 69 + PieceSize){
			return 1;
		}
		else if( x > 175 && x < 175 + PieceSize){
			return 2;
		}
		else if( x > 281 && x < 281 + PieceSize ){
			return 3;
		}
		else if( x > 325 && x < 325 + PieceSize){
			return 3;
		}
		else{
			return 0;
		}
	}
	
	/**
	 * convert x,y coordinate into logical row
	 * @param c
	 * @return logical row for coordinate
	 */
	public int convertCoodinatesToRow(int x, int y){
		int PieceSize = 60;		
		
		if( y > 25 && y < 25 + PieceSize ){
			return 1;
		}
		else if ( y > 69 && y < 69 + PieceSize){
			return 1;
		}
		else if( y > 175 && y < 175 + PieceSize){
			return 2;
		}
		else if( y > 281 && y < 281 + PieceSize ){
			return 3;
		}
		else if( y > 325 && y < 325 + PieceSize){
			return 3;
		}
		else{
			return 0;
		}
	}
	
	void detectWinner(){
		int winner = this.shisimaGame.getWinner();
		if( winner != ShisimaGame.UNKNOWN ){
			if( winner == ShisimaGame.NO_WINNER ){
				JOptionPane.showMessageDialog(null, "There is no Winner in this game!!!");
			} else if( winner == this.shisimaGame.getPlayer() ){
				JOptionPane.showMessageDialog(null, "You win!!!");
			} else {
				JOptionPane.showMessageDialog(null, "You loose!!!");
			}
			int reply = JOptionPane.showConfirmDialog(null, "Do you really want to play a new Shisima Game?", "Play Again", JOptionPane.YES_NO_OPTION);
	        if (reply == JOptionPane.YES_OPTION) {
	        	// create chess game
	        	this.shisimaGame.restart();
	        	piecesGui.clear();	        	
	    		for (Piece piece : this.shisimaGame.getPieces()) {
	    			createAndAddGuiPiece(piece);
	    		}
	 			this.repaint();
	        }
	        
		}
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
		int targetRow = this.convertCoodinatesToRow(x,y);
		int targetColumn = this.convertCoordinatesToColumn(x,y);
		
		if( targetRow < Piece.ROW_1 || targetRow > Piece.ROW_3 || targetColumn < Piece.COLUMN_1 || targetColumn > Piece.COLUMN_3){
			// reset piece position if move is not valid
			dragPiece.resetToUnderlyingPiecePosition();
		
		}else{
			//change model and update gui piece afterwards
			System.out.println("moving piece to "+targetRow+"/"+targetColumn);
			this.shisimaGame.movePiece(
					dragPiece.getPiece().getRow(),
					dragPiece.getPiece().getColumn(),
					targetRow, 
					targetColumn);

			dragPiece.resetToUnderlyingPiecePosition();
			detectWinner();
		}
		
	}
	
	@Override
	public void receive(String message) {
		String[] msg = message.split(":");
				
		if( msg.length >= 3 && !msg[2].isEmpty() ){
			String[] s = msg[2].split(",");
			
			System.out.println("Receiving packet");
			System.out.println("Player: "+this.getPlayer());
			
			for( PieceGui p: piecesGui){
				if ( p.getPiece().getId() == Integer.valueOf(s[0])
						&& p.getPiece().getType() != this.getPlayer() ){
					p.getPiece().setRowColumn(Integer.valueOf(s[1]), Integer.valueOf(s[2]));
					p.resetToUnderlyingPiecePosition();
					this.changeGameState();
					this.repaint();
				}
			}
			
			detectWinner();
		}
	}
}
