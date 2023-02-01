package lat1;

import java.util.*;
import java.sql.*;
import java.time.LocalDateTime;

public class Main {
	
	Connect con = Connect.getConnection();
	
	PreparedStatement ps;
	ResultSet rs;
	
	Scanner scan = new Scanner(System.in);
	Scanner sc = new Scanner(System.in);
	
	Vector<Produk> newProduk = new Vector<>();
	Vector<Transaksi> newTransaksi = new Vector<>();
	
	Produk p = null;
	Transaksi q = null;
	Produk r = null;
	
	public void Transaksi() {
		newTransaksi.clear();
		getTransaksi();
		
		System.out.println("=======================================");
		System.out.println("| Kode Transaksi | Nomor HP | Nominal |");
		System.out.println("=======================================");
		
		int count = 0;
		int total = 0;
		
		for (Transaksi i : newTransaksi) {
			if(i.getStatusBayar().contains("SUDAH") == true) {
				Produk j = getProduk(i.getKodeProduk());
				
				System.out.println("| " + i.getKodeTransaksi() + " | " + i.getNomorHP() + " | " + j.getNominal() + " |");
				
				count++;
				total += j.getNominal();
			}
		}
		
		System.out.println("=======================================");
		
		System.out.println("| TOTAL TRANSAKSI BERHASIL = " + count + " |");
		System.out.println("| TOTAL PENDAPATAN = " + total + " |");
		
		System.out.println("=======================================");
		
		Prompt();
	}
	
	public void pembatalan() {
		String query = "update transaksi set StatusBayar = ? where KodeTransaksi = ?";
		
		ps = con.prepareStatement(query);
		
		try {
			ps.setString(1, "BATAL");
			ps.setInt(2, q.getKodeTransaksi());
			
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Pembatalan pesanan berhasil");
		
		Prompt();
	}
	
	public void pembayaran() {
		if(r.getStok() == 0) {
			System.out.println("Pembayaran tidak dapat dilakukan karena stok sudah habis, lakukan pengisian stok terlebih dahulu");
		}else {
			String query1 = "update transaksi set StatusBayar = ?, WaktuPembayaran = ? where KodeTransaksi = ?";
			
			ps = con.prepareStatement(query1);
			
			try {
				ps.setString(1, "SUDAH");
				ps.setString(2, java.time.LocalDateTime.now().toString());
				ps.setInt(3, q.getKodeTransaksi());
				
				ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String query2 = "update produk set Stok = ? where KodeProduk = ?";
			
			ps = con.prepareStatement(query2);
			
			try {
				ps.setInt(1, r.getStok()-1);
				ps.setString(2, q.getKodeProduk());
				
				ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("Pembayaran berhasil dilakukan, stok produk sudah dikurangi 1");
		}
		
		Prompt();
	}
	
	public void ChoiceTransaksi(int menu) {
		switch (menu) {
			case 1:{
				pembayaran();
				
				break;
			}
			case 2:{
				pembatalan();
				
				break;
			}
			case 3:{
				Prompt();
				
				break;
			}
		}
	}
	
	public void MenuTransaksi() {
		r = getProduk(q.getKodeProduk());
		
		System.out.println("Kode Transaksi: " + q.getKodeTransaksi());
		System.out.println("Nomor HP: " + q.getNomorHP());
		System.out.println("Nominal: " + r.getNominal());
		System.out.println("Stok: " + r.getStok());
		
		System.out.println("\nMENU");
		System.out.println("[1] Pembayaran Pesanan");
		System.out.println("[2] Pembatalan Pesanan");
		System.out.println("[3] Kembali ke Menu Utama");
		
		int menu;
		
		do {
			System.out.print("Pilih [1-3]: ");
			menu = scan.nextInt();
			scan.nextLine();
			ChoiceTransaksi(menu);
		} while (menu >= 1 || menu <= 3);
	}
	
	public void Pesanan() {
		System.out.println("PEMBAYARAN/PEMBATALAN PESANAN\n");
		
		newTransaksi.clear();
		getTransaksi();
		printTransaksi();
		
		System.out.println("Masukkan Kode Transaksi untuk membayar, membatalkan pesanan, atau '-' untuk kembali ke Menu Utama");
		
		String kode;
		int x;
		
		do {
			System.out.print("Kode Transaksi: ");
			kode = scan.nextLine();
			
			x = 0;
			
			if(kode.compareTo("-") != 0) {
				int code = Integer.parseInt(kode);
				
				for (Transaksi i : newTransaksi) {
					if(i.getKodeTransaksi() == code) {
						x = 1;
						q = i;
						break;
					}
				}
			}
			
			if(x == 0 && kode.compareTo("-") != 0) {
				System.out.println("Kode tidak ditemukan");
			}
		} while (kode.contains("-") == false && x == 0);
		
		if(kode.compareTo("-") == 0) {
			Prompt();
		}else {
			MenuTransaksi();
		}
	}
	
	public void printTransaksi() {
		System.out.println("============================================================");
		System.out.println("| Kode Transaksi | Kode Produk | Nomor HP | Nominal | Stok |");
		System.out.println("============================================================");
		
		for (Transaksi i : newTransaksi) {
			if(i.getStatusBayar().contains("BELUM") == true) {
				Produk j = getProduk(i.getKodeProduk());
				
				System.out.println("| " + i.getKodeTransaksi() + " | " + i.getKodeProduk() + " | " + i.getNomorHP() + " | " + j.getNominal() + " | " + j.getStok() + " |");
			}
		}
		
		System.out.println("============================================================");
	}
	
	public Produk getProduk(String kode) {
		String query = "select * from Produk where KodeProduk = ?";
		
		ps = con.prepareStatement(query);
		
		try {
			ps.setString(1, kode);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String KodeProduk = rs.getString(1);
				String Operator = rs.getString(2);
				int Nominal = rs.getInt(3);
				int Stok = rs.getInt(4);
				
				return new Produk(KodeProduk, Operator, Nominal, Stok);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void getTransaksi() {
		ps = con.prepareStatement("select * from Transaksi");
		
		try {
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int KodeTransaksi = rs.getInt(1);
				String KodeProduk = rs.getString(2);
				String NomorHP = rs.getString(3);
				String WaktuPesanan = rs.getString(4);
				String WaktuPembayaran = rs.getString(5);
				String StatusBayar = rs.getString(6);
				
				newTransaksi.add(new Transaksi(KodeTransaksi, KodeProduk, NomorHP, WaktuPesanan, WaktuPembayaran, StatusBayar));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	int count = getLastKode();
	
	public void pesanProduk() {
		if(p.getStok() == 0) {
			System.out.println("Pemesanan tidak dapat dilakukan karena stok kosong");
		}else {
			int KodeTransaksi;
			String KodeProduk;
			String NomorHP;
			String WaktuPesanan;
			String WaktuPembayaran;
			String StatusBayar;
			
			int x;
			
			do {
				System.out.print("Masukkan Nomor HP [10-15 digit, angka semua, diawali 08]: ");
				NomorHP = scan.nextLine();
				
				x = 0;
				
				for (int i = 0; i < NomorHP.length(); i++) {
					if(Character.isDigit(NomorHP.charAt(i)) == false) {
						x = 1;
						break;
					}
				}
			} while ((NomorHP.length() < 10 || NomorHP.length() > 15) || x == 1 || NomorHP.startsWith("08") == false);
			
			KodeProduk = p.getKodeProduk();
			
			WaktuPesanan = java.time.LocalDateTime.now().toString();
			
			WaktuPembayaran = "";
			
			StatusBayar = "BELUM";
			
			KodeTransaksi = count;
			
			String query = "insert into Transaksi values (?, ?, ?, ?, ?, ?)";
			
			ps = con.prepareStatement(query);
			
			try {
				ps.setInt(1, KodeTransaksi);
				ps.setString(2, KodeProduk);
				ps.setString(3, NomorHP);
				ps.setString(4, WaktuPesanan);
				ps.setString(5, WaktuPembayaran);
				ps.setString(6, StatusBayar);
				ps.executeUpdate();
				
				newTransaksi.add(new Transaksi(KodeTransaksi, KodeProduk, NomorHP, WaktuPesanan, WaktuPembayaran, StatusBayar));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("Pemesanan berhasil, segera lakukan pembayaran");
		}
		
		PromptProduk();
	}
	
	public int getLastKode() {
		String query = "select KodeTransaksi from Transaksi order by KodeTransaksi desc limit 1";
		
		ps = con.prepareStatement(query);
		
		try {
			rs = ps.executeQuery();
			
			if(rs.isBeforeFirst()) {
				rs.next();
				int kode = rs.getInt(1);
				return kode + 1;
			}
			else return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
	
	public void tambahStok() {
		if(p.getStok() < 100) {
			int awal = p.getStok();
			
			int stok;
			
			do {
				System.out.printf("Tambahkan stok [1 - %d]: ", 100 - p.getStok());
				stok = scan.nextInt();
				scan.nextLine();
			} while (stok < 1 || stok > 100 - p.getStok());
			
			String query = "update produk set Stok = ? where KodeProduk = ?";
			
			ps = con.prepareStatement(query);
			
			try {
				ps.setInt(1, awal + stok);
				ps.setString(2, p.getKodeProduk());
				
				ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			p.setStok(awal + stok);
			
			for (Produk i : newProduk) {
				if(i.getKodeProduk().compareTo(p.getKodeProduk()) == 0) {
					i.setStok(awal + stok);
				}
			}
			
			System.out.println("Stok berhasil ditambahkan");
		}else {
			System.out.println("Stok tidak dapat ditambahkan lagi karena sudah penuh");
		}
		
		PromptProduk();
	}
	
	public void ChoiceProduk(int menu) {
		switch (menu) {
			case 1:{
				tambahStok();
				
				break;
			}
			case 2:{
				pesanProduk();
				
				break;
			}
			case 3:{
				Prompt();
				
				break;
			}
		}
	}
	
	public void MenuProduk() {
		System.out.println("Kode Produk: " + p.getKodeProduk());
		System.out.println("Operator: " + p.getOperator());
		System.out.println("Nominal: " + p.getNominal());
		System.out.println("Stok: " + p.getStok());
		
		System.out.println("\nMENU");
		System.out.println("[1] Tambah Stok Produk");
		System.out.println("[2] Pemesanan Produk");
		System.out.println("[3] Kembali ke Menu Utama");
		
		int menu;
		
		do {
			System.out.print("Pilih [1-3]: ");
			menu = scan.nextInt();
			scan.nextLine();
			ChoiceProduk(menu);
		} while (menu >= 1 || menu <= 3);
	}
	
	public void PromptProduk() {
		System.out.println("Tekan ENTER untuk kembali ke Menu Produk");
		sc.nextLine();
		MenuProduk();
	}
	
	public void Produk() {
		System.out.println("LIHAT, TAMBAH STOK, DAN PEMESANAN PRODUK\n");
		
		newProduk.clear();
		getProduk();
		printProduk();
		
		System.out.println("Masukkan Kode Produk untuk menambahkan stok, pemesanan produk, atau '-' untuk kembali ke Menu Utama");
		
		String kode;
		int x;
		
		do {
			System.out.print("Kode Produk: ");
			kode = scan.nextLine();
			
			x = 0;
			
			for (Produk i : newProduk) {
				if(i.getKodeProduk().compareTo(kode) == 0) {
					x = 1;
					p = i;
					break;
				}
			}
			
			if(x == 0 && kode.compareTo("-") != 0) {
				System.out.println("Kode tidak ditemukan");
			}
		} while (kode.contains("-") == false && x == 0);
		
		if(kode.compareTo("-") == 0) {
			Prompt();
		}else {
			MenuProduk();
		}
	}
	
	public void printProduk() {
		System.out.println("====================================");
		System.out.println("| Kode | Operator | Nominal | Stok |");
		System.out.println("====================================");
		
		for (Produk i : newProduk) {
			System.out.println("| " + i.getKodeProduk() + " | " + i.getOperator() + " | " + i.getNominal() + " | " + i.getStok() + " |");
		}
		
		System.out.println("====================================");
	}
	
	public void getProduk() {
		ps = con.prepareStatement("select * from Produk");
		
		try {
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String KodeProduk = rs.getString(1);
				String Operator = rs.getString(2);
				int Nominal = rs.getInt(3);
				int Stok = rs.getInt(4);
				
				newProduk.add(new Produk(KodeProduk, Operator, Nominal, Stok));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void Prompt() {
		System.out.println("Tekan ENTER untuk kembali ke Menu Utama");
		sc.nextLine();
		Menu();
	}
	
	public void Choice(int menu) {
		switch (menu) {
			case 1:{
				Produk();
				
				break;
			}
			case 2:{
				Pesanan();
				
				break;
			}
			case 3:{
				Transaksi();
				
				break;
			}
			case 4:{
				System.exit(0);
				
				break;
			}
		}
	}
	
	public void Menu() {
		System.out.println("MENU UTAMA");
		System.out.println("[1] Lihat, Tambah Stok, dan Pemesanan Produk");
		System.out.println("[2] Pembayaran/Pembatalan Pesanan");
		System.out.println("[3] Lihat Laporan Transaksi Pesanan Berhasil");
		System.out.println("[4] Keluar");
		
		int menu;
		
		do {
			System.out.print("Pilih [1-4]: ");
			menu = scan.nextInt();
			scan.nextLine();
			Choice(menu);
		} while (menu >= 1 || menu <= 4);
	}

	public Main() {
		// TODO Auto-generated constructor stub
		
		Menu();
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new Main();
		
	}

}
