package rmi;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ShisimaGameRmiServer implements TransmitRmiInterface {
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
	public void transmit(PlayerInstance p, String msg) {
		System.out.println("Publishing message...");
		for (GameInstance game : this.games) {
			if( game.getSerialID().compareTo(p.getGameID()) == 0 ) {
				System.out.println("Saving message...");
				game.transmit(p,msg);
			}
		}
	}
	
	public static void main( String args[] ){
		
		try{
			Registry registry = LocateRegistry.getRegistry();
			ShisimaGameRmiServer server = new ShisimaGameRmiServer();
			TransmitRmiInterface stub = (TransmitRmiInterface) UnicastRemoteObject.exportObject(server, 0);
			registry.rebind("Shisima", stub);
		}catch(Exception e){
			e.printStackTrace();
		}
        System.err.println("Shisima Server Ready...");
	}

}
