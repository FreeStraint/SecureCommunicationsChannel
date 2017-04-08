
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;


public class Transmit {
	
	DataOutputStream out;
	DataInputStream in;
	Encrypt encrypt;
	
	public Transmit(Socket socket) {
		encrypt = new Encrypt();
		try {
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setKey(byte[] b){
		encrypt.setKey(b);
	}
	public void sendEncryptMessage(String s){
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
	
	public void sendKey(byte[] b){
		try {
			out.write(b);
		} catch (IOException e) {
		}
	}
	
	public String readEncryptMessage(){
		/**
		 * First initialize a byte array of 20000;
		 * Then read the byte array from inputstream
		 * Construct a byte array from the read in byte array.
		 */
		String read;
		byte[] b = new byte[20000];
		try {
			in.read(b);
			b = encrypt.decryptByteArray(b);
			read = new String(b);
			return read.trim();
		} catch (IOException e) {}

		return null;
	}
	
	public byte[] readKey(){

		byte[] b = new byte[20000];
		try {
			in.read(b);
			return b;
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
