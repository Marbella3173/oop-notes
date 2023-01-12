package uap2;

// encapsulation

public class Transaction {

	private String transactionId;
	private String drinkId;
	private String buyerName;
	private int quantity;
	
	public Transaction(String transactionId, String drinkId, String buyerName, int quantity) {
		super();
		this.transactionId = transactionId;
		this.drinkId = drinkId;
		this.buyerName = buyerName;
		this.quantity = quantity;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getDrinkId() {
		return drinkId;
	}

	public void setDrinkId(String drinkId) {
		this.drinkId = drinkId;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String selectAll() {
		String query = "select * from Transaction";
		
		return query;
	}
	
}
