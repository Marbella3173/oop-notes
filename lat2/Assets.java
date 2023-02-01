package lat2;

public class Assets {

	private Integer assetID;
	private String UserID;
	private String CoinID;
	private Double sum_of_assets;
	
	public Assets(Integer assetID, String userID, String coinID, Double sum_of_assets) {
		super();
		this.assetID = assetID;
		UserID = userID;
		CoinID = coinID;
		this.sum_of_assets = sum_of_assets;
	}

	public Integer getAssetID() {
		return assetID;
	}

	public void setAssetID(Integer assetID) {
		this.assetID = assetID;
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

	public Double getSum_of_assets() {
		return sum_of_assets;
	}

	public void setSum_of_assets(Double sum_of_assets) {
		this.sum_of_assets = sum_of_assets;
	}
	
}
