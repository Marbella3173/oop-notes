package lat2;

import java.util.*;
import java.sql.*;

public class Main {
	
	Connect con = Connect.getConnection();
	
	PreparedStatement ps;
	ResultSet rs;
	
	Vector<Transaction> newTransaction = new Vector<>();
	Vector<User> newUser = new Vector<>();
	Vector<CryptoCurrency> newCoin = new Vector<>();
	Vector<Assets> newAsset = new Vector<>();
	
	public void printAssets() {
		for (Assets a : newAsset) {
			User u = getUserName(a.getUserID());
			
			System.out.println("Name : " + u.getUser_name());
			System.out.println("Assets in Rp : " + a.getSum_of_assets());
			System.out.println("");
		}
	}
	
	int idx = 1;
	
	public void buyAndSell() {
		for (Transaction t : newTransaction) {
			User u = getUserName(t.getUserID());
			CryptoCurrency c = getCoinName(t.getCoinID());
			
			Double money = u.getRupiah_balance();
			Double price = c.getRp_exchange_rate() * t.getNum_of_coins();
			
			Double coin = 0.0;
			
			if(t.getTransaction().contains("buy") == true) {
				if(money < price) {
					System.out.println("buy transaction unsuccessful.");
				}else {
					System.out.println("buy transaction successful");
					
					coin += t.getNum_of_coins();
					money -= price;
				}
			}else {
				if(coin < t.getNum_of_coins()) {
					System.out.println("sell transaction unsuccessful");
				}else {
					System.out.println("sell transaction successful");
					
					money += price;
					coin -= t.getNum_of_coins();
				}
			}
			
			String query = "insert into Assets values (?, ?, ?, ?)";
			
			ps = con.prepareStatement(query);
			
			try {
				ps.setInt(1, idx);
				ps.setString(2, t.getUserID());
				ps.setString(3, t.getCoinID());
				ps.setDouble(4, money);
				
				newAsset.add(new Assets(idx, t.getUserID(), t.getCoinID(), money));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			idx++;
		}
	}
	
	public void printCoin() {
		String name1 = null;
		Double sold1 = 0.0;
		Double bought1 = 0.0;
		Double price1 = 0.0;
		
		String name2 = null;
		Double sold2 = 0.0;
		Double bought2 = 0.0;
		Double price2 = 0.0;
		
		String name3 = null;
		Double sold3 = 0.0;
		Double bought3 = 0.0;
		Double price3 = 0.0;
		
		for (Transaction t : newTransaction) {
			if(t.getCoinID().contains("BTC") == true) {
				CryptoCurrency c = getCoinName(t.getCoinID());
				
				if(t.getTransaction().contains("sell") == true) {
//					sold1 += t.getNum_of_coins();
				}else {
					bought1 += t.getNum_of_coins();
				}
				
				name1 = c.getCoinID();
				price1 = c.getRp_exchange_rate();
			}else if(t.getCoinID().contains("ETH") == true) {
				CryptoCurrency c = getCoinName(t.getCoinID());
				
				if(t.getTransaction().contains("sell") == true) {
					sold2 += t.getNum_of_coins();
				}else {
					bought2 += t.getNum_of_coins();
				}
				
				name2 = c.getCoinID();
				price2 = c.getRp_exchange_rate();
			}else {
				CryptoCurrency c = getCoinName(t.getCoinID());
				
				if(t.getTransaction().contains("sell") == true) {
					sold3 += t.getNum_of_coins();
				}else {
					bought3 += t.getNum_of_coins();
				}
				
				name3 = c.getCoinID();
				price3 = c.getRp_exchange_rate();
				}
			}
			
		System.out.println("Name : " + name1);
		System.out.println("Rp Exchange Rate : " + price1);
		System.out.println("Asset Sold : " + sold1);
		System.out.println("Asset Bought : " + bought1);
		System.out.println("Rp Sold : " + sold1 * price1);
		System.out.println("Rp Bought : " + bought1 * price1);
		System.out.println("");
		
		System.out.println("Name : " + name2);
		System.out.println("Rp Exchange Rate : " + price2);
		System.out.println("Asset Sold : " + sold2);
		System.out.println("Asset Bought : " + bought2);
		System.out.println("Rp Sold : " + sold2 * price2);
		System.out.println("Rp Bought : " + bought2 * price2);
		System.out.println("");
		
		System.out.println("Name : " + name3);
		System.out.println("Rp Exchange Rate : " + price3);
		System.out.println("Asset Sold : " + sold3);
		System.out.println("Asset Bought : " + bought3);
		System.out.println("Rp Sold : " + sold3 * price3);
		System.out.println("Rp Bought : " + bought3 * price3);
		System.out.println("");
	}
	
	public void printTransaction() {
		for (Transaction t : newTransaction) {
			User u = getUserName(t.getUserID());
			CryptoCurrency c = getCoinName(t.getCoinID());
			
			System.out.println("User name : " + u.getUser_name());
			System.out.println("Transaction : " + t.getTransaction());
			System.out.println("Coin name : " + c.getCoin_name());
			System.out.println("Number of coins : " + t.getNum_of_coins());
			System.out.println("");
		}
	}
	
	public Transaction getTransactionName(String id) {
		String query = "select * from Transaction where UserID = ?";
		
		ps = con.prepareStatement(query);
		
		try {
			ps.setString(1, id);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Integer transactionID = rs.getInt(1);
				String UserID = rs.getString(2);
				String CoinID = rs.getString(3);
				String transaction = rs.getString(4);
				Double num_of_coins = rs.getDouble(5);
				
				return new Transaction(transactionID, UserID, CoinID, transaction, num_of_coins);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public CryptoCurrency getCoinName(String id) {
		String query = "select * from CryptoCurrency where CoinID = ?";
		
		ps = con.prepareStatement(query);
		
		try {
			ps.setString(1, id);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String CoinID = rs.getString(1);
				String coin_name = rs.getString(2);
				Double rp_exchange_rate = rs.getDouble(3);
				
				return new CryptoCurrency(CoinID, coin_name, rp_exchange_rate);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public User getUserName(String id) {
		String query = "select * from User where UserID = ?";
		
		ps = con.prepareStatement(query);
		
		try {
			ps.setString(1, id);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String UserID = rs.getString(1);
				String user_name = rs.getString(2);
				Double rupiah_balance = rs.getDouble(3);
				
				return new User(UserID, user_name, rupiah_balance);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void getAsset() {
		String query = "select * from Assets";
		
		ps = con.prepareStatement(query);
		
		try {
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Integer assetID = rs.getInt(1);
				String UserID = rs.getString(2);
				String CoinID = rs.getString(3);
				Double sum_of_assets = rs.getDouble(4);
				
				newAsset.add(new Assets(assetID, UserID, CoinID, sum_of_assets));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getCoin() {
		String query = "select * from CryptoCurrency";
		
		ps = con.prepareStatement(query);
		
		try {
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String CoinID = rs.getString(1);
				String coin_name = rs.getString(2);
				Double rp_exchange_rate = rs.getDouble(3);
				
				newCoin.add(new CryptoCurrency(CoinID, coin_name, rp_exchange_rate));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getUser() {
		String query = "select * from User";
		
		ps = con.prepareStatement(query);
		
		try {
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String UserID = rs.getString(1);
				String user_name = rs.getString(2);
				Double rupiah_balance = rs.getDouble(3);
				
				newUser.add(new User(UserID, user_name, rupiah_balance));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getTransaction() {
		String query = "select * from Transaction";
		
		ps = con.prepareStatement(query);
		
		try {
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Integer transactionID = rs.getInt(1);
				String UserID = rs.getString(2);
				String CoinID = rs.getString(3);
				String transaction = rs.getString(4);
				Double num_of_coins = rs.getDouble(5);
				
				newTransaction.add(new Transaction(transactionID, UserID, CoinID, transaction, num_of_coins));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Main() {
		// TODO Auto-generated constructor stub
		
		// NOMOR 1
		
		newTransaction.clear();
		newUser.clear();
		newCoin.clear();
		
		getTransaction();
		getUser();
		getCoin();
		
		printTransaction();
		
		
		// NOMOR 2
		
		printCoin();
		
		
		// NOMOR 3
		
		buyAndSell();
		
		
		// NOMOR 4
		
		printAssets();
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new Main();
		
	}

}
