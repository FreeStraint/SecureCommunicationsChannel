
public class Encrypt {
	
	public byte[] encryptString(String s, double[] key){
		byte[] b = s.getBytes();
		encrypt(b, key);
		
		return b;
	}
	
	public byte[] encryptBtyeArray(byte[] b, double[] key){
		encrypt(b, key);
		return b;
	}
	
	public static void decryptString(){
		
	}

	public static void decryptByteArray(){
		
		
	}
	
	private native void encrypt(byte[] value, double[] key);
	private native void decrypt(byte[] value, double[] key);
}
