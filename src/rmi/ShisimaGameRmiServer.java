package rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ShisimaGameRmiServer implements GameMethodsInterface {
	private List<GameInstance> games = new ArrayList<GameInstance>();
	
	private GameInstance getNextFreeGame(){
		for (GameInstance game : this.games) {
			if( game.getState() == GameInstance.States.NOT_STARTED || 
					game.getState() == GameInstance.States.WAITING_PLAYER_2){
				return game;
			}
		}
		return null;
	}
	
	@Override
	public PlayerInstance login() {
		
		PlayerInstance p = new PlayerInstance();
		
		GameInstance game = getNextFreeGame();
		if( game != null ){
			p.setGameID(game.getSerialID());
			game.addPlayer(p);
		} else{
			game = new GameInstance();
			p.setGameID(game.getSerialID());
			game.addPlayer(p);
			games.add(game);
		}
		return p;
		
	}

	@Override
	public void movePiece(PlayerInstance p, int pieceId, int row, int column) {
		for (GameInstance game : this.games) {
			if( game.getSerialID().compareTo(p.getGameID()) == 0 ) {
				game.movePiece(p,pieceId,row,column);
			}
		}
	}
	
	@Override
	public void startGame(PlayerInstance p) {
		for (GameInstance game : this.games) {
			if( game.getSerialID().compareTo(p.getGameID()) == 0 ) {
				game.startGame(p);
			}
		}
	}
	

	@Override
	public void closeGame(PlayerInstance p) {
		for (GameInstance game : this.games) {
			if( game.getSerialID().compareTo(p.getGameID()) == 0 ) {
				game.closeGame(p);
			}
		}
		
	}

	
	@Override
	public void restartGame(PlayerInstance p) {
		for (GameInstance game : this.games) {
			if( game.getSerialID().compareTo(p.getGameID()) == 0 ) {
				game.restartGame(p);
			}
		}
		
	}
	
	@Override
	public void sendMessage(PlayerInstance p, String userName, String msg) {
		for (GameInstance game : this.games) {
			if( game.getSerialID().compareTo(p.getGameID()) == 0 ) {
				game.sendMessage(p, userName, msg);			}
		}
	}
	
	public static void main( String args[] ){
		
		try{
			Registry registry = LocateRegistry.getRegistry();
			ShisimaGameRmiServer server = new ShisimaGameRmiServer();
			GameMethodsInterface stub = (GameMethodsInterface) UnicastRemoteObject.exportObject(server, 0);
			registry.rebind("GameMethods", stub);
		}catch(Exception e){
			e.printStackTrace();
		}
        System.err.println("Shisima Server Ready...");
	}

}
