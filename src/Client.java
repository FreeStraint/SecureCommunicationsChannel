import java.net.Socket;

public class Client {
	
	private String userID;
	private String password;
	private Double key;
	private Transmit transmit;
	
	public Client(Socket socket){
		//this.generateKey();
		transmit = new Transmit(socket);
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Double getKey() {
		return key;
	}

	public void setKey(Double key) {
		this.key = key;
	}
	
	public void sendMessage(String s){
		transmit.sendMessage(s);
	}
	
	public String readMessage(){
		return transmit.readMessage().trim();
	}
	
	public void readFile(String fname){
		transmit.readFile(fname);
	}
	
//	public Key generateKey(){
//		try {
//			KeyGenerator generator = KeyGenerator.getInstance("AES");
//			Key key = generator.generateKey();
//			this.setKey(key);
//			return key;
//		} catch (NoSuchAlgorithmException e) {
//			//e.printStackTrace();
//		}
//		return null;
//	}
}
