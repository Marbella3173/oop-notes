package uap1;

public class Transactions {
	
	Connect con = Connect.getConnection();

	private String TransactionId;
	private String UserId;
	private String DessertId;
	private int Quantity;
	
	public Transactions(String transactionId, String userId, String dessertId, int quantity) {
		super();
		TransactionId = transactionId;
		UserId = userId;
		DessertId = dessertId;
		Quantity = quantity;
	}

	public String getTransactionId() {
		return TransactionId;
	}

	public void setTransactionId(String transactionId) {
		TransactionId = transactionId;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getDessertId() {
		return DessertId;
	}

	public void setDessertId(String dessertId) {
		DessertId = dessertId;
	}

	public int getQuantity() {
		return Quantity;
	}

	public void setQuantity(int quantity) {
		Quantity = quantity;
	}
	
}
