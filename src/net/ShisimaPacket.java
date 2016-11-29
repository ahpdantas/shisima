package net;

public class ShisimaPacket {
	public String userName = "";
	public String msg = "";
	public String mov = "";
	public String oper = "";

	public ShisimaPacket(String userName, String msg, String mov, String oper ){
		this.userName = userName;
		this.msg = msg;
		this.mov = mov;
		this.oper = oper;
	}
	
	@Override
	public String toString(){
		return this.userName +":" +this.msg +":" + this.mov + ":" + oper;
	}
}
