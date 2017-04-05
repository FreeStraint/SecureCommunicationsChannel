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
	
	public long[] encryptMessage(long[] value, long[] key){
		long[] b = encrypt(value, key);
		System.out.println("value: "+ value);
		System.out.println("b: "+ b);
		return null;
	}

	public long[] decryptMessage(long[] value, long[] key){
		long[] b = decrypt(value, key);
		System.out.println("value: "+ value);
		System.out.println("b: "+ b);
		return null;
	}
	
	private native long[] encrypt(long[] value, long[] key);
	private native long[] decrypt(long[] value, long[] key);
	
	public static void main(String[] args) {
		Encrypt e = new Encrypt();
		long[] key = new long[4];
		long[] value = new long[]{100,200,300,400};
		//String s = "ABC";
		//byte[] b = s.getBytes();
		System.out.println(value);
		
		long[] l = e.encryptMessage(value, key);
		System.out.println("encrypt: "+ l);
		
		l = e.decryptMessage(value, key);
		System.out.println("decrypt: "+ l);
	}
}
