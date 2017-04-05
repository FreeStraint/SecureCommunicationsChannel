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
	
	public byte[] encryptMessage(byte[] value, long[] key){
		byte[] b = encrypt(value, key);
		//return null;
		return b;
	}
	
	public byte[] decryptMessage(byte[] value, long[] key){
		byte[] b = decrypt(value, key);
		return b;
	}
	
	private native byte[] encrypt(byte[] value, long[] key);
	private native byte[] decrypt(byte[] value, long[] key);
	
	public static void main(String[] args) {
		Encrypt e = new Encrypt();
		Key k;
		try {
			k = KeyGenerator.getInstance("AES").generateKey();
			//byte[] key = k.getEncoded();
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
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
		//Encrypt e = new Encrypt();
		
		//System.out.println(new Random().longs(4).mapToObj(Long::toString).collect(Collectors.joining(",")));
	}
}
