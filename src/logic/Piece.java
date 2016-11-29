package logic;

public class Piece {

	public static final int TYPE_PLAYER_1 = 1;
	public static final int TYPE_PLAYER_2 = 2;
	
	private int type;

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
	
	
	public Piece(int type, int row, int column) {
		this.row = row;
		this.column = column;
		this.type = type;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setColumn(int column) {
		this.column = column;
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