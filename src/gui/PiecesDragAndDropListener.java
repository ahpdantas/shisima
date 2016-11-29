package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import logic.ShisimaGame;

public class PiecesDragAndDropListener implements MouseListener, MouseMotionListener {

	private List<PieceGui> piecesGui;
	private ShisimaGui shisimaGui;
	
	private PieceGui dragPiece;
	private int dragOffsetX;
	private int dragOffsetY;
	

	public PiecesDragAndDropListener(List<PieceGui> piecesGui, ShisimaGui shisimaGui) {
		this.piecesGui = piecesGui;
		this.shisimaGui = shisimaGui;
	}
	

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}
	
	@Override
	public void mouseMoved(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent evt) {
		int x = evt.getPoint().x;
		int y = evt.getPoint().y;
		
		// find out which piece to move.
		// we check the list from top to buttom
		// (therefore we itereate in reverse order)
		//
		for (int i = this.piecesGui.size()-1; i >= 0; i--) {
			PieceGui piece = this.piecesGui.get(i);
			
			if( mouseOverPiece(piece,x,y)){
				// calculate offset, because we do not want the drag piece
				// to jump with it's upper left corner to the current mouse
				// position
				//
				if( (	this.shisimaGui.getGameState() == ShisimaGame.GAME_STATE_PLAYER_1
						&& piece.getPiece().getType() == logic.Piece.TYPE_PLAYER_1
					) ||
					(	this.shisimaGui.getGameState() == ShisimaGame.GAME_STATE_PLAYER_2
							&& piece.getPiece().getType() == logic.Piece.TYPE_PLAYER_2
						)
					){
						this.dragOffsetX = x - piece.getX();
						this.dragOffsetY = y - piece.getY();
						this.dragPiece = piece;
				}
				break;
			}
		}
		
		// move drag piece to the top of the list
		if(this.dragPiece != null){
			this.piecesGui.remove( this.dragPiece );
			this.piecesGui.add(this.dragPiece);
		}
	}

	/**
	 * check whether the mouse is currently over this piece
	 * @param piece the playing piece
	 * @param x x coordinate of mouse
	 * @param y y coordinate of mouse
	 * @return true if mouse is over the piece
	 */
	private boolean mouseOverPiece(PieceGui piece, int x, int y) {
		return piece.getX() <= x 
			&& piece.getX()+piece.getWidth() >= x
			&& piece.getY() <= y
			&& piece.getY()+piece.getHeight() >= y;
	}

	@Override
	public void mouseReleased(MouseEvent evt) {
		if( this.dragPiece != null){
			int x = evt.getPoint().x - this.dragOffsetX;
			int y = evt.getPoint().y - this.dragOffsetY;
			
			// set game piece to the new location if possible
			//
			shisimaGui.setNewPieceLocation(this.dragPiece, x, y);
			this.shisimaGui.repaint();
			this.dragPiece = null;
		}
	}

	@Override
	public void mouseDragged(MouseEvent evt) {
		if(this.dragPiece != null){				
			int x = evt.getPoint().x - this.dragOffsetX;
			int y = evt.getPoint().y - this.dragOffsetY;
				
			System.out.println("row:"+shisimaGui.convertCoodinatesToRow(x, y)  
				+" column:"+shisimaGui.convertCoordinatesToColumn(x, y));
			
			this.dragPiece.setX(x);
			this.dragPiece.setY(y);
			
			this.shisimaGui.repaint();
		}
		
	}
}
