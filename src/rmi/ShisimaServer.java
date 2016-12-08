package rmi;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public class ShisimaServer {
	public enum States{
		NOT_STARTED, WAITING_PLAYER_2,RUNNING_GAME;
	};
	
	private UUID serialID;
	private List<Message> messages = new ArrayList<Message>();
	private States state = States.NOT_STARTED;

	public ShisimaServer(){
		this.serialID = UUID.randomUUID();
	}
	
	public void addPlayer(Player p){
		switch( this.state){
		case NOT_STARTED:
			this.state = States.WAITING_PLAYER_2;
			p.setGameID(this.serialID);
			p.setType(Player.Type.PLAYER_1);
			System.out.println("Player 1(ID: "+p.getUserId()+") registered in Game ID: "+p.getGameID());
			break;
		case WAITING_PLAYER_2:
			p.setGameID(this.serialID);
			p.setType(Player.Type.PLAYER_2);
			System.out.println("Player 2(ID: "+p.getUserId()+") registered in Game ID: "+p.getGameID());
			this.state = States.RUNNING_GAME;
			break;
		}
	}
	
	public void publish(UUID userID, String msg){
		this.messages.add(new Message(userID,msg));
	}
	
	public String receive(UUID userID){
		for( Message m : this.messages ){
			if( m.from.compareTo(userID) != 0 ){
				this.messages.remove(m);
				return m.msg;
			}
		}
		return null;
	}
	
	public UUID getSerialID() {
		return serialID;
	}
	
	public States getState() {
		return state;
	}
	
}
