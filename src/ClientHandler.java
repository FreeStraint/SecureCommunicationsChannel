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
	Transmit transmit;
	String retrieve;
	
	public ClientHandler(Socket socket, Server server){
		this.socket = socket;
		this.server = server;
		transmit = new Transmit(socket);
	}
	@Override
	public void run(){
		// Read from client
		retrieve = transmit.readMessage().trim();
		if(retrieve == null){
			System.out.println("Error");
		}
		System.out.println("Code is "+ retrieve);
		server.setKey(retrieve);
		
		retrieve = transmit.readMessage().trim();
		String userID = retrieve;

		retrieve = transmit.readMessage().trim();
		String password = retrieve;

		if(!server.checkAuthenticate(userID, password)){
			transmit.sendMessage(userNotExist);
			try {
				socket.close();
				Thread.currentThread().interrupt();
			} catch (IOException e) {}
		}else{
			transmit.sendMessage(Success);
			
			while(true){
				retrieve = transmit.readMessage();
				//If user send "finished" break the loop and close the socket
				System.out.println("Read message: " + retrieve);
				if(retrieve.equals(Finish)){
					break;
				}
				//If file exist, send confirm message and send file
				if(server.checkFile(retrieve)){
					transmit.sendMessage(fileExist);
					transmit.sendFile(retrieve);
				}
				//If file do not exist, send file not found message
				else{
					transmit.sendMessage(fileNotExist);
				}
			}
			try {
				socket.close();
			} catch (IOException e) {}
		}
	}
}
