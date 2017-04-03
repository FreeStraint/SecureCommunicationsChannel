
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * This class was used to transmit string message and file between client and server
 * @author James
 *
 */
public class Transmit {
	
	
	DataOutputStream out;
	DataInputStream in;
	
	public Transmit(Socket socket) {
		try {
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String s){
		try {
			out.writeUTF(s);
		} catch (IOException e) {
		}
	}
	
	public String readMessage(){
		String read;
		
		try {
			read = in.readUTF();
			return read;
		} catch (IOException e) {}

		return null;
	}
	
	public void sendFile(String filename){
		File file = new File(filename);
		byte[] b = new byte[(int) file.length()];
		
		try{
			FileInputStream fis = new FileInputStream(file);
			fis.read(b);
			fis.close();
			out.write(b);
		}catch (Exception e){}
	}
	
	public void readFile(String filename){
		File file = new File("rev"+filename);
		byte[] b = new byte[6022386];
		try {
			in.read(b);
			
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(b);
			fos.close();
		} catch (IOException e) {}
	}
}
