package net;

public class ReceiverTask implements Runnable {
	private NetworkService service;

	public ReceiverTask(NetworkService service){
		this.service = service;
	}
	
	@Override
	public void run() {
		while(true){
			String msg = this.service.receive();
			for( ReceiverListener r: this.service.getReceiverListeners()){
				if( msg != null ){
					r.receive(msg);
				}
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
