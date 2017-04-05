import java.nio.ByteBuffer;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.swing.plaf.synth.SynthSeparatorUI;

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
		return null;
	}

	public byte[] decryptMessage(byte[] value, long[] key){
		byte[] b = decrypt(value, key);
		System.out.println("value: "+ value);
		System.out.println("b: "+ b);
		return null;
	}
	
	private native byte[] encrypt(byte[] value, long[] key);
	private native byte[] decrypt(byte[] value, long[] key);
	
	public static void main(String[] args) {
		Encrypt e = new Encrypt();
		long[] key = new long[4];
		
		String s = "ABC";
		byte[] b = s.getBytes();
		System.out.println(b);
		
		b = e.encryptMessage(b, key);
		System.out.println("encrypt: "+ b);
		
		b = e.decryptMessage(b, key);
		System.out.println("decrypt: "+ b);
	}
}
