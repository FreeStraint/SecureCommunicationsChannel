
import java.net.Socket;
import java.security.Key;
import java.util.Scanner;

public class ClientConnection {
	
	final String userNotExist = "UserNotExist";
	final String Success = "Success";
	final String Finish = "finished";
	final String fileNotExist = "File not exist";
	final String fileExist = "FileExist";
	final private int portNumber = 16000;
	final private String address = "127.0.0.1";

	Client client;
	Scanner scan;
	Socket socket;
	Transmit transmit;
	private String retrieve;
	
	public ClientConnection() throws Exception{
		scan = new Scanner(System.in);
		socket = new Socket(address, portNumber);
		transmit = new Transmit(socket);
		client = new Client();
	}
	
	public void getUserInfo(){
		System.out.println("Please enter your userID");
		String userID = scan.nextLine();
		client.setUserID(userID);
		System.out.println("Please enter your password");
		String password = scan.nextLine();
		client.setPassword(password);
	}
	public void run() throws Exception{

		//Send code
		//transmit.sendMessage(client.getKey().toString());
		transmit.sendMessage("12345");
		
		getUserInfo();
		transmit.sendMessage(client.getUserID());
		transmit.sendMessage(client.getPassword());
		
		retrieve = transmit.readMessage().trim();
		System.out.println("Message from server: "+ retrieve);
		if(retrieve.equals(userNotExist)){
			System.out.println("You are not exist in the system");
			socket.close();
			return;
		}
		
		while(true){
			System.out.println("Please enter a filename, enter 'finished' to exit");
			String filename = scan.nextLine();
			transmit.sendMessage(filename);
			if(filename.equals(Finish)){
				break;
			}
			retrieve = transmit.readMessage();
			if(retrieve == null){
				System.out.println("Please try again");
				continue;
			}
			if(retrieve.equals(fileExist)){
				System.out.println(fileExist);
				transmit.readFile(filename);
				System.out.println("Done");
			}
			else{
				System.out.println(fileNotExist);
			}
				
		}
		socket.close();
	}
	
	public static void main(String[] args) throws Exception{
		ClientConnection client = new ClientConnection();
		client.run();
		System.out.println("Terminate Connection, bye bye");

	}
}
