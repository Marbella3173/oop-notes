package lat2;

public class User {

	private String UserID;
	private String user_name;
	private Double rupiah_balance;
	
	public User(String userID, String user_name, Double rupiah_balance) {
		super();
		UserID = userID;
		this.user_name = user_name;
		this.rupiah_balance = rupiah_balance;
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public Double getRupiah_balance() {
		return rupiah_balance;
	}

	public void setRupiah_balance(Double rupiah_balance) {
		this.rupiah_balance = rupiah_balance;
	}
	
}
