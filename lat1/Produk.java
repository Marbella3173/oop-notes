package lat1;

public class Produk {

	private String KodeProduk;
	private String Operator;
	private int Nominal;
	private int Stok;
	
	public Produk(String kodeProduk, String operator, int nominal, int stok) {
		super();
		KodeProduk = kodeProduk;
		Operator = operator;
		Nominal = nominal;
		Stok = stok;
	}

	public String getKodeProduk() {
		return KodeProduk;
	}

	public void setKodeProduk(String kodeProduk) {
		KodeProduk = kodeProduk;
	}

	public String getOperator() {
		return Operator;
	}

	public void setOperator(String operator) {
		Operator = operator;
	}

	public int getNominal() {
		return Nominal;
	}

	public void setNominal(int nominal) {
		Nominal = nominal;
	}

	public int getStok() {
		return Stok;
	}

	public void setStok(int stok) {
		Stok = stok;
	}
	
}
