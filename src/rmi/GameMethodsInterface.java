package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import rmi.GameInstance.States;

public interface GameMethodsInterface extends Remote {
	PlayerInstance login() throws RemoteException;
	void movePiece(PlayerInstance p, int pieceId, int row, int column)throws RemoteException;
	States getGameState(PlayerInstance p)throws RemoteException;
	void startGame(PlayerInstance p)throws RemoteException;
	void closeGame(PlayerInstance p)throws RemoteException;
	void restartGame(PlayerInstance p)throws RemoteException;
	void sendMessage(PlayerInstance p, String userName, String msg)throws RemoteException;
}

