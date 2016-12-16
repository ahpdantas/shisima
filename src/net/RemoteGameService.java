package net;

import java.rmi.RemoteException;
import rmi.GameMethodsInterface;
import rmi.PlayerInstance;

public class RemoteGameService {
	private PlayerInstance player;
	private GameMethodsInterface remoteGame;

	public RemoteGameService(GameMethodsInterface remoteGame){
		this.remoteGame = remoteGame;
		try {
			this.player = this.remoteGame.login();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public PlayerInstance getPlayer(){
		return this.player;
	}

	public PlayerInstance login(){
		try {
			return this.remoteGame.login();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void movePiece( int pieceId, int row, int column ){
		this.remoteGame.movePiece(this.player, pieceId, row, column);
	}
	
	public void startGame(){
		this.remoteGame.startGame(this.player);
	}
	
	public void restartGame(){
		this.remoteGame.restartGame(this.player);
	}
	
	public void closeGame(){
		this.remoteGame.closeGame(this.player);
	}
	
	public void sendMessage(String userName, String message){
		this.remoteGame.sendMessage(this.player, userName, message);
	}
	
}
