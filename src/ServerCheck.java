import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ServerCheck {
	
	public boolean readShadowFile(String userID, String password){
		String shadowFile = "secret.txt";
		
		BufferedReader br = null;
		String[] info;
		
		try{
			br = new BufferedReader(new FileReader(shadowFile));
			String line;
			while((line = br.readLine()) != null){
				info = line.split(" ");
				if(info[0].trim().equals(userID) && info[1].trim().equals(hash(password).trim())){
					br.close();
					return true;
				}
			}
		}
		catch (IOException e){
			System.out.println("Error, cannot read from shadow file");
		}
		finally{
			try {
				br.close();
			} catch (IOException e) {}
		}
		return false;
	}
	
	public boolean checkFile(String filename){
		File f = new File(filename);
		if(f.exists() && !f.isDirectory()){
			return true;
		}
		return false;
	}
	
	//Reference http://www.quicklyjava.com/salt-in-java/
	public String hash(String password){
		String salt = "ECE422Project2SaltingString";
		MessageDigest mg = null;
		String encryptP;
		try {
			mg = MessageDigest.getInstance("SHA");
			mg.update((password+salt).getBytes());
			encryptP = (new BigInteger(mg.digest(password.getBytes()))).toString(16);
			return encryptP;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return null;
	}
	
	
	public static void main(String[] args) {
		ServerCheck se = new ServerCheck();
		String unenct = "abcde";
		
		//boolean a = se.readShadowFile("tom", "abcde");
		
		String en = se.hash(unenct);
		System.out.println("hash: "+ en);
	}
	
	
}
