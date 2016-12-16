package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import listeners.PiecesDragAndDropListener;
import logic.ShisimaGame;
import net.RemoteGameService;
import net.RestartGameInterface;
import net.UpdateGameStatusInterface;
import utils.Coordinates;
import logic.Piece;


public class ShisimaGui extends JPanel implements UpdateGameStatusInterface
{
	private static final long serialVersionUID = 3114147670071466558L;
	private Image imgBackground;
	private ShisimaGame shisimaGame;
	private JLabel instruction;
	private JLabel gamesPlayed;
	private JLabel wins;
	
	private static final int GAME_BORDER_X = 50;
	private static final int GAME_BORDER_y = 50;
	
	private static final int GAME_BOARD_X = 150;
	private static final int GAME_BOARD_y = 100;
	
	
	private static final int PIECES_START_X = GAME_BOARD_X + GAME_BORDER_X;
	private static final int PIECES_START_Y = GAME_BOARD_y + GAME_BORDER_y;

	private List<PieceGui> piecesGui = new ArrayList<PieceGui>();
	
	public ShisimaGui(RemoteGameService remoteGame ){
		
		registerRmiMethods(remoteGame);
		//background
		URL urlBackgroundImg = getClass().getResource("/shisima/img/board.png");
		//network.addReceiverListener(this);
		
		this.imgBackground = new ImageIcon(urlBackgroundImg).getImage();
		
		// create chess game
		this.shisimaGame = new ShisimaGame(remoteGame);
		
		this.setLayout(null);
		
		this.instruction = new JLabel("Waiting another player...", SwingConstants.CENTER);
		this.add(this.instruction);
		this.instruction.setBounds(220, 25, 360, 50);
		this.instruction.setFont(new Font("Arial", Font.BOLD, 24));
		
		this.gamesPlayed = new JLabel("Games: " + this.shisimaGame.getGamesPlayed());
		this.add(this.gamesPlayed);
		this.gamesPlayed.setBounds(30, 100, 100, 50);
		this.gamesPlayed.setFont(new Font("Arial", Font.BOLD, 20));
		
		this.wins = new JLabel("Wins: " + this.shisimaGame.getWins() );
		this.add(this.wins);
		this.wins.setBounds(30,160,100,50);
		this.wins.setFont(new Font("Arial", Font.BOLD, 20));
				
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
		
	private void registerRmiMethods(RemoteGameService remoteGame){
		
		try {
			Registry registry = LocateRegistry.getRegistry();
			UpdateGameStatusInterface stub = (UpdateGameStatusInterface) UnicastRemoteObject.exportObject(this, 0);
			// Bind the remote object's stub in the registry
            registry.rebind(remoteGame.getPlayer().getUserId().toString()+":update", stub);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
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
		
		x = x - GAME_BOARD_X;
		System.out.println("x: "+x);
		
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
		
		y = y - GAME_BOARD_y;
		System.out.println("y: "+y+"\n");
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
				this.shisimaGame.setWins(this.shisimaGame.getWins() + 1);
			} else {
				JOptionPane.showMessageDialog(null, "You loose!!!");
			}
			this.shisimaGame.setGamesPlayed(this.shisimaGame.getGamesPlayed() + 1);
			this.gamesPlayed.setText("Games: "+this.shisimaGame.getGamesPlayed());
			this.wins.setText("Wins: "+this.shisimaGame.getWins());
			
			int reply = JOptionPane.showConfirmDialog(null, "Do you really want to play a new Shisima Game?", "Play Again", JOptionPane.YES_NO_OPTION);
	        if (reply == JOptionPane.YES_OPTION) {
	        	// create chess game
	        	this.shisimaGame.restart();
	        	if( this.shisimaGame.getPlayer() == shisimaGame.PLAYER_1){
	        		this.instruction.setText("Start the game");
	        	}else{
	        		this.instruction.setText("Wait the opponent's move");
	        	}
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
			if( this.shisimaGame.movePiece(
					dragPiece.getPiece().getRow(),
					dragPiece.getPiece().getColumn(),
					targetRow, 
					targetColumn) ){
				this.instruction.setText("Wait the opponent's move");
			}
			
			dragPiece.resetToUnderlyingPiecePosition();
			detectWinner();
		}
		
	}
	
	@Override
	public void updatePieceMoviment(int pieceId, int row, int column) throws RemoteException {
		
		for( PieceGui p: piecesGui){
			if ( p.getPiece().getId() == pieceId 
					&& p.getPiece().getType() != this.getPlayer() ){
				p.getPiece().setRowColumn(row, column);
				this.instruction.setText("Your move");
				p.resetToUnderlyingPiecePosition();
				this.changeGameState();
				this.repaint();
			}
		}
		detectWinner();
	}

	@Override
	public void startGame() {
		JOptionPane.showMessageDialog(null, "Other player detected. Starting Shisima Game!!!");
		this.instruction.setText("Start the game");
		this.changeGameState();
	}

}
