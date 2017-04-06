
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class was used to transmit string message and file between client and server
 * @author James
 *
 */
public class Transmit {
	
	ObjectOutputStream out;
	ObjectInputStream in;
	//DataOutputStream out;
	//DataInputStream in;
	Encrypt encrypt;
	
	public Transmit(Socket socket) {
		encrypt = new Encrypt();
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String s){
		/**
		 * First convert string to byte array
		 * then write the byte array to outputstream
		 */
		byte[] b = s.getBytes();
		b = encrypt.encryptBtyeArray(b);
		
		try {
			out.write(b);
		} catch (IOException e) {
		}
	}
	
	
	public String readMessage(){
		/**
		 * First initialize a byte array of 2000;
		 * Then read the byte array from inputstream
		 * Construct a byte array from the read in byte array.
		 */
		String read;
		byte[] b = new byte[2000];
		try {
			in.read(b);
			b = encrypt.decryptByteArray(b);
			read = new String(b);
			return read.trim();
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
			byte[] en = encrypt.encryptBtyeArray(b);
			out.write(en);
		}catch (Exception e){}
	}
	
	public void readFile(String filename){
		File file = new File("rev"+filename);
		byte[] b = new byte[6022386];
		try {
			in.read(b);
			byte[] de = encrypt.decryptByteArray(b);
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(de);
			fos.close();
		} catch (IOException e) {}
	}
}
