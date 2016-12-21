package net;

import java.rmi.RemoteException;

import rmi.GameInstance.States;
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
		try {
			this.remoteGame.movePiece(this.player, pieceId, row, column);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void startGame(){
		System.out.println("I'm player2");
		try {
			this.remoteGame.startGame(this.player);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public States getGameState(){
		States s = null;
		try {
			s = this.remoteGame.getGameState(this.player);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	
	public void restartGame(){
		try {
			this.remoteGame.restartGame(this.player);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closeGame(){
		try {
			this.remoteGame.closeGame(this.player);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String userName, String message){
		try {
			this.remoteGame.sendMessage(this.player, userName, message);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
