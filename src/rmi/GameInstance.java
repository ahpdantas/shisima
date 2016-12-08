package rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.UUID;
import net.ReceiverRmiInterface;

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
	
	public void transmit(PlayerInstance p, String msg){
		try{
			Registry registry = LocateRegistry.getRegistry(null);
			ReceiverRmiInterface receiver = (ReceiverRmiInterface) registry.lookup(getOpponentID(p).toString());
			receiver.receive(msg);
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
