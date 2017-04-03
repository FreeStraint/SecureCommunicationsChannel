import java.net.Socket;

public class Server {
	
	
	private Socket socket;
	ServerCheck check;
	
	public Server(Socket socket){
		this.socket = socket;
		check = new ServerCheck();
	}
	
	public boolean checkAuthenticate(String userID, String password){
		return check.readShadowFile(userID, password);
	}
	
	public boolean checkFile(String fname){
		return check.checkFile(fname);
	}
}
