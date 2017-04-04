
public class Client {
	
	private String userID;
	private String password;
	private Double key;
	
	public Client(){
		//this.generateKey();
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Double getKey() {
		return key;
	}

	public void setKey(Double key) {
		this.key = key;
	}
	
//	public Key generateKey(){
//		try {
//			KeyGenerator generator = KeyGenerator.getInstance("AES");
//			Key key = generator.generateKey();
//			this.setKey(key);
//			return key;
//		} catch (NoSuchAlgorithmException e) {
//			//e.printStackTrace();
//		}
//		return null;
//	}
}
