package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameMethodsInterface extends Remote {
	PlayerInstance login() throws RemoteException;
	void movePiece(PlayerInstance p, int pieceId, int row, int column)throws RemoteException;
	void startGame(PlayerInstance p)throws RemoteException;
	void closeGame(PlayerInstance p)throws RemoteException;
	void restartGame(PlayerInstance p)throws RemoteException;
	void sendMessage(PlayerInstance p, String userName, String msg)throws RemoteException;
}

