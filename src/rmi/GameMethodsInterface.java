package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameMethodsInterface extends Remote {
	PlayerInstance login() throws RemoteException;
	void movePiece(PlayerInstance p, int pieceId, int row, int column);
	void startGame(PlayerInstance p);
	void closeGame(PlayerInstance p);
	void restartGame(PlayerInstance p);
	void sendMessage(PlayerInstance p, String userName, String msg);
}

