package net;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UpdateGameStatusInterface extends Remote {
	void updatePieceMoviment(int pieceId, int row, int column) throws RemoteException;
	void startGame() throws RemoteException;
}
