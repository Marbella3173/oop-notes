package lat1;

public class Transaksi {

	private int KodeTransaksi;
	private String KodeProduk;
	private String NomorHP;
	private String WaktuPesanan;
	private String WaktuPembayaran;
	private String StatusBayar;
	
	public Transaksi(int kodeTransaksi, String kodeProduk, String nomorHP, String waktuPesanan, String waktuPembayaran,
			String statusBayar) {
		super();
		KodeTransaksi = kodeTransaksi;
		KodeProduk = kodeProduk;
		NomorHP = nomorHP;
		WaktuPesanan = waktuPesanan;
		WaktuPembayaran = waktuPembayaran;
		StatusBayar = statusBayar;
	}

	public int getKodeTransaksi() {
		return KodeTransaksi;
	}

	public void setKodeTransaksi(int kodeTransaksi) {
		KodeTransaksi = kodeTransaksi;
	}

	public String getKodeProduk() {
		return KodeProduk;
	}

	public void setKodeProduk(String kodeProduk) {
		KodeProduk = kodeProduk;
	}

	public String getNomorHP() {
		return NomorHP;
	}

	public void setNomorHP(String nomorHP) {
		NomorHP = nomorHP;
	}

	public String getWaktuPesanan() {
		return WaktuPesanan;
	}

	public void setWaktuPesanan(String waktuPesanan) {
		WaktuPesanan = waktuPesanan;
	}

	public String getWaktuPembayaran() {
		return WaktuPembayaran;
	}

	public void setWaktuPembayaran(String waktuPembayaran) {
		WaktuPembayaran = waktuPembayaran;
	}

	public String getStatusBayar() {
		return StatusBayar;
	}

	public void setStatusBayar(String statusBayar) {
		StatusBayar = statusBayar;
	}
	
}
