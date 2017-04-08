import java.nio.ByteBuffer;
import java.util.Arrays;

public class Encrypt {
	
	int[] key;
	
	public Encrypt(){
	}
	
	public void setKey(byte[] b){
		key = convertBytesToInts(b);
	}
	static{
		System.loadLibrary("encrypt");
	}
	public byte[] encryptBtyeArray(byte[] b){
		
		int[] l = convertBytesToInts(b);
		l = encrypt(l, key);
		b = convertIntsToBytes(l);
		return b;
	}

	public byte[] decryptByteArray(byte[] b){
		int[] l = convertBytesToInts(b);
		l = decrypt(l, key);
		b = convertIntsToBytes(l);
		return b;
	}
	
	private native int[] encrypt(int[] value, int[] key);
	private native int[] decrypt(int[] value, int[] key);
	
	//Reference from http://stackoverflow.com/questions/4485128/how-do-i-convert-long-to-byte-and-back-in-java
	public int[] convertBytesToInts(byte[] b){
		int size;
		if(b.length%4 == 0){
			size = b.length/2;
		}
		else{
			size = b.length/2 + 1;
		}
		if (size == 1){
			size += 1;
		}
		ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES *size);
		buffer.put(b);
		buffer.rewind();
		
		int[] result = new int[size];
		while(buffer.remaining() > 0){
			result[buffer.position()/4] = buffer.getInt();
		}
		
		return result;
	}
	//Reference from http://stackoverflow.com/questions/11665147/convert-a-longbuffer-intbuffer-shortbuffer-to-bytebuffer
	public byte[] convertIntsToBytes(int[] l){
		int size = l.length * 4;
		ByteBuffer buffer = ByteBuffer.allocate(size);
		buffer.asIntBuffer().put(l);
		
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
}
