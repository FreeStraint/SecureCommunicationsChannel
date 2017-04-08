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
	
	public boolean checkAuthenticate(String userID, String password){
		return check.readShadowFile(userID.trim(), password.trim());
	}
	
	public boolean checkFile(String fname){
		return check.checkFile(fname);
	}
		
	public void readKey(){
		transmit.setKey(transmit.readKey());
	}
	
	public void sendEncryptMesage(String s){
		transmit.sendEncryptMessage(s);
	}
	
	public String readEncryptMesage(){
		return transmit.readEncryptMessage().trim();
	}
	
	public void sendFile(String fname){
		transmit.sendFile(fname);
	}
}
