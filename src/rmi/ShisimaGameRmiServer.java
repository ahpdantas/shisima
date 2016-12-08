package rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ShisimaGameRmiServer implements ShisimaGameInterface {
	private List<ShisimaServer> games = new ArrayList<ShisimaServer>();
	
	private ShisimaServer getNextFreeGame(){
		for (ShisimaServer game : this.games) {
			if( game.getState() == ShisimaServer.States.NOT_STARTED || 
					game.getState() == ShisimaServer.States.WAITING_PLAYER_2){
				return game;
			}
		}
		return null;
	}
	
	@Override
	public Player login() {
		
		Player p = new Player();
		
		ShisimaServer game = getNextFreeGame();
		if( game != null ){
			p.setGameID(game.getSerialID());
			game.addPlayer(p);
		} else{
			game = new ShisimaServer();
			p.setGameID(game.getSerialID());
			game.addPlayer(p);
			games.add(game);
		}
		return p;
		
	}
	
	@Override
	public void publish(Player p, String msg) {
		System.out.println("Publishing message...");
		for (ShisimaServer game : this.games) {
			if( game.getSerialID().compareTo(p.getGameID()) == 0 ) {
				System.out.println("Saving message...");
				game.publish(p.getUserId(),msg);
			}
		}
	}
	
	@Override
	public String receive(Player p) {
		for (ShisimaServer game : this.games) {
			if(game.getSerialID().compareTo(p.getGameID()) == 0){
				return game.receive(p.getUserId());
			}
		}
		return null;
	}
	
	public static void main( String args[] ){
		try{
			
			Registry registry = LocateRegistry.createRegistry(1099);
			
			ShisimaGameRmiServer server = new ShisimaGameRmiServer();
			ShisimaGameInterface stub = (ShisimaGameInterface) UnicastRemoteObject.exportObject(server, 0);
			
			 // Bind the remote object's stub in the registry
            registry.bind("Shisima", stub);

            System.err.println("Shisima Server Ready...");
		}catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
	}

}
