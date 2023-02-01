package lat2;

public class CryptoCurrency {

	private String CoinID;
	private String coin_name;
	private Double rp_exchange_rate;
	
	public CryptoCurrency(String coinID, String coin_name, Double rp_exchange_rate) {
		super();
		CoinID = coinID;
		this.coin_name = coin_name;
		this.rp_exchange_rate = rp_exchange_rate;
	}

	public String getCoinID() {
		return CoinID;
	}

	public void setCoinID(String coinID) {
		CoinID = coinID;
	}

	public String getCoin_name() {
		return coin_name;
	}

	public void setCoin_name(String coin_name) {
		this.coin_name = coin_name;
	}

	public Double getRp_exchange_rate() {
		return rp_exchange_rate;
	}

	public void setRp_exchange_rate(Double rp_exchange_rate) {
		this.rp_exchange_rate = rp_exchange_rate;
	}
	
}
