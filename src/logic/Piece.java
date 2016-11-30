package logic;

import java.util.ArrayList;
import java.util.List;

import utils.Coordinates;

public class Piece {
	private List<Coordinates> moves = new ArrayList<Coordinates>();
	
	public static final int TYPE_PLAYER_1 = 1;
	public static final int TYPE_PLAYER_2 = 2;
	
	public static final int PIECE_1 = 1;
	public static final int PIECE_2 = 2;
	public static final int PIECE_3 = 3;
	
	
	private int type;
	private int id;
	
	//Chess is played on a square board of
	//eight rows (called ranks and denoted with numbers 1 to 8)
	//and eight columns (called files and denoted with letters a to h) of squares.
	private int row;
	
	public static final int ROW_1 = 1;
	public static final int ROW_2 = 2;
	public static final int ROW_3 = 3;
		
	private int column;
	
	public static final int COLUMN_1 = 1;
	public static final int COLUMN_2 = 2;
	public static final int COLUMN_3 = 3;
	
	
	public Piece(int type, int id, int row, int column) {
		this.row = row;
		this.column = column;
		this.type = type;
		this.id = id;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	
	public void setRowColumn( int row, int column){
		this.row = row;
		this.column = column;
		this.moves.add(new Coordinates(row, column));
		
	}
	
	public List<Coordinates> getMoves() {
		return moves;
	}

	@Override
	public String toString() {
		
		String strType = "unknown";
		switch (this.type) {
			case TYPE_PLAYER_1: strType = "P1";break;
			case TYPE_PLAYER_2: strType = "P2";break;
		}
		
		String strRow = getRowString(this.row);
		String strColumn = getColumnString(this.column);
		
		return strType+" "+strRow+"/"+strColumn;
	}

	public int getType() {
		return this.type;
	}
	
	public int getId() {
		return this.id;
	}
	
	public static String getRowString(int row){
		String strRow = "unknown";
		switch (row) {
			case ROW_1: strRow = "1";break;
			case ROW_2: strRow = "2";break;
			case ROW_3: strRow = "3";break;
		}
		return strRow;
	}
	
	public static String getColumnString(int column){
		String strColumn = "unknown";
		switch (column) {
			case COLUMN_1: strColumn = "1";break;
			case COLUMN_2: strColumn = "2";break;
			case COLUMN_3: strColumn = "3";break;
		}
		return strColumn;
	}

}