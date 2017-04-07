import java.nio.ByteBuffer;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.KeyGenerator;

public class Encrypt {
	
	long[] key;
	
	public Encrypt(){
		//key = new long[]{100,200,300,400};
	}
	
	public void setKey(byte[] b){
		key = convertBytesToLongs(b);
	}
	static{
		System.loadLibrary("encrypt");
	}
	public byte[] encryptBtyeArray(byte[] b){
		
		long[] l = convertBytesToLongs(b);
		l = encrypt(l, key);
		b = convertLongsToBytes(l);
		return b;
	}

	public byte[] decryptByteArray(byte[] b){
		long[] l = convertBytesToLongs(b);
		l = decrypt(l, key);
		b = convertLongsToBytes(l);
		return b;
	}
	
	public long[] encryptMessage(long[] value, long[] key){
		long[] l = encrypt(value, key);
		return l;
	}

	public long[] decryptMessage(long[] value, long[] key){
		long[] l = decrypt(value, key);
		return l;
	}
	
	private native long[] encrypt(long[] value, long[] key);
	private native long[] decrypt(long[] value, long[] key);
	
	//Reference from http://stackoverflow.com/questions/4485128/how-do-i-convert-long-to-byte-and-back-in-java
	public long[] convertBytesToLongs(byte[] b){
		int size;
		if(b.length%4 == 0){
			size = b.length/4;
		}
		else{
			size = b.length/4 + 1;
		}
		if (size == 1){
			size += 1;
		}
		ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES *size);
		buffer.put(b);
		buffer.rewind();
		
		long[] result = new long[size];
		while(buffer.remaining() > 0){
			result[buffer.position()/8] = buffer.getLong();
		}
		
		return result;
	}
	//Reference from http://stackoverflow.com/questions/11665147/convert-a-longbuffer-intbuffer-shortbuffer-to-bytebuffer
	public byte[] convertLongsToBytes(long[] l){
		int size = l.length * 8;
		ByteBuffer buffer = ByteBuffer.allocate(size);
		buffer.asLongBuffer().put(l);
		
		byte[] bytes = buffer.array();
		//Had to remove trailing zeros, otherwise, the converted string won't match
		for(int i = 0; i<bytes.length; i++){
			if(bytes[i] == 0){
				byte[] res = Arrays.copyOfRange(bytes, 0, i);
				return res;
			}
		}
		return bytes;
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		Encrypt e = new Encrypt();
		long[] key;
		Key k = KeyGenerator.getInstance("AES").generateKey();
		key = e.convertBytesToLongs(k.getEncoded());
		String s = "tom";
		byte[] bb = s.getBytes();

		System.out.println(Arrays.toString(bb));
		System.out.println("----------");
		long[] ll = e.convertBytesToLongs(bb);
		System.out.println(Arrays.toString(ll));
		bb = e.convertLongsToBytes(ll);
		System.out.println(Arrays.toString(bb));
	
		long[] value = ll;
		System.out.println(Arrays.toString(value));
		System.out.println("original: " +value);
		
		long[] l = e.encryptMessage(value, key);
		System.out.println("encrypt: "+ l);
		
		byte[] encry = e.convertLongsToBytes(l);
		
		l = e.convertBytesToLongs(encry);
		
		l = e.decryptMessage(l, key);
		System.out.println("decrypt: ");
		System.out.println(Arrays.toString(l));
		bb = e.convertLongsToBytes(l);
		System.out.println(Arrays.toString(bb));
		String newst = new String(bb);
		System.out.println(newst);
		if(newst.trim().equals(s.trim())){
			System.out.println("Yes");
		}
	
	}
}
