import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.stream.Collectors;

import javax.crypto.KeyGenerator;

public class Encrypt {
		
	static{
		System.loadLibrary("encrypt");
	}
	public byte[] encryptBtyeArray(byte[] b){
		//encrypt(b, key);
		return b;
	}

	public byte[] decryptByteArray(byte[] b){
		//decrypt(b, key);
		return b;
	}
	
	public void encryptMessage(byte[] value, byte[] key){
		encrypt(value, key);
		//return null;
	}
	
	public void decryptMessage(byte[] value, byte[] key){
		decrypt(value, key);
	}
	
	private native void encrypt(byte[] value, byte[] key);
	private native void decrypt(byte[] value, byte[] key);
	
	public static void main(String[] args) {
		Encrypt e = new Encrypt();
		Key k;
		try {
			k = KeyGenerator.getInstance("AES").generateKey();
			byte[] key = k.getEncoded();
			byte[] value = "ABCDE".getBytes();
			
			System.out.println("Original: " + value);
			e.encryptMessage(value, key);
			String s = new String(value);
			
			System.out.println("Encrypt: "+ value);
			e.decryptMessage(value, key);
			System.out.println("Decrypt:" + value);
			
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
		//Encrypt e = new Encrypt();
		
		//System.out.println(new Random().longs(4).mapToObj(Long::toString).collect(Collectors.joining(",")));
	}
}
