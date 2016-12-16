package net;

import java.rmi.Remote;
import java.rmi.RemoteException;

import rmi.PlayerInstance;

public interface UpdateGameStatusInterface extends Remote {
	void updatePieceMoviment(int pieceId, int row, int column) throws RemoteException;
}
