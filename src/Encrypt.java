import java.nio.ByteBuffer;
import java.nio.LongBuffer;
import java.util.Arrays;

public class Encrypt {
		
	static{
		System.loadLibrary("encrypt");
	}
	public byte[] encryptBtyeArray(byte[] b){
		return b;
	}

	public byte[] decryptByteArray(byte[] b){
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

	public static void main(String[] args) {
		Encrypt e = new Encrypt();
		
		String s = "ABCDEDdaffasfdsaadfadsfadsfadsfadsdfdsafadsfasdfadsf";
		byte[] bb = s.getBytes();

		System.out.println(Arrays.toString(bb));
		System.out.println("----------");
		long[] ll = e.convertBytesToLongs(bb);
		System.out.println(Arrays.toString(ll));
		bb = e.convertLongsToBytes(ll);
		System.out.println(Arrays.toString(bb));
	
		long[] key = new long[4];
		long[] value = ll;
		System.out.println(Arrays.toString(value));
		System.out.println("original: " +value);
		
		long[] l = e.encryptMessage(value, key);
		System.out.println("encrypt: "+ l);
		
		l = e.decryptMessage(l, key);
		System.out.println("decrypt: ");
		System.out.println(Arrays.toString(l));
		bb = e.convertLongsToBytes(l);
		System.out.println(Arrays.toString(bb));
		String newst = new String(bb);
		System.out.println(newst);
		if(newst.equals(s.trim())){
			System.out.println("Yes");
		}
	
	}
}
