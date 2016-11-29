package gui;

import java.awt.Image;
import logic.Piece;

public class PieceGui {
	
	private Image img;
	private Piece piece;
	private Coordinates c; 
	
	public PieceGui(Image img, Piece piece) {
		this.img = img;
		this.piece = piece;
		
		this.resetToUnderlyingPiecePosition();
	}

	public Image getImage() {
		return img;
	}

	public int getX() {
		return c.x;
	}

	public int getY() {
		return c.y;
	}

	public void setX(int x) {
		this.c.x = x;
	}

	public void setY(int y) {
		this.c.y = y;
	}

	public int getWidth() {
		return img.getHeight(null);
	}

	public int getHeight() {
		return img.getHeight(null);
	}
	
	@Override
	public String toString() {
		return this.piece+" "+c.x+"/"+c.y;
	}

	/**
	 * move the gui piece back to the coordinates that
	 * correspond with the underlying piece's row and column
	 */
	public void resetToUnderlyingPiecePosition() {
		this.c = ShisimaGui.convertToCoordinate( piece.getRow(), piece.getColumn());
	}
	
	public Piece getPiece() {
		return piece;
	}


}
