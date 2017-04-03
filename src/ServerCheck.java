import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
				if(info[0].equals(userID) && info[1].equals(password)){
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
	
	
	
	public static void main(String[] args) {
		ServerCheck server = new ServerCheck();
		boolean data = server.readShadowFile("tom", "54321");
		System.out.println(data);
		
		data = server.readShadowFile("jim", "12345");
		System.out.println(data);
		
		data = server.checkFile("secret.txt");
		System.out.println(data);
		
		data = server.checkFile("secr3.txt");
		System.out.println(data);
	}
}
