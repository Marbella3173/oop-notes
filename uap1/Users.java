package uap1;

public class Users {
	
	Connect con = Connect.getConnection();

	private String UserId;
	private String UserName;
	private String UserEmail;
	private int UserAge;
	
	public Users(String userId, String userName, String userEmail, int userAge) {
		super();
		UserId = userId;
		UserName = userName;
		UserEmail = userEmail;
		UserAge = userAge;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getUserEmail() {
		return UserEmail;
	}

	public void setUserEmail(String userEmail) {
		UserEmail = userEmail;
	}

	public int getUserAge() {
		return UserAge;
	}

	public void setUserAge(int userAge) {
		UserAge = userAge;
	}
	
}
