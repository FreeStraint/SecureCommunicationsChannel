import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {
	
	final String userNotExist = "UserNotExist";
	final String Success = "Success";
	final String Finish = "finished";
	final String fileNotExist = "File not exist";
	final String fileExist = "FileExist";

	private Socket socket;
	private Server server;
	String retrieve;
	
	public ClientHandler(Socket socket){
		this.socket = socket;
		this.server = new Server(socket);
	}
	
	@Override
	public void run(){
		// Read from client
		server.readKey();
		
		retrieve = server.readEncryptMesage().trim();
		String userID = retrieve;

		retrieve = server.readEncryptMesage().trim();
		String password = retrieve;

		if(!server.checkAuthenticate(userID, password)){
			server.sendEncryptMesage(userNotExist);
			try {
				socket.close();
				Thread.currentThread().interrupt();
			} catch (IOException e) {}
		}else{
			server.sendEncryptMesage(Success);
			
			while(true){
				retrieve = server.readEncryptMesage();
				//If user send "finished" break the loop and close the socket
				System.out.println("Read message: " + retrieve);
				if(retrieve.equals(Finish)){
					break;
				}
				//If file exist, send confirm message and send file
				if(server.checkFile(retrieve)){
					server.sendEncryptMesage(fileExist);
					server.sendFile(retrieve);
				}
				//If file do not exist, send file not found message
				else{
					server.sendEncryptMesage(fileNotExist);
				}
			}
			try {
				socket.close();
			} catch (IOException e) {}
		}
	}
}
