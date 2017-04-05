import java.security.Key;
import java.security.NoSuchAlgorithmException;

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
	
	public byte[] encryptMessage(byte[] value, long[] key){
		byte[] b = encrypt(value, key);
		System.out.println("value: "+ value);
		System.out.println("b: "+ b);
		return b;
	}
	
	public byte[] decryptMessage(byte[] value, long[] key){
		byte[] b = decrypt(value, key);
		System.out.println("value: "+ value);
		System.out.println("b: "+ b);
		return b;
	}
	
	private native byte[] encrypt(byte[] value, long[] key);
	private native byte[] decrypt(byte[] value, long[] key);
	
	public static void main(String[] args) {
		Encrypt e = new Encrypt();
		Key k;
		try {
			k = KeyGenerator.getInstance("AES").generateKey();
			long[] key = new long[]{1,2,3,4,5};
			byte[] value = "ABCDE".getBytes();
			
			System.out.println("Original: " + value);
			value = e.encryptMessage(value, key);
			String s = new String(value);
			
			System.out.println("Encrypt: "+ value);
			value = e.decryptMessage(value, key);
			System.out.println("Decrypt:" + value);
			s = new String(value);
			System.out.println("Decrypt Message: "+ s);
			
		} catch (NoSuchAlgorithmException e1) {
		}		
	}
}
