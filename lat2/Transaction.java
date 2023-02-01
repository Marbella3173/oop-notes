package lat2;

public class Transaction {

	private Integer transactionID;
	private String UserID;
	private String CoinID;
	private String transaction;
	private Double num_of_coins;
	
	public Transaction(Integer transactionID, String userID, String coinID, String transaction, Double num_of_coins) {
		super();
		this.transactionID = transactionID;
		UserID = userID;
		CoinID = coinID;
		this.transaction = transaction;
		this.num_of_coins = num_of_coins;
	}

	public Integer getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(Integer transactionID) {
		this.transactionID = transactionID;
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String getCoinID() {
		return CoinID;
	}

	public void setCoinID(String coinID) {
		CoinID = coinID;
	}

	public String getTransaction() {
		return transaction;
	}

	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}

	public Double getNum_of_coins() {
		return num_of_coins;
	}

	public void setNum_of_coins(Double num_of_coins) {
		this.num_of_coins = num_of_coins;
	}
	
}
