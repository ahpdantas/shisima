package rmi;

import java.io.Serializable;
import java.util.UUID;

public class PlayerInstance implements Serializable {
	public enum Type{
		PLAYER_1, PLAYER_2
	};
	private UUID userID;
	private UUID gameID;
	private Type type;
		
	public PlayerInstance(){
		this.userID = UUID.randomUUID();
	}
		
	public void setType(Type type) {
		this.type = type;
	}

	public Type getType() {
		return type;
	}

	public UUID getUserId() {
		return userID;
	}
	
	public void setGameID(UUID gameID) {
		this.gameID = gameID;
	}
	
	public UUID getGameID() {
		return gameID;
	}
}
