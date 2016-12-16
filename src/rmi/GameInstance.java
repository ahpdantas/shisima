package rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.UUID;

import net.CloseGameInterface;
import net.RestartGameInterface;
import net.StartGameInterface;
import net.UpdateChatInterface;
import net.UpdateGameStatusInterface;

public class GameInstance {
	public enum States{
		NOT_STARTED, WAITING_PLAYER_2,RUNNING_GAME;
	};
	
	private UUID serialID;
	private States state = States.NOT_STARTED;
	private PlayerInstance player1;
	private PlayerInstance player2;

	public GameInstance(){
		this.serialID = UUID.randomUUID();
	}
	
	public void addPlayer(PlayerInstance p){
		switch( this.state){
		case NOT_STARTED:
			this.state = States.WAITING_PLAYER_2;
			p.setGameID(this.serialID);
			p.setType(PlayerInstance.Type.PLAYER_1);
			this.player1 = p;
			break;
		case WAITING_PLAYER_2:
			p.setGameID(this.serialID);
			p.setType(PlayerInstance.Type.PLAYER_2);
			this.player2 = p;
			this.state = States.RUNNING_GAME;
			break;
		}
	}
	
	private UUID getOpponentID(PlayerInstance p){
		if( p.getType() == this.player1.getType() ){
			return player2.getUserId();
		} else {
			return player1.getUserId();
		}
	}
	
	public void movePiece(PlayerInstance p, int pieceId, int row, int column){
		try{
			Registry registry = LocateRegistry.getRegistry(null);
			UpdateGameStatusInterface board = (UpdateGameStatusInterface) registry.lookup(getOpponentID(p).toString());
			board.updatePieceMoviment(pieceId, row, column);
		}catch( Exception e){
			e.printStackTrace();
		}
	}
	
	public void startGame(PlayerInstance p) {
		// TODO Auto-generated method stub
		try{
			Registry registry = LocateRegistry.getRegistry(null);
			StartGameInterface instance = (StartGameInterface) registry.lookup(getOpponentID(p).toString()+":start");
			instance.startGame();
		}catch( Exception e){
			e.printStackTrace();
		}
		
	}
	

	public void closeGame(PlayerInstance p) {
		try{
			Registry registry = LocateRegistry.getRegistry(null);
			CloseGameInterface instance = (CloseGameInterface) registry.lookup(getOpponentID(p).toString()+":close");
			instance.closeGame();
		}catch( Exception e){
			e.printStackTrace();
		}
		
	}

	
	public void restartGame(PlayerInstance p) {
		try{
			Registry registry = LocateRegistry.getRegistry(null);
			RestartGameInterface instance = (RestartGameInterface) registry.lookup(getOpponentID(p).toString()+":restart");
			instance.RestartGame();
		}catch( Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void sendMessage(PlayerInstance p, String userName, String msg) {
		try{
			Registry registry = LocateRegistry.getRegistry(null);
			UpdateChatInterface instance = (UpdateChatInterface) registry.lookup(getOpponentID(p).toString()+":chat");
			instance.updateChat(userName, msg);
		}catch( Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	public UUID getSerialID() {
		return serialID;
	}
	
	public States getState() {
		return state;
	}
	
}
