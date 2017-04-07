import java.net.Socket;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;

public class Client {
	
	private String userID;
	private String password;
	private byte[] key;
	private Transmit transmit;
	
	public Client(Socket socket){
		transmit = new Transmit(socket);
		key = generateKey();
		transmit.setKey(key);
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

	public byte[] getKey() {
		return key;
	}
	
	public void sendKey(){
		transmit.sendKey(this.key);
	}
		
	public void readFile(String fname){
		transmit.readFile(fname);
	}
	
	public void sendEncryptMesage(String s){
		transmit.sendEncryptMessage(s);
	}
	
	public String readEncryptMesage(){
		return transmit.readEncryptMessage().trim();
	}
	
	public byte[] generateKey(){
		try {
			KeyGenerator generator = KeyGenerator.getInstance("AES");
			Key key = generator.generateKey();
			return key.getEncoded();

		} catch (NoSuchAlgorithmException e) {
			//e.printStackTrace();
		}
		return null;
	}
}
