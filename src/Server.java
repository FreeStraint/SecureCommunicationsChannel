import java.net.Socket;

public class Server {
	
	private double key;
	private Socket socket;
	ServerCheck check;
	
	public Server(Socket socket){
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

	public Double getKey() {
		return key;
	}

	public void setKey(String k) {
		this.key = Double.parseDouble(k);
		//this.key = key;
	}
}
