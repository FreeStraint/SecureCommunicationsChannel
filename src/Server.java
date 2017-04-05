import java.net.Socket;

public class Server {
	
	private Socket socket;
	private ServerCheck check;
	private Transmit transmit;

	
	public Server(Socket socket){
		transmit = new Transmit(socket);
		this.socket = socket;
		check = new ServerCheck();
	}
	public Socket getSocket(){
		return socket;
	}
	
	public boolean checkAuthenticate(String userID, String password){
		return check.readShadowFile(userID, password);
	}
	
	public boolean checkFile(String fname){
		return check.checkFile(fname);
	}
	
	public void sendMessage(String s){
		transmit.sendMessage(s);
	}
	
	public String readMessage(){
		return transmit.readMessage().trim();
	}
	
	public void sendFile(String fname){
		transmit.sendFile(fname);
	}
}
