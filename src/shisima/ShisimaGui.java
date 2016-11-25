package shisima;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import shisima.PiecesDragAndDropListener;


public class ShisimaGui extends JPanel
{
	private static final long serialVersionUID = 3114147670071466558L;
	private Image imgBackground;
	private List<Piece> pieces = new ArrayList<Piece>();
	

	private static final int BOARD_START_X = 50;
	private static final int BOARD_START_Y = 50;
	
	private static final int TYPE_PLAYER_1 = 1;
	private static final int TYPE_PLAYER_2 = 2;
	
	public ShisimaGui(){
				
		URL urlBackgroundImg = getClass().getResource("/shisima/img/board.png");
		this.imgBackground = new ImageIcon(urlBackgroundImg).getImage();
		
		createAndAddPiece(TYPE_PLAYER_1, BOARD_START_X + 175, BOARD_START_Y + 25);
		createAndAddPiece(TYPE_PLAYER_1, BOARD_START_X + 69, BOARD_START_Y + 69);
		createAndAddPiece(TYPE_PLAYER_1, BOARD_START_X + 281, BOARD_START_Y + 69);
		
		createAndAddPiece(TYPE_PLAYER_2, BOARD_START_X + 69, BOARD_START_Y + 281);
		createAndAddPiece(TYPE_PLAYER_2, BOARD_START_X + 175, BOARD_START_Y + 325);
		createAndAddPiece(TYPE_PLAYER_2, BOARD_START_X + 281, BOARD_START_Y + 281);
		
		// add mouse listeners to enable drag and drop
		//
		PiecesDragAndDropListener listener = new PiecesDragAndDropListener(this.pieces, this);
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
	private void createAndAddPiece(int player, int x, int y) {
		Image img = this.getImageForPiece(player);
		Piece piece = new Piece(img, x, y);
		this.pieces.add(piece);
	}
	
	/**
	 * load image for given color and type. This method translates the color and
	 * type information into a filename and loads that particular file.
	 * 
	 * @param color color constant
	 * @param type type constant
	 * @return image
	 */
	private Image getImageForPiece(int player) {
		String filename = "";

		switch (player) {
			case TYPE_PLAYER_1:
				filename += "player1";
				break;
			case TYPE_PLAYER_2:
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
		for (Piece piece : this.pieces) {
			g.drawImage(piece.getImage(), piece.getX(), piece.getY(), null);
		}
	}
	
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(this.imgBackground.getWidth(null), this.imgBackground.getHeight(null));
    }

}
