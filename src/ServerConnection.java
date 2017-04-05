import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerConnection {
	
	private int poolSize = 20;
	private int portNumber = 16000;
	private ServerSocket serverSocket;
	private ExecutorService pool;

	public ServerConnection() throws IOException{
		serverSocket = new ServerSocket(portNumber);
		pool = Executors.newFixedThreadPool(poolSize);
	}
	
	public void run(){
		System.out.println("Server socket created");
		try{
			while(true){
				Socket socket = serverSocket.accept();
				System.out.println("Socket accepted");
				pool.execute(new ClientHandler(socket));
			}
		}catch (IOException ex){
			pool.shutdown();
		}
	}
	
	public static void main(String[] args) throws Exception {
		ServerConnection serverConnection = new ServerConnection();
		serverConnection.run();
	}
}
